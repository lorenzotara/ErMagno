package it.polimi.ingsw.GC_29.Controllers.Input;

import it.polimi.ingsw.GC_29.Controllers.Controller;
import it.polimi.ingsw.GC_29.Controllers.Input.Input;
import it.polimi.ingsw.GC_29.Controllers.PlayerState;
import it.polimi.ingsw.GC_29.Model.Model;
import it.polimi.ingsw.GC_29.Model.TowerAction;
import it.polimi.ingsw.GC_29.Model.Player;

/**
 * Created by Lorenzotara on 18/06/17.
 */
public class PayCard extends Input {

    int costChosen;

    public PayCard(int costChosen) {
        this.costChosen = costChosen;
    }

    @Override
    public void perform(Model model, Controller controller) {

        Player currentPlayer = model.getCurrentPlayer();

        TowerAction towerAction = (TowerAction)currentPlayer.getCurrentAction();
        towerAction.setCostChosen(costChosen);

        towerAction.execute();

        if (currentPlayer.getPlayerState() != PlayerState.SUSPENDED) {
            controller.handleEndAction();

        }
        else {

            //Elimino eventuali bonus action, council privileges e temporary bonus and malus
            currentPlayer.getCurrentBonusActionList().clear();
            currentPlayer.getCouncilPrivilegeEffectList().clear();
            currentPlayer.getCurrentBonusActionBonusMalusOnCostList().clear();

        }
    }
}
