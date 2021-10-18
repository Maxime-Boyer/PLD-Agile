package Controleur;

import Model.Carte;

public class Controleur {

    private Carte carte;
    //window
    //listofcommands
    private Etat etatActuel;
    protected final EtatAjoutRequete1PointCollecte etatAjoutRequete1PointCollecte = new EtatAjoutRequete1PointCollecte();
    protected final EtatAjoutRequete2PositionCollecte etatAjoutRequete2PositionCollecte = new EtatAjoutRequete2PositionCollecte();
    protected final EtatAjoutRequete3DureeCollecte etatAjoutRequete3DureeCollecte = new EtatAjoutRequete3DureeCollecte();
    protected final EtatAjoutRequete4PointDepot etatAjoutRequete4PointDepot = new EtatAjoutRequete4PointDepot();
    protected final EtatAjoutRequete5PositionDepot etatAjoutRequete5PositionDepot = new EtatAjoutRequete5PositionDepot();
    protected final EtatAjoutRequete6DureeDepot etatAjoutRequete6DureeDepot = new EtatAjoutRequete6DureeDepot();
    protected final EtatInitial etatInitial = new EtatInitial();
    protected final EtatInitialTournee etatInitialTournee = new EtatInitialTournee();
    protected final EtatOrdonneTournee etatOrdonneTournee = new EtatOrdonneTournee();
    protected final EtatPlanAffiche etatPlanAffiche = new EtatPlanAffiche();
    protected final EtatSupprimerRequete etatSupprimerRequete = new EtatSupprimerRequete();
    protected final EtatSupprimerRequetePointSelectionne etatSupprimerRequetePointSelectionne = new EtatSupprimerRequetePointSelectionne();

    public Controleur(Carte carte, int echelle) {
        this.carte = carte;
        //listofcommands
        this.etatActuel = etatInitial;
        //window
    }

    protected void setEtatActuel(Etat etat){
        etatActuel = etat;
    }

    public void chargerPlan(){};
    public void chargerNouveauPlan(){};
    public void chargerListeRequete(){};
    public void chargerNouvelleListeRequete(){};
    public void preparerTournee(){};
    public void supressionRequete(){};
    public void selectionPointCarte(Controleur c){};
    public void validerSupressionRequete(Controleur c){};
    public void exporterFeuilleDeRoute(Controleur c){};
    public void ajoutRequete(Controleur c){};
    public void ajoutRequetePointCollecte(Controleur c){};
    public void ajoutRequeteOrdreCollecte(Controleur c){};
    public void ajoutRequeteDureeCollecte(Controleur c){};
    public void ajoutRequetePointDepot(Controleur c){};
    public void ajoutRequeteOrdreDepot(Controleur c){};
    public void ajoutRequeteDureeDepot(Controleur c){};
}
