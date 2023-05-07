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

/**
 * Client class for RMI and TCP connection
 */
public class MyShelfieClient {

    static Socket socket;
    static MyShelfieRMIInterface server;
    static ObjectOutputStream outputStream;
    static ObjectInputStream inputStream;

    /**
     * Main method: Asks to the user to choose between TCP and RMI connection and establishes the connection with the chosen protocol.
     * @param args
     * @throws IOException
     */
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
                    outputStream = new ObjectOutputStream(socket.getOutputStream());
                    inputStream = new ObjectInputStream(socket.getInputStream());
                    try {
                        //Scanner sc= new Scanner(System.in);
                        byte[] buffer = new byte[4096];
                        String nickname = "";
                        String serverAnswer = "";
                        int i = 0;
                        do {
                            if(i > 0){
                                if(serverAnswer.equals("EXNICKNAME"))
                                    System.err.println("User with nickname '" + nickname + "' already exists. Choose another nickname.");
                                if(serverAnswer.equals("WRNICKNAME"))
                                    System.err.println("Wrong nickname pattern! You nickname cannot contain spaces and must have between 3 and 15 characters!");
                            }
                            i++;
                            System.out.println("Enter your nickname: "); //inserisce nickname
                            nickname = sc.nextLine();
                            outputStream.writeObject(nickname);
                            outputStream.flush();
                            serverAnswer = (String) inputStream.readObject();
                        } while (!serverAnswer.equals("OKUSER"));

                        String command = (String) inputStream.readObject();
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
                            outputStream.writeObject(playersNumber);
                            outputStream.flush();
                        }
                        command = (String) inputStream.readObject();
                        System.out.println(command);

                        while (true) {
                            command = (String) inputStream.readObject();
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
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
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
                server = (MyShelfieRMIInterface) Naming.lookup("rmi://localhost:1099/Server");
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
                    if (server.RMICheckForStart(game.getId() - 1)) {
                        break;
                    }
                }
                System.out.println("GAME STARTED!");
                //new MyShelfieClient().handleGameRMI(nickname);
            } catch (Exception e) {
                System.err.println("Connection failed!" + e);
            }
        }
    }

    /**
     * This methods handles the Game with TCP connection
     * @param nickname
     */
    private void handleGameTCP(String nickname){
        ClientViewObservable view = new ClientViewObservable(new CLIView(nickname));
        ClientController controller = new ClientController(view, this, nickname);
        view.setClientController(controller);
        try {
            NetworkMessage nm = (NetworkMessage) inputStream.readObject();
            controller.playerSetup(nm);
            NetworkMessage currentPlayer = (NetworkMessage) inputStream.readObject();
            controller.updateResults(currentPlayer);
            while (true) {
                //if(!currentPlayer.getContent().get(0).equals(nickname)){
                    NetworkMessage board = (NetworkMessage) inputStream.readObject();
                    if(board.getRequestId().equals("ER")){
                        System.err.println("\n" + board.getTextMessage());
                        break;
                    }
                    controller.updateBoard(board);
                //}
                NetworkMessage token = (NetworkMessage) inputStream.readObject();
                controller.updateGameTokens(token);
                NetworkMessage result = (NetworkMessage) inputStream.readObject();
                System.out.println(result.getContent().get(0));
                controller.updateResults(result);
            }
        } catch (IOException e) {
            System.err.println("\nConnection lost!");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method handles the game with RMI connection
     * @param game
     * @param nickname
     */
    private void handleGameRMI(Game game, String nickname){
        ClientViewObservable view = new ClientViewObservable(new CLIView(nickname));
        ClientController controller = new ClientController(view, this, nickname);
        try {
            NetworkMessage nm = server.RMIHandlePlayerSetup(game, nickname);
            controller.playerSetup(nm);
            NetworkMessage currentPlayer = server.RMIGetFirstPlayer(nickname);
            controller.updateResults(currentPlayer);
            /*while (true) {
                if(!currentPlayer.getContent().get(0).equals(nickname)){
                    NetworkMessage board = (NetworkMessage) objectInputStream.readObject();
                    if(board.getRequestId().equals("ER")){
                        System.err.println("\n" + board.getTextMessage());
                        break;
                    }
                    controller.updateBoard(board);
                }
                NetworkMessage token = (NetworkMessage) objectInputStream.readObject();
                controller.updateGameTokens(token);
                NetworkMessage result = (NetworkMessage) objectInputStream.readObject();
                controller.updateResults(result);
            }*/
        } catch (IOException e) {
            System.err.println("\nConnection lost!");
        } /*catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }*/
    }

    /**
     * This methods handles the communication between controller and server by client
     * @param networkMessage
     * @return
     */
    public NetworkMessage sendMessage(NetworkMessage networkMessage){
        try {
            socket.setKeepAlive(true);
            outputStream.flush();
            if(networkMessage.getRequestId().equals("MT")){
                outputStream.writeObject(networkMessage);
                return (NetworkMessage) inputStream.readObject();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}

/**
 * RMI interface for server methods to call
 */
interface MyShelfieRMIInterface extends Remote {
    Game checkforAvailableGame(String message) throws RemoteException;
    Game RMIHandleGameCreation(int playersNumber, String nickname) throws RemoteException;
    boolean RMICheckForStart(int gameId) throws RemoteException;
    NetworkMessage RMIHandlePlayerSetup(Game game, String nickname) throws RemoteException;
    NetworkMessage RMIGetFirstPlayer(String nickname) throws RemoteException;
}
