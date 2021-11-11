package Vue;

import java.awt.event.*;

public class EcouteurZoom implements MouseWheelListener{

    private CartePanel cartePanel;
    private double forceZoom;

    EcouteurZoom(CartePanel cartePanel, double forceZoom){
        this.cartePanel = cartePanel;
        this.forceZoom = forceZoom;
    }

    /**
     * Listener sur la MouseWheel. Permet de zoom et dezoom en fonction de la position de la souris.
     * @param e
     */
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {

        forceZoom = 0.05; //5%

        //On applique le zoom au coordonnees
        double maxLatitudeCarte = cartePanel.getMaxLatitudeCarte()+forceZoom*e.getWheelRotation()*(cartePanel.getMaxLatitudeCarte()-cartePanel.getMinLatitudeCarte());
        double minLatitudeCarte = cartePanel.getMinLatitudeCarte()-forceZoom*e.getWheelRotation()*(cartePanel.getMaxLatitudeCarte()-cartePanel.getMinLatitudeCarte());
        double maxLongitudeCarte = cartePanel.getMaxLongitudeCarte()+forceZoom*e.getWheelRotation()*(cartePanel.getMaxLongitudeCarte()-cartePanel.getMinLongitudeCarte());
        double minLongitudeCarte = cartePanel.getMinLongitudeCarte()-forceZoom*e.getWheelRotation()*(cartePanel.getMaxLongitudeCarte()-cartePanel.getMinLongitudeCarte());
        //On applique les coordonnees, et donc le zoom
        //Si celui-ci n'inverse pas l'image
        if(maxLatitudeCarte > minLatitudeCarte && maxLongitudeCarte > minLongitudeCarte) {
            //FIXME : dezoom 2* plus lent lorsqu'une des deux coordonnées à atteint le bord.
            /*cartePanel.setMaxLatitudeCarte(maxLatitudeCarte > cartePanel.getMaxLatitudeInitialeCarte() ? cartePanel.getMaxLatitudeInitialeCarte() : maxLatitudeCarte);
            cartePanel.setMinLatitudeCarte(minLatitudeCarte < cartePanel.getMinLatitudeInitialeCarte() ? cartePanel.getMinLatitudeInitialeCarte() : minLatitudeCarte);
            cartePanel.setMaxLongitudeCarte(maxLongitudeCarte > cartePanel.getMaxLongitudeInitialeCarte() ? cartePanel.getMaxLongitudeInitialeCarte() : maxLongitudeCarte);
            cartePanel.setMinLongitudeCarte(minLongitudeCarte < cartePanel.getMinLongitudeInitialeCarte() ? cartePanel.getMinLongitudeInitialeCarte() : minLongitudeCarte);
            */

            cartePanel.setMaxLatitudeCarte(maxLatitudeCarte);
            cartePanel.setMinLatitudeCarte(minLatitudeCarte);
            cartePanel.setMaxLongitudeCarte(maxLongitudeCarte);
            cartePanel.setMinLongitudeCarte(minLongitudeCarte);
        }


        //On calcul translation
        double diffLatitude = ((cartePanel.getMinLatitudeCarte() + cartePanel.getMaxLatitudeCarte())/2) - (valeurLatitude(e.getY()));
        double diffLongitude = ((cartePanel.getMinLongitudeCarte() + cartePanel.getMaxLongitudeCarte())/2) - (valeurLongitude(e.getX()));
        double mult = 4;
        diffLatitude = - diffLatitude*e.getWheelRotation()*forceZoom*mult;
        diffLongitude = diffLongitude*e.getWheelRotation()*forceZoom*mult;


        //cartePanel.setMaxLatitudeCarte(maxLatitudeCarte - diffLatitude*e.getWheelRotation()*forceZoom*mult); //*forceZoom
        //cartePanel.setMinLatitudeCarte(minLatitudeCarte - diffLatitude*e.getWheelRotation()*forceZoom*mult);
        //cartePanel.setMaxLongitudeCarte(maxLongitudeCarte + diffLongitude*e.getWheelRotation()*forceZoom*mult);
        //cartePanel.setMinLongitudeCarte(minLongitudeCarte + diffLongitude*e.getWheelRotation()*forceZoom*mult);

        //On deplace la carte sans pour autant y sortir
        /*
        if(cartePanel.getMaxLatitudeCarte()+diffLatitude > cartePanel.getMaxLatitudeInitialeCarte()){
            diffLatitude = cartePanel.getMaxLatitudeInitialeCarte() - cartePanel.getMaxLatitudeCarte();
        } else if(cartePanel.getMinLatitudeCarte()+diffLatitude < cartePanel.getMinLatitudeInitialeCarte()){
            diffLatitude = cartePanel.getMinLatitudeInitialeCarte() - cartePanel.getMinLatitudeCarte();
        }
        if(cartePanel.getMaxLongitudeCarte()+diffLongitude > cartePanel.getMaxLongitudeInitialeCarte()){
            diffLongitude = cartePanel.getMaxLongitudeInitialeCarte() - cartePanel.getMaxLongitudeCarte();
        } else if(cartePanel.getMinLongitudeCarte()+diffLongitude < cartePanel.getMinLongitudeInitialeCarte()){
            diffLongitude = cartePanel.getMinLongitudeInitialeCarte() - cartePanel.getMinLongitudeCarte();
        }*/
        cartePanel.setMaxLatitudeCarte(cartePanel.getMaxLatitudeCarte()+diffLatitude);
        cartePanel.setMinLatitudeCarte(cartePanel.getMinLatitudeCarte()+diffLatitude);
        cartePanel.setMaxLongitudeCarte(cartePanel.getMaxLongitudeCarte()+diffLongitude);
        cartePanel.setMinLongitudeCarte(cartePanel.getMinLongitudeCarte()+diffLongitude);



        cartePanel.repaint();

    }

    private double valeurLongitude(int posX){
        double ecartLongitude = cartePanel.getMaxLongitudeCarte() - cartePanel.getMinLongitudeCarte();
        double coeffX = cartePanel.getLargeur() / ecartLongitude;
        return posX / coeffX + cartePanel.getMinLongitudeCarte();
    }

    private double valeurLatitude(int posY){
        double ecartLatitude = cartePanel.getMaxLatitudeCarte() - cartePanel.getMinLatitudeCarte();
        double coeffY = cartePanel.getHauteur() / ecartLatitude;
        double latitude = posY / coeffY + cartePanel.getMinLatitudeCarte();
        return latitude;
    }
}
