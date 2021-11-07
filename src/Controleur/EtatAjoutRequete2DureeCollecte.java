package Controleur;

import Exceptions.CommandeImpossibleException;
import Model.Adresse;
import Model.Carte;
import Model.Etape;
import Model.Tournee;
import Vue.Fenetre;

public class EtatAjoutRequete2DureeCollecte implements Etat{
private Integer duree;
    @Override
    public void cliqueGauche (Controleur controleur, Fenetre fenetre, Carte carte, ListeDeCommandes l, Tournee tournee, Adresse precedent){
        controleur.etatAjoutRequete3PointPrecedentCollecte.mettreAjourDuree(duree);
        controleur.setEtatActuel(controleur.etatAjoutRequete3PointPrecedentCollecte);
        fenetre.afficherEtatAjoutRequete3();
    }

    public void validerAjoutDureeEtape(Controleur controleur, Fenetre fenetre){
          duree = fenetre.getPopUpSaisieDuree().getDureePopUp();
    }


}
