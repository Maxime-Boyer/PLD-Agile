package Algorithmie;

import Exceptions.AStarImpossibleException;
import Model.*;

import java.sql.Timestamp;
import java.time.LocalTime;
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
     * @return
     * @throws AStarImpossibleException
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

        ajouteHeureDePassage(tournee);

        //Passe la tournee à ordonne et notifie l'observer que l'objet tournée a été modifié
        tournee.setTourneeEstOrdonee(true);
        tournee.notifyObservers();

        return grapheCompletDesEtapes;
    }


    /**
     * Calcul le graphe complet de l'ensemble des étapes
     * @param astar
     * @return le graphe complet de l'ensemble des étapes
     * @throws AStarImpossibleException
     */
    public HashMap<Long, HashMap<Long, CheminEntreEtape>> calculerGrapheCompletDesEtapes(Astar astar) throws AStarImpossibleException{
        //Astar1 astar = new Astar1(carte);

        // HashMap< idAresseDepart, HashMap<idAresseArrivee, CheminEntreEtape> >
        HashMap<Long, HashMap<Long, CheminEntreEtape>> grapheCompletDesEtapes = new HashMap<>();

        //Boucler pour construire le graphe complet
        for(int iDepart=0 ; iDepart<tournee.getListeRequetes().size()*2 + 1 ; iDepart++) {
            Etape etapeDepart;
            if(iDepart == tournee.getListeRequetes().size()*2) {
                etapeDepart = new Etape(tournee.getAdresseDepart().getLatitude(),tournee.getAdresseDepart().getLongitude(),tournee.getAdresseDepart().getIdAdresse(),0,LocalTime.of(0,0,0,0));
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
                    Etape etapeArrivee;
                    if(arr == tournee.getListeRequetes().size()*2) {
                        etapeArrivee = new Etape(tournee.getAdresseDepart().getLatitude(),tournee.getAdresseDepart().getLongitude(),tournee.getAdresseDepart().getIdAdresse(),0,LocalTime.of(0,0,0,0));
                    } else {
                        if(arr%2==0) {
                            etapeArrivee = tournee.getListeRequetes().get(arr/2).getEtapeCollecte();
                        } else {
                            etapeArrivee = tournee.getListeRequetes().get(arr/2).getEtapeDepot();
                        }
                    }
                    //System.out.println("etapeDepart="+etapeDepart.getIdAdresse()+", etapeArrivee"+etapeArrivee.getIdAdresse());

                    CheminEntreEtape nouveauChemin = astar.chercherCheminEntreEtape(etapeDepart,etapeArrivee);
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

    private void ajouteHeureDePassage(Tournee tournee){
        int vitesse = 15; //15 km.h-1
        LocalTime heureActuelle = tournee.getDateDepart();
        for(CheminEntreEtape cee : tournee.getListeChemins()){
            cee.getEtapeDepart().setHeureDePassage(heureActuelle);
            heureActuelle = heureActuelle.plusSeconds(cee.getEtapeDepart().getDureeEtape() + ((cee.distance/(vitesse*1000))*60));
            cee.getEtapeArrivee().setHeureDePassage(heureActuelle);
        }
    }

    public Tournee getTournee(){
        return tournee;
    }




}
