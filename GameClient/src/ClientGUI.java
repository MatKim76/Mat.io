
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
/*
 * ClientGUI.java
 *
 * Created on 21 ����, 2008, 02:26 �
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;

/**
 *
 * @author Mohamed Talaat Saad
 */
public class ClientGUI extends JFrame implements ActionListener,WindowListener 
{
    /** Creates a new instance of ClientGUI */
    private static final Color[] COULEURS = {Color.BLUE, Color.CYAN, Color.DARK_GRAY, Color.GRAY, Color.GREEN, Color.LIGHT_GRAY, Color.MAGENTA, Color.ORANGE, Color.PINK, Color.RED, Color.YELLOW};

    private JLabel ipaddressLabel;
    private JLabel portLabel;
    
    private JTextField ipaddressText;
    private JTextField portText;
    
    private JButton registerButton;
    
    private JPanel registerPanel;
    private Client client;
    private Joueur clientJoueur;
    
    private JButton btnServeur;
	private JButton btnSearch;
    private JComboBox<String> serverList;
    
    int width=790,height=580;
    boolean isRunning=true;
    private GameBoardPanel boardPanel;
    
    private SoundManger soundManger;
    
    public ClientGUI(int a) 
    {
        setTitle("Mat.io");
        setSize(width,height);
        this.setLocationRelativeTo(null);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addWindowListener(this);

        registerPanel=new JPanel();
        registerPanel.setBackground(Color.YELLOW);
        registerPanel.setSize(width, height);
        //registerPanel.setLayout(new );
     
        ipaddressLabel=new JLabel("IP address: ");
        portLabel=new JLabel("Port: ");
        ipaddressText=new JTextField("localhost");
        portText=new JTextField("11111");
       
        registerButton=new JButton("Register");
        registerButton.addActionListener(this);
        //registerButton.setFocusable(true);
        
        this.btnServeur = new JButton("Serveur");
        this.btnServeur.addActionListener(this);

        this.btnSearch = new JButton("Recherche de serveur");
        this.btnSearch.addActionListener(this);

        this.serverList = new JComboBox<>();
        this.serverList.addItem("localhost");


        registerPanel.add(ipaddressLabel);
        registerPanel.add(ipaddressText);
        registerPanel.add(portLabel);
        registerPanel.add(portText);
        registerPanel.add(registerButton);

        registerPanel.add(this.btnSearch);
        registerPanel.add(this.btnServeur);
        registerPanel.add(this.serverList);

        client=Client.getGameClient();

        

        //this.boardPanel   .setVisible(false);
        //this.registerPanel.setVisible(true);
        
        this.add(registerPanel);
              
        setVisible(true);
    }

    private JPanel panelOuest;
    private JPanel panelEst;

    private JTable tableauServeur;
    private JScrollPane scrollPane;

    private JTextField txtServeur;

    private ServerGUI serverGui;

