package it.polimi.ingsw.GC_29.Client.GUI;
import it.polimi.ingsw.GC_29.Client.ClientSocket.ClientSocketCLI;
import it.polimi.ingsw.GC_29.Client.Distribution;
import it.polimi.ingsw.GC_29.Server.Observable;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.event.ActionEvent;
import javafx.stage.Stage;


/**
 * Created by AlbertoPennino on 21/06/2017.
 */
public class LoginController extends Observable<LoginChange> implements ControllerGUI {

    @FXML private TextField username;
    @FXML private TextField password;
    @FXML private RadioButton rmi;
    @FXML private RadioButton socket;
    @FXML private Button submit;
    @FXML private Text errorBlankFields;
    @FXML private Text errorUserPsw;
    private Distribution connection;
    private final int PORT = 29999;
    private final String IP = "127.0.0.1";
    private boolean firstTime = true;
    private ClientSocketCLI clientSocketCLI;
    private boolean connected = false;
    private ActionEvent event;


    /**
     * called when the player clicks on the submit button
     * checks if all the areas are completed
     * if they aren't an error message is shown
     * if they are begins the connection with the server
     * @param event
     */
    public void sendSubmit(ActionEvent event){

        this.event = event;

        if(!(username.getText().isEmpty()
                && password.getText().isEmpty())
                && ((rmi.isSelected()
                    || socket.isSelected()))) {

            if (rmi.isSelected()) {

                setConnection(Distribution.RMI);
                connected = true;
                notifyObserver(new LoginChange(connected));

            }

            else if (socket.isSelected()) {

                setConnection(Distribution.SOCKET);
                connected = true;

                notifyObserver(new LoginChange(connected));

            }


        }
        else {
            errorBlankFields.setVisible(true);
        }
    }


    /**
     * doesn't allow to both RMI and Socket buttons to be selected simultaneously
     * @param event
     */
    public void switchButtons(ActionEvent event){
        if (event.getSource()==rmi){
            socket.setSelected(false);
        }
        else if (event.getSource()==socket){
            rmi.setSelected(false);
        }
    }

    /**
     * closes the login window
     */
    public void close() {

        Node source = (Node) event.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }


    public void setConnection(Distribution connection) {
        this.connection = connection;
    }

    public boolean getConnected() {
        return connected;
    }

    public void setConnected(Boolean connected) {
        this.connected = connected;
    }

    public Distribution getConnection() {
        return connection;
    }

    public String getUsername() {

        return username.getText();
    }

    public String getPassword() {

        return password.getText();
    }

    public void showError() {
        errorUserPsw.setVisible(true);
    }

    /*
    public void connectionStable() {

        switch (connection) {

            case SOCKET:
                rmi.setVisible(false);
                break;

            case RMI:
                socket.setVisible(false);
                break;
        }
    }
    */




    /*
    private void executeSocket(ActionEvent event) {
        try {

            if (this.clientSocketCLI == null) {
                this.clientSocketCLI = new ClientSocketCLI(EnumInterface.GUI);
            }

            clientSocketCLI.startClientGUI();

            if (clientSocketCLI.loginGUI(username.getText(), password.getText())) {
                Node source = (Node) event.getSource();
                Stage stage  = (Stage) source.getScene().getWindow();
                stage.close();
                //Application.launch(WaitingNewGame.class);

                clientSocketCLI.playNewGameGUI();
            }

            else {

                errorUserPsw.setVisible(true);

            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void executeRMI(ActionEvent event) {

    }
    */
}
