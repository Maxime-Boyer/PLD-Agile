package Algorithmie;

import Controleur.BooleanThread;
import Exceptions.AStarImpossibleException;
import Model.Carte;
import Model.Tournee;

public class RunnableAlgorithmie implements Runnable {

    private Carte carte;
    private Tournee tournee;
    private BooleanThread booleanThread;

    public RunnableAlgorithmie(Carte carte, Tournee tournee, BooleanThread booleanThread){
        this.carte = carte;
        this.tournee = tournee;
        this.booleanThread = booleanThread;
    }

    @Override
    public void run() {
        CalculateurTournee calculTournee = new CalculateurTournee(carte, tournee);
        try {
            calculTournee.calculerTournee(booleanThread);
        } catch (AStarImpossibleException e){
            e.printStackTrace();
        }
    }

    public BooleanThread getBooleanThread() {
        return booleanThread;
    }

    public void setBoolMinuteurDemarre(BooleanThread booleanThread) {
        this.booleanThread = booleanThread;
    }
}