    public ClientGUI()
    {
        this.setTitle("Mat.io");
        this.setSize(width,height);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addWindowListener(this);

        //Création du panel ESt
        this.panelEst = new JPanel();
        this.panelEst.setBackground(Color.RED);
        this.panelEst.setBounds(500, 0, 290, 580);

        //Création du tableau de la liste des serveurs
        this.tableauServeur = new JTable(10, 1);
        this.tableauServeur.setValueAt("localhost", 0, 0);

        this.tableauServeur.setDragEnabled(false);
        this.tableauServeur.getColumnModel().getColumn(0).setHeaderValue("Serveurs disponibles :");
        this.tableauServeur.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.tableauServeur.setDefaultEditor(Object.class, null);

        this.scrollPane = new JScrollPane(this.tableauServeur);
        this.scrollPane.setPreferredSize(new Dimension(250, 200));
        
        this.panelEst.add(this.scrollPane);

        //Création du btn de recherche serveur
        this.btnSearch = new JButton("Rafraichir la liste");
        this.btnSearch.addActionListener(this);

        this.panelEst.add(this.btnSearch);

        this.panelEst.add(new JSeparator(SwingConstants.HORIZONTAL));

        //Création du TextField pour les autres adresses

        JPanel panelText = new JPanel();
        panelText.setBackground(Color.BLUE);
        panelText.setLayout(new GridLayout(2, 1));
        panelText.setPreferredSize(new Dimension(250, 50));

        this.txtServeur = new JTextField(15);

        panelText.add( new JLabel("Adresse IP :") );
        panelText.add(this.txtServeur);

        this.panelEst.add(panelText);

        //Création du bouton pour serveur
        this.btnServeur = new JButton("Lancer un serveur");
        this.btnServeur.addActionListener(this);

        this.panelEst.add(this.btnServeur);


        //Création du panel Ouest
        this.panelOuest = new JPanel();
        this.panelOuest.setBackground(Color.YELLOW);
        this.panelOuest.setBounds(0, 0, 500, 580);

        this.add(this.panelOuest);
        this.add(this.panelEst);

        this.setVisible(true);
    }
    
    public void actionPerformed(ActionEvent e) 
    {
        if(e.getSource() == registerButton)
        {
            registerButton.setEnabled(false);
            
            try 
            {
                //Création du Joueur
                String nomJoueur = JOptionPane.showInputDialog(null, "Veuillez choisir un pseudo :");
                while( nomJoueur.length() > 10 )
                    nomJoueur = JOptionPane.showInputDialog(null, "Pseudo trop long (10 cara max) :");
                
                int indexCouleur = (int)(Math.random()*ClientGUI.COULEURS.length);

                this.clientJoueur = new Joueur(1, nomJoueur, ClientGUI.COULEURS[indexCouleur], indexCouleur );
                boardPanel=new GameBoardPanel(clientJoueur,client,false);
                this.add(boardPanel);  

                String selectedServer = (String) this.serverList.getSelectedItem();
                client.register(selectedServer, 11111, clientJoueur, indexCouleur);
                
                //soundManger=new SoundManger();
                
                //Afficher le plateau
                this.boardPanel.setGameStatus(true);
                this.registerPanel.setVisible(false);
                this.boardPanel.setVisible(true);
                boardPanel.repaint();

                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                new ClientRecivingThread(client.getSocket()).start();

                registerButton.setFocusable(false);
                boardPanel.setFocusable(true);
            } 
            catch (IOException ex) 
            {
                JOptionPane.showMessageDialog(this,"The Server is not running, try again later!","Tanks 2D Multiplayer Game",JOptionPane.INFORMATION_MESSAGE);
                System.out.println("The Server is not running!");
                registerButton.setEnabled(true);
            }
        }

        if (e.getSource() == this.btnSearch)
		{
            this.serverList.removeAllItems();
			chercherServ();
        }

        if(e.getSource() == this.btnServeur )
        {
            new ServerGUI();
        }
        
    }

    public void windowOpened(WindowEvent e) 
    {

    }

    public void windowClosing(WindowEvent e) 
    {
       // int response=JOptionPane.showConfirmDialog(this,"Are you sure you want to exit ?","Tanks 2D Multiplayer Game!",JOptionPane.YES_NO_OPTION);
        Client.getGameClient().sendToServer(new MessageClient().ExitMessagePacket(clientJoueur.getId()));
    }
    public void windowClosed(WindowEvent e) {
        
    }

    public void windowIconified(WindowEvent e) {
    }

    public void windowDeiconified(WindowEvent e) {
    }

    public void windowActivated(WindowEvent e) {
    }

    public void windowDeactivated(WindowEvent e) {
    }
    
