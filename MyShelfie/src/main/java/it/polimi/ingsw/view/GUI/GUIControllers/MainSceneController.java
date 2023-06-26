package it.polimi.ingsw.view.GUI.GUIControllers;

import it.polimi.ingsw.MyShelfieClient;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.view.GUI.GUIView;
import it.polimi.ingsw.view.GUI.SceneController;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

import static java.lang.String.valueOf;

/**
 * This class represents the Controller of the Main scene
 * MainScene is the GUI scene which contains the board, the players' shelves, the goals and the chat.
 */
public class MainSceneController implements GenericSceneController, Initializable {

    private GUIView gui;
    private ArrayList<Position> positionsToPick = new ArrayList<>();
    private ArrayList<Position> positionsToOrder = new ArrayList<>();
    private static int selectedColumn;
    private int turnPhase;
    private int gameId;
    private String nickname;
    private int protocol;

    @FXML
    private ImageView board00, board01, board02, board03, board04, board05, board06, board07, board08, board10, board11, board12, board13, board14, board15, board16, board17, board18, board20, board21, board22, board23, board24, board25, board26, board27, board28, board30, board31, board32, board33, board34, board35, board36, board37, board38, board40, board41, board42, board43, board44, board45, board46, board47, board48, board50, board51, board52, board53, board54, board55, board56, board57, board58, board60, board61, board62, board63, board64, board65, board66, board67, board68, board70, board71, board72, board73, board74, board75, board76, board77, board78, board80, board81, board82, board83, board84, board85, board86, board87, board88;
    private ImageView[][] boardImage = new ImageView[9][9];
    @FXML
    private ToggleButton myShelf, otherPlayerButton0, otherPlayerButton1, otherPlayerButton2;
    @FXML
    private Button boardButton03, boardButton04, boardButton13, boardButton14, boardButton15, boardButton22, boardButton23, boardButton24, boardButton25, boardButton26, boardButton31, boardButton32, boardButton33, boardButton34, boardButton35, boardButton36, boardButton37, boardButton38, boardButton40, boardButton41, boardButton42, boardButton43, boardButton44, boardButton45, boardButton46, boardButton47, boardButton48, boardButton50, boardButton51, boardButton52, boardButton53, boardButton54, boardButton55, boardButton56, boardButton57, boardButton62, boardButton63, boardButton64, boardButton65, boardButton66, boardButton73, boardButton74, boardButton75, boardButton84, boardButton85;
    @FXML
    private Button firstColumnButton, secondColumnButton, thirdColumnButton, fourthColumnButton, fifthColumnButton;
    @FXML
    private Button vButton, backButton, makeMoveButton;
    @FXML
    private Button quit;
    @FXML
    private Button pickedBottomButton, pickedMiddleButton, pickedTopButton;
    @FXML
    private ImageView pickedTileBottom, pickedTileMiddle, pickedTileTop;
    @FXML
    private Label bottomLabel, middleLabel, topLabel;
    @FXML
    private Label turnPhaseLabel;
    @FXML
    private Label currentlyPlayingLabel;
    @FXML
    private Label commonGoal1AchievedLabel, commonGoal2AchievedLabel;
    @FXML
    private ImageView boardEndGameToken, commonGoal1Token, commonGoal2Token;
    @FXML
    private ImageView shelf00, shelf01, shelf02, shelf03, shelf04, shelf10, shelf11, shelf12, shelf13, shelf14, shelf20, shelf21, shelf22, shelf23, shelf24, shelf30, shelf31, shelf32, shelf33, shelf34, shelf40, shelf41, shelf42, shelf43, shelf44, shelf50, shelf51, shelf52, shelf53, shelf54;
    private ImageView[][] shelfImage = new ImageView[6][5];
    @FXML
    private ImageView seat;
    @FXML
    private ImageView tokenAchieved0, tokenAchieved1, tokenAchieved2;
    @FXML
    private ImageView personalGoal, commonGoal1, commonGoal2;
    @FXML
    private ChoiceBox choiceBox;
    @FXML
    private Button sendMessageButton;
    @FXML
    private TextArea messageField;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private Pane pane;

    private double textHeigth = 0.0;

    /**
     * Class Constructor
     * @param gui is the gui view of the client
     * @param parameters contains the game id, the client nickname and the connection protocol
     */
    public MainSceneController(GUIView gui, ArrayList<Object> parameters){
        this.gui = gui;
        this.gameId = (int) parameters.get(0);
        this.nickname = (String) parameters.get(1);
        this.protocol = (int) parameters.get(2);
    }


    /**
     * Override method of setGui in GenericSceneController
     * @param gui is the gui to set
     */
    @Override
    public void setGui(GUIView gui) {
        this.gui = gui;
    }

    /**
     * Override method of initData from GenericSceneController
     * @param parameters is the list of parameters
     */
    @Override
    public void initData(ArrayList<Object> parameters) {}

    /**
     * Override method of initialize from Initializable
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(this::initAll);
        Platform.runLater(() ->
        {
            sendMessageButton.addEventHandler(MouseEvent.MOUSE_CLICKED, this::sendMessage);
            quit.addEventHandler(MouseEvent.MOUSE_CLICKED, this::quitGame);
        });
        myShelf.setSelected(true);
    }

    /**
     * This method initializes buttons, and some attributes of the scene
     */
    private void initAll(){
        setAttributes();
        gui = MyShelfieClient.getGuiView();
        gui.setStageController(this);
        turnPhase = 0;
        setTurnPhaseLabel(turnPhase);

        initButtons();
    }

