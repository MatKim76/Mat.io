import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.*;
import javax.imageio.*;
import java.io.*;

public class ImageCropper extends JFrame {
    private JLabel imageLabel;
    private ImageIcon imageIcon;
    private BufferedImage originalImage;
    private BufferedImage croppedImage;

    private int squareSize = 200; // Taille du carré de sélection

    public ImageCropper() {
        setTitle("Sélectionner un carré de l'image");
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
                        originalImage = ImageIO.read(selectedFile);
                        // Redimensionner l'image pour s'ajuster dans le JLabel
                        Image scaledImage = originalImage.getScaledInstance(squareSize, squareSize, Image.SCALE_SMOOTH);
                        imageIcon = new ImageIcon(scaledImage);
                        imageLabel.setIcon(imageIcon);
                        imageLabel.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
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

        // Ajouter un MouseListener pour sélectionner la région carrée de l'image
        imageLabel.addMouseListener(new MouseAdapter() {
            int initialX, initialY;

            public void mousePressed(MouseEvent e) {
                initialX = e.getX();
                initialY = e.getY();
            }

            public void mouseReleased(MouseEvent e) {
                int finalX = e.getX();
                int finalY = e.getY();
                int width = Math.abs(finalX - initialX);
                int height = Math.abs(finalY - initialY);
                int x = Math.min(initialX, finalX);
                int y = Math.min(initialY, finalY);

                // Assurez-vous que la sélection reste dans les limites de l'image
                x = Math.max(x, 0);
                y = Math.max(y, 0);
                width = Math.min(width, originalImage.getWidth() - x);
                height = Math.min(height, originalImage.getHeight() - y);

                // Créer une image croppée avec la région sélectionnée
                croppedImage = originalImage.getSubimage(x, y, width, height);
            }
        });

        // Création d'un JButton pour télécharger l'image croppée
        JButton downloadButton = new JButton("Télécharger le carré");
        downloadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (croppedImage != null) {
                    // Afficher la boîte de dialogue de sélection de fichier pour enregistrer l'image
                    JFileChooser fileChooser = new JFileChooser();
                    int result = fileChooser.showSaveDialog(null);
                    if (result == JFileChooser.APPROVE_OPTION) {
                        // Enregistrer l'image croppée dans le fichier sélectionné
                        File selectedFile = fileChooser.getSelectedFile();
                        try {
                            ImageIO.write(croppedImage, "png", selectedFile);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Aucune région carrée sélectionnée.");
                }
            }
        });

        // Ajouter le JButton pour télécharger l'image croppée à la JFrame
        getContentPane().add(downloadButton, BorderLayout.NORTH);

        pack();
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ImageCropper example = new ImageCropper();
            example.setVisible(true);
        });
    }
}
