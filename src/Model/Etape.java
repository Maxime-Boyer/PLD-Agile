package Model;

import java.time.LocalTime;

/**
 * Représenteune étape pour une tournée, c'est-à-dire une adresse avec une heure de passage et une durrée
 */
public class Etape extends Adresse {

    private LocalTime heureDePassage;
    private Integer dureeEtape;
    private String nomAdresse;


    /**
     * Constructeur de la classe Etape,point de collecte ou de dépôt situé à une adresse qui descend de Adresse. Ce constructeur est utilisée après le calcul du plus court chemin
     *
     * @param latitude:       latitude de l'etape
     * @param longitude:      longitude de l'etape
     * @param idAdresse:      id de l'adresse de l'etape
     * @param dureeEtape:     duree de l'étape
     * @param heureDePassage: heure de passage calculée après le calcul du plus court chemin
     */
    public Etape(double latitude, double longitude, Long idAdresse, Integer dureeEtape, LocalTime heureDePassage) {
        super(latitude, longitude, idAdresse);
        this.dureeEtape = dureeEtape;
        this.heureDePassage = heureDePassage;
        this.nomAdresse = "";
    }

    /**
     * Constructeur de la classe Etape, sans heure de passage
     *
     * @param latitude
     * @param longitude
     * @param idAdresse
     * @param dureeEtape
     */
    public Etape(double latitude, double longitude, Long idAdresse, Integer dureeEtape) {
        super(latitude, longitude, idAdresse);
        this.nomAdresse = "";
        this.dureeEtape = dureeEtape;
    }

    /**
     * Méthode qui retourne l'heure de passage
     *
     * @return: l'heure de passage
     */
    public LocalTime getHeureDePassage() {
        return heureDePassage;
    }

    /**
     * Méthode qui retourne la duree de l'étape
     *
     * @return: la durée de l'Etape
     */
    public Integer getDureeEtape() {
        return dureeEtape;
    }

    /**
     * Méthode qui retourne le nom
     *
     * @return: le nom de l'Etape
     */
    public String getNomAdresse() {
        return nomAdresse;
    }

    /**
     * Méthode qui place l'heure de passage en paramètre
     *
     * @param heureDePassage: heure de passage
     */
    public void setHeureDePassage(LocalTime heureDePassage) {
        this.heureDePassage = heureDePassage;
    }

    /**
     * Méthode qui place le nom de l'adresse
     *
     * @param nomAdresse: nom de l'adresse
     */
    public void setNomAdresse(String nomAdresse) {
        this.nomAdresse = nomAdresse;
    }

    /**
     * Méthode permettant l'affichage textuel de l'étape
     *
     * @return L'objet Etape sous forme textuel
     */
    @Override
    public String toString() {
        return "Etape{" +
                "idAdress=" + super.getIdAdresse() +
                ", heureDePassage=" + heureDePassage +
                ", dureeEtape=" + dureeEtape +
                '}';
    }


}
