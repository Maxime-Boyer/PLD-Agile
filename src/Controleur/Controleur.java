package Controleur;

import Model.Carte;
import Vue.Fenetre;

public class Controleur {

    private Carte carte;
    private Fenetre fenetre;
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
        fenetre = new Fenetre(carte, echelle, this);
    }

    protected void setEtatActuel(Etat etat) {
        etatActuel = etat;
    }

    public void chargerPlan() {
        etatActuel.chargerPlan(this, fenetre);
    }

    public void chargerNouveauPlan() {
        etatActuel.chargerNouveauPlan(this, fenetre);
    }

    public void chargerListeRequete() {
        etatActuel.chargerListeRequete(this, fenetre);
    }

    public void chargerNouvelleListeRequete() {
        etatActuel.chargerNouvelleListeRequete(this, fenetre);
    }

    public void preparerTournee() {
        etatActuel.preparerTournee(this, fenetre);
    }

    public void supressionRequete() {
        etatActuel.supressionRequete(this, fenetre);
    }

    public void selectionPointCarte() {
        etatActuel.selectionPointCarte(this, fenetre);
    }

    public void validerSupressionRequete() {
        etatActuel.validerSupressionRequete(this, fenetre);
    }

    public void exporterFeuilleDeRoute() {
        etatActuel.exporterFeuilleDeRoute(this, fenetre);
    }

    public void ajoutRequete() {
        etatActuel.ajoutRequete(this, fenetre);
    }

    public void ajoutRequetePointCollecte() {
        etatActuel.ajoutRequetePointCollecte(this, fenetre);
    }

    public void ajoutRequeteOrdreCollecte() {
        etatActuel.ajoutRequeteOrdreCollecte(this, fenetre);
    }

    public void ajoutRequeteDureeCollecte() {
        etatActuel.ajoutRequeteDureeCollecte(this, fenetre);
    }

    public void ajoutRequetePointDepot() {
        etatActuel.ajoutRequetePointDepot(this, fenetre);
    }

    public void ajoutRequeteOrdreDepot() {
        etatActuel.ajoutRequeteOrdreDepot(this, fenetre);
    }

    public void ajoutRequeteDureeDepot() {
        etatActuel.ajoutRequeteDureeDepot(this, fenetre);
    }
}
