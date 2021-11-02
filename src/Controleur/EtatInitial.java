package Controleur;

import Exceptions.NameFile;
import Vue.Fenetre;

public class EtatInitial implements Etat {

    @Override
    public void chargerPlan (Controleur controleur, Fenetre fenetre) throws NameFile {
        System.out.println("EtatInitial : chargerPlan");
        fenetre.retirerEcranAccueil();
         fenetre.afficherEtat(NomEtat.ETAT_PLAN_AFFICHE);
        controleur.setEtatActuel(controleur.etatPlanAffiche);
    }

}
