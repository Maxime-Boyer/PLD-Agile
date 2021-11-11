package Algorithmie;

import Model.Adresse;
import Model.Carte;

public class Astar1 extends TemplateAstar {

    /**
     * Constructeur de Astar1
     *
     * @param carte la carte à partir de laquelle sera calculé le chemin
     */
    public Astar1(Carte carte) {
        super(carte);
    }

    @Override
    protected double calculHeuristique(Adresse adresse, Adresse arrivee) {
        return 0;
    }

}
