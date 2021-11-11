package Controleur;

import Exceptions.CommandeImpossibleException;

public class InverseCommande implements Commande {
    private Commande cmd;

    /**
     * Créez la commande inverse de cmd (afin que la commande cmd.do corresponde à la commande this.undo, et vice-versa)
     * @param cmd la commande a inverser
     */
    public InverseCommande(Commande cmd){
        this.cmd = cmd;
    }

    /**
     * faire la commande
     * @throws CommandeImpossibleException
     */
    @Override
    public void faireCommande() throws CommandeImpossibleException {
        cmd.faireCommande();
    }

    /**
     * defaire la commande
     * @throws CommandeImpossibleException
     */
    @Override
    public void defaireCommande() throws CommandeImpossibleException {
        cmd.defaireCommande();
    }
}
