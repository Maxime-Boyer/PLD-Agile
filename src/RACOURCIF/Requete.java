package RACOURCIF;

public class Requete {
    private Adresse addresseCollecte;
    private Adresse addresseDepot;
    private Integer dureeCollecte;
    private Integer dureeDepot;

    public Requete(Adresse addresseCollecte, Adresse addresseDepot, Integer dureeCollecte, Integer dureeDepot) {
        this.addresseCollecte = addresseCollecte;
        this.addresseDepot = addresseDepot;
        this.dureeCollecte = dureeCollecte;
        this.dureeDepot = dureeDepot;
    }
}
