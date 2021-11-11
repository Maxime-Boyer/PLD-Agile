package Controleur;

import Exceptions.CommandeImpossibleException;
import Model.*;
import Vue.Fenetre;

import javax.swing.*;

public class EtatAjoutRequete4PointDepot implements Etat{

    @Override
    public void cliqueGauche (Controleur controleur, Fenetre fenetre, Carte carte, ListeDeCommandes l, Tournee tournee, Adresse depot){
        Adresse depotAPlacer = carte.recherche(depot);
        try{
            if(depotAPlacer == fenetre.getCartePanel().getNouvelleAdresse().get(0)){
                throw new CommandeImpossibleException("Erreur le point de dépôt ne peut pas être placé sur le point de collecte");
            }

            fenetre.getCartePanel().ajouterAdresseNouvelleRequete(depotAPlacer);
            controleur.setEtatActuel(controleur.etatAjoutRequete5DureeDepot);
            fenetre.afficherEtatAjoutRequete5();
        } catch (CommandeImpossibleException e) {
            //En cas d'erreur
            String messageErreur = e.getMessage();
            System.out.println("ERREUR "+e);
            controleur.setEtatActuel(controleur.etatAjoutRequete4PointDepot);
            JOptionPane.showMessageDialog(null, messageErreur);
            //Reste dans l'état actuel
        }

    }

    @Override
    public void annuler(Controleur controleur, Fenetre fenetre, Carte carte, ListeDeCommandes l, Tournee tournee) {
        //tournee.enleverChemin(collecte,carte);
        fenetre.getCartePanel().viderNouvelleRequete();
        controleur.setEtatActuel(controleur.etatTourneeOrdonnee);
        fenetre.afficherEtatTourneePreparee(tournee);
        //tournee.notifyObservers(tournee);
    }



}
