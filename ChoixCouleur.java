import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChoixCouleur extends JFrame implements ActionListener {

    private JButton[] couleurButtons;

    public ChoixCouleur() {
        setTitle("Choix de Couleur");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        JPanel panel = new JPanel(new GridLayout(0, 3, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        String[] couleurs = {"Rouge", "Vert", "Bleu", "Jaune", "Orange", "Rose", "Violet", "Gris", "Noir", "Blanc"};
        couleurButtons = new JButton[couleurs.length];
        
        for (int i = 0; i < couleurs.length; i++) {
            JButton button = new JButton();
            button.setBackground(getColorFromString(couleurs[i]));
            button.setOpaque(true);
            button.setBorderPainted(false);
            button.setActionCommand(couleurs[i]);
            button.addActionListener(this);
            panel.add(button);
            couleurButtons[i] = button;
        }
        
        add(panel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String couleurChoisie = e.getActionCommand();
        Color couleur = getColorFromString(couleurChoisie);
        // Faites ce que vous voulez avec la couleur choisie, par exemple, l'utiliser dans votre application
        System.out.println("Couleur choisie : " + couleurChoisie);
    }

    private Color getColorFromString(String couleur) {
        switch (couleur.toLowerCase()) {
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ChoixCouleur::new);
    }
}
