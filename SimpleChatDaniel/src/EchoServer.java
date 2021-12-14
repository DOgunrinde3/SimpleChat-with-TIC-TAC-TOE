

import java.io.*;

/**
 * This class overrides some of the methods in the abstract superclass in order
 * to give more functionality to the server.
 *
 */
public class EchoServer extends AbstractServer {
    //Class variables *************************************************

    /**
     * The default port to listen on.
     */
    final public static int DEFAULT_PORT = 5555;

    //Constructors ****************************************************
    /**
     * Constructs an instance of the echo server.
     *
     * @param port The port number to connect on.
     */
    public EchoServer(int port) {
        super(port);
    }

    //Instance methods ************************************************
    @Override
    protected void clientConnected(ConnectionToClient client) {
        System.out.println("Client Connected from " + client);
    }

    @Override
    synchronized protected void clientException(
            ConnectionToClient client, Throwable exception) {
        System.out.println("Client Disconnected from " + client);
    }

    /**
     * This method handles any messages received from the client.
     *
     * @param msg The message received from the client.
     * @param client The connection from which the message originated.
     */
    public void handleMessageFromClient(Object msg, ConnectionToClient client) throws IOException {
        String message = msg.toString();
        if (message.startsWith("#")) {
            handleCommandFromClient(message, client);
        } else {
            System.out.println("Message received: " + msg + " from " + client);
            this.sendToAllClients(msg);
        }

    }

    public void handleCommandFromClient(String message, ConnectionToClient client) throws IOException {
        System.out.println("Handle Command ");
        if (message.startsWith("#login")) {
            String userId = message.substring(message.indexOf(" ") + 1, message.length());
            userId = userId.trim();
            System.out.println(">>" + userId);

            client.setInfo("userId", userId);
            sendToAllClients(userId + " just logged in");
        } else if (message.startsWith("#join")) {
            String room = message.substring(message.indexOf(" ") + 1, message.length());
            room = room.trim();
            System.out.println(">>>" + room);
            client.setInfo("room", room);
            String userId = client.getInfo("userId").toString();
            sendToAllClients(userId + " just joined room: " + room);
        } else if (message.startsWith("#pm")) {
            String target = "";
            String pmMessage = "";

            String messageWOCommand = message.substring(message.indexOf(" ") + 1, message.length());
            target = messageWOCommand.substring(0, messageWOCommand.indexOf(" "));
            pmMessage = messageWOCommand.substring(messageWOCommand.indexOf(" "), messageWOCommand.length());
            sendToAClient(pmMessage, target, client);
        } else if (message.startsWith("#yell")) {
            String Message = "";

            Message = message.substring(message.indexOf(" ") + 1, message.length());
            sendToAllClients(Message);
        } else if (message.startsWith("#intercom")) {
            String interMessage = "";
            String room = "";
            String messageWOCommand = message.substring(message.indexOf(" ") + 1, message.length());
            room = messageWOCommand.substring(0, messageWOCommand.indexOf(" "));
            //room=room.trim();
            interMessage = messageWOCommand.substring(messageWOCommand.indexOf(" "), messageWOCommand.length());

            sendToAllClientsInRoom(interMessage, room, client);
        } else if (message.startsWith("#ison")) {

            String userId = message.substring(message.indexOf(" ") + 1, message.length());
            userId = userId.trim();
            System.out.println(">>" + userId);

            ison(userId, client);
        }
       else if (message.startsWith("#TTT")) {
            System.out.println("Starts with TTT");
            String target = "";
            String buttonName = "";
       

            String messageWOCommand = message.substring(message.indexOf(" ") + 1, message.length());
            target = messageWOCommand.substring(0, messageWOCommand.indexOf(" "));
            buttonName = messageWOCommand.substring(messageWOCommand.indexOf(" "), messageWOCommand.length());
           
       
            
            System.out.println("pmMessage "+buttonName);
            sendToAClientTTT(buttonName,target, client);
    }
        
        
    }

    public void sendToAClient(Object msg, String target, ConnectionToClient client) {
        Thread[] clientThreadList = getClientConnections();

        for (int i = 0; i < clientThreadList.length; i++) {
            ConnectionToClient user = ((ConnectionToClient) clientThreadList[i]);
            if (user.getInfo("userId").equals(target)) {

                try {
                    String message = client.getInfo("userId") + " PM: " + msg;
                    user.sendToClient(message);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
    
    public void sendToAClientTTT(Object msg1, String target, ConnectionToClient client) {
        Thread[] clientThreadList = getClientConnections();

        
        String message = msg1.toString().trim();
  
        
        for (int i = 0; i < clientThreadList.length; i++) {
            ConnectionToClient user = ((ConnectionToClient) clientThreadList[i]);
            if (user.getInfo("userId").equals(target)) {

                try {
                    System.out.println("TTT Message sent");
                    user.sendToClient("#TTT "+ target + " " +message);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
    
     
    
    

    public void ison(String userID, ConnectionToClient client) throws IOException {
        Thread[] clientThreadList = getClientConnections();
        boolean found = false;
        for (int i = 0; i < clientThreadList.length; i++) {
            ConnectionToClient user = ((ConnectionToClient) clientThreadList[i]);
            if (user.getInfo("userId").equals(userID)) {
                try {
                    String room = user.getInfo("room").toString();
                    client.sendToClient(userID + " is on and in room: " + room);
                    found = true;
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
        if (!found) {
            client.sendToClient("User is not on");
        }
    }

    public void sendToAllClientsInRoom(Object msg, String room, ConnectionToClient client) {
        Thread[] clientThreadList = getClientConnections();

        for (int i = 0; i < clientThreadList.length; i++) {
            ConnectionToClient user = ((ConnectionToClient) clientThreadList[i]);
            if (user.getInfo("room").equals(room)) {
                try {
                    user.sendToClient(msg);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    /**
     * This method overrides the one in the superclass. Called when the server
     * starts listening for connections.
     */
    protected void serverStarted() {
        System.out.println("Server listening for connections on port " + getPort());
    }

    /**
     * This method overrides the one in the superclass. Called when the server
     * stops listening for connections.
     */
    protected void serverStopped() {
        System.out.println("Server has stopped listening for connections.");
    }

    //Class methods ***************************************************
    /**
     * This method is responsible for the creation of the server instance (there
     * is no UI in this phase).
     *
     * @param args[0] The port number to listen on. Defaults to 5555 if no
     * argument is entered.
     */
    public static void main(String[] args) {
        int port = 0; //Port to listen on

        try {
            port = Integer.parseInt(args[0]); //Get port from command line
        } catch (Throwable t) {
            port = DEFAULT_PORT; //Set port to 5555
        }

        EchoServer sv = new EchoServer(port);

        try {
            sv.listen(); //Start listening for connections
        } catch (Exception ex) {
            System.out.println("ERROR - Could not listen for clients!");
        }
    }
}
//End of EchoServer class
