package Model;

import java.util.LinkedList;
import java.util.List;

/**
 * Une adresse représente une intersection entre plusieurs segments.
 * Une adresse se caractérise par un id et des coordonnées géographiques.
 */
public class Adresse {
    private double latitude;
    private double longitude;
    private Long idAdresse;
    private List<Segment> segmentsSortants;

    /**
     * Constructeur de la classe Adresse, c'est un croisement entre deux ou plusieurs segments repérés par une longitude et une latitude et un id unique
     * @param latitude:  latitude de l'Adresse
     * @param longitude: longitude de l'Adresse
     * @param idAdresse: id de l'Adresse
     */
    public Adresse(double latitude, double longitude, Long idAdresse) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.idAdresse = idAdresse;
        segmentsSortants = new LinkedList<>();
    }

    /**
     * Constructeur par défaut d'Adresse
     */
    public Adresse() {
        segmentsSortants = new LinkedList<>();
    }

    /**
     * constructeur de l'Adresse avec une latitude et une longitude utile pour avoir l'Adresse de la position du clic de la souris sur l'IHM
     *
     * @param latitude:  latitude du clic
     * @param longitude: longitude du clic
     */
    public Adresse(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        segmentsSortants = new LinkedList<>();
    }

    /**
     * Méthode permetant d'ajouter un segment à la liste des segments sortant d'une Adresse
     *
     * @param segment: segment à ajouter à cette liste
     */
    public void ajouterSegmentSortant(Segment segment) {
        segmentsSortants.add(segment);
    }

    /**
     * Méthode qui retourne la liste des segments sortant
     *
     * @return la liste des segments sortants de l'Adresse
     */
    public List<Segment> getSegmentsSortants() {
        return segmentsSortants;
    }

    /**
     * Méthode pour retourner la latitude de l'adresse
     *
     * @return la latitude de l'Adresse
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Méthode pour retourner la longitude de l'adresse
     *
     * @return la longitude de l'Adresse
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Méthode pour retourner l'id de l'adresse
     *
     * @return l'id de l'Adresse
     */
    public Long getIdAdresse() {
        return idAdresse;
    }

    /**
     * Méthode permettant l'affichage textuel de l'adresse
     *
     * @return L'objet adresse sous forme textuel
     */
    @Override
    public String toString() {
        return "Adresse{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                ", idAdresse=" + idAdresse +
                '}';
    }
}
