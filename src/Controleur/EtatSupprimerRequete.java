package Controleur;

import Model.Requete;
import Model.Tournee;
import Vue.Fenetre;

public class EtatSupprimerRequete implements Etat {

    @Override
    public void supressionRequete(Controleur controleur, Fenetre fenetre, ListeDeCommandes listeDeCommandes, Tournee tournee, Requete requete){
        try{
            listeDeCommandes.ajouter();
        }
    }


}