    /**
     * This method initializes arrays for a simpler use of the imageView of the tiles
     */
    public void setAttributes(){
        boardImage[0][0] = board00;
        boardImage[0][1] = board01;
        boardImage[0][2] = board02;
        boardImage[0][3] = board03;
        boardImage[0][4] = board04;
        boardImage[0][5] = board05;
        boardImage[0][6] = board06;
        boardImage[0][7] = board07;
        boardImage[0][8] = board08;

        boardImage[1][0] = board10;
        boardImage[1][1] = board11;
        boardImage[1][2] = board12;
        boardImage[1][3] = board13;
        boardImage[1][4] = board14;
        boardImage[1][5] = board15;
        boardImage[1][6] = board16;
        boardImage[1][7] = board17;
        boardImage[1][8] = board18;

        boardImage[2][0] = board20;
        boardImage[2][1] = board21;
        boardImage[2][2] = board22;
        boardImage[2][3] = board23;
        boardImage[2][4] = board24;
        boardImage[2][5] = board25;
        boardImage[2][6] = board26;
        boardImage[2][7] = board27;
        boardImage[2][8] = board28;

        boardImage[3][0] = board30;
        boardImage[3][1] = board31;
        boardImage[3][2] = board32;
        boardImage[3][3] = board33;
        boardImage[3][4] = board34;
        boardImage[3][5] = board35;
        boardImage[3][6] = board36;
        boardImage[3][7] = board37;
        boardImage[3][8] = board38;

        boardImage[4][0] = board40;
        boardImage[4][1] = board41;
        boardImage[4][2] = board42;
        boardImage[4][3] = board43;
        boardImage[4][4] = board44;
        boardImage[4][5] = board45;
        boardImage[4][6] = board46;
        boardImage[4][7] = board47;
        boardImage[4][8] = board48;

        boardImage[5][0] = board50;
        boardImage[5][1] = board51;
        boardImage[5][2] = board52;
        boardImage[5][3] = board53;
        boardImage[5][4] = board54;
        boardImage[5][5] = board55;
        boardImage[5][6] = board56;
        boardImage[5][7] = board57;
        boardImage[5][8] = board58;

        boardImage[6][0] = board60;
        boardImage[6][1] = board61;
        boardImage[6][2] = board62;
        boardImage[6][3] = board63;
        boardImage[6][4] = board64;
        boardImage[6][5] = board65;
        boardImage[6][6] = board66;
        boardImage[6][7] = board67;
        boardImage[6][8] = board68;

        boardImage[7][0] = board70;
        boardImage[7][1] = board71;
        boardImage[7][2] = board72;
        boardImage[7][3] = board73;
        boardImage[7][4] = board74;
        boardImage[7][5] = board75;
        boardImage[7][6] = board76;
        boardImage[7][7] = board77;
        boardImage[7][8] = board78;

        boardImage[8][0] = board80;
        boardImage[8][1] = board81;
        boardImage[8][2] = board82;
        boardImage[8][3] = board83;
        boardImage[8][4] = board84;
        boardImage[8][5] = board85;
        boardImage[8][6] = board86;
        boardImage[8][7] = board87;
        boardImage[8][8] = board88;


        shelfImage[0][0] = shelf00;
        shelfImage[0][1] = shelf01;
        shelfImage[0][2] = shelf02;
        shelfImage[0][3] = shelf03;
        shelfImage[0][4] = shelf04;

        shelfImage[1][0] = shelf10;
        shelfImage[1][1] = shelf11;
        shelfImage[1][2] = shelf12;
        shelfImage[1][3] = shelf13;
        shelfImage[1][4] = shelf14;

        shelfImage[2][0] = shelf20;
        shelfImage[2][1] = shelf21;
        shelfImage[2][2] = shelf22;
        shelfImage[2][3] = shelf23;
        shelfImage[2][4] = shelf24;

        shelfImage[3][0] = shelf30;
        shelfImage[3][1] = shelf31;
        shelfImage[3][2] = shelf32;
        shelfImage[3][3] = shelf33;
        shelfImage[3][4] = shelf34;

        shelfImage[4][0] = shelf40;
        shelfImage[4][1] = shelf41;
        shelfImage[4][2] = shelf42;
        shelfImage[4][3] = shelf43;
        shelfImage[4][4] = shelf44;

        shelfImage[5][0] = shelf50;
        shelfImage[5][1] = shelf51;
        shelfImage[5][2] = shelf52;
        shelfImage[5][3] = shelf53;
        shelfImage[5][4] = shelf54;
    }

    /**
     * This method sets the tiles picture in the board
     */
    public void setBoardImage() {
    Board board = gui.getBoard();

    TileType[][] types = board.getTilesType();
    Tile[][] tiles = board.getTilesTable();
        for(int i = 0; i<9; i++) {
            for (int j = 0; j<9; j++) {
                if (tiles[i][j] != null) {
                    setTilePicture(boardImage[i][j], tiles[i][j]);
                } else boardImage[i][j].setOpacity(0);
            }
        }
    }

    /**
     * This method sets the tiles picture in the shelf
     */
    public void setShelfImage(Shelf shelf) {
        TileType[][] types = shelf.getShelfTypes();
        for(int i = 0; i<6; i++) {
            for (int j = 0; j<5; j++) {
                if (shelf.getTile(i, j) != null) {
                    setTilePicture(shelfImage[i][j], shelf.getTile(i, j));
                } else shelfImage[i][j].setOpacity(0);
            }
        }
    }

