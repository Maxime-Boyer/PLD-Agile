package Model;

import java.awt.*;
import java.util.Objects;
import java.util.Random;

/**
 * Représente deux étapes : une collecte et un dépôt
 */
public class Requete {
    private Etape etapeCollecte;
    private Etape etapeDepot;
    private Color couleurRequete;

    /**
     * Constructeur d'une Reqeuete, information composée d’une adresse de collecte, une durée de collecte, une adresse de dépôt et une durée de dépôt
     *
     * @param etapeCollecte étape de collecte de la requete
     * @param etapeDepot    étape de dépot de la requete
     */
    public Requete(Etape etapeCollecte, Etape etapeDepot) {
        this.etapeCollecte = etapeCollecte;
        this.etapeDepot = etapeDepot;
        Random rand = new Random();
        int maximumCouleur = 255;
        int r = rand.nextInt(maximumCouleur);
        int gr = rand.nextInt(maximumCouleur);
        int b = rand.nextInt(maximumCouleur);
        this.couleurRequete = new Color(r, gr, b);
    }

    /**
     * Constructeur d'une Reqeuete, information composée d’une adresse de collecte, une durée de collecte
     *
     * @param etapeCollecte étape de collecte de la requete
     */
    public Requete(Etape etapeCollecte) {
        this.etapeCollecte = etapeCollecte;
        this.etapeDepot = null;
        this.couleurRequete = Color.RED;
    }

    /**
     * Méthode qui retourne l'étape de collecte de la requete
     *
     * @return l'Etape de collecte
     */
    public Etape getEtapeCollecte() {
        return etapeCollecte;
    }

    /**
     * Méthode qui retourne l'étape de dépot de la requete
     *
     * @return l'Etape de depôt
     */
    public Etape getEtapeDepot() {
        return etapeDepot;
    }

    /**
     * Méthode qui retourne la couleur de la requête qui est utlile pour la création d'une couleur qui sera la même pour la collecte et le dépôt dans l'IHM
     *
     * @return la couleur de la Requete
     */
    public Color getCouleurRequete() {
        return couleurRequete;
    }

    /**
     * Méthode qui affiche la requête
     *
     * @return la Requete this
     */
    @Override
    public String toString() {
        return "Requete{" +
                "etapeCollecte=" + etapeCollecte +
                ", etapeDepot=" + etapeDepot +
                '}';
    }
}
