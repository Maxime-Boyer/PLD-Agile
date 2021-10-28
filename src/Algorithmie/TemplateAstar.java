package Algorithmie;

import Model.Adresse;
import Model.Carte;
import Model.CheminEntreEtape;
import Model.Segment;

import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;

public abstract class TemplateAstar implements Astar {

    private Carte carte;

    // Contient les adresses associees a leur antecedent : HashMap<idEnfant, SegmentDeParentAEnfant>
    private HashMap<Long, Segment> parent;

    // cout[p] est une estimation de la distance entre la position p et la cible
    private HashMap<Long, Double> cout;

    // Contient les adresses grises, associees a leur cout : Pair<Cout, idAdresse>
    //private PriorityQueue<Long, Double> adressesGrises;
    PriorityQueue<NoeudAdresse> adressesGrises;

    // Contient les adresses noir (déjà visitées)
    private HashSet<Long> adressesNoire;

    public TemplateAstar(Carte carte) {
        this.carte = carte;
    }

    /**
     * Calcul l'estimation du coût pour aller de l'adresse actuelle à l'arrivée
     * @param adresse : adresse actuelle
     * @param arrivee : adresse de destionation
     * @return Cout calculé
     */
    protected abstract double calculHeuristique( Adresse adresse, Adresse arrivee );

    //TODO : implémenter
    @Override
    public CheminEntreEtape chercherCheminEntreEtape(Adresse depart, Adresse arrivee) {

        //TODO : vider les listes

        // On met le point de départ dans les maps grises
        adressesGrises.offer(new NoeudAdresse(depart.getIdAdresse(), calculHeuristique(depart, arrivee)));

        while (!adressesGrises.isEmpty()) {
            //Prend l'adresse de la liste grise ayant le cout min
            Adresse adresseActuelle = carte.obtenirAdresseParId(adressesGrises.peek().getIdAdresse());

            //Si on a atteint la destination alors on retourne le chemin trouve (etat actuel correspond à l'arrivee)
            if (adresseActuelle.getIdAdresse().equals(arrivee.getIdAdresse())) {
                //TODO : retracer le chemin inverse
            }

            //Visite les voisins de l'adresse actuelle
            for (Segment segSortants : adresseActuelle.getSegmentsSortants()) {
                Adresse voisin = segSortants.getDestination();
                // Il ne faut rien faire si le voisin est noir (deja visite)
                if (! adressesNoire.contains(voisin.getIdAdresse()))
                    //TODO faire relacher ////////////////////
                    relacher();
                if ()
            }



        }

        return null;
    }

    //TODO : implémenter
    private void relacher(  ){

    }
}
