package it.polimi.ingsw.GC_29.EffectBonusAndActions;

import it.polimi.ingsw.GC_29.Player.Player;

/**
 * Created by Christian on 18/05/2017.
 */
public class ActionEffect implements Effect{


    //TODO: ricordarsi di onlyWorkers quando viene attivata

    private ZoneType type;

    private int actionValue;

    private BonusAndMalusOnCost bonusAndMalusOnCost;

    private Boolean onlyWorkers = false;

    public ActionEffect(
            ZoneType type,
            int actionValue,
            BonusAndMalusOnCost bonusAndMalusOnCost) {

        this(type, actionValue);

        this.bonusAndMalusOnCost = bonusAndMalusOnCost;
    }

    public ActionEffect(
            ZoneType type,
            int actionValue,
            BonusAndMalusOnCost bonusAndMalusOnCost,
            Boolean onlyWorkers) {

        this(type, actionValue);

        this.bonusAndMalusOnCost = bonusAndMalusOnCost;

        this.onlyWorkers = onlyWorkers;
    }


    public ActionEffect(ZoneType zoneType, int actionValue){

        this.type = zoneType;
        this.actionValue = actionValue;
    }


    public ActionEffect(ActionEffect actionEffect) {

        this.type = actionEffect.type;

        this.actionValue = actionEffect.actionValue;

        this.bonusAndMalusOnCost = actionEffect.bonusAndMalusOnCost;
    }

    public ZoneType getType() {

        return type;
    }

    public int getActionValue() {

        return actionValue;
    }

    public BonusAndMalusOnCost getBonusAndMalusOnCost() {

        return bonusAndMalusOnCost;
    }

    public Boolean getOnlyWorkers() {
        return onlyWorkers;
    }

    @Override
    public String toString() {
        return "ActionEffect{" +
                "type=" + type +
                ", actionValue=" + actionValue +
                ", bonusAndMalusOnCost=" + bonusAndMalusOnCost +
                '}';
    }



    @Override
    public void execute(Player player) {

        player.getCurrentBonusActionList().add(this);
    }

}
