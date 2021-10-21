package Algorithmie;

import Model.Adresse;
import Model.Carte;
import Model.CheminEntreEtape;
import Model.Tournee;

import java.util.HashMap;
import java.util.LinkedList;

public class CalculateurTournee {

    private Carte carte;
    private Tournee tournee;

    private Dijkstra dijkstra;
    private TSP tsp;

    private LinkedList<CheminEntreEtape> listeChemins;

    public CalculateurTournee(Carte carte, Tournee tournee){

        //Recuperation des informations
        this.carte = carte;
        this.tournee = tournee;

        //Utilisation de Dijkstra et TSP
        dijkstra = new Dijkstra(carte,tournee);

    }

    public HashMap<Long,LinkedList<CheminEntreEtape>> calculerTournee(){

        System.out.println("-------------- DEBUT ALGO ------------");
        HashMap<Long,LinkedList<CheminEntreEtape>> resultatDijkstra = dijkstra.calculerChemins();
        System.out.println("------------ FIN DIJKSTRA ------------");
        tsp = new TSP(carte,tournee,resultatDijkstra);
        tsp.calculerOrdreEtapes();
        System.out.println("-------------- FIN ALGO ------------");


        return resultatDijkstra;

    }
}
