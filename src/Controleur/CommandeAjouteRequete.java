package Controleur;

import Exceptions.CommandeImpossibleException;
import Model.*;

public class CommandeAjouteRequete implements Commande{

    Tournee tournee;
    Etape precedentDepot;
    Etape precedentCollecte;
    Requete requete;
    Carte carte;

    public CommandeAjouteRequete(Requete requete, Etape precedentCollecte, Etape precedentDepot, Tournee tournee, Carte carte){
        this.tournee = tournee;
        this.carte = carte;
        this.requete = requete;
        this.precedentCollecte = precedentCollecte;
        this.precedentDepot = precedentDepot;
    }

    /**
     * Execute la commande de this
     */
    @Override
    public void faireCommande(){
        tournee.ajoutRequete(requete, precedentCollecte, precedentDepot, carte);
    }

    /**
     * Execute la commande inverse de this
     */
    @Override
    public void defaireCommande() throws CommandeImpossibleException { tournee.supprimerRequete(requete, carte);}

}
