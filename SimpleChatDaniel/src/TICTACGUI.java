/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 *
 * @author ogunrinde1600
 */
public class TICTACGUI extends JFrame
  
{
    ChatClient client;
    public JButton buttons[] = new JButton[9]; 
    int alternate = 0;//if this number is a even, then put a X. If it's odd, then put an O
    public GUIChat mainChat;

    public TICTACGUI(ChatClient client, GUIChat gui)
    {
        this.client = client;
        this.mainChat = gui;
      setLayout(new GridLayout(3,3));
      initializebuttons(); 
      
    }
    
    public void initializebuttons()
    {
        for(int i = 0; i <= 8; i++)
        {
            buttons[i] = new JButton();
            buttons[i].setName("button"+i);
            buttons[i].setText("");
            buttons[i].addActionListener(new buttonListener());
            
            add(buttons[i]); //adds this button to JPanel (note: no need for JPanel.add(...)
                                //because this whole class is a JPanel already           
        }
    }
    public void resetButtons()
    {
        for(int i = 0; i <= 8; i++)
        {
            buttons[i].setText("");
        }
    }
    
// when a button is clicked, it generates an ActionEvent. Thus, each button needs an ActionListener. When it is clicked, it goes to this listener class that I have created and goes to the actionPerformed method. There (and in this class), we decide what we want to do.
    public class buttonListener implements ActionListener
    {
       
        public void actionPerformed(ActionEvent e) 
        {
            
            JButton buttonClicked = (JButton)e.getSource(); //get the particular button that was clicked
            
         
            
            try {
                
               
                client.sendToServer("#TTT "+ mainChat.TicTacToeTxF.getText() + " " + buttonClicked.getName());
                alternate++;
                
                
            } catch (IOException ex) {
                Logger.getLogger(TICTACGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            if(buttonClicked.getText().equals("X") || buttonClicked.getText().equals("O") )
            {
                JOptionPane.showConfirmDialog(null, "Can't place there");
               
                
            
            }
            
            if(alternate%2 == 0)
                buttonClicked.setText("O");
            else
                buttonClicked.setText("X");
            
            
            if(checkForWin() == true)
            {
                JOptionPane.showConfirmDialog(null, "Game Over.");
                resetButtons();
                try {
                    client.sendToServer("#TTT "+ mainChat.TicTacToeTxF.getText() + " " + "Over");
                } catch (IOException ex) {
                    Logger.getLogger(TICTACGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
                
            alternate++;
            
        }
        
        public boolean checkForWin()
        {
            /**   Reference: the button array is arranged like this as the board
             *      0 | 1 | 2
             *      3 | 4 | 5
             *      6 | 7 | 8
             */
            //horizontal win check
            if( checkAdjacent(0,1) && checkAdjacent(1,2) ) //no need to put " == true" because the default check is for true
                return true;
            else if( checkAdjacent(3,4) && checkAdjacent(4,5) )
                return true;
            else if ( checkAdjacent(6,7) && checkAdjacent(7,8))
                return true;
            
            //vertical win check
            else if ( checkAdjacent(0,3) && checkAdjacent(3,6))
                return true;  
            else if ( checkAdjacent(1,4) && checkAdjacent(4,7))
                return true;
            else if ( checkAdjacent(2,5) && checkAdjacent(5,8))
                return true;
            
            //diagonal win check
            else if ( checkAdjacent(0,4) && checkAdjacent(4,8))
                return true;  
            else if ( checkAdjacent(2,4) && checkAdjacent(4,6))
                return true;
            else 
                return false;
            
            
        }
        
        public boolean checkAdjacent(int a, int b)
        {
            if ( buttons[a].getText().equals(buttons[b].getText()) && !buttons[a].getText().equals("") )
                return true;
            else
                return false;
        }
        
    }
    
    public static void main(String[] args) 
    {
////        JFrame window = new JFrame("Tic-Tac-Toe");
////        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
////        window.getContentPane().add(new TICTACGUI());
////        window.setBounds(300,200,300,300);
////        window.setVisible(true);
    }

    /**
     * @return the player
     */
  
}

