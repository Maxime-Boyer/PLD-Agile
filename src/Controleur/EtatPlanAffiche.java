package Controleur;

import Model.Carte;
import Model.LecteurXML;
import Model.Tournee;
import Vue.Fenetre;

import javax.swing.*;

public class EtatPlanAffiche implements Etat {
    @Override
    public void chargerListeRequete (Controleur controleur, Fenetre fenetre, Carte carte, Tournee tournee) {
        System.out.println("EtatPlanAffiche : chargerListeRequete");
        /*fenetre.afficherEtat(NomEtat.ETAT_TOURNEE_CHARGEE);
        controleur.setEtatActuel(controleur.etatTourneeChargee);*/

        //Récupère le nom du fichier choisi
        String nomFichier = fenetre.afficherChoixFichier();
        //Appel la méthode qui vérifie si le fichier est valide et récupère la tournee
        LecteurXML lecteur = new LecteurXML();
        try {
            System.out.println("    avant");
            tournee = lecteur.lectureRequete(nomFichier, carte, tournee);
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
            System.out.println("EtatPlanAffiche : carte before = " + carte);
            carte = lecteur.lectureCarte(nomFichier, carte);
            System.out.println("EtatPlanAffiche : carte succes = " + carte);
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
            System.out.println("EtatPlanAffiche : carte echec = " + carte);
            //Change vers l'état Initial
            /*fenetre.retirerCartePanel();
            fenetre.retirerMenuLateral();
            fenetre.afficherEtat(NomEtat.ETAT_INITIAL);
            controleur.setEtatActuel(controleur.etatInitial);*/
        }
    }

    @Override
    public void ajoutRequete (Controleur controleur, Fenetre fenetre){
        fenetre.getMenuLateral().retirerBoutonsMenu();
        fenetre.getMenuLateral().setMessageUtilisateur("Ajouter une Etape de collecte: [Clique Gauche] sur une Adresse de la Carte " + "[Clique Droit] pour annuler");
        controleur.setEtatActuel(controleur.etatAjoutRequete1PointCollecte);
    }

}
