package Controleur;

import Model.*;
import Vue.CartePanel;
import Vue.Fenetre;

/**
 * Permet de mettre en place le design pattern Etat. Chaque état permet d'accéder à un ou plusieurs autres états avec des affichages différents.
 */
public interface Etat {

    /**
     * Méthode appelée par le controlleur après avoir cliqué sur le bouton "Importer un plan"
     * @param c le controleur
     */
    public default void chargerPlan(Controleur c, Fenetre exporterFeuilleDeRoute, Carte carte, Tournee tournee) {
    }

    /**
     * Méthode appelée par fenetre après avoir cliqué sur le bouton "Importer tournée"
     *
     * @param c       le controlleur
     * @param fenetre la fenêtre
     * @param carte   la carte
     * @param tournee la tournee actuelle
     */
    public default void chargerListeRequete(Controleur c, Fenetre fenetre, Carte carte, Tournee tournee) {
    }

    /**
     * Methode appelée par fenetre après avoir cliqué sur le bouton "Calculer l'itinéraire"
     * @param c       le controleur
     * @param fenetre la fenêtre
     * @param carte   la carte
     * @param tournee la tournee actuelle
     */
    public default void preparerTournee(Controleur c, Fenetre fenetre, Carte carte, Tournee tournee, ListeDeCommandes l) {
    }

    /**
     * Methode appelée par fenetre après avoir cliqué sur le bouton de suppression des requêtes
     * @param c                le controleur
     * @param fenetre          la fenêtre
     * @param listeDeCommandes la liste des commandes pour ajouter la commande de supression
     * @param tournee          la tournée actuelle
     * @param carte            la carte
     * @param requete          la requête qui va être supprimé
     */
    public default void supressionRequete(Controleur c, Fenetre fenetre, ListeDeCommandes listeDeCommandes, Tournee tournee, Carte carte, Requete requete) {
    }

    /**
     * Methode appelée par fenetre après avoir cliqué sur le bouton "Exporter feuille de route"
     * @param tournee    la tournée actuelle
     * @param cartePanel l'objet carte pannel pour la capture d'écran
     */
    public default void exporterFeuilleDeRoute(Tournee tournee, CartePanel cartePanel) {
    }

    /**
     * Methode appelée par fenetre après avoir cliqué sur le bouton "Ajouter requête"
     * @param c       le controleur
     * @param fenetre la fenêtre
     */
    public default void ajoutRequete(Controleur c, Fenetre fenetre) {
    }

    /**
     * Methode appelée par fenetre après avoir valider la durée d'ajout d'étape
     * @param c       le controlleur
     * @param fenetre la fenetre
     */
    public default void validerAjoutDureeEtape(Controleur c, Fenetre fenetre) {
    }

    /**
     * Methode appelée par fenetre après avoir cliqué sur le bouton gauche de la souris
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
     * Methode appelée par fenetre après avoir cliqué sur le bouton "Annuler" lors de l'ajout d'une requête
     * @param c       le controleur
     * @param fenetre la fenetre
     * @param carte   la carte
     * @param l       la liste des commandes
     * @param tournee la tournée
     */
    public default void annuler(Controleur c, Fenetre fenetre, Carte carte, ListeDeCommandes l, Tournee tournee) {
    }

    /**
     * Methode appelée par fenetre après avoir cliqué sur la fenêtre pour sélectionner une requête
     * @param fenetre la fenetre
     * @param collecte le point de collecte de la requête selectionnée
     * @param depot le point de dépot de la requête selectionnée
     */
    public default void afficherIndiquerPositionRequete(Fenetre fenetre, Etape collecte, Etape depot) {
    }

    /**
     * Methode appelée par la fenetre après avoir cliquer sur la position d'une autre requete pour supprimer la position de celle précédemment séléctionnée
     * @param fenetre
     */

    public default void  supprimerPositionRequete(Fenetre fenetre){
    }
}
