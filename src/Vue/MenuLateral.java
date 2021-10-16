package Vue;

import javax.swing.*;
import java.awt.*;

public class MenuLateral extends JPanel {

    public MenuLateral(int largeurEcran, int hauteurEcran, Font policeTexte){

        // propriétés du pannel principal
        this.setBounds(largeurEcran * 3/4, 0, largeurEcran * 1/4, hauteurEcran);
        this.setBackground(Color.RED);

        // TO REMOVE
        JLabel toRemove = new JLabel("Zone menu latéral");
        toRemove.setFont(policeTexte);
        this.add(toRemove);
    }
}
