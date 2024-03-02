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
    /*public String NewClientPacket(int x,int y,int dir,int id)
    {
        message="NewClient"+x+","+y+"-"+dir+"|"+id;
        return message;   
    }*/

    public String NewClientPacket(int x,int y,int id)
    {
        message="NewClient"+x+","+y+"|"+id;
        return message;   
    }


}
