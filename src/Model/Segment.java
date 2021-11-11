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
     * méthode qui affiche le segment
     * @return: le Segment this
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
     * méthode qui renvoie l'adresse d'origine
     * @return: l'Adresse d'origine
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
     * méthode qui renvoie l'adresse de destination
     * @return: l'Adresse de destination
     */
    public Adresse getDestination() {
        return destination;
    }

    /**
     * méthode qui place l'adresse de destination
     * @param destination: adresse de destination
     */
    public void setDestination(Adresse destination) {
        this.destination = destination;
    }

    /**
     * merhode qi renvoie le nom
     * @return: le nom
     */
    public String getNom() {
        return nom;
    }



    /**
     * méthode qui renvoie la longueur
     * @return: la longueur
     */
    public Double getLongueur() {
        return longueur;
    }


}
