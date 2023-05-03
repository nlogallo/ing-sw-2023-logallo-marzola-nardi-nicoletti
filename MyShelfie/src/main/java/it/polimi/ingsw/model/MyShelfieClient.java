package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.ClientController;
import it.polimi.ingsw.utils.NetworkMessage;
import it.polimi.ingsw.view.CLIView;
import it.polimi.ingsw.view.ClientViewObservable;
import org.apache.commons.lang3.SerializationUtils;

import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Scanner;

public class MyShelfieClient {

    static Socket socket;


    public static void main(String args[]) throws IOException {
        System.out.println("Welcome to MyShelfie!");
        System.out.println("Choose your connection protocol:\n1. TCP\n2. RMI\n3. Quit\n");
        Scanner sc = new Scanner(System.in);
        int protocol = Integer.parseInt(sc.nextLine());
        do {
            if(protocol < 1 || protocol > 3){
                System.out.println("Wrong choice!\n Please choose your connection protocol:\n1. TCP\n2. RMI\n3. Quit\n");
            }
        }while(protocol < 1 || protocol > 3);
        if(protocol == 1) {
            System.out.println("Connecting to TCP server...");
            String host = "localhost";
            int port = 12345;
            //Socket socket;
            try {
                socket = new Socket(host, port);
                //socket.setSoTimeout(5000);
                if (socket.isConnected()) {
                    System.out.println("Connected! :)");
                    socket.setKeepAlive(true);
                    try {
                        InputStream input = socket.getInputStream();
                        OutputStream output = socket.getOutputStream();
                        //Scanner sc= new Scanner(System.in);
                        byte[] buffer = new byte[4096];

                        System.out.print("Enter your nickname: "); //inserisce nickname
                        String str = sc.nextLine();

                        String nickname = str;
                        output.write(str.getBytes());
                        output.flush();

                        String command = new String(buffer, 0, input.read(buffer));
                        if (command.equals("newGame")) {
                            System.out.println("No games available, creating a new one...");
                            int playersNumber;
                            do {
                                System.out.println("Choose players number: ");
                                playersNumber = Integer.parseInt(sc.nextLine());
                                if (playersNumber > 4 || playersNumber < 2) {
                                    System.out.println("Players number must be 2, 3 or 4");
                                }
                            } while (playersNumber > 4 || playersNumber < 2);
                            output.write(String.valueOf(playersNumber).getBytes());
                            output.flush();
                        }
                        command = new String(buffer, 0, input.read(buffer));
                        System.out.println(command);

                        while (true) {
                            command = new String(buffer, 0, input.read(buffer));
                            if (command.equals("START_GAME")) {
                                System.out.println("GAME STARTED!");
                                new MyShelfieClient().handleGameTCP(nickname);
                                break;
                            }
                        }//while(!command.equals("START_GAME"));
                    } catch (SocketTimeoutException e) {
                        System.err.println("Socket timed out");
                    } catch (StringIndexOutOfBoundsException e) {
                        System.err.println("Server went offline!");
                    } finally {
                        socket.close();
                    }
                } else {
                    System.err.println("Connection to the server has failed :(");
                }
            } catch (
                    ConnectException e) {
                System.err.print("Connection to the server has failed :(");
            }
        } else if (protocol == 2) {
            try {
                System.out.println("Connecting to RMI server...");
                MyShelfieRMIInterface server = (MyShelfieRMIInterface) Naming.lookup("rmi://localhost:1099/Server");
                //Scanner scanner = new Scanner(System.in);
                System.out.println("Connected! :)");
                System.out.println("Enter your nickname: ");
                String nickname = sc.nextLine();
                Game game = server.checkforAvailableGame(nickname);
                if (game == null) {
                    System.out.println("No games available, creating a new one...");
                    int playersNumber;
                    do {
                        System.out.println("Choose players number: ");
                        playersNumber = Integer.parseInt(sc.nextLine());
                        if (playersNumber > 4 || playersNumber < 2) {
                            System.err.println("Players number must be 2, 3 or 4");
                        }
                    } while (playersNumber > 4);
                    game = server.RMIHandleGameCreation(playersNumber, nickname);
                }
                System.out.println("Hi " + nickname + "!\nYou have been added to game with id " + game.getId() + "\nYour game will start when the players number is fulfilled");
                while (true) {
                    if (server.checkForStart(game.getId() - 1)) {
                        break;
                    }
                }
                System.out.println("GAME STARTED!");
                //new MyShelfieClient().handleGameTCP(nickname, 2);
            } catch (Exception e) {
                System.err.println("Connection failed!" + e);
                //System.err.println("Exception in main: " + e);
            }
        }
    }

    private void handleGameTCP(String nickname){
        ClientViewObservable view = new ClientViewObservable(new CLIView(nickname));
        ClientController controller = new ClientController(view, this, nickname);
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            NetworkMessage nm = (NetworkMessage) objectInputStream.readObject();
            controller.playerSetup(nm);
            NetworkMessage currentPlayer = (NetworkMessage) objectInputStream.readObject();
            controller.updateResults(currentPlayer);
            while (true) {
                if(!currentPlayer.getContent().get(0).equals(nickname)){
                    NetworkMessage board = (NetworkMessage) objectInputStream.readObject();
                    controller.updateBoard(board);
                }
                NetworkMessage token = (NetworkMessage) objectInputStream.readObject();
                controller.updateGameTokens(token);
                NetworkMessage result = (NetworkMessage) objectInputStream.readObject();
                controller.updateResults(result);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public NetworkMessage sendMessage(NetworkMessage networkMessage){
        InputStream input = null;
        OutputStream output = null;
        try {
            input = socket.getInputStream();
            output = socket.getOutputStream();
            if(networkMessage.getRequestId().equals("MT")){
                output.write(SerializationUtils.serialize(networkMessage));
                NetworkMessage resp = new NetworkMessage();
                resp = SerializationUtils.deserialize(input.readAllBytes());
                return resp;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}

interface MyShelfieRMIInterface extends Remote {
    Game checkforAvailableGame(String message) throws RemoteException;
    Game RMIHandleGameCreation(int playersNumber, String nickname) throws RemoteException;
    boolean checkForStart(int gameId) throws RemoteException;
}
