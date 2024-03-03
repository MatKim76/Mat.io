import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Map;

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
        
        joueurs = new ArrayList<Joueur>(50);
        
        for(int i=0;i<100;i++)
        {
            joueurs.add(null);
        }
   
    }
    public void paintComponent(Graphics gr) {
        super.paintComponent(gr);
        Graphics2D g=(Graphics2D)gr;
 
        //g.setColor(Color.BLACK);
        //g.fillRect(0,0, getWidth(),getHeight());
        
        //g.setColor(Color.GREEN);
        //g.fillRect(70,50, getWidth()-100,getHeight());
        //g.drawImage(new ImageIcon("Images/bg.jpg").getImage(),70,50,null);
        //g.setColor(Color.YELLOW);
        //g.setFont(new Font("Comic Sans MS",Font.BOLD,25));
        //g.drawString("Tanks 2D Multiplayers Game",255,30);

        if(gameStatus) 
        {
            // Récupérer la position du joueur
            int joueurX = this.joueur.getX();
            int joueurY = this.joueur.getY();
        
            // Calculer les coordonnées de dessin du joueur pour qu'il soit au centre du panel
            int joueurDessinX = getWidth() / 2 - joueurX - joueur.getTaille()/2;
            int joueurDessinY = getHeight() / 2 - joueurY - joueur.getTaille()/2;

            int posX = joueurX + joueurDessinX;
            int posY = joueurY + joueurDessinY;

            // Dessin du joueur controler
            g.setColor(joueur.getCouleur());
            g.fillRect(posX, posY, joueur.getTaille(), joueur.getTaille());
            g.drawString(joueur.getNom() + "", posX + 6 - joueur.getNom().length() * 3, posY + joueur.getTaille() + 10);

            if (joueur.getBouclier()) {
                g.setColor(Color.BLUE);
                g.drawRect(posX - 5, posY - 5, joueur.getTaille() + 10, joueur.getTaille() + 10);
            }
        
            // Dessiner les autres éléments de la carte en ajustant leurs coordonnées de dessin
            for(int i=1;i<joueurs.size();i++)  
            {
                if(joueurs.get(i)!=null)
                {
                    int dessinX = joueurDessinX + joueurs.get(i).getX();
                    int dessinY = joueurDessinY + joueurs.get(i).getY();
            
                    // Dessiner le joueur à sa nouvelle position calculée
                    g.setColor(joueurs.get(i).getCouleur());
                    g.fillRect(dessinX, dessinY, joueurs.get(i).getTaille(), joueurs.get(i).getTaille());
                    g.drawString(joueurs.get(i).getNom() + "", dessinX + 6 - joueurs.get(i).getNom().length() * 3, dessinY + joueurs.get(i).getTaille() + 10);
            
                    if (joueurs.get(i).getBouclier()) {
                        g.setColor(Color.BLUE);
                        g.drawRect(dessinX - 5, dessinY - 5, joueurs.get(i).getTaille() + 10, joueurs.get(i).getTaille() + 10);
                    }
                }
            }

            // Dessin des bonus
            /*for (Bonus b : this.ctrl.getBonus()) 
            {
                int dessinX = joueurDessinX + b.getX();
                int dessinY = joueurDessinY + b.getY();
        
                // Dessiner le bonus à sa nouvelle position calculée
                g.setColor(b.getCouleur());
                g.fillRect(dessinX, dessinY, b.getTaille(), b.getTaille());
            }*/
        
            // Dessiner d'autres éléments de la carte (bordures, etc.) en ajustant leurs coordonnées de dessin
            int dessinBordureX = joueurDessinX;
            int dessinBordureY = joueurDessinY;
        
            g.setColor(Color.BLACK);
            g.drawLine(dessinBordureX, dessinBordureY, dessinBordureX, dessinBordureY + Joueur.HAUTEUR);
            g.drawLine(dessinBordureX, dessinBordureY, dessinBordureX + Joueur.LARGEUR, dessinBordureY);
            g.drawLine(dessinBordureX + Joueur.LARGEUR, dessinBordureY, dessinBordureX + Joueur.LARGEUR, dessinBordureY + Joueur.HAUTEUR);
            g.drawLine(dessinBordureX, dessinBordureY + Joueur.HAUTEUR, dessinBordureX + Joueur.LARGEUR, dessinBordureY + Joueur.HAUTEUR);
        
            // Dessiner d'autres éléments non liés à la carte (informations, etc.) normalement
            g.drawString("score : " + this.joueur.getScore(), 0, 20);
            g.drawString("kill : " + this.joueur.getKill(), 0, 35);

            //affichage du leaderboard
            /*joueurs.sort(null);
            for (int i=0 ; i < joueurs.size() ; i++)
            {
                if(joueurs.get(i)!=null)
                {
                    if(i > 10 ) break;

                    g.drawString( (i+1) + ". " + joueurs.get(i).toString(), this.getWidth() - 100, 20 + i*15);
                }
            }*/

            // affichage de charge bouclier
            g.setColor(Color.BLUE);
            g.fillRect( 0, this.getHeight() - 10, (int)(this.getWidth() * this.joueur.getNbBouclier()/this.joueur.getNbMaxBouclier()), 15);
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
  
    public ArrayList<Joueur> getClients()
    {
        return joueurs;
    }
}
