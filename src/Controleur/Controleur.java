package Controleur;

import Exceptions.CommandeImpossibleException;
import Model.Adresse;
import Model.Carte;
import Model.Tournee;
import Vue.Fenetre;

public class Controleur {

    private Carte carte;
    private Tournee tournee;
    private Fenetre fenetre;
    private ListeDeCommandes listeDeCommandes;
    private Etat etatActuel;

    // Instances associés à chaque état possible du controleur
    protected final EtatAjoutRequete1PointCollecte etatAjoutRequete1PointCollecte = new EtatAjoutRequete1PointCollecte();
    protected final EtatAjoutRequete2DureeCollecte etatAjoutRequete2DureeCollecte = new EtatAjoutRequete2DureeCollecte();
    protected final EtatAjoutRequete3PointPrecedentCollecte etatAjoutRequete3PointPrecedentCollecte = new EtatAjoutRequete3PointPrecedentCollecte();
    protected final EtatAjoutRequete4PointDepot etatAjoutRequete4PointDepot = new EtatAjoutRequete4PointDepot();
    protected final EtatAjoutRequete5DureeDepot etatAjoutRequete5DureeDepot = new EtatAjoutRequete5DureeDepot();
    protected final EtatAjoutRequete6PointPrecedentDepot etatAjoutRequete6PointPrecedentDepot = new EtatAjoutRequete6PointPrecedentDepot();
    protected final EtatInitial etatInitial = new EtatInitial();
    protected final EtatTourneeChargee etatTourneeChargee = new EtatTourneeChargee();
    protected final EtatTourneeOrdonnee etatTourneeOrdonnee = new EtatTourneeOrdonnee();
    protected final EtatPlanAffiche etatPlanAffiche = new EtatPlanAffiche();
    protected final EtatSupprimerRequete etatSupprimerRequete = new EtatSupprimerRequete();
    protected final EtatSupprimerRequetePointSelectionne etatSupprimerRequetePointSelectionne = new EtatSupprimerRequetePointSelectionne();

    /**
     * Cré le controlleur de l'application
     * @param carte : la carte
     */
    public Controleur(Carte carte) {
        this.carte = carte;
        tournee = new Tournee();
        listeDeCommandes = new ListeDeCommandes();
        this.etatActuel = etatInitial;
        //Crée la fenêtre d'affichage
        fenetre = new Fenetre(carte, tournee, this);
    }

    /**
     * Change l'état actuel du controleur
     * @param etat : le nouvel  état
     */
    protected void setEtatActuel(Etat etat) {
        etatActuel = etat;
    }

    /**
     * Méthode appelé par fenêtre après avoir cliqué sur le bouton "Importer un plan"
     */
    public void chargerPlan() {
        etatActuel.chargerPlan(this, fenetre, carte, tournee);
        System.out.println("Controlleur : carte = ");
        System.out.println( "       aaaaaaaaaaaaaaaaaaaaaaaa" + carte);
    }

    /**
     * Méthode appelé par fenetre après  sur le bouton "Importer un plan"
     */
    public void chargerNouveauPlan() {
        etatActuel.chargerNouveauPlan(this, fenetre);
    }

    /**
     * Méthode appelé par fenetre après avoir cliqué sur le bouton "Importer tournée"
     */
    public void chargerListeRequete(){
        etatActuel.chargerListeRequete(this, fenetre, carte, tournee);

    }
    /**
     * FIXME: pour moi c'est le même qu'au dessus
     */

    public void chargerNouvelleListeRequete() {
        etatActuel.chargerNouvelleListeRequete(this, fenetre);
    }

    /**
     * Méthode appelé par fenetre après avoir cliqué sur le bouton "Calculer l'itinéraire"
     * FIXME : cohérence du vocabulaire
     */
    public void preparerTournee() {
        etatActuel.preparerTournee(this, fenetre, carte, tournee);
    }

    /**
     * Méthode appelé par fenetre après avoir cliqué sur la carte
     */
    public void selectionPointCarte() {
        etatActuel.selectionPointCarte(this, fenetre);
    }

