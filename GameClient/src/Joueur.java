import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;

public class Joueur implements Comparable<Joueur>, Serializable
{
	public static int HAUTEUR = 1000;
    public static int LARGEUR = 1000;
    
    private static int VITESSE_BASE = 2;
	private static int TAILLE = 20;
	private static int MAX_BOUCLIER = 500;
	
	private int id;
    private String nom;
	private Color couleur;
	private int indexCouleur;

	private int chargeBouclier;
	private boolean bouclier;
	private int maxBouclier;

	private int score;
	private int kill;

	private int x;
	private int y;

	private int vitesse;

	public Joueur(int id, String nom, Color couleur, int indexCouleur)
	{
		this.id = id;
        this.nom = nom;
		this.couleur = couleur;
		this.indexCouleur = indexCouleur;

		this.maxBouclier = Joueur.MAX_BOUCLIER;
		this.chargeBouclier = Joueur.MAX_BOUCLIER;
		this.bouclier = false;

		//this.x = (int)(Math.random()*map.getLongueur());
		//this.y = (int)(Math.random()*map.getHauteur());

        this.x = (int)(Math.random()*Joueur.LARGEUR);
        this.y = (int)(Math.random()*Joueur.HAUTEUR);

		this.vitesse = Joueur.VITESSE_BASE;
		this.score = 0;
		this.kill = 0;

		new DetecteurCollision().start();
	}

	public void décharge()
	{
		if(chargeBouclier >= 5)
		{
			chargeBouclier -= 5;
			this.bouclier = true;
			this.vitesse = Joueur.VITESSE_BASE*2;
			return;
		}
		this.vitesse = Joueur.VITESSE_BASE;
		this.bouclier = false;
	}

	public void charge()
	{
		this.bouclier = false;
		this.vitesse = Joueur.VITESSE_BASE;

		if(chargeBouclier < this.maxBouclier)
			this.chargeBouclier++;
	}

	public void deplacer(char dir)
	{
		switch(dir)
		{
			case 'N' -> this.y -= vitesse;
			case 'S' -> this.y += vitesse;
			case 'E' -> this.x += vitesse;
			case 'O' -> this.x -= vitesse;
		}

		if(this.x < 0) this.x = 0;
		if(this.x > Joueur.LARGEUR - Joueur.TAILLE) this.x = Joueur.LARGEUR - Joueur.TAILLE;
		if(this.y < 0) this.y = 0;
		if(this.y > Joueur.HAUTEUR - Joueur.TAILLE) this.y = Joueur.HAUTEUR - Joueur.TAILLE;
	}

	public void addKill()
	{
		this.kill++;
	}
	
	public void addScore(int s)
	{
		this.score+=s;
	}

	public String toString()
	{
		return String.format("%-10s", this.nom ) + "  " + String.format("%5d", this.score);
	}

    public void setX(int x) {this.x = x;}
    public void setY(int y) {this.y = y;}
    public void setId(int id) {this.id=id;}
    public void setBouclier(boolean bool) {this.bouclier=bool;}
	public void setIndexCouleur(int index) {this.indexCouleur = index;}

	public String getNom() {return this.nom;}
	public int getX() {return this.x;}
	public int getY() {return this.y;}
	public Color getCouleur() {return this.couleur;}
	public int getTaille() {return Joueur.TAILLE;}
	public boolean getBouclier() {return this.bouclier;}
	public int getNbBouclier() {return this.chargeBouclier;}
	public int getNbMaxBouclier() {return this.maxBouclier;}
	public int getScore() {return this.score;}
	public int getKill() {return this.kill;}
    public int getId() {return this.id;}
	public int getIndexCouleur() {return this.indexCouleur;}

	@Override
	public int compareTo(Joueur j) 
	{
		if(this.score > j.getScore()) return -1; 
		if(this.score < j.getScore()) return 1;

		if(this.id > j.getId()) return -1;
		return 1;
	}

	public boolean equals(Joueur j)
	{
		if(j==null) return false;
		return (this.id == j.getId() && this.nom.equals(j.getNom()) && this.couleur.equals(j.getCouleur()));
	}


	public boolean checkCollision() 
    {
        int xPos, yPos;
		ArrayList<Joueur>clientTanks = GameBoardPanel.getClients();

		if(clientTanks == null) return false;

        for(int i=1; i<clientTanks.size(); i++) 
		{
			if(clientTanks.get(i)!=null) 
			{
                Joueur j = clientTanks.get(i);

				if(this.equals(j))break;

				//System.out.println(j.getId() + " " + j.getNom() + "     " + this.id +  " " + this.nom);
				
				xPos = j.getX();
                yPos = j.getY();
                
                if((y >= yPos && y <= yPos + Joueur.TAILLE) && (x >= xPos && x <= xPos+ Joueur.TAILLE)) 
                {
                    
					System.out.println("collision " + this.id + " " + j.getId());

					//si pas de bouclier alors tu meurs
					if( !bouclier )
						Client.getGameClient().sendToServer(new MessageClient().RemoveClientPacket( this.id ));  

					//si l'autre a pas de bouclier alors tu fais un kill
					if( !j.getBouclier() )
					{
						this.addKill();
						this.addScore((int)(j.getScore() /2 ));
						System.out.println("kill");
					}
                    
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }

                    return true;
                }
            }
        }
        return false;
    }

	//TODO faire une classe privée pour les coliision ?

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
