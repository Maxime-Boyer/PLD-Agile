package Algorithmie;

import Model.*;
import javafx.util.Pair;

import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class DijkstraBis {

    private HashMap<Long, Segment> pi;

    private Map<Long,Adresse> listeBlanc;
    private HashMap<Long, Double> mapCoutGris;
    private HashMap<Long, Double> mapCoutNoir;
    private Long depart;
    private Carte carte;
    private Tournee tournee;

    private HashMap<Long,LinkedList<CheminEntreEtape>> resultatDijkstra;


    public DijkstraBis(Carte carte, Tournee tournee){

        this.tournee = tournee;
        this.carte = carte;
        this.listeBlanc = new HashMap<>();
        mapCoutGris = new HashMap<>();
        mapCoutNoir = new HashMap<>();
        pi = new HashMap<>();
        resultatDijkstra = new HashMap<>();

        //TODO: Arreter plus tot le dijkstra

    }


    public HashMap<Long,LinkedList<CheminEntreEtape>> calculerChemins(){


        //dijkstra pour l'adresse de depart
        this.depart = tournee.getEtapeDepart().getIdAdresse();
        listeBlanc.clear();
        for(Map.Entry<Long, Adresse> entry : carte.getListeAdresses().entrySet()){
            this.listeBlanc.put(entry.getKey(),entry.getValue());
        }
        this.listeBlanc.remove(depart);
        mapCoutGris.clear();
        this.mapCoutGris.put(depart,0.0);
        mapCoutNoir.clear();
        pi.clear();
        //execution dijkstra
        resultatDijkstra.put(depart,executerDijkstra());



        //Pour chaque future etape
        //Il y a deux etapes par requete

        for(int i=0 ; i<tournee.getListeRequetes().size()*2 ; i++) {

            //Determination du depart
            if(i%2==0){
                this.depart = tournee.getListeRequetes().get(i/2).getEtapeCollecte().getIdAdresse();
            } else {
                this.depart = tournee.getListeRequetes().get(i/2).getEtapeDepot().getIdAdresse();
            }
            System.out.println(carte.obtenirAdresseParId(depart));
            //initialisation dijkstra
            //this.listeBlanc = carte.getListeAdresses();
            listeBlanc.clear();
            for(Map.Entry<Long, Adresse> entry : carte.getListeAdresses().entrySet()){
                this.listeBlanc.put(entry.getKey(),entry.getValue());
            }
            this.listeBlanc.remove(depart);

            mapCoutGris.clear();
            this.mapCoutGris.put(depart,0.0);

            mapCoutNoir.clear();

            pi.clear();

            //execution dijkstra
            resultatDijkstra.put(depart,executerDijkstra());

        }

        return resultatDijkstra;

    }


    public LinkedList<CheminEntreEtape> executerDijkstra(){

        //tant qu'il existe un sommet gris
        int compteur = 0;
        while (!mapCoutGris.isEmpty()) {

            //On recupere la sommet gris tel que son cout est minimal
            Pair<Long, Double> pairCoutMin = coutMinimum((mapCoutGris));

            //Pour chaque successeur...

            //System.out.println("before");
            /*if(pairCoutMin.getKey().getIdAdresse() == 208769039){
                System.out.println("la");
            }*/
            //System.out.println(pairCoutMin.getKey().getClass());
            boolean segmentTrouve = false;
            for(Segment segment : carte.getListeSegments()){
                //if(segment.getOrigine().getIdAdresse() == 208769039){
                //    System.out.println("ici");
                //}
                if(segment.getOrigine().getIdAdresse().intValue() == pairCoutMin.getKey().intValue()){

                    //System.out.println("here");

                    //si blanc ou gris
                    if(!mapCoutNoir.containsKey(segment.getDestination().getIdAdresse())){
                        segmentTrouve = true;

                        //relacher + colorier
                        relacher(segment, pairCoutMin.getValue());

                    }
                }
            }
            if(!segmentTrouve){
                mapCoutGris.remove(pairCoutMin.getKey());
                mapCoutNoir.put(pairCoutMin.getKey(),pairCoutMin.getValue());
            }
        }



        //Mise en forme du resultat
        LinkedList<CheminEntreEtape> listeCheminEntreEtape = new LinkedList<>();


        for(int i=0 ; i<tournee.getListeRequetes().size()*2 ; i++) {
            //On recupere toutes les arrivees que l'on souhaite (!= de depart)
            LinkedList<Segment> listeSegment = new LinkedList<>();
            Long arrivee;
            Integer distance;
            if(i%2==0){
                arrivee = tournee.getListeRequetes().get(i/2).getEtapeCollecte().getIdAdresse();
            } else {
                arrivee = tournee.getListeRequetes().get(i/2).getEtapeDepot().getIdAdresse();
            }
            if(arrivee != depart){

                //todo: Tout mettre a Double!
                distance = mapCoutNoir.get(arrivee).intValue();

                Long adresseActuelle = arrivee;
                Segment segmentActuel = pi.get(adresseActuelle);
                while(adresseActuelle.intValue() != depart.intValue()){
                    listeSegment.add(segmentActuel);
                    adresseActuelle = segmentActuel.getOrigine().getIdAdresse();
                    segmentActuel = pi.get(adresseActuelle);
                }
                if(depart!=null && arrivee!=null && distance!=0 && listeSegment.size()!=0){
                    //todo: changer constructeur Etape, ou modifier CheminEntreEtape
                    //todo: changer duree etape

                    listeCheminEntreEtape.add(new CheminEntreEtape(new Etape(carte.obtenirAdresseParId(depart).getLatitude(),carte.obtenirAdresseParId(depart).getLongitude(),carte.obtenirAdresseParId(depart).getIdAdresse(),0, LocalTime.of(0,0,0,0)),new Etape(carte.obtenirAdresseParId(arrivee).getLatitude(),carte.obtenirAdresseParId(arrivee).getLongitude(),carte.obtenirAdresseParId(arrivee).getIdAdresse(),0,LocalTime.of(0,0,0,0)),listeSegment,distance));

                }
            }
        }

        return listeCheminEntreEtape;
    }


    public void relacher(Segment segment, Double cout){

        if(mapCoutGris.containsKey(segment.getDestination().getIdAdresse())){
            //si le cout actuel est superieur au nouveau cout
            if(mapCoutGris.get(segment.getDestination().getIdAdresse()) > cout + segment.getLongueur()){
                //on modifie le cout
                mapCoutGris.put(segment.getDestination().getIdAdresse(),cout + segment.getLongueur());
                //On modifie pi
                pi.remove(segment.getDestination().getIdAdresse());
                pi.put(segment.getDestination().getIdAdresse(),segment);
            }
            //si elle est blanche
        } else {
            //on set le cout (+ met dans gris)
            mapCoutGris.put(segment.getDestination().getIdAdresse(),cout + segment.getLongueur());
            //on enleve de blanc
            listeBlanc.remove(segment.getDestination().getIdAdresse());
            //On modifie pi
            pi.remove(segment.getDestination().getIdAdresse());
            pi.put(segment.getDestination().getIdAdresse(),segment);
        }

        //on met l'adresse en noir
        mapCoutGris.remove(segment.getOrigine().getIdAdresse());
        mapCoutNoir.put(segment.getOrigine().getIdAdresse(),cout);

    }

    public Pair<Long, Double> coutMinimum(HashMap<Long,Double> hm){

        //On met dans coutMin la valeur max pour un double (si c'est pas assez grand cette ligne ne sera pas le seul probleme)
        Double coutMin=Double.MAX_VALUE;
        //HashMap d'entree non vide, on peut mettre 0 de base
        Long adresseMin=0L;

        for(Map.Entry<Long, Double> entry : hm.entrySet()){
            if(coutMin > entry.getValue()){
                coutMin = entry.getValue();
                adresseMin = entry.getKey();
            }
        }

        //return
        Pair<Long, Double> pair = new Pair<>(adresseMin,coutMin);
        return pair;

    }
}
