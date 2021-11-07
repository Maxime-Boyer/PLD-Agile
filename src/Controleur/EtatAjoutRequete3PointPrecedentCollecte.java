package Controleur;

import Vue.Fenetre;

public class EtatAjoutRequete3PointPrecedentCollecte implements Etat{
    private Integer dureeEtape;
    @Override
    public void validerAjoutRequetePointCollecte(Controleur controleur, Fenetre fenetre) {

        //TODO : Créer une étape collecte avec longitude, latitude ET durée rentrée dans la pop up
        //Récuperer attribut d'un champ de texte de la pop up présente dans Fenetre

        controleur.setEtatActuel(controleur.etatAjoutRequete4PointDepot);
    }

}
