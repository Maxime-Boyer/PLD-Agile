package Algorithmie;

import Model.CheminEntreEtape;
import Model.Etape;


public interface Astar {

    /**
     * Calcul le plus court chemin entre deux adresses
     *
     * @param depart  : adresse de départ
     * @param arrivee : adresse d'arrivée
     * @return le plus court chemin entre ces deux adresses
     */
    public CheminEntreEtape chercherCheminEntreEtape(Etape depart, Etape arrivee);
}
