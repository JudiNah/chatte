package miainocient;
import client.Client;
import java.awt.event.*;
public class ListenerClient implements ActionListener{
    Client c ;
    public ListenerClient(Client c) {
        this.c=c;
    }
    public void actionPerformed(ActionEvent e) {
        if(this.c.bouton.getText() == "send"){
            this.c.envoyer(this.c.field.getText());
            this.c.field.setText("");
        }
    }
}