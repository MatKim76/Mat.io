import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.SocketException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
/*
 * ServerGUI.java
 *
 * Created on 07 �����, 2008, 06:32 �
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
import javax.swing.JPanel;

/**
 *
 * @author Mohamed Talaat Saad
 */
public class ServerGUI extends JPanel implements ActionListener {
    
    private JButton startServerButton;
    private JButton stopServerButton;
    private JLabel statusLabel;
    
    private Server server;
    /** Creates a new instance of ServerGUI */
    public ServerGUI() 
    {
        //setTitle("Game Server GUI");
        //setBounds(350,200,300,200);
        //this.setLocationRelativeTo(null);
        
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        setLayout(null);
        startServerButton=new JButton("Lancer Serveur");
        startServerButton.setBounds(30,10,190,25);
        startServerButton.addActionListener(this);
        
        stopServerButton=new JButton("Arrêter Serveur");
        stopServerButton.setBounds(30,50,190,25);
        stopServerButton.addActionListener(this);
        stopServerButton.setEnabled(false);
        
        statusLabel=new JLabel();
        statusLabel.setBounds(30,90,200,25);
        
        this.add(statusLabel);
        this.add(startServerButton);
        this.add(stopServerButton);

        
        
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) 
    {
        if(e.getSource()==startServerButton)
        {
            try
            {
                server = new Server();
            } catch (SocketException ex) {
                ex.printStackTrace();
            }
            
            server.start();
            startServerButton.setEnabled(false);
            stopServerButton.setEnabled(true);
            statusLabel.setText("Le Serveur est en route.....");
        }
        
        if(e.getSource()==stopServerButton && server != null)
        {
            try 
            {
                server.stopServer();
                statusLabel.setText("Arrêt du Serveur.");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                //System.exit(0);
                startServerButton.setEnabled(true);
                stopServerButton.setEnabled(false);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    
}
