package Vue;

import Model.Carte;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Legende extends JPanel{

    private Carte carte;

    private LegendeFormes legendeFormes;
    private JLabel legendeTriangle;
    private JLabel legendeRond;
    private JLabel legendeCarre;
    private JLabel legendeSegment1;
    private JLabel legendeSegment2;
    private JLabel legendeSegment3;
    private JLabel legendeSegment4;
    private JLabel legendeSegment5;

    private int ecartLabels;
    private int hauteurLigne;
    private int largeurLabels;
    private int tailleBordureVerticale;
    private int tailleBordure;

    private int largeurParent;
    private int hauteurParent;


    /**
     * Affichage d'une legende pour expliquer à l'utilisateur la signification
     * du rond, du carre et du triangle
     * @param largeurParent: largeur du parent en px
     * @param hauteurParent: haueteur du parent en px
     * @param ecouteurDragDrop: ecouteur assurant le drag & drop de la legende
     * @param ecouteurSurvol: ecouteur gerant les evenements de survol
     */
    public Legende(Carte carte, int largeurParent, int hauteurParent, EcouteurDragDrop ecouteurDragDrop, EcouteurSurvol ecouteurSurvol) {

        this.carte = carte;

        this.largeurParent = largeurParent;
        this.hauteurParent = hauteurParent;

        ecartLabels = 50;
        largeurLabels = 230;
        hauteurLigne = 20;
        tailleBordureVerticale = 10;
        tailleBordure = 2;

        //parametres de la fenetre
        this.setBounds(20, 20, ecartLabels + largeurLabels, 2 * tailleBordureVerticale + 8 * hauteurLigne + 2 *tailleBordure);
        this.setLayout(null);
        //Bordure
        this.setBorder(BorderFactory.createMatteBorder(tailleBordure, tailleBordure, tailleBordure, tailleBordure, Color.darkGray));

        this.addMouseMotionListener(ecouteurDragDrop);
        this.addMouseListener(ecouteurSurvol);

        // dessin des formes
        legendeFormes = new LegendeFormes(carte, ecartLabels - tailleBordure, this.getHeight() - 2*tailleBordure, tailleBordureVerticale, hauteurLigne,tailleBordure);
        this.add(legendeFormes);

        // labels de la legende
        legendeTriangle = new JLabel("Adresse de départ et d'arrivée");
        legendeRond = new JLabel("Adresse de collecte");
        legendeCarre = new JLabel("Adresse de dépôt");
        legendeSegment1 = new JLabel("Un passage sur le segment");
        legendeSegment2 = new JLabel("Deux passages sur le segment");
        legendeSegment3 = new JLabel("Trois passages sur le segment");
        legendeSegment4 = new JLabel("Quatre passages sur le segment");
        legendeSegment5 = new JLabel("Cinq ou plus passages sur le segment");


        legendeTriangle.setBounds(ecartLabels, tailleBordureVerticale, largeurLabels, hauteurLigne);
        legendeRond.setBounds(ecartLabels, hauteurLigne + tailleBordureVerticale, largeurLabels, hauteurLigne);
        legendeCarre.setBounds(ecartLabels, hauteurLigne * 2 + tailleBordureVerticale, largeurLabels, hauteurLigne);
        legendeSegment1.setBounds(ecartLabels, hauteurLigne * 3 + tailleBordureVerticale, largeurLabels, hauteurLigne);
        legendeSegment2.setBounds(ecartLabels, hauteurLigne * 4 + tailleBordureVerticale, largeurLabels, hauteurLigne);
        legendeSegment3.setBounds(ecartLabels, hauteurLigne * 5 + tailleBordureVerticale, largeurLabels, hauteurLigne);
        legendeSegment4.setBounds(ecartLabels, hauteurLigne * 6 + tailleBordureVerticale, largeurLabels, hauteurLigne);
        legendeSegment5.setBounds(ecartLabels, hauteurLigne * 7 + tailleBordureVerticale, largeurLabels, hauteurLigne);

        this.add(legendeTriangle);
        this.add(legendeRond);
        this.add(legendeCarre);
        this.add(legendeSegment1);
        this.add(legendeSegment2);
        this.add(legendeSegment3);
        this.add(legendeSegment4);
        this.add(legendeSegment5);

    }

    public void setPosition(int x, int y){

        x = x - this.getWidth()/2;
        y = y - 50;

        if(x < (- this.getWidth() /2))
            x = (- this.getWidth() /2);
        if(y < (- this.getHeight() /2))
            y = (- this.getHeight() /2);
        if(x > largeurParent - this.getWidth() /2)
            x = largeurParent - this.getWidth() /2;
        if(y > hauteurParent - this.getHeight() /2)
            y = hauteurParent - this.getHeight() /2;

        this.setBounds(x, y, this.getWidth(), this.getHeight());

    }
}
