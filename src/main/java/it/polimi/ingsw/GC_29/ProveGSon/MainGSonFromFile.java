package it.polimi.ingsw.GC_29.ProveGSon;

import com.google.gson.Gson;
import it.polimi.ingsw.GC_29.Components.DevelopmentCard;
import it.polimi.ingsw.GC_29.EffectBonusAndActions.ObtainEffect;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

/**
 * Created by Lorenzotara on 22/05/17.
 */
public class MainGSonFromFile {

    public static void main(String[] args) throws FileNotFoundException {

        Gson gson = new Gson();

        FileReader fileReader = new FileReader("/Users/Lorenzotara/Desktop/cartaProva");

        DevelopmentCard card;

        //ObtainEffectInstanceCreator creator = new ObtainEffectInstanceCreator();

        card = gson.fromJson(fileReader, DevelopmentCard.class);

        System.out.println(card);

    }



}