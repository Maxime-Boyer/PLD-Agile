package Controleur;

import Vue.Fenetre;

public class EtatPlanAffiche implements Etat {
    @Override
    public void chargerListeRequete (Controleur controleur, Fenetre fenetre) {
        System.out.println("EtatPlanAffiche : chargerListeRequete");
        fenetre.afficherEtat(NomEtat.ETAT_TOURNEE_CHARGEE);
        controleur.setEtatActuel(controleur.etatTourneeChargee);
    }

    @Override
    public void chargerPlan (Controleur controleur, Fenetre fenetre) {
        System.out.println("EtatPlanAffiche : chargerPlan");
        fenetre.retirerCartePanel();
        fenetre.retirerMenuLateral();
        fenetre.afficherEtat(NomEtat.ETAT_PLAN_AFFICHE);
        controleur.setEtatActuel(controleur.etatPlanAffiche);
    }

}
