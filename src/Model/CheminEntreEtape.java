package Model;

import java.util.List;

public class CheminEntreEtape {

    public Etape etapeDepart;
    public Etape etapeArrivee;
    public List<Segment> listeSegment;
    public Integer distance;

    public CheminEntreEtape(Etape etapeDepart, Etape etapeArrivee, List<Segment> listeSegment, Integer distance) {
        this.etapeDepart = etapeDepart;
        this.etapeArrivee = etapeArrivee;
        this.listeSegment = listeSegment;
        this.distance = distance;
    }

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
