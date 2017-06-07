package it.polimi.ingsw.GC_29.Controllers;

import it.polimi.ingsw.GC_29.Components.*;
import it.polimi.ingsw.GC_29.EffectBonusAndActions.Effect;
import it.polimi.ingsw.GC_29.EffectBonusAndActions.Filter;
import it.polimi.ingsw.GC_29.Player.Player;
import it.polimi.ingsw.GC_29.Player.PlayerColor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AlbertoPennino on 22/05/2017.
 */
public class GameController {

    //TODO: move delle pawn sulle track a fine turno
    //TODO: traduzione risorse punti vittoria

    private GameStatus gameStatus;

    public GameController() {
        this.gameStatus = GameStatus.getInstance();
    }

    public void init() throws Exception {

        DevelopmentCard[] greenDeck = new DevelopmentCard[4];
        DevelopmentCard[] blueDeck = new DevelopmentCard[4];
        DevelopmentCard[] yellowDeck = new DevelopmentCard[4];
        DevelopmentCard[] purpleDeck = new DevelopmentCard[4];


        while (gameStatus.getCurrentRound() <= 6) {

            // throwDices(); TODO: metodo di interfaccia

            setFamilyPawnsValues();

            for (int i = 0; i < 4; i++) {
                greenDeck[i] = gameStatus.getOrderedDecks().get(CardColor.GREEN).pop();
                blueDeck[i] = gameStatus.getOrderedDecks().get(CardColor.BLUE).pop();
                yellowDeck[i] = gameStatus.getOrderedDecks().get(CardColor.YELLOW).pop();
                purpleDeck[i] = gameStatus.getOrderedDecks().get(CardColor.PURPLE).pop();

            }

            gameStatus.getGameBoard().setTurn(greenDeck,blueDeck,yellowDeck,purpleDeck);

            while (gameStatus.getCurrentTurn() <= gameStatus.getTurnOrder().size()) {

                for (Player player : gameStatus.getTurnOrder()) {
                    gameStatus.setCurrentPlayer(player);
                    gameStatus.getPlayerController().init();
                }

                gameStatus.setCurrentTurn(gameStatus.getCurrentTurn()+1);

            }


            checkSkipTurn();
            setNewTurnOrder();

            if (gameStatus.getCurrentRound()%2 == 0) {
                executeTiles();

                if (gameStatus.getCurrentEra() == Era.FIRST) {
                    gameStatus.setCurrentEra(Era.SECOND);

                } else if (gameStatus.getCurrentEra() == Era.SECOND) {
                    gameStatus.setCurrentEra(Era.THIRD);

                } else if (gameStatus.getCurrentEra() == Era.THIRD) endGame();
            }

            gameStatus.getGameBoard().clearAll();
            gameStatus.setCurrentTurn(1);

            // Setting availability on every pawn

            for (Player player : gameStatus.getTurnOrder()) {

                for (FamilyPawnType familyPawnType : FamilyPawnType.values()){

                    if (familyPawnType != FamilyPawnType.BONUS)  player.getFamilyPawnAvailability().put(familyPawnType, true);
                }
            }

            gameStatus.setCurrentRound(gameStatus.getCurrentRound()+1);

        }
    }


    //TODO testing
    private void setFamilyPawnsValues() throws Exception {
        for (Player player : gameStatus.getTurnOrder()) {

            Dice tempDice;

            for (FamilyPawnType familyPawnType : FamilyPawnType.values()) {

                if (familyPawnType != FamilyPawnType.BONUS && familyPawnType != FamilyPawnType.NEUTRAL) {
                    tempDice = gameStatus.getGameBoard().getDice(familyPawnType);
                    player.setFamilyPawnValue(familyPawnType, tempDice.getFace());

                }
            }
        }
    }


    /**
     * endGame calculates the victoryPoints of all the players and chooses the winner
     */
    public void endGame() { //TODO: make private

        List<Player> players = gameStatus.getTurnOrder();

        Player winner = null;
        int winningPoints = 0;

        for (Player player : players) {

            if (!Filter.applySpecial(player, CardColor.PURPLE)) {
                DevelopmentCard[] cards =  player.getPersonalBoard().getLane(CardColor.PURPLE).getCards();

                for (DevelopmentCard card : cards) {

                    for (Effect effect : card.getPermanentEffect()) {
                        effect.execute(player);
                    }
                }
            }

            /* non mi piace
            Pawn pawn = player.getMarkerDiscs()[0];
            playersVictoryPoints.put(player, pawnMap.get(pawn)); */

            int playerPoints = player.getActualGoodSet().getGoodAmount(GoodType.VICTORYPOINTS);

            if (playerPoints > winningPoints) {
                winningPoints = playerPoints;
                winner = player;
            }
        }

        System.out.println("The winner is... " + winner);

        //TODO: clear game

    }


