import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class ImageChooser extends JFrame {
    private JLabel imageLabel;
    private ImageIcon imageIcon;
    private BufferedImage image;
    private int squareSize = 200; // Taille du carré dans lequel l'image doit être ajustée

    public ImageChooser() {
        setTitle("Choisir une image");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Création d'un JButton pour choisir une image
        JButton chooseButton = new JButton("Choisir une image");
        chooseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Afficher la boîte de dialogue de sélection de fichier
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    // Charger l'image sélectionnée
                    File selectedFile = fileChooser.getSelectedFile();
                    try {
                        image = ImageIO.read(selectedFile);
                        // Redimensionner l'image pour s'ajuster dans le carré
                        Image scaledImage = image.getScaledInstance(squareSize, squareSize, Image.SCALE_SMOOTH);
                        imageIcon = new ImageIcon(scaledImage);
                        imageLabel.setIcon(imageIcon);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        // Création d'un JLabel pour afficher l'image
        imageLabel = new JLabel();
        imageLabel.setPreferredSize(new Dimension(squareSize, squareSize));
        imageLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Ajouter le JLabel à la JFrame
        getContentPane().add(imageLabel, BorderLayout.CENTER);
        // Ajouter le JButton à la JFrame
        getContentPane().add(chooseButton, BorderLayout.SOUTH);

        // Ajouter un MouseMotionListener pour déplacer l'image
        imageLabel.addMouseMotionListener(new MouseMotionAdapter() {
            int initialX, initialY;

            public void mousePressed(MouseEvent e) {
                initialX = e.getX();
                initialY = e.getY();
            }

            public void mouseDragged(MouseEvent e) {
                int deltaX = e.getX() - initialX;
                int deltaY = e.getY() - initialY;
                int newX = imageLabel.getX() + deltaX;
                int newY = imageLabel.getY() + deltaY;
                // Limiter le déplacement pour que l'image reste dans les limites du conteneur
                newX = Math.min(Math.max(0, newX), getContentPane().getWidth() - imageLabel.getWidth());
                newY = Math.min(Math.max(0, newY), getContentPane().getHeight() - imageLabel.getHeight());
                imageLabel.setLocation(newX, newY);
            }
        });

        pack();
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ImageChooser example = new ImageChooser();
            example.setVisible(true);
        });
    }
}
