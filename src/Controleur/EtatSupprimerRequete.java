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
            Etape etapePrecedentCollecte = tournee.precedentCollecte(requete);
            Etape etapePrecedentDepot = tournee.precedentDepot(requete);
            listeDeCommandes.ajouter(new CommandeSupprimerRequete(tournee, requete, etapePrecedentCollecte, etapePrecedentDepot, carte));
            fenetre.setAuthorisationCliquerBoutonUndo(true);
            controleur.setEtatActuel(controleur.etatTourneeOrdonnee);
            fenetre.afficherEtatTourneePreparee(tournee);
            tournee.notifyObservers(tournee);
        } catch (CommandeImpossibleException e) {
            e.printStackTrace();
        }
    }

}
