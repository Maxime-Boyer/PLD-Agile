package Algorithmie;

import Model.Adresse;
import Model.CheminEntreEtape;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class IterateurSeq implements Iterator<Adresse> {

    private Adresse[] candidats;
    private int nbCandidats;

    public IterateurSeq(Collection<Adresse> nonVisite, Adresse adresseActuelle, HashMap<Long, HashMap<Long, CheminEntreEtape>> grapheCompletDesEtapes) {
        this.candidats = new Adresse[nonVisite.size()];
        for (Adresse a : nonVisite) {
            if (a.getIdAdresse() != adresseActuelle.getIdAdresse()) {
                candidats[nbCandidats++] = a;
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
