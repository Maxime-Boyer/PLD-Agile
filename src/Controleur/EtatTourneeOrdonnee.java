package Controleur;

import Exceptions.NameFile;
import Vue.Fenetre;

public class EtatTourneeOrdonnee implements Etat {


    @Override
    public void chargerListeRequete (Controleur controleur, Fenetre fenetre) throws NameFile {
        System.out.println("EtatTourneeOrdonnee : chargerListeRequete");
        fenetre.retirerMenuEtape();
        fenetre.afficherEtat(NomEtat.ETAT_TOURNEE_CHARGEE);
        controleur.setEtatActuel(controleur.etatTourneeChargee);
    }

    @Override
    public void chargerPlan (Controleur controleur, Fenetre fenetre) throws NameFile {
        System.out.println("Ouvrir explorateur de fichier");
        fenetre.retirerCartePanel();
        fenetre.retirerMenuLateral();
        fenetre.afficherEtat(NomEtat.ETAT_PLAN_AFFICHE);
        controleur.setEtatActuel(controleur.etatPlanAffiche);
    }
}
