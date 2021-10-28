package Model;

import java.awt.*;
import java.util.Random;

public class Requete {
    private Etape etapeCollecte;
    private Etape etapeDepot;
    private Color couleur;

    public Requete(Etape etapeCollecte, Etape etapeDepot) {
        this.etapeCollecte = etapeCollecte;
        this.etapeDepot = etapeDepot;

        Random rand = new Random();
        int maximumCouleur = 255;
        int r = rand.nextInt(maximumCouleur);
        int gr = rand.nextInt(maximumCouleur);
        int b = rand.nextInt(maximumCouleur);

        couleur = new Color(r, gr, b);
    }

    public Etape getEtapeCollecte() {
        return etapeCollecte;
    }

    public Etape getEtapeDepot() {
        return etapeDepot;
    }

    public Color getCouleur() { return couleur; }

    @Override
    public String toString() {
        return "Requete{" +
                "etapeCollecte=" + etapeCollecte +
                ", etapeDepot=" + etapeDepot +
                '}';
    }
}
