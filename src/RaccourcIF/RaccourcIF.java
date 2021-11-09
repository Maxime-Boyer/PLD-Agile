package RaccourcIF;

import Model.Carte;
import Controleur.Controleur;
import Model.FeuilleRoute;

public class RaccourcIF {

    /**
     * classe main de l'application
     */
    public static void main(String[] args) {
        Carte carte = new Carte();
        new Controleur(carte);
        FeuilleRoute feuilleRoute = new FeuilleRoute();
        feuilleRoute.createFeuilleRoute();
    }

}
