package Vue;

import javax.swing.*;

public class Legende extends JPanel{

    private LegendeFormes legendeFormes;
    private JLabel legendeTriangle;
    private JLabel legendeRond;
    private JLabel legendeCarre;

    private int ecartLabels;
    private int hauteurLigne;

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
    public Legende(int largeurParent, int hauteurParent, EcouteurDragDrop ecouteurDragDrop, EcouteurSurvol ecouteurSurvol){

        this.largeurParent = largeurParent;
        this.hauteurParent = hauteurParent;

        //parametres de la fenetre
        this.setBounds(20, 20, 260, 120);
        this.setLayout(null);

        this.addMouseMotionListener(ecouteurDragDrop);
        this.addMouseListener(ecouteurSurvol);

        ecartLabels = 50;
        hauteurLigne = 25;

        // dessin des formes
        legendeFormes = new LegendeFormes( ecartLabels, this.getHeight());
        this.add(legendeFormes);

        // labels de la legende
        legendeTriangle = new JLabel("Adresse de départ et d'arrivée");
        legendeRond = new JLabel("Adresse de collecte");
        legendeCarre = new JLabel("Adresse de dépôt");

        legendeTriangle.setBounds(ecartLabels, 10, this.getWidth() - ecartLabels, hauteurLigne);
        legendeRond.setBounds(ecartLabels, legendeTriangle.getY() + legendeTriangle.getHeight() + 10, this.getWidth() - ecartLabels, hauteurLigne);
        legendeCarre.setBounds(ecartLabels, legendeRond.getY() + legendeRond.getHeight() + 10, this.getWidth() - ecartLabels, hauteurLigne);

        this.add(legendeTriangle);
        this.add(legendeRond);
        this.add(legendeCarre);
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
