package Vue;

import javax.swing.*;
import java.awt.*;

public class LegendeFormes extends JPanel {

    private int largeur;
    private int hauteur;

    public LegendeFormes(int largeur, int hauteur){
        this.largeur = largeur;
        this.hauteur = hauteur;
        this.setBounds(0, 0, largeur, hauteur);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        var largeurSymbole = 14;
        int valeurDepart = (int) largeur/2 - largeurSymbole/2;

        int valeurXBasGauche = valeurDepart;
        int valeurYBasGauche = 20 + 7;

        int valeurXBasDroite = valeurDepart + 14;
        int valeurYBasDroite = 20 + 7;

        int valeurXHaute = valeurDepart +7;
        int valeurYHaute = 20 - 7;

        int []XPoints = {valeurXBasGauche,valeurXBasDroite,valeurXHaute};
        int []YPoints = {valeurYBasGauche,valeurYBasDroite,valeurYHaute};

        g.drawPolygon(XPoints,YPoints,3);

        g.drawRoundRect(valeurDepart, 50, largeurSymbole, largeurSymbole, largeurSymbole, largeurSymbole);
        g.drawRect(valeurDepart, 85, largeurSymbole, largeurSymbole);
    }
}
