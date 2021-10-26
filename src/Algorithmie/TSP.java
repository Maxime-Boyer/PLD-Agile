package Algorithmie;

import Model.Adresse;
import Model.Carte;
import Model.CheminEntreEtape;
import Model.Tournee;
//import javafx.util.Pair;
//import sun.awt.image.ImageWatched;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class TSP {

    private HashMap<Long,LinkedList<CheminEntreEtape>> resultatDijkstra;

    private LinkedList<CheminEntreEtape> listeCheminsActuelsAmodifier;
    private LinkedList<CheminEntreEtape> listeCheminsSwapAmodifier;

    private Tournee tournee;
    private Carte carte;

    public Tournee getTournee() {
        return tournee;
    }

    public TSP(Carte carte, Tournee tournee, HashMap<Long,LinkedList<CheminEntreEtape>> resultatDijkstra){

        this.resultatDijkstra = resultatDijkstra;

        this.carte = carte;
        this.tournee = tournee;

    }

    public void calculerOrdreEtapes(){

        for(int i=0 ; i<tournee.getListeRequetes().size()*2-2 ; i++){
            for(int j=i+2 ; j<tournee.getListeRequetes().size()*2 ; j++){

                System.out.println("forfor");

                Long adresse1;
                if(i%2==0){
                    adresse1=tournee.getListeRequetes().get(i/2).getEtapeCollecte().getIdAdresse();
                } else {
                    adresse1=tournee.getListeRequetes().get(i/2).getEtapeDepot().getIdAdresse();
                }

                Long adresse2=-1L;
                for(CheminEntreEtape cee : tournee.getListeChemins()){
                    if(cee.etapeDepart.getIdAdresse().equals(adresse1)){
                        adresse2 = cee.etapeArrivee.getIdAdresse();
                    }
                }

                if(adresse2 >= 0L){

                    Long adresse3;
                    if(j%2==0){
                        adresse3=tournee.getListeRequetes().get(j/2).getEtapeCollecte().getIdAdresse();
                    } else {
                        adresse3=tournee.getListeRequetes().get(j/2).getEtapeDepot().getIdAdresse();
                    }

                    if(swapBenefique(adresse1,adresse2,adresse3) && listeCheminsSwapAmodifierCorrect() && ordreCollecteDepotCorrect(adresse2,adresse3)){
                        tournee.getListeChemins().remove(listeCheminsActuelsAmodifier.get(0));
                        tournee.getListeChemins().remove(listeCheminsActuelsAmodifier.get(1));
                        tournee.getListeChemins().remove(listeCheminsActuelsAmodifier.get(2));
                        tournee.getListeChemins().remove(listeCheminsActuelsAmodifier.get(3));
                        tournee.getListeChemins().add(listeCheminsSwapAmodifier.get(0));
                        tournee.getListeChemins().add(listeCheminsSwapAmodifier.get(1));
                        tournee.getListeChemins().add(listeCheminsSwapAmodifier.get(2));
                        tournee.getListeChemins().add(listeCheminsSwapAmodifier.get(3));
                    }

                }

            }

        }

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

    private boolean listeCheminsSwapAmodifierCorrect(){
        return listeCheminsSwapAmodifier.get(0) != null && listeCheminsSwapAmodifier.get(1) != null && listeCheminsSwapAmodifier.get(2) != null && listeCheminsSwapAmodifier.get(3) != null;
    }

    private boolean ordreCollecteDepotCorrect(Long adresse2, Long adresse3){
        return true;
    }

    private boolean swapBenefique(Long adresse1, Long adresse2, Long adresse3){


        Integer longueurActuelle = longueurPartielleTournee(false,adresse1,adresse2,adresse3);
        Integer longueurSwap = longueurPartielleTournee(true,adresse1,adresse2,adresse3);

        if(longueurSwap<longueurActuelle){
            return true;
        }
        return false;
    }

    private Integer longueurPartielleTournee(boolean swap, Long adresse1, Long adresse2, Long adresse3){
        Integer longueurTournee = 0;

        LinkedList<CheminEntreEtape> listeCheminAModifier = new LinkedList<>();

        CheminEntreEtape chemin1to2=null;
        CheminEntreEtape chemin2to2a=null;
        CheminEntreEtape chemin3dto3=null;
        CheminEntreEtape chemin3to3a=null;

        CheminEntreEtape chemin1to3=null;
        CheminEntreEtape chemin3to2a=null;
        CheminEntreEtape chemin3dto2=null;
        CheminEntreEtape chemin2to3a=null;

        if(!swap){
            for(CheminEntreEtape cee : tournee.getListeChemins()){
                //L1
                if(cee.etapeDepart.getIdAdresse().equals(adresse1)){
                    longueurTournee+=cee.distance;
                    chemin1to2 = cee;
                }
                //L2
                if(cee.etapeDepart.getIdAdresse().equals(adresse2)){
                    longueurTournee+=cee.distance;
                    chemin2to2a = cee;
                }
                //L3
                if(cee.etapeArrivee.getIdAdresse().equals(adresse3)){
                    longueurTournee+=cee.distance;
                    chemin3dto3 = cee;
                }
                //L4
                if(cee.etapeDepart.getIdAdresse().equals(adresse3)){
                    longueurTournee+=cee.distance;
                    chemin3to3a = cee;
                }
            }
            listeCheminAModifier.push(chemin1to2);
            listeCheminAModifier.push(chemin2to2a);
            listeCheminAModifier.push(chemin3dto3);
            listeCheminAModifier.push(chemin3to3a);

            this.listeCheminsActuelsAmodifier = listeCheminAModifier;

        } else {
            //L1
            for(CheminEntreEtape cee : resultatDijkstra.get(adresse1)){
                if(cee.getEtapeArrivee().getIdAdresse().equals(adresse3)){
                    longueurTournee+=cee.distance;
                    chemin1to3 = cee;
                    break;
                }
            }
            //L2
            Long adresse2Arrivee = -1L;
            Long adresse3Depart = -1L;
            Long adresse3Arrivee = -1L;
            for(CheminEntreEtape cee : tournee.getListeChemins()) {
                if(cee.etapeDepart.getIdAdresse().equals(adresse2)){
                    adresse2Arrivee = cee.etapeArrivee.getIdAdresse();
                }
                if(cee.etapeArrivee.getIdAdresse().equals(adresse3)){
                    adresse3Depart = cee.etapeDepart.getIdAdresse();
                }
                if(cee.etapeDepart.getIdAdresse().equals(adresse3)){
                    adresse3Arrivee = cee.etapeArrivee.getIdAdresse();
                }
            }
            for(CheminEntreEtape cee : resultatDijkstra.get(adresse3)){
                if(cee.getEtapeArrivee().getIdAdresse().equals(adresse2Arrivee)){
                    longueurTournee+=cee.distance;
                    chemin3to2a = cee;
                    break;
                }
            }
            //L3
            for(CheminEntreEtape cee : resultatDijkstra.get(adresse3Depart)){
                if(cee.getEtapeArrivee().getIdAdresse().equals(adresse2)){
                    longueurTournee+=cee.distance;
                    chemin3dto2 = cee;
                    break;
                }
            }
            //L4
            for(CheminEntreEtape cee : resultatDijkstra.get(adresse2)){
                if(cee.getEtapeArrivee().getIdAdresse().equals(adresse3Arrivee)){
                    longueurTournee+=cee.distance;
                    chemin2to3a = cee;
                    break;
                }
            }

            listeCheminAModifier.push(chemin1to3);
            listeCheminAModifier.push(chemin3to2a);
            listeCheminAModifier.push(chemin3dto2);
            listeCheminAModifier.push(chemin2to3a);
            this.listeCheminsSwapAmodifier = listeCheminAModifier;

        }



        return longueurTournee;
    }
}
