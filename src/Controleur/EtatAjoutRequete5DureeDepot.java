package Controleur;

import Model.Etape;
import Model.Carte;
import Model.Requete;
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

    @Override
    public void cliqueDroit(Controleur controleur , Fenetre fenetre, Carte carte, ListeDeCommandes l, Tournee tournee) {
        //tournee.enleverChemin(collecte,carte);
        fenetre.getCartePanel().viderNouvelleRequete();
        controleur.setEtatActuel(controleur.etatTourneeOrdonnee);
        fenetre.getCartePanel().remove(fenetre.getPopUpSaisieDuree());
        fenetre.afficherEtatTourneePreparee(tournee);
        //tournee.notifyObservers(tournee);
    }


}
