package Model;

import java.util.List;

public class Adresse {
    private float latitude;
    private float longitude;
    private Integer idAdresse;

    public Adresse(float latitude, float longitude, int idAdresse) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.idAdresse = idAdresse;
    }

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public Integer getIdAdresse() {
        return idAdresse;
    }
}
