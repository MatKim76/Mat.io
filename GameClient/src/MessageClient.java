/*
 * Protocol.java
 *
 * Created on 25 ����, 2008, 10:32 �
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author Mohamed Talaat Saad
 */
public class MessageClient 
{
    /**
     * Creates a new instance of Protocol
     */
    private String message="";
    public MessageClient() 
    {
        
    }
    
    public String RegisterPacket(String nom, int x, int y, int couleur)
    {
        message = "Hello" + nom + "[" + x + "," + y + "]" + couleur;
        return message;
    }
    
    public String RemoveClientPacket(int id)
    {
        message="Remove"+id;
        return message;
    }
    public String ExitMessagePacket(int id)
    {
        message="Exit"+id;
        return message;
    }

    public String UpdatePacket (int x,int y,int id, boolean shield)
    {
        message="Update"+x+","+y+"|"+id + "#"+shield ;
        return message;
    }
}
