package Controleur;

import Model.Adresse;
import Model.Carte;
import Model.Tournee;
import Vue.Fenetre;

public class EtatAjoutRequete5DureeDepot implements Etat{

    @Override
    public void validerAjoutDureeEtape(Controleur controleur, Fenetre fenetre){
        Integer duree = fenetre.getPopUpSaisieDuree().getDureePopUp();
        controleur.etatAjoutRequete6PointPrecedentDepot.mettreAjourDuree(duree);
        controleur.setEtatActuel(controleur.etatAjoutRequete6PointPrecedentDepot);
        fenetre.afficherEtatAjoutRequete6();
    }

}
