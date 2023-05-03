package it.polimi.ingsw.model;

import it.polimi.ingsw.utils.NetworkMessage;
import it.polimi.ingsw.view.VirtualView;
import org.apache.commons.lang3.SerializationUtils;

import java.io.*;
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
    private static ArrayList<Game> games = new ArrayList<>();

    public MyShelfieServer() throws RemoteException{
        super();
    }

    /**
     * Executable main method which makes available TCP and RMI connections for clients on two separate threads.
     * @param args
     */
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

    /**
     * Utils method to check for available games
     * @param message
     * @return
     * @throws RemoteException
     */
    public synchronized Game checkforAvailableGame(String message) throws RemoteException {
        System.out.println("RMI client connected with nickname: " + message);
        String nickname = message;
        Game game = findAvailableGame();
        if(game == null) return game;
        game.addPlayer(new Player(false, new Shelf(), nickname, game));
        System.out.println("Added " + nickname + " to game with id " + game.getId());
        return game;
    }

    /**
     * RMI method to create game
     * @param playersNumber
     * @param nickname
     * @return
     * @throws RemoteException
     */
    public synchronized Game RMIHandleGameCreation(int playersNumber, String nickname) throws RemoteException {
        Game game = createNewGame(playersNumber);
        game.addPlayer(new Player(true, new Shelf(), nickname, game));
        games.add(game);
        System.out.println("New game created with id " + game.getId() + " for " + game.getPlayersNumber() + " players");
        return game;
    }

    /**
     * RMI method which checks for game start
     * @param gameId
     * @return
     */
    public synchronized boolean checkForStart(int gameId){
        if(games.get(gameId).getPlayers().size() == games.get(gameId).getPlayersNumber())
            return true;
        return false;
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
                Player player = new Player(seat, new Shelf(), nickname, game);
                game.addPlayer(player);
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

                int playerIndex = 0;
                for(int i=0; i<game.getPlayers().size(); i++){
                    if(game.getPlayers().get(i).getNickname().equals(nickname))
                        playerIndex = i;
                }

                game.startGame();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
                ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
                objectOutputStream.flush();
                VirtualView virtualView = new VirtualView(game);
                NetworkMessage setup = virtualView.playerSetup(nickname);
                objectOutputStream.writeObject(setup);
                NetworkMessage firstPlayer = virtualView.updateResult();
                objectOutputStream.writeObject(firstPlayer);
                while(true) {
                    if(game.getCurrentPlayer().getNickname().equals(nickname)) {
                        NetworkMessage moveTiles = SerializationUtils.deserialize(input.readAllBytes());
                        if (moveTiles.getRequestId().equals("MT")) {
                            NetworkMessage resp = virtualView.moveTiles(moveTiles, player);
                            objectOutputStream.writeObject(resp);
                        }
                    }
                    while (!game.getMutexAtIndex(playerIndex)){}
                    game.setMutexFalseAtIndex(playerIndex);
                    virtualView.updateBoard();
                    virtualView.updateGameTokens();
                    virtualView.updateResult();
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
