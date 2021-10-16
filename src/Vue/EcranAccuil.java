package Vue;
import javax.swing.*;
import java.awt.*;

public class EcranAccuil extends JPanel{

    public EcranAccuil(int largeurEcran, int hauteurEcran, Font policeSousTitre, Font policeTexte) {

        // propriétés du pannel principal
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
        JButton buttonImporterPlan = new JButton("Importer un plan");
        buttonImporterPlan.setFont(policeTexte);
        buttonImporterPlan.setMargin(new Insets(5,20,5,20));
        pannelBouton.add(buttonImporterPlan);
    }
}
