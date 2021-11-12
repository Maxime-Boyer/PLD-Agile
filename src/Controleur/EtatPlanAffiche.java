package Controleur;

import Model.Carte;
import Model.LecteurXML;
import Model.Tournee;
import Vue.Fenetre;

import javax.swing.*;

/**
 * Etat lorsqu'une carte vient d'être chargée. Permet de charger une liste de requête ou un nouveau plan.
 */
public class EtatPlanAffiche implements Etat {

    /**
     * Methode qui permet de charger un fichier XML, contenant une liste de requêtes
     * @param controleur, le controleur
     * @param fenetre la fenêtre
     * @param carte   la carte
     * @param tournee la liste des requêtes qui va être charger
     */
    @Override
    public void chargerListeRequete(Controleur controleur, Fenetre fenetre, Carte carte, Tournee tournee) {
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
                JOptionPane.showMessageDialog(null, messageErreur);
                //Reste dans l'état actuel
            }
        }
    }

    /**
     * Methode qui permet de choisir un fichier XML contenant une carte, et de charger la carte
     * @param controleur, le controleur
     * @param fenetre, la fenetre
     * @param carte, la carte
     * @param tournee, la tournee
     */
    @Override
    public void chargerPlan(Controleur controleur, Fenetre fenetre, Carte carte, Tournee tournee) {

        //Récupère le nom du fichier choisi
        String nomFichier = fenetre.afficherChoixFichier();
        if (!nomFichier.equals("nullnull")) {
            //Appel la méthode qui vérifie si le fichier est valide et récupère la carte
            LecteurXML lecteur = new LecteurXML();
            try {
                carte = lecteur.lectureCarte(nomFichier, carte);
                //Change vers l'état PlanAffiche avec la nouvelle carte
                fenetre.afficherEtatPlanAffiche(carte);
                controleur.setEtatActuel(controleur.etatPlanAffiche);
            } catch (Exception e) {
                //En cas d'erreur
                String messageErreur = e.getMessage();
                JOptionPane.showMessageDialog(null, messageErreur);
                //Change vers l'état Initial
            }
        }
    }

}
