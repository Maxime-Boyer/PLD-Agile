package Controleur;

import Exceptions.CommandeImpossibleException;
import Model.Adresse;
import Model.Carte;
import Model.Etape;
import Model.Tournee;
import Vue.Fenetre;

import javax.swing.*;

public class EtatAjoutRequete2DureeCollecte implements Etat{
    /**
     * méthode qui permet l'ajout d'une duree d'étape de collecte lorsqu'on ajoute une étape après avoir chargé la tournée
     * @param controleur: controleur pour mettre l'état actuel et faire la mis à jour
     * @param fenetre: la où se trouve la pop up de saisie
     */
    @Override
    public void validerAjoutDureeEtape(Controleur controleur, Fenetre fenetre){
        try{
            int duree = fenetre.getPopUpSaisieDuree().getDureePopUp();
            controleur.etatAjoutRequete3PointPrecedentCollecte.mettreAjourDuree(duree);
            controleur.setEtatActuel(controleur.etatAjoutRequete3PointPrecedentCollecte);
            fenetre.afficherEtatAjoutRequete3();
        }catch (Exception ex){
            String messageErreur = "Veuillez saisir un nombre  positif et < 2147483647 ";
            System.out.println("ERREUR " + ex);
            JOptionPane.showMessageDialog(null, messageErreur);
        }
    }

    @Override
    public void annuler(Controleur controleur, Fenetre fenetre, Carte carte, ListeDeCommandes l, Tournee tournee){
        fenetre.getCartePanel().viderNouvelleRequete();
        controleur.setEtatActuel(controleur.etatTourneeOrdonnee);
        fenetre.getCartePanel().remove(fenetre.getPopUpSaisieDuree());
        fenetre.afficherEtatTourneePreparee(tournee);

    }




}
