package Controleur;

import Vue.Fenetre;

public class EtatInitial implements Etat {

    @Override
    public void chargerPlan (Controleur controleur, Fenetre fenetre) {
        System.out.println("Ouvrir explorateur de fichier");
        fenetre.retirerElment(NomEtat.ETAT_INITIAL);
        fenetre.afficherEtat(NomEtat.ETAT_PLAN_AFFICHE);
        controleur.setEtatActuel(controleur.etatPlanAffiche);
    }

}
