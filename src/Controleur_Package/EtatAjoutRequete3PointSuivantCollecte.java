package Controleur_Package;

import Model.Adresse;
import Model.Carte;
import Model.Tournee;
import Vue.Fenetre;

public class EtatAjoutRequete3PointSuivantCollecte implements Etat{

    @Override
    public void cliqueGauche (Controleur controleur, Fenetre fenetre, Carte carte, ListeDeCommandes l, Tournee tournee, Adresse suivante){
        Adresse etapeSuivantCollecte = tournee.rechercheEtape(suivante);

        //TODO : add Astar pour trouver plus court chemin entre etape suivante et point collecte

        controleur.setEtatActuel(controleur.etatAjoutRequete4DureeCollecte);
    }

}
