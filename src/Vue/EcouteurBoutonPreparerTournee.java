package Vue;

import Controleur.Controleur;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EcouteurBoutonPreparerTournee extends EcouteurBoutons {

    private Controleur controleur;
    private JTextArea jTextArea;

    /**
     * Creation d'une classe permettant de gérer les clics sur les boutons
     * @param controleur: le controleur du MVC
     */
    public EcouteurBoutonPreparerTournee(Controleur controleur, JTextArea jTextArea){
        super(controleur);
        this.jTextArea = jTextArea;
    }

    public EcouteurBoutonPreparerTournee(){}

    /**
     * Identification du bouton cliqué et appelle des méthodes du controleur associées
     * @param e: l'evenement à gérer
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // Method called by the button listener each time a button is clicked
        // Forward the corresponding message to the controller
        super.actionPerformed(e);
        if(e.getActionCommand() == Fenetre.PREPARER_TOURNEE){
            int sec = Integer.parseInt(jTextArea.getText());
            controleur.preparerTournee();
        }
    }

    public void setjTextArea(JTextArea jTextArea) {
        this.jTextArea = jTextArea;
    }
}