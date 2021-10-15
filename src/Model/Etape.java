package Model;

import java.util.Date;

public class Etape extends Adresse{

    //public Date heureDePassage;
    public Integer dureeEtape;


    public Etape(float latitude, float longitude, Long idAdresse, Integer dureeEtape) {
        super(latitude, longitude, idAdresse);
        this.dureeEtape = dureeEtape;

    }

    /*public Date getHeureDePassage() {
        return heureDePassage;
    }*/

    public Integer getDureeEtape() {
        return dureeEtape;
    }

    @Override
    public String toString() {
        return super.toString()+"Etape{" +
                "dureeEtape=" + dureeEtape +
                '}';
    }
}
