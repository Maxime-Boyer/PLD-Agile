package Model;

public class Adresse {
    private double latitude;
    private double longitude;
    private Long idAdresse;

    /**
     * Constructeur de la classe Adresse
     * @param latitude
     * @param longitude
     * @param idAdresse
     */
    public Adresse(double latitude, double longitude, Long idAdresse) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.idAdresse = idAdresse;
    }

    /**
     * methode permettant l'affichage de l'adresse
     * @return
     */
    @Override
    public String toString() {
        return "Adresse{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                ", idAdresse=" + idAdresse +
                '}';
    }

    /**
     * methode pour retourner la latitude de l'adresse
     * @return
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * methode pour retourner la longitude de l'adresse
     * @return
     */
    public double getLongitude() {
        return longitude;
    }
    /**
     * methode pour retourner l'id de l'adresse
     * @return
     */
    public Long getIdAdresse() {
        return idAdresse;
    }
}
