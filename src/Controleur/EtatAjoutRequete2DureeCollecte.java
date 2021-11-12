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
            //On affiche la pop up de saisie de durée d'étape à l'écran
            int duree = fenetre.getPopUpSaisieDuree().getDureePopUp();
            //On transmet la durée à l'état 3 d'ajout requete
            controleur.etatAjoutRequete3PointPrecedentCollecte.mettreAjourDuree(duree);
            //On passe dans l'état 3 de l'ajout de requete
            controleur.setEtatActuel(controleur.etatAjoutRequete3PointPrecedentCollecte);
            //On change le message utilisateur et remove la pop up
            fenetre.afficherEtatAjoutRequete3();
        } catch (Exception ex) {
            String messageErreur = "Veuillez saisir un nombre  positif et < 2147483647 ";
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
        //On vide la liste de CartePanel pour ne plus afficher le point de collecte temporaire en rouge
        fenetre.getCartePanel().viderNouvelleRequete();
        //On revient dans l'état tournée ordonnée
        controleur.setEtatActuel(controleur.etatTourneeOrdonnee);
        //On force le remove de la pop up de saisie
        fenetre.getCartePanel().remove(fenetre.getPopUpSaisieDuree());
        //On change l'affichage pour remettre celui de l'état ordonnée tournée
        fenetre.afficherEtatTourneePreparee(tournee);

    }
}
