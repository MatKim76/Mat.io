import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.net.Socket;
/*
 * InputManager.java
 *
 * Created on 25 ����, 2008, 02:57 �
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author Mohamed Talaat Saad
 */
public class InputManager implements KeyListener, Runnable
{
    private final int LEFT = 37;
    private final int RIGHT = 39;
    private final int UP = 38;
    private final int DOWN = 40;
    private static int status=0;    
    
    private Client client;

    private boolean[] keysPressed = new boolean[256];
    private Joueur joueur;


    /** Creates a new instance of InputManager */
    public InputManager(Joueur joueur) 
    {
        this.client=Client.getGameClient();
        this.joueur = joueur;

        Thread movementThread = new Thread(this);
        movementThread.start();
        
    }

    /*public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) 
    {

        if(e.getKeyCode()==LEFT)
        {
            if(tank.getDirection()==1|tank.getDirection()==3)
            {
                
                tank.moveLeft();
                
                client.sendToServer(new MessageClient().UpdatePacket(tank.getXposition(),
                          tank.getYposition(),tank.getTankID(),tank.getDirection()));
                
 
            }
            else if(tank.getDirection()==4)
            {
                tank.moveLeft();          
                client.sendToServer(new MessageClient().UpdatePacket(tank.getXposition(),
                            tank.getYposition(),tank.getTankID(),tank.getDirection()));
            }
        }
        else if(e.getKeyCode()==RIGHT)
        {
            if(tank.getDirection()==1|tank.getDirection()==3)
            {
                tank.moveRight();                        
                client.sendToServer(new MessageClient().UpdatePacket(tank.getXposition(),
                           tank.getYposition(),tank.getTankID(),tank.getDirection()));
                    
            }
            else if(tank.getDirection()==2)
            {
                tank.moveRight();
                client.sendToServer(new MessageClient().UpdatePacket(tank.getXposition(),
                             tank.getYposition(),tank.getTankID(),tank.getDirection()));
            }
        }
        else if(e.getKeyCode()==UP)
        {
            if(tank.getDirection()==2|tank.getDirection()==4)
            {
                tank.moveForward();                            
                client.sendToServer(new MessageClient().UpdatePacket(tank.getXposition(),
                          tank.getYposition(),tank.getTankID(),tank.getDirection()));
                        
            }
            else if(tank.getDirection()==1)
            {
                tank.moveForward();
                client.sendToServer(new MessageClient().UpdatePacket(tank.getXposition(),
                        tank.getYposition(),tank.getTankID(),tank.getDirection()));
                            
            }
        }
        else if(e.getKeyCode()==DOWN)
        {
            if(tank.getDirection()==2|tank.getDirection()==4)
            {
                tank.moveBackward();
               
                client.sendToServer(new MessageClient().UpdatePacket(tank.getXposition(),
                        tank.getYposition(),tank.getTankID(),tank.getDirection()));
                            
            }
            else if(tank.getDirection()==3)
            {
                tank.moveBackward();
                                    
                client.sendToServer(new MessageClient().UpdatePacket(tank.getXposition(),
                                tank.getYposition(),tank.getTankID(),tank.getDirection()));
                                
            }
        }
        else if(e.getKeyCode()==KeyEvent.VK_SPACE)
        {
            client.sendToServer(new MessageClient().ShotPacket(tank.getTankID()));
            tank.shot();
        }
    }

    public void keyReleased(KeyEvent e) {
    }*/

    @Override
	public void keyTyped(KeyEvent e) 
	{

	}

	@Override
	public void keyPressed(KeyEvent e) 
	{
		int keyCode = e.getKeyCode();
        keysPressed[keyCode] = true;
	}

	@Override
	public void keyReleased(KeyEvent e) 
	{
		int keyCode = e.getKeyCode();
        keysPressed[keyCode] = false;
	}

	public void run()
	{
		while(true)
		{
			if(keysPressed[68] || keysPressed[39]) {joueur.deplacer('E');}
			if(keysPressed[81] || keysPressed[37]) {joueur.deplacer('O');}
			if(keysPressed[90] || keysPressed[38]) {joueur.deplacer('N');}
			if(keysPressed[83] || keysPressed[40]) {joueur.deplacer('S');}

			if(keysPressed[32])
			{
				joueur.décharge();
			}
			else
			{
				joueur.charge();
			}

			//repaint();
            client.sendToServer(new MessageClient().UpdatePacket(joueur.getX(),joueur.getY(),joueur.getId() ));

			try 
			{
                Thread.sleep(10);//changer la valeur pour voir sur les pc IUT
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
		}
		
	}
    
}
