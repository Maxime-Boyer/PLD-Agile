package Controleur;

import Exceptions.CommandeImpossibleException;
import Model.*;
import Vue.Fenetre;

import javax.swing.*;

/**
 * Sixième et dernier état de l'ajout de requêtes, permet de choisir l'étape précédent l'étape ajoutée et valide la création de la requête
 */
public class EtatAjoutRequete6PointPrecedentDepot implements Etat {
    private Integer dureeEtape;
    private Etape etapePrecedentCollecte = null;

    @Override
    public void cliqueGauche(Controleur controleur, Fenetre fenetre, Carte carte, ListeDeCommandes l, Tournee tournee, Adresse precedent) {
        try {
            Adresse nouvelleAdresseDepot = fenetre.getCartePanel().getNouvelleAdresse().get(1);
            Etape depot = new Etape(nouvelleAdresseDepot.getLatitude(), nouvelleAdresseDepot.getLongitude(), nouvelleAdresseDepot.getIdAdresse(), dureeEtape);
            Adresse nouvelleAdresseCollecte = fenetre.getCartePanel().getNouvelleAdresse().get(0);
            Etape collecte = new Etape(nouvelleAdresseCollecte.getLatitude(), nouvelleAdresseCollecte.getLongitude(), nouvelleAdresseCollecte.getIdAdresse(), dureeEtape);
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

    @Override
    public void annuler(Controleur controleur, Fenetre fenetre, Carte carte, ListeDeCommandes l, Tournee tournee) {
        fenetre.getCartePanel().viderNouvelleRequete();
        controleur.setEtatActuel(controleur.etatTourneeOrdonnee);
        fenetre.afficherEtatTourneePreparee(tournee);
    }

    public void mettreAjourDuree(Integer dureeEtape) {
        this.dureeEtape = dureeEtape;
    }


    public void mettreAJourPrecedentCollecte(Etape precendentColl) {
        this.etapePrecedentCollecte = precendentColl;
    }
}