    /**
     * This method sets the tiles picture in the shelf
     */
    private void makeMoveButtonClick(Event event) {
        if (gui.getCurrentPlayer() != null  && gui.getCurrentPlayer().equals(gui.getClientNickname()) && turnPhase == 0) {
            turnPhase = 1;
            positionsToPick = new ArrayList<>();
            positionsToOrder = new ArrayList<>();
            setTurnPhaseLabel(turnPhase);
        }
    }

    /**
     * This method manages the actions related to the vButton
     *
     * @param event is the event triggered by clicking the vButton
     */
    private void vButtonClick(Event event) {
        switch (turnPhase) {
            case 1 -> {
                if (positionsToPick.size() >= 1 && positionsToPick.size() <= 3) {
                        setTilePicture(pickedTileBottom, gui.getBoard().getTilesTable()[positionsToPick.get(0).getRow()][positionsToPick.get(0).getColumn()]);
                        pickedTileBottom.setOpacity(1);
                        if (positionsToPick.size() >= 2) {
                            setTilePicture(pickedTileMiddle, gui.getBoard().getTilesTable()[positionsToPick.get(1).getRow()][positionsToPick.get(1).getColumn()]);
                            pickedTileMiddle.setOpacity(1);
                            if (positionsToPick.size() == 3) {
                                setTilePicture(pickedTileTop, gui.getBoard().getTilesTable()[positionsToPick.get(2).getRow()][positionsToPick.get(2).getColumn()]);
                                pickedTileTop.setOpacity(1);
                            }
                        }
                        turnPhase = 2;
                        setTurnPhaseLabel(turnPhase);
                        if (positionsToPick.size() == 1) {
                            positionsToOrder.add(positionsToPick.get(0));
                            turnPhase = 3;
                            setTurnPhaseLabel(turnPhase);
                        }
                }
            }

            case 2 -> {
                if (positionsToOrder.size() ==  positionsToPick.size()) {
                    setTilePicture(pickedTileBottom, gui.getBoard().getTilesTable()[positionsToOrder.get(0).getRow()][positionsToOrder.get(0).getColumn()]);
                    pickedTileBottom.setOpacity(1);
                    bottomLabel.setOpacity(0);
                    if (positionsToOrder.size() >= 2) {
                        setTilePicture(pickedTileMiddle, gui.getBoard().getTilesTable()[positionsToOrder.get(1).getRow()][positionsToOrder.get(1).getColumn()]);
                        pickedTileMiddle.setOpacity(1);
                        middleLabel.setOpacity(0);
                        if (positionsToOrder.size() == 3) {
                            setTilePicture(pickedTileTop, gui.getBoard().getTilesTable()[positionsToOrder.get(2).getRow()][positionsToOrder.get(2).getColumn()]);
                            pickedTileTop.setOpacity(1);
                            topLabel.setOpacity(0);
                        }
                    }
                    selectedColumn = -1;
                    turnPhase = 3;
                    setTurnPhaseLabel(turnPhase);
                }
            }

            case 3 -> {
                if (selectedColumn != -1) {
                    if (gui.getShelf().freeRows(selectedColumn) >= positionsToOrder.size()) {
                        ArrayList<String> positions = new ArrayList<>();
                        for (int i = 0; i < positionsToOrder.size(); i++) {
                            String txt1 = valueOf(positionsToOrder.get(i).getRow() + 1);
                            String txt2 = valueOf(positionsToOrder.get(i).getColumn() + 1);
                            positions.add(txt1 + txt2);
                        }
                        gui.getClientController().moveTiles(positions, selectedColumn + 1);

                        bottomLabel.setOpacity(0);
                        middleLabel.setOpacity(0);
                        topLabel.setOpacity(0);
                        pickedTileBottom.setOpacity(0);
                        pickedTileMiddle.setOpacity(0);
                        pickedTileTop.setOpacity(0);
                        setBoardImage();
                        setShelfImage(gui.getShelf());
                        turnPhase = 0;
                        setTurnPhaseLabel(turnPhase);
                        selectedColumn = -1;

                    }
                }
            }
        }
    }

    /**
     * This method manages the actions related to the back button
     *
     * @param event is the event triggered by clicking the back button
     */
    private void backButtonClick (Event event){
        switch (turnPhase) {
            case 1 -> {
                positionsToPick = new ArrayList<>();
                for (int i = 0; i<9; i++) {
                    for(int j = 0; j<9; j++)
                        if (gui.getBoard().getTilesTable()[i][j] != null)
                            boardImage[i][j].setOpacity(1);
                }
            }

            case 2, 3 -> {
                positionsToPick = new ArrayList<>();
                positionsToOrder = new ArrayList<>();
                for (int i = 0; i<9; i++) {
                    for(int j = 0; j<9; j++)
                        if (gui.getBoard().getTilesTable()[i][j] != null)
                            boardImage[i][j].setOpacity(1);
                }
                turnPhase = 1;
                setTurnPhaseLabel(turnPhase);
                bottomLabel.setOpacity(0);
                middleLabel.setOpacity(0);
                topLabel.setOpacity(0);
                pickedTileBottom.setOpacity(0);
                pickedTileMiddle.setOpacity(0);
                pickedTileTop.setOpacity(0);

                selectedColumn = -1;

            }
        }

    }

