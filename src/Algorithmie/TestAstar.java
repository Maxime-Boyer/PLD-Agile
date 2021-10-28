package Algorithmie;

import Model.*;

import java.util.ArrayList;
import java.util.List;

public class TestAstar {
    public static void main(String[] args) {
        List<Adresse> listeAdresse = new ArrayList<>();
        listeAdresse.add( new Adresse(0.0, 0.0, 0L) );
        listeAdresse.add( new Adresse(1.0, 0.0, 1L) );
        listeAdresse.add( new Adresse(0.0, 1.0, 2L) );
        listeAdresse.add( new Adresse(1.0, 1.0, 3L) );
        listeAdresse.add( new Adresse(2.0, 1.0, 4L) );

        List<Segment> listeSegment = new ArrayList<>();

        creerSegment(0,1, "s0", listeSegment, listeAdresse);
        creerSegment(1,0, "Is0", listeSegment, listeAdresse);
        creerSegment(1,2, "s1", listeSegment, listeAdresse);
        creerSegment(1,3, "s2", listeSegment, listeAdresse);
        creerSegment(2,3, "s3", listeSegment, listeAdresse);
        creerSegment(1,4, "s4", listeSegment, listeAdresse);
        creerSegment(3,4, "s5", listeSegment, listeAdresse);

        int idDep = 3;
        Etape e0 = new Etape(listeAdresse.get(idDep).getLatitude(), listeAdresse.get(idDep).getLongitude(), listeAdresse.get(idDep).getIdAdresse(), 5, null);
        int idArr = 0;
        Etape e1 = new Etape(listeAdresse.get(idArr).getLatitude(), listeAdresse.get(idArr).getLongitude(), listeAdresse.get(idArr).getIdAdresse(), 10, null);

        Carte carte = new Carte("NomCarte");

        for (Adresse adresse : listeAdresse)
            carte.ajouterAdresse(adresse);

        for (Segment segment : listeSegment)
            carte.ajouterSegment(segment);

        AstarOLD astar = new AstarOLD(carte);
        CheminEntreEtape cheminEntreEtape = astar.executerAstar(e0, e1);
        System.out.println(cheminEntreEtape);
    }

    public static void creerSegment (int idDep, int idArr, String nom, List<Segment> listeSegment, List<Adresse> listeAdresse) {
        Segment nouveauSegment = new Segment(listeAdresse.get(idDep), listeAdresse.get(idArr), nom, distance(listeAdresse.get(idDep), listeAdresse.get(idArr)));
        listeSegment.add( nouveauSegment );
        listeAdresse.get(idDep).ajouterSegmentSortant( nouveauSegment );
    }

    public static double distance(Adresse a1, Adresse a2)
    {
        // The math module contains a function
        // named toRadians which converts from
        // degrees to radians.
        double lon1 = Math.toRadians(a1.getLongitude());
        double lon2 = Math.toRadians(a2.getLongitude());
        double lat1 = Math.toRadians(a1.getLatitude());
        double lat2 = Math.toRadians(a2.getLatitude());

        // Haversine formula
        double dlon = lon2 - lon1;
        double dlat = lat2 - lat1;
        double a = Math.pow(Math.sin(dlat / 2), 2)
                + Math.cos(lat1) * Math.cos(lat2)
                * Math.pow(Math.sin(dlon / 2),2);

        double c = 2 * Math.asin(Math.sqrt(a));

        // Radius of earth in kilometers. Use 3956
        // for miles
        double r = 6371;

        // calculate the result
        return(c * r);
    }
}
