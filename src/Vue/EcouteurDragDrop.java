package Vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * Ecouteur permettant de déplacer la légende
 */
public class EcouteurDragDrop implements MouseMotionListener {

    /**
     * Constructeur de l'écouteur
     * Permet de déplacer la légende
     */
    public EcouteurDragDrop(){}

    @Override
    public void mouseDragged(MouseEvent e) {
        if(e.getSource() instanceof Legende && SwingUtilities.isLeftMouseButton(e)){
            int x = e.getXOnScreen();
            int y = e.getYOnScreen();

            ( (Legende) (e.getSource())).setPosition(x, y);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}

