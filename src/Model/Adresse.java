package Model;

public class Adresse {
    private double latitude;
    private double longitude;
    private Long idAdresse;

    public Adresse(double latitude, double longitude, Long idAdresse) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.idAdresse = idAdresse;
    }

    @Override
    public String toString() {
        return "Adresse{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                ", idAdresse=" + idAdresse +
                '}';
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public Long getIdAdresse() {
        return idAdresse;
    }
}
