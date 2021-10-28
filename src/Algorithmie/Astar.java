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

    // Contient les adresses grises, associees a leur cout : Pair<Cout, idAdresse>
    //private PriorityQueue<Long, Double> filePrioriteAdressesGises;
    PriorityQueue<NoeudAdresse> filePrioriteAdressesGises;

    // Contient les adresses grises (en attente de visite) associée à leur cout.
    // Permet d'accéder en temps constant à l'objet NoeudAdresse.
    // Doit être mis à jour en même temps que filePrioriteAdressesGises (stocke le même pointeur)
    private HashMap<Long, NoeudAdresse> adressesGrises;

    // Contient les adresses noir (déjà visitées)
    private HashSet<Long> adressesNoire;

    // La carte
    private Carte carte;

    public Astar(Carte carte) {

        this.carte = carte;
        this.cout = new HashMap<>();
        filePrioriteAdressesGises = new PriorityQueue<>();
        adressesNoire = new HashSet<>();
        parent = new HashMap<>();
        adressesGrises = new HashMap<>();
    }

    //Calcul l'heuristique avec la distance euclidienne
    //Nous utilisons la formule de Haversine to calculate Great-Circle distance, that is to say the shortest
    //distance between 2 points on a sphere
    public double calculHeuristique(Adresse adresse, Etape arrivee) {

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
                * Math.pow(Math.sin(dlon / 2), 2);

        double c = 2 * Math.asin(Math.sqrt(a));

        // Rayon de la terre of en m
        double r = 6371000;

        // Retourne la distance
        return (c * r);
    }

    public CheminEntreEtape executerAstar(Etape depart, Etape arrivee) {

        //Vide les listes
        parent.clear();
        cout.clear();
        filePrioriteAdressesGises.clear();
        adressesNoire.clear();
        adressesGrises.clear();

        // On met le point de départ dans les maps grises
        cout.put(depart.getIdAdresse(), calculHeuristique(depart, arrivee));
        NoeudAdresse nouveauNoeudAdresse = new NoeudAdresse(depart.getIdAdresse(), cout.get(depart.getIdAdresse()));
        filePrioriteAdressesGises.offer(nouveauNoeudAdresse);
        adressesGrises.put(nouveauNoeudAdresse.getIdAdresse(), nouveauNoeudAdresse);
        int nbLoop = 0;//TODO : delete this
        while (!filePrioriteAdressesGises.isEmpty()) {
            //System.out.println("Loop " + nbLoop);

            //Prend l'adresse de la liste grise ayant le cout min
            Adresse adresseActuelle = carte.obtenirAdresseParId(filePrioriteAdressesGises.peek().getIdAdresse()); //O(1)

            //System.out.println("    adresseActuelle="+adresseActuelle);
            //Si on a atteint la destination alors on retourne le chemin trouve (etat actuel correspond à l'arrivee)
            if (adresseActuelle.getIdAdresse().equals(arrivee.getIdAdresse())) {
                //System.out.println("    destination atteinte");
                ArrayList<Segment> meilleurChemin = new ArrayList<>();
                //Tant que l'adresse actuelle est differente de l'adresse de depart
                int distance = 0;
                while (!adresseActuelle.getIdAdresse().equals(depart.getIdAdresse())) {
                    //System.out.println("        adresseActuelle="+adresseActuelle);
                    //Ajoute le chemin pour aller du parent à l'adresse actuelle
                    Segment segmentVenantDuParent = parent.get(adresseActuelle.getIdAdresse());
                    meilleurChemin.add(0, segmentVenantDuParent);
                    distance += segmentVenantDuParent.getLongueur();
                    //L'adresse actuelle devient celle du parent
                    adresseActuelle = segmentVenantDuParent.getOrigine();
                    //System.out.println("        adresseParent="+adresseActuelle);
                }
                return new CheminEntreEtape(depart, arrivee, meilleurChemin, distance);
            }

            //Passe l'adresse actuelle en visitée
            adressesNoire.add(adresseActuelle.getIdAdresse());
            filePrioriteAdressesGises.poll();
            adressesGrises.remove(adresseActuelle.getIdAdresse());
            //System.out.println("    filePrioriteAdressesGises="+filePrioriteAdressesGises);

            //Visite les voisins de l'adresse actuelle
            for (Segment segSortants : adresseActuelle.getSegmentsSortants()) {
                Adresse voisin = segSortants.getDestination();

                // Il ne faut rien faire si le voisin est noir (deja visite)
                if (adressesNoire.contains(voisin.getIdAdresse())) continue;
                // Si le voisin est blanc ou gris, il faut potentiellement mettre à jour la plus courte distance actuelle à ce voisin
                double nouveauCout = cout.get(adresseActuelle.getIdAdresse()) + segSortants.getLongueur() - calculHeuristique(adresseActuelle, arrivee) + calculHeuristique(voisin, arrivee);
                if (!cout.containsKey(voisin.getIdAdresse()) || (cout.get(voisin.getIdAdresse()) > nouveauCout)) {
                    //Si cette adresse n'a pas de cout associe ajoute le cout, sinon le remplace
                    if (!cout.containsKey(voisin.getIdAdresse()))
                        cout.put(voisin.getIdAdresse(), nouveauCout);
                    else
                        cout.replace(voisin.getIdAdresse(), nouveauCout);
                    parent.put(voisin.getIdAdresse(), segSortants);
                    //Si le voisin n'est pas déjà gris, l'jaoute à la file de priorité
                    if (adressesGrises.get(voisin.getIdAdresse())== null)
                        filePrioriteAdressesGises.offer(new NoeudAdresse(voisin.getIdAdresse(), cout.get(voisin.getIdAdresse())));
                    //Sinon met à jour le cout de ce noeud gris
                    else
                        adressesGrises.get(voisin.getIdAdresse()).setCout(cout.get(voisin.getIdAdresse()));
                }
            }
            nbLoop++;
        }
        return null;
    }
}
