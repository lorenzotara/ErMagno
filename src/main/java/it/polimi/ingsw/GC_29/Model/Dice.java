package it.polimi.ingsw.GC_29.Model;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Lorenzotara on 17/05/17.
 */
public class Dice {
    private int face;
    private FamilyPawnType color;

    public int getFace() {
        return face;
    }

    public FamilyPawnType getColor() {
        return color;
    }

    public Dice(FamilyPawnType dicetype) {

        if (dicetype != FamilyPawnType.BLACK && dicetype != FamilyPawnType.ORANGE && dicetype != FamilyPawnType.WHITE){
            throw new IllegalArgumentException("Illegal dice type: " + dicetype);
        }

        this.face = 1;
        this.color= dicetype;
    }

    /**
     * roll the dice generating a random number from 1 to 6
     */
    public void roll(){

        face = ThreadLocalRandom.current().nextInt(1,7);
    }


    @Override
    public String toString() {
        return "Dice{" +
                "face=" + face +
                ", color=" + color +
                '}';
    }
}
