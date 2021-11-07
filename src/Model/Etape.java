package Model;

import java.time.LocalTime;

public class Etape extends Adresse{

    // TODO: Pourquoi public???
    private LocalTime heureDePassage;
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
    public Etape(double latitude, double longitude, Long idAdresse, Integer dureeEtape, LocalTime heureDePassage) {
        super(latitude, longitude, idAdresse);
        this.dureeEtape = dureeEtape;
        this.heureDePassage=heureDePassage;
        this.nomAdresse = "";
    }

    public Etape(double latitude, double longitude, Long idAdresse) {
        super(latitude, longitude, idAdresse);
        this.nomAdresse = "";
    }

    public LocalTime getHeureDePassage() {
        return heureDePassage;
    }

    public Integer getDureeEtape() {
        return dureeEtape;
    }

    public String getNomAdresse() { return nomAdresse; }

    public void setHeureDePassage(LocalTime heureDePassage) {
        this.heureDePassage = heureDePassage;
    }

    public void setNomAdresse(String  nomAdresse){
        this.nomAdresse = nomAdresse;
    }

    public void setDureeEtape(Integer dureeEtape) {
        this.dureeEtape = dureeEtape;
    }

    @Override
    public String toString() {
        return super.toString()+"Etape{" +
                "heureDePassage=" + heureDePassage +
                ", dureeEtape=" + dureeEtape +
                '}';
    }
}
