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
     * Méthode  qui permet l'ajout d'une duree d'étape de dépot lorsqu'on ajoute une étape après avoir chargé la tournée
     *
     * @param controleur: controleur pour mettre l'état actuel et faire la mis à jour
     * @param fenetre:    la où se trouve la pop up de saisie
     */
    @Override
    public void validerAjoutDureeEtape(Controleur controleur, Fenetre fenetre) {
        try {
            Integer duree = fenetre.getPopUpSaisieDuree().getDureePopUp();
            controleur.etatAjoutRequete6PointPrecedentDepot.mettreAjourDuree(duree);
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
        //tournee.enleverChemin(collecte,carte);
        fenetre.getCartePanel().viderNouvelleRequete();
        controleur.setEtatActuel(controleur.etatTourneeOrdonnee);
        fenetre.getCartePanel().remove(fenetre.getPopUpSaisieDuree());
        fenetre.afficherEtatTourneePreparee(tournee);
        //tournee.notifyObservers(tournee);
    }


}
