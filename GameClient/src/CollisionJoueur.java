import java.util.ArrayList;



public class CollisionJoueur implements Runnable
{
    private Joueur joueur;
    private ArrayList<Joueur> lstJoueur;

    private Client client;

    public CollisionJoueur(Joueur joueur, ArrayList<Joueur> lstJoueur)
    {
        this.joueur = joueur;
        this.lstJoueur = lstJoueur;

        this.client = Client.getGameClient();
    }
    
    @Override
    public void run() 
    {
        while (true)
        {
            //Test colision entre joueurs
            for (int j = 0; j < lstJoueur.size(); j++) 
            {
                if( lstJoueur.get(j) != null )
                {
                    Joueur joueur2 = lstJoueur.get(j);

                    if(joueur.equals(joueur2)) break;

                    if (collision(joueur, joueur2)) 
                    {
                        System.out.println("collision");

                        //si pas de bouclier alors tu meurs
                        if(!joueur.getBouclier())
                            client.sendToServer(new MessageClient().RemoveClientPacket( joueur.getId() ));

                        //si l'autre a pas de bouclier alors tu fais un kill
                        if(!joueur2.getBouclier())
                        {
                            joueur.addKill();
                            joueur.addScore((int)(joueur2.getScore() /2 ));
                        }
                    }
                }
            }

            try {
                Thread.sleep(50);
            } catch (Exception e) {
            }
        }
    }

    public boolean collision(Joueur joueur1, Joueur joueur2)
    {
        return (joueur1.getX() <= joueur2.getX() && joueur1.getX() + joueur1.getTaille() >= joueur2.getX() &&
                joueur1.getY() <= joueur2.getY() && joueur1.getY() + joueur1.getTaille() >= joueur2.getY() ||
                joueur2.getX() <= joueur1.getX() && joueur2.getX() + joueur2.getTaille() >= joueur1.getX() &&
                joueur2.getY() <= joueur1.getY() && joueur2.getY() + joueur2.getTaille() >= joueur1.getY());
    }
    
}
