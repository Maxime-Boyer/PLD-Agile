package Algorithmie;

import Model.CheminEntreEtape;
import Model.Tournee;

import java.util.HashMap;
import java.util.LinkedList;

public class TourneeInitialePlusCourtChemin {

    Tournee tournee;
    HashMap<Long, HashMap<Long, CheminEntreEtape>> grapheCompletDesEtapes;

    public TourneeInitialePlusCourtChemin(Tournee tournee, HashMap<Long, HashMap<Long, CheminEntreEtape>> grapheCompletDesEtapes){

        this.tournee = tournee;
        this.grapheCompletDesEtapes = grapheCompletDesEtapes;

    }

    public void trouverTourneeInitiale(){

        LinkedList<CheminEntreEtape> listeChemins = new LinkedList<>();

        Long adresseActuelle = tournee.getEtapeDepart().getIdAdresse();

        //Liste des etapes deja realisees
        LinkedList<Long> listeAdressesNoires = new LinkedList<>();

        for(int i=0 ; i<grapheCompletDesEtapes.size() ; i++) {
            long min = Long.MAX_VALUE;
            CheminEntreEtape ceeMin = null;
            for (CheminEntreEtape cee : grapheCompletDesEtapes.get(adresseActuelle).values()) {
                if (!listeAdressesNoires.contains(cee.etapeArrivee.getIdAdresse()) && cee.distance < min && cee.etapeArrivee != tournee.getEtapeDepart()) {
                    min = cee.distance;
                    ceeMin = cee;
                }
            }
            if (ceeMin != null) {
                listeAdressesNoires.add(ceeMin.etapeArrivee.getIdAdresse());
                listeChemins.push(ceeMin);
                adresseActuelle = ceeMin.etapeArrivee.getIdAdresse();
            }
        }
        //On retourne au depart de la tournee

        listeChemins.add(grapheCompletDesEtapes.get(adresseActuelle).get(tournee.getEtapeDepart().getIdAdresse()));

        tournee.setListeChemins(listeChemins);

    }

}
