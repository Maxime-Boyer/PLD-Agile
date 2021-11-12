package Controleur;

import Exceptions.CommandeImpossibleException;
import Model.*;
import Vue.Fenetre;

import javax.swing.*;

/**
 * Sixième et dernier état de l'ajout de requêtes, permet de choisir l'étape précédent l'étape ajoutée et valide la création de la requête
 */
public class EtatAjoutRequete6PointPrecedentDepot implements Etat {
    private Integer dureeCollecte;
    private Integer dureeDepot;
    private Etape etapePrecedentCollecte = null;

    /** Méthode qui se lance au clique gauche sur la carte, de l'utilisateur
     * @param controleur controleur qui permet de changer l'état actuel
     * @param fenetre contient l'affichage de l'état suivant
     * @param carte   la carte
     * @param l       la liste des commandes, contient la liste des commandes qui ont été executé
     * @param tournee la tournée, tournée à laquelle la requête doit être ajouter
     * @param precedent précédent du dépot à ajouter, dans la tournée
     */
    @Override
    public void cliqueGauche(Controleur controleur, Fenetre fenetre, Carte carte, ListeDeCommandes l, Tournee tournee, Adresse precedent) {
        try {
            Adresse nouvelleAdresseDepot = fenetre.getCartePanel().getNouvelleAdresse().get(1);
            Etape depot = new Etape(nouvelleAdresseDepot.getLatitude(), nouvelleAdresseDepot.getLongitude(), nouvelleAdresseDepot.getIdAdresse(), dureeDepot);
            Adresse nouvelleAdresseCollecte = fenetre.getCartePanel().getNouvelleAdresse().get(0);
            Etape collecte = new Etape(nouvelleAdresseCollecte.getLatitude(), nouvelleAdresseCollecte.getLongitude(), nouvelleAdresseCollecte.getIdAdresse(), dureeCollecte);
            Adresse etapePrecedentDepot = tournee.rechercheEtape(precedent, nouvelleAdresseCollecte);
            Etape etapePrecDepot;
            if (nouvelleAdresseCollecte.getIdAdresse().equals(etapePrecedentDepot.getIdAdresse())) {
                etapePrecDepot = collecte;
            } else {
                etapePrecDepot = tournee.obtenirEtapeParId(etapePrecedentDepot.getIdAdresse());
            }

            Requete nouvelleRequete = new Requete(collecte, depot);

            if (!tournee.collectePrecedeDepot(collecte, etapePrecDepot, etapePrecedentCollecte)) {
                throw new CommandeImpossibleException("Erreur le prédecesseur du depot se situe avant la collecte dans l'itinéraire");
            }
            l.ajouter(new CommandeAjouteRequete(nouvelleRequete, etapePrecedentCollecte, etapePrecDepot, tournee, carte));
            fenetre.setAuthorisationCliquerBoutonUndo(true);
            fenetre.getCartePanel().viderNouvelleRequete();
            controleur.setEtatActuel(controleur.etatTourneeOrdonnee);
            fenetre.afficherEtatTourneePreparee(tournee);
            tournee.notifyObservers(tournee);
        } catch (CommandeImpossibleException e) {
            //En cas d'erreur
            String messageErreur = e.getMessage();
            System.out.println("ERREUR " + e);
            JOptionPane.showMessageDialog(null, messageErreur);
            //Reste dans l'état actuel
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

    /** Methode qui met à jour la durée du dépot
     * @param dureeDepot la durée du dépot
     */
    public void mettreAJourDureeDepot(Integer dureeDepot) {
        this.dureeDepot = dureeDepot;
    }

    /** Methode qui met à jour la durée de la collecte
     * @param dureeCollecte la durée de la collecte
     */
    public void mettreAJourDureeCollecte(Integer dureeCollecte){this.dureeCollecte = dureeCollecte;}

    /** Méthode qui permet de récupérer le précédent de collecte dans la tournée
     * @param precendentColl, le précédent de la collecte dans la tournée
     */
    public void mettreAJourPrecedentCollecte(Etape precendentColl) {
        this.etapePrecedentCollecte = precendentColl;
    }
}
