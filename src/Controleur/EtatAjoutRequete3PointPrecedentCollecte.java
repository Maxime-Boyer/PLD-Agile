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

    /** Méthode qui se lance au clique gauche sur la carte, de l'utilisateur
     * @param controleur controleur qui permet de changer l'état actuel
     * @param fenetre contient l'affichage de l'état suivant
     * @param carte   la carte
     * @param l       la liste des commandes, contient la liste des commandes qui ont été executé
     * @param tournee la tournée, tournée à laquelle la requête doit être ajouter
     * @param precedent l'étape qui précéde la collecte qui vient d'être ajouter
     */
    @Override
    public void cliqueGauche(Controleur controleur, Fenetre fenetre, Carte carte, ListeDeCommandes l, Tournee tournee, Adresse precedent) {
        Adresse etapePrecedentCollecte = tournee.rechercheEtape(precedent, null);
        if (etapePrecedentCollecte != null) {
            Etape etapePrecColl = tournee.obtenirEtapeParId(etapePrecedentCollecte.getIdAdresse());
            controleur.etatAjoutRequete6PointPrecedentDepot.mettreAJourPrecedentCollecte(etapePrecColl);
            controleur.etatAjoutRequete6PointPrecedentDepot.mettreAJourDureeCollecte(dureeEtape);
            Adresse nouvelleAdresseCollecte = fenetre.getCartePanel().getNouvelleAdresse().get(0);
            Etape collecte = new Etape(nouvelleAdresseCollecte.getLatitude(), nouvelleAdresseCollecte.getLongitude(), nouvelleAdresseCollecte.getIdAdresse(), dureeEtape);
            controleur.setEtatActuel(controleur.etatAjoutRequete4PointDepot);
            fenetre.afficherEtatAjoutRequete4();
        }
    }

    /** Méthode qui permet de revenir à l'état tournée ordonnée, en annulant tout ce qui a été fait jusque là
     * @param controleur le controleur
     * @param fenetre contient l'affichage de l'état tournée ordonnée
     * @param carte   la carte
     * @param l       la liste des commandes, contient la liste des commandes qui ont été executé
     * @param tournee la tournée, tournée à laquelle la requête doit être ajouter
     */

    @Override
    public void annuler(Controleur controleur, Fenetre fenetre, Carte carte, ListeDeCommandes l, Tournee tournee) {
        fenetre.getCartePanel().viderNouvelleRequete();
        controleur.setEtatActuel(controleur.etatTourneeOrdonnee);
        fenetre.afficherEtatTourneePreparee(tournee);
    }

    /** Methode qui permet de mettre à jour la duree de la collecte
     * @param dureeEtape, duree en secondes de la collecte
     */
    public void mettreAjourDuree(Integer dureeEtape) {
        this.dureeEtape = dureeEtape;
    }
}
