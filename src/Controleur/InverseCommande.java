package Controleur;

import Exceptions.CommandeImpossibleException;

public class InverseCommande implements Commande {
    private Commande cmd;

    /**
     * Create the command reverse to cmd (so that cmd.doCommand corresponds to this.undoCommand, and vice-versa)
     * @param cmd the command to reverse
     */
    public InverseCommande(Commande cmd){
        this.cmd = cmd;
    }

    @Override
    public void faireCommande() throws CommandeImpossibleException {
        cmd.faireCommande();
    }

    @Override
    public void defaireCommande() throws CommandeImpossibleException {
        cmd.defaireCommande();
    }
}
