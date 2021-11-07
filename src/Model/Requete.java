package Model;

import java.awt.*;
import java.util.Objects;
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

    public Color getCouleur() { return couleurRequete; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Requete requete = (Requete) o;
        return etapeCollecte.getIdAdresse().equals(requete.etapeCollecte) && etapeDepot.getIdAdresse().equals(requete.etapeDepot);
    }

    @Override
    public int hashCode() {
        return Objects.hash(etapeCollecte, etapeDepot, couleurRequete);
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
