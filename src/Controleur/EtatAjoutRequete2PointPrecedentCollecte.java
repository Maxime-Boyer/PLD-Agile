package Controleur;

import Exceptions.CommandeImpossibleException;
import Model.Adresse;
import Model.Carte;
import Model.Etape;
import Model.Tournee;
import Vue.Fenetre;

public class EtatAjoutRequete2PointPrecedentCollecte implements Etat{

    @Override
    public void cliqueGauche (Controleur controleur, Fenetre fenetre, Carte carte, ListeDeCommandes l, Tournee tournee, Adresse precedent){
        try {
            Adresse etapePrecedentCollecte = tournee.rechercheEtape(precedent);
            Etape etapePrecColl = tournee.obtenirEtapeParId(etapePrecedentCollecte.getIdAdresse());
            Etape collecte = (Etape) fenetre.getCartePanel().getNouvelleAdresse().get(0);
            l.ajouter(new CommandeAjouteRequete(tournee, carte, collecte, etapePrecColl));
        }
        catch (CommandeImpossibleException e) {
            e.printStackTrace();
        }


        /*TODO : add Astar pour trouver plus court chemin entre etape précédent et point collecte
        Ajouter le tracage de ce chemin sur la carte
        */


        controleur.setEtatActuel(controleur.etatAjoutRequete3DureeCollecte);
    }


}
