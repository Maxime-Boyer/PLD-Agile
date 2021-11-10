package Controleur;

import Exceptions.CommandeImpossibleException;
import Model.Carte;
import Model.Requete;
import Model.Tournee;
import Vue.Fenetre;

public class EtatSupprimerRequete implements Etat {

    @Override
    public void supressionRequete(Controleur controleur, Fenetre fenetre, ListeDeCommandes listeDeCommandes, Tournee tournee, Carte carte, Requete requete){
        try{
            listeDeCommandes.ajouter();
        }
    }


}
