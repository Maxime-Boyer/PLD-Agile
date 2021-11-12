package Controleur;

import Model.Carte;
import Model.LecteurXML;
import Model.Tournee;
import Vue.Fenetre;

import javax.swing.*;

/**
 * Le premier état en ouvrant l'application. Il permet exclusivement de charger un nouveau plan.
 */
public class EtatInitial implements Etat {
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
                // Carte carte = new Carte();
                carte = lecteur.lectureCarte(nomFichier, carte);
                //Change vers l'état PlanAffiche
                fenetre.retirerEcranAccueil();
                fenetre.afficherEtatPlanAffiche(carte);
                controleur.setEtatActuel(controleur.etatPlanAffiche);
            } catch (Exception e) {
                //En cas d'erreur
                String messageErreur = e.getMessage();
                JOptionPane.showMessageDialog(null, messageErreur);
                //Reste sur l'état initial
            }
        }
    }

}
