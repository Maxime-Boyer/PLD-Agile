package Model;

public class Requete {
    private Etape etapeCollecte;
    private Etape etapeDepot;

    public Requete(Etape etapeCollecte, Etape etapeDepot) {
        this.etapeCollecte = etapeCollecte;
        this.etapeDepot = etapeDepot;
    }

    public Etape getEtapeCollecte() {
        return etapeCollecte;
    }

    public Etape getEtapeDepot() {
        return etapeDepot;
    }
}
