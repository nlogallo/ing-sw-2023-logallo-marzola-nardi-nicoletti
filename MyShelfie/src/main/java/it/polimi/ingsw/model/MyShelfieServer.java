package it.polimi.ingsw.model;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/**
 * Server class
 */
public class MyShelfieServer extends UnicastRemoteObject implements MyShelfieRMIInterface {
    private ArrayList<String> buffer;
    int bufferIndex = 0;
    private static ArrayList<Game> games = new ArrayList<>();

    public MyShelfieServer() throws RemoteException{
        super();
        buffer = new ArrayList<String>();
    }
    public static void main(String args[]) {
        new Thread(() -> {
            try {
                int port = 12345;
                ServerSocket serverSocket = new ServerSocket(port);
                System.out.println("TCP Server started on port " + port);
                while (true) {
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("TCP Client connected from " + clientSocket.getInetAddress().getHostAddress());
                    ClientTCPHandler client = new ClientTCPHandler(clientSocket);
                    new Thread(client).start();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
        new Thread(() -> {
            int port = 1099;
            try {
                MyShelfieServer server = new MyShelfieServer();
                Registry registry = LocateRegistry.createRegistry(port);
                registry.rebind("Server", server);
                System.out.println("RMI Server started on port " + port);
            } catch (Exception e) {
                System.out.println("Exception in main: " + e);
            }
        }).start();
    }

    /*public synchronized void sendRMIMessage(String message) throws RemoteException {
        buffer.add(message);
        switch (bufferIndex){
            case 0://First connection with nickname
                System.out.println("RMI client connected: " + message);
                String nickname = message;
                boolean seat = false;
                Game game = findAvailableGame();
                if (game == null) {
                    output.write("newGame".getBytes());
                }else{
                    output.write("addedToGame".getBytes());
                }
                break;
        }
        notifyAll();
    }*/

    public synchronized Game checkforAvailableGame(String message) throws RemoteException {
        System.out.println("RMI client connected with nickname: " + message);
        String nickname = message;
        Game game = findAvailableGame();
        if(game == null) return game;
        game.addPlayer(new Player(false, new Shelf(), nickname, game));
        System.out.println("Added " + nickname + " to game with id " + game.getId());
        return game;
    }

    public synchronized Game handleGameCreation(int playersNumber, String nickname) throws RemoteException {
        Game game = createNewGame(playersNumber);
        game.addPlayer(new Player(true, new Shelf(), nickname, game));
        games.add(game);
        System.out.println("New game created with id " + game.getId() + " for " + game.getPlayersNumber() + " players");
        return game;
    }

    public synchronized boolean checkForStart(int gameId){
        if(games.get(gameId).getPlayers().size() == games.get(gameId).getPlayersNumber())
            return true;
        return false;
    }

    /*public synchronized String receiveRMIMessage() throws RemoteException, InterruptedException {
        while (buffer.size() <= bufferIndex) {
            wait();
        }
        bufferIndex++;
        String message = buffer.get(bufferIndex);
        System.out.println("Sending message: " + message);
        return message;
    }*/

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
    private static class ClientTCPHandler implements Runnable {
        private final Socket clientSocket;

        public ClientTCPHandler(Socket socket) {
            this.clientSocket = socket;
        }

        public void run() {
            try {
                InputStream input = clientSocket.getInputStream();
                OutputStream output = clientSocket.getOutputStream();
                byte[] buffer = new byte[1024];
                int bytesRead = input.read(buffer);
                String nickname = new String(buffer, 0, bytesRead);
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
