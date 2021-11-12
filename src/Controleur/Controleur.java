package Controleur;

import Exceptions.CommandeImpossibleException;
import Model.Adresse;
import Model.Carte;
import Model.Requete;
import Model.Tournee;
import Vue.Fenetre;

/**
 * Controleur dans l'architecture MVC
 */
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

    /**
     * Créer le controlleur de l'application
     */
    public Controleur() {
        carte = new Carte();;
        tournee = new Tournee();
        listeDeCommandes = new ListeDeCommandes();
        this.etatActuel = etatInitial;
        //Crée la fenêtre d'affichage
        fenetre = new Fenetre(carte, tournee, this);
    }

    /**
     * Change l'état actuel du controleur
     *
     * @param etat le nouvel  état
     */
    protected void setEtatActuel(Etat etat) {
        etatActuel = etat;
    }

    /**
     * Méthode appelée par fenêtre après avoir cliqué sur le bouton "Importer un plan"
     */
    public void chargerPlan() {
        etatActuel.chargerPlan(this, fenetre, carte, tournee);
    }

    /**
     * Méthode appelée par fenetre après avoir cliqué sur le bouton "Importer tournée"
     */
    public void chargerListeRequete() {
        etatActuel.chargerListeRequete(this, fenetre, carte, tournee);

    }

    /**
     * Méthode appelée par fenetre après avoir cliqué sur le bouton "Calculer l'itinéraire"
     * FIXME : cohérence du vocabulaire
     */
    public void preparerTournee() {
        etatActuel.preparerTournee(this, fenetre, carte, tournee, listeDeCommandes);
    }

    /**
     * Méthode appelée par fenetre après avoir cliqué sur la croix d'une étape de la requête qui doit être supprimé
     */
    public void supressionRequete(Requete requete) {
        etatActuel.supressionRequete(this, fenetre, listeDeCommandes, tournee, carte, requete);
    }

    /**
     * Méthode appelée par fenetre après avoir cliqué sur le bouton "exporter feuiile de route"
     */
    public void exporterFeuilleDeRoute() {
        etatActuel.exporterFeuilleDeRoute(tournee, fenetre.cartePanel);
    }

    /**
     * Méthode appelée par fenetre après avoir cliqué sur le bouton "Ajouter requête"
     */
    public void ajoutRequete() {
        etatActuel.ajoutRequete(this, fenetre);
    }

    /**
     * Méthode appeléee par la fenêtre après avoir cliqué sur le clic gauche de la souris
     *
     * @param a: Adresse obtenu lors du clic
     */
    public void cliqueGauche(Adresse a) throws CommandeImpossibleException {
        etatActuel.cliqueGauche(this, fenetre, carte, listeDeCommandes, tournee, a);
    }

    /**
     * Méthode appeléee par la fenêtre après avoir cliqué sur le clic droit de la souris
     */
    public void annuler(){
        etatActuel.annuler(this, fenetre,carte,listeDeCommandes, tournee);
    }

    /**
     * Méthode appelée par fenetre après avoir cliqué sur le bouton "Valider" lors de l'ajout de la durée d'un point de collecte/depot
     */
    public void validerAjoutDureeEtape() {
        etatActuel.validerAjoutDureeEtape(this, fenetre);
    }

    /**
     * Méthode appelée par fenetre après avoir cliqué sur le bouton "Undo"
     *
     * @throws CommandeImpossibleException retourne une erreure si la commande est impossible
     */
    public void cliqueBoutonUndo() throws CommandeImpossibleException {
        listeDeCommandes.defaire();
        fenetre.setAuthorisationCliquerBoutonRedo(true);

        if (listeDeCommandes.getIndexCourant() < 0) {
            fenetre.setAuthorisationCliquerBoutonUndo(false);
        }
        fenetre.afficherEtatTourneePreparee(tournee);
        tournee.notifyObservers();


    }

    /**
     * Méthode appelée par fenetre après avoir cliqué sur le bouton "Redo"
     *
     * @throws CommandeImpossibleException retourne une erreure si la commande est impossible
     */
    public void cliqueBoutonRedo() throws CommandeImpossibleException {
        listeDeCommandes.refaire();
        fenetre.setAuthorisationCliquerBoutonUndo(true);
        if (listeDeCommandes.getIndexCourant() == listeDeCommandes.getList().size() - 1) {
            fenetre.setAuthorisationCliquerBoutonRedo(false);
        }
        fenetre.afficherEtatTourneePreparee(tournee);
        tournee.notifyObservers();
    }

}
