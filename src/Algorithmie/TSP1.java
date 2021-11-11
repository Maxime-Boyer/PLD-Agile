package Algorithmie;

import Model.Adresse;
import Model.Carte;
import Model.CheminEntreEtape;
import Model.Tournee;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class TSP1 extends TemplateTSP {

    /**
     * Constructeur de TSP1
     *
     * @param carte                  La carte
     * @param tournee                La liste des requetes souhaites
     * @param grapheCompletDesEtapes Le graphe complet des etapes
     * @param tempsLimite            Le temps limite de calcul du TSP
     */
    TSP1(Carte carte, Tournee tournee, HashMap<Long, HashMap<Long, CheminEntreEtape>> grapheCompletDesEtapes, int tempsLimite) {
        super(carte, tournee, grapheCompletDesEtapes, tempsLimite);
    }

    @Override
    protected int evaluation(Adresse adresseActuelle, List<Adresse> nonVisite) {
        return 0;
    }

    @Override
    protected Iterator<Adresse> iterateur(Adresse adresseActuelle, List<Adresse> nonVisite) {
        return new IterateurSeq(nonVisite, adresseActuelle);
    }

}
