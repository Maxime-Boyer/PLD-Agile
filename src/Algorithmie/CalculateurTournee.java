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
        System.out.println(resultatDijkstra);

        System.out.println(" before ");

        System.out.println(tournee.getListeChemins());

        System.out.println(" after ");

        tsp = new TSP(carte,tournee,resultatDijkstra);
        tsp.calculerTourneeInitiale();

        System.out.println(tournee.getListeChemins());

        tsp.calculerOrdreEtapes();

        System.out.println(" after again ");

        System.out.println(tournee.getListeChemins());


        System.out.println("-------------- FIN ALGO ------------");


        return resultatDijkstra;

    }

    public TSP getTsp() {
        return tsp;
    }
}
