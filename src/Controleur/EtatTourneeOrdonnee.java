package Controleur;

import Model.*;
import Vue.CartePanel;
import Vue.Fenetre;

import javax.swing.*;

/**
 * Etat dans lequel la tournée est calculé et ordonnée.
 * Permet d'ajouter et supprimer des requête et d'annuler / refaire des actions.
 * Permet également de charger une nouvelle liste de requête ou un nouveau plan.
 */
public class EtatTourneeOrdonnee implements Etat {

    @Override
    public void chargerListeRequete(Controleur controleur, Fenetre fenetre, Carte carte, Tournee tournee) {
        System.out.println("EtatTourneeOrdonnee : chargerListeRequete");

        //Récupère le nom du fichier choisi
        String nomFichier = fenetre.afficherChoixFichier();
        if (!nomFichier.equals("nullnull")) {
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
        System.out.println("Ouvrir explorateur de fichier");

        //Récupère le nom du fichier choisi
        String nomFichier = fenetre.afficherChoixFichier();
        if (!nomFichier.equals("nullnull")) {
            //Appel la méthode qui vérifie si le fichier est valide et récupère la carte
            LecteurXML lecteur = new LecteurXML();
            try {
                carte = lecteur.lectureCarte(nomFichier, carte);
                //Change vers l'état PlanAffiche avec la nouvelle carte
                tournee.reset();
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
    public void ajoutRequete(Controleur controleur, Fenetre fenetre) {
        fenetre.afficherEtatAjoutRequete();
        controleur.setEtatActuel(controleur.etatAjoutRequete1PointCollecte);
    }

    @Override
    public void supressionRequete(Controleur controleur, Fenetre fenetre, ListeDeCommandes listeDeCommandes, Tournee tournee, Carte carte, Requete requete) {
        controleur.setEtatActuel(controleur.etatSupprimerRequete);
    }

    @Override
    public void exporterFeuilleDeRoute(Tournee tournee, CartePanel cartePanel) {
        cartePanel.supprimerPositionRequete();
        new FeuilleRoute(tournee, cartePanel);
    }

    @Override
    public void afficherIndiquerPositionRequete(Fenetre fenetre, Etape collecte, Etape depot) {
        fenetre.indiquerPositionRequete(collecte, depot);
    }

    @Override
    public void supprimerPositionRequete(Fenetre fenetre){
        fenetre.supprimerPositionRequete();
    }
}
