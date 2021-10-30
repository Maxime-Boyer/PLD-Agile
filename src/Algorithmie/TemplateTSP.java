package Algorithmie;

import Model.Carte;
import Model.CheminEntreEtape;
import Model.Tournee;

import java.util.HashMap;

public abstract class TemplateTSP implements TSP{

    Carte carte;
    Tournee tournee;
    HashMap<Long, HashMap<Long, CheminEntreEtape>> grapheCompletDesEtapes;

    TemplateTSP(Carte carte, Tournee tournee, HashMap<Long, HashMap<Long, CheminEntreEtape>> grapheCompletDesEtapes){
        this.carte = carte;
        this.tournee = tournee;
        this.grapheCompletDesEtapes = grapheCompletDesEtapes;
    }

    protected abstract void lien();

    protected abstract void iterateur();

    protected abstract void tourneeInitiale();

    @Override
    public void chercherSolution() {
        tourneeInitiale();
    }

    @Override
    public Tournee obtenirSolution() {
        return tournee;
    }

    @Override
    public void obtenirCoutSolution() {

    }

}
