package Controleur;

import Vue.Fenetre;

public class EtatTourneeChargee implements Etat {
    @Override
    public void preparerTournee (Controleur controleur, Fenetre fenetre) {
        fenetre.afficherEtat(NomEtat.ETAT_TOURNEE_PREPAREE);
        //controleur.setEtatActuel(controleur.etatTourneeChargee);
    }
}
