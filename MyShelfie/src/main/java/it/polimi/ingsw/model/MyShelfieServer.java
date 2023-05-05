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
import java.util.HashMap;

/**
 * Server class
 */
public class MyShelfieServer extends UnicastRemoteObject implements MyShelfieRMIInterface {
    private static ArrayList<Game> games = new ArrayList<>();
    private static ArrayList<String> nicknames = new ArrayList<>();
    private static HashMap<String, VirtualView> RMIVirtualViews = new HashMap<>();

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
                //ClientRMIHandler RMIClient = new ClientRMIHandler(server);
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

    public synchronized NetworkMessage RMIHandlePlayerSetup(Game game, String nickname){
        VirtualView virtualView = new VirtualView(game);
        RMIVirtualViews.put(nickname, virtualView);
        NetworkMessage setup = RMIVirtualViews.get(nickname).playerSetup(nickname);
        return setup;
    }

    public synchronized NetworkMessage RMIGetFirstPlayer(String nickname){
        NetworkMessage firstPlayer = RMIVirtualViews.get(nickname).updateResult();
        return firstPlayer;
    }

    /**
     * RMI method which checks for game start
     * @param gameId
     * @return
     */
    public synchronized boolean RMICheckForStart(int gameId){
        if(games.get(gameId).getPlayers().size() == games.get(gameId).getPlayersNumber()) {
            games.get(gameId).startGame();
            return true;
        }
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
            String nickname = "";
            Game game = new Game(-1, 0);
            ObjectOutputStream objectOutputStream = null;
            ObjectInputStream objectInputStream = null;
            try {
                InputStream input = clientSocket.getInputStream();
                OutputStream output = clientSocket.getOutputStream();
                byte[] buffer = new byte[1024];
                do{
                    int bytesRead = input.read(buffer);
                    nickname = new String(buffer, 0, bytesRead);
                    if(nicknames.contains(nickname)){
                        output.write("EXUSER".getBytes());
                    }
                }while (nicknames.contains(nickname));
                output.write("OKUSER".getBytes());
                nicknames.add(nickname);
                boolean seat = false;
                game = findAvailableGame();
                if (game == null) {
                    output.write("newGame".getBytes());
                    int playersNumber = Integer.parseInt(new String(buffer, 0, input.read(buffer)));
                    game = createNewGame(playersNumber);
                    games.add(game);
                    System.out.println("New game created with id " + game.getId() + " for " + game.getPlayersNumber() + " players");
                    seat = true;
                } else {
                    output.write("addedToGame".getBytes());
                }
                Player player = new Player(seat, new Shelf(), nickname, game);
                game.addPlayer(player);
                System.out.println("Added " + nickname + " to game with id " + game.getId());
                String message = "Hi " + nickname + "!\nYou have been added to game with id " + game.getId() + "\nYour game will start when the players number is fulfilled";
                output.write(message.getBytes());
                output.flush();

                while (true) {
                    this.clientSocket.setKeepAlive(true);
                    if (game.getPlayers().size() == game.getPlayersNumber()) {
                        game.setState(GameState.STARTED);
                        if(seat) {
                            System.out.println("Game with id " + game.getId() + " started!");
                        }
                        output.write("START_GAME".getBytes());
                        output.flush();
                        break;
                    }
                }

                int playerIndex = 0;
                for (int i=0; i<game.getPlayers().size(); i++){
                    if(game.getPlayers().get(i).getNickname().equals(nickname))
                        playerIndex = i;
                }

                game = games.get(game.getId()-1);

                if(seat){
                    game.startGame();
                    games.set(game.getId()-1, game);
                }else {
                    while (true) {
                        this.clientSocket.setKeepAlive(true);

                        if (games.get(game.getId() - 1).isSetupFinished()) {
                            game = games.get(game.getId() - 1);
                            break;
                        }
                    }
                }

                objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
                objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
                objectOutputStream.flush();
                VirtualView virtualView = new VirtualView(game);
                NetworkMessage setup = virtualView.playerSetup(nickname);
                objectOutputStream.writeObject(setup);
                NetworkMessage firstPlayer = virtualView.updateResult();
                objectOutputStream.writeObject(firstPlayer);
                while (true) {
                    if(games.get(game.getId() - 1).getState() == GameState.STARTED) {
                        if(game.getCurrentPlayer().getNickname().equals(nickname)) {
                            NetworkMessage netMessage = (NetworkMessage) objectInputStream.readObject();
                            if (netMessage.getRequestId().equals("MT")) {
                                if (game.getCurrentPlayer().getNickname().equals(nickname)) {
                                    NetworkMessage resp = virtualView.moveTiles(netMessage, player);
                                    objectOutputStream.writeObject(resp);
                                }
                            }
                        }
                        virtualView.updateBoard();
                        virtualView.updateGameTokens();
                        virtualView.updateResult();
                    } else {
                        NetworkMessage errMessage = new NetworkMessage();
                        errMessage.setRequestId("ER");
                        errMessage.setTextMessage("A player left the game. Game end here.");
                        objectOutputStream.writeObject(errMessage);
                    }
                    /*while (!game.getMutexAtIndex(playerIndex)){}
                    game.setMutexFalseAtIndex(playerIndex);
                    virtualView.updateBoard();
                    virtualView.updateGameTokens();
                    virtualView.updateResult();*/
                }

            } catch (EOFException e) {
                System.err.println("User " + nickname + " left the server.");
                System.err.println("Game with id " + game.getId() + " has been terminated.");
                games.get(game.getId() - 1).setState(GameState.ENDED);
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("User " + nickname + " left the server.");
            } catch (StringIndexOutOfBoundsException e) {
                System.err.println("Communication error. User disconnected.");
            }
        }
    }
}
