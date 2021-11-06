package Algorithmie;

import Model.Adresse;
import Model.Carte;

public class Astar1 extends TemplateAstar {

    public Astar1(Carte carte) {
        super(carte);
    }

    @Override
    protected double calculHeuristique(Adresse adresse, Adresse arrivee) {
        return 0;
    }

}
