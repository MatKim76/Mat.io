import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChoixCouleur extends JFrame implements ActionListener 
{
    public static String[] COULEURS = {"Rouge", "Vert", "Bleu", "Jaune", "Orange", "Rose", "Violet", "Gris", "Noir"};

    private JButton[] couleurButtons;
    private JLabel lbl;

    public ChoixCouleur(JLabel label) 
    {
        this.lbl = label;
        this.setSize(200, 200);

        this.setTitle("Choix de Couleur");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        JPanel panel = new JPanel(new GridLayout(0, 3, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        couleurButtons = new JButton[ChoixCouleur.COULEURS.length];
        
        for (int i = 0; i < ChoixCouleur.COULEURS.length; i++) 
        {
            JButton button = new JButton();
            button.setBackground(getColorFromString(ChoixCouleur.COULEURS[i]));
            button.setOpaque(true);
            button.setBorderPainted(false);
            button.setActionCommand(ChoixCouleur.COULEURS[i]);
            button.addActionListener(this);
            panel.add(button);
            this.couleurButtons[i] = button;
        }
        
        this.add(panel);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        String couleurChoisie = e.getActionCommand();
        Color couleur = getColorFromString(couleurChoisie);
        this.lbl.setBackground(couleur);

        this.dispose();
    }

    public static Color getColorFromString(String couleur) 
    {
        switch (couleur.toLowerCase()) 
        {
            case "rouge":
                return Color.RED;
            case "vert":
                return Color.GREEN;
            case "bleu":
                return Color.BLUE;
            case "jaune":
                return Color.YELLOW;
            case "orange":
                return Color.ORANGE;
            case "rose":
                return Color.PINK;
            case "violet":
                return new Color(128, 0, 128); // Violet
            case "gris":
                return Color.GRAY;
            case "noir":
                return Color.BLACK;
            case "blanc":
                return Color.WHITE;
            default:
                return Color.BLACK;
        }
    }

    public static int getIndex(Color couleur)
    {
        int index = -1;
        for (int i = 0; i < COULEURS.length; i++)
        {
            if (getColorFromString(COULEURS[i]).equals(couleur))
            {
                index = i;
                break;
            }
        }
        return index;
    }

    public static Color getRandomCouleur()
    {
        return getColorFromString(COULEURS[(int)(Math.random()*COULEURS.length)]);
    }
    
}
