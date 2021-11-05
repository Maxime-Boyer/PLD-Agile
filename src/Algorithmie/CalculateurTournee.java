package Algorithmie;

import Exceptions.AStarImpossibleException;
import Model.*;

import java.sql.Timestamp;
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
    public HashMap<Long, HashMap<Long, CheminEntreEtape>> calculerTournee () throws AStarImpossibleException{

        //long msBefore = System.currentTimeMillis();
        //HashMap<Long, HashMap<Long, CheminEntreEtape>> grapheCompletDesEtapes = calculerGrapheCompletDesEtapes(new Astar1(carte));
        //System.out.println("Temps execution Astar1 : " + (System.currentTimeMillis()-msBefore));
        //msBefore = System.currentTimeMillis();
        HashMap<Long, HashMap<Long, CheminEntreEtape>> grapheCompletDesEtapes = calculerGrapheCompletDesEtapes(new Astar2(carte));
        //System.out.println("Temps execution Astar2 : " + (System.currentTimeMillis()-msBefore));

        TSP2 tsp = new TSP2(carte,tournee,grapheCompletDesEtapes,20000);
        tsp.chercherSolution();
        //System.out.println("switch tsp");
        //TSP1 tspBis = new TSP1(carte,tournee,grapheCompletDesEtapes,20000);
        //tspBis.chercherSolution();
        //tsp.calculerOrdreEtapes();
        return grapheCompletDesEtapes;
    }



    /**
     * Calcul le graphe complet de l'ensemble des étapes
     * @return le graphe complet de l'ensemble des étapes
     */
    private HashMap<Long, HashMap<Long, CheminEntreEtape>> calculerGrapheCompletDesEtapes(Astar astar) throws AStarImpossibleException{
        //Astar1 astar = new Astar1(carte);

        // HashMap< idAresseDepart, HashMap<idAresseArrivee, CheminEntreEtape> >
        HashMap<Long, HashMap<Long, CheminEntreEtape>> grapheCompletDesEtapes = new HashMap<>();

        //Boucler pour construire le graphe complet
        for(int iDepart=0 ; iDepart<tournee.getListeRequetes().size()*2 + 1 ; iDepart++) {
            Adresse etapeDepart;
            if(iDepart == tournee.getListeRequetes().size()*2) {
                etapeDepart = tournee.getAdresseDepart();
            } else {
                if (iDepart % 2 == 0) {
                    etapeDepart = tournee.getListeRequetes().get(iDepart / 2).getEtapeCollecte();
                } else {
                    etapeDepart = tournee.getListeRequetes().get(iDepart / 2).getEtapeDepot();
                }
            }

            HashMap<Long, CheminEntreEtape> listeCheminEntreEtape = new HashMap<>();
            for(int arr=0 ; arr<tournee.getListeRequetes().size()*2 + 1 ; arr++) {
                if (arr != iDepart) {
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
                    //System.out.println("etapeDepart="+etapeDepart.getIdAdresse()+", etapeArrivee"+etapeArrivee.getIdAdresse());
                    CheminEntreEtape nouveauChemin = astar.chercherCheminEntreEtape(new Etape(etapeDepart.getLatitude(),etapeDepart.getLongitude(),etapeDepart.getIdAdresse(),0,new Timestamp(0)), new Etape(etapeArrivee.getLatitude(),etapeArrivee.getLongitude(),etapeArrivee.getIdAdresse(),0,new Timestamp(0)));
                    if(nouveauChemin == null){
                        throw new AStarImpossibleException("Les adresses "+etapeDepart.getIdAdresse()+" et "+etapeArrivee.getIdAdresse()+" renvoient un chemin null.");
                    }
                    listeCheminEntreEtape.put(etapeArrivee.getIdAdresse(), nouveauChemin);
                    //System.out.println("    longeurChemin="+nouveauChemin.getDistance());
                    //System.out.println("    listeCheminEntreEtape = " + listeCheminEntreEtape);
                }
            }

            grapheCompletDesEtapes.put(etapeDepart.getIdAdresse(), listeCheminEntreEtape);
        }


        return grapheCompletDesEtapes;
    }

    public Tournee getTournee(){
        return tournee;
    }



}
