package Algorithmie;

import Model.Carte;
import Model.CheminEntreEtape;
import Model.Tournee;

import javax.print.DocFlavor;
import java.util.HashMap;
import java.util.LinkedList;

public class TSP1 extends TemplateTSP {

    TSP1(Carte carte, Tournee tournee, HashMap<Long, HashMap<Long, CheminEntreEtape>> grapheCompletDesEtapes) {
        super(carte,tournee,grapheCompletDesEtapes);
    }

    @Override
    protected void lien() {

    }

    @Override
    protected void iterateur() {

    }

    @Override
    protected void tourneeInitiale(){

        /*
        System.out.println("TourneeInitiale");

        LinkedList<CheminEntreEtape> listeChemins = new LinkedList<>();

        Long adresseActuelle = tournee.getAdresseDepart().getIdAdresse();
        Long adresseVisee = null;

        for (int i = 0; i < tournee.getListeRequetes().size(); i++) {

            adresseVisee = tournee.getListeRequetes().get(i).getEtapeCollecte().getIdAdresse();

            listeChemins.add(grapheCompletDesEtapes.get(adresseActuelle).get(adresseVisee));

            adresseActuelle = adresseVisee;
            adresseVisee = tournee.getListeRequetes().get(i).getEtapeDepot().getIdAdresse();

            listeChemins.add(grapheCompletDesEtapes.get(adresseActuelle).get(adresseVisee));

            adresseActuelle = adresseVisee;

        }

        adresseActuelle = adresseVisee;
        if(adresseVisee != null){
            adresseVisee = tournee.getAdresseDepart().getIdAdresse();
            listeChemins.add(grapheCompletDesEtapes.get(adresseActuelle).get(adresseVisee));
        }


        tournee.setListeChemins(listeChemins);

        System.out.println("Fin tourneeInitiale");
        */

        /* NEW */

        System.out.println("TourneeInitiale");

        LinkedList<CheminEntreEtape> listeChemins = new LinkedList<>();

        Long adresseActuelle = tournee.getAdresseDepart().getIdAdresse();

        //Liste des etapes deja realisees
        LinkedList<Long> listeAdressesNoires = new LinkedList<>();

        for(int i=0 ; i<grapheCompletDesEtapes.size() ; i++) {
            long min = Long.MAX_VALUE;
            CheminEntreEtape ceeMin = null;
            for (CheminEntreEtape cee : grapheCompletDesEtapes.get(adresseActuelle).values()) {
                if (!listeAdressesNoires.contains(cee.etapeArrivee.getIdAdresse()) && cee.distance < min && cee.etapeArrivee != tournee.getAdresseDepart()) {
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
        System.out.println(adresseActuelle);
        System.out.println(tournee.getAdresseDepart().getIdAdresse());
        System.out.println(grapheCompletDesEtapes.get(adresseActuelle));
        System.out.println(grapheCompletDesEtapes.get(adresseActuelle).get(tournee.getAdresseDepart().getIdAdresse()));

        listeChemins.add(grapheCompletDesEtapes.get(adresseActuelle).get(tournee.getAdresseDepart().getIdAdresse()));

        System.out.println("listeChemins :" +listeChemins);
        tournee.setListeChemins(listeChemins);

        System.out.println("Fin tourneeInitiale");


    }

}
