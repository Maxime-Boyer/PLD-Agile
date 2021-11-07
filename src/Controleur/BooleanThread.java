package Controleur;

public class BooleanThread {

    private boolean boolMinuteurDemarre;
    private boolean resultatTrouve;
    private boolean stopThread;

    public BooleanThread(boolean boolMinuteurDemarre, boolean resultatTrouve, boolean stopThread){
        this.boolMinuteurDemarre = boolMinuteurDemarre;
        this.resultatTrouve = resultatTrouve;
        this.stopThread = stopThread;
    }

    public boolean isBoolMinuteurDemarre() {
        return boolMinuteurDemarre;
    }

    public void setBoolMinuteurDemarre(boolean boolMinuteurDemarre) {
        this.boolMinuteurDemarre = boolMinuteurDemarre;
    }

    public boolean isResultatTrouve() {
        return resultatTrouve;
    }

    public void setResultatTrouve(boolean resultatTrouve) {
        this.resultatTrouve = resultatTrouve;
    }

    public boolean isStopThread() {
        return stopThread;
    }

    public void setStopThread(boolean stopThread) {
        this.stopThread = stopThread;
    }
}
