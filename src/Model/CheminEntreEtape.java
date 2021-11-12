package Model;

import java.util.List;

/**
 * Permet de stocker le chemin orienté entre deux étapes, c'est-à-dire la liste des segments permettant de les rejoindre avec la distance associée.
 */
public class CheminEntreEtape implements Comparable<CheminEntreEtape> {

    public Etape etapeDepart;
    public Etape etapeArrivee;
    public List<Segment> listeSegment;
    public Integer distance;

    /**
     * Constructeur de la classe chemin entre étape qui correspond aux chemins entre deux étapes de la collecte
     *
     * @param etapeDepart  Etape de départ
     * @param etapeArrivee Etape d'arrivée
     * @param listeSegment liste des segments entre les deux étapes
     * @param distance     distance en mètre entre les deuc étapes
     */
    public CheminEntreEtape(Etape etapeDepart, Etape etapeArrivee, List<Segment> listeSegment, Integer distance) {
        this.etapeDepart = etapeDepart;
        this.etapeArrivee = etapeArrivee;
        this.listeSegment = listeSegment;
        this.distance = distance;
    }

    /**
     * Méthode qui retourne l'étape de départ
     *
     * @return l'Etape de départ
     */
    public Etape getEtapeDepart() {
        return etapeDepart;
    }

    /**
     * Méthode qui reourne l'étape d'arrivée
     *
     * @return l'Etape d'arrivée
     */
    public Etape getEtapeArrivee() {
        return etapeArrivee;
    }

    /**
     * Méthode qui retourne la liste des segments entre les étapes
     *
     * @return la liste des segments
     */
    public List<Segment> getListeSegment() {
        return listeSegment;
    }

    /**
     * Méthode qui retourne la distance du chemin
     *
     * @return la distance
     */
    public Integer getDistance() {
        return distance;
    }

    /**
     * Méthode qui compare la distance entre le CheminEntreEtape et celui mis en paramètre
     *
     * @param cheminEntreEtape CheminEntreEtape à vérifier
     * @return un boolean ( true si la distance est la même)
     */
    @Override
    public int compareTo(CheminEntreEtape cheminEntreEtape) {
        return this.getDistance() - cheminEntreEtape.getDistance();
    }

    /**
     * Méthode permettant l'affichage textuel de CheminEntreEtape
     *
     * @return L'objet CheminEntreEtape sous forme textuel
     */
    @Override
    public String toString() {
        return "CheminEntreEtape{" +
                "etapeDepart=" + etapeDepart +
                ", etapeArrivee=" + etapeArrivee +
                ", distance=" + distance +
                '}';
    }
}
