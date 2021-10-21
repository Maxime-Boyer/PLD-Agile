package Algorithmie;

import Model.Adresse;
import Model.Carte;
import Model.CheminEntreEtape;
import Model.Tournee;

import java.util.HashMap;
import java.util.LinkedList;

public class TSP {

    private HashMap<Long,LinkedList<CheminEntreEtape>> resultatDijkstra;

    private Tournee tournee;
    private Carte carte;

    public TSP(Carte carte, Tournee tournee, HashMap<Long,LinkedList<CheminEntreEtape>> resultatDijkstra){

        this.resultatDijkstra = resultatDijkstra;

        this.carte = carte;
        this.tournee = tournee;

    }

    public void calculerOrdreEtapes(){

    }

    public void calculerTourneeInitiale() {


        LinkedList<CheminEntreEtape> listeChemins = new LinkedList<>();

        Long adresseActuelle = tournee.getAdresseDepart().getIdAdresse();
        Long adresseVisee;

        for (int i = 0; i < tournee.getListeRequetes().size(); i++) {

            adresseVisee = tournee.getListeRequetes().get(i).getEtapeCollecte().getIdAdresse();

            trouverChemin(adresseActuelle,adresseVisee, listeChemins);

            adresseActuelle = adresseVisee;
            adresseVisee = tournee.getListeRequetes().get(i).getEtapeDepot().getIdAdresse();

            trouverChemin(adresseActuelle,adresseVisee, listeChemins);

            adresseActuelle = adresseVisee;

        }

        tournee.setListeChemins(listeChemins);

    }

    private void trouverChemin(Long adresseActuelle, Long adresseVisee, LinkedList<CheminEntreEtape> listeChemins){
        for(int j=0 ; j<resultatDijkstra.get(adresseActuelle).size() ; j++){
            if(resultatDijkstra.get(adresseActuelle).get(j).etapeArrivee.getIdAdresse().intValue() == adresseVisee.intValue()){
                listeChemins.push(resultatDijkstra.get(adresseActuelle).get(j));
                break;
            }
        }
    }
}
