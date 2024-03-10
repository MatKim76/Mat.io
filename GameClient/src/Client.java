import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import javax.imageio.ImageIO;

/**
 *
 * @author Mohamed Talaat Saad
 */
public class Client {
    
    /**
     * Creates a new instance of Client
     */
    
    private Socket clientSocket;
    private String hostName;
    private int serverPort;
    private DataInputStream reader;
    private DataOutputStream writer;
    private MessageClient protocol;

    private static Client client;
    private Client() throws IOException 
    {
        protocol = new MessageClient();
    }

    public void register(String ip, int port, Joueur joueur) throws IOException 
    {
        this.serverPort = port;
        this.hostName = ip;
        clientSocket = new Socket(ip, port);
        writer = new DataOutputStream(clientSocket.getOutputStream());
    
        writer.writeUTF(protocol.RegisterPacket(joueur.getNom(), joueur.getX(), joueur.getY(), joueur.getIndexCouleur()));
    
        if (joueur.getIndexCouleur() == -1) 
        {
            envoyerImage(clientSocket, joueur.getImage());
        }
    }
    
    public void envoyerImage(Socket socket, BufferedImage image)
    {
        try {
            OutputStream outputStream = socket.getOutputStream();
            
            // Convertir l'image en byte array
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(image, "png", byteArrayOutputStream);
            byte[] imageBytes = byteArrayOutputStream.toByteArray();
            
            // Envoyer la taille de l'image
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            dataOutputStream.writeInt(imageBytes.length);
            dataOutputStream.flush();
            
            // Envoyer l'image
            outputStream.write(imageBytes);
            outputStream.flush();
            
            socket.close();
            System.out.println("Image envoyée avec succès.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
  
    public void sendToServer(String message)
    {   
        if(message.equals("exit"))
            System.exit(0);
        else
        {
             try {
                 Socket s=new Socket(hostName,serverPort);
                 //System.out.println(message);
                 writer=new DataOutputStream(s.getOutputStream());
                writer.writeUTF(message);
            } catch (IOException ex) {}
        }

    }
    
    public Socket getSocket()
    {
        return clientSocket;
    }
    
    public String getIP()
    {
        return hostName;
    }

    public static Client getGameClient()
    {
        if(client==null)
            
            try {
                client=new Client();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        return client;
    }

    public void closeAll()
    {
        try {
            reader.close(); 
            writer.close();
            clientSocket.close();
        } catch (IOException ex) {
            
        }
    }
}
