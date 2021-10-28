package Algorithmie;

import Model.*;

import java.util.HashMap;

public class CalculateurTournee {

    private Carte carte;
    private Tournee tournee;

    /**
     * Constructeur de CalculateurTournee
     * @param carte : la carte
     * @param tournee : la tournée souhaitée
     */
    public CalculateurTournee(Carte carte, Tournee tournee){

        //Recuperation des informations
        this.carte = carte;
        this.tournee = tournee;
    }

    /**
     * Calcul la tournée passant par l'ensemble des étapes
     */
    public void calculerTournee (){
        //Appel calculerGrapheCompletDesEtapes
        //Appel TSP
    }



    /**
     * Calcul le graphe complet de l'ensemble des étapes
     * @return le graphe complet de l'ensemble des étapes
     */
    private HashMap<Long, HashMap<Long, CheminEntreEtape>> calculerGrapheCompletDesEtapes(){
        Astar1 astar = new Astar1(carte);

        // HashMap< idAresseDepart, HashMap<idAresseArrivee, CheminEntreEtape> >
        HashMap<Long, HashMap<Long, CheminEntreEtape>> grapheCompletDesEtapes = new HashMap<>();

        //Boucler pour construire le graphe complet
        for(int depart=0 ; depart<tournee.getListeRequetes().size()*2 + 1 ; depart++) {
            Adresse etapeDepart;
            if(depart == tournee.getListeRequetes().size()*2) {
                etapeDepart = tournee.getAdresseDepart();
            } else {
                if (depart % 2 == 0) {
                    etapeDepart = tournee.getListeRequetes().get(depart / 2).getEtapeCollecte();
                } else {
                    etapeDepart = tournee.getListeRequetes().get(depart / 2).getEtapeDepot();
                }
            }

            HashMap<Long, CheminEntreEtape> listeCheminEntreEtape = new HashMap<>();
            for(int arr=0 ; arr<tournee.getListeRequetes().size()*2 + 1 ; arr++) {
                if (arr != depart) {
                    Adresse etapeArrivee;
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
                    CheminEntreEtape nouveauChemin = astar.chercherCheminEntreEtape(etapeDepart, etapeArrivee);
                    listeCheminEntreEtape.put(etapeArrivee.getIdAdresse(), nouveauChemin);
                    System.out.println("    longeurChemin="+nouveauChemin.getDistance());
                    System.out.println("    listeCheminEntreEtape = " + listeCheminEntreEtape);
                }
            }

            grapheCompletDesEtapes.put(etapeDepart.getIdAdresse(), listeCheminEntreEtape);
        }


        return grapheCompletDesEtapes;
    }



}
