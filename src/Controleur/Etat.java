package Controleur;

import Exceptions.CommandeImpossibleException;
import Model.Adresse;
import Model.Carte;
import Model.Requete;
import Model.Tournee;
import Vue.CartePanel;
import Vue.Fenetre;

public interface Etat {

    /**
     * Méthode appelée par le controlleur après avoir cliqué sur le bouton "Importer un plan"
     * @param c : le controlleur
     * @param fenetre : la fenêtre
     */
    public default void chargerPlan(Controleur c, Fenetre exporterFeuilleDeRoute, Carte carte, Tournee tournee) {}

    public default void chargerListeRequete(Controleur c, Fenetre fenetre, Carte carte, Tournee tournee) {}

    /**
     * Méthode appelée par fenetre après avoir cliqué sur le bouton "Calculer l'itinéraire"
     * @param c le controlleur
     * @param fenetre la fenêtre
     * @param carte la carte
     * @param tournee la liste des requêtes qui vas permettred de calculer une tournee
     */
    public default void preparerTournee(Controleur c, Fenetre fenetre, Carte carte, Tournee tournee,ListeDeCommandes l) {}
    public default void supressionRequete(Controleur c, Fenetre fenetre,ListeDeCommandes listeDeCommandes,Tournee tournee, Carte carte, Requete requete){}
    public default void selectionPointCarte(Controleur c, Fenetre fenetre){}
    public default void validerSupressionRequete(Controleur c, Fenetre fenetre){}
    public default void chargerNouveauPlan(Controleur c, Fenetre fenetre){}
    public default void chargerNouvelleListeRequete(Controleur c, Fenetre fenetre){}
    public default void exporterFeuilleDeRoute(Tournee tournee, CartePanel cartePanel){}
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
    public default void validerAjoutDureeEtape(Controleur c, Fenetre fenetre){}
    public default void cliqueGauche (Controleur c, Fenetre fenetre, Carte carte, ListeDeCommandes l, Tournee tournee, Adresse a) {}
    public default void cliqueDroit (Controleur c, Fenetre fenetre, Carte carte, ListeDeCommandes l, Tournee tournee){}
}
