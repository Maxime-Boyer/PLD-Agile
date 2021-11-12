package Controleur;

import Exceptions.CommandeImpossibleException;
import Model.Carte;
import Model.Etape;
import Model.Requete;
import Model.Tournee;
import Vue.Fenetre;

/**
 * Etat atteint pour supprimmer une requête.
 */
public class EtatSupprimerRequete implements Etat {
    /**
     * Methode qui permet de supprimer une requête
     * @param controleur, le controleur
     * @param fenetre          la fenêtre
     * @param listeDeCommandes la liste des commandes pour ajouter la commande de supression
     * @param tournee          la tournée à laquelle la requête sera supprimé
     * @param carte            la carte
     * @param requete          la requête qui va être supprimer
     */
    @Override
    public void supressionRequete(Controleur controleur, Fenetre fenetre, ListeDeCommandes listeDeCommandes, Tournee tournee, Carte carte, Requete requete) {
        try {
            //On cherche les étapes précédente de la collect et du dépot de la requete dans la tournée
            Etape etapePrecedentCollecte = tournee.precedentCollecte(requete);
            Etape etapePrecedentDepot = tournee.precedentDepot(requete);
            //On ajoute la commande SupprimerRequete dans la liste des commandes
            listeDeCommandes.ajouter(new CommandeSupprimerRequete(tournee, requete, etapePrecedentCollecte, etapePrecedentDepot, carte));
            //On permet l'affichage du bouton undo
            fenetre.setAuthorisationCliquerBoutonUndo(true);
            //On revient à l'état ordonné de la tournée et on change l'affichage
            controleur.setEtatActuel(controleur.etatTourneeOrdonnee);
            fenetre.afficherEtatTourneePreparee(tournee);
            //On notifie l'observer
            tournee.notifyObservers(tournee);
        } catch (CommandeImpossibleException e) {
            e.printStackTrace();
        }
    }

}
