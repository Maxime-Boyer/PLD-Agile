package Model;

import java.util.List;

public class Adresse {
    private float latitude;
    private float longitude;
    private Long idAdresse;

    public Adresse(float latitude, float longitude, Long idAdresse) {
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

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public Long getIdAdresse() {
        return idAdresse;
    }
}
