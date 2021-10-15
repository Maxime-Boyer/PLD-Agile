package Model;

import java.util.List;

public class Carte {
    private List<Adresse> listeAdresses;
    private List<Segment> listeSegments;
    private String nomCarte;

    public Carte(List<Adresse> listeAdresses, List<Segment> listeSegments) {
        this.listeAdresses = listeAdresses;
        this.listeSegments = listeSegments;
    }


}
