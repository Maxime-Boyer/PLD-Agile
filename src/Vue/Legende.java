package Vue;

import javax.swing.*;
import java.awt.*;

public class Legende extends JFrame{

    private LegendeFormes legendeFormes;
    private JLabel legendeTriangle;
    private JLabel legendeRond;
    private JLabel legendeCarre;

    private int ecartLabels;
    private int hauteurLigne;

    public Legende(){

        //parametres de la fenetre
        this.setTitle("Légende");
        this.setBounds(20, 60, 260, 135);
        this.setVisible(true);
        this.setResizable(false);
        this.setLayout(null);

        ecartLabels = 50;
        hauteurLigne = 25;

        // dessin des formes
        legendeFormes = new LegendeFormes( ecartLabels, this.getHeight());
        this.add(legendeFormes);

        // labels de la legende
        legendeTriangle = new JLabel("Adresse de départ et d'arrivée");
        legendeRond = new JLabel("Adresse de collecte");
        legendeCarre = new JLabel("Adresse de retrait");

        legendeTriangle.setBounds(ecartLabels, 10, this.getWidth() - ecartLabels, hauteurLigne);
        legendeRond.setBounds(ecartLabels, legendeTriangle.getY() + legendeTriangle.getHeight() + 10, this.getWidth() - ecartLabels, hauteurLigne);
        legendeCarre.setBounds(ecartLabels, legendeRond.getY() + legendeRond.getHeight() + 10, this.getWidth() - ecartLabels, hauteurLigne);

        this.add(legendeTriangle);
        this.add(legendeRond);
        this.add(legendeCarre);
    }
}
