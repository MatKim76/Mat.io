import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
/*
 * Server.java
 *
 * Created on 21 ����, 2008, 09:41 �
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author Mohamed Talaat Saad
 */
public class Server extends Thread {
    
    /**
     * Creates a new instance of Server
     */
    
    private ArrayList<ClientInfo> clients;
    private ServerSocket serverSocket;
    private int serverPort=11111;
    
   
    private DataInputStream reader;
    private DataOutputStream writer;
   
    private MessageServer protocol;
    private boolean running=true;
    public Server() throws SocketException 
    {
        clients=new ArrayList<ClientInfo>();
        protocol=new MessageServer();
        try {
            serverSocket=new ServerSocket(serverPort);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
        
    public void run()
    {
        Socket clientSocket=null;
        while(running)
        {     
            try {
                clientSocket=serverSocket.accept();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            String sentence="";
            try {
                reader=new DataInputStream(clientSocket.getInputStream());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            try {
                sentence=reader.readUTF();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            
            //System.out.println(sentence);
            if(sentence.startsWith("Hello"))
            {
                int pos1 = sentence.indexOf('[');
                int pos2 = sentence.indexOf(',');
                int pos3 = sentence.indexOf(']');

                String nom = sentence.substring(5, pos1);
                int x = Integer.parseInt(sentence.substring(pos1+1, pos2));
                int y = Integer.parseInt(sentence.substring(pos2+1, pos3));
                int couleur = Integer.parseInt(sentence.substring(pos3+1, sentence.length()));
              
                try {
                    writer=new DataOutputStream(clientSocket.getOutputStream());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                sendToClient(protocol.IDPacket(clients.size()+1));

                try {
                    BroadCastMessage(protocol.NewClientPacket(nom, x, y, couleur, clients.size()+1));
                    sendAllClients(writer);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                clients.add(new ClientInfo(writer, nom, x, y, couleur, false));
            }
            
            else if(sentence.startsWith("Update"))
            {
                int pos1=sentence.indexOf(',');
                int pos2=sentence.indexOf('|');
                int pos3=sentence.indexOf('#');

                int x  = Integer.parseInt(sentence.substring(6, pos1));
                int y  = Integer.parseInt(sentence.substring(pos1+1, pos2));
                int id = Integer.parseInt(sentence.substring(pos2+1, pos3));
                boolean bool = Boolean.parseBoolean(sentence.substring(pos3+1, sentence.length())); 

                if(clients.size() > id-1)
                {
                    if( clients.get(id-1)!=null)//TODO surement des probleme avec le -1
                    {
                        clients.get(id-1).setPosX(x);
                        clients.get(id-1).setPosY(y);
                        clients.get(id-1).setBouclier(bool);
                        try {
                            BroadCastMessage(sentence);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
                
                    
            }
            else if(sentence.startsWith("Remove"))
            {
                int id=Integer.parseInt(sentence.substring(6));

                try {
                    BroadCastMessage(sentence);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                clients.set(id-1,null);
            }     
            else if(sentence.startsWith("Exit"))
            {
                int id=Integer.parseInt(sentence.substring(4));

                try {
                    BroadCastMessage(sentence);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                if(clients.get(id-1)!=null)
                    clients.set(id-1,null);
            }
        }
        
        try {
            reader.close();
        writer.close();
        serverSocket.close();
            clientSocket.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public void stopServer() throws IOException
    {
        running=false;
    }
    
    public void BroadCastMessage(String mess) throws IOException
    {
        for(int i=0;i<clients.size();i++)
        {
            if(clients.get(i)!=null)
                clients.get(i).getWriterStream().writeUTF(mess);
        }
    }

    public void sendToClient(String message)
    {
         if(message.equals("exit"))
            System.exit(0);
        else
        {    
            try {
                writer.writeUTF(message);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public void sendAllClients(DataOutputStream writer)
    {
        int x, y, index;
        String nom;
        for(int i=0;i<clients.size();i++)
        {
            if(clients.get(i)!=null) 
            {
                nom = clients.get(i).getNom();
                x = clients.get(i).getX();
                y = clients.get(i).getY();
                index = clients.get(i).getIndexCouleur();

                try {
                    writer.writeUTF(protocol.NewClientPacket(nom, x, y, index, i+1));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
    
    public class ClientInfo
    {
        DataOutputStream writer;
        int posX,posY;
        boolean bouclier;
        String nom;
        int indexCouleur;
        
        public ClientInfo(DataOutputStream writer, String nom, int posX, int posY, int indexCouleur, boolean bouclier)
        {
           this.writer = writer;
           this.posX = posX;
           this.posY = posY;
           this.bouclier = bouclier;
           this.nom = nom;
           this.indexCouleur = indexCouleur;
        }
        
        public void setPosX(int x) {posX = x;}
        public void setPosY(int y) {posY = y;}
        public void setBouclier(boolean bool) {this.bouclier = bool;}
        public void setNom(String nom) {this.nom = nom;}
        public void setIndexCouleur(int index) {this.indexCouleur = index;}

        public DataOutputStream getWriterStream() {return writer;}
        public int getX() {return posX;}
        public int getY() {return posY;}
        public boolean getBouclier() {return this.bouclier;}
        public String getNom() {return this.nom;}
        public int getIndexCouleur() {return this.indexCouleur;}
    }
    
}
