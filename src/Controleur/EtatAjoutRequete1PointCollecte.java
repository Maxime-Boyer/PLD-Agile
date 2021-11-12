package Controleur;

import Model.Adresse;
import Model.Carte;
import Model.Tournee;
import Vue.Fenetre;

/**
 * Première état de l'ajout de requêtes, permet de choisir une nouvelle étape de collecte
 */
public class EtatAjoutRequete1PointCollecte implements Etat {
    /**
     * Méthode qui se lance au clique gauche sur la carte, de l'utilisateur, place la collecte
     * @param controleur controleur qui permet de changer l'état actuel
     * @param fenetre contient l'affichage de l'état suivant
     * @param carte   la carte
     * @param l       la liste des commandes, contient la liste des commandes qui ont été executé
     * @param tournee la tournée, tournée à laquelle la requête doit être ajouter
     * @param collecte l'étape qui doit être àjouter
     */
    @Override
    public void cliqueGauche (Controleur controleur, Fenetre fenetre, Carte carte, ListeDeCommandes l, Tournee tournee, Adresse collecte){

        //On recherche l'étape de la tournée la plus proche du clique gauche sur la carte
        Adresse collecteAPlacer = carte.recherche(collecte);
        //On ajoute cette adresse (correspondant à une étape de la tournée) dans une liste de CartePanel pour afficher temporairement la collecte à placer en rouge
        fenetre.getCartePanel().ajouterAdresseNouvelleRequete(collecteAPlacer);
        //On va ensuite dans l'état 2 de l'ajout de requete
        controleur.setEtatActuel(controleur.etatAjoutRequete2DureeCollecte);
        //On change le message utilisateur pour l'état d'apres et on peint le point de collecte en rouge
        fenetre.afficherEtatAjoutRequete2();
    }

    @Override
    public void annuler(Controleur controleur, Fenetre fenetre, Carte carte, ListeDeCommandes l, Tournee tournee){
        //On revient à l'état ordonné de la tournée
        controleur.setEtatActuel(controleur.etatTourneeOrdonnee);
        //On change l'affichage à l'écran pour revenir à celui d'état de tournée ordonnéee
        fenetre.afficherEtatTourneePreparee(tournee);
    }

}
