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

    public Adresse getOrigine() {
        return origine;
    }

    public void setOrigine(Adresse origine) {
        this.origine = origine;
    }

    public Adresse getDestination() {
        return destination;
    }

    public void setDestination(Adresse destination) {
        this.destination = destination;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Double getLongueur() {
        return longueur;
    }

    public void setLongueur(Double longueur) {
        this.longueur = longueur;
    }
}
