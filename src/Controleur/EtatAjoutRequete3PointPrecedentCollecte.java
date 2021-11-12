package Controleur;

import Model.Adresse;
import Model.Carte;
import Model.Etape;
import Model.Tournee;
import Vue.Fenetre;

/**
 * Troisième état de l'ajout de requêtes, permet de choisir l'étape qui précédent l'étape ajoutée
 */
public class EtatAjoutRequete3PointPrecedentCollecte implements Etat {
    private Integer dureeEtape;

    /**
     * Méthode qui se lance au clique gauche sur la carte, de l'utilisateur
     * @param controleur controleur qui permet de changer l'état actuel
     * @param fenetre contient l'affichage de l'état suivant
     * @param carte   la carte
     * @param l       la liste des commandes, contient la liste des commandes qui ont été executé
     * @param tournee la tournée, tournée à laquelle la requête doit être ajouter
     * @param precedent l'étape qui précéde la collecte qui vient d'être ajouter
     */
    @Override
    public void cliqueGauche(Controleur controleur, Fenetre fenetre, Carte carte, ListeDeCommandes l, Tournee tournee, Adresse precedent) {
        //On recherche l'étape de la tournée qui va etre le precedent de notre nouvelle collecte le plus proche de notre clique gauche
        Adresse etapePrecedentCollecte = tournee.rechercheEtape(precedent);
        if (etapePrecedentCollecte != null) {
            //On recupere l'étape de l'adresse précedente trouvée à partir de son id
            Etape etapePrecColl = tournee.obtenirEtapeParId(etapePrecedentCollecte.getIdAdresse());
            //On transmet l'étape précedent la collecte à l'état 6 qui se chargera de l'ajout de la requete à la liste des requetes ainsi que le calcul et ajout des nouveaux chemins
            controleur.etatAjoutRequete6PointPrecedentDepot.mettreAJourPrecedentCollecte(etapePrecColl);
            //On va à l'état 4 de l'ajout de requete
            controleur.etatAjoutRequete6PointPrecedentDepot.mettreAJourDureeCollecte(dureeEtape);
            Adresse nouvelleAdresseCollecte = fenetre.getCartePanel().getNouvelleAdresse().get(0);
            Etape collecte = new Etape(nouvelleAdresseCollecte.getLatitude(), nouvelleAdresseCollecte.getLongitude(), nouvelleAdresseCollecte.getIdAdresse(), dureeEtape);
            controleur.setEtatActuel(controleur.etatAjoutRequete4PointDepot);
            //Met à jour l'affichage de la fenetre, le message utilisateur
            fenetre.afficherEtatAjoutRequete4();
        }
    }

    /**
     * Méthode qui permet de revenir à l'état tournée ordonnée, en annulant tout ce qui a été fait jusque là
     * @param controleur le controleur
     * @param fenetre contient l'affichage de l'état tournée ordonnée
     * @param carte   la carte
     * @param l       la liste des commandes, contient la liste des commandes qui ont été executé
     * @param tournee la tournée, tournée à laquelle la requête doit être ajouter
     */

    @Override
    public void annuler(Controleur controleur, Fenetre fenetre, Carte carte, ListeDeCommandes l, Tournee tournee) {
        //On vide la liste de CartePanel contenant le point de collecet temporaire
        fenetre.getCartePanel().viderNouvelleRequete();
        //On revient à l'état tournée ordonnée et on met à jour l'affichage
        controleur.setEtatActuel(controleur.etatTourneeOrdonnee);
        fenetre.afficherEtatTourneePreparee(tournee);
    }

    /**
     * Methode qui permet de mettre à jour la duree de la collecte
     * @param dureeEtape, duree en secondes de la collecte
     */
    public void mettreAjourDuree(Integer dureeEtape) {
        //On recupere la valeur de durée rentrée à l'état 2
        this.dureeEtape = dureeEtape;
    }
}
