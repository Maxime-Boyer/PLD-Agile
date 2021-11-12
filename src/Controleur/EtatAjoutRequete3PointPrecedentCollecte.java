package Controleur;

import Model.Adresse;
import Model.Carte;
import Model.Etape;
import Model.Tournee;
import Vue.Fenetre;

/**
 * Troisième état de l'ajout de requêtes, permet de choisir l'étape précédent l'étape ajoutée
 */
public class EtatAjoutRequete3PointPrecedentCollecte implements Etat {
    private Integer dureeEtape;

    @Override
    public void cliqueGauche(Controleur controleur, Fenetre fenetre, Carte carte, ListeDeCommandes l, Tournee tournee, Adresse precedent) {
        Adresse etapePrecedentCollecte = tournee.rechercheEtape(precedent, null);
        if (etapePrecedentCollecte != null) {
            Etape etapePrecColl = tournee.obtenirEtapeParId(etapePrecedentCollecte.getIdAdresse());
            controleur.etatAjoutRequete6PointPrecedentDepot.mettreAJourPrecedentCollecte(etapePrecColl);
            Adresse nouvelleAdresseCollecte = fenetre.getCartePanel().getNouvelleAdresse().get(0);
            Etape collecte = new Etape(nouvelleAdresseCollecte.getLatitude(), nouvelleAdresseCollecte.getLongitude(), nouvelleAdresseCollecte.getIdAdresse(), dureeEtape);
            controleur.setEtatActuel(controleur.etatAjoutRequete4PointDepot);
            fenetre.afficherEtatAjoutRequete4();
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
}
