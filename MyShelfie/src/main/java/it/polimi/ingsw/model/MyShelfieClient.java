package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.ClientController;
import it.polimi.ingsw.utils.NetworkMessage;
import it.polimi.ingsw.view.CLI.CLIView;
import it.polimi.ingsw.view.ClientViewObservable;
import it.polimi.ingsw.view.GUI.GUIView;
import it.polimi.ingsw.view.GUI.MyShelfieFX;
import it.polimi.ingsw.view.GUI.SceneController;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * Client class for RMI and TCP connection
 */
public class MyShelfieClient {

    static Socket socket;
    static Socket chatSocket;
    static MyShelfieRMIInterface RMIServer;
    static MyShelfieRMIInterface RMIChatServer;
    static ObjectOutputStream outputStream;
    static ObjectInputStream inputStream;
    static ObjectOutputStream chatOutput;
    static ObjectInputStream chatInput;
    static int protocol;
    static String remoteNickname;
    static int gameId;
    static ClientViewObservable view;
    static ClientController controller;
    private final Object lock = new Object();
    private final Object inputLock = new Object();
    static GUIView guiView;
    private static int interfaceChosen;
    private static boolean isRecovered;

    /**
     * Main method: Asks to the user to choose between TCP and RMI connection and establishes the connection with the chosen protocol.
     *
     * @param args
     * @throws IOException
     */
    public static void main(String args[]) throws IOException {
        System.out.println("Welcome to MyShelfie!");
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("Press 1 to use the CLI or 2 to use the GUI:");
            interfaceChosen = Integer.parseInt(sc.nextLine());
            if (interfaceChosen == 2) {
                MyShelfieFX.main(null);
            } else if (interfaceChosen == 1) {
                break;
            }
        }
        while (true) {
            System.out.println("Choose your connection protocol:\n1. TCP\n2. RMI\n3. Quit\n");
            try {
                protocol = Integer.parseInt(sc.nextLine());
                if (protocol >= 1 && protocol <= 3) {
                    break;
                }
            } catch (NumberFormatException e) {
                System.err.println("Please select a valid option!");
            }
        }
        String serverAddress = "";
        while (true) {
            System.out.println("Server address and port (hostname:port): ");
            serverAddress = sc.nextLine();
            if (serverAddress.contains(":")) {
                break;
            } else {
                System.err.println("Wrong server address! (hostname:port)");
            }
        }
        String hostname = serverAddress.substring(0, serverAddress.indexOf(':'));
        int port = Integer.parseInt(serverAddress.substring(serverAddress.indexOf(':') + 1));
        if (connect(hostname, port, protocol)) {
            if (protocol == 1) {
                try {
                    String nickname;
                    String serverAnswer;
                    do {
                        System.out.println("Enter your nickname: ");
                        nickname = sc.nextLine();
                        serverAnswer = checkNickname(nickname, protocol);
                        if (serverAddress.equals("nicknameExists")) {
                            System.err.println("User with nickname '" + nickname + "' already exists. Choose another nickname.");
                        } else if (serverAddress.equals("nicknameWrong")) {
                            System.err.println("Wrong nickname pattern! You nickname cannot contain spaces and must have between 3 and 15 characters!");
                        }
                    } while (!serverAnswer.equals("nicknameOk"));
                    remoteNickname = nickname;
                    ArrayList<Object> recoverableGames = TCPCheckForRecoverableGames();
                    String input;
                    if (!recoverableGames.isEmpty()){
                        int integerInput = 0;
                        goBack: while (true) {
                            ArrayList<Integer> recoverableIds = new ArrayList<>();
                            Game recoverable = null;
                            System.out.println("\nOne or more recoverable games have been found:" + recoverableGames.size());
                            for (int i = 0; i < recoverableGames.size(); i++) {
                                recoverable = (Game) recoverableGames.get(i);
                                recoverableIds.add(recoverable.getId());
                                System.out.print("\nId:" + recoverable.getId() + " -> with players: ");
                                for (int j = 0; j < recoverable.getPlayersNumber(); j++) {
                                    System.out.print(recoverable.getPlayers().get(j).getNickname());
                                    if (j < recoverable.getPlayersNumber() - 1) {
                                        System.out.print(", ");
                                    }
                                }
                            }
                            int handledGameId = -1;
                            do {
                                System.out.println("\nPlease type the id of the game you want to handle or type 'newGame' to search for a new game: ");
                                input = sc.nextLine();
                                if (!input.equals("newGame")) {
                                    try {
                                        handledGameId = Integer.parseInt(input);
                                        if (!recoverableIds.contains(handledGameId)) {
                                            System.out.println("Please type a valid id!");
                                        }
                                    } catch (NumberFormatException e) {
                                        System.out.println("Please type a valid id!");
                                        handledGameId = -1;
                                    }
                                } else {
                                    TCPDoWantToRecover("NEWGAME", handledGameId);
                                    break goBack;
                                }
                            } while (!recoverableIds.contains(handledGameId));
                            if (!input.equals("newGame")) {
                                Game handledGame = null;
                                for (int i = 0; i < recoverableIds.size(); i++) {
                                    handledGame = (Game) recoverableGames.get(i);
                                    if (handledGame.getId() == handledGameId) {
                                        break;
                                    }
                                }
                                do {
                                    System.out.println("1. Restore");
                                    if (handledGame.getPlayers().get(0).getNickname().equals(nickname)) {
                                        System.out.println("2. Delete");
                                    }
                                    System.out.println("3. Go back");
                                    input = sc.nextLine();
                                    integerInput = Integer.parseInt(input);
                                    if (integerInput != 1 && integerInput != 2 && integerInput != 3) {
                                        System.out.println("Please insert a valid answer!");
                                    }
                                } while (integerInput != 1 && integerInput != 2 && integerInput != 3);
                                if (integerInput == 1) {
                                    TCPDoWantToRecover("RECGAME", handledGameId);
                                    System.out.println("Recovering game with id " + handledGameId + "...");
                                    break;
                                } else if (integerInput == 2) {
                                    TCPDoWantToRecover("DELGAME", handledGameId);
                                    recoverableGames.remove(recoverableIds.indexOf(handledGameId));
                                    recoverableIds.remove(handledGameId);
                                    System.out.println("Game with id " + handledGameId + " deleted!");
                                    //break;
                                }
                            }
                        }/* while(integerInput != 1)*/
                    }
                    boolean repeat;
                    do {
                        repeat = false;
                        String command = TCPCheckForAvailableGames();
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
                            TCPSetPlayersNumber(playersNumber);
                        }
                        gameId = TCPGetGameId();
                        System.out.println("Hi " + nickname + "!\nYou have been added to game with id " + gameId + "\nYour game will start when the players number is fulfilled");

                        while (true) {
                            command = TCPCheckForGameStart();
                            if (command.equals("startGame")) {
                                System.out.println("GAME STARTED!");
                                String finalNickname = nickname;
                                new MyShelfieClient().handleGameTCP(nickname);
                                break;
                            }else if(command.equals("stopWaitingAndRepeat")){
                                repeat = true;
                                break;
                            }
                        }
                    } while (repeat);
                } catch (SocketTimeoutException e) {
                    System.err.println("Socket timed out");
                } catch (StringIndexOutOfBoundsException | EOFException e) {
                    System.err.println("Server went offline!");
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                } finally {
                    socket.close();
                }
            } else if (protocol == 2) {
                try {
                    String nickname = "";
                    String serverAnswer = "";
                    do {
                        System.out.println("Enter your nickname: ");
                        nickname = sc.nextLine();
                        serverAnswer = checkNickname(nickname, protocol);
                        if (serverAnswer.equals("nicknameExists")) {
                            System.err.println("User with nickname '" + nickname + "' already exists. Choose another nickname.");
                        } else if (serverAnswer.equals("nicknameWrong")) {
                            System.err.println("Wrong nickname pattern! You nickname cannot contain spaces and must have between 3 and 15 characters!");
                        }
                    } while (!serverAnswer.equals("nicknameOk"));
                    remoteNickname = nickname;
                    ArrayList<Object> recoverableGames = RMICheckForAvailableGame();
                    String input;
                    Game game = null;
                    if(!recoverableGames.isEmpty()){
                        game = (Game) recoverableGames.get(0);
                    }
                    if (game != null && game.getPlayers().size() == game.getPlayersNumber()){
                        int integerInput = 0;
                        goBack: while (true) {
                            ArrayList<Integer> recoverableIds = new ArrayList<>();
                            System.out.println("\nOne or more recoverable games have been found:" + recoverableGames.size());
                            for (int i = 0; i < recoverableGames.size(); i++) {
                                Game recoverable = (Game) recoverableGames.get(i);
                                recoverableIds.add(recoverable.getId());
                                System.out.print("\nId:" + recoverable.getId() + " -> with players: ");
                                for (int j = 0; j < recoverable.getPlayersNumber(); j++) {
                                    System.out.print(recoverable.getPlayers().get(j).getNickname());
                                    if (j < recoverable.getPlayersNumber() - 1) {
                                        System.out.print(", ");
                                    }
                                }
                            }
                            int handledGameId = -1;
                            do {
                                System.out.println("\nPlease type the id of the game you want to handle or type 'newGame' to search for a new game: ");
                                input = sc.nextLine();
                                if (!input.equals("newGame")) {
                                    try {
                                        handledGameId = Integer.parseInt(input);
                                        if (!recoverableIds.contains(handledGameId)) {
                                            System.out.println("Please type a valid id!");
                                        }
                                    } catch (NumberFormatException e) {
                                        System.out.println("Please type a valid id!");
                                        handledGameId = -1;
                                    }
                                } else {
                                    game = RMIDoWantToRecover("NEWGAME", handledGameId);
                                    break goBack;
                                }
                            } while (!recoverableIds.contains(handledGameId));
                            if (!input.equals("newGame")) {
                                Game handledGame = null;
                                for (int i = 0; i < recoverableIds.size(); i++) {
                                    handledGame = (Game) recoverableGames.get(i);
                                    if (handledGame.getId() == handledGameId) {
                                        break;
                                    }
                                }
                                do {
                                    System.out.println("1. Restore");
                                    if (handledGame.getPlayers().get(0).getNickname().equals(nickname)) {
                                        System.out.println("2. Delete");
                                    }
                                    System.out.println("3. Go back");
                                    input = sc.nextLine();
                                    integerInput = Integer.parseInt(input);
                                    if (integerInput != 1 && integerInput != 2 && integerInput != 3) {
                                        System.out.println("Please insert a valid answer!");
                                    }
                                } while (integerInput != 1 && integerInput != 2 && integerInput != 3);
                                if (integerInput == 1) {
                                    game = RMIDoWantToRecover("RECGAME", handledGameId);
                                    System.out.println("Recovering game with id " + handledGameId + "...");
                                    break;
                                } else if (integerInput == 2) {
                                    game = RMIDoWantToRecover("DELGAME", handledGameId);
                                    recoverableGames.remove(recoverableIds.indexOf(handledGameId));
                                    recoverableIds.remove(handledGameId);
                                    System.out.println("Game with id " + handledGameId + " deleted!");
                                }
                            }
                        }
                    }
                    boolean repeat;
                    boolean seat = false;
                    isRecovered = false;
                    do {
                        repeat = false;
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
                            game = RMIHandleGameCreation(playersNumber);
                            seat = true;
                        }else if(game.getPlayers().size() != game.getPlayersNumber()){
                            gameId = game.getId();
                            game = RMISetPlayer(gameId);
                        }else{
                            isRecovered = true;
                        }
                        gameId = game.getId();
                        System.out.println("Hi " + nickname + "!\nYou have been added to game with id " + game.getId() + "\nYour game will start when the players number is fulfilled");
                        String command;
                        while (true) {
                            command = RMICheckForGameStart(gameId);
                            if (command.equals("startGame")) {
                                game = RMIGetGame(game.getId());
                                System.out.println("GAME STARTED!");
                                new MyShelfieClient().handleGameRMI(game, nickname);
                                break;
                            }else if(command.equals("stopWaitingAndRepeat")){
                                repeat = true;
                                recoverableGames = RMICheckForAvailableGame();
                                if(recoverableGames.isEmpty()){
                                    game = null;
                                }else{
                                    game = (Game) recoverableGames.get(0);
                                }
                                break;
                            }
                        }
                    } while (repeat);
                } catch (Exception e) {
                    System.err.println("Connection lost! :(");
                }
            }
        } else {
            System.err.println("Connection failed! :(");
        }
    }

    /**
     * Method which handles the connection for both Socket and RMI
     *
     * @param hostname
     * @param port
     * @param connectionProtocol
     * @return
     */
    static public boolean connect(String hostname, int port, int connectionProtocol) {
        if (connectionProtocol == 1) {
            System.out.println("Connecting to TCP server...");
            protocol = 1;
            try {
                socket = new Socket(hostname, port);
                chatSocket = new Socket(hostname, port + 1);
                if (chatSocket.isConnected()) {
                    chatSocket.setKeepAlive(true);
                    chatOutput = new ObjectOutputStream(chatSocket.getOutputStream());
                    chatOutput.flush();
                    //chatOutput.writeObject("chatOk");
                    //chatOutput.close();
                }
                if (socket.isConnected()) {
                    System.out.println("Connected! :)");
                    socket.setKeepAlive(true);
                    outputStream = new ObjectOutputStream(socket.getOutputStream());
                    inputStream = new ObjectInputStream(socket.getInputStream());
                    return true;
                } else {
                    return false;
                }
            } catch (IOException e) {
                return false;
            }
        } else if (connectionProtocol == 2) {
            protocol = 2;
            try {
                System.out.println("Connecting to RMI server...");
                RMIServer = (MyShelfieRMIInterface) Naming.lookup("rmi://" + hostname + ":" + port + "/Server");
                RMIChatServer = (MyShelfieRMIInterface) Naming.lookup("rmi://" + hostname + ":" + (port + 1) + "/chatServer");
                System.out.println("Connected! :)");
                return true;
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }

    /**
     * Method which handles nickname check communication
     *
     * @param nickname
     * @param protocol
     * @return nicknameExists if nickname already exists in the server
     * nicknameWrong if nickname has a wrong pattern
     * nicknameOk if nickname is valid
     * @throws IOException
     * @throws ClassNotFoundException
     */
    static public String checkNickname(String nickname, int protocol) throws IOException, ClassNotFoundException {
        if (interfaceChosen == 2)
            guiView = new GUIView((nickname));
        if (protocol == 1) {
            outputStream.writeObject(nickname);
            outputStream.flush();
            return (String) inputStream.readObject();
        } else {
            return RMIServer.RMICheckNickname(nickname);
        }
    }

    /**
     * Method which checks if a recoverable game exists (TCP)
     *
     * @return recoverableGameFound if a recoverable game exists
     * noRecoverableGameFound vice-versa
     * @throws IOException
     * @throws ClassNotFoundException
     */
    static public ArrayList<Object> TCPCheckForRecoverableGames() throws IOException, ClassNotFoundException {
        NetworkMessage networkMessage = (NetworkMessage) inputStream.readObject();
        return networkMessage.getContent();
    }

    /**
     * This method handle the game saves options
     * @param operation can be NEWGAME to search for a new game, DELGAME to delete the game with id specified in handledGameId, RECGAME to recover the game with id specified in handledGameId
     * @param handledGameId
     * @throws IOException
     * @throws ClassNotFoundException
     */
    static public void TCPDoWantToRecover(String operation, int handledGameId) throws IOException, ClassNotFoundException {
        NetworkMessage networkMessage = new NetworkMessage();
        networkMessage.setRequestId(operation);
        networkMessage.addContent(handledGameId);
        outputStream.writeObject(networkMessage);
    }

    /**
     * Method which checks if a game is available (TCP)
     *
     * @return newGame if no game exists
     * addedToGame if there is an existing game available to join
     * @throws IOException
     * @throws ClassNotFoundException
     */
    static public String TCPCheckForAvailableGames() throws IOException, ClassNotFoundException {
        return (String) inputStream.readObject();
    }

    /**
     * Setter method for remoteNickname
     *
     * @param nickname
     */
    static public void setRemoteNickname(String nickname) {
        remoteNickname = nickname;
    }

    /**
     * Method which checks if a game is available (RMI)
     *
     * @return the Game where the player has been added to or null if there was no available game to join
     * @throws RemoteException
     */
    static public ArrayList<Object> RMICheckForAvailableGame() throws RemoteException {
        return RMIServer.RMICheckforAvailableGame(remoteNickname).getContent();
    }

    static public Game RMIDoWantToRecover(String operation, int handledGameId) throws RemoteException {
        NetworkMessage networkMessage = new NetworkMessage();
        networkMessage.setRequestId(operation);
        networkMessage.addContent(handledGameId);
        networkMessage.addContent(remoteNickname);
        return RMIServer.RMIDoWantToRecover(networkMessage);
    }

    static public Game RMISetPlayer(int gameId) throws RemoteException {
        return RMIServer.RMISetPlayer(gameId, remoteNickname);
    }

    /**
     * Method which sets the isRecovered variable
     * @param value
     * @throws RemoteException
     */
    static public void RMISetIsRecovered(boolean value) throws RemoteException {
        isRecovered = value;
    }

    /**
     * Method which sets the player number (TCP)
     *
     * @param playersNumber
     * @throws IOException
     */
    static public void TCPSetPlayersNumber(int playersNumber) throws IOException {
        outputStream.writeObject(playersNumber);
        outputStream.flush();
    }

    /**
     * Method which handles the entire game creation (RMI)
     *
     * @param playersNumber
     * @return
     * @throws RemoteException
     */
    static public Game RMIHandleGameCreation(int playersNumber) throws RemoteException {
        return RMIServer.RMIHandleGameCreation(playersNumber, remoteNickname);
    }

    /**
     * Method which gets the game id (TCP)
     *
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    static public int TCPGetGameId() throws IOException, ClassNotFoundException {
        return (Integer) inputStream.readObject();
    }

    /**
     * Method which checks for the start game command (TCP)
     *
     * @return startGame if the game started
     * @throws IOException
     * @throws ClassNotFoundException
     */
    static public String TCPCheckForGameStart() throws IOException, ClassNotFoundException {
        return (String) inputStream.readObject();
    }

    /**
     * Method which checks for the start game command (RMI)
     *
     * @param gameId
     * @return true if the game started
     * @throws RemoteException
     */
    static public String RMICheckForGameStart(int gameId) throws RemoteException {
        return RMIServer.RMICheckForStart(gameId, isRecovered);
    }

    /**
     * Getter for RMI game
     *
     * @param gameId
     * @return the updated game
     * @throws RemoteException
     */
    static public Game RMIGetGame(int gameId) throws RemoteException {
        return RMIServer.RMIGetGame(gameId);
    }

    /**
     * This methods handles the Game with TCP connection
     *
     * @param nickname
     */
    public void handleGameTCP(String nickname) {
        if (interfaceChosen == 2)
            view = new ClientViewObservable(guiView);
        else
            view = new ClientViewObservable(new CLIView(nickname));
        controller = new ClientController(view, this, nickname);
        view.setClientController(controller);
        try {
            chatInput = new ObjectInputStream(chatSocket.getInputStream());
            new Thread(() -> handleChatTCP(nickname, chatSocket)).start();
            if (interfaceChosen == 2)
                SceneController.createMainStage("MainStage.fxml");
            NetworkMessage nm = (NetworkMessage) inputStream.readObject();
            if(interfaceChosen == 1) {
                new Thread(() -> {
                    String textFromUser;
                    while(true){
                        Scanner scanner = new Scanner(System.in);
                        synchronized (inputLock){
                            try {
                                inputLock.wait();
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        System.out.println("To quit insert the command: \u001B[1m/quitGame");

                        textFromUser = scanner.nextLine();
                        if(textFromUser.equals("/quitGame"))
                            break;
                        else
                            controller.enableInput();
                    }
                    //user left the game
                }).start();
            }
            controller.playerSetup(nm);
            NetworkMessage currentPlayer = (NetworkMessage) inputStream.readObject();
            synchronized (inputLock) {
                controller.updateResults(currentPlayer);
                inputLock.notifyAll();
            }
            /*if (interfaceChosen == 1 && currentPlayer.getContent().get(0).equals(nickname)) {
                controller.enableInput();
            }*/
            ArrayList<NetworkMessage> result = null;
            while (true) {
                if ((currentPlayer.getContent().get(0).equals(nickname) && result == null) || (result != null && result.get(2).getContent().get(0).equals(nickname))) {
                    synchronized (lock) {
                        lock.wait();
                        TimeUnit.MILLISECONDS.sleep(100);
                    }
                }
                result = (ArrayList<NetworkMessage>) inputStream.readObject();
                NetworkMessage board = result.get(0);
                if (board.getRequestId().equals("ER")) {
                    System.err.println("\n" + board.getTextMessage());
                    break;
                } else if (board.getRequestId().equals("UC")) {
                    controller.updateChat(board);
                } else if (board.getRequestId().equals("UB")) {
                    controller.updateBoard(board);
                    NetworkMessage token = result.get(1);
                    controller.updateGameTokens(token);
                    NetworkMessage res = result.get(2);
                    synchronized (inputLock) {
                        controller.updateResults(res);
                    inputLock.notifyAll();
                    }
                    if(res.getTextMessage().equals("END"))
                        break;
                }
            }
        } catch (IOException ex) {
            System.err.println("Connection lost!");
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Chat handler for receiving messages (TCP)
     * @param nickname
     * @param chatSocket
     */
    public void handleChatTCP(String nickname, Socket chatSocket) {
        try {
            while (true) {
                NetworkMessage nm = (NetworkMessage) chatInput.readObject();
                if(nm.getRequestId().equals("UC")){
                    controller.updateChat(nm);
                }
            }
        } catch (IOException e){
            System.err.println("Chat connection lost!");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Chat handler for receiving messages (RMI)
     * @param nickname
     * @param server
     */
    public void handleChatRMI(String nickname, MyShelfieRMIInterface server){
        HashMap<Integer, Integer> numberMessages = new HashMap<>();
        while(true){
            try {
                NetworkMessage answer = server.RMICheckForChatUpdates(nickname, gameId, numberMessages);
                if(answer != null) {
                    if(answer.getRequestId().equals("UC")){
                        numberMessages = (HashMap<Integer, Integer>) answer.getContent().get(3);
                        controller.updateChat(answer);
                    }
                }
            } catch (RemoteException e) {
                System.err.println("Chat connection lost!");
                break;
            }
        }
    }

    /**
     * This method handles the game with RMI connection
     *
     * @param game
     * @param nickname
     */
    public void handleGameRMI(Game game, String nickname) {
        gameId = game.getId();
        if (interfaceChosen == 2)
            view = new ClientViewObservable(guiView);
        else
            view = new ClientViewObservable(new CLIView(nickname));
        controller = new ClientController(view, this, nickname);
        view.setClientController(controller);
        try {
            new Thread(() -> handleChatRMI(nickname, RMIChatServer)).start();
            if (interfaceChosen == 2)
                SceneController.createMainStage("MainStage.fxml");
            NetworkMessage nm;
            do {
                nm = RMIServer.RMIHandlePlayerSetup(game, nickname, isRecovered);
            }while (nm == null);
            game = (Game) nm.getContent().get(nm.getContent().size() - 1);
            if(interfaceChosen == 1) {
                new Thread(() -> {
                    String textFromUser;
                    while(true){
                        Scanner scanner = new Scanner(System.in);
                        synchronized (inputLock){
                            try {
                                inputLock.wait();
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        System.out.println("To quit insert the command: \u001B[1m/quitGame");

                        textFromUser = scanner.nextLine();
                        if(textFromUser.equals("/quitGame"))
                            break;
                        else
                            controller.enableInput();
                    }
                    //user left the game
                }).start();
            }
            controller.playerSetup(nm);
            NetworkMessage currentPlayer = RMIServer.RMIGetFirstPlayer(nickname);
            synchronized (inputLock) {
                controller.updateResults(currentPlayer);
                inputLock.notifyAll();
            }
            ArrayList<NetworkMessage> result = null;
            int playerIndex = -1;
            for (int i = 0; i < game.getPlayers().size(); i++) {
                if (game.getPlayers().get(i).getNickname().equals(nickname))
                    playerIndex = i;
            }

            while (true) {
                game = RMIServer.RMIGetGame(game.getId());
                int isFinished = RMIServer.RMIIsGameFinished(gameId);
                if(isFinished == 0) {
                    if ((currentPlayer.getContent().get(0).equals(nickname) && result == null) || (result != null && result.get(2).getContent().get(0).equals(nickname))) {
                        synchronized (lock) {
                            lock.wait();
                            TimeUnit.MILLISECONDS.sleep(100);
                        }
                    }
                    if (RMIServer.RMIGetMutexAtIndex(game.getId(), playerIndex)) {
                        result = RMIServer.RMIGetResult(game.getId(), nickname);
                        game = RMIServer.RMIGetGame(game.getId());
                        NetworkMessage board = result.get(0);
                        NetworkMessage token = result.get(1);
                        NetworkMessage res = result.get(2);
                        if (board.getRequestId().equals("ER")) {
                            System.err.println("\n" + board.getTextMessage());
                            break;
                        }
                        controller.updateBoard(board);
                        controller.updateGameTokens(token);
                        synchronized (inputLock) {
                            if(!res.getRequestId().equals("END"))
                                controller.updateResults(res);
                            inputLock.notifyAll();
                        }
                        RMIServer.RMISetMutexFalseAtIndex(gameId, playerIndex);
                    }
                }else if (isFinished == 1) {
                    //if (RMIServer.RMIGetMutexAtIndex(game.getId(), playerIndex)) {
                    result = RMIServer.RMIGetResult(game.getId(), nickname);
                    game = RMIServer.RMIGetGame(game.getId());
                    NetworkMessage board = result.get(0);
                    NetworkMessage token = result.get(1);
                    NetworkMessage res = result.get(2);
                    if (board.getRequestId().equals("ER")) {
                        System.err.println("\n" + board.getTextMessage());
                        break;
                    }
                    controller.updateBoard(board);
                    controller.updateGameTokens(token);
                    controller.updateResults(res);
                    RMIServer.RMISetMutexFalseAtIndex(gameId, playerIndex);
                    //}
                    System.out.println("Game ended! Thank you for playing!");
                    break;
                } else if (isFinished == 2) {
                    System.err.println("A player left the game. Game end here.");
                    break;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
            //System.err.println("\nConnection lost!");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This methods handles the communication between controller and server by client
     *
     * @param networkMessage
     * @return
     */
    public NetworkMessage sendMessage(NetworkMessage networkMessage) {
        if (protocol == 1) {
            try {
                socket.setKeepAlive(true);
                chatSocket.setKeepAlive(true);
                if (networkMessage.getRequestId().equals("MT")) {
                    outputStream.flush();
                    outputStream.writeObject(networkMessage);
                    NetworkMessage result;
                    synchronized (lock) {
                        result = (NetworkMessage) inputStream.readObject();
                        lock.notifyAll();
                    }
                    return result;
                } else if (networkMessage.getRequestId().equals("SM")) {
                    chatOutput.flush();
                    chatOutput.writeObject(networkMessage);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            return null;
        } else {
            if (networkMessage.getRequestId().equals("MT")) {
                try {
                    NetworkMessage tilesMoved = new NetworkMessage();
                    synchronized (lock) {
                        tilesMoved = RMIServer.RMIMoveTiles(networkMessage, remoteNickname, gameId);
                        lock.notifyAll();
                        return tilesMoved;
                    }
                } catch (RemoteException e) {
                    return null;
                }
            } else if (networkMessage.getRequestId().equals("SM")) {
                try {
                    RMIServer.RMISendMessage(networkMessage, remoteNickname, gameId);
                } catch (RemoteException e) {
                    return null;
                }
            }
        }
        return null;
    }

    public static GUIView getGuiView(){
        return guiView;
    }

    public static int getInterfaceChosen(){
        return interfaceChosen;
    }

}

/**
 * RMI interface for server methods
 */
interface MyShelfieRMIInterface extends Remote {
    NetworkMessage RMICheckforAvailableGame(String message) throws RemoteException;

    String RMICheckNickname(String nickname) throws RemoteException;

    Game RMIHandleGameCreation(int playersNumber, String nickname) throws RemoteException;

    String RMICheckForStart(int gameId, boolean isRecovered) throws RemoteException;

    Game RMIGetGame(int gameId) throws RemoteException;

    NetworkMessage RMIHandlePlayerSetup(Game game, String nickname, boolean isRecovered) throws RemoteException;

    NetworkMessage RMIGetFirstPlayer(String nickname) throws RemoteException;

    ArrayList<NetworkMessage> RMIGetResult(int gameId, String nickname) throws RemoteException;

    boolean RMIGetMutexAtIndex(int gameId, int playerIndex) throws RemoteException;

    void RMISetMutexFalseAtIndex(int gameId, int playerIndex) throws RemoteException;

    NetworkMessage RMIMoveTiles(NetworkMessage networkMessage, String nickname, int gameId) throws RemoteException;

    void RMISendMessage(NetworkMessage networkMessage, String nickname, int gameId) throws RemoteException;

    int RMIIsGameFinished(int gameId) throws RemoteException;

    NetworkMessage RMICheckForChatUpdates(String nickname, int gameId, HashMap<Integer, Integer> numberMessages) throws RemoteException;

    Game RMIDoWantToRecover(NetworkMessage networkMessage) throws RemoteException;
    Game RMISetPlayer(int gameId, String nickname) throws RemoteException;
}
