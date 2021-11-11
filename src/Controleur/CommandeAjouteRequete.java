package Controleur;

import Model.Adresse;
import Model.Carte;
import Model.Etape;
import Model.Tournee;

public class CommandeAjouteRequete implements Commande{

    Tournee tournee;
    Carte carte;
    Etape etape;
    Etape precedent;

    public CommandeAjouteRequete(Tournee tournee, Carte carte, Etape etape, Etape precedent){
        this.tournee = tournee;
        this.etape =  etape;
        this.precedent = precedent;
        this.carte = carte;
    }

    /**
     * Execute la commande de this
     */
    @Override
    public void faireCommande(){
        tournee.ajoutChemin(etape,precedent,carte);
    }

    /**
     * Execute la commande inverse de this
     */
    @Override
    public void defaireCommande(){ //tournee.supprimerChemin()}
}
