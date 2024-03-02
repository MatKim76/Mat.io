import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
/*
 * GameBoardPanel.java
 *
 * Created on 25 ����, 2008, 09:21 �
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author Mohamed Talaat Saad
 */
public class GameBoardPanel extends JPanel {
    
    /** Creates a new instance of GameBoardPanel */
    private Joueur joueur;
    private int width=609;
    private int height=523;
    private ArrayList<Joueur> joueurs;
    private boolean gameStatus;


    public GameBoardPanel(Joueur joueur,Client client, boolean gameStatus) 
    {
        this.joueur = joueur;
        this.gameStatus = gameStatus;
        setSize(width,height);
        setBounds(-50,0,width,height);
        addKeyListener(new InputManager(joueur));
        setFocusable(true);
        
        joueurs = new ArrayList<Joueur>(100);
        
        for(int i=0;i<100;i++)
        {
            joueurs.add(null);
        }
   
    }
    public void paintComponent(Graphics gr) {
        super.paintComponent(gr);
        Graphics2D g=(Graphics2D)gr;
 
        g.setColor(Color.BLACK);
        g.fillRect(0,0, getWidth(),getHeight());
        
        g.setColor(Color.GREEN);
        g.fillRect(70,50, getWidth()-100,getHeight());
        //g.drawImage(new ImageIcon("Images/bg.jpg").getImage(),70,50,null);
        g.setColor(Color.YELLOW);
        g.setFont(new Font("Comic Sans MS",Font.BOLD,25));
        g.drawString("Tanks 2D Multiplayers Game",255,30);
        if(gameStatus) 
        {
            //g.drawImage(tank.getBuffImage(),tank.getXposition(),tank.getYposition(),this);
            g.setColor(joueur.getCouleur());
            g.fillRect(joueur.getX(), joueur.getY(), joueur.getTaille(), joueur.getTaille());
            //TODO afficher bouclier

            /*for(int j=0;j<1000;j++)
            {
                if(tank.getBomb()[j]!=null) 
                {
                    if(tank.getBomb()[j].stop==false){
                        g.drawImage(tank.getBomb()[j].getBomBufferdImg(),tank.getBomb()[j].getPosiX(),tank.getBomb()[j].getPosiY(),this);
                    }
                }
            }*/
            for(int i=1;i<joueurs.size();i++) 
            {
                if(joueurs.get(i)!=null)
                {
                    g.setColor(joueur.getCouleur());
                    g.fillRect(joueurs.get(i).getX(), joueurs.get(i).getY(), joueurs.get(i).getTaille(), joueurs.get(i).getTaille());
                }
                    
                    //g.drawImage(tanks.get(i).getBuffImage(),tanks.get(i).getXposition(),tanks.get(i).getYposition(),this);
                
                /*for(int j=0;j<1000;j++)
                {
                    if(tanks.get(i)!=null)
                    {
                        if(tanks.get(i).getBomb()[j]!=null) 
                        {
                            if(tanks.get(i).getBomb()[j].stop==false){
                            g.drawImage(tanks.get(i).getBomb()[j].getBomBufferdImg(),tanks.get(i).getBomb()[j].getPosiX(),tanks.get(i).getBomb()[j].getPosiY(),this);
                            }
                        }
                    }
                }*/
            }

        }
        
        repaint();
    }

    //renommer les machins
    public void registerNewTank(Joueur newTank)
    {
        joueurs.set(newTank.getId(),newTank);
    }
    public void removeTank(int tankID)
    {
        joueurs.set(tankID,null);
    }
    public Joueur getTank(int id)
    {
        return joueurs.get(id);
    }
    public void setGameStatus(boolean status)
    {
        gameStatus=status;
    }
  
    public static ArrayList<Joueur> getClients()
    {
        return joueurs;
    }
}
