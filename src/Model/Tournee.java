package Model;

import java.sql.Array;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Tournee {
    private Etape etapeDepart;
    private LocalTime heureDepart;
    private List<Requete> listeRequetes;
    private List<CheminEntreEtape> listeChemins;

    public Tournee(){
        listeRequetes = new ArrayList<>();
        listeChemins = new ArrayList<>();
    }

    public Etape getAdresseDepart() {
        return etapeDepart;
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

    public void setEtapeDepart(Etape etapeDepart) {
        this.etapeDepart = etapeDepart;
    }

    public void setHeureDepart(LocalTime heureDepart) {
        this.heureDepart = heureDepart;
    }

    public void setListeRequetes(List<Requete> listeRequetes) {
        this.listeRequetes = listeRequetes;
    }

    public void setListeChemins(List<CheminEntreEtape> listeChemins) {
        this.listeChemins = listeChemins;
    }

    @Override
    public String toString() {
        return "Tournee{" +
                "adresseDepart=" + etapeDepart +
                ", heureDepart=" + heureDepart +
                ", listeRequetes=" + listeRequetes +
                ", listeChemins=" + listeChemins +
                '}';
    }
}
