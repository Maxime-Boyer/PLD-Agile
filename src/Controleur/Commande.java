package Controleur;

public interface Commande {

    /**
     * Execute the command this
     */
    void faireCommande();

    /**
     * Execute the reverse command of this
     */
    void defaireCommande();
}
