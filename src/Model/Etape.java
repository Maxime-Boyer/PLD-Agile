package Model;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

public class Etape extends Adresse{

    // TODO: Pourquoi public???
    public Timestamp heureDePassage;
    public Integer dureeEtape;
    private String nomAdresse;


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
        this.nomAdresse = "";
    }

    public Date getHeureDePassage() {
        return heureDePassage;
    }

    public Integer getDureeEtape() {
        return dureeEtape;
    }

    public String getNomAdresse() { return nomAdresse; }

    public void setNomAdresse(String  nomAdresse){
        this.nomAdresse = nomAdresse;
    }

    @Override
    public String toString() {
        return super.toString()+"Etape{" +
                "heureDePassage=" + heureDePassage +
                ", dureeEtape=" + dureeEtape +
                '}';
    }
/*
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Etape etape = (Etape) o;
        return getHeureDePassage().equals(etape.getHeureDePassage()) && getDureeEtape().equals(etape.getDureeEtape()) && getNomAdresse().equals(etape.getNomAdresse());
    }*/

    @Override
    public int hashCode() {
        return Objects.hash(getHeureDePassage(), getDureeEtape(), getNomAdresse());
    }

}
