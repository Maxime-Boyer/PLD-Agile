package Controleur;

import Model.*;
import Vue.Fenetre;

public class EtatAjoutRequete4PointDepot implements Etat{

    @Override
    public void cliqueGauche (Controleur controleur, Fenetre fenetre, Carte carte, ListeDeCommandes l, Tournee tournee, Adresse depot){
        Adresse depotAPlacer = carte.recherche(depot);
        int positionDepotX = fenetre.getCartePanel().valeurX(depotAPlacer.getLongitude());
        int positionDepotY = fenetre.getCartePanel().valeurX(depotAPlacer.getLatitude());

        fenetre.getCartePanel().ajouterAdresseNouvelleRequete(depotAPlacer);
        controleur.setEtatActuel(controleur.etatAjoutRequete5DureeDepot);
        fenetre.afficherEtatAjoutRequete5(positionDepotX,positionDepotY);
    }

    @Override
    public void cliqueDroit(Controleur controleur, Fenetre fenetre, Carte carte, ListeDeCommandes l, Tournee tournee) {
        //tournee.enleverChemin(collecte,carte);
        fenetre.getCartePanel().viderNouvelleRequete();
        controleur.setEtatActuel(controleur.etatTourneeOrdonnee);
        fenetre.afficherEtatTourneePreparee(tournee);
        //tournee.notifyObservers(tournee);
    }



}
