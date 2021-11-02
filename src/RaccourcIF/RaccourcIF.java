package RaccourcIF;

import Exceptions.NameFile;
import Model.Carte;
import Controleur.Controleur;
import Model.FeuilleRoute;

public class RaccourcIF {

    public static void main(String[] args) throws NameFile {
        Carte carte = new Carte();
        new Controleur(carte);
        FeuilleRoute feuilleRoute = new FeuilleRoute();
      //  feuilleRoute.createFeuilleRoute();
    }
}