    /**
     * This method manages the actions of sorting the tiles to put into the shelf (Top Tile)
     *
     * @param event is the event triggered by clicking the invisible button under the chosen tile
     */
    private void pickedBottomButtonClick (Event event){
        if (turnPhase == 2) {
            if ((positionsToOrder.size() < positionsToPick.size()) && pickedTileBottom.getOpacity() == 1) {
                positionsToOrder.add(positionsToPick.get(0));
                pickedTileBottom.setOpacity(0.3);
                String text = "  " + (positionsToOrder.size());
                bottomLabel.setText(text);
                bottomLabel.setOpacity(1);
            }
        }
    }

    /**
     * This method manages the actions of sorting the tiles to put into the shelf (Middle Tile)
     *
     * @param event is the event triggered by clicking the invisible button under the chosen tile
     */
    private void pickedMiddleButtonClick (Event event){
        if (turnPhase == 2) {
            if ((positionsToOrder.size() < positionsToPick.size()) && pickedTileMiddle.getOpacity() == 1) {
                positionsToOrder.add(positionsToPick.get(1));
                pickedTileMiddle.setOpacity(0.3);
                String text = "  " + (positionsToOrder.size());
                middleLabel.setText(text);
                middleLabel.setOpacity(1);
            }
        }
    }

    /**
     * This method manages the actions of sorting the tiles to put into the shelf (Bottom Tile)
     *
     * @param event is the event triggered by clicking the invisible button under the chosen tile
     */
    private void pickedTopButtonClick (Event event){
        if (turnPhase == 2) {
            if ((positionsToOrder.size() < positionsToPick.size()) && pickedTileTop.getOpacity() == 1) {
                positionsToOrder.add(positionsToPick.get(2));
                pickedTileTop.setOpacity(0.3);
                String text = "  " + (positionsToOrder.size());
                topLabel.setText(text);
                topLabel.setOpacity(1);
            }
        }
    }

    /**
     * (First Column) This method manages the actions of choosing the column of the shelf to put the tile into
     */
    private void ColumnButtonClick(int column){
        if (turnPhase == 3)
            selectedColumn = column;
    }

    private void otherPlayerButtonClick0(Event event) {}
    private void otherPlayerButtonClick1(Event event) {}
    private void otherPlayerButtonClick2(Event event) {}

    /**
     * This method sets the picture of all the goals
     */
    public void setGoalsPicture(){
        setCommonGoalPicture(commonGoal1, gui.getCommonGoals().get(0).getId());
        setCommonGoalPicture(commonGoal2, gui.getCommonGoals().get(1).getId());
        setPersonalGoalPicture(personalGoal, gui.getPersonalGoal().getId());
    }
    /**
     * This method sets the picture of all the tokens
     */
    public void setTokensPicture(){
        setTokenPicture(commonGoal1Token, 1);
        setTokenPicture(commonGoal2Token, 2);
        setTokenPicture(boardEndGameToken, 0);
        setAchievedTokenPicture(tokenAchieved0, 0);
        setAchievedTokenPicture(tokenAchieved1, 1);
        setAchievedTokenPicture(tokenAchieved2, 2);
        setAchievedLabel();
    }
    /**
     * This method sets the achieved label if a token is achieved
     */
    private void setAchievedLabel() {
        for (int i = 0; i< gui.getPersonalTokens().size(); i++) {
            if ((gui.getPersonalTokens().get(i).getId() != 0) && ((gui.getPersonalTokens().get(i).getId() % 2) == 0))
                commonGoal1AchievedLabel.setOpacity(1);
            if ((gui.getPersonalTokens().get(i).getId() % 2) == 1)
                commonGoal2AchievedLabel.setOpacity(1);
        }
    }

    /**
     * This method sets the currently playing label
     */
    public void setCurrentlyPlayingLabel (String currentPlayer){
        if (currentPlayer != null && !currentPlayer.equals(gui.getClientNickname())) {
            Platform.runLater( () -> {currentlyPlayingLabel.setText("It's "+ currentPlayer+ " turn");});
        } else if (currentPlayer != null && currentPlayer.equals(gui.getClientNickname())) {
                Platform.runLater( () -> {currentlyPlayingLabel.setText("It's your turn");});
        } else {currentlyPlayingLabel.setText("IDK who is playing");}
    }

    /**
     * This method sets the seat picture
     */
    public void setSeatPicture() {
        if (gui.isSeat() == true)
            seat.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/misc/firstplayertoken.png"))));;
    }

    /**
     * This method removes a tile from the Board
     * @param row is the row of the tile
     * @param column is the column of the tile
     */
    private void boardButtonClick (int row, int column){
        if (turnPhase == 1 && gui.getShelf().freeSpots() > positionsToPick.size()) {
            if (boardImage[row][column].getOpacity() == 1.0) {
                if (positionsToPick.size() >= 0 && positionsToPick.size() < 3) {
                    if (gui.getBoard().canPull(row, column)) {
                        positionsToPick.add(new Position(row, column));
                        if (gui.getBoard().areAligned(positionsToPick)) {
                            boardImage[row][column].setOpacity(0.3);
                        } else positionsToPick.remove(positionsToPick.size() - 1);
                    }
                }
            }
        }
    }

