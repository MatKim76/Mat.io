import java.awt.Color;
import java.util.ArrayList;

public class Bonus 
{
	private int id;
	
	private int x;
	private int y;

	private int taille;
	private Color couleur;
	private boolean visible;

	public Bonus(int id, int x, int y, Color couleur, int taille)
	{
		this.id = id;

		this.x = x;
		this.y = y;

		this.couleur = couleur;
		this.taille = taille;

		this.visible = true;

		new DetecteurCollision().start();
		
	}

	public int getId() {
		return id;
	}

	public int getTaille() {
		return taille;
	}

	public Color getCouleur() {
		return couleur;
	}

	public int getNumCouleur()
	{
		int num = this.couleur.getRed() * 1_000_000 + this.couleur.getGreen() * 1_000 + this.couleur.getBlue();
		return num;
	}

	public boolean isVisible() {
		return visible;
	}

	public int getX() 
	{
		return x;
	}

	public int getY() 
	{
		return y;
	}

	public boolean checkCollision() 
    {
        int xPos, yPos;
		ArrayList<Joueur> clientTanks = GameBoardPanel.getClients();
		clientTanks.set(0, GameBoardPanel.getJoueur());

		//TODO: reglage probleme car le current joueur est pas dans la liste
		if(clientTanks == null) return false;

        for(int i = 0; i < clientTanks.size(); i++) 
		{
			if(clientTanks.get(i)!=null) 
			{
                Joueur j = clientTanks.get(i);

				//System.out.println(j.getId() + " " + j.getNom() + "     " + this.id );
				
				xPos = j.getX();
                yPos = j.getY();
                
                if((y >= yPos && y <= yPos + this.taille) && (x >= xPos && x <= xPos + this.taille)) 
                {
					System.out.println("collision bonus " + j.getId());

					j.addScore(this.taille * 5);
					this.visible = false;
                    
                    /*try {
                        Thread.sleep(200);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }*/

					Client.getGameClient().sendToServer(new MessageClient().RemoveBonus(this.id));

                    return true;
                }
            }
        }
        return false;
    }

	private class DetecteurCollision extends Thread 
	{
		
		public void run() 
		{
			while( !checkCollision() )
			{
				try {
					Thread.sleep(50);
				}catch(InterruptedException e){e.printStackTrace();}
			}
		}
	}
}
