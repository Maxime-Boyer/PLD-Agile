package Controleur;

import Exceptions.CommandeImpossibleException;
import Model.*;
import Vue.Fenetre;

public class EtatAjoutRequete6PointPrecedentDepot implements Etat{

    private Integer dureeEtape;
    @Override
    public void cliqueGauche (Controleur controleur, Fenetre fenetre, Carte carte, ListeDeCommandes l, Tournee tournee, Adresse precedent){
        try {
            Adresse nouvelleAdresseDepot = fenetre.getCartePanel().getNouvelleAdresse().get(1);
            Etape depot = new Etape(nouvelleAdresseDepot.getLatitude(), nouvelleAdresseDepot.getLongitude(), nouvelleAdresseDepot.getIdAdresse(),dureeEtape);
            Adresse nouvelleAdresseCollecte = fenetre.getCartePanel().getNouvelleAdresse().get(0);
            Etape collecte = new Etape(nouvelleAdresseCollecte.getLatitude(), nouvelleAdresseCollecte.getLongitude(), nouvelleAdresseCollecte.getIdAdresse(),dureeEtape);

            Adresse etapePrecedentDepot = tournee.rechercheEtape(precedent, nouvelleAdresseCollecte);
            //System.out.println(etapePrecedentDepot);
            Etape etapePrecDepot = tournee.obtenirEtapeParId(etapePrecedentDepot.getIdAdresse());
            //System.out.println(etapePrecDepot);

            if(!tournee.collectePrecedeDepot(collecte, etapePrecDepot)){
                throw new CommandeImpossibleException("Erreur le prédecesseur du depot se situe avant la collecte dans l'itinéraire");
            }

            l.ajouter(new CommandeAjouteRequete(tournee, carte, depot, etapePrecDepot));
            Requete nouvelleRequete = new Requete(collecte,depot);
            tournee.ajoutRequete(nouvelleRequete);
            fenetre.getCartePanel().getNouvelleAdresse().clear();
            //fenetre.getCartePanel().repaint();
            controleur.setEtatActuel(controleur.etatTourneeOrdonnee);
            //fenetre.afficherEtatTourneePreparee(tournee);
            tournee.notifyObservers(tournee);
        }
        catch (CommandeImpossibleException e) {
            e.printStackTrace();
        }
    }

    public void mettreAjourDuree(Integer dureeEtape){
        this.dureeEtape = dureeEtape;
    }
}
