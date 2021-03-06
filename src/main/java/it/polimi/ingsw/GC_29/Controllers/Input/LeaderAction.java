package it.polimi.ingsw.GC_29.Controllers.Input;

import it.polimi.ingsw.GC_29.Controllers.Controller;
import it.polimi.ingsw.GC_29.Controllers.PlayerState;
import it.polimi.ingsw.GC_29.Model.*;

/**
 * Created by Lorenzotara on 02/07/17.
 */
public class LeaderAction extends Input {

    PlayerColor playerColor;
    int leaderIndex;
    boolean activate;

    public LeaderAction(boolean b, int index, PlayerColor playerColor) {
        this.leaderIndex = index;
        this.activate = b;
        this.playerColor = playerColor;
    }

    @Override
    public void perform(Model model, Controller controller) {

        System.out.println("performing leader action \n");

        for (Player player : model.getTurnOrder()) {

            if (player.getPlayerColor() == playerColor) {
                LeaderCard leaderCard = player.getLeaderCards().get(leaderIndex);

                if (activate) {

                    leaderCard.execute(player);
                    handleBonusAndCouncil(player, controller);
                    }


                else {
                    player.getLeaderCards().get(leaderIndex).setDiscarded();
                    player.setLastState(player.getPlayerState());
                    CouncilPrivilegeEffect councilPrivilegeEffect = new CouncilPrivilegeEffect(1);
                    councilPrivilegeEffect.execute(player);
                    player.setPlayerState(PlayerState.CHOOSE_COUNCIL_PRIVILEGE);
                }

                break;
            }
        }
    }

    private void handleBonusAndCouncil(Player player, Controller controller) {

        if(!player.getCouncilPrivilegeEffectList().isEmpty()){

            player.setLastState(player.getPlayerState());
            player.setPlayerState(PlayerState.CHOOSE_COUNCIL_PRIVILEGE);
        }

        if (!player.getCurrentBonusActionList().isEmpty()) {

            ActionEffect currentBonusAction = player.getCurrentBonusActionList().removeFirst();

            // temporary bonusMalusOn cost setted in the player
            if (currentBonusAction.getBonusAndMalusOnCost() != null) {

               player.getCurrentBonusActionBonusMalusOnCostList().add(currentBonusAction.getBonusAndMalusOnCost());

            }

            controller.getActionChecker().resetActionListExceptPlayer();

            FamilyPawn familyPawn = new FamilyPawn(player.getPlayerColor(), FamilyPawnType.BONUS, currentBonusAction.getActionValue());

            controller.getActionChecker().setValidActionForFamilyPawn(familyPawn, currentBonusAction.getType());

            player.setLastState(player.getPlayerState());
            player.setPlayerState(PlayerState.BONUSACTION);

        }
    }
}
