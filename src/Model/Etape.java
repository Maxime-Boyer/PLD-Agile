package Model;

import java.sql.Timestamp;
import java.util.Date;

public class Etape extends Adresse{

    public Timestamp heureDePassage;
    public Integer dureeEtape;

    /**
     * Constructeur de la classe étape
     * @param latitude
     * @param longitude
     * @param idAdresse
     * @param dureeEtape
     * @param heureDePassage
     */
    public Etape(double latitude, double longitude, Long idAdresse, Integer dureeEtape, Timestamp heureDePassage) {
        super(latitude, longitude, idAdresse);
        this.dureeEtape = dureeEtape;
        this.heureDePassage=heureDePassage;

    }

    public Date getHeureDePassage() {
        return heureDePassage;
    }

    public Integer getDureeEtape() {
        return dureeEtape;
    }

    @Override
    public String toString() {
        return super.toString()+"Etape{" +
                "heureDePassage=" + heureDePassage +
                ", dureeEtape=" + dureeEtape +
                '}';
    }
}
