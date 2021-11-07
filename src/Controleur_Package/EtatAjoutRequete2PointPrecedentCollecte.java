package Controleur_Package;

import Model.Adresse;
import Model.Carte;
import Model.Tournee;
import Vue.Fenetre;

public class EtatAjoutRequete2PointPrecedentCollecte implements Etat{

    @Override
    public void cliqueGauche (Controleur controleur, Fenetre fenetre, Carte carte, ListeDeCommandes l, Tournee tournee, Adresse precedent){
        Adresse etapePrecedentCollecte = tournee.rechercheEtape(precedent);

        /*TODO : add Astar pour trouver plus court chemin entre etape précédent et point collecte
        Ajouter le tracage de ce chemin sur la carte
        */

        controleur.setEtatActuel(controleur.etatAjoutRequete3PointSuivantCollecte);
    }
}
