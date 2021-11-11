package Controleur;

import Exceptions.CommandeImpossibleException;
import Model.Carte;
import Model.Etape;
import Model.Requete;
import Model.Tournee;

/**
 * Permet l'ajout d'exécuter la commande de suppression de requêtes
 */
public class CommandeSupprimerRequete implements Commande{

    private Tournee tournee;
    private Requete requeteASupprimer;
    private Carte carte;
    private Etape etapePrecedentCollecte;
    private Etape etapePrecedentDepot;

    /**
     * Créé la commande qui supprime une requte dans une tournee
     * @param tournee la tournee auquelle est retiré
     * @param requeteASupprimer la requete qui va être supprimé de la tournee;
     */
    public CommandeSupprimerRequete(Tournee tournee, Requete requeteASupprimer,Etape etapePrecedentCollecte,Etape etapePrecedentDepot, Carte carte) {
        this.tournee = tournee;
        this.carte = carte;
        this.requeteASupprimer = requeteASupprimer;
        this.etapePrecedentCollecte = etapePrecedentCollecte;
        this.etapePrecedentDepot = etapePrecedentDepot;
    }
    /**
     * Execute la commande de this
     */
    @Override
    public void faireCommande() throws CommandeImpossibleException {
        tournee.supprimerRequete(requeteASupprimer, carte);
    }
    /**
     * Execute la commande inverse de this
     */
    @Override
    public void defaireCommande() {
        tournee.ajoutRequete(requeteASupprimer, etapePrecedentCollecte, etapePrecedentDepot, carte);
    }
}
