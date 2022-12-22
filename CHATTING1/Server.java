import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.awt.Dimension;
import javax.swing.plaf.DimensionUIResource;
public class Server extends JFrame{
   // ListenerServer ecoute ;
    JPanel pan1 = new JPanel();
    JPanel pan2 = new JPanel();
    JLabel label1 = new JLabel();
    JLabel label2 = new JLabel();
    JButton bouton = new JButton();
    JTextField field = new JTextField("");
    JTextArea chatArea = new JTextArea();
    Socket socket;
    ServerSocket serverSocket;
    ObjectOutputStream dataOut;
    ObjectInputStream dataIn;
    BufferedReader pen ;
    String message="";
    int totalClients = 100;
    int port = 6789;
    public Server(){
        fenetrage();
        this.setContentPane(pan1);
        this.setVisible(true);
        label1.setVisible(true);  
    }
    public void startRun(){
    try
    {
        serverSocket=new ServerSocket(port, totalClients);
        try{
            label1.setText(" Waiting for a client...");
            socket=serverSocket.accept();
            label1.setText("Serveur"+socket.getInetAddress().getHostName());
            dataOut = new ObjectOutputStream(socket.getOutputStream());
            // dataOut.flush();
            dataIn = new ObjectInputStream(socket.getInputStream());
        while (true) {
            readText();
            }
        }catch(EOFException eofException){
        }
    }catch(IOException ioException){
        ioException.printStackTrace();
    }
}
public void readText() throws IOException{
    String message="";    
    field.setEditable(true);
    do{
        try{
             message = (String) dataIn.readObject();
            chatArea.append("\n"+message);
        }
        catch(Exception e){}
    } while(!message.equals("Client - END"));
}
    public void fenetrage(){
        field.setPreferredSize(new DimensionUIResource(200, 30));;
        field.setBounds(100, 100, 270, 30);
        bouton.setBounds(310, 50, 80, 30);
        bouton.setText("send");
       // bouton.addActionListener(new ListenerServer(this));
        bouton.setBounds(320,655,120,40);
        bouton.setBackground(new Color(7,94,84));
        bouton.setForeground(Color.WHITE);
        bouton.setFont(new Font("SAN_SERIF",Font.PLAIN,16));
        chatArea.setColumns(30);
        chatArea.setRows(10);
        chatArea.setLineWrap(true);
        chatArea.setPreferredSize(new Dimension(400,300)); 
        pan1.setPreferredSize(new Dimension(200, 200));
        pan1.setBackground(Color.gray);
        pan1.add(field);
        pan1.add(bouton);
        pan1.add(label1);
        pan1.add(chatArea);
        this.setTitle("Server");
        this.setSize(350, 400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);  

    field.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            envoyer(field.getText());
            field.setText("");
        }
    });  
}
public void envoyer(String message){
    try{
        System.out.println("cc"+ dataOut);
        dataOut.writeObject("\n\t\t\t"+"You => " + message);
        System.out.println("njj");
        dataOut.flush();
        chatArea.append("\nMoi - "+message);
    }catch(Exception e){
        System.out.println(e);
        chatArea.append("\n Unable to Send Message");
    }
}    
}