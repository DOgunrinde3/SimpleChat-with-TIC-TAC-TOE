
import java.io.*;

/**
 * This class overrides some of the methods defined in the abstract superclass
 * in order to give more functionality to the client.
 *
 *
 */
public class ChatClient extends AbstractClient {
    //Instance variables **********************************************

    /**
     * The interface type variable. It allows the implementation of the display
     * method in the client.
     */
    ChatIF clientUI;

    //Constructors ****************************************************
    /**
     * Constructs an instance of the chat client.
     *
     * @param host The server to connect to.
     * @param port The port number to connect on.
     * @param clientUI The interface type variable.
     */
    public ChatClient(String host, int port, ChatIF clientUI)
            throws IOException {
        super(host, port); //Call the superclass constructor
        this.clientUI = clientUI;
        openConnection();
    }

    //Instance methods ************************************************
    /**
     * This method handles all data that comes in from the server.
     *
     * @param msg The message from the server.
     */
    public void handleMessageFromServer(Object msg) {
        clientUI.display(msg.toString());
         


        if (msg.toString().startsWith("#TTT ")) {
            int alternate = 0;
            String target = "";
            String buttonName = "";
           
            
            String messageWOCommand = msg.toString().substring(msg.toString().indexOf(" ") + 1, msg.toString().length());
            target = messageWOCommand.substring(0, messageWOCommand.indexOf(" "));
            buttonName = messageWOCommand.substring(messageWOCommand.indexOf(" "), messageWOCommand.length());
            

            System.out.println(buttonName);
        
            buttonName = buttonName.trim();
            
           

            if (buttonName.equals("button0")) {
                 if(alternate%2 == 0){
                ((GUIChat) clientUI).window.buttons[0].setText("O");
                 }
                 
                 else{ ((GUIChat) clientUI).window.buttons[0].setText("X");}
                 
                  alternate++;
      
            }

            if (buttonName.equals("button1")) {
             
                if(alternate%2 == 0){
                ((GUIChat) clientUI).window.buttons[1].setText("O");
                 }
                 
                 else{ ((GUIChat) clientUI).window.buttons[1].setText("X");}
                
               alternate++;
              
            }
            if (buttonName.equals("button2")) {
            
               if(alternate%2 == 0){
                ((GUIChat) clientUI).window.buttons[2].setText("O");
                 }
                 
                 else{ ((GUIChat) clientUI).window.buttons[2].setText("X");}
               
                alternate++;
            }
               
            if (buttonName.equals("button3")) {
       
                if(alternate%2 == 0){
                ((GUIChat) clientUI).window.buttons[3].setText("O");
                 }
                 
                 else{ ((GUIChat) clientUI).window.buttons[3].setText("X");}
                 alternate++;
               
            }
            if (buttonName.equals("button4")) {
              
                if(alternate%2 == 0){
                ((GUIChat) clientUI).window.buttons[4].setText("O");
                 }
                 
                 else{ ((GUIChat) clientUI).window.buttons[4].setText("X");}
                 alternate++;
             
            }
            if (buttonName.equals("button5")) {
             
                if(alternate%2 == 0){
                ((GUIChat) clientUI).window.buttons[5].setText("O");
                 }
                 
                 else{ ((GUIChat) clientUI).window.buttons[5].setText("X");}
                 alternate++;

            }
            if (buttonName.equals("button6")) {
              
                    if(alternate%2 == 0){
                ((GUIChat) clientUI).window.buttons[6].setText("O");
                 }
                 
                 else{ ((GUIChat) clientUI).window.buttons[6].setText("X");}
                     alternate++;
        
            }
            if (buttonName.equals("button7")) {
               
                
                    if(alternate%2 == 0){
                ((GUIChat) clientUI).window.buttons[7].setText("O");
                 }
                 
                 else{ ((GUIChat) clientUI).window.buttons[7].setText("X");}
                     alternate++;
                   
            
            }
            if (buttonName.equals("button8")) {
                
                    if(alternate%2 == 0){
                ((GUIChat) clientUI).window.buttons[8].setText("O");
                 }
                 
                 else{ ((GUIChat) clientUI).window.buttons[8].setText("X");}
                     alternate++;

            }
            
            if (buttonName.equals("Over")) {
             
               for(int i = 0; i <= 8; i++)
        {
            ((GUIChat) clientUI).window.buttons[i].setText("");
        }
            }
        }
        
        
    }
    
     
    

    /**
     * This method handles all data coming from the UI
     *
     * @param message The message from the UI.
     */
    public void handleMessageFromClientUI(String message) {
        try {

            if (message.startsWith("#quit")) {
                quit();
            } else if (message.startsWith("#logoff")) {
                closeConnection();
            } else if (message.startsWith("#sethost")) {
                String temp = message.substring(message.indexOf("<") + 1, message.indexOf(">"));
                setHost(temp);
            } else if (message.startsWith("#setport")) {
                int temp = Integer.parseInt(message.substring(message.indexOf("<") + 1, message.indexOf(">")));
                setPort(temp);
            } else if (message.startsWith("#login")) {
                if (!isConnected()) {
                    openConnection();
                }
                sendToServer(message);
            } else if (message.startsWith("#getport")) {
                clientUI.display(Integer.toString(getPort()));
            } else if (message.startsWith("#gethost")) {
                clientUI.display(String.valueOf(getHost()));
            } else if (message.startsWith("#pm")) {
                sendToServer(message);
            } else if (message.startsWith("#join")) {
                sendToServer(message);
            } else if (message.startsWith("#intercom")) {
                sendToServer(message);
            } else if (message.startsWith("#yell")) {
                sendToServer(message);
            } else if (message.startsWith("#ison")) {
                sendToServer(message);
            } else {
                sendToServer(message);
            }

        } catch (IOException e) {
            clientUI.display("Could not send message to server.  Terminating client.");
            quit();
        }
    }

    /**
     * This method terminates the client.
     */
    public void quit() {
        try {
            closeConnection();
        } catch (IOException e) {
        }
        System.exit(0);
    }

    public void handleCommand(String s) {
        try {
            if (s.startsWith("#quit")) {
                quit();
            } else if (s.startsWith("#login")) {
                sendToServer(s);
            } else if (s.startsWith("#pm")) {
                sendToServer(s);
            } else if (s.startsWith("#TTT")) {
                sendToServer(s);
            }

        } catch (IOException e) {
            clientUI.display("Could not send message to server. Terminating Client");
            quit();
        }
    }
}
//End of ChatClient class
