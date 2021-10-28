package Algorithmie;

import Model.Adresse;
import Model.Carte;
import Model.CheminEntreEtape;

public interface Astar {

    /**
     * Calcul le plus court chemin entre deux adresses
     * @param depart : adresse de départ
     * @param arrivee : adresse d'arrivée
     * @return le plus court chemin entre ces deux adresses
     */
    public CheminEntreEtape chercherCheminEntreEtape(Adresse depart, Adresse arrivee);
}
