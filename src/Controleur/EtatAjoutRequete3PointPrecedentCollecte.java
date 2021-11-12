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
        //On recherche l'étape de la tournée qui va etre le precedent de notre nouvelle collecte le plus proche de notre clique gauche
        Adresse etapePrecedentCollecte = tournee.rechercheEtape(precedent);
        if (etapePrecedentCollecte != null) {
            //On recupere l'étape de l'adresse précedente trouvée à partir de son id
            Etape etapePrecColl = tournee.obtenirEtapeParId(etapePrecedentCollecte.getIdAdresse());
            //On transmet l'étape précedent la collecte à l'état 6 qui se chargera de l'ajout de la requete à la liste des requetes ainsi que le calcul et ajout des nouveaux chemins
            controleur.etatAjoutRequete6PointPrecedentDepot.mettreAJourPrecedentCollecte(etapePrecColl);
            //On va à l'état 4 de l'ajout de requete
            controleur.setEtatActuel(controleur.etatAjoutRequete4PointDepot);
            //Met à jour l'affichage de la fenetre, le message utilisateur
            fenetre.afficherEtatAjoutRequete4();
        }
    }

    @Override
    public void annuler(Controleur controleur, Fenetre fenetre, Carte carte, ListeDeCommandes l, Tournee tournee) {
        //On vide la liste de CartePanel contenant le point de collecet temporaire
        fenetre.getCartePanel().viderNouvelleRequete();
        //On revient à l'état tournée ordonnée et on met à jour l'affichage
        controleur.setEtatActuel(controleur.etatTourneeOrdonnee);
        fenetre.afficherEtatTourneePreparee(tournee);
    }


    public void mettreAjourDuree(Integer dureeEtape) {
        //On recupere la valeur de durée rentrée à l'état 2
        this.dureeEtape = dureeEtape;
    }
}
