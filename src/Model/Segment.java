package Model;

public class Segment {

    private Adresse origine;
    private Adresse destination;
    private String nom;
    private Double longueur;


    public Segment(Adresse origine, Adresse destination, String nom, Double longueur) {
        this.origine = origine;
        this.destination = destination;
        this.nom = nom;
        this.longueur = longueur;
    }

    @Override
    public String toString() {
        return "Segment{" +
                "origine=" + origine +
                ", destination=" + destination +
                ", nom='" + nom + '\'' +
                ", longueur=" + longueur +
                '}';
    }
}
