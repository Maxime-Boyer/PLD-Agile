package Vue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Controleur.Controleur;
import Exceptions.NameFile;

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
                try {
                    controleur.chargerPlan();
                } catch (NameFile ex) {
                    ex.printStackTrace();
                }
                break;
            case Fenetre.IMPORT_TOURNEE:
                try {
                    controleur.chargerListeRequete();
                } catch (NameFile ex) {
                    ex.printStackTrace();
                }
                //TODO : delete this
                System.out.println("EcouteurBoutons : IMPORT_TOURNEE");
                break;
            case Fenetre.PREPARER_TOURNEE:
                try {
                    controleur.preparerTournee();
                } catch (NameFile ex) {
                    ex.printStackTrace();
                }
                break;
        }
    }
}