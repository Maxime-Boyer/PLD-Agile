import Vue.*;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;

public class FenetreApp {
    public static void main(String[] args) throws ParserConfigurationException, SAXException {

        // creation de la fenetre de l'application
        JFrame fenetreApp = new JFrame();
        fenetreApp.setTitle("Raccourc'IF - Hexanome Détect'IF");

        fenetreApp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenetreApp.setLayout(null);

        // Récupération des dimensions de l'écran
        Dimension dimensionsEcran = Toolkit.getDefaultToolkit().getScreenSize();
        fenetreApp.setSize(dimensionsEcran.width, dimensionsEcran.height);

        // Création des différentes tailles de police
        Font policeTitre = new Font("SansSerif", Font.BOLD, 28);
        Font policeSousTitre = new Font("SansSerif", Font.BOLD, 20);
        Font policeTexteImportant = new Font("SansSerif", Font.BOLD, 16);
        Font policeTexte = new Font("SansSerif", 400, 14);

        /*****************************************************************************/
        /*  Ajout vue(s) à la fenetre (sera remplacé par l'action du controleur)     */
        /*****************************************************************************/

        // E1: Vue ecran Accueil
        // EcranAccuil ecranAccueil = new EcranAccuil(dimensionsEcran.width, dimensionsEcran.height, policeSousTitre, policeTexte);

        // E2 et +
        Plan plan = new Plan(dimensionsEcran.width*3/4, dimensionsEcran.height, policeTexte);
        MenuLateral menuLateral = new MenuLateral(dimensionsEcran.width, dimensionsEcran.height, policeTexte, policeTexteImportant);

        // ajout des composants à la fenetre
        fenetreApp.add(plan);
        fenetreApp.add(menuLateral);

        fenetreApp.setVisible(true);
    }
}  