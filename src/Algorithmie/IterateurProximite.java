package Algorithmie;

import Model.Adresse;
import Model.CheminEntreEtape;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class IterateurProximite implements Iterator<Adresse> {

    private Adresse[] candidats;
    private int nbCandidats;

    /**
     * //TODO: Ajouter description
     *
     * @param nonVisite              Addresses non visitées
     * @param adresseActuelle        Addresse actuelle
     * @param grapheCompletDesEtapes Le graphe complet des étapes
     */
    public IterateurProximite(Collection<Adresse> nonVisite, Adresse adresseActuelle, HashMap<Long, HashMap<Long, CheminEntreEtape>> grapheCompletDesEtapes) {
        this.candidats = new Adresse[nonVisite.size()];
        for (Adresse a : nonVisite) {
            if (!a.getIdAdresse().equals(adresseActuelle.getIdAdresse())) {
                candidats[nbCandidats++] = a;
            }
        }

        boolean tableauTrie = false;
        while (!tableauTrie) {
            tableauTrie = true;
            for (int i = 0; i < nbCandidats - 1; i++) {
                if (grapheCompletDesEtapes.get(adresseActuelle.getIdAdresse()).get(candidats[i].getIdAdresse()).getDistance() < grapheCompletDesEtapes.get(adresseActuelle.getIdAdresse()).get(candidats[i + 1].getIdAdresse()).getDistance()) {
                    Adresse temporaire = candidats[i];
                    candidats[i] = candidats[i + 1];
                    candidats[i + 1] = temporaire;
                    tableauTrie = false;
                }
            }
        }
    }

    @Override
    public boolean hasNext() {
        return nbCandidats > 0;
    }

    @Override
    public Adresse next() {
        nbCandidats--;
        return candidats[nbCandidats];
    }

    @Override
    public void remove() {
    }

}
