package Vue;

import Model.Carte;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;


/**
 * Ecouteur permettant de deplacer la carte avec clic droit ou clic molette
 */
public class EcouteurDrag implements MouseMotionListener {

    private CartePanel cartePanel;
    long temps;
    Point pointPrecedent;


    /**
     * Constructeur de l'écouteur
     * @param cartePanel
     */
    public EcouteurDrag(CartePanel cartePanel){
        this.cartePanel = cartePanel;
        //-100 pour empecher toute erreur si l'utilisateur clique instantannement sur le panel
        temps = System.currentTimeMillis() - 100;
        pointPrecedent = new Point(0,0);
    }

    /**
     * Listener sur la souris permettant de déplacer la carte au clic molette ou clic droit
     * @param e MouseEvent
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        if(SwingUtilities.isMiddleMouseButton(e) || SwingUtilities.isRightMouseButton(e)){
            if(System.currentTimeMillis() - temps < 100){
                double modificationLatitude = -(pointPrecedent.y-e.getY())/(double)cartePanel.getHauteur()*(cartePanel.getMaxLatitudeCarte()-cartePanel.getMinLatitudeCarte());
                double modificationLongitude = (pointPrecedent.x-e.getX())/(double)cartePanel.getLargeur()*(cartePanel.getMaxLongitudeCarte()-cartePanel.getMinLongitudeCarte());
                cartePanel.setMaxLatitudeCarte(cartePanel.getMaxLatitudeCarte() + modificationLatitude);
                cartePanel.setMinLatitudeCarte(cartePanel.getMinLatitudeCarte() + modificationLatitude);
                cartePanel.setMaxLongitudeCarte(cartePanel.getMaxLongitudeCarte() + modificationLongitude);
                cartePanel.setMinLongitudeCarte(cartePanel.getMinLongitudeCarte() + modificationLongitude);
            }
            pointPrecedent = e.getPoint();
            temps = System.currentTimeMillis();
            cartePanel.repaint();
        }
    }

    /**
     * Listener de la tournée lorqu'elle est déplacée
     * @param e
     */
    @Override
    public void mouseMoved(MouseEvent e) {}
}