    /**
     * This method sets the assets of the other players
     */
    public void setOtherPlayersAssets() {
        if(choiceBox.getItems().size() == 0) {

            if (gui.getPlayersNickname().size() >= 1) {
                Platform.runLater( () -> {
                    otherPlayerButton0.setText(gui.getPlayersNickname().get(0));
                });
                if(gui.getPlayersNickname().size() >= 2) {
                    Platform.runLater( () -> {
                                otherPlayerButton1.setText(gui.getPlayersNickname().get(1));
                            });
                } else {
                    Platform.runLater(() -> {
                        otherPlayerButton1.setOpacity(0);
                        otherPlayerButton1.setDisable(true);
                    });
                }

                if(gui.getPlayersNickname().size() >= 3) {
                    Platform.runLater( () -> {
                    otherPlayerButton2.setText(gui.getPlayersNickname().get(2));
                });}
                else {
                    Platform.runLater(() -> {
                        otherPlayerButton2.setOpacity(0);
                        otherPlayerButton2.setDisable(true);
                    });
                }
                ArrayList<String> nicknameList = new ArrayList<>();
                nicknameList.add("Everyone");
                if(gui.getPlayersNickname().size() > 1)
                    nicknameList.addAll(gui.getPlayersNickname());
                ObservableList<String> list = FXCollections.observableArrayList(nicknameList);
                choiceBox.getItems().addAll(list);
                Platform.runLater(()-> {
                    choiceBox.getSelectionModel().selectFirst();
                });
                }
            }
    }

    /**
     * This method sets the shelves of the other players
     */
    private void setOtherPlayersShelves(Event event){
        myShelf.setSelected(false);
        otherPlayerButton0.setSelected(false);
        otherPlayerButton1.setSelected(false);
        otherPlayerButton2.setSelected(false);
        if(event.getSource().equals(myShelf)){
            setShelfImage(gui.getShelf());
            firstColumnButton.setDisable(false);
            secondColumnButton.setDisable(false);
            thirdColumnButton.setDisable(false);
            fourthColumnButton.setDisable(false);
            fifthColumnButton.setDisable(false);
            firstColumnButton.setOpacity(100);
            secondColumnButton.setOpacity(100);
            thirdColumnButton.setOpacity(100);
            fourthColumnButton.setOpacity(100);
            fifthColumnButton.setOpacity(100);
            myShelf.setSelected(true);
        }else {
            int getIndex;
            if (event.getSource().equals(otherPlayerButton0)) {
                otherPlayerButton0.setSelected(true);
                getIndex = 0;
            }
            else if (event.getSource().equals(otherPlayerButton1)) {
                getIndex = 1;
                otherPlayerButton1.setSelected(true);
            } else {
                getIndex = 2;
                otherPlayerButton2.setSelected(true);
            }
            Shelf playerShelf = gui.getPlayersShelf().get(gui.getPlayersNickname().get(getIndex));
            setShelfImage(playerShelf);
            firstColumnButton.setDisable(true);
            secondColumnButton.setDisable(true);
            thirdColumnButton.setDisable(true);
            fourthColumnButton.setDisable(true);
            fifthColumnButton.setDisable(true);
            firstColumnButton.setOpacity(0);
            secondColumnButton.setOpacity(0);
            thirdColumnButton.setOpacity(0);
            fourthColumnButton.setOpacity(0);
            fifthColumnButton.setOpacity(0);
        }
    }



