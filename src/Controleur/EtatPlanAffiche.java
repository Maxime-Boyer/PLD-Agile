package Controleur;

import Vue.Fenetre;

public class EtatPlanAffiche implements Etat {
    @Override
    public void chargerListeRequete (Controleur controleur, Fenetre fenetre) {
        fenetre.afficherEtat(NomEtat.ETAT_TOURNEE_CHARGEE);
        controleur.setEtatActuel(controleur.etatTourneeChargee);
    }

    @Override
    public void chargerPlan (Controleur controleur, Fenetre fenetre) {
        System.out.println("Ouvrir explorateur de fichier");
        fenetre.retirerElment(NomEtat.ETAT_PLAN_AFFICHE);
        fenetre.afficherEtat(NomEtat.ETAT_PLAN_AFFICHE);
        controleur.setEtatActuel(controleur.etatPlanAffiche);
    }

}
