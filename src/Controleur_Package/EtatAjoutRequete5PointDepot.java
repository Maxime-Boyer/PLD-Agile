package Controleur_Package;

import Model.Adresse;
import Model.Carte;
import Model.Tournee;
import Vue.Fenetre;

public class EtatAjoutRequete5PointDepot implements Etat{

    @Override
    public void cliqueGauche (Controleur controleur, Fenetre fenetre, Carte carte, ListeDeCommandes l, Tournee tournee, Adresse depot){
        Adresse depotAPlacer = carte.recherche(depot);
        fenetre.getCartePanel().ajouterAdresseNouvelleRequete(depotAPlacer);
        controleur.setEtatActuel(controleur.etatAjoutRequete6PointPrecedentDepot);
    }

}
