package Controleur;

import Vue.Fenetre;

public class EtatInitial implements Etat {

    @Override
    public void chargerNouveauPlan (Controleur c, Fenetre fenetre) {
        //fenetre.allow(false);
        c.setEtatActuel(c.etatPlanAffiche);
    }

}
