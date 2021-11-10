package Model;

import java.awt.*;
import java.util.Objects;
import java.util.Random;

public class Requete {
    private Etape etapeCollecte;
    private Etape etapeDepot;
    private Color couleurRequete;

    /**
     * Constructeur d'une Reqeuete, information composée d’une adresse de collecte, une durée de collecte, une adresse de dépôt et une durée de dépôt
     * @param etapeCollecte: étape de collecte de la requete
     * @param etapeDepot: étape de dépot de la requete
     */
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

    /**
     * methode qui retourne l'étape de collecte de la requete
     * @return
     */
    public Etape getEtapeCollecte() {
        return etapeCollecte;
    }
    /**
     * methode qui retourne l'étape de dépot de la requete
     * @return
     */
    public Etape getEtapeDepot() {
        return etapeDepot;
    }
    /**
     * methode qui retourne la couleur de la requete
     * @return
     */
    public Color getCouleur() { return couleurRequete; }

    /**
     * methode qui retourne true si les deux requetes sont les mêmes
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Requete requete = (Requete) o;
        return etapeCollecte.getIdAdresse().equals(requete.etapeCollecte) && etapeDepot.getIdAdresse().equals(requete.etapeDepot);
    }

    /**
     * methode  qui digère les données stockées dans une instance de la classe dans une valeur de hachage
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(etapeCollecte, etapeDepot, couleurRequete);
    }

    /**
     * methode qui affiche la requête
     * @return
     */
    @Override
    public String toString() {
        return "Requete{" +
                "etapeCollecte=" + etapeCollecte +
                ", etapeDepot=" + etapeDepot +
                '}';
    }

    /**
     * methode qui retourne la couleur de la requête qui est utlile pour la création d'une couleur qui sera la même pour la collecte et le dépôt dans l'IHM
     * @return
     */
    public Color getCouleurRequete() {
        return couleurRequete;
    }
}
