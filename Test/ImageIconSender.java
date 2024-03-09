
import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.*;
import java.net.Socket;

public class ImageIconSender {

    public static void main(String[] args) throws IOException {
        // Création de l'ImageIcon à envoyer
        ImageIcon imageIcon = new ImageIcon("lesbocchis.png");

        // Convertir ImageIcon en tableau d'octets
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(imageIcon);
        byte[] imageData = byteArrayOutputStream.toByteArray();

        // Envoi du tableau d'octets sur la socket
        Socket socket = new Socket("localhost", 12345); // Adresse IP et port du destinataire
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write(imageData);

        // Fermeture des flux et de la socket
        outputStream.close();
        socket.close();
    }
}
