package Vue;

import javax.swing.*;
import java.awt.*;

public class Bouton extends JButton {

    public Bouton(String texteBouton, Font policeTexte){
        this.setText(texteBouton);
        this.setFont(policeTexte);
        this.setMargin(new Insets(5,20,5,20));
    }
}