    /**
     * Méthode appelé par fenetre après avoir cliqué sur la croix d'une étape de la requête qui doit être supprimé
     */
    public void supressionRequete() {
        etatActuel.supressionRequete(this, fenetre);
    }

    /**
     * Méthode appelé par fenetre après avoir cliqué sur la validation de la suppression de la reqête
     */
    public void validerSupressionRequete() {
        etatActuel.validerSupressionRequete(this, fenetre);
    }

    /**
     * Méthode appelé par fenetre après avoir cliqué sur le bouton "exporter feuiile de route"
     */
    public void exporterFeuilleDeRoute() {
        etatActuel.exporterFeuilleDeRoute(this, fenetre);
    }

    /**
     * Méthode appelé par fenetre après avoir cliqué sur le bouton "Ajouter requête"
     */
    public void ajoutRequete() {
        etatActuel.ajoutRequete(this, fenetre);
    }

    /**
     * Méthode appelé par fenetre après avoir cliqué sur la carte à l'endroit du nouveau point de collecte souhaité
     */
    public void ajoutRequetePointCollecte() {
        etatActuel.ajoutRequetePointCollecte(this, fenetre);
    }

    /**
     * Méthode appelé par fenetre après avoir cliqué sur le bouton "Valider position dun point de collecte"
     * TODO : vérifier ensemble si cette méthode existe
     */
    public void validerAjoutRequetePointCollecte() {
        etatActuel.validerAjoutRequetePointCollecte(this, fenetre);
    }

    /**
     * Méthode appelé par fenetre après avoir cliqué sur l'étape qui doit précéder le point de collecte
     */
    public void ajoutRequeteOrdreCollecte() {
        etatActuel.ajoutRequeteOrdreCollecte(this, fenetre);
    }

    //TODO : vérifier ensemble si il ne faut pas ajouter une méthode validerAjoutRequeteOrdreCollecte

    public void ajoutRequeteDureeCollecte() {
        etatActuel.ajoutRequeteDureeCollecte(this, fenetre);
    }

    /**
     * Méthode appelé par fenetre après avoir cliqué sur la carte à l'endroit du nouveau point de dépôt souhaité
     */
    public void ajoutRequetePointDepot() {
        etatActuel.ajoutRequetePointDepot(this, fenetre);
    }

    /**
     * Méthode appelé par fenetre après avoir cliqué sur le bouton "Valider position du point de dépôt"
     * TODO : vérifier ensemble si cette méthode existe
     */
    public void validerAjoutRequetePointDepot() {
        etatActuel.validerAjoutRequetePointDepot(this, fenetre);
    }

    /**
     * Méthode appelé par fenetre après avoir cliqué sur l'étape qui doit précéder le point de dépôt
     */
    public void ajoutRequeteOrdreDepot() {
        etatActuel.ajoutRequeteOrdreDepot(this, fenetre);
    }

    //TODO : vérifier ensemble si il ne faut pas ajouter une méthode validerAjoutRequeteOrdreDepot

    /**
     * Méthode appelé par fenetre après avoir cliqué sur le bouton "Modifier la durée du dépôt"
     * TODO : préciser
     */
    public void ajoutRequeteDureeDepot() {
        etatActuel.ajoutRequeteDureeDepot(this, fenetre);
    }


    public void cliqueGauche(Adresse a){
        etatActuel.cliqueGauche(this, fenetre,carte,listeDeCommandes, tournee, a);
    }

    /**
     * Méthode appelé par fenetre après avoir cliqué sur le bouton "undo"
     */
    public void defaire() throws CommandeImpossibleException {
        listeDeCommandes.defaire();
    }

    /**
     * Méthode appelé par fenetre après avoir cliqué sur le bouton "redo"
     */
    public void refaire() throws CommandeImpossibleException {
        listeDeCommandes.refaire();
    }

    /**
     * Méthode appelé par fenetre après avoir cliqué sur le bouton "Valider" lors de l'ajout de la durée d'un point de collecte/depot
     */
    public void validerAjoutDureeEtape() {
        etatActuel.validerAjoutDureeEtape(this, fenetre);
    }

}
