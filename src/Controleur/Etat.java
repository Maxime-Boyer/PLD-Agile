package Controleur;

public interface Etat {

    public default void chargerPlan(Controleur c, Fenetre fenetre){};
    public default void chargerNouveauPlan(Controleur c, Fenetre fenetre){};
    public default void chargerListeRequete(Controleur c, Fenetre fenetre){};
    public default void chargerNouvelleListeRequete(Controleur c, Fenetre fenetre){};
    public default void preparerTournee(Controleur c, Fenetre fenetre){};
    public default void supressionRequete(Controleur c, Fenetre fenetre){};
    public default void selectionPointCarte(Controleur c, Fenetre fenetre){};
    public default void validerSupressionRequete(Controleur c, Fenetre fenetre){};
    public default void exporterFeuilleDeRoute(Controleur c, Fenetre fenetre){};
    public default void ajoutRequete(Controleur c, Fenetre fenetre){};
    public default void ajoutRequetePointCollecte(Controleur c, Fenetre fenetre){};
    public default void ajoutRequeteOrdreCollecte(Controleur c, Fenetre fenetre){};
    public default void ajoutRequeteDureeCollecte(Controleur c, Fenetre fenetre){};
    public default void ajoutRequetePointDepot(Controleur c, Fenetre fenetre){};
    public default void ajoutRequeteOrdreDepot(Controleur c, Fenetre fenetre){};
    public default void ajoutRequeteDureeDepot(Controleur c, Fenetre fenetre){};

}
