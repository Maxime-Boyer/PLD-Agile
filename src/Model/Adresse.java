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
}
