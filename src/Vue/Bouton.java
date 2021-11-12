package Vue;

import javax.swing.*;
import java.awt.*;

public class Bouton extends JButton {

    /**
     * Design d'une template de bouton pour l'application
     * @param texteBouton:     le texte affiché dans le bouton
     * @param policeTexte:     le type de police à utiliser
     * @param ecouteurBoutons: l'ecouteur à appliquer pour gérer le clic sur le bouton
     */
    public Bouton(String texteBouton, Font policeTexte, EcouteurBoutons ecouteurBoutons) {
        this.setText(texteBouton);
        this.setFont(policeTexte);
        this.setMargin(new Insets(5, 20, 5, 20));
        this.addActionListener(ecouteurBoutons);
    }
}
