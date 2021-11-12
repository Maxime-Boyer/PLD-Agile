package Vue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Controleur.Controleur;
import Exceptions.CommandeImpossibleException;
import Model.Requete;

public class EcouteurBoutons implements ActionListener {

    private Controleur controleur;

    /**
     * Creation d'une classe permettant de gérer les clics sur les boutons
     * @param controleur: le controleur du MVC
     */
    public EcouteurBoutons(Controleur controleur){
        this.controleur = controleur;
    }

    public EcouteurBoutons(){}

    /**
     * Identification du bouton cliqué et appel des méthodes du controleur associées
     * @param e: l'evenement à gérer
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // Méthode appelé par l'ecouteurBouton chaque fois que un bouton est cliqué
        //Cette méthode appele les bonnes méthodes du controleur
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

            case Fenetre.EXPORTER_FEUILLE_ROUTE:
                controleur.exporterFeuilleDeRoute();
                break;

            case Fenetre.AJOUT_REQUETE:
                controleur.ajoutRequete();
                break;

            case Fenetre.VALIDER_AJOUT_DUREE_COLLECTE_REQUETE:
                controleur.validerAjoutDureeEtape();
                break;

            case Fenetre.SUPPRIMER_REQUETE:
                Requete requete = ((BoutonSuppressionRequete) e.getSource()).getRequete();
                System.out.println(requete);
                controleur.supressionRequete(requete);
                break;
            case Fenetre.UNDO:
                try {
                    controleur.cliqueBoutonUndo();
                } catch (CommandeImpossibleException commandeImpossibleException) {
                    commandeImpossibleException.printStackTrace();
                }
                break;
            case Fenetre.REDO:
                try {
                    controleur.cliqueBoutonRedo();
                } catch (CommandeImpossibleException ex) {
                    ex.printStackTrace();
                }
                break;
            case Fenetre.ANNULER_AJOUT_REQUETE:
                controleur.annuler();
        }
    }
}