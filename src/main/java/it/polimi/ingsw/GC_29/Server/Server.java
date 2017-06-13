package it.polimi.ingsw.GC_29.Server;

import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Christian on 11/06/2017.
 */
public class Server {

    //Main class from the server side
    private final static int PORT = 29999;

    private final String NAME = "connection";

    private final static int RMI_PORT = 52365;

    private ExecutorService executor = Executors.newCachedThreadPool();

    private GameMatchHandler gameMatchHandler;

    private final int maxNumberOfPlayers = 2;

    private ConnectionInterfaceImpl connectionServer;

    private Boolean lobbyCreated = false;

    private Boolean minClientNumberReached = false;

    private long startTime;

    private SocketConnection socketConnection;

    private final long elapsedTime = 300000;


    protected Server() throws AlreadyBoundException, RemoteException {

        socketConnection = new SocketConnection();

        gameMatchHandler = new GameMatchHandler();

        execute();

    }

    private void execute() throws AlreadyBoundException, RemoteException {

        System.out.println("START RMI");
        startRMI();

        System.out.println("START SOCKET");
        Thread socketThread = new Thread(socketConnection);
        socketThread.start();

       // Boolean b = true;

        /*long i = 0;

        long var = 213999999;

        System.out.println("ENTRO NEL CICLO SERVER");

        while (b){ // TODO: rifare rispetto al runnable

            if(i % var == 0){

               // System.out.println("sono dentro main" + i);
            }


            if(gameMatchHandler.isLobbyCreated()){
                System.out.println("condizione lobby vera");
                gameMatchHandler.evaluateMinCondition();

                if(gameMatchHandler.evaluateConditionNewGame()){

                    System.out.println("NUOVA PARTITA!");

                    executor.submit(gameMatchHandler.getCurrentNewGame());

                    gameMatchHandler.setLobbyCreated(false);

                }
            }

            i++;
            if(i == 1283999994){
                i=0;
            }
        }*/

        System.out.println("ERRORE: FUORI CICLO");

    }

    public static void main(String[] args) throws IOException, AlreadyBoundException {
        Server server = new Server();
    }




    private void startRMI() throws RemoteException, AlreadyBoundException {

        //create the registry to publish remote objects
        Registry registry = LocateRegistry.createRegistry(RMI_PORT);
        System.out.println("Constructing the RMI registry");

        connectionServer = new ConnectionInterfaceImpl(gameMatchHandler);

        System.out.println("Binding the server implementation to the registry");
        registry.bind(NAME, connectionServer);


    }

    class SocketConnection implements Runnable{


        @Override
        public void run() {


        }
    }

}

