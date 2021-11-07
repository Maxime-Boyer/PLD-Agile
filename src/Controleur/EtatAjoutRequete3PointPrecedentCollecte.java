package Controleur;

import Exceptions.CommandeImpossibleException;
import Model.Adresse;
import Model.Carte;
import Model.Etape;
import Model.Tournee;
import Vue.Fenetre;

public class EtatAjoutRequete3PointPrecedentCollecte implements Etat{
    private Integer dureeEtape;
    @Override
    public void cliqueGauche (Controleur controleur, Fenetre fenetre, Carte carte, ListeDeCommandes l, Tournee tournee, Adresse precedent){
        try {
            Adresse etapePrecedentCollecte = tournee.rechercheEtape(precedent);
            System.out.println(etapePrecedentCollecte);
            Etape etapePrecColl = tournee.obtenirEtapeParId(etapePrecedentCollecte.getIdAdresse());
            System.out.println(etapePrecColl);
            Adresse nouvelleAdresseCollecte = fenetre.getCartePanel().getNouvelleAdresse().get(0);
            Etape collecte = new Etape(nouvelleAdresseCollecte.getLatitude(), nouvelleAdresseCollecte.getLongitude(), nouvelleAdresseCollecte.getIdAdresse(),dureeEtape);
            l.ajouter(new CommandeAjouteRequete(tournee, carte, collecte, etapePrecColl));
            fenetre.getCartePanel().repaint();
        }
        catch (CommandeImpossibleException e) {
            e.printStackTrace();
        }


        /*TODO : add Astar pour trouver plus court chemin entre etape précédent et point collecte
        Ajouter le tracage de ce chemin sur la carte
        */


        controleur.setEtatActuel(controleur.etatAjoutRequete3PointPrecedentCollecte);
    }

    public void mettreAjourDuree(Integer dureeEtape){
        this.dureeEtape = dureeEtape;
    }

}
