package Controleur;

import Exceptions.CommandeImpossibleException;
import Model.Carte;
import Model.Etape;
import Model.Requete;
import Model.Tournee;
import Vue.Fenetre;

/**
 * Etat lors de la suppression d'une requête.
 */
public class EtatSupprimerRequete implements Etat {

    @Override
    public void supressionRequete(Controleur controleur, Fenetre fenetre, ListeDeCommandes listeDeCommandes, Tournee tournee, Carte carte, Requete requete) {
        try {
            System.out.println(requete);
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
