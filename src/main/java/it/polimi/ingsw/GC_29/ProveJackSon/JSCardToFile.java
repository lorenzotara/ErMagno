package it.polimi.ingsw.GC_29.ProveJackSon;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import it.polimi.ingsw.GC_29.Components.*;
import it.polimi.ingsw.GC_29.EffectBonusAndActions.Effect;
import it.polimi.ingsw.GC_29.EffectBonusAndActions.ObtainEffect;
import it.polimi.ingsw.GC_29.EffectBonusAndActions.PayToObtainEffect;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Lorenzotara on 22/05/17.
 */
public class JSCardToFile {

    public static void main(String[] args) throws IOException {


        // Ospitare i mendicanti

        ArrayList<Effect> immediateEffectsOIM = new ArrayList<Effect>();
        immediateEffectsOIM.add(new PayToObtainEffect(new GoodSet(1,1,1,1,1,1,1), new GoodSet(1,1,1,1,1,1,1)));

        ArrayList<Effect> permanentEffectsOIM = new ArrayList<Effect>();
        permanentEffectsOIM.add(new ObtainEffect(0,0,0,0,4,0,0));


        DevelopmentCard ospitareIMendicanti = new DevelopmentCard(
                "Ospitare i Mendicanti",
                "descrizione",
                Era.FIRSTERA,
                new CardCost(false, true, new GoodSet(4,0,0,0,0,0,0), new GoodSet(), false, new GoodSet()),
                CardColor.PURPLE,
                immediateEffectsOIM,
                permanentEffectsOIM,
                false,
                0);


        // JACKSON

        ObjectMapper mapper = new ObjectMapper();
        FileWriter fileWriter = new FileWriter("/Users/Lorenzotara/Desktop/cartaProva");

        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.writeValue(fileWriter, ospitareIMendicanti);

        fileWriter.close();

    }
}
