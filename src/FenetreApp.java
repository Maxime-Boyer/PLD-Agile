import Vue.*;

import javax.swing.*;
import java.awt.*;

public class FenetreApp {
    public static void main(String[] args) {

        // creation de la fenetre de l'application
        JFrame fenetreApp = new JFrame();
        fenetreApp.setTitle("Raccourc'IF - Hexanome Détect'IF");
        fenetreApp.setVisible(true);
        fenetreApp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenetreApp.setLayout(null);

        // Récupération des dimensions de l'écran
        Dimension dimensionsEcran = Toolkit.getDefaultToolkit().getScreenSize();
        fenetreApp.setSize(dimensionsEcran.width, dimensionsEcran.height);

        // Création des différentes tailles de police
        Font policeTitre = new Font("SansSerif", Font.BOLD, 28);
        Font policeSousTitre = new Font("SansSerif", Font.BOLD, 20);
        Font policeTexte = new Font("SansSerif", 400, 14);

        /*****************************************************************************/
        /*  Ajout vue(s) à la fenetre (sera remplacé par l'action du controleur)     */
        /*****************************************************************************/

        // Vue ecran Accueil
        // EcranAccuil ecranAccueil = new EcranAccuil(dimensionsEcran.width, dimensionsEcran.height, policeSousTitre, policeTexte);

        // E2 et +
        Plan plan = new Plan(dimensionsEcran.width, dimensionsEcran.height, policeTexte);
        MenuLateral menuLateral = new MenuLateral(dimensionsEcran.width, dimensionsEcran.height, policeTexte);

        // ajout des composants à la fenetre
        fenetreApp.add(plan);
        fenetreApp.add(menuLateral);
    }
}  