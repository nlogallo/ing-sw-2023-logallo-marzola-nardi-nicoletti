package it.polimi.ingsw;

import it.polimi.ingsw.controller.ClientController;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.commonGoal.CG_4Corners;
import it.polimi.ingsw.model.commonGoal.CG_StdEqualTiles;
import it.polimi.ingsw.model.commonGoal.CommonGoal;
import it.polimi.ingsw.utils.NetworkMessage;
import it.polimi.ingsw.view.CLI.CLIView;
import it.polimi.ingsw.view.ClientViewObservable;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class ClientControllerTest {

    CLIView cliView = new CLIView("test");

    ClientViewObservable view = new ClientViewObservable(cliView);
    ClientController clientController;

    @Before
    public void setUp(){
        clientController = new ClientController(view, new MyShelfieClient(), "test");
    }

    @Test
    public void checkNullTiles_NullInThatPosition_AssertFalse(){
        assertFalse(clientController.checkNullTiles(1, 4, new Board(2)));
    }

    @Test
    public void checkNullTiles_NotNullInThatPosition_AssertTrue(){
        assertTrue(clientController.checkNullTiles(2, 4, new Board(2)));
    }

    @Test
    public void checkCanPullTile_FreeEdges_AssertTrue(){
        assertTrue(clientController.checkCanPullTile(2, 4, new Board(2)));
    }

    @Test
    public void checkCanPullTile_NoFreeEdges_AssertFalse(){
        assertFalse(clientController.checkCanPullTile(4, 4, new Board(2)));
    }

    @Test
    public void checkIsAlignedTiles_NoAligned_AssertFalse(){
        ArrayList<String> positions = new ArrayList<>();
        positions.add("13");
        positions.add("26");
        assertFalse(clientController.checkIsAlignedTiles(positions, new Board(2)));
    }

    @Test
    public void checkIsAlignedTiles_Aligned_AssertTrue(){
        ArrayList<String> positions = new ArrayList<>();
        positions.add("13");
        positions.add("14");
        assertTrue(clientController.checkIsAlignedTiles(positions, new Board(2)));
    }

    @Test
    public void checkFreeSpotsInColumnShelf_NotFull_AssertTrue(){
        ArrayList<String> positions = new ArrayList<>();
        positions.add("13");
        positions.add("14");
        assertTrue(clientController.checkFreeSpotsInColumnShelf(positions, new Shelf(), 1));
    }

    @Test
    public void checkFreeSpotsInColumnShelf_Full_AssertFalse(){
        ArrayList<String> positions = new ArrayList<>();
        positions.add("13");
        positions.add("14");
        Shelf playerShelf = new Shelf();
        ArrayList<Tile> tiles = new ArrayList<>();
        tiles.add(new Tile(0));
        tiles.add(new Tile(1));
        tiles.add(new Tile(2));
        tiles.add(new Tile(3));
        tiles.add(new Tile(4));
        playerShelf.insertTiles(0, tiles);
        assertFalse(clientController.checkFreeSpotsInColumnShelf(positions, playerShelf, 1));
    }

    @Test
    public void updateBoard_SetNewBoard_AssertEquals(){
        Board board = new Board(3);
        NetworkMessage networkMessage = new NetworkMessage();
        networkMessage.setTextMessage("This is your Board");
        networkMessage.addContent(board);
        networkMessage.setRequestId("UB");
        clientController.updateBoard(networkMessage);
        assertEquals(cliView.getBoard(), board);
    }

    @Test
    public void playerSetup_SetInitialSetup_AssertEquals(){
        NetworkMessage networkMessage = new NetworkMessage();
        ArrayList<String> otherNicknamesList = new ArrayList<>();
        otherNicknamesList.add("second");
        otherNicknamesList.add("third");
        Integer numOfGameToken = (3 * 2) + 1;
        ArrayList<CommonGoal> commonGoals = new ArrayList<>();
        commonGoals.add(new CG_4Corners(4,"Four tiles of the same type in the four corners of the bookshelf."));
        commonGoals.add(new CG_StdEqualTiles(10, "Eight tiles of the same type. There’s no restriction about the position of these tiles."));
        ArrayList<Token> remainingTokenList = new ArrayList<>();
        remainingTokenList.add(new Token(8));
        remainingTokenList.add(new Token(7));
        remainingTokenList.add(new Token(6));
        remainingTokenList.add(new Token(5));
        remainingTokenList.add(new Token(4));
        remainingTokenList.add(new Token(3));
        remainingTokenList.add(new Token(0));
        networkMessage.setTextMessage("test, welcome to the Game. Enjoy it!");
        Board board = new Board(3);
        networkMessage.addContent(board);
        Shelf firstShelf = new Shelf();
        networkMessage.addContent(firstShelf);
        TileType[][] playerShelf =  {{TileType.PLANT, TileType.EMPTY, TileType.FRAME, TileType.EMPTY, TileType.EMPTY},{TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.CAT},{TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.BOOK, TileType.EMPTY},{TileType.EMPTY, TileType.GAME, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY},{TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY, TileType.EMPTY}, {TileType.EMPTY, TileType.EMPTY, TileType.TROPHY, TileType.EMPTY, TileType.EMPTY}};
        PersonalGoal personalGoal = new PersonalGoal(playerShelf);
        networkMessage.addContent(personalGoal);
        networkMessage.addContent(commonGoals.get(0));
        networkMessage.addContent(commonGoals.get(1));
        networkMessage.addContent(true);
        networkMessage.addContent(2);
        networkMessage.addContent("second");
        networkMessage.addContent("third");
        networkMessage.addContent(numOfGameToken);
        for (Token token : remainingTokenList) {
            networkMessage.addContent(token);
        }
        networkMessage.setRequestId("PS");
        clientController.playerSetup(networkMessage);
        assertEquals(cliView.getBoard(), board);
        assertEquals(cliView.getShelf(), firstShelf);
        assertEquals(cliView.getCommonGoals(), commonGoals);
        assertEquals(cliView.getPersonalGoal(), personalGoal);
        assertEquals(cliView.getPlayersNickname(), otherNicknamesList);
        assertEquals(cliView.getGameTokens(), remainingTokenList);
        assertEquals(cliView.getScreenMessage(), networkMessage.getTextMessage());
    }

    @Test
    public void updateGameTokens_SetNewTokens_AssertEquals(){
        ArrayList<Token> remainingTokenList = new ArrayList<>();
        remainingTokenList.add(new Token(6));
        remainingTokenList.add(new Token(5));
        remainingTokenList.add(new Token(4));
        remainingTokenList.add(new Token(3));
        remainingTokenList.add(new Token(0));
        NetworkMessage networkMessage = new NetworkMessage();
        networkMessage.setTextMessage("Game Token Update");
        networkMessage.setRequestId("UGT");
        networkMessage.addContent(5);
        for (Token token : remainingTokenList) {
            networkMessage.addContent(token);
        }
        clientController.updateGameTokens(networkMessage);
        assertEquals(cliView.getGameTokens(), remainingTokenList);
    }

    @Test
    public void updateResult_UpdateInfo_AssertEquals() {
        playerSetup_SetInitialSetup_AssertEquals();
        NetworkMessage networkMessage = new NetworkMessage();
        networkMessage.addContent("second");
        Map<Player, Shelf> shelvesMap = new HashMap<>();
        Shelf secondShelf = new Shelf();
        Shelf thirdShelf = new Shelf();
        ArrayList<Tile> tiles = new ArrayList<>();
        Game game = new Game(400, 3);
        tiles.add(new Tile(0));
        secondShelf.insertTiles(0, tiles);
        shelvesMap.put(new Player(false, secondShelf, "second", game), secondShelf);
        tiles.remove(0);
        tiles.add(new Tile(1));
        secondShelf.insertTiles(4, tiles);
        shelvesMap.put(new Player(false, thirdShelf, "third", game), thirdShelf);
        networkMessage.addContent(shelvesMap);
        networkMessage.setRequestId("UR");
        networkMessage.setTextMessage("second is your turn");
        clientController.updateResults(networkMessage);
        assertEquals(cliView.getPlayersShelf(), shelvesMap);
        assertEquals(cliView.getScreenMessage(), networkMessage.getTextMessage());
        assertEquals(cliView.getCurrentPlayer(), "second");
    }
    @Test
    public void updateChat_NewMessageInGlobalChat_AssertEquals(){
        playerSetup_SetInitialSetup_AssertEquals();
        NetworkMessage networkMessage = new NetworkMessage();
        networkMessage.setRequestId("UC");
        networkMessage.addContent("second");
        ArrayList<String> receivers = new ArrayList<>();
        receivers.add("test");
        receivers.add("second");
        receivers.add("third");
        networkMessage.addContent(receivers);
        networkMessage.addContent("Hi, how are you?");
        clientController.updateChat(networkMessage);
        assertEquals(cliView.getGlobalChat().getClientMessages().get(0).getMessage(), "Hi, how are you?");
    }

    @Test
    public void sendMessage_SendNullMessageInChat_AssertEquals(){
        ArrayList<String> receivers = new ArrayList<>();
        receivers.add("test");
        receivers.add("second");
        receivers.add("third");
        clientController.sendMessage("test", receivers, null);
        assertEquals(cliView.getScreenMessage(), "You can't send empty message");
    }

    /**
     * This test method has the only purpose to test if the ClientController makes the call to MyShelfieClient.
     * That said the NullPointerException is called for that reason.
     * It has no purpose to check if the server receives it, because it is necessary to establish a real connection between them.
     * Furthermore, this method does not check if the infos are sent correctly.
     */
    @Test
    public void sendMessage_SendNewMessageInChat_ThrowsNullPointerException(){
        ArrayList<String> receivers = new ArrayList<>();
        receivers.add("test");
        receivers.add("second");
        receivers.add("third");
        assertThrows(NullPointerException.class, ()->clientController.sendMessage("test", receivers, "Nice to meet you"));
    }


    /**
     * This test method has the only purpose to test if the ClientController makes the call to MyShelfieClient.
     * * That said the NullPointerException is called for that reason.
     * It has no purpose to check if the server receives it, because it is necessary to establish a real connection between them.
     * Furthermore, this method does not check if the infos are sent correctly.
     */
    @Test
    public void moveTiles_MoveTilesFromBoardToShelf_ThrowsNullPointerException(){
        ArrayList<String> positions = new ArrayList<>();
        positions.add("13");
        positions.add("14");
        assertThrows(NullPointerException.class, ()->clientController.moveTiles(positions, 1));
    }

}
