package Controleur;

//Credit : PlaCo by Christine Solnon (INSA Lyon)

import Exceptions.CommandeImpossibleException;

public interface Commande {

    /**
     * Execute the command this
     */
    void doCommand() throws CommandeImpossibleException;

    /**
     * Execute the reverse command of this
     */
    void undoCommand() throws CommandeImpossibleException;

}
