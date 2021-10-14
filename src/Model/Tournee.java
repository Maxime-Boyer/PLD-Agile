package Model;

import java.util.List;

public class Tournee {
    private Adresse adresseDepart;
    private Integer dateDepart;
    private List<Requete> listeRequetes;
    private List<Segment> listeSegments;

    public Tournee(Adresse adresseDepart, Integer dateDepart, List<Requete> listeRequetes, List<Segment> listeSegments) {
        this.adresseDepart = adresseDepart;
        this.dateDepart = dateDepart;
        this.listeRequetes = listeRequetes;
        this.listeSegments = listeSegments;
    }

}
