package it.polimi.ingsw.GC_29.Client;

import it.polimi.ingsw.GC_29.Client.ClientRMI.ClientRMI;
import it.polimi.ingsw.GC_29.Client.ClientRMI.ClientRemoteInterfaceImpl;
import it.polimi.ingsw.GC_29.Client.ClientSocket.ClientSocket;
import it.polimi.ingsw.GC_29.Server.RMI.ConnectionInterface;

import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

/**
 * Created by Christian on 11/06/2017.
 */
public class Client {

    private Distribution distribution;
    private boolean connectionChosen;

    private ClientRemoteInterfaceImpl clientRemote;

    private ClientRMI gameRMI;

    private ConnectionInterface connectionStub;

    public Client() throws RemoteException {
        super();

        gameRMI = new ClientRMI();

        askWichConnection();

        try {
            execute();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            // TODO: chiusura canale rmi e canale socket
        }
    }

    private void askWichConnection() {

        Scanner stdIn = new Scanner(System.in);

        while(!connectionChosen){

            System.out.println("which connection do you want to use?");
            System.out.println("rmi --> rmi connection");
            System.out.println("socket --> socket connection");

            String input = stdIn.nextLine();

            switch (input){

                case "rmi":
                    distribution = Distribution.RMI;
                    connectionChosen = true;
                    break;

                case  "socket":
                    distribution = Distribution.SOCKET;
                    connectionChosen = true;
                    break;

                default:
                    System.out.println("wrong input!");
                    System.out.println("");
                    break;

            }
        }
    }

    private void execute() throws Exception {

        switch (distribution) {

            case RMI:
                gameRMI.executeRMI();
                break;

            case SOCKET:
                executeSocket();
                break;

            default:
                throw new Exception("Illegal distribution type: " + distribution);

        }

    }

    private void executeSocket() {

        try {

            ClientSocket clientSocket = new ClientSocket();
            clientSocket.startClient();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public static void main(String[] args) throws IOException, InterruptedException {
        Client client = new Client();
    }
}


