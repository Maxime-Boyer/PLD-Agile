package Algorithmie;

import Model.Adresse;
import Model.Carte;
import Model.CheminEntreEtape;
import Model.Tournee;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class TSP3 extends TemplateTSP {

    /**
     * Constructeur de TSP3
     * Utilise une évaluation retournant cheminLePlusCourt parmis les chemins restant * nombre d'adresses non visitées, et IterateurProximite.
     *
     * @param carte                  La carte
     * @param tournee                La liste des requetes souhaites
     * @param grapheCompletDesEtapes Le graphe complet des etapes
     * @param tempsLimite            Le temps limite de calcul du TSP
     */
    TSP3(Carte carte, Tournee tournee, HashMap<Long, HashMap<Long, CheminEntreEtape>> grapheCompletDesEtapes, int tempsLimite) {
        super(carte, tournee, grapheCompletDesEtapes, tempsLimite);
    }

    @Override
    protected int evaluation(Adresse adresseActuelle, List<Adresse> nonVisite) {
        int cheminLePlusPetit = Integer.MAX_VALUE;
        for (Map.Entry<Long, HashMap<Long, CheminEntreEtape>> entry : grapheCompletDesEtapes.entrySet()) {
            for (Map.Entry<Long, CheminEntreEtape> entryBis : entry.getValue().entrySet()) {
                if (cheminLePlusPetit > entryBis.getValue().distance && nonVisite.contains(entryBis.getValue().getEtapeDepart()) && nonVisite.contains(entryBis.getValue().getEtapeArrivee())) {
                    cheminLePlusPetit = entryBis.getValue().distance;
                }
            }
        }
        return cheminLePlusPetit * nonVisite.size();
    }

    @Override
    protected Iterator<Adresse> iterateur(Adresse adresseActuelle, List<Adresse> nonVisite) {
        return new IterateurProximite(nonVisite, adresseActuelle, grapheCompletDesEtapes);
    }

}
