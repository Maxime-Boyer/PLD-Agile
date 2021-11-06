package Vue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Controleur_Package.Controleur;

public class EcouteurBoutons implements ActionListener {

    private Controleur controleur;

    /**
     * Creation d'une classe permettant de gérer les clics sur les boutons
     * @param controleur: le controleur du MVC
     */
    public EcouteurBoutons(Controleur controleur){
        this.controleur = controleur;
    }

    /**
     * Identification du bouton cliqué et appelle des méthodes du controleur associées
     * @param e: l'evenement à gérer
     */
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

            case Fenetre.PREPARER_TOURNEE:
                controleur.preparerTournee();
                break;
            case Fenetre.AJOUT_REQUETE:
                controleur.ajoutRequete();
                break;
        }
    }
}