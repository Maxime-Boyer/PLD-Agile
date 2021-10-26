package RaccourcIF;

import Exceptions.NameFile;
import Model.Carte;
import Controleur.Controleur;

public class RaccourcIF {

    public static void main(String[] args) throws NameFile {
        Carte carte = new Carte();
        new Controleur(carte);
    }
}
