package it.polimi.ingsw.GC_29.Controllers.Input;


import it.polimi.ingsw.GC_29.Controllers.Controller;
import it.polimi.ingsw.GC_29.Controllers.GameState;
import it.polimi.ingsw.GC_29.Controllers.PlayerState;
import it.polimi.ingsw.GC_29.Model.Model;
import it.polimi.ingsw.GC_29.Model.Player;
import it.polimi.ingsw.GC_29.Model.PlayerColor;

import java.util.List;

/**
 * Created by Christian on 13/06/2017.
 */
public class Initialize extends Input {

    private PlayerColor playerColor;

    public Initialize(PlayerColor playerColor) {

        this.playerColor = playerColor;

    }

    @Override
    public void perform(Model model, Controller controller) {

        if(model.getGameState() == null){

            model.setGameState(GameState.RUNNING);
        }

        List<Player> turnOrder = model.getTurnOrder();

        Player lastPlayer = turnOrder.get(turnOrder.size()-1);

        if(lastPlayer.getPlayerColor() == playerColor){

            model.setCurrentPlayer(lastPlayer);
            controller.setCurrentBonusTileIndexPlayer(turnOrder.indexOf(lastPlayer));
            lastPlayer.setPlayerState(PlayerState.CHOOSE_BONUS_TILE);

            controller.startTimer(lastPlayer);
        }

        else {
            model.getPlayer(playerColor).setPlayerState(PlayerState.WAITING);
        }


    }

}
