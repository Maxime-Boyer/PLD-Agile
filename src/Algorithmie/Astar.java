package Algorithmie;

import Model.*;
import javafx.util.Pair;

import java.sql.Timestamp;
import java.util.*;

public class Astar {

    // Contient les adresses associees a leur antecedent : HashMap<idEnfant, SegmentDeParentAEnfant>
    private HashMap<Long, Segment> parent;

    // cout[p] est une estimation de la distance entre la position p et la cible
    private HashMap<Long, Double> cout;

    // Contient les adresses blanches, associees a leur cout
    private HashMap<Long, Double> adressesBlanches;

    // Contient les adresses grises, associees a leur cout : Pair<Cout, idAdresse>
    //private PriorityQueue<Long, Double> adressesGrises;
    PriorityQueue<NoeudAdresse > adressesGrises;

    // Contient les adresses noir (déjà visitées)
    private HashSet<Long> adressesNoire;

    // La carte
    private Carte carte;

    // L'adresse de départ
    private Etape depart;

    // L'adresse d'arrivée'
    private Etape arrivee;

    public Astar(Carte carte, Etape depart, Etape arrivee){

        this.carte = carte;
        this.depart = depart;
        this.arrivee = arrivee;
        this.cout = new HashMap<>();
        this.adressesBlanches = new HashMap<>();
        adressesGrises = new PriorityQueue<>();
        adressesNoire = new HashSet<>();
        parent = new HashMap<>();
    }

    //Calcul l'heuristique avec la distance euclidienne
    //Nous utilisons la formule de Haversine to calculate Great-Circle distance, that is to say the shortest
    //distance between 2 points on a sphere
    public double calculHeuristique(Adresse adresse) {

        double degToRad = 0.01745329;
        double rayonTerre = 6371; // in kilometers

        //Convertion degré à radian
        double lat1 = adresse.getLatitude() * degToRad;
        double lat2 = arrivee.getLatitude() * degToRad;
        double lon1 = adresse.getLongitude() * degToRad;
        double lon2 = arrivee.getLongitude() * degToRad;

        // Haversine formula
        double dlon = lon2 - lon1;
        double dlat = lat2 - lat1;
        double a = Math.pow(Math.sin(dlat / 2), 2)
                + Math.cos(lat1) * Math.cos(lat2)
                * Math.pow(Math.sin(dlon / 2),2);

        double c = 2 * Math.asin(Math.sqrt(a));

        // Rayon de la terre of en m
        double r = 6371000;

        // Retourne la distance
        return(c * r);
    }

    public CheminEntreEtape executerAstar() {

        // On met le point de départ dans les maps grises
        cout.put(depart.getIdAdresse(), calculHeuristique(depart));
        System.out.println(adressesGrises);
        adressesGrises.offer( new NoeudAdresse(depart.getIdAdresse(), cout.get(depart.getIdAdresse())) );
        System.out.println(adressesGrises);

        while( !adressesGrises.isEmpty() ) {
            //Prend l'adresse de la liste grise ayant le cout min
            Adresse adresseActuelle = carte.obtenirAdresseParId(adressesGrises.peek().getIdAdresse()); //O(1)

            //Si on a atteint la destination alors on retourne le chemin trouve (etat actuel correspond à l'arrivee)
            if( adresseActuelle.getIdAdresse().equals(arrivee.getIdAdresse()) )
            {
                ArrayList<Segment> meilleurChemin = new ArrayList<>();
                //Tant que l'adresse actuelle est differente de l'adresse de depart
                int distance = 0;
                while( !adresseActuelle.getIdAdresse().equals(depart.getIdAdresse()) )
                {
                    //Ajoute le chemin pour aller du parent à l'adresse actuelle
                    Segment segmentVenantDuParent = parent.get(adresseActuelle.getIdAdresse());
                    meilleurChemin.add(0, segmentVenantDuParent);
                    distance += segmentVenantDuParent.getLongueur();
                    //L'adresse actuelle devient celle du parent
                    Adresse adressePrecedente = carte.obtenirAdresseParId( segmentVenantDuParent.getOrigine().getIdAdresse() );
                }
                return new CheminEntreEtape(depart, arrivee, meilleurChemin, distance);
            }

            //Passe l'adresse actuelle en visitée
            adressesNoire.add(adresseActuelle.getIdAdresse());
            System.out.println(adressesGrises.peek().getIdAdresse());
            adressesGrises.poll();
            System.out.println(adressesGrises);

            //Visite les voisins de l'adresse actuelle
            for (Segment segSortants  : adresseActuelle.getSegmentsSortants()) {
                Adresse voisin = segSortants.getDestination();

                // Il ne faut rien faire si le voisin est noir (deja visite)
                if( adressesNoire.contains(voisin) ) continue;
                // Si le voisin est blanc ou gris, il faut potentiellement mettre à jour la plus courte distance actuelle à ce voisin
                double nouveauCout = cout.get(adresseActuelle.getIdAdresse()) + segSortants.getLongueur() - calculHeuristique(adresseActuelle) + calculHeuristique(voisin);
                if( !cout.containsKey(voisin.getIdAdresse()) || ( cout.get(voisin.getIdAdresse()) > nouveauCout ) )
                {
                    cout.replace(voisin.getIdAdresse(), nouveauCout);
                    parent.put(voisin.getIdAdresse(), segSortants);
                    NoeudAdresse nouveauNoeudAdresse = new NoeudAdresse(voisin.getIdAdresse(), cout.get(voisin.getIdAdresse()));
                    System.out.println(nouveauNoeudAdresse);
                    adressesGrises.offer( nouveauNoeudAdresse );
                }
            }
        }
        return null;
    }
}
