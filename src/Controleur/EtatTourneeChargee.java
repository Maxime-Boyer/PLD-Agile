package Controleur;

import Algorithmie.CalculateurTournee;
import Vue.Fenetre;

public class EtatTourneeChargee implements Etat {

    @Override
    public void preparerTournee (Controleur controleur, Fenetre fenetre) {
        fenetre.afficherEtat(NomEtat.ETAT_TOURNEE_PREPAREE);

        //Algo
        //CalculateurTournee calculateurTournee = new CalculateurTournee(carte, tournee);
        //= calculateurTournee.calculerTournee();
        //Fin Algo

        controleur.setEtatActuel(controleur.etatTourneeOrdonnee);
    }

    @Override
    public void chargerListeRequete (Controleur controleur, Fenetre fenetre) {
        fenetre.afficherEtat(NomEtat.ETAT_TOURNEE_CHARGEE);
        controleur.setEtatActuel(controleur.etatTourneeChargee);
    }

    @Override
    public void chargerPlan (Controleur controleur, Fenetre fenetre) {
        System.out.println("Ouvrir explorateur de fichier");
        fenetre.retirerElment(NomEtat.ETAT_TOURNEE_CHARGEE);
        fenetre.afficherEtat(NomEtat.ETAT_PLAN_AFFICHE);
        controleur.setEtatActuel(controleur.etatPlanAffiche);
    }
}