    /**
     * This method initializes all the buttons
     */
    private void initButtons() {
        myShelf.addEventHandler(MouseEvent.MOUSE_CLICKED, this::setOtherPlayersShelves);
        otherPlayerButton0.addEventHandler(MouseEvent.MOUSE_CLICKED, this::setOtherPlayersShelves);
        otherPlayerButton1.addEventHandler(MouseEvent.MOUSE_CLICKED, this::setOtherPlayersShelves);
        otherPlayerButton2.addEventHandler(MouseEvent.MOUSE_CLICKED, this::setOtherPlayersShelves);
        makeMoveButton.addEventHandler(MouseEvent.MOUSE_RELEASED, this::makeMoveButtonClick);
        backButton.addEventHandler(MouseEvent.MOUSE_RELEASED, this::backButtonClick);
        vButton.addEventHandler(MouseEvent.MOUSE_RELEASED, this::vButtonClick);

        firstColumnButton.setOnMouseClicked(mouseEvent ->{ColumnButtonClick(0);});
        secondColumnButton.setOnMouseClicked(mouseEvent -> {ColumnButtonClick(1);});
        thirdColumnButton.setOnMouseClicked(mouseEvent -> {ColumnButtonClick(2);});
        fourthColumnButton.setOnMouseClicked(mouseEvent -> {ColumnButtonClick(3);});
        fifthColumnButton.setOnMouseClicked(mouseEvent -> {ColumnButtonClick(4);});

        boardButton03.setOnMouseClicked(mouseEvent -> {boardButtonClick(0, 3);});
        boardButton04.setOnMouseClicked(mouseEvent -> {boardButtonClick(0, 4);});
        boardButton13.setOnMouseClicked(mouseEvent -> {boardButtonClick(1, 3);});
        boardButton14.setOnMouseClicked(mouseEvent -> {boardButtonClick(1, 4);});
        boardButton15.setOnMouseClicked(mouseEvent -> {boardButtonClick(1, 5);});
        boardButton22.setOnMouseClicked(mouseEvent -> {boardButtonClick(2, 2);});
        boardButton23.setOnMouseClicked(mouseEvent -> {boardButtonClick(2, 3);});
        boardButton24.setOnMouseClicked(mouseEvent -> {boardButtonClick(2, 4);});
        boardButton25.setOnMouseClicked(mouseEvent -> {boardButtonClick(2, 5);});
        boardButton26.setOnMouseClicked(mouseEvent -> {boardButtonClick(2, 6);});
        boardButton31.setOnMouseClicked(mouseEvent -> {boardButtonClick(3, 1);});
        boardButton32.setOnMouseClicked(mouseEvent -> {boardButtonClick(3, 2);});
        boardButton33.setOnMouseClicked(mouseEvent -> {boardButtonClick(3, 3);});
        boardButton34.setOnMouseClicked(mouseEvent -> {boardButtonClick(3, 4);});
        boardButton35.setOnMouseClicked(mouseEvent -> {boardButtonClick(3, 5);});
        boardButton36.setOnMouseClicked(mouseEvent -> {boardButtonClick(3, 6);});
        boardButton37.setOnMouseClicked(mouseEvent -> {boardButtonClick(3, 7);});
        boardButton38.setOnMouseClicked(mouseEvent -> {boardButtonClick(3, 8);});
        boardButton40.setOnMouseClicked(mouseEvent -> {boardButtonClick(4, 0);});
        boardButton41.setOnMouseClicked(mouseEvent -> {boardButtonClick(4, 1);});
        boardButton42.setOnMouseClicked(mouseEvent -> {boardButtonClick(4, 2);});
        boardButton43.setOnMouseClicked(mouseEvent -> {boardButtonClick(4, 3);});
        boardButton44.setOnMouseClicked(mouseEvent -> {boardButtonClick(4, 4);});
        boardButton45.setOnMouseClicked(mouseEvent -> {boardButtonClick(4, 5);});
        boardButton46.setOnMouseClicked(mouseEvent -> {boardButtonClick(4, 6);});
        boardButton47.setOnMouseClicked(mouseEvent -> {boardButtonClick(4, 7);});
        boardButton48.setOnMouseClicked(mouseEvent -> {boardButtonClick(4, 8);});
        boardButton50.setOnMouseClicked(mouseEvent -> {boardButtonClick(5, 0);});
        boardButton51.setOnMouseClicked(mouseEvent -> {boardButtonClick(5, 1);});
        boardButton52.setOnMouseClicked(mouseEvent -> {boardButtonClick(5, 2);});
        boardButton53.setOnMouseClicked(mouseEvent -> {boardButtonClick(5, 3);});
        boardButton54.setOnMouseClicked(mouseEvent -> {boardButtonClick(5, 4);});
        boardButton55.setOnMouseClicked(mouseEvent -> {boardButtonClick(5, 5);});
        boardButton56.setOnMouseClicked(mouseEvent -> {boardButtonClick(5, 6);});
        boardButton57.setOnMouseClicked(mouseEvent -> {boardButtonClick(5, 7);});
        boardButton62.setOnMouseClicked(mouseEvent -> {boardButtonClick(6, 2);});
        boardButton63.setOnMouseClicked(mouseEvent -> {boardButtonClick(6, 3);});
        boardButton64.setOnMouseClicked(mouseEvent -> {boardButtonClick(6, 4);});
        boardButton65.setOnMouseClicked(mouseEvent -> {boardButtonClick(6, 5);});
        boardButton66.setOnMouseClicked(mouseEvent -> {boardButtonClick(6, 6);});
        boardButton73.setOnMouseClicked(mouseEvent -> {boardButtonClick(7, 3);});
        boardButton74.setOnMouseClicked(mouseEvent -> {boardButtonClick(7, 4);});
        boardButton75.setOnMouseClicked(mouseEvent -> {boardButtonClick(7, 5);});
        boardButton84.setOnMouseClicked(mouseEvent -> {boardButtonClick(8, 4);});
        boardButton85.setOnMouseClicked(mouseEvent -> {boardButtonClick(8, 5);});

        pickedBottomButton.addEventHandler(MouseEvent.MOUSE_RELEASED, this::pickedBottomButtonClick);
        pickedMiddleButton.addEventHandler(MouseEvent.MOUSE_RELEASED, this::pickedMiddleButtonClick);
        pickedTopButton.addEventHandler(MouseEvent.MOUSE_RELEASED, this::pickedTopButtonClick);

        otherPlayerButton0.addEventHandler(MouseEvent.MOUSE_RELEASED, this::otherPlayerButtonClick0);
        otherPlayerButton1.addEventHandler(MouseEvent.MOUSE_RELEASED, this::otherPlayerButtonClick1);
        otherPlayerButton2.addEventHandler(MouseEvent.MOUSE_RELEASED, this::otherPlayerButtonClick2);
    }

    /**
     * This method sets the text of the label of the current turn phase
     * @param phase is the turn phase
     */
    private void setTurnPhaseLabel (int phase) {
        switch(phase) {
            case 0 -> turnPhaseLabel.setText("When it's your turn click on 'Make move' to pick tiles.");

            case 1 -> {
                String maxTiles;
                if (gui.getShelf().freeSpots() < 3 ) {
                    maxTiles = String.valueOf(gui.getShelf().freeSpots());
                } else {maxTiles = "3";}
                turnPhaseLabel.setText("Choose no more than " + maxTiles + " aligned Tiles and click on V.");
            }
             case 2 -> {
                turnPhaseLabel.setText("Tap on the tiles to choose the order and click on V.");
             }
             case 3 -> {
                turnPhaseLabel.setText("Choose the column and click on V.");
             }
        }
    }

