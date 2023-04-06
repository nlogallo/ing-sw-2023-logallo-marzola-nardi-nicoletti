package it.polimi.ingsw.model;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * @author  Nunzio Logallo
 * Server class
 */
public class MyShelfieTCPServer {

    private static ArrayList<Game> games = new ArrayList<>();

    /**
     * This method instantiate the server socket and listens on the port 12345.
     * When a client connects to server a new thread is created and started.
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        int port = 12345;
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Server started on port " + port);

        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected from " + clientSocket.getInetAddress().getHostAddress());
            ClientHandler client = new ClientHandler(clientSocket);
            new Thread(client).start();
        }
    }

    /**
     * This method searches for an available game
     * @return game
     */
    private static Game findAvailableGame() {
        for (Game game : games) {
            if (game.getPlayers().size() < game.getPlayersNumber() && game.getState()==GameState.WAITING_FOR_PLAYERS) {
                return game;
            }
        }
        return null;
    }

    /**
     * This method creates a new game with the specified players number
     * @param playersNumber
     * @return game
     */
    private static Game createNewGame(int playersNumber) {
        int id = games.size() + 1;
        return new Game(id, playersNumber);
    }

    /**
     * This runnable class is the real thread which handle the initial operations after the connection of the client
     */
    private static class ClientHandler implements Runnable {
        private final Socket clientSocket;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        public void run() {
            try {
                InputStream input = clientSocket.getInputStream();
                OutputStream output = clientSocket.getOutputStream();
                byte[] buffer = new byte[1024];
                int bytesRead = input.read(buffer);
                String nickname = new String(buffer, 0, bytesRead);
                //this.clientSocket.;
                boolean seat = false;
                Game game = findAvailableGame();
                if (game == null) {
                    output.write("newGame".getBytes());
                    int playersNumber = Integer.parseInt(new String(buffer, 0, input.read(buffer)));
                    game = createNewGame(playersNumber);
                    games.add(game);
                    System.out.println("New game created with id " + game.getId() + " for " + game.getPlayersNumber() + " players");
                    seat = true;
                }else{
                    output.write("addedToGame".getBytes());
                }
                game.addPlayer(new Player(seat, new Shelf(), nickname, game));
                System.out.println("Added " + nickname + " to game with id " + game.getId());
                String message = "Hi " + nickname + "!\nYou have been added to game with id " + game.getId() + "\nYour game will start when the players number is fulfilled";
                output.write(message.getBytes());
                output.flush();

                while(true) {
                    this.clientSocket.setKeepAlive(true);
                    if (game.getPlayers().size() == game.getPlayersNumber()) {
                        game.setState(GameState.STARTED);
                        System.out.println("Game with id " + game.getId() + " started!");
                        output.write("START_GAME".getBytes());
                        output.flush();
                        break;
                    }
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
