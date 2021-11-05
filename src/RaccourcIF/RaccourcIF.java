package RaccourcIF;

import Model.Carte;
import Controleur_Package.Controleur;

public class RaccourcIF {

    public static void main(String[] args) {
        Carte carte = new Carte();
        new Controleur(carte);
    }

}
