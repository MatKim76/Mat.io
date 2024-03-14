import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;

public class GenerateurBonus extends Thread
{
	private Server serveur;
	private int num = 0;
	
	public GenerateurBonus(Server serv)
	{
		this.serveur = serv;
	}

	@Override
	public void run() 
	{
		while (true) 
		{
			int x = (int)(Math.random()*1000);
			int y = (int)(Math.random()*1000);

			int taille = (int)(Math.random()*15) + 5;
			
			Color couleur = new Color( (int)(Math.random()*256), (int)(Math.random()*256), (int)(Math.random()*256) );

			Bonus b = new Bonus(num++, x, y, couleur, taille);

			try {
				serveur.BroadCastMessage(new MessageServer().newBonus(b));
			} catch (IOException e) {
				e.printStackTrace();
			}

			try {
				Thread.sleep((int)(Math.random() * 5000));
			} catch (Exception e) {}
		}
	}
}
