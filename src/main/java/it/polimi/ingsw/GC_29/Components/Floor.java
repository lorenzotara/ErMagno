package it.polimi.ingsw.GC_29.Components;

import it.polimi.ingsw.GC_29.EffectBonusAndActions.Action;
import it.polimi.ingsw.GC_29.EffectBonusAndActions.Effect;
import it.polimi.ingsw.GC_29.EffectBonusAndActions.ObtainEffect;

/**
 * Created by Lorenzotara on 17/05/17.
 */
public class Floor {
    private DevelopmentCard developmentCard;
    private ActionSpace actionSpace;

    public Floor(ObtainEffect effect,int actionCost){
        this.developmentCard = null;
        this.actionSpace = new ActionSpace(effect,actionCost,new PawnSlot(1,true),true,false);
    }

    public void setDevelopmentCard(DevelopmentCard developmentCard) {
        this.developmentCard = developmentCard;
    }

    public DevelopmentCard getDevelopmentCard() {
        return developmentCard;
    }

    public ActionSpace getActionSpace() {
        return actionSpace;
    }

    public DevelopmentCard removeCard(){
        DevelopmentCard card = this.developmentCard;
        this.developmentCard = null;
        return card;
    }

}
