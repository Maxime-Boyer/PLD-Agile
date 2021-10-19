package Vue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Controleur.Controleur;

public class EcouteurBoutons implements ActionListener {

    private Controleur controleur;

    public EcouteurBoutons(Controleur controleur){
        this.controleur = controleur;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Method called by the button listener each time a button is clicked
        // Forward the corresponding message to the controller
        switch (e.getActionCommand()){
            case Fenetre.IMPORT_CARTE:
                controleur.chargerPlan();
                break;
            case Fenetre.IMPORT_TOURNEE:
                controleur.chargerListeRequete();
                break;
        }
    }
}