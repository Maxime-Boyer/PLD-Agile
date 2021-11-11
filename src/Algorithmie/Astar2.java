package Algorithmie;

import Model.Adresse;
import Model.Carte;

public class Astar2 extends TemplateAstar {

    /**
     * Constructeur de Astar2
     *
     * @param carte la carte à partir de laquelle sera calculé le chemin
     */
    public Astar2(Carte carte) {
        super(carte);
    }

    @Override
    protected double calculHeuristique(Adresse adresse, Adresse arrivee) {
        double degToRad = 0.01745329;

        //Convertion degré à radian
        double lat1 = adresse.getLatitude() * degToRad;
        double lat2 = arrivee.getLatitude() * degToRad;
        double lon1 = adresse.getLongitude() * degToRad;
        double lon2 = arrivee.getLongitude() * degToRad;

        // Formule de Haversine
        double dlon = lon2 - lon1;
        double dlat = lat2 - lat1;
        double a = Math.pow(Math.sin(dlat / 2), 2)
                + Math.cos(lat1) * Math.cos(lat2)
                * Math.pow(Math.sin(dlon / 2), 2);

        double c = 2 * Math.asin(Math.sqrt(a));

        // Rayon de la terre en m
        double rayonTerre = 6371000;

        // Retourne la distance
        return (c * rayonTerre);
    }

}

