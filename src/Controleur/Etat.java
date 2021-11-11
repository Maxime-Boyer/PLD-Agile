package Controleur;

import Model.Adresse;
import Model.Carte;
import Model.Requete;
import Model.Tournee;
import Vue.CartePanel;
import Vue.Fenetre;

public interface Etat {

    /**
     * Méthode appelée par le controlleur après avoir cliqué sur le bouton "Importer un plan"
     *
     * @param c le controlleur
     */
    public default void chargerPlan(Controleur c, Fenetre exporterFeuilleDeRoute, Carte carte, Tournee tournee) {
    }

    /**
     * Méthode appelée par fenetre après avoir cliqué sur le bouton "Importer tournée"
     *
     * @param c       le controlleur
     * @param fenetre la fenêtre
     * @param carte   la carte
     * @param tournee la liste des requêtes qui vas permettred de calculer une tournee
     */
    public default void chargerListeRequete(Controleur c, Fenetre fenetre, Carte carte, Tournee tournee) {
    }

    /**
     * Méthode appelée par fenetre après avoir cliqué sur le bouton "Calculer l'itinéraire"
     *
     * @param c       le controlleur
     * @param fenetre la fenêtre
     * @param carte   la carte
     * @param tournee la liste des requêtes qui vas permettred de calculer une tournee
     */
    public default void preparerTournee(Controleur c, Fenetre fenetre, Carte carte, Tournee tournee, ListeDeCommandes l) {
    }

    /**
     * Méthode appelée par fenetre après avoir cliqué sur le bouton de suppression des requêtes
     *
     * @param c                le controlleur
     * @param fenetre          la fenêtre
     * @param listeDeCommandes la liste des commandes pour ajouter la suppression
     * @param tournee          la tournée actuelle
     * @param carte            la carte
     * @param requete          la requête qui vas être supprimé
     */
    public default void supressionRequete(Controleur c, Fenetre fenetre, ListeDeCommandes listeDeCommandes, Tournee tournee, Carte carte, Requete requete) {
    }

    /**
     * Méthode appelée par fenetre après avoir cliqué sur le bouton "Exporter feuille de route"
     *
     * @param tournee    la tournée actuelle
     * @param cartePanel l'objet carte pannel pour la capture d'écran
     */
    public default void exporterFeuilleDeRoute(Tournee tournee, CartePanel cartePanel) {
    }

    /**
     * Méthode appelée par fenetre après avoir cliqué sur le bouton "Ajouter requête"
     *
     * @param c       le controlleur
     * @param fenetre la fenêtre
     */
    public default void ajoutRequete(Controleur c, Fenetre fenetre) {
    }

    /**
     * Méthode appelée par fenetre après avoir valider la durée d'ajout d'étape
     *
     * @param c       le controlleur
     * @param fenetre la fenetre
     */
    public default void validerAjoutDureeEtape(Controleur c, Fenetre fenetre) {
    }

    /**
     * Méthode appelée par fenetre après avoir cliqué sur le bouton gauche de la souris
     *
     * @param c       le controlleur
     * @param fenetre la fenetre
     * @param carte   la carte
     * @param l       la liste des commandes
     * @param tournee la tournée
     * @param a       l'adresse
     */
    public default void cliqueGauche(Controleur c, Fenetre fenetre, Carte carte, ListeDeCommandes l, Tournee tournee, Adresse a) {
    }

    /**
     * Méthode appelée par fenetre après avoir cliqué sur le bouton "Annuler"
     *
     * @param c       le controlleur
     * @param fenetre la fenetre
     * @param carte   la carte
     * @param l       la liste des commandes
     * @param tournee la tournée
     */
    public default void cliqueDroit(Controleur c, Fenetre fenetre, Carte carte, ListeDeCommandes l, Tournee tournee) {
    }
}
