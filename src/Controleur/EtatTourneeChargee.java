package Controleur;

import Algorithmie.CalculateurTournee;
import Exceptions.AStarImpossibleException;
import Exceptions.ValeurNegativeException;
import Model.Carte;
import Model.LecteurXML;
import Model.Tournee;
import Vue.Fenetre;

import javax.swing.*;
import java.sql.SQLOutput;

public class EtatTourneeChargee implements Etat {

    @Override
    public void preparerTournee(Controleur controleur, Fenetre fenetre, Carte carte, Tournee tournee) {
        System.out.println("EtatTourneeChargee : preparerTournee");

        CalculateurTournee calculTournee = new CalculateurTournee(carte, tournee);
        try {
            int tempsMaxCalcul = fenetre.obtenirTempsMaxCalcul();
            //Calcul la tounee
            calculTournee.calculerTournee(tempsMaxCalcul);
            //Change vers l'état etatTourneeOrdonnee avec la nouvelle carte
            fenetre.afficherEtatTourneePreparee(tournee);
            controleur.setEtatActuel(controleur.etatTourneeOrdonnee);
        } catch (AStarImpossibleException e) {
            String messageErreur = e.getMessage();
            System.out.println("ERREUR " + e);
            JOptionPane.showMessageDialog(null, messageErreur);
            //Reste dans l'état actuel
        } catch (NumberFormatException | ValeurNegativeException e){
            String messageErreur = "Veuillez saisir un nombre  positif et < 2147483647 ";
            System.out.println("ERREUR " + e);
            JOptionPane.showMessageDialog(null, messageErreur);
        }
    }

    @Override
    public void chargerListeRequete(Controleur controleur, Fenetre fenetre, Carte carte, Tournee tournee) {
        System.out.println("EtatTourneeChargee : preparerTournee");
        /*fenetre.retirerMenuRequete();
        fenetre.afficherEtat(NomEtat.ETAT_TOURNEE_CHARGEE);
        controleur.setEtatActuel(controleur.etatTourneeChargee);*/
        //Récupère le nom du fichier choisi

        String nomFichier = fenetre.afficherChoixFichier();
        if(!nomFichier.equals("nullnull")) {
            //Appel la méthode qui vérifie si le fichier est valide et récupère la tournee
            LecteurXML lecteur = new LecteurXML();
            try {
                tournee = lecteur.lectureRequete(nomFichier, carte, tournee);
                //Change vers l'état PlanAffiche avec la nouvelle carte
                fenetre.afficherEtatTourneChargee(tournee);
                controleur.setEtatActuel(controleur.etatTourneeChargee);
            } catch (Exception e) {
                //En cas d'erreur
                String messageErreur = e.getMessage();
                System.out.println("ERREUR " + e);
                JOptionPane.showMessageDialog(null, messageErreur);
                //Reste dans l'état actuel
            }
        }
    }

    @Override
    public void chargerPlan(Controleur controleur, Fenetre fenetre, Carte carte, Tournee tournee) {
        System.out.println("EtatTourneeChargee : chargerPlan");
        /*
        fenetre.retirerCartePanel();
        fenetre.retirerMenuLateral();
        fenetre.afficherEtat(NomEtat.ETAT_PLAN_AFFICHE);
        controleur.setEtatActuel(controleur.etatPlanAffiche);*/


        //Récupère le nom du fichier choisi
        String nomFichier = fenetre.afficherChoixFichier();
        if(!nomFichier.equals("nullnull")) {
            //Appel la méthode qui vérifie si le fichier est valide et récupère la carte
            LecteurXML lecteur = new LecteurXML();
            try {
                carte = lecteur.lectureCarte(nomFichier, carte);
                //Change vers l'état PlanAffiche avec la nouvelle carte
                //fenetre.retirerCartePanel();
                tournee.reset();
                //fenetre.retirerMenuLateral();
                fenetre.afficherEtatPlanAffiche(carte);
                controleur.setEtatActuel(controleur.etatPlanAffiche);
            } catch (Exception e) {
                //En cas d'erreur
                String messageErreur = e.getMessage();
                System.out.println("ERREUR " + e);
                JOptionPane.showMessageDialog(null, messageErreur);
                //Reste dans l'état actuel
            }
        }
    }

}
