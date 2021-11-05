package Controleur_Package;

import Model.Carte;
import Vue.Fenetre;

public interface Etat {

    /**
     * Méthode appelé par le controlleur après avoir cliqué sur le bouton "Importer un plan"
     * @param c : le controlleur
     * @param fenetre : la fenêtre
     */
    public default void chargerPlan(Controleur c, Fenetre fenetre, Carte carte) {}

    public default void choixFichierCarte (Controleur c, Fenetre fenetre) {}

    /**
     * Méthode appelé par le controlleur après avoir cliqué sur le bouton "Importer tournée"
     * @param c : le controlleur
     * @param fenetre : la fenêtre
     */
    public default void chargerNouveauPlan(Controleur c, Fenetre fenetre){}
    public default void chargerListeRequete(Controleur c, Fenetre fenetre, Carte carte) {}
    public default void chargerNouvelleListeRequete(Controleur c, Fenetre fenetre){}
    public default void preparerTournee(Controleur c, Fenetre fenetre) {}
    public default void supressionRequete(Controleur c, Fenetre fenetre){}
    public default void selectionPointCarte(Controleur c, Fenetre fenetre){}
    public default void validerSupressionRequete(Controleur c, Fenetre fenetre){}
    public default void exporterFeuilleDeRoute(Controleur c, Fenetre fenetre){}
    public default void ajoutRequete(Controleur c, Fenetre fenetre){}
    public default void ajoutRequetePointCollecte(Controleur c, Fenetre fenetre){}
    public default void validerAjoutRequetePointCollecte(Controleur c, Fenetre fenetre){}
    public default void ajoutRequeteOrdreCollecte(Controleur c, Fenetre fenetre){}
    public default void ajoutRequeteDureeCollecte(Controleur c, Fenetre fenetre){}
    public default void ajoutRequetePointDepot(Controleur c, Fenetre fenetre){}
    public default void validerAjoutRequetePointDepot(Controleur c, Fenetre fenetre){}
    public default void ajoutRequeteOrdreDepot(Controleur c, Fenetre fenetre){}
    public default void ajoutRequeteDureeDepot(Controleur c, Fenetre fenetre){}
    public default void ajouterRequete(Controleur c, Fenetre fenetre){}
}