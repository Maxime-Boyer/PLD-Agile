package Vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class EcouteurDragDrop implements MouseMotionListener {

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

