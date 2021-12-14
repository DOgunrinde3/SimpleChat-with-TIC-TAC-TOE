/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

 

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author osborne
 */
public class GUIChat extends JFrame implements ChatIF  {

    final public static int DEFAULT_PORT = 5555;
    ChatClient client;

    private JButton closeB = new JButton("Close");
    private JButton openB = new JButton("Open");
    private JButton sendB = new JButton("Send");
    private JButton quitB = new JButton("Quit");
    private JButton joinB = new JButton("Join");
    private JButton loginB = new JButton("Login");
    private JButton pmB = new JButton("Private Message");
    private JButton PlayTicTacToeB = new JButton("Play TicTacToe");
    private JTextField joinTxF = new JTextField("");
    private JTextField loginTxF = new JTextField("");
    private JTextField pmTxF = new JTextField("");
    public JTextField TicTacToeTxF = new JTextField("");

    private JTextField portTxF = new JTextField("5555");
    private JTextField hostTxF = new JTextField("127.0.0.1");
    public JTextField messageTxF = new JTextField("");

    private JLabel portLB = new JLabel("Port: ", JLabel.RIGHT);
    private JLabel hostLB = new JLabel("Host: ", JLabel.RIGHT);
    private JLabel messageLB = new JLabel("Message: ", JLabel.RIGHT);
 JButton buttons[] = new JButton[9]; 
    int alternate = 0;
    private JTextArea messageList = new JTextArea();
    
    public TICTACGUI window;

    public GUIChat(String host, int port) {
        super("Simple Chat GUI");
        setSize(300, 400);

        setLayout(new BorderLayout(8, 8));
        JPanel bottom = new JPanel();
        JPanel top = new JPanel();
        add("Center", messageList);
        add("South", bottom);
         add("North", top);

        bottom.setLayout(new GridLayout(14, 2, 6, 6));
        bottom.add(hostLB);
        bottom.add(hostTxF);
        bottom.add(portLB);
        bottom.add(portTxF);
        bottom.add(messageLB);
        bottom.add(messageTxF);
        bottom.add(openB);
        bottom.add(sendB);
        bottom.add(closeB);
        bottom.add(quitB);
        bottom.add(joinB);
        bottom.add(joinTxF);
        bottom.add(loginB);
        bottom.add(loginTxF);
        bottom.add(pmB);
        bottom.add(pmTxF);
        bottom.add(PlayTicTacToeB);
        bottom.add(TicTacToeTxF);
        
        
  
         //top.setLayout(new GridLayout(1,1));
     

        sendB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                send();
                //display(messageTxF.getText() + "\n");
            }
        });
        
         quitB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               client.quit(); 
                //display(messageTxF.getText() + "\n");
            }
        });
         
         joinB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                 messageTxF.setText("#join " + joinTxF.getText());
                display(messageTxF.getText() + "\n");
                 send();
                //display(messageTxF.getText() + "\n");
            }
        });
         
          loginB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               messageTxF.setText("#login " + loginTxF.getText());
                display(messageTxF.getText() + "\n");
                send();
            }
        });
           pmB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                 messageTxF.setText("#pm " + pmTxF.getText());
                display(messageTxF.getText() + "\n");
                 send();
                //display(messageTxF.getText() + "\n");
            }
        });
           
            PlayTicTacToeB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               messageTxF.setText("#pm " + TicTacToeTxF.getText() + " would you like to play TicTacToe? ");
               display(messageTxF.getText() + "\n");
                TicTacToeTxF.setText(TicTacToeTxF.getText());
                 send();
                
                window = new TICTACGUI(client, GUIChat.this);
                window.setDefaultCloseOperation(TICTACGUI.EXIT_ON_CLOSE);
                //window.getContentPane().add(new TICTACGUI(client));
                window.setBounds(300,200,300,300);
                window.setVisible(true);
               
            }
        });

        setVisible(true);

        try {
            client = new ChatClient(host, port, this);
        } catch (IOException exception) {
            System.out.println("Error: Can't setup connection!"
                    + " Terminating client.");
            System.exit(1);
        }

    }

    public void display(String message) {
        messageList.insert(message, 0);
    }
    
   
    public void send(){
        try{
            String message = messageTxF.getText();
            client.handleMessageFromClientUI(message);
            
        }
        catch(Exception ioe){
            ioe.printStackTrace();
        }
        
    }
    
    
    
 

  


    public static void main(String[] args) {

        String host = "";
        int port = 0;  //The port number

        try {
            port = Integer.parseInt(args[1]);
        } catch (ArrayIndexOutOfBoundsException e) {
            port = DEFAULT_PORT;
        }
        System.out.println("PORT:" + port);

        try {
            host = "Enter your ip address";
        } catch (ArrayIndexOutOfBoundsException e) {
            host = "localhost";
        }

        GUIChat clientConsole = new GUIChat(host, port);
    }

}
