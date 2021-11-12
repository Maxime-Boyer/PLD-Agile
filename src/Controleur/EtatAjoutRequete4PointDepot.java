package Controleur;

import Exceptions.CommandeImpossibleException;
import Model.*;
import Vue.Fenetre;

import javax.swing.*;

/**
 * Quatrièmme état de l'ajout de requêtes, permet de choisir une nouvelle étape de dépôt
 */
public class EtatAjoutRequete4PointDepot implements Etat{


    @Override
    public void cliqueGauche (Controleur controleur, Fenetre fenetre, Carte carte, ListeDeCommandes l, Tournee tournee, Adresse depot){
        Adresse depotAPlacer = carte.recherche(depot);
        try{
            if(depotAPlacer == fenetre.getCartePanel().getNouvelleAdresse().get(0)){
                throw new CommandeImpossibleException("Erreur le point de dépôt ne peut pas être placé sur le point de collecte");
            }

            //On ajoute la nouvelle étape dépot dans la liste de CartePanel de l'affichage temporaire en rouge
            fenetre.getCartePanel().ajouterAdresseNouvelleRequete(depotAPlacer);
            //On va à l'état 5
            controleur.setEtatActuel(controleur.etatAjoutRequete5DureeDepot);
            //On met à jour l'affichage et le message utilisateur
            fenetre.afficherEtatAjoutRequete5();
        } catch (CommandeImpossibleException e) {
            //En cas d'erreur
            String messageErreur = e.getMessage();
            System.out.println("ERREUR "+e);
            //On reste dans l'état actuel
            controleur.setEtatActuel(controleur.etatAjoutRequete4PointDepot);
            JOptionPane.showMessageDialog(null, messageErreur);
        }

    }


    @Override
    public void annuler(Controleur controleur, Fenetre fenetre, Carte carte, ListeDeCommandes l, Tournee tournee) {
        //On vide la liste de CartePanel pour ne plus afficher l'étape de collecte et dépot placé temporairement
        fenetre.getCartePanel().viderNouvelleRequete();
        //On revient à l'état tournée ordonnée et on change l'affichage en conséquence
        controleur.setEtatActuel(controleur.etatTourneeOrdonnee);
        fenetre.afficherEtatTourneePreparee(tournee);
    }



}
