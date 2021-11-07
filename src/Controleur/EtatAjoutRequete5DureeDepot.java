package Controleur;

import Model.Adresse;
import Model.Carte;
import Model.Tournee;
import Vue.Fenetre;

public class EtatAjoutRequete5DureeDepot implements Etat{

    @Override
    public void cliqueGauche (Controleur controleur, Fenetre fenetre, Carte carte, ListeDeCommandes l, Tournee tournee, Adresse precedent){
        Adresse etapePrecedentDepot = tournee.rechercheEtape(precedent);

        /*TODO : add Astar pour trouver plus court chemin entre etape précédent et point depot
        Ajouter le tracage de ce chemin sur la carte
        */

        controleur.setEtatActuel(controleur.etatAjoutRequete6PointPrecedentDepot);
    }

}
