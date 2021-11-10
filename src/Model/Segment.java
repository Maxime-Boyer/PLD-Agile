package Model;

public class Segment {

    private Adresse origine;
    private Adresse destination;
    private String nom;
    private Double longueur;

    /**
     * Constructeur de Segment, tronçon de route orienté parcourable entre deux adresses directement adjacentes
     * @param origine: Adresse d'origine du segment
     * @param destination: Adresse de destination du segment
     * @param nom: nom du segment
     * @param longueur: longueur du segment
     */
    public Segment(Adresse origine, Adresse destination, String nom, Double longueur) {
        this.origine = origine;
        this.destination = destination;
        this.nom = nom;
        this.longueur = longueur;
    }

    /**
     * methode qui affiche le segment
     * @return
     */
    @Override
    public String toString() {
        return "Segment{" +
                "origine=" + origine +
                ", destination=" + destination +
                ", nom='" + nom + '\'' +
                ", longueur=" + longueur +
                '}';
    }

    /**
     * methode qui renvoie l'adresse d'origine
     * @return
     */
    public Adresse getOrigine() {
        return origine;
    }

    /**
     * merhode qui place l'adresse d'origine
     * @param origine: adresse d'origine
     */
    public void setOrigine(Adresse origine) {
        this.origine = origine;
    }

    /**
     * methode qui renvoie l'adresse de destination
     * @return
     */
    public Adresse getDestination() {
        return destination;
    }

    /**
     * methode qui place l'adresse de destination
     * @param destination: adresse de destination
     */
    public void setDestination(Adresse destination) {
        this.destination = destination;
    }

    /**
     * merhode qi renvoie le nom
     * @return
     */
    public String getNom() {
        return nom;
    }



    /**
     * methode qui renvoie la longueur
     * @return
     */
    public Double getLongueur() {
        return longueur;
    }


}
