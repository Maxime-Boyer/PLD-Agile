package Controleur;

import Model.Carte;
import Model.Tournee;
import Vue.Fenetre;

import javax.swing.*;

/**
 * Cinquième état de l'ajout de requêtes, permet de valider la durée de l'étape
 */
public class EtatAjoutRequete5DureeDepot implements Etat {
    /**
     * Méthode  qui permet l'ajout d'une duree d'étape de dépot lorsqu'on ajoute un dépot
     * @param controleur: controleur pour mettre l'état actuel et faire la mis à jour
     * @param fenetre:    la où se trouve la pop up de saisie,et l'affichage de l'état suivant
     */
    @Override
    public void validerAjoutDureeEtape(Controleur controleur, Fenetre fenetre) {
        try {
            Integer duree = fenetre.getPopUpSaisieDuree().getDureePopUp();
            controleur.etatAjoutRequete6PointPrecedentDepot.mettreAJourDureeDepot(duree);
            controleur.setEtatActuel(controleur.etatAjoutRequete6PointPrecedentDepot);
            fenetre.afficherEtatAjoutRequete6();
        } catch (Exception e) {
            String messageErreur = "Veuillez saisir un nombre  positif et < 2147483647 ";
            System.out.println("ERREUR " + e);
            JOptionPane.showMessageDialog(null, messageErreur);
        }

    }

    /**
     * Méthode qui permet de revenir à l'état tournée ordonnée, en annulant tout ce qui a été fait jusque là
     * @param controleur le controleur
     * @param fenetre contient l'affichage de l'état tournée ordonnée
     * @param carte   la carte
     * @param l       la liste des commandes, contient la liste des commandes qui ont été executé
     * @param tournee la tournée, tournée à laquelle la requête doit être ajouter
     */
    @Override
    public void annuler(Controleur controleur, Fenetre fenetre, Carte carte, ListeDeCommandes l, Tournee tournee) {
        fenetre.getCartePanel().viderNouvelleRequete();
        controleur.setEtatActuel(controleur.etatTourneeOrdonnee);
        fenetre.getCartePanel().remove(fenetre.getPopUpSaisieDuree());
        fenetre.afficherEtatTourneePreparee(tournee);
    }


}
