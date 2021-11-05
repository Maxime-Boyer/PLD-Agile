package Vue;

import javax.swing.*;
import java.awt.*;

public class Bouton extends JButton {
    /**
     * constructeur de bouton
     * @param texteBouton
     * @param policeTexte
     * @param ecouteurBoutons
     */
    public Bouton(String texteBouton, Font policeTexte, EcouteurBoutons ecouteurBoutons){
        this.setText(texteBouton);
        this.setFont(policeTexte);
        this.setMargin(new Insets(5,20,5,20));
        this.addActionListener(ecouteurBoutons);
    }
}
