package Algorithmie;

import Model.Adresse;
import Model.Carte;
import Model.CheminEntreEtape;
import Model.Tournee;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class TSP2 extends TemplateTSP {

    protected int cheminLePlusPetit;

    TSP2(Carte carte, Tournee tournee, HashMap<Long, HashMap<Long, CheminEntreEtape>> grapheCompletDesEtapes, int tempsLimite) {
        super(carte, tournee, grapheCompletDesEtapes, tempsLimite);

        cheminLePlusPetit = Integer.MAX_VALUE;
        for (Map.Entry<Long, HashMap<Long, CheminEntreEtape>> entry : grapheCompletDesEtapes.entrySet()) {
            for (Map.Entry<Long, CheminEntreEtape> entryBis : entry.getValue().entrySet()) {
                if (cheminLePlusPetit > entryBis.getValue().distance) {
                    cheminLePlusPetit = entryBis.getValue().distance;
                }
            }
        }
    }

    @Override
    protected int evaluation(Adresse adresseActuelle, List<Adresse> nonVisite) {
        return cheminLePlusPetit * nonVisite.size();
    }

    @Override
    protected Iterator<Adresse> iterateur(Adresse adresseActuelle, List<Adresse> nonVisite) {
        return new IterateurProximite(nonVisite, adresseActuelle, grapheCompletDesEtapes);
    }

}
