package Controleur;

import Vue.Fenetre;

public class EtatPlanAffiche implements Etat {
    @Override
    public void chargerListeRequete (Controleur controleur, Fenetre fenetre) {
        //fenetre.allow(false);
        System.out.println("Charger la liste de requêtes");
        fenetre.afficherEtat(NomEtat.ETAT_TOURNEE_CHARGEE);
        controleur.setEtatActuel(controleur.etatTourneeChargee);
    }

}
