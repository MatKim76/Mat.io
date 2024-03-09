
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.*;
import javax.swing.border.BevelBorder;
/*
 * ClientGUI.java
 *
 * Created on 21 ����, 2008, 02:26 �
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
 import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Mohamed Talaat Saad
 */
public class ClientGUI extends JFrame implements ActionListener,WindowListener 
{
    /** Creates a new instance of ClientGUI */    
    private Client client;
    private Joueur clientJoueur;
    
    private JButton btnServeur;
	private JButton btnSearch;
    
    int width=790,height=580;
    boolean isRunning=true;
    private GameBoardPanel boardPanel;
    
    private SoundManger soundManger;

    private JPanel panelGeneral;
    private JPanel panelOuest;
    private JPanel panelEst;

    private JTable tableauServeur;
    private JScrollPane scrollPane;

    private JTextField txtServeur;

    private ServerGUI serveurGUI;

    private JButton btnChoixCouleur;
    private JButton btnChoixImage;

    private JLabel labelCouleur;
    private JLabel labelImage; 

    private JTextField txtPseudo;

    private JButton btnLancer;
    private JLabel labelErreur;

    public ClientGUI()
    {
        /*-------------------Frame---------------------*/

        this.setTitle("Mat.io");
        this.setSize(width,height);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addWindowListener(this);

        this.client = Client.getGameClient();

        this.panelGeneral = new JPanel();
        this.panelGeneral.setLayout(null);

        this.add(this.panelGeneral);

        /*-------------------Panel Est---------------------*/
        
        //Création du panel ESt
        this.panelEst = new JPanel();
        this.panelEst.setBackground(Color.RED);
        this.panelEst.setBounds(500, 0, 290, 580);
        this.panelEst.setLayout(null);

        //Création du tableau de la liste des serveurs
        this.tableauServeur = new JTable(0, 1);
        this.tableauServeur.setDragEnabled(false);
        this.tableauServeur.getColumnModel().getColumn(0).setHeaderValue("Serveurs IUT disponibles :");
        this.tableauServeur.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.tableauServeur.setDefaultEditor(Object.class, null);

        this.scrollPane = new JScrollPane(this.tableauServeur);
        this.scrollPane.setBounds(20, 20, 250, 150);
        
        this.panelEst.add(this.scrollPane);

        //Création du btn de recherche serveur
        this.btnSearch = new JButton("Rafraichir la liste");
        this.btnSearch.addActionListener(this);
        this.btnSearch.setBounds(20, 180, 250, 25);

        this.panelEst.add(this.btnSearch);

        //Création de la séparation
        JSeparator separator1 = new JSeparator(SwingConstants.HORIZONTAL);
        separator1.setBounds(0, 230, 120, 1);

        JSeparator separator2 = new JSeparator(SwingConstants.HORIZONTAL);
        separator2.setBounds(170, 230, 120, 1);

        JLabel labelOU = new JLabel("OU");
        labelOU.setBounds(135, 225, 30, 10);

        this.panelEst.add(separator1);
        this.panelEst.add(separator2);
        this.panelEst.add(labelOU);

        //Création du TextField pour les autres adresses
        JLabel labelIP = new JLabel("Adresse IP :");
        labelIP.setBounds(20, 250, 250, 20);

        this.txtServeur = new JTextField(15);
        this.txtServeur.setBounds(20, 270, 250, 20);

        this.panelEst.add( labelIP );
        this.panelEst.add(this.txtServeur);

        //Création nouveau séparateur
        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        separator.setBounds(0, 320, 290, 1);

        this.panelEst.add(separator);

        //Création du bouton pour serveur
        this.btnServeur = new JButton("Hoster un serveur");
        this.btnServeur.addActionListener(this);
        this.btnServeur.setBounds(20, 340, 250, 25);

        this.panelEst.add(this.btnServeur);

        //Création panel serveurGUI
        this.serveurGUI = new ServerGUI();
        this.serveurGUI.setBounds(20, 380, 250, 140);
        this.serveurGUI.setVisible(false);

        this.panelEst.add(this.serveurGUI);

        this.panelGeneral.add(this.panelEst);

        /*-------------------Panel Ouest---------------------*/
       
        //Création du panel Ouest
        this.panelOuest = new JPanel();
        this.panelOuest.setBackground(Color.YELLOW);
        this.panelOuest.setBounds(0, 0, 500, 580);
        this.panelOuest.setLayout(null);

        //Création coté de choix couleur
        this.labelCouleur = new JLabel();
        this.labelCouleur.setBackground(ChoixCouleur.getRandomCouleur());
        this.labelCouleur.setOpaque(true);
        this.labelCouleur.setBounds(20, 20, 200, 200);
        this.labelCouleur.setBorder(new BevelBorder(1, Color.BLACK, Color.BLACK));

        this.btnChoixCouleur = new JButton("Choisir une couleur");
        this.btnChoixCouleur.addActionListener(this);
        this.btnChoixCouleur.setBounds(20, 230, 200, 25);

        this.panelOuest.add(this.labelCouleur);
        this.panelOuest.add(this.btnChoixCouleur);

        //Création coté de choix Image
        this.labelImage = new JLabel();
        this.labelImage.setBackground(Color.BLUE);
        this.labelImage.setOpaque(true);
        this.labelImage.setBounds(280, 20, 200, 200);
        this.labelImage.setBorder(new BevelBorder(1, Color.BLACK, Color.BLACK));

        this.btnChoixImage = new JButton("Choisir une image");
        this.btnChoixImage.addActionListener(this);
        this.btnChoixImage.setBounds(280, 230, 200, 25);

        this.panelOuest.add(this.labelImage);
        this.panelOuest.add(this.btnChoixImage);

        //Création txt pour pseudo
        JLabel labelPseudo = new JLabel("Votre pseudo : ");
        labelPseudo.setBounds(20, 330, 200, 20);

        this.txtPseudo = new JTextField(15);
        this.txtPseudo.setBounds(20, 350, 280, 20);

        this.panelOuest.add(labelPseudo);
        this.panelOuest.add(this.txtPseudo);

        //Création du bouton de lancement
        this.btnLancer = new JButton("Lancer la partie");
        this.btnLancer.addActionListener(this);
        this.btnLancer.setBounds(20, 470, 460, 50);

        this.panelOuest.add(this.btnLancer);

        //Création label erreur
        this.labelErreur = new JLabel("");
        this.labelErreur.setBounds(20, 450, 460, 20);

        this.panelOuest.add(this.labelErreur);

        this.panelGeneral.add(this.panelOuest);

        this.setVisible(true);
    }
    
