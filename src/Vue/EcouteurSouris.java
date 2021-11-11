package Vue;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import Exceptions.CommandeImpossibleException;
import Model.Adresse;
import Model.Etape;

import Controleur.Controleur;

import javax.swing.*;

/**
 * Ecouteur pour l'ajout d'une requete
 */
public class EcouteurSouris extends MouseAdapter {

    private Controleur controleur;
    private CartePanel vueGraphique;
    private Fenetre fenetre;

    public EcouteurSouris(Controleur controleur, CartePanel vueGraphique, Fenetre fenetre) {
        this.controleur = controleur;
        this.vueGraphique = vueGraphique;
        this.fenetre = fenetre;
    }

    /**
     * Méthode écoutant le clic de la souris
     * @param evt
     */
    @Override
    public void mouseClicked(MouseEvent evt) {
        // Method called by the mouse listener each time the mouse is clicked
        switch (evt.getButton()) {
            case MouseEvent.BUTTON1:
                Adresse a = coordonnees(evt);
                if (a != null) {
                    try {
                        controleur.cliqueGauche(a);
                    } catch (CommandeImpossibleException e) {
                        e.printStackTrace();
                    }
                }
                break;
             case MouseEvent.BUTTON3:
                controleur.cliqueDroit();
                break;
            default:
        }
    }

    /**
     * Cree une adresse a partir de la position de la souris
     * @param evt MouseEvent
     * @return l'adresse la plus proche de la souris
     */
    private Adresse coordonnees(MouseEvent evt){

        if(vueGraphique != null) {
            double longitude = vueGraphique.valeurLongitude(evt.getX());
            double latitude = vueGraphique.valeurLatitude(evt.getY());
            Adresse positionClique = new Adresse(latitude, longitude);
            return positionClique;
        }
        return null;
    }

    /**
     * Setter sur vueGraphie (CartePanel)
     * @param vueGraphique
     */
    public void setVueGraphique(CartePanel vueGraphique) {
        this.vueGraphique = vueGraphique;
    }
}