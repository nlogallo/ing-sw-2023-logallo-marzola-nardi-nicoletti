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

    /**
     * Constructor method
     * @throws RemoteException
     */
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

    /**
     * RMI method which handles the player setup
     * @param game
     * @param nickname
     * @return
     */
    public synchronized NetworkMessage RMIHandlePlayerSetup(Game game, String nickname){
        VirtualView virtualView = new VirtualView(game);
        RMIVirtualViews.put(nickname, virtualView);
        NetworkMessage setup = RMIVirtualViews.get(nickname).playerSetup(nickname);
        return setup;
    }

    /**
     * RMI method which gets the first player to play
     * @param nickname
     * @return
     */
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
            ObjectOutputStream outputStream = null;
            ObjectInputStream inputStream = null;
            try {
                outputStream = new ObjectOutputStream(this.clientSocket.getOutputStream());
                inputStream = new ObjectInputStream(this.clientSocket.getInputStream());
                boolean accept = true;
                do{
                    accept = true;
                    nickname = (String) inputStream.readObject();
                    if(nicknames.contains(nickname)){
                        accept = false;
                        outputStream.writeObject("EXNICKNAME");
                        outputStream.flush();
                    }else if(nickname.contains(" ") || nickname.length() > 15 || nickname.length() < 3){
                        accept = false;
                        outputStream.writeObject("WRNICKNAME");
                        outputStream.flush();
                    }
                }while (!accept);
                outputStream.writeObject("OKUSER");
                outputStream.flush();
                nicknames.add(nickname);
                boolean seat = false;
                game = findAvailableGame();
                if (game == null) {
                    outputStream.writeObject("newGame");
                    outputStream.flush();
                    int playersNumber = (Integer) inputStream.readObject();
                    game = createNewGame(playersNumber);
                    games.add(game);
                    System.out.println("New game created with id " + game.getId() + " for " + game.getPlayersNumber() + " players");
                    seat = true;
                } else {
                    outputStream.writeObject("addedToGame");
                    outputStream.flush();
                }
                Player player = new Player(seat, new Shelf(), nickname, game);
                game.addPlayer(player);
                System.out.println("Added " + nickname + " to game with id " + game.getId());
                String message = "Hi " + nickname + "!\nYou have been added to game with id " + game.getId() + "\nYour game will start when the players number is fulfilled";
                outputStream.writeObject(message);
                outputStream.flush();

                while (true) {
                    this.clientSocket.setKeepAlive(true);
                    if (game.getPlayers().size() == game.getPlayersNumber()) {
                        game.setState(GameState.STARTED);
                        if(seat) {
                            System.out.println("Game with id " + game.getId() + " started!");
                        }
                        outputStream.writeObject("START_GAME");
                        outputStream.flush();
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

                outputStream.flush();
                VirtualView virtualView = new VirtualView(game);
                NetworkMessage setup = virtualView.playerSetup(nickname);
                outputStream.writeObject(setup);
                outputStream.flush();
                NetworkMessage firstPlayer = virtualView.updateResult();
                outputStream.writeObject(firstPlayer);
                outputStream.flush();
                boolean turnFinished = false;
                while (true) {
                    this.clientSocket.setKeepAlive(true);
                    if(games.get(game.getId() - 1).getState() == GameState.STARTED) {
                        if(games.get(game.getId() - 1).getCurrentPlayer().getNickname().equals(nickname)) {
                            NetworkMessage netMessage = (NetworkMessage) inputStream.readObject();
                            if (netMessage.getRequestId().equals("MT")) {
                                //if (game.getCurrentPlayer().getNickname().equals(nickname)) {
                                    NetworkMessage resp = virtualView.moveTiles(netMessage, player);
                                    game = virtualView.getGame();
                                    System.out.println("ANNASOR " + game.getCurrentPlayer().getNickname());
                                    outputStream.reset();
                                    outputStream.writeObject(resp);
                                    games.set(game.getId() - 1, game);
                                //}
                            }
                        }
                        if(games.get(game.getId() - 1).getMutexAtIndex(playerIndex)) {
                            System.out.println("AHAJSJ " + games.get(game.getId() - 1).getCurrentPlayer().getNickname());
                            virtualView = new VirtualView(games.get(game.getId() - 1));
                            outputStream.reset();
                            outputStream.writeObject(virtualView.updateBoard());
                            outputStream.reset();
                            outputStream.writeObject(virtualView.updateGameTokens());
                            outputStream.reset();
                            NetworkMessage nj = virtualView.updateResult();
                            System.out.println("UAUUA " + nj.getContent().get(0));
                            outputStream.writeObject(nj);
                            game.setMutexFalseAtIndex(playerIndex);
                            games.set(game.getId() - 1, game);
                        }
                    } else {
                        NetworkMessage errMessage = new NetworkMessage();
                        errMessage.setRequestId("ER");
                        errMessage.setTextMessage("A player left the game. Game end here.");
                        outputStream.writeObject(errMessage);
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
            } catch (IOException e) {
                System.err.println("User " + nickname + " left the server.");
            } catch (StringIndexOutOfBoundsException e) {
                System.err.println("Communication error. User disconnected.");
            } catch (ClassNotFoundException e) {
                System.err.println("Errore interno: " + e);
            }
            nicknames.remove(nickname);
        }
    }
}
