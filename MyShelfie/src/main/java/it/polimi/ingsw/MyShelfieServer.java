package it.polimi.ingsw;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.utils.NetworkMessage;
import it.polimi.ingsw.view.VirtualView;

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
    private static HashMap<Integer, Game> games = new HashMap();
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
                ServerSocket chatSocket = new ServerSocket(port + 1);
                while (true) {
                    //Game client handler
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("TCP Client connected from " + clientSocket.getInetAddress().getHostAddress());
                    Socket chatClientSocket = chatSocket.accept();
                    System.out.println("Chat Client enabled for " + chatClientSocket.getInetAddress().getHostAddress());
                    ClientTCPHandler client = new ClientTCPHandler(clientSocket, chatClientSocket);
                    new Thread(client).start();
                    //Chat client handler
                }
            } catch (IOException e) {
                System.err.println("Exception in main: " + e);
            }
        }).start();
        new Thread(() -> {
            int port = 30034;
            try {
                MyShelfieServer server = new MyShelfieServer();
                Registry registry = LocateRegistry.createRegistry(port);
                registry.rebind("Server", server);
                Registry chatRegistry = LocateRegistry.createRegistry(port + 1);
                chatRegistry.rebind("chatServer", server);
                System.out.println("RMI Server started on port " + port);
            } catch (Exception e) {
                System.err.println("Exception in main: " + e);
            }
        }).start();
        restoreGames();
    }

    /**
     * This method check for the first available gameId
     * @return the first available id
     */
    private static int getFirstAvailableId(){
        int maxId = -1;
        Object[] keySet = games.keySet().toArray();
        for(int i = 0; i < keySet.length; i++){
            if(maxId < (Integer)keySet[i]){
                maxId = (Integer)keySet[i];
            }
        }
        for (int j = 0; j < maxId; j++) {
            if(games.get(j) == null){
                return j;
            }
        }
        return maxId + 1;
    }

    /**
     * This method deserialize in an Object
     *
     * @param data is an array of byte
     * @return an Object
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private static Object deserializeObject(byte[] data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        return objectInputStream.readObject();
    }

    /**
     * This method restore the infos of the game
     *
     * @return a Game object
     */
    private static void restoreGames() {
        Object object;
        File[] gameFiles = new File("data/savedGames").listFiles();
        for(int i = 0; i < gameFiles.length; i++) {
            File gameFile = gameFiles[i];
            byte[] data = new byte[(int) gameFile.length()];
            try {
                FileInputStream fileInputStream = new FileInputStream(gameFile);
                fileInputStream.read(data, 0, data.length);
                fileInputStream.close();
                object = deserializeObject(data);
            } catch (ClassNotFoundException | IOException e) {
                throw new RuntimeException(e);
            }
            int gameId = Integer.parseInt(gameFile.getName().substring(4, gameFile.getName().indexOf('.')));
            games.put(gameId, (Game) object);
        }
    }

    /**
     * This method deletes the game file with the given id
     * @param gameId
     */
    private static void deleteGame(int gameId){
        String filename = "game" + gameId + ".bin";
        File file = new File("data/savedGames/" + filename);
        if(file.exists()){
            if (file.delete()) {
                System.out.println("Game with id " + gameId + " deleted!");
            } else {
                System.out.println("Failed to delete game with id " + gameId);
            }
        }
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
    public synchronized NetworkMessage RMICheckforAvailableGame(String message) throws RemoteException {
        System.out.println("RMI client connected with nickname: " + message);
        String nickname = message;
        NetworkMessage networkMessage = new NetworkMessage();
        networkMessage.setRequestId("SYN");
        ArrayList<Game> gamesResult = findAvailableGame(nickname, false);
        for(Game game : gamesResult){
            networkMessage.addContent(game);
        }
        return networkMessage;
    }

    public synchronized Game RMISetPlayer(int gameId, String nickname) throws RemoteException {
        Game game = games.get(gameId);
        game.addPlayer(new Player(false, new Shelf(), nickname, game));
        System.out.println("Added " + nickname + " to game with id " + game.getId());
        games.put(gameId, game);
        return game;
    }

    public synchronized Game RMIDoWantToRecover(NetworkMessage networkMessage) {
        Game game = null;
        int gameId = (int) networkMessage.getContent().get(0);
        String nickname = (String) networkMessage.getContent().get(1);
        if (networkMessage.getRequestId().equals("DELGAME")) {
            deleteGame(gameId);
            games.remove(gameId);
            ArrayList<Game> gamesResult = findAvailableGame(nickname, true);
            if(gamesResult.isEmpty())
                game = null;
            else
                game = gamesResult.get(0);
        } else if (networkMessage.getRequestId().equals("RECGAME")) {
            game = games.get(gameId);
            game.addRecoveredPlayer(nickname);
            games.put(gameId, game);
        } else if (networkMessage.getRequestId().equals("NEWGAME")) {
            ArrayList<Game> gamesResult = findAvailableGame(nickname, true);
            if(gamesResult.isEmpty())
                game = null;
            else
                game = gamesResult.get(0);
        }
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
        games.put(game.getId(), game);
        System.out.println("New game created with id " + game.getId() + " for " + game.getPlayersNumber() + " players");
        return game;
    }

    /**
     * RMI method which handles the player setup
     * @param game
     * @param nickname
     * @return
     */
    public synchronized NetworkMessage RMIHandlePlayerSetup(Game game, String nickname, boolean isRecovered) throws RemoteException {
        if(!isRecovered){
            boolean seat = nickname.equals(game.getPlayers().get(0).getNickname());
            if (seat) {
                game.startGame();
                games.put(game.getId(), game);
            } else {
                if (games.get(game.getId()).isSetupFinished()) {
                    game = games.get(game.getId());
                    game.clearRecoveredPlayers();
                    games.put(game.getId(), game);
                }
                else
                    return null;
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
    public synchronized String RMICheckForStart(int gameId, boolean isRecovered) throws RemoteException {
        Game game = games.get(gameId);
        if(game != null) {
            if(!isRecovered){
                if (game.getPlayers().size() == game.getPlayersNumber()) {
                    return "startGame";
                }
            }else{
                if (game.getRecoveredPlayers().size() == game.getPlayersNumber()) {
                    return "startGame";
                }
            }
        }else {
            return "stopWaitingAndRepeat";
        }
        return "wait";
    }

    /**
     * RMI method which gets a specific game
     * @param gameId
     * @return
     * @throws RemoteException
     */
    public synchronized Game RMIGetGame(int gameId) throws RemoteException{
        return games.get(gameId);
    }

    /**
     * RMI method which gets results of a game turn
     * @param gameId
     * @param nickname
     * @return
     * @throws RemoteException
     */
    public synchronized ArrayList<NetworkMessage> RMIGetResult(int gameId, String nickname) throws RemoteException {
        ArrayList<NetworkMessage> result = new ArrayList<>();
        Game game = games.get(gameId);
        VirtualView virtualView = RMIVirtualViews.get(nickname);
        result.add(virtualView.updateBoard());
        result.add(virtualView.updateGameTokens());
        result.add(virtualView.updateResult());
        games.put(gameId, virtualView.getGame());
        return result;
    }

    /**
     * RMI method which gets game mutex status
     * @param gameId
     * @param playerIndex
     * @return
     */
    public synchronized boolean RMIGetMutexAtIndex(int gameId, int playerIndex) throws RemoteException{
        return games.get(gameId).getMutexAtIndex(playerIndex);
    }

    /**
     * This RMI method sets the specified player mutex to false
     * @param gameId
     * @param playerIndex
     * @throws RemoteException
     */
    public synchronized void RMISetMutexFalseAtIndex(int gameId, int playerIndex) throws RemoteException{
        Game game = games.get(gameId);
        game.setMutexFalseAtIndex(playerIndex);
        games.put(gameId, game);
    }

    /**
     * This RMI method makes the tiles move
     * @param networkMessage
     * @param nickname
     * @param gameId
     * @return
     * @throws RemoteException
     */
    public synchronized NetworkMessage RMIMoveTiles(NetworkMessage networkMessage, String nickname, int gameId) throws RemoteException{
        VirtualView virtualView = RMIVirtualViews.get(nickname);
        if (networkMessage.getRequestId().equals("MT")) {
            Game game = games.get(gameId);
            Player player = null;
            for(int i = 0; i < game.getPlayersNumber(); i++){
                if(game.getPlayers().get(i).getNickname().equals(nickname))
                    player = game.getPlayers().get(i);
            }
            NetworkMessage resp = virtualView.moveTiles(networkMessage, player);
            game = virtualView.getGame();
            RMIVirtualViews.put(nickname, virtualView);
            games.put(game.getId(), game);
            return resp;
        }
        return null;
    }

    /**
     * This RMI method checks for new messages to send from client
     * @param networkMessage
     * @param nickname
     * @param gameId
     * @throws RemoteException
     */
    public synchronized void RMISendMessage(NetworkMessage networkMessage, String nickname, int gameId) throws RemoteException {
        VirtualView view = new VirtualView(games.get(gameId), nickname);
        view.sendMessage(networkMessage);
        games.put(gameId, view.getGame());
    }

    /**
     * This RMI method continuously checks for new messages from the chat
     * @param nickname
     * @param gameId
     * @param numberMessages
     * @return
     * @throws RemoteException
     */
    public synchronized NetworkMessage RMICheckForChatUpdates(String nickname, int gameId, HashMap<Integer, Integer> numberMessages) throws RemoteException{
        Game game = games.get(gameId);
        ArrayList<Chat> chats = game.getChats();
        for(int i = 0; i < chats.size(); i++) {
            Chat chat = chats.get(i);
            if (!numberMessages.containsKey(chat.getId())) {
                numberMessages.put(chat.getId(), 0);
            }
            if(chat.getNameChatMembers().contains(nickname) && chat.getMessages().size() > numberMessages.get(chat.getId())){
                numberMessages.put(chat.getId(), chat.getMessages().size());
                Message m = chat.getLastMessage();
                NetworkMessage nm = new NetworkMessage();
                nm.setRequestId("UC");
                nm.addContent(m.getSender().getNickname());
                ArrayList<String> receivers = new ArrayList<>();
                for(String nick : chat.getNameChatMembers()){
                    if(!nick.equals(m.getSender().getNickname()))
                        receivers.add(nick);
                }
                nm.addContent(receivers);
                nm.addContent(m.getMessage());
                nm.addContent(numberMessages);
                return nm;
            }
        }
        return null;
    }

    /**
     * This RMI method check if the game ended or not for client internal checks
     * @param gameId
     * @return
     * @throws RemoteException
     */
    public synchronized int RMIIsGameFinished(int gameId) throws RemoteException {
        Game game = games.get(gameId);
        if(game.getState().equals(GameState.ENDED)){
            if(game.getCurrentPlayer() == null){
                return 1;
            }
            return 2;
        }
        return 0;
    }

    /**
     * This method searches for an available game
     * @return game
     */
    private static ArrayList<Game> findAvailableGame(String nickname, boolean newGame) {
        int maxId = -1;
        ArrayList<Game> gamesResult = new ArrayList<>();
        Object[] keySet = games.keySet().toArray();
        for(int i = 0; i < keySet.length; i++){
            if(maxId < (Integer)keySet[i]){
                maxId = (Integer)keySet[i];
            }
        }
        if(!newGame){
            for (int i = 0; i < maxId + 1; i++) {
                Game game = games.get(i);
                if (game != null) {
                    ArrayList<Player> players = game.getPlayers();
                    for (Player player : players) {
                        if (player.getNickname().equals(nickname)) {
                            gamesResult.add(game);
                        }
                    }
                }
            }
        }
        if(gamesResult.isEmpty()) {
            for (int i = 0; i < maxId + 1; i++) {
                Game game = games.get(i);
                if (game != null) {
                    if (game.getPlayers().size() < game.getPlayersNumber() && game.getState() == GameState.WAITING_FOR_PLAYERS) {
                        gamesResult.add(game);
                    }
                }
            }
        }
        return gamesResult;
    }

    /**
     * This method creates a new game with the specified players number
     * @param playersNumber
     * @return game
     */
    private static Game createNewGame(int playersNumber) {
        int id = getFirstAvailableId();
        return new Game(id, playersNumber);
    }

    /**
     * This runnable class is the real thread which handle the initial operations after the connection of the client
     */
    private static class ClientTCPHandler implements Runnable {
        private final Socket clientSocket;
        private final Socket chatClientSocket;

        public ClientTCPHandler(Socket socket, Socket chatSocket) {
            this.clientSocket = socket;
            this.chatClientSocket = chatSocket;
        }

        public void run() {
            String nickname = "";
            Game game = null;
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
                ArrayList<Game> gamesResult = findAvailableGame(nickname, false);
                if(gamesResult.isEmpty())
                    game = null;
                else
                    game = gamesResult.get(0);
                NetworkMessage syn = new NetworkMessage();
                syn.setRequestId("SYN");
                if (!gamesResult.isEmpty()) {
                    for(Game result : gamesResult){
                        if(result.getPlayers().size() == result.getPlayersNumber()){
                            syn.addContent(result);
                        }
                    }
                    if(!syn.getContent().isEmpty()) {
                        outputStream.writeObject(syn);
                        outputStream.flush();
                        NetworkMessage networkMessage;
                        do {
                            networkMessage = (NetworkMessage) inputStream.readObject();
                            int toRecoverGameId = (int) networkMessage.getContent().get(0);
                            if (networkMessage.getRequestId().equals("NEWGAME")) {
                                gamesResult = findAvailableGame(nickname, true);
                                if (gamesResult.isEmpty())
                                    game = null;
                                else
                                    game = gamesResult.get(0);
                            } else if (networkMessage.getRequestId().equals("DELGAME")) {
                                deleteGame(toRecoverGameId);
                                games.remove(toRecoverGameId);
                                /*gamesResult = findAvailableGame(nickname, true);
                                if (gamesResult.isEmpty())
                                    game = null;
                                else
                                    game = gamesResult.get(0);*/
                            } else {
                                game = games.get(toRecoverGameId);
                                game.addRecoveredPlayer(nickname);
                                games.put(game.getId(), game);
                            }
                        } while(networkMessage.getRequestId().equals("DELGAME"));
                    } else {
                        outputStream.writeObject(syn);
                        outputStream.flush();
                    }
                } else {
                    outputStream.writeObject(syn);
                    outputStream.flush();
                }

                boolean isRecovered;
                boolean repeat;

                do {
                    repeat = false;
                    if (game == null) {
                        outputStream.writeObject("newGame");
                        outputStream.flush();
                        int playersNumber = (Integer) inputStream.readObject();
                        game = createNewGame(playersNumber);
                        games.put(game.getId(), game);
                        System.out.println("New game created with id " + game.getId() + " for " + game.getPlayersNumber() + " players");
                        seat = true;
                    } else {
                        outputStream.writeObject("addedToGame");
                        outputStream.flush();
                    }
                    if (game.getRecoveredPlayers().size() == 0) {
                        Player player = new Player(seat, new Shelf(), nickname, game);
                        game.addPlayer(player);
                        System.out.println("Added " + nickname + " to game with id " + game.getId());
                    } else {
                        if (game.getPlayers().get(0).getNickname().equals(nickname)) {
                            seat = true;
                        }
                        System.out.println("Recovered " + nickname + " to game with id " + game.getId());
                    }
                    outputStream.writeObject(game.getId());
                    outputStream.flush();

                    isRecovered = game.getRecoveredPlayers().size() > 0;
                    while (true) {
                        this.clientSocket.setKeepAlive(true);
                        if (!isRecovered) {
                            if (game.getPlayers().size() == game.getPlayersNumber()) {
                                game.setState(GameState.STARTED);
                                if (seat) {
                                    System.out.println("Game with id " + game.getId() + " started!");
                                }
                                outputStream.writeObject("startGame");
                                outputStream.flush();
                                break;
                            }
                        } else {
                            game = games.get(game.getId());
                            if (game != null) {
                                if (game.getPlayers().size() == game.getRecoveredPlayers().size()) {
                                    game.setState(GameState.STARTED);
                                    if (seat) {
                                        System.out.println("Game with id " + game.getId() + " recovered!");
                                    }
                                    outputStream.writeObject("startGame");
                                    outputStream.flush();
                                    break;
                                }
                            } else {
                                gamesResult = findAvailableGame(nickname, false);
                                if(gamesResult.isEmpty())
                                    game = null;
                                else
                                    game = gamesResult.get(0);
                                isRecovered = false;
                                repeat = true;
                                outputStream.writeObject("stopWaitingAndRepeat");
                                break;
                            }
                        }
                    }
                } while (repeat);

                int playerIndex = 0;
                for (int i=0; i<game.getPlayers().size(); i++){
                    if(game.getPlayers().get(i).getNickname().equals(nickname))
                        playerIndex = i;
                }

                ChatClientTCPHandler chat = new ChatClientTCPHandler(chatClientSocket, nickname, game.getId());
                new Thread(chat).start();

                game = games.get(game.getId());

                if (!isRecovered) {
                    if (seat) {
                        game.startGame();
                        games.put(game.getId(), game);
                    } else {
                        while (true) {
                            this.clientSocket.setKeepAlive(true);
                            if (games.get(game.getId()).isSetupFinished()) {
                                game = games.get(game.getId());
                                game.clearRecoveredPlayers();
                                games.put(game.getId(), game);
                                break;
                            }
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
                boolean updateSend = true;
                while (true) {
                    this.clientSocket.setKeepAlive(true);
                    if(games.get(game.getId()).getState() == GameState.STARTED && games.get(game.getId()).getCurrentPlayer() != null) {
                        if(games.get(game.getId()).getCurrentPlayer().getNickname().equals(nickname) && updateSend) {
                            NetworkMessage netMessage = (NetworkMessage) inputStream.readObject();
                            if (netMessage.getRequestId().equals("MT")) {
                                virtualView = new VirtualView(games.get(game.getId()), game.getCurrentPlayer().getNickname());
                                NetworkMessage resp = virtualView.moveTiles(netMessage, game.getCurrentPlayer());
                                game = virtualView.getGame();
                                outputStream.reset();
                                outputStream.writeObject(resp);
                                games.put(game.getId(), game);
                            }
                        }
                        updateSend = false;
                        ArrayList<NetworkMessage> result = new ArrayList<>();
                        if (games.get(game.getId()).getState() == GameState.STARTED && games.get(game.getId()).getMutexAtIndex(playerIndex) && game.getCurrentPlayer() != null) {
                            virtualView = new VirtualView(games.get(game.getId()), game.getCurrentPlayer().getNickname());
                            result.add(virtualView.updateBoard());
                            result.add(virtualView.updateGameTokens());
                            result.add(virtualView.updateResult());
                            outputStream.reset();
                            outputStream.writeObject(result);
                            game.setMutexFalseAtIndex(playerIndex);
                            games.put(game.getId(), game);
                            updateSend = true;
                        }
                    } else {
                        ArrayList<NetworkMessage> result = new ArrayList<>();
                        if (games.get(game.getId()).getState() == GameState.ENDED) {
                            virtualView = new VirtualView(games.get(game.getId()), null);
                            result.add(virtualView.updateBoard());
                            result.add(virtualView.updateGameTokens());
                            result.add(virtualView.updateResult());
                            outputStream.reset();
                            outputStream.writeObject(result);
                            game.setMutexFalseAtIndex(playerIndex);
                            games.put(game.getId(), game);
                        }
                        NetworkMessage endMessage = new NetworkMessage();
                        if(games.get(game.getId()).getCurrentPlayer() != null){
                            endMessage.setRequestId("ER");
                            endMessage.setTextMessage("A player left the game. Game end here.");
                        }else{
                            endMessage.setRequestId("ER");
                            endMessage.setTextMessage("Game ended! Thank you for playing!");
                        }
                        deleteGame(game.getId());
                        outputStream.writeObject(endMessage);
                    }
                }
            } catch (EOFException e) {
                if(!nickname.isEmpty()){
                    System.err.println("User " + nickname + " left the server.");
                }else{
                    System.err.println("User from " + clientSocket.getInetAddress().getHostAddress() + " left the server.");
                }
                if(game != null && game.getId() != -1){
                    System.err.println("Game with id " + game.getId() + " has been terminated.");
                    games.get(game.getId()).setState(GameState.ENDED);
                    //deleteGame(game.getId());
                }
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

    /**
     * This runnable class handles the connection to the chat checking for messages to send and to receive
     */
    private static class ChatClientTCPHandler implements Runnable {
        private final Socket clientSocket;
        private final String nickname;
        private final int gameId;

        public ChatClientTCPHandler(Socket socket, String nickname, int gameId) {
            this.clientSocket = socket;
            this.nickname = nickname;
            this.gameId = gameId;
        }

        public void run() {
            ObjectOutputStream outputStream = null;
            ObjectInputStream inputStream = null;
            try {
                outputStream = new ObjectOutputStream(this.clientSocket.getOutputStream());
                inputStream = new ObjectInputStream(this.clientSocket.getInputStream());
                ObjectInputStream finalInputStream = inputStream;
                new Thread(() -> {
                    while(true){
                        try {
                            clientSocket.setKeepAlive(true);
                            NetworkMessage nm = (NetworkMessage) finalInputStream.readObject();
                            VirtualView view = new VirtualView(games.get(gameId), nickname);
                            view.sendMessage(nm);
                            games.put(gameId, view.getGame());
                        } catch (IOException | ClassNotFoundException e) {
                            System.err.println("User " + nickname + " left the chat!");
                            break;
                        }
                    }
                }).start();
                ObjectOutputStream finalOutputStream = outputStream;
                new Thread(() -> {
                    HashMap<Integer, Integer> numberMessages = new HashMap<>();
                    Game game = games.get(gameId);
                    ArrayList<Chat> chats = game.getChats();
                    if (chats.size() > 0) {
                        for (Chat chat : chats) {
                            numberMessages.put(chat.getId(), chat.getMessages().size());
                        }
                    }
                    while(true){
                        try {
                            clientSocket.setKeepAlive(true);
                            game = games.get(gameId);
                            chats = game.getChats();
                            for(int i = 0; i < chats.size(); i++) {
                                Chat chat = chats.get(i);
                                if(!numberMessages.containsKey(chat.getId())){
                                    numberMessages.put(chat.getId(), 0);
                                }
                                if(chat.getNameChatMembers().contains(nickname) && chat.getMessages().size() > numberMessages.get(chat.getId())){
                                    numberMessages.put(chat.getId(), chat.getMessages().size());
                                    Message m = chat.getLastMessage();
                                    NetworkMessage nm = new NetworkMessage();
                                    nm.setRequestId("UC");
                                    nm.addContent(m.getSender().getNickname());
                                    ArrayList<String> receivers = new ArrayList<>();
                                    if(chat.getId() == 0)
                                        receivers.addAll(chat.getNameChatMembers());
                                    else {
                                        for (String nick : chat.getNameChatMembers()) {
                                            if (!nick.equals(m.getSender().getNickname()))
                                                receivers.add(nick);
                                        }
                                    }
                                    nm.addContent(receivers);
                                    nm.addContent(m.getMessage());
                                    finalOutputStream.writeObject(nm);
                                }
                            }
                        } catch (IOException e) {
                            System.err.println("User " + nickname + " left the chat!");
                            break;
                        }
                    }
                }).start();
            } catch (IOException e) {
            }
        }

    }
}