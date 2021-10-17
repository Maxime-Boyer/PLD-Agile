package Vue;

import javax.swing.*;
import java.awt.*;

public class Plan extends JPanel {

    public Plan(int largeurEcran, int hauteurEcran, Font policeTexte){

        // propriétés du pannel principal
        this.setBounds(0, 0, largeurEcran * 3/4, hauteurEcran);
        this.setBackground(Color.CYAN);

        // TO REMOVE
        JLabel toRemove = new JLabel("Zone plan");
        toRemove.setFont(policeTexte);
        this.add(toRemove);
    }



}
