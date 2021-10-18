package Controleur;

import Vue.Fenetre;

public class EtatInitial implements Etat {

    @Override
    public void chargerNouveauPlan (Controleur c, Fenetre fenetre) {
        window.allow(false);
        c.setEtatActuel(c.etatPlanAffiche);
    }

}