    /**
     * This method sets the picture of the tile
     * @param tile is the tile
     * @param image is the image of the tile
     */
    private void setTilePicture (ImageView image, Tile tile) {
        TileType type = tile.getType();
        int imageType = tile.getImageType();
        if (tile == null) {
            image.setOpacity(0);
        } else {
            switch (type) {
                case PLANT -> {
                    switch (imageType) {
                        case 0 -> Platform.runLater( () -> {image.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/item tiles/Piante1.1.png"))));});
                        case 1 -> Platform.runLater( () -> {image.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/item tiles/Piante1.2.png"))));});
                        case 2 -> Platform.runLater( () -> {image.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/item tiles/Piante1.3.png"))));});
                    }
                    image.setOpacity(1);
                }
                case TROPHY -> {
                    switch (imageType) {
                        case 0 -> Platform.runLater( () -> {image.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/item tiles/Trofei1.1.png"))));});
                        case 1 -> Platform.runLater( () -> {image.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/item tiles/Trofei1.2.png"))));});
                        case 2 -> Platform.runLater( () -> {image.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/item tiles/Trofei1.3.png"))));});
                    }
                    image.setOpacity(1);
                }
                case GAME -> {
                    switch (imageType) {
                        case 0 -> Platform.runLater( () -> {image.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/item tiles/Giochi1.1.png"))));});
                        case 1 -> Platform.runLater( () -> {image.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/item tiles/Giochi1.2.png"))));});
                        case 2 -> Platform.runLater( () -> {image.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/item tiles/Giochi1.3.png"))));});
                    }
                    image.setOpacity(1);
                }
                case CAT -> {
                    switch (imageType) {
                        case 0 -> Platform.runLater( () -> {image.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/item tiles/Gatti1.1.png"))));});
                        case 1 -> Platform.runLater( () -> {image.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/item tiles/Gatti1.2.png"))));});
                        case 2 -> Platform.runLater( () -> {image.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/item tiles/Gatti1.3.png"))));});
                    }
                    image.setOpacity(1);
                }
                case BOOK -> {
                    switch (imageType) {
                        case 0 -> Platform.runLater( () -> {image.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/item tiles/Libri1.1.png"))));});
                        case 1 -> Platform.runLater( () -> {image.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/item tiles/Libri1.2.png"))));});
                        case 2 -> Platform.runLater( () -> {image.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/item tiles/Libri1.3.png"))));});
                    }
                    image.setOpacity(1);
                }
                case FRAME -> {
                    switch (imageType) {
                        case 0 -> Platform.runLater( () -> {image.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/item tiles/Cornici1.1.png"))));});
                        case 1 -> Platform.runLater( () -> {image.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/item tiles/Cornici1.2.png"))));});
                        case 2 -> Platform.runLater( () -> {image.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/item tiles/Cornici1.3.png"))));});
                    }
                    image.setOpacity(1);
                }
            }
        }
    }

    /**
     * This method sets the picture of the common goal
     * @param id is the common goal id
     * @param image is the image of the tile
     */
    private void setCommonGoalPicture(ImageView image, int id) {
        switch (id) {
            case 0 -> Platform.runLater( () -> {image.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/common goal cards/4.jpg"))));});
            case 1 -> Platform.runLater( () -> {image.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/common goal cards/11.jpg"))));});
            case 2 -> Platform.runLater( () -> {image.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/common goal cards/3.jpg"))));});
            case 3 -> Platform.runLater( () -> {image.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/common goal cards/7.jpg"))));});
            case 4 -> Platform.runLater( () -> {image.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/common goal cards/8.jpg"))));});
            case 5 -> Platform.runLater( () -> {image.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/common goal cards/2.jpg"))));});
            case 6 -> Platform.runLater( () -> {image.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/common goal cards/1.jpg"))));});
            case 7 -> Platform.runLater( () -> {image.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/common goal cards/6.jpg"))));});
            case 8 -> Platform.runLater( () -> {image.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/common goal cards/5.jpg"))));});
            case 9 -> Platform.runLater( () -> {image.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/common goal cards/10.jpg"))));});
            case 10 -> Platform.runLater( () -> {image.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/common goal cards/9.jpg"))));});
            case 11 -> Platform.runLater( () -> {image.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/common goal cards/12.jpg"))));});
        }
    }

    /**
     * This method sets the picture of the personal goal
     * @param id is the personal goal id
     * @param image is the image of the tile
     */
    private void setPersonalGoalPicture(ImageView image, int id) {
        switch (id) {
            case 0 -> Platform.runLater( () -> {image.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/personal goal cards/Personal_Goals.png"))));});
            case 1 -> Platform.runLater( () -> {image.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/personal goal cards/Personal_Goals2.png"))));});
            case 2 -> Platform.runLater( () -> {image.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/personal goal cards/Personal_Goals3.png"))));});
            case 3 -> Platform.runLater( () -> {image.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/personal goal cards/Personal_Goals4.png"))));});
            case 4 -> Platform.runLater( () -> {image.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/personal goal cards/Personal_Goals5.png"))));});
            case 5 -> Platform.runLater( () -> {image.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/personal goal cards/Personal_Goals6.png"))));});
            case 6 -> Platform.runLater( () -> {image.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/personal goal cards/Personal_Goals7.png"))));});
            case 7 -> Platform.runLater( () -> {image.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/personal goal cards/Personal_Goals8.png"))));});
            case 8 -> Platform.runLater( () -> {image.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/personal goal cards/Personal_Goals9.png"))));});
            case 9 -> Platform.runLater( () -> {image.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/personal goal cards/Personal_Goals10.png"))));});
            case 10 -> Platform.runLater( () -> {image.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/personal goal cards/Personal_Goals11.png"))));});
            case 11 -> Platform.runLater( () -> {image.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/personal goal cards/Personal_Goals12.png"))));});
        }
    }


    /**
     * This method sets the picture of the available token
     * @param goalNo is the common goal
     * @param image is the image of the token
     */
    private void setTokenPicture(ImageView image, int goalNo) {
        switch(goalNo) {

            case 0 -> {
                boolean present = false;

                for (int i = 0; i < gui.getGameTokens().size(); i++) {
                    if (gui.getGameTokens().get(i).getId() == 0)
                        present = true;
                }
                if (present) {
                    Platform.runLater( () -> {image.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/scoring tokens/end game.jpg"))));});
                } else {image.setOpacity(0);}
            }

            case 1 -> {
                int maxToken = 0;

                for (int i = 0; i < gui.getGameTokens().size(); i++) {
                    if ((gui.getGameTokens().get(i).getId() != 0) && ((gui.getGameTokens().get(i).getId() % 2) == 0))
                    {
                        if (gui.getGameTokens().get(i).getPoints() > maxToken)
                            maxToken = gui.getGameTokens().get(i).getPoints();
                    }
                }
                switch(maxToken) {
                    case 8 -> Platform.runLater( () -> {image.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/scoring tokens/scoring_8.jpg"))));});
                    case 6 -> Platform.runLater( () -> {image.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/scoring tokens/scoring_6.jpg"))));});
                    case 4 -> Platform.runLater( () -> {image.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/scoring tokens/scoring_4.jpg"))));});
                    case 2 -> Platform.runLater( () -> {image.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/scoring tokens/scoring_2.jpg"))));});
                    case 0 -> image.setOpacity(0);
                }
            }

            case 2 -> {
                int maxToken = 0;

                for (int i = 0; i < gui.getGameTokens().size(); i++) {
                    if ((gui.getGameTokens().get(i).getId() % 2) == 1)
                    {
                        if (gui.getGameTokens().get(i).getPoints() > maxToken)
                            maxToken = gui.getGameTokens().get(i).getPoints();
                    }
                }

                switch(maxToken) {
                    case 8 -> Platform.runLater( () -> {image.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/scoring tokens/scoring_8.jpg"))));});
                    case 6 -> Platform.runLater( () -> {image.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/scoring tokens/scoring_6.jpg"))));});
                    case 4 -> Platform.runLater( () -> {image.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/scoring tokens/scoring_4.jpg"))));});
                    case 2 -> Platform.runLater( () -> {image.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/scoring tokens/scoring_2.jpg"))));});
                    case 0 -> image.setOpacity(0);
                }
            }
        }
    }
    /**
     * This method sets the picture of the achieved token
     * @param tokenNo is the number of the token
     * @param image is the image of the token
     */
    private void setAchievedTokenPicture(ImageView image, int tokenNo) {
        int size = gui.getPersonalTokens().size();
        if (size == tokenNo + 1) {
            int points = gui.getPersonalTokens().get(tokenNo).getPoints();
            switch (points) {
                case 8 -> Platform.runLater( () -> {image.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/scoring tokens/scoring_8.jpg"))));});
                case 6 -> Platform.runLater( () -> {image.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/scoring tokens/scoring_6.jpg"))));});
                case 4 -> Platform.runLater( () -> {image.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/scoring tokens/scoring_4.jpg"))));});
                case 2 -> Platform.runLater( () -> {image.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/scoring tokens/scoring_2.jpg"))));});
                case 1 -> Platform.runLater( () -> {image.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/scoring tokens/end game.jpg"))));});
            }
        }
    }

    private void sendMessage(Event event){
        if(messageField != null && !messageField.getText().isEmpty()){
            ArrayList<String> receivers = new ArrayList<>();
            if(choiceBox.getValue().toString().equals("Everyone"))
                receivers.addAll(gui.getPlayersNickname());
            else
                receivers.add(choiceBox.getValue().toString());
            gui.getClientController().sendMessage(gui.getClientNickname(), receivers, messageField.getText());
        }
    }

    public void addMessage(String sender, ArrayList<String> receivers, String text, Timestamp timestamp){
        String receiversText;
        if(receivers.size() > 1 || gui.getPlayersNickname().size() == 1)
            receiversText = "everyone";
        else
            receiversText = receivers.get(0);
        Time time = new Time(timestamp.getTime());
        Label labelHeader = new Label(sender + " to " + receiversText + " at " + time);
        labelHeader.setId("chatHeader");
        labelHeader.setPadding(new Insets(textHeigth, 0, 0, 0));
        textHeigth += 15;
        Label labelMessage = new Label(text);
        labelMessage.setPadding(new Insets(textHeigth, 0, 0, 0));
        labelMessage.setId("chatContent");
        String[] lines = text.split("\r\n|\r|\n");
        textHeigth += 22 + lines.length * 18;
        pane.getChildren().add(labelHeader);
        pane.getChildren().add(labelMessage);
        scrollPane.setContent(pane);
    }

    public void quitGame(Event event){
        MyShelfieClient.voluntaryQuitting(gameId, nickname);
        ArrayList<Object> parameters = new ArrayList<>();
        parameters.add(protocol);
        parameters.add(nickname);
        SceneController.changeScene("QuittingScene.fxml", parameters);
    }

}
