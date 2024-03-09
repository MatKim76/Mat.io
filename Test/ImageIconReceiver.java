
import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ImageIconReceiver {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        // Création d'une socket serveur
        ServerSocket serverSocket = new ServerSocket(12345); // Port à écouter

        // Attente d'une connexion entrante
        Socket socket = serverSocket.accept();

        // Réception du tableau d'octets
        InputStream inputStream = socket.getInputStream();
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
        //byte[] imageData = (byte[]) objectInputStream.readObject();
        ImageIcon image = (ImageIcon)  objectInputStream.readObject();

        // Convertir le tableau d'octets en ImageIcon
        //ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(imageData);
        //ImageIcon receivedImageIcon = (ImageIcon) new ObjectInputStream(byteArrayInputStream).readObject();

        // Affichage de l'ImageIcon reçu
        JLabel label = new JLabel(image);
        JFrame frame = new JFrame();
        frame.add(label);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Fermeture des flux et de la socket
        inputStream.close();
        socket.close();
        serverSocket.close();
    }
}
