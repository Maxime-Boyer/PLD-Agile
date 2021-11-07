package Controleur;

import Exceptions.CommandeImpossibleException;
import Model.Adresse;
import Model.Carte;
import Model.Etape;
import Model.Tournee;
import Vue.Fenetre;

public class EtatAjoutRequete2DureeCollecte implements Etat{

    @Override
    public void validerAjoutDureeEtape(Controleur controleur, Fenetre fenetre){
          Integer duree = fenetre.getPopUpSaisieDuree().getDureePopUp();
          controleur.etatAjoutRequete3PointPrecedentCollecte.mettreAjourDuree(duree);
          controleur.setEtatActuel(controleur.etatAjoutRequete3PointPrecedentCollecte);
          fenetre.afficherEtatAjoutRequete3();
    }


}
