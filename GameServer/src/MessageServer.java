/*
 * Protocol.java
 *
 * Created on 01 �����, 2008, 09:38 �
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author Mohamed Talaat Saad
 */
public class MessageServer {
    
    private String message="";
    /** Creates a new instance of Protocol */
    public MessageServer() {
    }
    
    public String IDPacket(int id)
    {
        message="ID"+id;
        return message;
    }

    public String NewClientPacket(String nom, int x, int y, int couleur, int id)
    {
        message="NewClient" + nom + "[" + x + "," + y + "]" + couleur + "|" + id;
        return message;   
    }

    public String newBonus(Bonus b)
    {
        message = "AddBonus" + b.getId() + "[" + b.getX() + "," + b.getY() + "]" + b.getNumCouleur() + "|" + b.getTaille();
        return message;
    }


}
