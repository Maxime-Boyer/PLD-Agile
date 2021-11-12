package Vue;

import java.awt.event.*;

public class EcouteurZoom implements MouseWheelListener{

    private CartePanel cartePanel;
    private double forceZoom;

    /**
     * Création d'une classe permettant de gérer les actions liées au zoom sur la carte
     * @param cartePanel: le panel où est affiché la carte
     * @param forceZoom: vitesse du zoom lié à la souris de l'utilisateur
     */
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

        cartePanel.setMaxLatitudeCarte(cartePanel.getMaxLatitudeCarte()+diffLatitude);
        cartePanel.setMinLatitudeCarte(cartePanel.getMinLatitudeCarte()+diffLatitude);
        cartePanel.setMaxLongitudeCarte(cartePanel.getMaxLongitudeCarte()+diffLongitude);
        cartePanel.setMinLongitudeCarte(cartePanel.getMinLongitudeCarte()+diffLongitude);

        cartePanel.repaint();

    }

    /**
     * Converti une coordonnée x en pixel n longitude
     * @param posX coordonnée x en pixel
     * @return coordonnée en longitude
     */
    private double valeurLongitude(int posX){
        double ecartLongitude = cartePanel.getMaxLongitudeCarte() - cartePanel.getMinLongitudeCarte();
        double coeffX = cartePanel.getLargeur() / ecartLongitude;
        return posX / coeffX + cartePanel.getMinLongitudeCarte();
    }

    /**
     * Converti une coordonnée y en pixel en latitude
     * @param posY coordonnée x en pixel
     * @return coordonnée en latitude
     */
    private double valeurLatitude(int posY){
        double ecartLatitude = cartePanel.getMaxLatitudeCarte() - cartePanel.getMinLatitudeCarte();
        double coeffY = cartePanel.getHauteur() / ecartLatitude;
        double latitude = posY / coeffY + cartePanel.getMinLatitudeCarte();
        return latitude;
    }
}