    /**
     * executeTiles first decides which is the threshold for the excommunication looking at the the currentEra.
     * Then it takes the right Tile for that Era and saves in a arrayList of excommunicated pawns all the pawns that
     * are in the FaithPointsTrack before the threshold. After finding the players from the pawns (it compares the
     * color of the pawns with the color of the players in the turnOrderTrack) add all the maluses present in the Tile
     * to every player excommunicated.
     */
    private void executeTiles() {

        int threshold=0;
        Era currentEra = gameStatus.getCurrentEra();
        switch (currentEra) {
            case FIRST:
                threshold = 3;
                break;
            case SECOND:
                threshold = 4;
                break;
            case THIRD:
                threshold = 5;
                break;
        }

        ExcommunicationTile tileToExecute = gameStatus.getGameBoard().getExcommunicationLane().getExcommunicationTile(currentEra);
        FaithPointsTrack faithPointsTrack = gameStatus.getGameBoard().getFaithPointsTrack();

        List<Player> players = gameStatus.getTurnOrder();
        ArrayList<Pawn> excommunicatedPawns = new ArrayList<Pawn>();

        for (int i = 0; i < threshold; i++) {
            PawnSlot pawnSlot = faithPointsTrack.getPawnSlot(i);
            excommunicatedPawns.addAll(pawnSlot.getPlayerPawns());
        }

        if (!excommunicatedPawns.isEmpty()) {

            for (Pawn excommunicatedPawn : excommunicatedPawns) {
                for (Player player : players) {

                    if (excommunicatedPawn.getPlayerColor() == player.getPlayerColor()) {

                        tileToExecute.execute(player);

                    }
                }

            }

        }

        //TODO: vuoi pregare? Se player prega torna a 0 nella track e prende i punti vittoria dello slot della fede e non viene scomunicato. Se decide di rimanere
        //TODO: riceve la Tile e viene scomunicato

    }



    /**
     * newTurnOrder is the turnOrderTrack from the councilPalace. This track contains the first players, but it is
     * not sure that contains them all. In case some players didn't go to the palace, they will follow the same order,
     * relatively, that they had in the previous turn. For this reason setNewTurnOrder copies all the player of the
     * oldTurnOrder following the order of the color in newTurnOrder in a temporary arrayList. While doing this process,
     * the method saves all the indices of the oldTurnOrder that point to the players that have already been copied.
     * After this first step, all the players of the oldTurnOrder are added to the temporary arrayList, skipping the
     * ones who have already been copied. Then the TurnOrder in the GameStatus is set.
     */
    public void setNewTurnOrder() {  //Todo: make private
        PlayerColor[] newTurnOrder = gameStatus.getGameBoard().getCouncilPalace().getTurnOrder();
        List<Player> oldTurnOrder = gameStatus.getTurnOrder();
        ArrayList<Player> temporaryTurnOrder = new ArrayList<>();
        ArrayList<Integer> indices = new ArrayList<>();

        for (PlayerColor playerColor : newTurnOrder) {

            for (Player player : oldTurnOrder) {

                if (player.getPlayerColor() == playerColor) {
                    temporaryTurnOrder.add(player);
                    indices.add(oldTurnOrder.indexOf(player));
                }
            }
        }

        for (int i = 0; i < oldTurnOrder.size(); i++) {
            if (!indices.contains(i)) temporaryTurnOrder.add(oldTurnOrder.get(i));
        }

        gameStatus.setTurnOrder(temporaryTurnOrder);

    }



    /**
     * checkSkipTurn takes all the players who have skipped the turn and make them execute their action
     */
    private void checkSkipTurn() {

        ArrayList<Player> players = gameStatus.getSkippedTurnPlayers();

        if (!players.isEmpty()) {
            for (Player player : players) {
                gameStatus.setCurrentPlayer(player);
                gameStatus.getPlayerController().init();
            }
        }
    }

}
