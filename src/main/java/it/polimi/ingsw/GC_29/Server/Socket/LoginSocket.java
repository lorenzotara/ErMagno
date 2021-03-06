package it.polimi.ingsw.GC_29.Server.Socket;

import it.polimi.ingsw.GC_29.Client.ClientRMI.ClientRMIView;
import it.polimi.ingsw.GC_29.Client.Distribution;
import it.polimi.ingsw.GC_29.Client.EnumInterface;
import it.polimi.ingsw.GC_29.Server.GameMatchHandler;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Lorenzotara on 14/06/17.
 *
 * LoginSocket handles the client's login if he has chosen Socket Distribution.
 */
public class LoginSocket {

    private final ObjectInputStream socketIn;
    private final ObjectOutputStream socketOut;
    private final Socket socket;
    private final GameMatchHandler gameMatchHandler;
    private String username;
    private boolean logged;
    private EnumInterface enumInterface;

    private static final Logger LOGGER  = Logger.getLogger(ClientRMIView.class.getName());


    public LoginSocket(PlayerSocket playerSocket, GameMatchHandler gameMatchHandler) throws IOException {
        this.socketIn = playerSocket.getSocketIn();
        this.socketOut = playerSocket.getSocketOut();
        this.socket = playerSocket.socket;
        this.gameMatchHandler = gameMatchHandler;
        this.logged = false;
    }

    /**
     * CLI case: the player inserts his username and password and the gameMatchHandler checks if they are
     * correct. If they are correct the client is logged, otherwise it asks again to fill username and password.
     *
     * GUI case: the player inserts username, password and connection and then the login does the same things
     * as in cli, except that if the login has not been successful, it exits the loop.
     * @return
     */
    public String login() {


        while (!logged) {
            try {

                String login = (String)socketIn.readObject();

                if (login.contentEquals("login")) {

                    String gameInterface = (String)socketIn.readObject();

                    if (gameInterface.contentEquals("gui")) {
                        enumInterface = EnumInterface.GUI;
                    }

                    if (gameInterface.contentEquals("cli")) {
                        enumInterface = EnumInterface.CLI;
                    }

                    username = (String)socketIn.readObject();
                    String password = (String)socketIn.readObject();

                    if (!gameMatchHandler.verifyLoggedClient(username)) {

                        String pw = gameMatchHandler.getUserPassword().get(username);

                        if (pw == null) {
                            gameMatchHandler.getUserPassword().put(username, password);
                            logged = true;
                        }

                        else {
                            logged = password.equals(pw);
                        }
                    }

                    else logged = false;


                }

                else System.out.println("NON è LOGIN ma sei in LOGIN del Server");

                socketOut.writeBoolean(logged);
                socketOut.flush();

                if (enumInterface == EnumInterface.GUI) break;

            } catch (IOException e) {
                LOGGER.log(Level.INFO, e.getMessage(), e);
            } catch (ClassNotFoundException e) {
                LOGGER.log(Level.INFO, e.getMessage(), e);
            }
        }

        return username;

    }

    public Socket getSocket() {
        return socket;
    }

    public EnumInterface getEnumInterface() {
        return enumInterface;
    }

    public boolean isLogged() {
        return logged;
    }
}
