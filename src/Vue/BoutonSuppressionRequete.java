package Vue;

import Model.Requete;

import javax.swing.*;
import java.awt.*;

public class BoutonSuppressionRequete extends JButton {

    private Requete requete;

    /**
     * Design d'un template de bouton pour l'application
     * @param texteBouton: le texte affiché dans le bouton
     * @param policeTexte: le type de police à utiliser
     * @param ecouteurBoutons: l'ecouteur a appliquer pour gérer le clic sur le bouton
     */
    public BoutonSuppressionRequete(String texteBouton, Font policeTexte, EcouteurBoutons ecouteurBoutons, Requete requete){
        this.requete = requete;
        this.setText(texteBouton);
        this.setFont(policeTexte);
        this.setMargin(new Insets(5,20,5,20));
        this.addActionListener(ecouteurBoutons);
    }

    public Requete getRequete() {
        return requete;
    }
}
