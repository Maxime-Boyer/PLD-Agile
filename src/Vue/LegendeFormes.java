package Vue;

import Model.Carte;

import javax.swing.*;
import java.awt.*;

public class LegendeFormes extends JPanel {

    private int largeur;
    private int hauteur;
    private int tailleBordureVerticale;
    private int hauteurLigne;
    private int tailleBordure;

    private Carte carte;

    /**
     * Zone d'affichage des formes géométriques de la legende
     * @param largeur: largeur de la zone
     * @param hauteur: hauteur de la zone
     */
    public LegendeFormes(Carte carte, int largeur, int hauteur, int tailleBordureVerticale, int hauteurLigne, int tailleBordure){
        this.largeur = largeur;
        this.hauteur = hauteur;
        this.setBounds(tailleBordure, tailleBordure, largeur, hauteur);
        this.tailleBordureVerticale = tailleBordureVerticale;
        this.hauteurLigne = hauteurLigne;

        this.carte = carte;
    }

    /**
     * Tracé du carré, du rond et du triangle dans la zone dediee
     * @param g
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int largeurSymbole = 14;

        int largeurDepart = (int) largeur/2 - largeurSymbole/2;

        // --- TRIANGLE ---
        int valeurXBasGauche = largeurDepart;
        int valeurYBasGauche = tailleBordureVerticale + largeurSymbole;

        int valeurXBasDroite = largeurDepart + largeurSymbole;
        int valeurYBasDroite = tailleBordureVerticale + largeurSymbole;

        int valeurXHaute = largeurDepart + largeurSymbole/2;
        int valeurYHaute = tailleBordureVerticale;

        int []XPoints = {valeurXBasGauche,valeurXBasDroite,valeurXHaute};
        int []YPoints = {valeurYBasGauche,valeurYBasDroite,valeurYHaute};

        g.drawPolygon(XPoints,YPoints,3);
        //--- FIN TRIANGLE ---

        //Autres
        g.drawOval(largeurDepart, tailleBordureVerticale + hauteurLigne, largeurSymbole, largeurSymbole);
        g.drawRoundRect(largeurDepart, tailleBordureVerticale + hauteurLigne * 2, largeurSymbole, largeurSymbole,5,5);

        for(int i=0 ; i<5 ; i++){
            g.setColor(carte.getCouleurExterieurChemin()[i]);
            g.fillRect(largeurDepart - largeurSymbole/2, tailleBordureVerticale + hauteurLigne * (i+3) + (int)(0.1 * largeurSymbole), largeurSymbole * 2 , (int)(largeurSymbole*0.8));
            g.setColor(carte.getCouleurInterieurChemin()[i]);
            g.fillRect(largeurDepart + (int)(0.1*largeurSymbole) - largeurSymbole/2, tailleBordureVerticale + hauteurLigne * (i+3) + (int)(0.2 * largeurSymbole), (largeurSymbole -(int)(0.1*largeurSymbole))*2 , (int)(largeurSymbole*0.7));
        }

    }
}
