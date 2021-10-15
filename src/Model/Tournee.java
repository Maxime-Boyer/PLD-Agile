package Model;

import java.util.List;

public class Tournee {
    private Adresse adresseDepart;
    private Integer dateDepart;
    private List<Requete> listeRequetes;
    private List<CheminEntreEtape> listeChemins;

    public Tournee(Adresse adresseDepart, Integer dateDepart, List<Requete> listeRequetes, List<CheminEntreEtape> listeChemins) {
        this.adresseDepart = adresseDepart;
        this.dateDepart = dateDepart;
        this.listeRequetes = listeRequetes;
        this.listeChemins = listeChemins;
    }
}
