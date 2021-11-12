package Controleur;

//Credit : PlaCo by Christine Solnon (INSA Lyon)

import Exceptions.CommandeImpossibleException;

import java.util.LinkedList;

/**
 * Permet de stocker la liste de commandes réalisés, ce qui permet d'effectuer les fonctionalités undo/redo
 */
public class ListeDeCommandes {
    private LinkedList<Commande> list;
    private int indexCourant;

    public ListeDeCommandes() {
        indexCourant = -1;
        list = new LinkedList<>();
    }

    /**
     * Ajoute la commande c à this
     *
     * @param c la commande à ajouter
     */
    public void ajouter(Commande c) throws CommandeImpossibleException {
        int i = indexCourant + 1;
        while (i < list.size()) {
            list.remove(i);
        }
        indexCourant++;
        list.add(indexCourant, c);
        c.faireCommande();
    }

    /**
     * Supprimer temporairement la dernière commande ajoutée (cette commande peut être réinsérée avec refaire)
     */
    public void defaire() throws CommandeImpossibleException {
        if (indexCourant >= 0) {
            Commande cde = list.get(indexCourant);
            indexCourant--;
            cde.defaireCommande();
        }
    }

    /**
     * Supprimer définitivement la dernière commande ajoutée (cette commande ne peut pas être réinsérée avec refaire)
     */
    public void annuler() throws CommandeImpossibleException {
        if (indexCourant >= 0) {
            Commande cde = list.get(indexCourant);
            list.remove(indexCourant);
            indexCourant--;
            cde.defaireCommande();
        }
    }

    /**
     * Réinsére la dernière commande supprimée par annuler
     */
    public void refaire() throws CommandeImpossibleException {
        if (indexCourant < list.size() - 1) {
            indexCourant++;
            Commande cde = list.get(indexCourant);
            cde.faireCommande();
        }
    }

    /**
     * Supprimer définitivement toutes les commandes de la liste
     */
    public void reinitialiser() {
        indexCourant = -1;
        list.clear();
    }

    public int getIndexCourant() {
        return indexCourant;
    }

    public LinkedList<Commande> getList() {
        return list;
    }
}
