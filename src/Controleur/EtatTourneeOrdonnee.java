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

    /**
     * Methode qui permet de charger un fichier XML, contenant une liste de requêtes
     * @param controleur, le controleur
     * @param fenetre la fenêtre
     * @param carte   la carte
     * @param tournee la liste des requêtes qui va être charger
     */

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

    /**
     * Methode qui permet de choisir un fichier XML contenant une carte, et de charger la carte
     * @param controleur, le controleur
     * @param fenetre, la fenetre
     * @param carte, la carte
     * @param tournee, la tournee
     */

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

    /**
     * Methode qui permet de passer à l'état d'ajout requete
     * @param controleur le controleur
     * @param fenetre la fenêtre
     */
    @Override
    public void ajoutRequete(Controleur controleur, Fenetre fenetre) {
        fenetre.afficherEtatAjoutRequete();
        controleur.setEtatActuel(controleur.etatAjoutRequete1PointCollecte);
    }

    /** Methode qui permet de passer à l'état de supression d'une requete
     * @param controleur       le controleur
     * @param fenetre          la fenêtre
     * @param listeDeCommandes la liste des commandes pour ajouter la commande de supression
     * @param tournee          la tournée actuelle
     * @param carte            la carte
     * @param requete          la requête qui va être supprimer
     */

    @Override
    public void supressionRequete(Controleur controleur, Fenetre fenetre, ListeDeCommandes listeDeCommandes, Tournee tournee, Carte carte, Requete requete) {
        controleur.setEtatActuel(controleur.etatSupprimerRequete);
    }

    /**
     * Methode qu permet de générer la feuille de route
     * @param tournee    la tournée actuelle
     * @param cartePanel l'objet carte pannel pour la capture d'écran
     */
    @Override
    public void exporterFeuilleDeRoute(Tournee tournee, CartePanel cartePanel) {
        cartePanel.supprimerPositionRequete();
        new FeuilleRoute(tournee, cartePanel);
    }
}
