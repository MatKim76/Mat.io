import java.awt.Color;
import java.io.Serializable;

public class Joueur implements Comparable<Joueur>, Serializable
{
	public static int HAUTEUR = 400;
    public static int LARGEUR = 473;
    
    private static int VITESSE_BASE = 2;
	private static int TAILLE = 20;
	private static int MAX_BOUCLIER = 500;
	
	private int id;
    private String nom;
	private Color couleur;

	private int chargeBouclier;
	private boolean bouclier;
	private int maxBouclier;

	private int score;
	private int kill;

	private int x;
	private int y;

	private int vitesse;

	public Joueur(int id, String nom, Color couleur)
	{
		this.id = id;
        this.nom = nom;
		this.couleur = couleur;

		this.maxBouclier = Joueur.MAX_BOUCLIER;
		this.chargeBouclier = Joueur.MAX_BOUCLIER;
		this.bouclier = false;

		//this.x = (int)(Math.random()*map.getLongueur());
		//this.y = (int)(Math.random()*map.getHauteur());

        //this.x = (int)(Math.random()*Joueur.LARGEUR);
        //this.y = (int)(Math.random()*Joueur.HAUTEUR);

        this.x = 200;
        this.y = 200;

		this.vitesse = Joueur.VITESSE_BASE;
		this.score = 0;
		this.kill = 0;
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

	@Override
	public int compareTo(Joueur j) 
	{
		if(this.score == j.getScore()) return 0;
		else if(this.score > j.getScore()) return -1; //c'est inversé mais tkt
		else return 1;
	}

	public boolean equals(Joueur j)
	{
		if(j==null) return false;
		return (this.nom.equals(j.getNom()) && this.couleur.equals(j.getCouleur()));
	}
}
