package Controleur;

import Model.Carte;
import Model.Tournee;
import Vue.Fenetre;

import javax.swing.*;

/**
 * Cinquième état de l'ajout de requêtes, permet de valider la durée de l'étape
 */
public class EtatAjoutRequete5DureeDepot implements Etat {

    @Override
    public void validerAjoutDureeEtape(Controleur controleur, Fenetre fenetre) {
        try {
            //On affiche la pop up de saisie de durée de l'étape de dépot
            Integer duree = fenetre.getPopUpSaisieDuree().getDureePopUp();
            //On transmet la durée de depot à l'état 6 de l'ajout de requete
            controleur.etatAjoutRequete6PointPrecedentDepot.mettreAjourDuree(duree);
            //On va à l'état 6 et on met à jour l'affichage de la fenetre, le message utilisateur
            controleur.setEtatActuel(controleur.etatAjoutRequete6PointPrecedentDepot);
            fenetre.afficherEtatAjoutRequete6();
        } catch (Exception e) {
            String messageErreur = "Veuillez saisir un nombre  positif et < 2147483647 ";
            System.out.println("ERREUR " + e);
            JOptionPane.showMessageDialog(null, messageErreur);
        }

    }

    @Override
    public void annuler(Controleur controleur, Fenetre fenetre, Carte carte, ListeDeCommandes l, Tournee tournee) {
        //Je vide la liste des requetes temporaire
        fenetre.getCartePanel().viderNouvelleRequete();
        //On revient à l'état ordonné de la tournée
        controleur.setEtatActuel(controleur.etatTourneeOrdonnee);
        //On force le remove de la pop up de saisie
        fenetre.getCartePanel().remove(fenetre.getPopUpSaisieDuree());
        //On change l'affichage de la fenetre pour afficher l'état de la tournée ordonnée
        fenetre.afficherEtatTourneePreparee(tournee);
    }


}
