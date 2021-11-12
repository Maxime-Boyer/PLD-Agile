package Controleur;

import Exceptions.CommandeImpossibleException;
import Model.Carte;
import Model.Etape;
import Model.Requete;
import Model.Tournee;

/**
 * Permet d'exécuter la commande d'ajout de requêtes
 */
public class CommandeAjouteRequete implements Commande {

    Tournee tournee;
    Etape precedentDepot;
    Etape precedentCollecte;
    Requete requete;
    Carte carte;

    /**
     * Créé la commande qui ajoute une requête dans une tournee
     * @param requete la requete qui doit être àjouter
     * @param precedentCollecte l'étape qui précède la collecte dans la tournée
     * @param precedentDepot l'étape qui précède le dépot dans la tournée
     * @param tournee la tournee auquelle est ajouté
     * @param carte la carte la carte qui contient les adresses et segments;
     */

    public CommandeAjouteRequete(Requete requete, Etape precedentCollecte, Etape precedentDepot, Tournee tournee, Carte carte) {
        this.tournee = tournee;
        this.carte = carte;
        this.requete = requete;
        this.precedentCollecte = precedentCollecte;
        this.precedentDepot = precedentDepot;
    }

    /**
     * Execute la commande de this, ici, celà correspond à la méthode d'ajout d'une requête
     */
    @Override
    public void faireCommande() {
        tournee.ajoutRequete(requete, precedentCollecte, precedentDepot, carte);
    }

    /**
     * Execute la commande inverse de this, ici, celà correspond à la méthode de suppression d'une requête
     */
    @Override
    public void defaireCommande() throws CommandeImpossibleException {
        tournee.supprimerRequete(requete, carte);
    }

}
