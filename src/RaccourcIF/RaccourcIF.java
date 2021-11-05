package RaccourcIF;

import Model.Carte;
import Controleur_Package.Controleur;

public class RaccourcIF {

    /**
     * classe main de l'application
     */
    public static void main(String[] args) {
        Carte carte = new Carte();
        new Controleur(carte);
    }

}
