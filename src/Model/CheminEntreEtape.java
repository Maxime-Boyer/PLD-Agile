package Model;

import java.util.List;

public class CheminEntreEtape implements Comparable<CheminEntreEtape> {

    public Etape etapeDepart;
    public Etape etapeArrivee;
    public List<Segment> listeSegment;
    public Integer distance;

    /**
     * constructeur de la classe chemin entre étape qui correspond aux chemins entre deux étapes de la collecte
     * @param etapeDepart: Etape de départ
     * @param etapeArrivee: Etape d'arrivée
     * @param listeSegment: liste des segments entre les deux étapes
     * @param distance: distance en mètre entre les deuc étapes
     */
    public CheminEntreEtape(Etape etapeDepart, Etape etapeArrivee, List<Segment> listeSegment, Integer distance) {
        this.etapeDepart = etapeDepart;
        this.etapeArrivee = etapeArrivee;
        this.listeSegment = listeSegment;
        this.distance = distance;
    }

    /**
     * méthode qui retourne l'étape de départ
     * @return: l'Etape de départ
     */
    public Etape getEtapeDepart() {
        return etapeDepart;
    }

    /**
     * méthode qui reourne l'étape d'arrivée
     * @return: l'Etape d'arrivée
     */
    public Etape getEtapeArrivee() {
        return etapeArrivee;
    }

    /**
     * méthode qui retourne la liste des segments entre les étapes
     * @return: la liste des segments
     */
    public List<Segment> getListeSegment() {
        return listeSegment;
    }

    /**
     * méthode qui retourne la distance du chemin
     * @return: la distance
     */
    public Integer getDistance() {
        return distance;
    }

    /**
     * méthode qui compare la distance entre le CheminEntreEtape et celui mis en paramètre
     * @param o: CheminEntreEtape à vérifier
     * @return: un boolean ( true si la distance est la même)
     */
    @Override
    public int compareTo(CheminEntreEtape o) {
        return this.getDistance() - o.getDistance();
    }

    @Override
    public String toString() {
        return "CheminEntreEtape{" +
                "etapeDepart=" + etapeDepart +
                ", etapeArrivee=" + etapeArrivee +
                ", distance=" + distance +
                '}';
    }
}
