package Algorithmie;

import Model.*;

import java.util.HashMap;
import java.util.LinkedList;

public class CalculateurTournee {

    private Carte carte;
    private Tournee tournee;

    private Astar astar;
    private Dijkstra dijkstra;
    private TSP tsp;

    private LinkedList<CheminEntreEtape> listeChemins;

    public CalculateurTournee(Carte carte, Tournee tournee){

        //Recuperation des informations
        this.carte = carte;
        this.tournee = tournee;

        //Utilisation de Dijkstra et TSP
        dijkstra = new Dijkstra(carte,tournee);
        astar = new Astar(carte);

    }

    public HashMap<Long,LinkedList<CheminEntreEtape>> calculerTournee(){

        System.out.println("Origine tournee = " + tournee.getAdresseDepart().getIdAdresse());
        System.out.println("    Liste des requêtes : "+tournee.getListeRequetes());
        System.out.println("-------------- DEBUT ALGO ------------");
        HashMap<Long,LinkedList<CheminEntreEtape>> resultatDijkstra = new HashMap<>();//dijkstra.calculerChemins();

        for(int depart=0 ; depart<tournee.getListeRequetes().size()*2 + 1 ; depart++) {
            Etape etapeDepart;
            if(depart == tournee.getListeRequetes().size()*2) {
                etapeDepart = tournee.getAdresseDepart();
            } else {
                if (depart % 2 == 0) {
                    etapeDepart = tournee.getListeRequetes().get(depart / 2).getEtapeCollecte();
                } else {
                    etapeDepart = tournee.getListeRequetes().get(depart / 2).getEtapeDepot();
                }
            }
            LinkedList<CheminEntreEtape> listeCheminEntereEtap = new LinkedList<>();

            for(int arr=0 ; arr<tournee.getListeRequetes().size()*2 + 1 ; arr++) {
                if (arr != depart) {
                    Etape etapeArrivee;
                    if(arr == tournee.getListeRequetes().size()*2) {
                        etapeArrivee = tournee.getAdresseDepart();
                    } else {
                        if(arr%2==0) {
                            etapeArrivee = tournee.getListeRequetes().get(arr/2).getEtapeCollecte();
                        } else {
                            etapeArrivee = tournee.getListeRequetes().get(arr/2).getEtapeDepot();
                        }
                    }
                    System.out.println("etapeDepart="+etapeDepart.getIdAdresse()+", etapeArrivee"+etapeArrivee.getIdAdresse());
                    CheminEntreEtape nouveauChemin = astar.executerAstar(etapeDepart, etapeArrivee);
                    listeCheminEntereEtap.add(nouveauChemin);
                    System.out.println("    longeurChemin="+nouveauChemin.getDistance());
                    System.out.println("    listeCheminEntereEtap = " + listeCheminEntereEtap);
                }
            }

            resultatDijkstra.put(etapeDepart.getIdAdresse(), listeCheminEntereEtap);
        }

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
