package Model;

public class Segment {

    private Adresse origine;
    private Adresse destination;
    private String nom;
    private Long longueur;


    public Segment(Adresse origine, Adresse destination, String nom, Long longueur) {
        this.origine = origine;
        this.destination = destination;
        this.nom = nom;
        this.longueur = longueur;
    }
}
