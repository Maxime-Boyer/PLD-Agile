package Controleur;

import Model.Carte;
import Model.Tournee;
import Vue.Fenetre;

import javax.swing.*;

/**
 * Deuxième état de l'ajout de requêtes, permet de valider la durée de l'étape
 */
public class EtatAjoutRequete2DureeCollecte implements Etat {
    /**
     * Méthode qui permet l'ajout d'une duree d'étape de collecte lorsqu'on ajoute une étape
     * @param controleur controleur qui permet de changer l'état actuel, et faire la mettre à jour la durée pour l'état suivant
     * @param fenetre    la où se trouve la pop up de saisie, et l'affichage de l'état suivant
     */
    @Override
    public void validerAjoutDureeEtape(Controleur controleur, Fenetre fenetre) {
        try {
            int duree = fenetre.getPopUpSaisieDuree().getDureePopUp();
            controleur.etatAjoutRequete3PointPrecedentCollecte.mettreAjourDuree(duree);
            controleur.setEtatActuel(controleur.etatAjoutRequete3PointPrecedentCollecte);
            fenetre.afficherEtatAjoutRequete3();
        } catch (Exception ex) {
            String messageErreur = "Veuillez saisir un nombre  positif et < 2147483647 ";
            System.out.println("ERREUR " + ex);
            JOptionPane.showMessageDialog(null, messageErreur);
        }
    }
    /** Méthode qui permet de revenir à l'état tournée ordonnée, en annulant tout ce qui a été fait jusque là
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
