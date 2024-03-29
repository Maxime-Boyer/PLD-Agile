package Vue;
import javax.swing.*;
import java.awt.*;

public class EcranAccueil extends JPanel{

    /**
     * Initialisation de la vue correspondant à l'état 0 (arrivee de l'utilisateur sur l'application)
     * @param largeurEcran: la largeur de la fenetre
     * @param hauteurEcran: hauteur de la fenetre
     * @param policeSousTitre: police à appliquer sur les sous titres
     * @param policeTexte: police à appliquer sur le texte
     * @param ecouteurBoutons: ecouteur à placer sur le bouton de la vue
     */
    public EcranAccueil(int largeurEcran, int hauteurEcran, Font policeSousTitre, Font policeTexte, EcouteurBoutons ecouteurBoutons) {

        // Propriétés du pannel principal
        int widthPannel = 900, heightPannel = 150;
        this.setBounds(largeurEcran/2 - widthPannel/2, hauteurEcran/2 - heightPannel/2, widthPannel,heightPannel);
        this.setLayout(null);

        JPanel pannelTextes = new JPanel();
        pannelTextes.setBounds(0, 0, widthPannel, heightPannel*2/3);
        this.add(pannelTextes);

        // Création des textes de l'ecran d'acceuil
        JLabel texteBienvenue = new JLabel("Bienvenue dans l'application Raccourc'IF!", SwingConstants.CENTER);
        texteBienvenue.setFont(policeSousTitre);
        pannelTextes.add(texteBienvenue);

        JLabel texteCommencer = new JLabel("Pour commencer, veuillez importer un plan au format xml svp.", SwingConstants.CENTER);
        texteCommencer.setBounds(0, 100, widthPannel, heightPannel);
        texteCommencer.setFont(policeSousTitre);
        pannelTextes.add(texteCommencer);

        JPanel pannelBouton = new JPanel();
        pannelBouton.setBounds(0, heightPannel*2/3, widthPannel, heightPannel*1/3);
        this.add(pannelBouton);

        // Création du bouton menant à l'explorateur de fichier
        Bouton boutonImporterCarte = new Bouton(Fenetre.IMPORT_CARTE, policeTexte, ecouteurBoutons);
        pannelBouton.add(boutonImporterCarte);
    }
}
