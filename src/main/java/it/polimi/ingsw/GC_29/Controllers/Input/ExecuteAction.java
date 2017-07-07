package it.polimi.ingsw.GC_29.Controllers.Input;

import it.polimi.ingsw.GC_29.Controllers.Controller;
import it.polimi.ingsw.GC_29.Controllers.PlayerState;
import it.polimi.ingsw.GC_29.Model.*;

import java.rmi.RemoteException;

/**
 * Created by Christian on 07/06/2017.
 */
public class ExecuteAction extends Input {

    private int actionIndex;

    private transient Player currentPlayer;

    public ExecuteAction(int actionIndex){

        this.actionIndex = actionIndex;
    }

    /**
     * The ExecuteAction class extends the Input class. the "perform" method is a polimorphic method that in this Concrete Input class executes
     * the chosen action (actionIndex) and than change the current player "PlayerState" --> due to this change the view of the specific player
     * will be notified about this change ( because each view observes the model and the specific PLayer associated with the client
     *
     * @throws RemoteException
     */
    @Override
    public void perform(Model model, Controller controller) {

        currentPlayer = model.getCurrentPlayer();

        Action actionSelected = currentPlayer.getCurrentValidActionsList().get(actionIndex);

        if(actionSelected instanceof WorkAction) {

            WorkAction workAction = (WorkAction)actionSelected;
            currentPlayer.setCurrentAction(workAction);

            workAction.buildDifferentChoices();


            if (workAction.getCardsForWorkers().keySet().contains(0)) {

                controller.handlePayToObtainCards(workAction, currentPlayer, 0);
            }

            else {

                currentPlayer.setPlayerState(PlayerState.CHOOSEWORKERS);
            }


        }


        else if (actionSelected instanceof TowerAction) {

            TowerAction towerAction = (TowerAction)actionSelected;
            currentPlayer.setCurrentAction(towerAction);
            if (towerAction.getCardCost().isWithPrice() && towerAction.getPossibleCardCosts().keySet().size() > 1) {
                currentPlayer.setPlayerState(PlayerState.CHOOSECOST);
            }

            else {
                towerAction.setCostChosen(0);
                towerAction.execute();
                controller.handleEndAction();
            }
        }

        /////////////NORMAL ACTIONS WITHOUT DISTRIBUTION////////////////////

        else {

            currentPlayer.setCurrentAction(actionSelected);

            actionSelected.execute();

            controller.handleEndAction();

        }

    }

}