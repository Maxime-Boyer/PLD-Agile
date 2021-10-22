package Model;

import java.util.List;

public class CheminEntreEtape {

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

    public Etape getEtapeArrivee() {
        return etapeArrivee;
    }

    public List<Segment> getListeSegment() {
        return listeSegment;
    }

    public Integer getDistance() {
        return distance;
    }
}
