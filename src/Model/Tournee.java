package Model;

import java.sql.Array;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Observable;

public class Tournee extends Observable {
    private Adresse adresseDepart;
    private LocalTime heureDepart;
    private List<Requete> listeRequetes;
    private List<CheminEntreEtape> listeChemins;

    public Tournee(){
        listeRequetes = new ArrayList<>();
        listeChemins = new ArrayList<>();
        adresseDepart = null;
        heureDepart = null;
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

    public void setListeRequetes(List<Requete> listeRequetes) {
        this.listeRequetes = listeRequetes;
    }

    public void setListeChemins(List<CheminEntreEtape> listeChemins) {
        this.listeChemins = listeChemins;
    }

    @Override
    public String toString() {
        return "Tournee{" +
                "adresseDepart=" + adresseDepart +
                ", heureDepart=" + heureDepart +
                ", listeRequetes=" + listeRequetes +
                ", listeChemins=" + listeChemins +
                '}';
    }

    public double distanceEntreAdresse(Adresse a, Adresse b){
        double distance = Math.pow(a.getLongitude() - b.getLongitude(), 2) + Math.pow(a.getLatitude() - b.getLatitude(), 2);
        return distance;
    }

    public Adresse rechercheEtape (Adresse a){
        double distanceMin = Double.MAX_VALUE;
        double distanceCollecte;
        double distanceDepot;
        Adresse plusProche = new Adresse();
        for(Requete r : listeRequetes){
            Adresse collecte = new Adresse (r.getEtapeCollecte().getLatitude(),r.getEtapeCollecte().getLongitude());
            Adresse depot = new Adresse (r.getEtapeDepot().getLatitude(),r.getEtapeDepot().getLongitude());
            distanceCollecte = distanceEntreAdresse(a, collecte);
            distanceDepot = distanceEntreAdresse(a, depot);
            if( distanceCollecte < distanceMin){
                distanceMin = distanceCollecte;
                plusProche = collecte;
            }
            else if( distanceDepot < distanceMin){
                distanceMin = distanceDepot;
                plusProche = depot;
            }
        }

        return plusProche;
    }
}
