package Controleur;

import Exceptions.CommandeImpossibleException;
import Model.Carte;
import Model.Etape;
import Model.Requete;
import Model.Tournee;

public class CommandeSupprimerRequete implements Commande{

    private Tournee tournee;
    private Requete requeteASupprimer;
    private Carte carte;
    private Etape etapePrecedentCollecte = null;
    private Etape etapePrecedentDepot = null;

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
    public void defaireCommande() throws CommandeImpossibleException {
        tournee.ajoutRequete(requeteASupprimer, etapePrecedentCollecte, etapePrecedentDepot, carte);
    }
}
