package Controleur;

public interface Etat {

    public default void chargerPlan(){};
    public default void chargerNouveauPlan(){};
    public default void chargerListeRequete(){};
    public default void chargerNouvelleListeRequete(){};
    public default void preparerTournee(){};
    public default void supressionRequete(){};
    public default void selectionPointCarte(Controleur c){};
    public default void validerSupressionRequete(Controleur c){};
    public default void exporterFeuilleDeRoute(Controleur c){};
    public default void ajoutRequete(Controleur c){};
    public default void ajoutRequetePointCollecte(Controleur c){};
    public default void ajoutRequeteOrdreCollecte(Controleur c){};
    public default void ajoutRequeteDureeCollecte(Controleur c){};
    public default void ajoutRequetePointDepot(Controleur c){};
    public default void ajoutRequeteOrdreDepot(Controleur c){};
    public default void ajoutRequeteDureeDepot(Controleur c){};

}
