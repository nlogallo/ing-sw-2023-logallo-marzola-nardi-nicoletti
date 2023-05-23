package it.polimi.ingsw.view.GUI.GUIControllers;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.view.GUI.GUIView;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

import static java.lang.String.valueOf;

public class MainStageController implements GenericSceneController, Initializable {

    private GUIView gui;
    private ArrayList<Position> positionsToPick = new ArrayList<>();
    private ArrayList<Position> positionsToOrder = new ArrayList<>();

    private int selectedColumn;

    private int turnPhase;
    /*
    0-> not your turn or not clicked make move;
    1-> select tiles to pick
    2-> select order
    3->select column;
    */

    public MainStageController(GUIView gui){
        this.gui = gui;
    }

    @FXML
    private ImageView board00, board01, board02, board03, board04, board05, board06, board07, board08, board10, board11, board12, board13, board14, board15, board16, board17, board18, board20, board21, board22, board23, board24, board25, board26, board27, board28, board30, board31, board32, board33, board34, board35, board36, board37, board38, board40, board41, board42, board43, board44, board45, board46, board47, board48, board50, board51, board52, board53, board54, board55, board56, board57, board58, board60, board61, board62, board63, board64, board65, board66, board67, board68, board70, board71, board72, board73, board74, board75, board76, board77, board78, board80, board81, board82, board83, board84, board85, board86, board87, board88;
    private ImageView[][] boardImage = new ImageView[9][9];
    @FXML
    private Button otherPlayerButton0, otherPlayerButton1, otherPlayerButton2;
    @FXML
    private Button boardButton03, boardButton04, boardButton13, boardButton14, boardButton15, boardButton22, boardButton23, boardButton24, boardButton25, boardButton26, boardButton31, boardButton32, boardButton33, boardButton34, boardButton35, boardButton36, boardButton37, boardButton38, boardButton40, boardButton41, boardButton42, boardButton43, boardButton44, boardButton45, boardButton46, boardButton47, boardButton48, boardButton50, boardButton51, boardButton52, boardButton53, boardButton54, boardButton55, boardButton56, boardButton57, boardButton62, boardButton63, boardButton64, boardButton65, boardButton66, boardButton73, boardButton74, boardButton75, boardButton84, boardButton85;
    @FXML
    private Button firstColumnButton, secondColumnButton, thirdColumnButton, fourthColumnButton, fifthColumnButton;
    @FXML
    private Button vButton, backButton, makeMoveButton;
    @FXML
    private Button pickedBottomButton, pickedMiddleButton, pickedTopButton;
    @FXML
    private ImageView pickedTileBottom, pickedTileMiddle, pickedTileTop;
    @FXML
    private Label bottomLabel, middleLabel, topLabel;
    @FXML
    private Label turnPhaseLabel;
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




