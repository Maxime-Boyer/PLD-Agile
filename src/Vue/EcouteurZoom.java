package Vue;

import java.awt.event.*;

public class EcouteurZoom implements MouseWheelListener{

    private CartePanel cartePanel;
    private double forceZoom;

    EcouteurZoom(CartePanel cartePanel, double forceZoom){
        this.cartePanel = cartePanel;
        this.forceZoom = forceZoom;
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {

        double maxLatitudeCarte = cartePanel.getMaxLatitudeCarte()+forceZoom*e.getWheelRotation()*(((double)e.getY()/cartePanel.getHauteur()))*(double)cartePanel.getHauteur()/cartePanel.getLargeur();
        double minLatitudeCarte = cartePanel.getMinLatitudeCarte()-forceZoom*e.getWheelRotation()*(1-((double)e.getY()/cartePanel.getHauteur()))*(double)cartePanel.getHauteur()/cartePanel.getLargeur();
        double maxLongitudeCarte = cartePanel.getMaxLongitudeCarte()+forceZoom*e.getWheelRotation()*(1-((double)e.getX()/cartePanel.getLargeur()));
        double minLongitudeCarte = cartePanel.getMinLongitudeCarte()-forceZoom*e.getWheelRotation()*((double)e.getX()/cartePanel.getLargeur());

        cartePanel.setMaxLatitudeCarte(maxLatitudeCarte > cartePanel.getMaxLatitudeInitialeCarte() ? cartePanel.getMaxLatitudeInitialeCarte() : maxLatitudeCarte);
        cartePanel.setMinLatitudeCarte(minLatitudeCarte < cartePanel.getMinLatitudeInitialeCarte() ? cartePanel.getMinLatitudeInitialeCarte() : minLatitudeCarte);
        cartePanel.setMaxLongitudeCarte(maxLongitudeCarte > cartePanel.getMaxLongitudeInitialeCarte() ? cartePanel.getMaxLongitudeInitialeCarte() : maxLongitudeCarte);
        cartePanel.setMinLongitudeCarte(minLongitudeCarte < cartePanel.getMinLongitudeInitialeCarte() ? cartePanel.getMinLongitudeInitialeCarte() : minLongitudeCarte);

        cartePanel.repaint();

    }

}
