package Controleur;

//Credit : PlaCo by Christine Solnon (INSA Lyon)

import Exceptions.CommandeImpossibleException;

/**
 * Permet de mettre en place le design pattern command utile pour le undo/redo
 */
public interface Commande {

    /**
     * Execute the command this
     */
    void faireCommande() throws CommandeImpossibleException;

    /**
     * Execute the reverse command of this
     */
    void defaireCommande() throws CommandeImpossibleException;

}
