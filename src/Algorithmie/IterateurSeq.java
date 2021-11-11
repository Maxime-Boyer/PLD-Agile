package Algorithmie;

import Model.Adresse;

import java.util.Collection;
import java.util.Iterator;

public class IterateurSeq implements Iterator<Adresse> {

    private Adresse[] candidats;
    private int nbCandidats;

    /**
     * //TODO: Ajouter description
     *
     * @param nonVisite       Addresses non visitées
     * @param adresseActuelle Addresse actuelle
     */
    public IterateurSeq(Collection<Adresse> nonVisite, Adresse adresseActuelle) {
        this.candidats = new Adresse[nonVisite.size()];
        for (Adresse a : nonVisite) {
            if (!a.getIdAdresse().equals(adresseActuelle.getIdAdresse())) {
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
