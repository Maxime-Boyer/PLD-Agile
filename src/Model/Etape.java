package Model;

import java.util.Date;

public class Etape {

    public Date heureDePassage;
    public Integer dureeEtape;
    public Adresse adresseEtape;

    public Etape(Date heureDePassage, Integer dureeEtape, Adresse adresseEtape) {
        this.heureDePassage = heureDePassage;
        this.dureeEtape = dureeEtape;
        this.adresseEtape = adresseEtape;
    }
}