    public class ClientRecivingThread extends Thread
    {
        Socket clientSocket;
        DataInputStream reader;
        public ClientRecivingThread(Socket clientSocket)
        {
            this.clientSocket=clientSocket;
            try {
                reader=new DataInputStream(clientSocket.getInputStream());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            
        }
        public void run()
        {
            while(isRunning) 
            {
                String sentence="";
                try {
                    sentence = reader.readUTF();                
                } catch (IOException ex) {
                    ex.printStackTrace();
                    isRunning = false;
                }

                if(sentence.startsWith("ID"))
                {
                    int id=Integer.parseInt(sentence.substring(2));
                    clientJoueur.setId(id);
                    System.out.println("My ID= "+id);
                }
                else if(sentence.startsWith("NewClient"))
                {
                    int pos1 = sentence.indexOf('[');
                    int pos2 = sentence.indexOf(',');
                    int pos3 = sentence.indexOf(']');
                    int pos4 = sentence.indexOf('|');

                    String nom = sentence.substring(9, pos1);
                    int x = Integer.parseInt(sentence.substring(pos1+1, pos2));
                    int y = Integer.parseInt(sentence.substring(pos2+1, pos3));
                    int index = Integer.parseInt(sentence.substring(pos3+1, pos4));
                    int id= Integer.parseInt(sentence.substring(pos4+1, sentence.length()));

                    if(id!=clientJoueur.getId())
                        boardPanel.registerNewTank( new Joueur(id, nom, ClientGUI.COULEURS[index], index ));

                    System.out.println("new Client " + id);
                }   
                else if(sentence.startsWith("Update"))
                {
                    int pos1=sentence.indexOf(',');
                    int pos2=sentence.indexOf('|');
                    int pos3=sentence.indexOf('#');

                    int x=Integer.parseInt(sentence.substring(6, pos1));
                    int y=Integer.parseInt(sentence.substring(pos1+1, pos2));
                    int id=Integer.parseInt(sentence.substring(pos2+1, pos3));
                    boolean bool = Boolean.parseBoolean(sentence.substring(pos3+1, sentence.length())); 

                    if(id!=clientJoueur.getId())
                    {
                        boardPanel.getTank(id).setX(x);
                        boardPanel.getTank(id).setY(y);
                        boardPanel.getTank(id).setBouclier(bool);
                        boardPanel.repaint();
                    }
                    
                }
                else if(sentence.startsWith("Remove"))
                {
                    int id = Integer.parseInt(sentence.substring(6));
                  
                    if(id == clientJoueur.getId())
                    {
                        int response=JOptionPane.showConfirmDialog(null,"You die. Do you want to try again ?","Tanks 2D Multiplayer Game",JOptionPane.OK_CANCEL_OPTION);
                        if(response==JOptionPane.OK_OPTION)
                        {
                            //client.closeAll();
                            setVisible(false);
                            dispose();
                            
                            new ClientGUI();
                        }
                        else
                        {
                            System.exit(0);
                        }
                    }
                    else
                    {
                        boardPanel.removeTank(id);
                    }
                }
                else if(sentence.startsWith("Exit"))
                {
                    int id=Integer.parseInt(sentence.substring(4));
                  
                    if(id!=clientJoueur.getId())
                    {
                        boardPanel.removeTank(id);
                    }
                }   
            }
           
            try {
                reader.close();
                clientSocket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            
        }
    }

    public void chercherServ()
	{
		//j'espère que ca pose pas trop de problème car lance au max 50 Thread
		ExecutorService executor = Executors.newFixedThreadPool(50);
		
		String s = "di-";
		String s2 = "c-";
		int num;

        this.serverList.addItem("localhost");

		for(num = 715; num < 730; num++)
		{
			for(int pc = 0; pc < 30; pc++)
			{
				String serv1 = s + num + "-" + String.format("%02d", pc);
				String serv2 = s2 + serv1;
				
				executor.execute(new RechercheServeur(serv1, this.serverList));
                executor.execute(new RechercheServeur(serv2, this.serverList));
			}
		}
		System.out.println("Fin recherche serveur");

	}
    
}
