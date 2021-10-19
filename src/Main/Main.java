package Main;
import Controleur.Controleur;
import Model.Carte;

public class Main {

    /**
     * Main pour lancer l'application
     * @param args
     */
    public static void main(String[] args) {
        Carte carte = new Carte();
        new Controleur(carte);
    }

}
