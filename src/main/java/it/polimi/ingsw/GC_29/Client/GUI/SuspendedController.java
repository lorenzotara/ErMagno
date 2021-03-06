package it.polimi.ingsw.GC_29.Client.GUI;


import it.polimi.ingsw.GC_29.Client.InputInterfaceGUI;

import it.polimi.ingsw.GC_29.Client.GUI.GameBoardController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import javafx.event.ActionEvent;

/**
 * Created by Lorenzotara on 01/07/17.
 */
public class SuspendedController {

    private InputInterfaceGUI sender;
    private GameBoardController gameBoardController;

    @FXML
    private Button joinGame;


    public void setSender(InputInterfaceGUI sender) {
        this.sender = sender;
    }

    /*
    public void joinGame(ActionEvent actionEvent) {
        System.out.println("Sending input");
        sender.sendInput("join game");
    }
    */

    /**
     * Questa funzione è chiamata quando il giocatore clicca sul bottone join game,
     * la funzione invia tramite l'InputInterfaceGUI una stringa che indica la volontà del giocatore di riconnettersi
     * @param actionEvent
     */
    public void joinGame(ActionEvent actionEvent) {
        sender.sendInput("join game");

        System.out.println("BOTTONE SCHIACCIATO JOIN GAME");
    }
}
