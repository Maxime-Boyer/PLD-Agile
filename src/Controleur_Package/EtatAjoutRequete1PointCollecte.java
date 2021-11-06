package Controleur_Package;

import Model.Adresse;
import Model.Carte;
import Model.Tournee;
import Vue.Fenetre;

public class EtatAjoutRequete1PointCollecte implements Etat {

    @Override
    public void cliqueGauche (Controleur c, Fenetre fenetre, Carte carte, ListeDeCommandes l, Tournee tournee, Adresse a){
        fenetre.getMenuLateral().setMessageUtilisateur("ZEBI");
    }

}
