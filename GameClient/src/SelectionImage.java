import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

public class SelectionImage extends JPanel implements MouseListener, MouseMotionListener, MouseWheelListener 
{
    private BufferedImage image;
    private int imageX = 0, imageY = 0;
    private double scale = 1.0;
    private int squareSize = 200;
    private int mouseX, mouseY;
    private boolean dragging = false;
    private JLabel label;
    private JFrame frame;

    public SelectionImage(BufferedImage image, JLabel label) 
    {
        this.frame = new JFrame("Sélection de la zone centrale de l'image");
        this.frame.getContentPane().add(this);
        this.frame.setSize(600, 600);
        this.frame.setLocationRelativeTo(null);
        this.frame.setVisible(true);
        
        this.image = image;
        this.label = label;

        this.setPreferredSize(new Dimension(600, 600)); // Taille de la fenêtre
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this. addMouseWheelListener(this);

        JButton saveButton = new JButton("Enregistrer");
        saveButton.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                saveSelectedImage();
            }
        });
        this.add(saveButton);
    }

    //TODO voir pour que sa marche avec le zoom + probleme quand on dézoom en dehors
    
    private void saveSelectedImage() 
    {
        int squareX = (getWidth() - squareSize) / 2;
        int squareY = (getHeight() - squareSize) / 2;
        int scaledSquareX = (int) ((squareX - imageX) / scale);
        int scaledSquareY = (int) ((squareY - imageY) / scale);
        int scaledSize = (int) (squareSize / scale);
    
        int imageWidth = (int) (image.getWidth() * scale);
        int imageHeight = (int) (image.getHeight() * scale);
    
        int maxScaledX = Math.min(imageWidth - scaledSize, Math.max(0, scaledSquareX));
        int maxScaledY = Math.min(imageHeight - scaledSize, Math.max(0, scaledSquareY));
    
        int clampedScaledX = Math.max(0, Math.min(maxScaledX, (int) (squareX - imageX) * image.getWidth() / imageWidth));
        int clampedScaledY = Math.max(0, Math.min(maxScaledY, (int) (squareY - imageY) * image.getHeight() / imageHeight));
    
        BufferedImage selectedImage = image.getSubimage(clampedScaledX, clampedScaledY, scaledSize, scaledSize);
        Image scaledImage = selectedImage.getScaledInstance(squareSize, squareSize, Image.SCALE_SMOOTH);

        ImageIcon icon = new ImageIcon(scaledImage);
        this.label.setIcon(icon);

        this.frame.dispose();
        
        /*try {
            ImageIO.write(selectedImage, "png", new File("selected_image.png"));
            JOptionPane.showMessageDialog(this, "Image sélectionnée et sauvegardée sous selected_image.png");
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur lors de la sauvegarde de l'image");
        }*/
    }

    protected void paintComponent(Graphics g) 
    {
        super.paintComponent(g);
        int imageWidth = (int)(image.getWidth() * scale);
        int imageHeight = (int)(image.getHeight() * scale);

        // Dessiner l'image
        g.drawImage(image,  imageX, imageY, imageWidth, imageHeight, this);

        // Dessiner le carré de sélection
        int squareX = (getWidth() - squareSize) / 2;
        int squareY = (getHeight() - squareSize) / 2;

        // Dessiner l'assombrissement autour du carré de sélection
        g.setColor(new Color(0, 0, 0, 100));
        g.fillRect(0, 0, squareX, getHeight()); 
        g.fillRect(squareX, 0, squareSize, squareY); 
        g.fillRect(squareX + squareSize, 0, getWidth() - (squareX + squareSize), getHeight());
        g.fillRect(squareX, squareY + squareSize, squareSize, getHeight() - (squareY + squareSize));

        g.setColor(Color.RED);
        g.drawRect(squareX, squareY, squareSize, squareSize);
    }

    public void mouseClicked(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}

    public void mousePressed(MouseEvent e) 
    {
        mouseX = e.getX();
        mouseY = e.getY();
        dragging = true;
    }

    public void mouseReleased(MouseEvent e) 
    {
        dragging = false;
    }

    public void mouseDragged(MouseEvent e) 
    {
        if (dragging) {
            int deltaX = e.getX() - mouseX;
            int deltaY = e.getY() - mouseY;
            mouseX = e.getX();
            mouseY = e.getY();
            imageX += deltaX;
            imageY += deltaY;
            repaint();

            int squareX = (getWidth() - squareSize) / 2;
            int squareY = (getHeight() - squareSize) / 2;

            if( imageX > squareX || imageX + image.getWidth () * scale < squareX + squareSize ) imageX -= deltaX;
            if( imageY > squareY || imageY + image.getHeight() * scale < squareY + squareSize ) imageY -= deltaY;

            //System.out.println(imageX + "  " + imageY + "   " + scale);
        }
    } 

    public void mouseMoved(MouseEvent e) {}

    public void mouseWheelMoved(MouseWheelEvent e) 
    {
        int notches = e.getWheelRotation();
        if (notches < 0) {
            scale *= 1.1;
        } else {
            scale *= 0.9;
        }
        repaint();
    }
}
