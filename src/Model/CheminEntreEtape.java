package Model;

import java.util.List;

public class CheminEntreEtape implements Comparable<CheminEntreEtape> {

    public Etape etapeDepart;
    public Etape etapeArrivee;
    public List<Segment> listeSegment;
    public Integer distance;

    /**
     * constructeur de la classe chemin entre étape qui correspond aux chemins entre deux étapes
     * @param etapeDepart
     * @param etapeArrivee
     * @param listeSegment
     * @param distance
     */
    public CheminEntreEtape(Etape etapeDepart, Etape etapeArrivee, List<Segment> listeSegment, Integer distance) {
        this.etapeDepart = etapeDepart;
        this.etapeArrivee = etapeArrivee;
        this.listeSegment = listeSegment;
        this.distance = distance;
    }

    /**
     * retourne l'étape de départ
     * @return
     */
    public Etape getEtapeDepart() {
        return etapeDepart;
    }

    /**
     * reourne l'étape d'arrivée
     * @return
     */
    public Etape getEtapeArrivee() {
        return etapeArrivee;
    }

    /**
     * retourne la liste des segments entre les étapes
     * @return
     */
    public List<Segment> getListeSegment() {
        return listeSegment;
    }

    /**
     * retourne la distance du chemin
     * @return
     */
    public Integer getDistance() {
        return distance;
    }

    @Override
    public int compareTo(CheminEntreEtape o) {
        return this.getDistance() - o.getDistance();
    }
}
