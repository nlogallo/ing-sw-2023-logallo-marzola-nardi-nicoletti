package it.polimi.ingsw.model;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Scanner;

public class MyShelfieClient {
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
            Socket socket;
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
                    game = server.handleGameCreation(playersNumber, nickname);
                }
                System.out.println("Hi " + nickname + "!\nYou have been added to game with id " + game.getId() + "\nYour game will start when the players number is fulfilled");
                while (true) {
                    if (server.checkForStart(game.getId() - 1)) {
                        break;
                    }
                }
                System.out.println("GAME STARTED!");
            } catch (Exception e) {
                System.err.println("Connection failed!" + e);
                //System.err.println("Exception in main: " + e);
            }
        }
    }
}

interface MyShelfieRMIInterface extends Remote {
    Game checkforAvailableGame(String message) throws RemoteException;
    Game handleGameCreation(int playersNumber, String nickname) throws RemoteException;
    boolean checkForStart(int gameId) throws RemoteException;
}