    public void actionPerformed(ActionEvent e) 
    {
        if(e.getSource() == this.btnLancer)
        {
            this.btnLancer.setEnabled(false);
            
            try 
            {
                //vérif de serveur
                String serveur = this.txtServeur.getText();
                if( serveur.equals("") )
                {
                    if( this.tableauServeur.getSelectedRow() == -1)
                    {
                        this.labelErreur.setText("Veuillez sélectionner un serveur");
                        this.btnLancer.setEnabled(true);
                        return;
                    }
                    else
                    {
                        serveur = (String) this.tableauServeur.getValueAt(this.tableauServeur.getSelectedRow(), 0);
                    }
                }

                //vérif pseudo
                String[] randomPseudo = {"Boudemousse", "Le Corbeau", "Chariot", "Navet", "Calin"}; 
                String pseudo = this.txtPseudo.getText();
                if (pseudo.equals(""))
                {
                    pseudo = randomPseudo[(int)(Math.random()*randomPseudo.length)];
                }
                
                //Création du Joueur
                Color coul = this.labelCouleur.getBackground();

                this.clientJoueur = new Joueur(1, pseudo, coul, ChoixCouleur.getIndex(coul) );
                this.boardPanel = new GameBoardPanel(clientJoueur,client,false);
                this.add(boardPanel);  

                this.client.register(serveur, 11111, clientJoueur, ChoixCouleur.getIndex(coul));
                
                //soundManger=new SoundManger();
                
                //Afficher le plateau
                this.boardPanel.setGameStatus(true);
                this.panelGeneral.setVisible(false);
                this.boardPanel.setVisible(true);
                this.boardPanel.repaint();

                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                new ClientRecivingThread(client.getSocket()).start();

                this.boardPanel.setFocusable(true);
            } 
            catch (IOException ex) 
            {
                JOptionPane.showMessageDialog(this,"Le serveur choisi n'est pas disponible !","Mat.io",JOptionPane.INFORMATION_MESSAGE);
                System.out.println("The Server is not running!");
                this.btnLancer.setEnabled(true);
                chercherServ();
            }
        }

        if (e.getSource() == this.btnSearch)
		{
			chercherServ();
        }

        if(e.getSource() == this.btnServeur )
        {
            this.serveurGUI.setVisible(!this.serveurGUI.isVisible());
        }

        if(e.getSource() == this.btnChoixCouleur)
        {
            new ChoixCouleur(this.labelCouleur);
        }
        
    }

    public void windowClosing(WindowEvent e) 
    {
       // int response=JOptionPane.showConfirmDialog(this,"Are you sure you want to exit ?","Tanks 2D Multiplayer Game!",JOptionPane.YES_NO_OPTION);
        Client.getGameClient().sendToServer(new MessageClient().ExitMessagePacket(clientJoueur.getId()));
    }

    public void windowOpened(WindowEvent e){}
    public void windowClosed(WindowEvent e) {}
    public void windowIconified(WindowEvent e) {}
    public void windowDeiconified(WindowEvent e) {}
    public void windowActivated(WindowEvent e) {}
    public void windowDeactivated(WindowEvent e) {}
    
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
                        boardPanel.registerNewTank( new Joueur(id, nom, ChoixCouleur.getColorFromString(ChoixCouleur.COULEURS[index]), index ));

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
                        /*int response=JOptionPane.showConfirmDialog(null,"You die. Do you want to try again ?","Mat.io",JOptionPane.OK_CANCEL_OPTION);
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
                        }*/

                        ClientGUI.this.btnLancer.setEnabled(true);

                        ClientGUI.this.boardPanel.setGameStatus(false);
                        ClientGUI.this.panelGeneral.setVisible(true);
                        ClientGUI.this.boardPanel.setVisible(false);

                        ClientGUI.this.panelGeneral.setFocusable(true);
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

        ArrayList<String> lstServ = new ArrayList<String>();

        executor.execute(new RechercheServeur("localhost", lstServ));

		for(num = 715; num < 730; num++)
		{
			for(int pc = 0; pc < 30; pc++)
			{
				String serv1 = s + num + "-" + String.format("%02d", pc);
				String serv2 = s2 + serv1;
				
				executor.execute(new RechercheServeur(serv1, lstServ));
                executor.execute(new RechercheServeur(serv2, lstServ));
			}
		}

        if(lstServ.size() > 0)
        {
            DefaultTableModel model = (DefaultTableModel) tableauServeur.getModel();
            model.setRowCount(0);

            for(int i = 0; i < lstServ.size(); i++) 
            {
                model.addRow(new Object[]{lstServ.get(i)});
            }
        }

		System.out.println("Fin recherche serveur");
	}
}
