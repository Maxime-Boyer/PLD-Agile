package Controleur_Package;

import Model.Adresse;
import Model.Carte;
import Model.Tournee;
import Vue.Fenetre;

public class EtatAjoutRequete2PointPrecedentCollecte implements Etat{

    @Override
    public void cliqueGauche (Controleur controleur, Fenetre fenetre, Carte carte, ListeDeCommandes l, Tournee tournee, Adresse precedent){
        Adresse etapePrecedente = tournee.rechercheEtape(precedent);



        controleur.setEtatActuel(controleur.etatAjoutRequete2PointPrecedentCollecte);
    }
}
