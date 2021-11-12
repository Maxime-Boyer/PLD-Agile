package Algorithmie;

import Model.Adresse;
import Model.Carte;
import Model.CheminEntreEtape;
import Model.Tournee;

import java.util.*;

public class TSP4 extends TemplateTSP {

    protected int distancePremierDecile;

    /**
     * Constructeur de TSP2
     * Utilise une évaluation retournant cheminLePlusCourt premier décile * nombre d'adresses non visitées, et IterateurProximite
     *
     * @param carte                  La carte
     * @param tournee                La liste des requetes souhaites
     * @param grapheCompletDesEtapes Le graphe complet des etapes
     * @param tempsLimite            Le temps limite de calcul du TSP
     */
    TSP4(Carte carte, Tournee tournee, HashMap<Long, HashMap<Long, CheminEntreEtape>> grapheCompletDesEtapes, int tempsLimite) {
        super(carte, tournee, grapheCompletDesEtapes, tempsLimite);

        distancePremierDecile = Integer.MAX_VALUE;
        LinkedList<Integer> distances = new LinkedList<>();
        for (Map.Entry<Long, HashMap<Long, CheminEntreEtape>> entry : grapheCompletDesEtapes.entrySet()) {
            for (Map.Entry<Long, CheminEntreEtape> entryBis : entry.getValue().entrySet()) {
                distances.add(entryBis.getValue().distance);
            }
        }
        distances.sort(Integer::compare);
        distancePremierDecile = distances.get(distances.size()/10);

    }

    @Override
    protected int evaluation(Adresse adresseActuelle, List<Adresse> nonVisite) {
        return distancePremierDecile * nonVisite.size();
    }

    @Override
    protected Iterator<Adresse> iterateur(Adresse adresseActuelle, List<Adresse> nonVisite) {
        return new IterateurProximite(nonVisite, adresseActuelle, grapheCompletDesEtapes);
    }

}
