package Controleur;

import Model.Adresse;
import Model.Carte;
import Model.Tournee;
import Vue.Fenetre;

public class EtatAjoutRequete1PointCollecte implements Etat {

    @Override
    public void cliqueGauche (Controleur controleur, Fenetre fenetre, Carte carte, ListeDeCommandes l, Tournee tournee, Adresse collecte){
        Adresse collecteAPlacer = carte.recherche(collecte);
        fenetre.getCartePanel().ajouterAdresseNouvelleRequete(collecteAPlacer);
        controleur.setEtatActuel(controleur.etatAjoutRequete2DureeCollecte);
        int positionCollecteX = fenetre.getCartePanel().valeurX(collecteAPlacer.getLongitude());
        int positionCollecteY = fenetre.getCartePanel().valeurX(collecteAPlacer.getLatitude());
        fenetre.afficherEtatAjoutRequete2(positionCollecteX,positionCollecteY);
    }

    @Override
    public void cliqueDroit (Controleur controleur, Fenetre fenetre, Carte carte, ListeDeCommandes l, Tournee tournee){
        controleur.setEtatActuel(controleur.etatTourneeOrdonnee);
        fenetre.afficherEtatTourneePreparee(tournee);
    }

}
