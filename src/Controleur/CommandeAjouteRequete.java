package Controleur;

import Model.Adresse;
import Model.Carte;
import Model.Etape;
import Model.Tournee;

public class CommandeAjouteRequete implements Commande{

    /**
     * Execute the command this
     */
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

    @Override
    public void faireCommande(){
        tournee.ajoutChemin(etape,precedent,carte);
    }

    /**
     * Execute the reverse command of this
     */
    @Override
    public void defaireCommande(){

    }
}
