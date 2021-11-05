package Controleur_Package;

import Model.Carte;
import Model.LecteurXML;
import Model.Tournee;
import Vue.Fenetre;

import javax.swing.*;

public class EtatPlanAffiche implements Etat {
    @Override
    public void chargerListeRequete (Controleur controleur, Fenetre fenetre, Carte carte) {
        System.out.println("EtatPlanAffiche : chargerListeRequete");
        /*fenetre.afficherEtat(NomEtat.ETAT_TOURNEE_CHARGEE);
        controleur.setEtatActuel(controleur.etatTourneeChargee);*/

        //Récupère le nom du fichier choisi
        String nomFichier = fenetre.afficherChoixFichier();
        //Appel la méthode qui vérifie si le fichier est valide et récupère la tournee
        Tournee tournee;
        LecteurXML lecteur = new LecteurXML();
        try {
            System.out.println("    avant");
            tournee = lecteur.lectureRequete(nomFichier, carte);
            System.out.println("    après tournee = " + tournee);
            //Change vers l'état PlanAffiche avec la nouvelle carte
            fenetre.afficherEtatTourneChargee(tournee);
            controleur.setEtatActuel(controleur.etatTourneeChargee);
        } catch (Exception e) {
            //En cas d'erreur
            String messageErreur = e.getMessage();
            System.out.println("ERREUR "+e);
            JOptionPane.showMessageDialog(null, messageErreur);
            //Reste dans l'état actuel
        }

    }

    @Override
    public void chargerPlan (Controleur controleur, Fenetre fenetre, Carte carte) {
        System.out.println("EtatPlanAffiche : chargerPlan");

        //Récupère le nom du fichier choisi
        String nomFichier = fenetre.afficherChoixFichier();
        //Appel la méthode qui vérifie si le fichier est valide et récupère la carte
        LecteurXML lecteur = new LecteurXML();
        try {

            carte = lecteur.lectureCarte(nomFichier, carte);
            //Change vers l'état PlanAffiche avec la nouvelle carte
            fenetre.retirerCartePanel();
            fenetre.retirerMenuLateral();
            fenetre.afficherEtatPlanAffiche(carte);
            controleur.setEtatActuel(controleur.etatPlanAffiche);
        } catch (Exception e) {
            //En cas d'erreur
            String messageErreur = e.getMessage();
            System.out.println("ERREUR "+e);
            JOptionPane.showMessageDialog(null, messageErreur);
            //Change vers l'état Initial
            /*fenetre.retirerCartePanel();
            fenetre.retirerMenuLateral();
            fenetre.afficherEtat(NomEtat.ETAT_INITIAL);
            controleur.setEtatActuel(controleur.etatInitial);*/
        }
    }

}
