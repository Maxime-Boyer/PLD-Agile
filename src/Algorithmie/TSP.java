package Algorithmie;

import Model.Tournee;

public interface TSP {

    /**
     * Lance la recherche d'une solution
     */
    public void chercherSolution();

    /**
     * Retourne le cout de la meilleure solution
     */
    public int obtenirMeilleureSolution();

}
