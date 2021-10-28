package Model;

import java.awt.*;
import java.util.Random;

public class Requete {
    private Etape etapeCollecte;
    private Etape etapeDepot;
    private Color couleurRequete;

    public Requete(Etape etapeCollecte, Etape etapeDepot) {
        this.etapeCollecte = etapeCollecte;
        this.etapeDepot = etapeDepot;
        Random rand = new Random();
        int maximumCouleur = 255;
        int r = rand.nextInt(maximumCouleur);
        int gr = rand.nextInt(maximumCouleur);
        int b = rand.nextInt(maximumCouleur);
        this.couleurRequete = new Color(r,gr,b);
    }

    public Etape getEtapeCollecte() {
        return etapeCollecte;
    }

    public Etape getEtapeDepot() {
        return etapeDepot;
    }

    @Override
    public String toString() {
        return "Requete{" +
                "etapeCollecte=" + etapeCollecte +
                ", etapeDepot=" + etapeDepot +
                '}';
    }

    public Color getCouleurRequete() {
        return couleurRequete;
    }
}
