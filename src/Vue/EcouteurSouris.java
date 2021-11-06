package Vue;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import Model.Adresse;
import Model.Etape;

import Controleur_Package.Controleur;

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
        MouseEvent e = SwingUtilities.convertMouseEvent(fenetre, evt, vueGraphique);
        double longitude = vueGraphique.valeurLongitude(e.getX());
        double latitude = vueGraphique.valeurLatitude(e.getY());
        Adresse positionClique = new Adresse (latitude,longitude);
        return positionClique;
    }
}