    @Override
    public void setGui(GUIView gui) {
        this.gui = gui;
    }
    @Override
    public void initData(ArrayList<Object> parameters) {}
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(this::initAll);
    }

    private void initAll(){
        setAttributes();
        gui = MyShelfieClient.getGuiView();
        gui.setStageController(this);
        turnPhase = 0;
        setTurnPhaseLabel(turnPhase);


        initButtons();
    }

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

    public void setShelfImage() {
        Shelf shelf = gui.getShelf();

        TileType[][] types = shelf.getShelfTypes();
        for(int i = 0; i<6; i++) {
            for (int j = 0; j<5; j++) {
                if (shelf.getTile(i, j) != null) {
                    setTilePicture(shelfImage[i][j], shelf.getTile(i, j));
                } else shelfImage[i][j].setOpacity(0);
            }
        }
    }

    private void makeMoveButtonClick(Event event) {
        if (gui.getCurrentPlayer() != null  && gui.getCurrentPlayer().equals(gui.getClientNickname()) && turnPhase == 0) {
            turnPhase = 1;
            positionsToPick = new ArrayList<>();
            positionsToOrder = new ArrayList<>();
            setTurnPhaseLabel(turnPhase);
        }
    }

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
                            setTilePicture(pickedTileTop, gui.getBoard().getTilesTable()[positionsToPick.get(2).getRow()][positionsToOrder.get(2).getColumn()]);
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
                        gui.getClientController().moveTiles(positions, selectedColumn + 1, gui.getBoard(), gui.getShelf());

                        bottomLabel.setOpacity(0);
                        middleLabel.setOpacity(0);
                        topLabel.setOpacity(0);
                        pickedTileBottom.setOpacity(0);
                        pickedTileMiddle.setOpacity(0);
                        pickedTileTop.setOpacity(0);
                        setBoardImage();
                        setShelfImage();
                        turnPhase = 0;
                        setTurnPhaseLabel(turnPhase);
                        selectedColumn = -1;

                    }
                }
            }
        }
    }

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


    private void firstColumnButtonClick(Event event){
        if (turnPhase == 3)
            selectedColumn = 0;
    }
    private void secondColumnButtonClick(Event event){
        if (turnPhase == 3)
            selectedColumn = 1;
    }
    private void thirdColumnButtonClick(Event event){
        if (turnPhase == 3)
            selectedColumn = 2;
    }
    private void fourthColumnButtonClick(Event event){
        if (turnPhase == 3)
            selectedColumn = 3;
    }
    private void fifthColumnButtonClick(Event event){
        if (turnPhase == 3)
            selectedColumn = 4;
    }

    private void otherPlayerButtonClick0(Event event) {}
    private void otherPlayerButtonClick1(Event event) {}
    private void otherPlayerButtonClick2(Event event) {}

    public void setGoalsPicture(){
        setCommonGoalPicture(commonGoal1, gui.getCommonGoals().get(0).getId());
        setCommonGoalPicture(commonGoal2, gui.getCommonGoals().get(1).getId());
        setPersonalGoalPicture(personalGoal, gui.getPersonalGoal().getId());
    }
    public void setTokensPicture(){
        setTokenPicture(commonGoal1Token, 1);
        setTokenPicture(commonGoal2Token, 2);
        setTokenPicture(boardEndGameToken, 0);
        setAchievedTokenPicture(tokenAchieved0, 0);
        setAchievedTokenPicture(tokenAchieved1, 1);
        setAchievedTokenPicture(tokenAchieved2, 2);
    }

    public void setSeatPicture() {
        if (gui.isSeat() == true)
            seat.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/misc/firstplayertoken.png"))));;
    }

    private void boardButtonClick03 (Event event){
        if (turnPhase == 1 && gui.getShelf().freeSpots() > positionsToPick.size()) {
            if (board03.getOpacity() == 1.0) {
                if (positionsToPick.size() >= 0 && positionsToPick.size() < 3) {
                    if (gui.getBoard().canPull(0, 3)) {
                        positionsToPick.add(new Position(0, 3));
                        if (gui.getBoard().areAligned(positionsToPick)) {
                            board03.setOpacity(0.3);
                        } else positionsToPick.remove(positionsToPick.size() - 1);
                    }
                }
            }
        }
    }
    private void boardButtonClick04 (Event event){
        if (turnPhase == 1 && gui.getShelf().freeSpots() > positionsToPick.size()) {
            if (board04.getOpacity() == 1.0) {
                if (positionsToPick.size() >= 0 && positionsToPick.size() < 3) {
                    if (gui.getBoard().canPull(0, 4)) {
                        positionsToPick.add(new Position(0, 4));
                        if (gui.getBoard().areAligned(positionsToPick)) {
                            board04.setOpacity(0.3);
                        } else positionsToPick.remove(positionsToPick.size() - 1);
                    }
                }
            }
        }
    }
    private void boardButtonClick13 (Event event){
        if (turnPhase == 1 && gui.getShelf().freeSpots() > positionsToPick.size()) {
            if (board13.getOpacity() == 1.0) {
                if (positionsToPick.size() >= 0 && positionsToPick.size() < 3) {
                    if (gui.getBoard().canPull(1, 3)) {
                        positionsToPick.add(new Position(1, 3));
                        if (gui.getBoard().areAligned(positionsToPick)) {
                            board13.setOpacity(0.3);
                        } else positionsToPick.remove(positionsToPick.size() - 1);
                    }
                }
            }
        }
    }
    private void boardButtonClick14 (Event event){
        if (turnPhase == 1 && gui.getShelf().freeSpots() > positionsToPick.size()) {
            if (board14.getOpacity() == 1.0) {
                if (positionsToPick.size() >= 0 && positionsToPick.size() < 3) {
                    if (gui.getBoard().canPull(1, 4)) {
                        positionsToPick.add(new Position(1, 4));
                        if (gui.getBoard().areAligned(positionsToPick)) {
                            board14.setOpacity(0.3);
                        } else positionsToPick.remove(positionsToPick.size() - 1);
                    }
                }
            }
        }
    }
    private void boardButtonClick15 (Event event){
        if (turnPhase == 1 && gui.getShelf().freeSpots() > positionsToPick.size()) {
            if (board15.getOpacity() == 1.0) {
                if (positionsToPick.size() >= 0 && positionsToPick.size() < 3) {
                    if (gui.getBoard().canPull(1, 5)) {
                        positionsToPick.add(new Position(1, 5));
                        if (gui.getBoard().areAligned(positionsToPick)) {
                            board15.setOpacity(0.3);
                        } else positionsToPick.remove(positionsToPick.size() - 1);
                    }
                }
            }
        }
    }
    private void boardButtonClick22 (Event event){
        if (turnPhase == 1 && gui.getShelf().freeSpots() > positionsToPick.size()) {
            if (board22.getOpacity() == 1.0) {
                if (positionsToPick.size() >= 0 && positionsToPick.size() < 3) {
                    if (gui.getBoard().canPull(2, 2)) {
                        positionsToPick.add(new Position(2, 2));
                        if (gui.getBoard().areAligned(positionsToPick)) {
                            board22.setOpacity(0.3);
                        } else positionsToPick.remove(positionsToPick.size() - 1);
                    }
                }
            }
        }
    }
    private void boardButtonClick23 (Event event){
        if (turnPhase == 1 && gui.getShelf().freeSpots() > positionsToPick.size()) {
            if (board23.getOpacity() == 1.0) {
                if (positionsToPick.size() >= 0 && positionsToPick.size() < 3) {
                    if (gui.getBoard().canPull(2, 3)) {
                        positionsToPick.add(new Position(2, 3));
                        if (gui.getBoard().areAligned(positionsToPick)) {
                            board23.setOpacity(0.3);
                        } else positionsToPick.remove(positionsToPick.size() - 1);
                    }
                }
            }
        }
    }
    private void boardButtonClick24 (Event event){
        if (turnPhase == 1 && gui.getShelf().freeSpots() > positionsToPick.size()) {
            if (board24.getOpacity() == 1.0) {
                if (positionsToPick.size() >= 0 && positionsToPick.size() < 3) {
                    if (gui.getBoard().canPull(2, 4)) {
                        positionsToPick.add(new Position(2, 4));
                        if (gui.getBoard().areAligned(positionsToPick)) {
                            board24.setOpacity(0.3);
                        } else positionsToPick.remove(positionsToPick.size() - 1);
                    }
                }
            }
        }
    }
    private void boardButtonClick25 (Event event){
        if (turnPhase == 1 && gui.getShelf().freeSpots() > positionsToPick.size()) {
            if (board25.getOpacity() == 1.0) {
                if (positionsToPick.size() >= 0 && positionsToPick.size() < 3) {
                    if (gui.getBoard().canPull(2, 5)) {
                        positionsToPick.add(new Position(2, 5));
                        if (gui.getBoard().areAligned(positionsToPick)) {
                            board25.setOpacity(0.3);
                        } else positionsToPick.remove(positionsToPick.size() - 1);
                    }
                }
            }
        }
    }
    private void boardButtonClick26 (Event event){
        if (turnPhase == 1 && gui.getShelf().freeSpots() > positionsToPick.size()) {
            if (board26.getOpacity() == 1.0) {
                if (positionsToPick.size() >= 0 && positionsToPick.size() < 3) {
                    if (gui.getBoard().canPull(2, 6)) {
                        positionsToPick.add(new Position(2, 6));
                        if (gui.getBoard().areAligned(positionsToPick)) {
                            board26.setOpacity(0.3);
                        } else positionsToPick.remove(positionsToPick.size() - 1);
                    }
                }
            }
        }
    }
    private void boardButtonClick31 (Event event){
        if (turnPhase == 1 && gui.getShelf().freeSpots() > positionsToPick.size()) {
            if (board31.getOpacity() == 1.0) {
                if (positionsToPick.size() >= 0 && positionsToPick.size() < 3) {
                    if (gui.getBoard().canPull(3, 1)) {
                        positionsToPick.add(new Position(3, 1));
                        if (gui.getBoard().areAligned(positionsToPick)) {
                            board31.setOpacity(0.3);
                        } else positionsToPick.remove(positionsToPick.size() - 1);
                    }
                }
            }
        }
    }
    private void boardButtonClick32 (Event event){
        if (turnPhase == 1 && gui.getShelf().freeSpots() > positionsToPick.size()) {
            if (board32.getOpacity() == 1.0) {
                if (positionsToPick.size() >= 0 && positionsToPick.size() < 3) {
                    if (gui.getBoard().canPull(3, 2)) {
                        positionsToPick.add(new Position(3, 2));
                        if (gui.getBoard().areAligned(positionsToPick)) {
                            board32.setOpacity(0.3);
                        } else positionsToPick.remove(positionsToPick.size() - 1);
                    }
                }
            }
        }
    }
    private void boardButtonClick33 (Event event){
        if (turnPhase == 1 && gui.getShelf().freeSpots() > positionsToPick.size()) {
            if (board33.getOpacity() == 1.0) {
                if (positionsToPick.size() >= 0 && positionsToPick.size() < 3) {
                    if (gui.getBoard().canPull(3, 3)) {
                        positionsToPick.add(new Position(3, 3));
                        if (gui.getBoard().areAligned(positionsToPick)) {
                            board33.setOpacity(0.3);
                        } else positionsToPick.remove(positionsToPick.size() - 1);
                    }
                }
            }
        }
    }
    private void boardButtonClick34 (Event event){
        if (turnPhase == 1 && gui.getShelf().freeSpots() > positionsToPick.size()) {
            if (board34.getOpacity() == 1.0) {
                if (positionsToPick.size() >= 0 && positionsToPick.size() < 3) {
                    if (gui.getBoard().canPull(3, 4)) {
                        positionsToPick.add(new Position(3, 4));
                        if (gui.getBoard().areAligned(positionsToPick)) {
                            board34.setOpacity(0.3);
                        } else positionsToPick.remove(positionsToPick.size() - 1);
                    }
                }
            }
        }
    }
    private void boardButtonClick35 (Event event){
        if (turnPhase == 1 && gui.getShelf().freeSpots() > positionsToPick.size()) {
            if (board35.getOpacity() == 1.0) {
                if (positionsToPick.size() >= 0 && positionsToPick.size() < 3) {
                    if (gui.getBoard().canPull(3, 5)) {
                        positionsToPick.add(new Position(3, 5));
                        if (gui.getBoard().areAligned(positionsToPick)) {
                            board35.setOpacity(0.3);
                        } else positionsToPick.remove(positionsToPick.size() - 1);
                    }
                }
            }
        }
    }
    private void boardButtonClick36 (Event event){
        if (turnPhase == 1 && gui.getShelf().freeSpots() > positionsToPick.size()) {
            if (board36.getOpacity() == 1.0) {
                if (positionsToPick.size() >= 0 && positionsToPick.size() < 3) {
                    if (gui.getBoard().canPull(3, 6)) {
                        positionsToPick.add(new Position(3, 6));
                        if (gui.getBoard().areAligned(positionsToPick)) {
                            board36.setOpacity(0.3);
                        } else positionsToPick.remove(positionsToPick.size() - 1);
                    }
                }
            }
        }
    }
    private void boardButtonClick37 (Event event){
        if (turnPhase == 1 && gui.getShelf().freeSpots() > positionsToPick.size()) {
            if (board37.getOpacity() == 1.0) {
                if (positionsToPick.size() >= 0 && positionsToPick.size() < 3) {
                    if (gui.getBoard().canPull(3, 7)) {
                        positionsToPick.add(new Position(3, 7));
                        if (gui.getBoard().areAligned(positionsToPick)) {
                            board37.setOpacity(0.3);
                        } else positionsToPick.remove(positionsToPick.size() - 1);
                    }
                }
            }
        }
    }
    private void boardButtonClick38 (Event event){
        if (turnPhase == 1 && gui.getShelf().freeSpots() > positionsToPick.size()) {
            if (board38.getOpacity() == 1.0) {
                if (positionsToPick.size() >= 0 && positionsToPick.size() < 3) {
                    if (gui.getBoard().canPull(3, 8)) {
                        positionsToPick.add(new Position(3, 8));
                        if (gui.getBoard().areAligned(positionsToPick)) {
                            board38.setOpacity(0.3);
                        } else positionsToPick.remove(positionsToPick.size() - 1);
                    }
                }
            }
        }
    }
    private void boardButtonClick40 (Event event){
        if (turnPhase == 1 && gui.getShelf().freeSpots() > positionsToPick.size()) {
            if (board40.getOpacity() == 1.0) {
                if (positionsToPick.size() >= 0 && positionsToPick.size() < 3) {
                    if (gui.getBoard().canPull(4, 0)) {
                        positionsToPick.add(new Position(4, 0));
                        if (gui.getBoard().areAligned(positionsToPick)) {
                            board40.setOpacity(0.3);
                        } else positionsToPick.remove(positionsToPick.size() - 1);
                    }
                }
            }
        }
    }
    private void boardButtonClick41 (Event event){
        if (turnPhase == 1 && gui.getShelf().freeSpots() > positionsToPick.size()) {
            if (board41.getOpacity() == 1.0) {
                if (positionsToPick.size() >= 0 && positionsToPick.size() < 3) {
                    if (gui.getBoard().canPull(4, 1)) {
                        positionsToPick.add(new Position(4, 1));
                        if (gui.getBoard().areAligned(positionsToPick)) {
                            board41.setOpacity(0.3);
                        } else positionsToPick.remove(positionsToPick.size() - 1);
                    }
                }
            }
        }
    }
    private void boardButtonClick42 (Event event){
        if (turnPhase == 1 && gui.getShelf().freeSpots() > positionsToPick.size()) {
            if (board42.getOpacity() == 1.0) {
                if (positionsToPick.size() >= 0 && positionsToPick.size() < 3) {
                    if (gui.getBoard().canPull(4, 2)) {
                        positionsToPick.add(new Position(4, 2));
                        if (gui.getBoard().areAligned(positionsToPick)) {
                            board42.setOpacity(0.3);
                        } else positionsToPick.remove(positionsToPick.size() - 1);
                    }
                }
            }
        }
    }
    private void boardButtonClick43 (Event event){
        if (turnPhase == 1 && gui.getShelf().freeSpots() > positionsToPick.size()) {
            if (board43.getOpacity() == 1.0) {
                if (positionsToPick.size() >= 0 && positionsToPick.size() < 3) {
                    if (gui.getBoard().canPull(4, 3)) {
                        positionsToPick.add(new Position(4, 3));
                        if (gui.getBoard().areAligned(positionsToPick)) {
                            board43.setOpacity(0.3);
                        } else positionsToPick.remove(positionsToPick.size() - 1);
                    }
                }
            }
        }
    }
    private void boardButtonClick44 (Event event){
        if (turnPhase == 1 && gui.getShelf().freeSpots() > positionsToPick.size()) {
            if (board44.getOpacity() == 1.0) {
                if (positionsToPick.size() >= 0 && positionsToPick.size() < 3) {
                    if (gui.getBoard().canPull(4, 4)) {
                        positionsToPick.add(new Position(4, 4));
                        if (gui.getBoard().areAligned(positionsToPick)) {
                            board44.setOpacity(0.3);
                        } else positionsToPick.remove(positionsToPick.size() - 1);
                    }
                }
            }
        }
    }
    private void boardButtonClick45 (Event event){
        if (turnPhase == 1 && gui.getShelf().freeSpots() > positionsToPick.size()) {
            if (board45.getOpacity() == 1.0) {
                if (positionsToPick.size() >= 0 && positionsToPick.size() < 3) {
                    if (gui.getBoard().canPull(4, 5)) {
                        positionsToPick.add(new Position(4, 5));
                        if (gui.getBoard().areAligned(positionsToPick)) {
                            board45.setOpacity(0.3);
                        } else positionsToPick.remove(positionsToPick.size() - 1);
                    }
                }
            }
        }
    }
    private void boardButtonClick46 (Event event){
        if (turnPhase == 1 && gui.getShelf().freeSpots() > positionsToPick.size()) {
            if (board46.getOpacity() == 1.0) {
                if (positionsToPick.size() >= 0 && positionsToPick.size() < 3) {
                    if (gui.getBoard().canPull(4, 6)) {
                        positionsToPick.add(new Position(4, 6));
                        if (gui.getBoard().areAligned(positionsToPick)) {
                            board46.setOpacity(0.3);
                        } else positionsToPick.remove(positionsToPick.size() - 1);
                    }
                }
            }
        }
    }
    private void boardButtonClick47 (Event event){
        if (turnPhase == 1 && gui.getShelf().freeSpots() > positionsToPick.size()) {
            if (board47.getOpacity() == 1.0) {
                if (positionsToPick.size() >= 0 && positionsToPick.size() < 3) {
                    if (gui.getBoard().canPull(4, 7)) {
                        positionsToPick.add(new Position(4, 7));
                        if (gui.getBoard().areAligned(positionsToPick)) {
                            board47.setOpacity(0.3);
                        } else positionsToPick.remove(positionsToPick.size() - 1);
                    }
                }
            }
        }
    }
    private void boardButtonClick48 (Event event){
        if (turnPhase == 1 && gui.getShelf().freeSpots() > positionsToPick.size()) {
            if (board48.getOpacity() == 1.0) {
                if (positionsToPick.size() >= 0 && positionsToPick.size() < 3) {
                    if (gui.getBoard().canPull(4, 8)) {
                        positionsToPick.add(new Position(4, 8));
                        if (gui.getBoard().areAligned(positionsToPick)) {
                            board48.setOpacity(0.3);
                        } else positionsToPick.remove(positionsToPick.size() - 1);
                    }
                }
            }
        }
    }
    private void boardButtonClick50 (Event event){
        if (turnPhase == 1 && gui.getShelf().freeSpots() > positionsToPick.size()) {
            if (board50.getOpacity() == 1.0) {
                if (positionsToPick.size() >= 0 && positionsToPick.size() < 3) {
                    if (gui.getBoard().canPull(5, 0)) {
                        positionsToPick.add(new Position(5, 0));
                        if (gui.getBoard().areAligned(positionsToPick)) {
                            board50.setOpacity(0.3);
                        } else positionsToPick.remove(positionsToPick.size() - 1);
                    }
                }
            }
        }
    }
    private void boardButtonClick51 (Event event){
        if (turnPhase == 1 && gui.getShelf().freeSpots() > positionsToPick.size()) {
            if (board51.getOpacity() == 1.0) {
                if (positionsToPick.size() >= 0 && positionsToPick.size() < 3) {
                    if (gui.getBoard().canPull(5, 1)) {
                        positionsToPick.add(new Position(5, 1));
                        if (gui.getBoard().areAligned(positionsToPick)) {
                            board51.setOpacity(0.3);
                        } else positionsToPick.remove(positionsToPick.size() - 1);
                    }
                }
            }
        }
    }
    private void boardButtonClick52 (Event event){
        if (turnPhase == 1 && gui.getShelf().freeSpots() > positionsToPick.size()) {
            if (board52.getOpacity() == 1.0) {
                if (positionsToPick.size() >= 0 && positionsToPick.size() < 3) {
                    if (gui.getBoard().canPull(5, 2)) {
                        positionsToPick.add(new Position(5, 2));
                        if (gui.getBoard().areAligned(positionsToPick)) {
                            board52.setOpacity(0.3);
                        } else positionsToPick.remove(positionsToPick.size() - 1);
                    }
                }
            }
        }
    }
    private void boardButtonClick53 (Event event){
        if (turnPhase == 1 && gui.getShelf().freeSpots() > positionsToPick.size()) {
            if (board53.getOpacity() == 1.0) {
                if (positionsToPick.size() >= 0 && positionsToPick.size() < 3) {
                    if (gui.getBoard().canPull(5, 3)) {
                        positionsToPick.add(new Position(5, 3));
                        if (gui.getBoard().areAligned(positionsToPick)) {
                            board53.setOpacity(0.3);
                        } else positionsToPick.remove(positionsToPick.size() - 1);
                    }
                }
            }
        }
    }
    private void boardButtonClick54 (Event event){
        if (turnPhase == 1 && gui.getShelf().freeSpots() > positionsToPick.size()) {
            if (board54.getOpacity() == 1.0) {
                if (positionsToPick.size() >= 0 && positionsToPick.size() < 3) {
                    if (gui.getBoard().canPull(5, 4)) {
                        positionsToPick.add(new Position(5, 4));
                        if (gui.getBoard().areAligned(positionsToPick)) {
                            board54.setOpacity(0.3);
                        } else positionsToPick.remove(positionsToPick.size() - 1);
                    }
                }
            }
        }
    }
    private void boardButtonClick55 (Event event){
        if (turnPhase == 1 && gui.getShelf().freeSpots() > positionsToPick.size()) {
            if (board55.getOpacity() == 1.0) {
                if (positionsToPick.size() >= 0 && positionsToPick.size() < 3) {
                    if (gui.getBoard().canPull(5, 5)) {
                        positionsToPick.add(new Position(5, 5));
                        if (gui.getBoard().areAligned(positionsToPick)) {
                            board55.setOpacity(0.3);
                        } else positionsToPick.remove(positionsToPick.size() - 1);
                    }
                }
            }
        }
    }
    private void boardButtonClick56 (Event event){
        if (turnPhase == 1 && gui.getShelf().freeSpots() > positionsToPick.size()) {
            if (board56.getOpacity() == 1.0) {
                if (positionsToPick.size() >= 0 && positionsToPick.size() < 3) {
                    if (gui.getBoard().canPull(5, 6)) {
                        positionsToPick.add(new Position(5, 6));
                        if (gui.getBoard().areAligned(positionsToPick)) {
                            board56.setOpacity(0.3);
                        } else positionsToPick.remove(positionsToPick.size() - 1);
                    }
                }
            }
        }
    }
    private void boardButtonClick57 (Event event){
        if (turnPhase == 1 && gui.getShelf().freeSpots() > positionsToPick.size()) {
            if (board57.getOpacity() == 1.0) {
                if (positionsToPick.size() >= 0 && positionsToPick.size() < 3) {
                    if (gui.getBoard().canPull(5, 7)) {
                        positionsToPick.add(new Position(5, 7));
                        if (gui.getBoard().areAligned(positionsToPick)) {
                            board57.setOpacity(0.3);
                        } else positionsToPick.remove(positionsToPick.size() - 1);
                    }
                }
            }
        }
    }
    private void boardButtonClick62 (Event event){
        if (turnPhase == 1 && gui.getShelf().freeSpots() > positionsToPick.size()) {
            if (board62.getOpacity() == 1.0) {
                if (positionsToPick.size() >= 0 && positionsToPick.size() < 3) {
                    if (gui.getBoard().canPull(6, 2)) {
                        positionsToPick.add(new Position(6, 2));
                        if (gui.getBoard().areAligned(positionsToPick)) {
                            board62.setOpacity(0.3);
                        } else positionsToPick.remove(positionsToPick.size() - 1);
                    }
                }
            }
        }
    }
    private void boardButtonClick63 (Event event){
        if (turnPhase == 1 && gui.getShelf().freeSpots() > positionsToPick.size()) {
            if (board63.getOpacity() == 1.0) {
                if (positionsToPick.size() >= 0 && positionsToPick.size() < 3) {
                    if (gui.getBoard().canPull(6, 3)) {
                        positionsToPick.add(new Position(6, 3));
                        if (gui.getBoard().areAligned(positionsToPick)) {
                            board63.setOpacity(0.3);
                        } else positionsToPick.remove(positionsToPick.size() - 1);
                    }
                }
            }
        }
    }
    private void boardButtonClick64 (Event event){
        if (turnPhase == 1 && gui.getShelf().freeSpots() > positionsToPick.size()) {
            if (board64.getOpacity() == 1.0) {
                if (positionsToPick.size() >= 0 && positionsToPick.size() < 3) {
                    if (gui.getBoard().canPull(6, 4)) {
                        positionsToPick.add(new Position(6, 4));
                        if (gui.getBoard().areAligned(positionsToPick)) {
                            board64.setOpacity(0.3);
                        } else positionsToPick.remove(positionsToPick.size() - 1);
                    }
                }
            }
        }
    }
    private void boardButtonClick65 (Event event){
        if (turnPhase == 1 && gui.getShelf().freeSpots() > positionsToPick.size()) {
            if (board65.getOpacity() == 1.0) {
                if (positionsToPick.size() >= 0 && positionsToPick.size() < 3) {
                    if (gui.getBoard().canPull(6, 5)) {
                        positionsToPick.add(new Position(6, 5));
                        if (gui.getBoard().areAligned(positionsToPick)) {
                            board65.setOpacity(0.3);
                        } else positionsToPick.remove(positionsToPick.size() - 1);
                    }
                }
            }
        }
    }
    private void boardButtonClick66 (Event event){
        if (turnPhase == 1 && gui.getShelf().freeSpots() > positionsToPick.size()) {
            if (board66.getOpacity() == 1.0) {
                if (positionsToPick.size() >= 0 && positionsToPick.size() < 3) {
                    if (gui.getBoard().canPull(6, 6)) {
                        positionsToPick.add(new Position(6, 6));
                        if (gui.getBoard().areAligned(positionsToPick)) {
                            board66.setOpacity(0.3);
                        } else positionsToPick.remove(positionsToPick.size() - 1);
                    }
                }
            }
        }
    }
    private void boardButtonClick73 (Event event){
        if (turnPhase == 1 && gui.getShelf().freeSpots() > positionsToPick.size()) {
            if (board73.getOpacity() == 1.0) {
                if (positionsToPick.size() >= 0 && positionsToPick.size() < 3) {
                    if (gui.getBoard().canPull(7, 3)) {
                        positionsToPick.add(new Position(7, 3));
                        if (gui.getBoard().areAligned(positionsToPick)) {
                            board73.setOpacity(0.3);
                        } else positionsToPick.remove(positionsToPick.size() - 1);
                    }
                }
            }
        }
    }
    private void boardButtonClick74 (Event event){
        if (turnPhase == 1 && gui.getShelf().freeSpots() > positionsToPick.size()) {
            if (board74.getOpacity() == 1.0) {
                if (positionsToPick.size() >= 0 && positionsToPick.size() < 3) {
                    if (gui.getBoard().canPull(7, 4)) {
                        positionsToPick.add(new Position(7, 4));
                        if (gui.getBoard().areAligned(positionsToPick)) {
                            board74.setOpacity(0.3);
                        } else positionsToPick.remove(positionsToPick.size() - 1);
                    }
                }
            }
        }
    }
    private void boardButtonClick75 (Event event){
        if (turnPhase == 1 && gui.getShelf().freeSpots() > positionsToPick.size()) {
            if (board75.getOpacity() == 1.0) {
                if (positionsToPick.size() >= 0 && positionsToPick.size() < 3) {
                    if (gui.getBoard().canPull(7, 5)) {
                        positionsToPick.add(new Position(7, 5));
                        if (gui.getBoard().areAligned(positionsToPick)) {
                            board75.setOpacity(0.3);
                        } else positionsToPick.remove(positionsToPick.size() - 1);
                    }
                }
            }
        }
    }
    private void boardButtonClick84 (Event event){
        if (turnPhase == 1 && gui.getShelf().freeSpots() > positionsToPick.size()) {
            if (board84.getOpacity() == 1.0) {
                if (positionsToPick.size() >= 0 && positionsToPick.size() < 3) {
                    if (gui.getBoard().canPull(8, 4)) {
                        positionsToPick.add(new Position(8, 4));
                        if (gui.getBoard().areAligned(positionsToPick)) {
                            board84.setOpacity(0.3);
                        } else positionsToPick.remove(positionsToPick.size() - 1);
                    }
                }
            }
        }
    }
    private void boardButtonClick85 (Event event){
        if (turnPhase == 1 && gui.getShelf().freeSpots() > positionsToPick.size()) {
            if (board85.getOpacity() == 1.0) {
                if (positionsToPick.size() >= 0 && positionsToPick.size() < 3) {
                    if (gui.getBoard().canPull(8, 5)) {
                        positionsToPick.add(new Position(8, 5));
                        if (gui.getBoard().areAligned(positionsToPick)) {
                            board85.setOpacity(0.3);
                        } else positionsToPick.remove(positionsToPick.size() - 1);
                    }
                }
            }
        }
    }




    private void initButtons() {
        if (gui.getCurrentPlayer() != null  && !gui.getCurrentPlayer().equals(gui.getClientNickname()) ) {
            //disable buttons you cant click if it isnt your turn
        }
        makeMoveButton.addEventHandler(MouseEvent.MOUSE_RELEASED, this::makeMoveButtonClick);
        backButton.addEventHandler(MouseEvent.MOUSE_RELEASED, this::backButtonClick);
        vButton.addEventHandler(MouseEvent.MOUSE_RELEASED, this::vButtonClick);

        firstColumnButton.addEventHandler(MouseEvent.MOUSE_RELEASED, this::firstColumnButtonClick);
        secondColumnButton.addEventHandler(MouseEvent.MOUSE_RELEASED, this::secondColumnButtonClick);
        thirdColumnButton.addEventHandler(MouseEvent.MOUSE_RELEASED, this::thirdColumnButtonClick);
        fourthColumnButton.addEventHandler(MouseEvent.MOUSE_RELEASED, this::fourthColumnButtonClick);
        fifthColumnButton.addEventHandler(MouseEvent.MOUSE_RELEASED, this::fifthColumnButtonClick);

        boardButton03.addEventHandler(MouseEvent.MOUSE_RELEASED, this::boardButtonClick03);
        boardButton04.addEventHandler(MouseEvent.MOUSE_RELEASED, this::boardButtonClick04);
        boardButton13.addEventHandler(MouseEvent.MOUSE_RELEASED, this::boardButtonClick13);
        boardButton14.addEventHandler(MouseEvent.MOUSE_RELEASED, this::boardButtonClick14);
        boardButton15.addEventHandler(MouseEvent.MOUSE_RELEASED, this::boardButtonClick15);
        boardButton22.addEventHandler(MouseEvent.MOUSE_RELEASED, this::boardButtonClick22);
        boardButton23.addEventHandler(MouseEvent.MOUSE_RELEASED, this::boardButtonClick23);
        boardButton24.addEventHandler(MouseEvent.MOUSE_RELEASED, this::boardButtonClick24);
        boardButton25.addEventHandler(MouseEvent.MOUSE_RELEASED, this::boardButtonClick25);
        boardButton26.addEventHandler(MouseEvent.MOUSE_RELEASED, this::boardButtonClick26);
        boardButton31.addEventHandler(MouseEvent.MOUSE_RELEASED, this::boardButtonClick31);
        boardButton32.addEventHandler(MouseEvent.MOUSE_RELEASED, this::boardButtonClick32);
        boardButton33.addEventHandler(MouseEvent.MOUSE_RELEASED, this::boardButtonClick33);
        boardButton34.addEventHandler(MouseEvent.MOUSE_RELEASED, this::boardButtonClick34);
        boardButton35.addEventHandler(MouseEvent.MOUSE_RELEASED, this::boardButtonClick35);
        boardButton36.addEventHandler(MouseEvent.MOUSE_RELEASED, this::boardButtonClick36);
        boardButton37.addEventHandler(MouseEvent.MOUSE_RELEASED, this::boardButtonClick37);
        boardButton38.addEventHandler(MouseEvent.MOUSE_RELEASED, this::boardButtonClick38);
        boardButton40.addEventHandler(MouseEvent.MOUSE_RELEASED, this::boardButtonClick40);
        boardButton41.addEventHandler(MouseEvent.MOUSE_RELEASED, this::boardButtonClick41);
        boardButton42.addEventHandler(MouseEvent.MOUSE_RELEASED, this::boardButtonClick42);
        boardButton43.addEventHandler(MouseEvent.MOUSE_RELEASED, this::boardButtonClick43);
        boardButton44.addEventHandler(MouseEvent.MOUSE_RELEASED, this::boardButtonClick44);
        boardButton45.addEventHandler(MouseEvent.MOUSE_RELEASED, this::boardButtonClick45);
        boardButton46.addEventHandler(MouseEvent.MOUSE_RELEASED, this::boardButtonClick46);
        boardButton47.addEventHandler(MouseEvent.MOUSE_RELEASED, this::boardButtonClick47);
        boardButton48.addEventHandler(MouseEvent.MOUSE_RELEASED, this::boardButtonClick48);
        boardButton50.addEventHandler(MouseEvent.MOUSE_RELEASED, this::boardButtonClick50);
        boardButton51.addEventHandler(MouseEvent.MOUSE_RELEASED, this::boardButtonClick51);
        boardButton52.addEventHandler(MouseEvent.MOUSE_RELEASED, this::boardButtonClick52);
        boardButton53.addEventHandler(MouseEvent.MOUSE_RELEASED, this::boardButtonClick53);
        boardButton54.addEventHandler(MouseEvent.MOUSE_RELEASED, this::boardButtonClick54);
        boardButton55.addEventHandler(MouseEvent.MOUSE_RELEASED, this::boardButtonClick55);
        boardButton56.addEventHandler(MouseEvent.MOUSE_RELEASED, this::boardButtonClick56);
        boardButton57.addEventHandler(MouseEvent.MOUSE_RELEASED, this::boardButtonClick57);
        boardButton62.addEventHandler(MouseEvent.MOUSE_RELEASED, this::boardButtonClick62);
        boardButton63.addEventHandler(MouseEvent.MOUSE_RELEASED, this::boardButtonClick63);
        boardButton64.addEventHandler(MouseEvent.MOUSE_RELEASED, this::boardButtonClick64);
        boardButton65.addEventHandler(MouseEvent.MOUSE_RELEASED, this::boardButtonClick65);
        boardButton66.addEventHandler(MouseEvent.MOUSE_RELEASED, this::boardButtonClick66);
        boardButton73.addEventHandler(MouseEvent.MOUSE_RELEASED, this::boardButtonClick73);
        boardButton74.addEventHandler(MouseEvent.MOUSE_RELEASED, this::boardButtonClick74);
        boardButton75.addEventHandler(MouseEvent.MOUSE_RELEASED, this::boardButtonClick75);
        boardButton84.addEventHandler(MouseEvent.MOUSE_RELEASED, this::boardButtonClick84);
        boardButton85.addEventHandler(MouseEvent.MOUSE_RELEASED, this::boardButtonClick85);

        pickedBottomButton.addEventHandler(MouseEvent.MOUSE_RELEASED, this::pickedBottomButtonClick);
        pickedMiddleButton.addEventHandler(MouseEvent.MOUSE_RELEASED, this::pickedMiddleButtonClick);
        pickedTopButton.addEventHandler(MouseEvent.MOUSE_RELEASED, this::pickedTopButtonClick);

        otherPlayerButton0.addEventHandler(MouseEvent.MOUSE_RELEASED, this::otherPlayerButtonClick0);
        otherPlayerButton1.addEventHandler(MouseEvent.MOUSE_RELEASED, this::otherPlayerButtonClick1);
        otherPlayerButton2.addEventHandler(MouseEvent.MOUSE_RELEASED, this::otherPlayerButtonClick2);
    }

    private void setTurnPhaseLabel (int phase) {
        switch(phase) {
            case 0 -> turnPhaseLabel.setText("When it's your turn tap on 'Make move' to pick tiles");

            case 1 -> {
                String maxTiles;
                if (gui.getShelf().freeSpots() < 3 ) {
                    maxTiles = String.valueOf(gui.getShelf().freeSpots());
                } else {maxTiles = "3";}
                turnPhaseLabel.setText("You can pick no more than " + maxTiles + " aligned Tiles that have at least one free edge, and tap on V");
            }
             case 2 -> {
                turnPhaseLabel.setText("Tap on the tiles to choose the order to put them in the shelf and press V");
             }
             case 3 -> {
                turnPhaseLabel.setText("Chose the column and tap on V");
             }
        }
    }

    private void setTilePicture (ImageView image, Tile tile) {
        TileType type = tile.getType();
        int imageType = tile.getImageType();
        if (tile == null) {
            image.setOpacity(0);
        } else {
            switch (type) {
                case PLANT -> {
                    switch (imageType) {
                        case 0 -> image.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/item tiles/Piante1.1.png"))));
                        case 1 -> image.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/item tiles/Piante1.2.png"))));
                        case 2 -> image.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/item tiles/Piante1.3.png"))));
                    }
                    image.setOpacity(1);
                }
                case TROPHY -> {
                    switch (imageType) {
                        case 0 -> image.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/item tiles/Trofei1.1.png"))));
                        case 1 -> image.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/item tiles/Trofei1.2.png"))));
                        case 2 -> image.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/item tiles/Trofei1.3.png"))));
                    }
                    image.setOpacity(1);
                }
                case GAME -> {
                    switch (imageType) {
                        case 0 -> image.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/item tiles/Giochi1.1.png"))));
                        case 1 -> image.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/item tiles/Giochi1.2.png"))));
                        case 2 -> image.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/item tiles/Giochi1.3.png"))));
                    }
                    image.setOpacity(1);
                }
                case CAT -> {
                    switch (imageType) {
                        case 0 -> image.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/item tiles/Gatti1.1.png"))));
                        case 1 -> image.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/item tiles/Gatti1.2.png"))));
                        case 2 -> image.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/item tiles/Gatti1.3.png"))));
                    }
                    image.setOpacity(1);
                }
                case BOOK -> {
                    switch (imageType) {
                        case 0 -> image.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/item tiles/Libri1.1.png"))));
                        case 1 -> image.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/item tiles/Libri1.2.png"))));
                        case 2 -> image.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/item tiles/Libri1.3.png"))));
                    }
                    image.setOpacity(1);
                }
                case FRAME -> {
                    switch (imageType) {
                        case 0 -> image.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/item tiles/Cornici1.1.png"))));
                        case 1 -> image.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/item tiles/Cornici1.2.png"))));
                        case 2 -> image.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/item tiles/Cornici1.3.png"))));
                    }
                    image.setOpacity(1);
                }
            }
        }
    }

    private void setCommonGoalPicture(ImageView image, int id) {
        switch (id) {
            case 0 -> image.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/common goal cards/4.jpg"))));
            case 1 -> image.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/common goal cards/11.jpg"))));
            case 2 -> image.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/common goal cards/3.jpg"))));
            case 3 -> image.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/common goal cards/7.jpg"))));
            case 4 -> image.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/common goal cards/8.jpg"))));
            case 5 -> image.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/common goal cards/2.jpg"))));
            case 6 -> image.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/common goal cards/1.jpg"))));
            case 7 -> image.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/common goal cards/6.jpg"))));
            case 8 -> image.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/common goal cards/5.jpg"))));
            case 9 -> image.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/common goal cards/10.jpg"))));
            case 10 -> image.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/common goal cards/9.jpg"))));
            case 11 -> image.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/common goal cards/12.jpg"))));
        }
    }

    private void setPersonalGoalPicture(ImageView image, int id) {
        switch (id) {
            case 0 -> image.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/personal goal cards/Personal_Goals.png"))));
            case 1 -> image.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/personal goal cards/Personal_Goals2.png"))));
            case 2 -> image.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/personal goal cards/Personal_Goals3.png"))));
            case 3 -> image.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/personal goal cards/Personal_Goals4.png"))));
            case 4 -> image.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/personal goal cards/Personal_Goals5.png"))));
            case 5 -> image.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/personal goal cards/Personal_Goals6.png"))));
            case 6 -> image.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/personal goal cards/Personal_Goals7.png"))));
            case 7 -> image.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/personal goal cards/Personal_Goals8.png"))));
            case 8 -> image.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/personal goal cards/Personal_Goals9.png"))));
            case 9 -> image.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/personal goal cards/Personal_Goals10.png"))));
            case 10 -> image.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/personal goal cards/Personal_Goals11.png"))));
            case 11 -> image.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/personal goal cards/Personal_Goals12.png"))));
        }
    }



    private void setTokenPicture(ImageView image, int goalNo) {
        switch(goalNo) {

            case 0 -> {
                boolean present = false;

                for (int i = 0; i < gui.getGameTokens().size(); i++) {
                    if (gui.getGameTokens().get(i).getId() == 0)
                        present = true;
                }
                if (present) {
                    image.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/scoring tokens/end game.jpg"))));
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
                    case 8 -> image.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/scoring tokens/scoring_8.jpg"))));
                    case 6 -> image.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/scoring tokens/scoring_6.jpg"))));
                    case 4 -> image.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/scoring tokens/scoring_4.jpg"))));
                    case 2 -> image.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/scoring tokens/scoring_2.jpg"))));
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
                    case 8 -> image.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/scoring tokens/scoring_8.jpg"))));
                    case 6 -> image.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/scoring tokens/scoring_6.jpg"))));
                    case 4 -> image.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/scoring tokens/scoring_4.jpg"))));
                    case 2 -> image.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/scoring tokens/scoring_2.jpg"))));
                    case 0 -> image.setOpacity(0);
                }
            }
        }
    }

    private void setAchievedTokenPicture(ImageView image, int tokenNo) {
        int size = gui.getPersonalTokens().size();
        if (size == tokenNo + 1) {
            int points = gui.getPersonalTokens().get(tokenNo).getPoints();
            switch (points) {
                case 8 -> image.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/scoring tokens/scoring_8.jpg"))));
                case 6 -> image.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/scoring tokens/scoring_6.jpg"))));
                case 4 -> image.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/scoring tokens/scoring_4.jpg"))));
                case 2 -> image.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/scoring tokens/scoring_2.jpg"))));
                case 1 -> image.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/scoring tokens/end game.jpg"))));
            }
        }
    }

}
