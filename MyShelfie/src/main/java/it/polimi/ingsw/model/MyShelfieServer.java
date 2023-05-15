package it.polimi.ingsw.model;

import it.polimi.ingsw.utils.NetworkMessage;
import it.polimi.ingsw.view.VirtualView;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
    private static HashMap<String, String> chatParser = new HashMap<>();

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
                int port = 25566;
                ServerSocket serverSocket = new ServerSocket(port);
                System.out.println("TCP Server started on port " + port);
                ServerSocket chatSocket = new ServerSocket(25567);
                while (true) {
                    //Game client handler
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("TCP Client connected from " + clientSocket.getInetAddress().getHostAddress());
                    ClientTCPHandler client = new ClientTCPHandler(clientSocket);
                    new Thread(client).start();
                    //Chat client handler
                    Socket chatClientSocket = chatSocket.accept();
                    System.out.println("Chat Client enabled for " + chatClientSocket.getInetAddress().getHostAddress());
                    ChatClientTCPHandler chat = new ChatClientTCPHandler(chatClientSocket, "");
                    new Thread(chat).start();
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
     * RMI method which checks game nickname
     * @param nickname
     * @return
     * @throws RemoteException
     */
    public synchronized String RMICheckNickname(String nickname) throws RemoteException {
        if(nicknames.contains(nickname))
            return "nicknameExists";
        else if(nickname.contains(" ") || nickname.length() > 15 || nickname.length() < 3){
            return "nicknameWrong";
        }
        nicknames.add(nickname);
        return "nicknameOk";
    }

    /**
     * RMI method which checks for available games
     * @param message
     * @return
     * @throws RemoteException
     */
    public synchronized Game RMICheckforAvailableGame(String message) throws RemoteException {
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
    public synchronized NetworkMessage RMIHandlePlayerSetup(Game game, String nickname) throws RemoteException {
        boolean seat = nickname.equals(game.getPlayers().get(0).getNickname());
        if(seat){
            game.startGame();
            games.set(game.getId() - 1, game);
        }else {
            while (true) {
                if (games.get(game.getId() - 1).isSetupFinished()) {
                    game = games.get(game.getId() - 1);
                    break;
                }
            }
        }
        VirtualView virtualView = new VirtualView(game, nickname);
        NetworkMessage setup = virtualView.playerSetup(nickname);
        RMIVirtualViews.put(nickname, virtualView);
        setup.addContent(game);
        return setup;
    }

    /**
     * RMI method which gets the first player to play
     * @param nickname
     * @return
     */
    public synchronized NetworkMessage RMIGetFirstPlayer(String nickname) throws RemoteException {
        VirtualView virtualView = RMIVirtualViews.get(nickname);
        NetworkMessage firstPlayer = virtualView.updateResult();
        RMIVirtualViews.put(nickname, virtualView);
        return firstPlayer;
    }

    /**
     * RMI method which checks for game start
     * @param gameId
     * @return
     */
    public synchronized boolean RMICheckForStart(int gameId, boolean hasSeat) throws RemoteException {
        Game game = games.get(gameId - 1);
        if(game.getPlayers().size() == game.getPlayersNumber()) {
            return true;
        }
        return false;
    }

    /**
     * RMI method which gets a specific game
     * @param gameId
     * @return
     * @throws RemoteException
     */
    public synchronized Game RMIGetGame(int gameId) throws RemoteException{
        return games.get(gameId - 1);
    }

    /**
     * RMI method which gets results of a game turn
     * @param gameId
     * @param nickname
     * @return
     * @throws RemoteException
     */
    public synchronized ArrayList<NetworkMessage> RMIGetResult(int gameId, String nickname) throws RemoteException {
        int playerIndex = 0;
        ArrayList<NetworkMessage> result = new ArrayList<>();
        Game game = games.get(gameId - 1);
        VirtualView virtualView = RMIVirtualViews.get(nickname);
        for (int i=0; i<game.getPlayers().size(); i++){
            if(game.getPlayers().get(i).getNickname().equals(nickname))
                playerIndex = i;
        }
        //if (games.get(gameId - 1).getMutexAtIndex(playerIndex)) {
            games.set(gameId - 1, game);
            result.add(virtualView.updateBoard());
            result.add(virtualView.updateGameTokens());
            result.add(virtualView.updateResult());
            return result;
        //}
        //return null;
    }

    /**
     * RMI method which gets game mutex status
     * @param gameId
     * @param playerIndex
     * @return
     */
    public synchronized boolean RMIGetMutexAtIndex(int gameId, int playerIndex) throws RemoteException{
        return games.get(gameId - 1).getMutexAtIndex(playerIndex);
    }

    public synchronized void RMISetMutexFalseAtIndex(int gameId, int playerIndex) throws RemoteException{
        Game game = games.get(gameId - 1);
        game.setMutexFalseAtIndex(playerIndex);
        games.set(gameId - 1, game);
    }

    public synchronized NetworkMessage RMIMoveTiles(NetworkMessage networkMessage, String nickname, int gameId) throws RemoteException{
        VirtualView virtualView = RMIVirtualViews.get(nickname);
        if (networkMessage.getRequestId().equals("MT")) {
            Game game = games.get(gameId - 1);
            Player player = null;
            for(int i = 0; i < game.getPlayersNumber(); i++){
                if(game.getPlayers().get(i).getNickname().equals(nickname))
                    player = game.getPlayers().get(i);
            }
            NetworkMessage resp = virtualView.moveTiles(networkMessage, player);
            game = virtualView.getGame();
            games.set(game.getId() - 1, game);
            return resp;
        }
        return null;
    }

    public synchronized NetworkMessage RMISendMessage(NetworkMessage networkMessage, String nickname, int gameId) throws RemoteException {
        return null;
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
                        outputStream.writeObject("nicknameExists");
                        outputStream.flush();
                    }else if(nickname.contains(" ") || nickname.length() > 15 || nickname.length() < 3){
                        accept = false;
                        outputStream.writeObject("nicknameWrong");
                        outputStream.flush();
                    }
                }while (!accept);
                outputStream.writeObject("nicknameOk");
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
                outputStream.writeObject(game.getId());
                outputStream.flush();

                while (true) {
                    this.clientSocket.setKeepAlive(true);
                    if (game.getPlayers().size() == game.getPlayersNumber()) {
                        game.setState(GameState.STARTED);
                        if(seat) {
                            System.out.println("Game with id " + game.getId() + " started!");
                        }
                        outputStream.writeObject("startGame");
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
                VirtualView virtualView = new VirtualView(game, game.getCurrentPlayer().getNickname());
                NetworkMessage setup = virtualView.playerSetup(nickname);
                outputStream.writeObject(setup);
                outputStream.flush();
                NetworkMessage firstPlayer = virtualView.updateResult();
                outputStream.writeObject(firstPlayer);
                outputStream.flush();
                while (true) {
                    this.clientSocket.setKeepAlive(true);
                    if(games.get(game.getId() - 1).getState() == GameState.STARTED && games.get(game.getId() - 1).getCurrentPlayer() != null) {
                        if(games.get(game.getId() - 1).getCurrentPlayer().getNickname().equals(nickname)) {
                            NetworkMessage netMessage = (NetworkMessage) inputStream.readObject();
                            if (netMessage.getRequestId().equals("MT")) {
                                NetworkMessage resp = virtualView.moveTiles(netMessage, player);
                                game = virtualView.getGame();
                                outputStream.reset();
                                outputStream.writeObject(resp);
                                games.set(game.getId() - 1, game);
                            }
                        }
                        if (games.get(game.getId() - 1).getMutexAtIndex(playerIndex) && game.getCurrentPlayer() != null) {
                            virtualView = new VirtualView(games.get(game.getId() - 1), game.getCurrentPlayer().getNickname());
                            outputStream.reset();
                            outputStream.writeObject(virtualView.updateBoard());
                            outputStream.reset();
                            outputStream.writeObject(virtualView.updateGameTokens());
                            outputStream.reset();
                            outputStream.writeObject(virtualView.updateResult());
                            game.setMutexFalseAtIndex(playerIndex);
                            games.set(game.getId() - 1, game);
                        }
                    } else {
                        NetworkMessage endMessage = new NetworkMessage();
                        if(games.get(game.getId() - 1).getCurrentPlayer() != null){
                            endMessage.setRequestId("ER");
                            endMessage.setTextMessage("A player left the game. Game end here.");
                        }else{
                            endMessage.setRequestId("ER");
                            endMessage.setTextMessage("Game ended! Good game to everyone!");
                        }
                        outputStream.writeObject(endMessage);
                    }
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

    private static class ChatClientTCPHandler implements Runnable {
        private final Socket clientSocket;
        private final String nickname;

        public ChatClientTCPHandler(Socket socket, String nickname) {
            this.clientSocket = socket;
            this.nickname = nickname;
        }

        public void run() {
            ObjectOutputStream outputStream = null;
            ObjectInputStream inputStream = null;
            try {
                outputStream = new ObjectOutputStream(this.clientSocket.getOutputStream());
                inputStream = new ObjectInputStream(this.clientSocket.getInputStream());
                while(true){
                    String msg = (String) inputStream.readObject();
                    if(msg.equals("chatOk")){
                        System.out.println("User from " + this.clientSocket.getInetAddress().getHostAddress() + " connected to chat!");
                        outputStream.writeObject("ciao iuser, purtroppo si, mi hai scoperto");
                    }
                }
            } catch (IOException e) {
            } catch (ClassNotFoundException e) {
            }
        }

    }
}
