package Vue;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import Model.Adresse;
import Model.Etape;

import Controleur.Controleur;

import javax.swing.*;

public class EcouteurSouris extends MouseAdapter {

    private Controleur controleur;
    private CartePanel vueGraphique;
    private Fenetre fenetre;

    public EcouteurSouris(Controleur controleur, CartePanel vueGraphique, Fenetre fenetre) {
        this.controleur = controleur;
        this.vueGraphique = vueGraphique;
        this.fenetre = fenetre;
    }

    @Override
    public void mouseClicked(MouseEvent evt) {
        // Method called by the mouse listener each time the mouse is clicked
        switch (evt.getButton()) {
            case MouseEvent.BUTTON1:
                Adresse a = coordonnees(evt);
                if (a != null)
                    controleur.cliqueGauche(a);
                break;
            /* case MouseEvent.BUTTON3:
                controller.rightClick();
                break;
            default:
        }*/
            default:
        }
    }

    private Adresse coordonnees(MouseEvent evt){

        if(vueGraphique != null) {
            double longitude = vueGraphique.valeurLongitude(evt.getX());
            double latitude = vueGraphique.valeurLatitude(evt.getY());
            Adresse positionClique = new Adresse(latitude, longitude);
            return positionClique;
        }
        return null;
    }

    public void setVueGraphique(CartePanel vueGraphique) {
        this.vueGraphique = vueGraphique;
    }
}