package it.polimi.ingsw.GC_29.Client.GUI.DisconectedPlayer;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

import java.util.List;

/**
 * Created by AlbertoPennino on 05/07/2017.
 */
public class DisconnectedPlayerController {

    @FXML
    private Text disconnectedPlayer;

    /**
     * update the name of the disconnected player to show
     * @param playerId
     */
    public void updateDisconnectedPlayer(List<String> playerId){

        String text = "THE FOLLOWING PLAYERS ARE DISCONNECTED: \n";

        for (String username : playerId) {

            text = text + username.toUpperCase() + "\n";

        }

        disconnectedPlayer.setText(text);
    }
}