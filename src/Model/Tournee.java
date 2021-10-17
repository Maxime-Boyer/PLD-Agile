package Model;

import java.sql.Array;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Tournee {
    private Adresse adresseDepart;
    private LocalTime heureDepart;
    private List<Requete> listeRequetes;
    private List<CheminEntreEtape> listeChemins;

    public Tournee(){
        listeRequetes = new ArrayList<>();
        listeChemins = new ArrayList<>();
    }

    public Adresse getAdresseDepart() {
        return adresseDepart;
    }

    public LocalTime getDateDepart() {
        return heureDepart;
    }

    public List<Requete> getListeRequetes() {
        return listeRequetes;
    }

    public List<CheminEntreEtape> getListeChemins() {
        return listeChemins;
    }

    public void setAdresseDepart(Adresse adresseDepart) {
        this.adresseDepart = adresseDepart;
    }

    public void setHeureDepart(LocalTime heureDepart) {
        this.heureDepart = heureDepart;
    }
}
