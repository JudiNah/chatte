package client;
import miainocient.ListenerClient;
import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.awt.Dimension;
import javax.swing.plaf.DimensionUIResource;

public class  Client extends JFrame{
        ListenerClient ecoute;
        JPanel pan1 = new JPanel();
        JPanel pan2 = new JPanel();
        JLabel label1 = new JLabel();
        JLabel label2 = new JLabel();
        public JButton bouton = new JButton();
        public JTextField field = new JTextField("");
        JTextArea chatArea = new JTextArea();
        Socket socket;
        ObjectOutputStream dataOut;
        ObjectInputStream dataIn;
        BufferedReader pen ;
        String message="";
        String serverIP;
        int port = 6789;

       public Client(){
        fenetrage();
        this.setTitle("Client");
        this.setVisible(true);
        label1.setVisible(true);
       }

       public void fenetrage(){
        field.setPreferredSize(new DimensionUIResource(200, 30));;
        field.setBounds(100, 100, 270, 30);
        this.setTitle("Client");
        this.setSize(350, 400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        bouton.setText("send");        
        bouton.addActionListener(new ListenerClient(this));
        bouton.setBounds(320,655,120,40);
        bouton.setBackground(new Color(7,94,84));
        bouton.setForeground(Color.WHITE);
        bouton.setFont(new Font("SAN_SERIF",Font.PLAIN,16));
        bouton.addActionListener(new ListenerClient(this));
        chatArea.setColumns(30);
        chatArea.setRows(10);
        chatArea.setLineWrap(true);
        chatArea.setPreferredSize(new Dimension(400,300)); 
        pan1.setPreferredSize(new Dimension(100, 100));
        pan1.setBackground(Color.gray);
        pan1.add(field);
        pan1.add(bouton);
        pan1.add(label1);
        pan1.add(chatArea);

        field.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                envoyer(field.getText());
	            field.setText("");
            }
        });
        this.setContentPane(pan1);
        this.setVisible(true);   
    }
    public void startRun(){
       try {
        label2.setText("Attemping connection...");
          try {
            socket = new Socket(InetAddress.getByName(serverIP),port);
          } catch (Exception e) {
          }
          label2.setText("Vous chatez avec:" +socket.getInetAddress().getHostName());
          dataOut = new ObjectOutputStream(socket.getOutputStream());
          dataIn = new ObjectInputStream(socket.getInputStream());
           dataOut.flush();
          readText();
       } catch (Exception e) {
       }    
    }
    public void readText() throws IOException{
        field.setEditable(true);
         do{
             try{
                message = (String) dataIn.readObject();
                chatArea.append("\n"+message);
            } catch(Exception e){
            }
        } while(!message.equals("Client - END"));
    }
    public void envoyer(String message){
        try{
            dataOut.writeObject("\n\t\t\t"+"You =>" + message);
            dataOut.flush();
            chatArea.append("\nMe - "+message);
        }catch(Exception e){
            chatArea.append("\n Unable to Send Message");
        }
    }    
}


    

