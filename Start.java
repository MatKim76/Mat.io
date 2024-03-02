import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Start extends JFrame implements ActionListener
{
    private JPanel panel;
    private JButton btnServeur;
    private JButton btnClient;

    public Start() 
    {
        this.setSize(200, 150);
        
        this.panel = new JPanel();
        this.btnClient = new JButton("Client");
        this.btnServeur = new JButton("Serveur");

        this.add(this.panel);
        this.panel.add(this.btnClient);
        this.panel.add(this.btnServeur);
        
        this.btnClient.addActionListener(this);
        this.btnServeur.addActionListener(this);

        this.setVisible(true);

    }
    
    public static void main(String args[]) throws IOException
    {
        new Start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == this.btnClient)
        {
            ClientGUI client = new ClientGUI();
        }

        if(e.getSource() == this.btnServeur)
        {
            ServerGUI server = new ServerGUI();
        }
    }
    
}
