package Controleur;

//Credit : PlaCo by Christine Solnon (INSA Lyon)

import Exceptions.CommandeImpossibleException;

/**
 * Permet de mettre en place le design pattern command utile pour le undo/redo
 */
public interface Commande {

    /**
     * Execute la commande
     */
    void faireCommande() throws CommandeImpossibleException;

    /**
     * Execute l'inverse de la commande
     */
    void defaireCommande() throws CommandeImpossibleException;

}
