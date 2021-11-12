package Algorithmie;

import Model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;

public abstract class TemplateAstar implements Astar {

    // Contient les adresses associees a leur antecedent : HashMap<idEnfant, SegmentDeParentAEnfant>
    private HashMap<Long, Segment> pi;

    // cout[p] est une estimation de la distance entre la position p et la cible
    private HashMap<Long, Double> d;

    // Contient les adresses grises, associees a leur cout : Pair<Cout, idAdresse>

    PriorityQueue<NoeudAdresse> filePrioriteAdressesGises;

    // Contient les adresses grises (en attente de visite) associée à leur cout.
    // Permet d'accéder en temps constant à l'objet NoeudAdresse.
    // Doit être mis à jour en même temps que filePrioriteAdressesGises (stocke le même pointeur)
    private HashMap<Long, NoeudAdresse> adressesGrises;

    // Contient les adresses noir (déjà visitées)
    private HashSet<Long> adressesNoire;

    // La carte
    private Carte carte;

    /**
     * Constructeur de TemplateAstar
     *
     * @param carte la carte à partir de laquelle sera calculé le chemin
     */
    public TemplateAstar(Carte carte) {
        this.carte = carte;
        this.d = new HashMap<>();
        filePrioriteAdressesGises = new PriorityQueue<>();
        adressesNoire = new HashSet<>();
        pi = new HashMap<>();
        adressesGrises = new HashMap<>();
    }

    /**
     * Calcul l'estimation du coût pour aller de l'adresse actuelle à l'arrivée
     *
     * @param adresse : adresse actuelle
     * @param arrivee : adresse de destionation
     * @return Cout calculé
     */
    protected abstract double calculHeuristique(Adresse adresse, Adresse arrivee);

    @Override
    public CheminEntreEtape chercherCheminEntreEtape(Etape depart, Etape arrivee) {

        //Vide les listes
        pi.clear();
        d.clear();
        filePrioriteAdressesGises.clear();
        adressesNoire.clear();
        adressesGrises.clear();

        // On met le point de départ dans les maps grises
        d.put(depart.getIdAdresse(), calculHeuristique(depart, arrivee));
        NoeudAdresse nouveauNoeudAdresse = new NoeudAdresse(depart.getIdAdresse(), d.get(depart.getIdAdresse()));
        filePrioriteAdressesGises.offer(nouveauNoeudAdresse);
        adressesGrises.put(nouveauNoeudAdresse.getIdAdresse(), nouveauNoeudAdresse);
        while (!filePrioriteAdressesGises.isEmpty()) {
            //Prend l'adresse de la liste grise ayant le cout min
            Adresse adresseActuelle = carte.obtenirAdresseParId(filePrioriteAdressesGises.peek().getIdAdresse()); //O(1)

            //Si on a atteint la destination alors on retourne le chemin trouve (etat actuel correspond à l'arrivee)
            if (adresseActuelle.getIdAdresse().equals(arrivee.getIdAdresse())) {
                ArrayList<Segment> meilleurChemin = new ArrayList<>();
                //Tant que l'adresse actuelle est differente de l'adresse de depart
                int distance = 0;
                while (!adresseActuelle.getIdAdresse().equals(depart.getIdAdresse())) {
                    //Ajoute le chemin pour aller du parent à l'adresse actuelle
                    Segment segmentVenantDuParent = pi.get(adresseActuelle.getIdAdresse());
                    meilleurChemin.add(0, segmentVenantDuParent);
                    distance += segmentVenantDuParent.getLongueur();
                    //L'adresse actuelle devient celle du parent
                    adresseActuelle = segmentVenantDuParent.getOrigine();
                }
                return new CheminEntreEtape(depart, arrivee, meilleurChemin, distance);
            }

            //Passe l'adresse actuelle en visitée
            adressesNoire.add(adresseActuelle.getIdAdresse());
            filePrioriteAdressesGises.poll();
            adressesGrises.remove(adresseActuelle.getIdAdresse());

            //Visite les voisins de l'adresse actuelle
            for (Segment segSortants : adresseActuelle.getSegmentsSortants()) {
                Adresse voisin = segSortants.getDestination();

                // Il ne faut rien faire si le voisin est noir (deja visite)
                if (adressesNoire.contains(voisin.getIdAdresse())) continue;
                // Si le voisin est blanc ou gris, il faut potentiellement mettre à jour la plus courte distance actuelle à ce voisin
                double nouveauCout = d.get(adresseActuelle.getIdAdresse()) + segSortants.getLongueur() - calculHeuristique(adresseActuelle, arrivee) + calculHeuristique(voisin, arrivee);
                if (!d.containsKey(voisin.getIdAdresse()) || (d.get(voisin.getIdAdresse()) > nouveauCout)) {
                    //Si cette adresse n'a pas de cout associe ajoute le cout, sinon le remplace
                    if (!d.containsKey(voisin.getIdAdresse())) {
                        d.put(voisin.getIdAdresse(), nouveauCout);
                        pi.put(voisin.getIdAdresse(), segSortants);
                    } else {
                        d.replace(voisin.getIdAdresse(), nouveauCout);
                        pi.replace(voisin.getIdAdresse(), segSortants);
                    }

                    //Si le voisin n'est pas déjà gris, l'jaoute à la file de priorité
                    if (!adressesGrises.containsKey(voisin.getIdAdresse())) {
                        NoeudAdresse na = new NoeudAdresse(voisin.getIdAdresse(), d.get(voisin.getIdAdresse()));
                        filePrioriteAdressesGises.offer(na);
                        adressesGrises.put(voisin.getIdAdresse(), na);

                        //Sinon met à jour le cout de ce noeud gris
                    } else {
                        adressesGrises.get(voisin.getIdAdresse()).setCout(d.get(voisin.getIdAdresse()));
                    }
                }
            }
        }
        return null;
    }

}
