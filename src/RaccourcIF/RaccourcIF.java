package RaccourcIF;

import Model.Carte;
import Controleur.Controleur;

public class RaccourcIF {

    public static void main(String[] args) {
        Carte carte = new Carte();
        new Controleur(carte);
    }

}
