package Controleur;

import Model.Carte;
import Model.FeuilleRoute;
import Model.LecteurXML;
import Model.Requete;
import Model.Tournee;
import Vue.CartePanel;
import Vue.Fenetre;

import javax.swing.*;

public class EtatTourneeOrdonnee implements Etat {

    @Override
    public void chargerListeRequete (Controleur controleur, Fenetre fenetre, Carte carte, Tournee tournee) {
        System.out.println("EtatTourneeOrdonnee : chargerListeRequete");
        /*fenetre.retirerMenuEtape();
        fenetre.afficherEtat(NomEtat.ETAT_TOURNEE_CHARGEE);
        controleur.setEtatActuel(controleur.etatTourneeChargee);*/

        //Récupère le nom du fichier choisi
        String nomFichier = fenetre.afficherChoixFichier();
        if(!nomFichier.equals("nullnull")) {
            //Appel la méthode qui vérifie si le fichier est valide et récupère la tournee
            LecteurXML lecteur = new LecteurXML();
            try {
                System.out.println("    avant");
                tournee = lecteur.lectureRequete(nomFichier, carte, tournee);
                //fenetre.retirerMenuEtape();
                System.out.println("    après tournee = " + tournee);
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
    public void chargerPlan (Controleur controleur, Fenetre fenetre, Carte carte, Tournee tournee) {
        System.out.println("Ouvrir explorateur de fichier");
        /*fenetre.retirerCartePanel();
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
                //retirerCartePanel();
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

    @Override
    public void ajoutRequete (Controleur controleur, Fenetre fenetre){
        fenetre.afficherEtatAjoutRequete();
        controleur.setEtatActuel(controleur.etatAjoutRequete1PointCollecte);
    }

    @Override
    public void supressionRequete(Controleur controleur, Fenetre fenetre, ListeDeCommandes listeDeCommandes, Tournee tournee, Carte carte, Requete requete){
        controleur.setEtatActuel(controleur.etatSupprimerRequete);
    }

    @Override
    public void exporterFeuilleDeRoute(Tournee tournee, CartePanel cartePanel){
        cartePanel.supprimerPositionRequete();
        new FeuilleRoute(tournee, cartePanel);
    }
}
