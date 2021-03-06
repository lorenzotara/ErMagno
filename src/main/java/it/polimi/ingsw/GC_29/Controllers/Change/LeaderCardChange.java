package it.polimi.ingsw.GC_29.Controllers.Change;

import it.polimi.ingsw.GC_29.Client.GUI.GuiChangeListener;
import it.polimi.ingsw.GC_29.Controllers.Change.GUIChange;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lorenzotara on 02/07/17.
 */
public class LeaderCardChange extends GUIChange {

    ArrayList<String> leaderCards;

    public LeaderCardChange(ArrayList<String> leaderCards) {
        this.leaderCards = leaderCards;
    }

    @Override
    public void perform(List<GuiChangeListener> listeners) {

        for (GuiChangeListener listener : listeners) {
            listener.setLeaderCards(leaderCards);
        }
    }
}
