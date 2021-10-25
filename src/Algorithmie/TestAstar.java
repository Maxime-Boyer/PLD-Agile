package Algorithmie;

import Model.*;

public class TestAstar {
    public static void main(String[] args) {
        Adresse a0 = new Adresse(0.0, 0.0, 0L);
        Adresse a1 = new Adresse(1.0, 0.0, 1L);
        Adresse a2 = new Adresse(0.0, 1.0, 2L);
        Adresse a3 = new Adresse(1.0, 1.0, 3L);
        Segment s0 = new Segment(a0, a1, "s0", 111195.0);
        a0.ajouterSegmentSortant(s0);
        Segment s1 = new Segment(a0, a2, "s1", 248629.0);
        a0.ajouterSegmentSortant(s1);
        Segment s2 = new Segment(a1, a3, "s2", 111178.0);
        a1.ajouterSegmentSortant(s2);
        Segment s3 = new Segment(a2, a3, "s3", 157249.0);
        a2.ajouterSegmentSortant(s3);

        Etape e0 = new Etape(a0.getLatitude(), a0.getLongitude(), a0.getIdAdresse(), 5, null);
        Etape e1 = new Etape(a3.getLatitude(), a3.getLongitude(), a3.getIdAdresse(), 10, null);

        Carte carte = new Carte("NomCarte");

        carte.getListeAdresses().put(a0.getIdAdresse(),a0);
        carte.getListeAdresses().put(a1.getIdAdresse(),a1);
        carte.getListeAdresses().put(a2.getIdAdresse(),a2);
        carte.getListeAdresses().put(a3.getIdAdresse(),a3);

        carte.getListeSegments().add(s0);
        carte.getListeSegments().add(s1);
        carte.getListeSegments().add(s2);
        carte.getListeSegments().add(s3);

        Astar astar = new Astar(carte, e0, e1);
        CheminEntreEtape cheminEntreEtape = astar.executerAstar();
        System.out.println(cheminEntreEtape);
    }
}
