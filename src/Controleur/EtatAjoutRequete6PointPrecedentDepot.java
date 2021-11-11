package Controleur;

import Exceptions.CommandeImpossibleException;
import Model.*;
import Vue.Fenetre;

import javax.swing.*;

public class EtatAjoutRequete6PointPrecedentDepot implements Etat{
    private Integer dureeEtape;
    private Etape precendentColl = null;

    @Override
    public void cliqueGauche (Controleur controleur, Fenetre fenetre, Carte carte, ListeDeCommandes l, Tournee tournee, Adresse precedent){
        try {
            Adresse nouvelleAdresseDepot = fenetre.getCartePanel().getNouvelleAdresse().get(1);
            Etape depot = new Etape(nouvelleAdresseDepot.getLatitude(), nouvelleAdresseDepot.getLongitude(), nouvelleAdresseDepot.getIdAdresse(), dureeEtape);
            Adresse nouvelleAdresseCollecte = fenetre.getCartePanel().getNouvelleAdresse().get(0);
            Etape collecte = new Etape(nouvelleAdresseCollecte.getLatitude(), nouvelleAdresseCollecte.getLongitude(), nouvelleAdresseCollecte.getIdAdresse(), dureeEtape);


            Adresse etapePrecedentDepot = tournee.rechercheEtape(precedent, nouvelleAdresseCollecte);
            //System.out.println(etapePrecedentDepot);
            Etape etapePrecDepot = null;
            if (nouvelleAdresseCollecte.getIdAdresse() == etapePrecedentDepot.getIdAdresse()) {
                etapePrecDepot = collecte;
            }else{
                etapePrecDepot = tournee.obtenirEtapeParId(etapePrecedentDepot.getIdAdresse());
            }
            //System.out.println(etapePrecDepot);
            Requete nouvelleRequete = new Requete(collecte,depot);
            //tournee.ajoutRequete(nouvelleRequete);

            l.ajouter(new CommandeAjouteRequete(nouvelleRequete, precendentColl, etapePrecDepot, tournee, carte));
            if(!tournee.collectePrecedeDepot(collecte, etapePrecDepot)){
                l.annuler();
                throw new CommandeImpossibleException("Erreur le prédecesseur du depot se situe avant la collecte dans l'itinéraire");
            }

            //l.ajouter(new CommandeAjouteRequete(tournee, carte, depot, etapePrecDepot));
            //tournee.ajoutChemin(depot,etapePrecDepot,carte);

            fenetre.getCartePanel().viderNouvelleRequete();
            //fenetre.getCartePanel().repaint();
            controleur.setEtatActuel(controleur.etatTourneeOrdonnee);
            fenetre.afficherEtatTourneePreparee(tournee);
            tournee.notifyObservers(tournee);
        }
        catch (CommandeImpossibleException e) {
            //En cas d'erreur
            String messageErreur = e.getMessage();
            System.out.println("ERREUR "+e);
            JOptionPane.showMessageDialog(null, messageErreur);
            //Reste dans l'état actuel
        }
    }

    @Override
    public void cliqueDroit(Controleur controleur , Fenetre fenetre, Carte carte, ListeDeCommandes l, Tournee tournee) {
        //tournee.enleverChemin(collecte,carte);
        fenetre.getCartePanel().viderNouvelleRequete();
        controleur.setEtatActuel(controleur.etatTourneeOrdonnee);
        fenetre.afficherEtatTourneePreparee(tournee);
        //tournee.notifyObservers(tournee);
    }

    public void mettreAjourDuree(Integer dureeEtape){
        this.dureeEtape = dureeEtape;
    }


    public void mettreAJourPrecedentCollecte(Etape precendentColl){
        this.precendentColl= precendentColl;
    }
}
