package it.polimi.ingsw.model;

import java.net.*;
import java.io.*;
import java.util.Scanner;

/**
 * @author  Nunzio Logallo
 * Client class
 */
public class MyShelfieClient {
    public static void main(String[] args) throws IOException {
        System.out.println("Welcome to MyShelfie!");
        System.out.println("Connecting to server...");
        String host = "localhost";
        int port = 12345;
        Socket socket;
        try {
            socket = new Socket(host, port);
            //socket.setSoTimeout(5000);
            if(socket.isConnected()){
                System.out.println("Connected! :)");
                try {
                    InputStream input = socket.getInputStream();
                    OutputStream output = socket.getOutputStream();
                    Scanner sc= new Scanner(System.in);
                    byte[] buffer = new byte[1024];

                    System.out.print("Enter your nickname: "); //inserisce nickname
                    String str= sc.nextLine();

                    output.write(str.getBytes());
                    output.flush();

                    String command = new String(buffer, 0, input.read(buffer));
                    if(command.equals("newGame")){
                        System.out.println("No games available, creating a new one...");
                        int playersNumber;
                        do{
                            System.out.println("Choose players number: ");
                            playersNumber = Integer.parseInt(sc.nextLine());
                            if(playersNumber > 4 || playersNumber < 2){
                                System.out.println("Players number must be 2, 3 or 4");
                            }
                        }while(playersNumber > 4);
                        output.write(String.valueOf(playersNumber).getBytes());
                        output.flush();
                    }
                    command = new String(buffer, 0, input.read(buffer));
                    System.out.println(command);
////
                    while(!command.equals("START_GAME")) {
                        command = new String(buffer, 0, input.read(buffer));
                        if (command.equals("START_GAME")) {
                            System.out.println("GAME STARTED!");
                        }
                    }
                } catch (SocketTimeoutException e) {
                    System.err.println("Socket timed out");
                } finally {
                    socket.close();
                }
            }else{
                System.err.println("Connection to the server has failed :(");
            }
        }catch (ConnectException e) {
            System.err.print("Connection to the server has failed :(");
        }
    }
}