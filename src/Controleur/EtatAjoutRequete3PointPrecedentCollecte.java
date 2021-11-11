package Controleur;

import Exceptions.CommandeImpossibleException;
import Model.*;
import Vue.Fenetre;

public class EtatAjoutRequete3PointPrecedentCollecte implements Etat{
    private Integer dureeEtape;
    @Override
    public void cliqueGauche (Controleur controleur, Fenetre fenetre, Carte carte, ListeDeCommandes l, Tournee tournee, Adresse precedent){
        Adresse etapePrecedentCollecte = tournee.rechercheEtape(precedent,null);
        if(etapePrecedentCollecte != null) {
            Etape etapePrecColl = tournee.obtenirEtapeParId(etapePrecedentCollecte.getIdAdresse());
            controleur.etatAjoutRequete6PointPrecedentDepot.mettreAJourPrecedentCollecte(etapePrecColl);
            Adresse nouvelleAdresseCollecte = fenetre.getCartePanel().getNouvelleAdresse().get(0);
            Etape collecte = new Etape(nouvelleAdresseCollecte.getLatitude(), nouvelleAdresseCollecte.getLongitude(), nouvelleAdresseCollecte.getIdAdresse(), dureeEtape);
            //l.ajouter(new CommandeAjouteRequete(tournee, carte, collecte, etapePrecColl));
            //tournee.ajoutChemin(collecte,etapePrecColl,carte);
            //fenetre.getCartePanel().repaint();
            controleur.setEtatActuel(controleur.etatAjoutRequete4PointDepot);
            fenetre.afficherEtatAjoutRequete4();
            //tournee.notifyObservers(tournee);
        }
    }

    @Override
    public void cliqueDroit(Controleur controleur, Fenetre fenetre, Carte carte, ListeDeCommandes l, Tournee tournee) {
        fenetre.getCartePanel().viderNouvelleRequete();
        controleur.setEtatActuel(controleur.etatTourneeOrdonnee);
        fenetre.afficherEtatTourneePreparee(tournee);
    }

    public void mettreAjourDuree(Integer dureeEtape){
        this.dureeEtape = dureeEtape;
    }


}
