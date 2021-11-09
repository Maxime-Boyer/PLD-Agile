package Vue;

import Model.Carte;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class EcouteurDrag implements MouseMotionListener {

    private CartePanel cartePanel;
    long temps;
    Point pointPrecedent;


    public EcouteurDrag(CartePanel cartePanel){
        this.cartePanel = cartePanel;
        //-100 pour empecher toute erreur si l'utilisateur clique instantannement sur le panel
        temps = System.currentTimeMillis() - 100;
        pointPrecedent = new Point(0,0);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if(SwingUtilities.isMiddleMouseButton(e) || SwingUtilities.isRightMouseButton(e)){
            if(System.currentTimeMillis() - temps < 50){
                double modificationLatitude = -(pointPrecedent.y-e.getY())/(double)cartePanel.getHauteur()*(cartePanel.getMaxLatitudeCarte()-cartePanel.getMinLatitudeCarte());
                double modificationLongitude = (pointPrecedent.x-e.getX())/(double)cartePanel.getLargeur()*(cartePanel.getMaxLongitudeCarte()-cartePanel.getMinLongitudeCarte());
                cartePanel.setMaxLatitudeCarte(cartePanel.getMaxLatitudeCarte() + modificationLatitude);
                cartePanel.setMinLatitudeCarte(cartePanel.getMinLatitudeCarte() + modificationLatitude);
                cartePanel.setMaxLongitudeCarte(cartePanel.getMaxLongitudeCarte() + modificationLongitude);
                cartePanel.setMinLongitudeCarte(cartePanel.getMinLongitudeCarte() + modificationLongitude);
                //System.out.println("Moved ! " + (new Point(pointPrecedent.x-e.getX(),pointPrecedent.y-e.getY())));
            }
            pointPrecedent = e.getPoint();
            temps = System.currentTimeMillis();
            cartePanel.repaint();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
