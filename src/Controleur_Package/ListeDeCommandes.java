package Controleur_Package;

import java.util.LinkedList;

public class ListeDeCommandes {
    private LinkedList<Commande> list;
    private int indexCourant;

    public ListeDeCommandes(){
        indexCourant = -1;
        list = new LinkedList<Commande>();
    }

    /**
     * Add command c to this
     * @param c the command to add
     */
    public void ajouter(Commande c){
        int i = indexCourant+1;
        while(i<list.size()){
            list.remove(i);
        }
        indexCourant++;
        list.add(indexCourant, c);
        c.faireCommande();
    }

    /**
     * Temporary remove the last added command (this command may be reinserted again with redo)
     */
    public void defaire(){
        if (indexCourant >= 0){
            Commande cde = list.get(indexCourant);
            indexCourant--;
            cde.defaireCommande();
        }
    }

    /**
     * Permanently remove the last added command (this command cannot be reinserted again with redo)
     */
    public void annuler(){
        if (indexCourant >= 0){
            Commande cde = list.get(indexCourant);
            list.remove(indexCourant);
            indexCourant--;
            cde.defaireCommande();
        }
    }

    /**
     * Reinsert the last command removed by undo
     */
    public void refaire(){
        if (indexCourant < list.size()-1){
            indexCourant++;
            Commande cde = list.get(indexCourant);
            cde.faireCommande();
        }
    }

    /**
     * Permanently remove all commands from the list
     */
    public void reinitialiser(){
        indexCourant = -1;
        list.clear();
    }
}
