package Controleur_Package;

import Vue.Fenetre;

public class EtatAjoutRequete6DureeDepot implements Etat{

    @Override
    public void validerAjoutRequetePointDepot(Controleur controleur, Fenetre fenetre) {

        //TODO : Créer une étape collecte avec longitude, latitude ET durée rentrée dans la pop up
        //Récuperer attribut d'un champ de texte de la pop up présente dans Fenetre

        controleur.setEtatActuel(controleur.etatTourneeOrdonnee);
    }
}
