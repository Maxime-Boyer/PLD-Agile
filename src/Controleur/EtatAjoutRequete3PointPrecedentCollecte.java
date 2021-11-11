package Controleur;

import Exceptions.CommandeImpossibleException;
import Model.*;
import Vue.Fenetre;

public class EtatAjoutRequete3PointPrecedentCollecte implements Etat{
    private Integer dureeEtape;
    @Override
    public void cliqueGauche (Controleur controleur, Fenetre fenetre, Carte carte, ListeDeCommandes l, Tournee tournee, Adresse precedent){
        try {
            Adresse etapePrecedentCollecte = tournee.rechercheEtape(precedent);
            Etape etapePrecColl = tournee.obtenirEtapeParId(etapePrecedentCollecte.getIdAdresse());
            Adresse nouvelleAdresseCollecte = fenetre.getCartePanel().getNouvelleAdresse().get(0);
            Etape collecte = new Etape(nouvelleAdresseCollecte.getLatitude(), nouvelleAdresseCollecte.getLongitude(), nouvelleAdresseCollecte.getIdAdresse(),dureeEtape);
            l.ajouter(new CommandeAjouteRequete(tournee, carte, collecte, etapePrecColl));
            //fenetre.getCartePanel().repaint();
            controleur.setEtatActuel(controleur.etatAjoutRequete4PointDepot);
            //fenetre.afficherEtatAjoutRequete4();
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
