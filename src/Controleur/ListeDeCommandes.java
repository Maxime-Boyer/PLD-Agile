package Controleur;

//Credit : PlaCo by Christine Solnon (INSA Lyon)

import Exceptions.CommandeImpossibleException;

import java.util.LinkedList;

public class ListeDeCommandes {
    private LinkedList<Commande> list;
    private int currentIndex;

    public ListeDeCommandes(){
        currentIndex = -1;
        list = new LinkedList<>();
    }

    /**
     * Add command c to this, remove all the following commands if the current command is not the most recent one
     * @param c the command to add
     */
    public void add(Commande c) throws CommandeImpossibleException {
        int i = currentIndex+1;
        while(i<list.size()){
            list.remove(i);
        }
        currentIndex++;
        list.add(currentIndex, c);
        c.doCommand();
    }

    /**
     * Temporary remove the last added command (this command may be reinserted again with redo)
     */
    public void undo() throws CommandeImpossibleException {
        if (currentIndex >= 0){
            Commande cde = list.get(currentIndex);
            currentIndex--;
            cde.undoCommand();
        }
    }

    /**
     * Permanently remove the last added command (this command cannot be reinserted again with redo)
     */
    public void cancel() throws CommandeImpossibleException {
        if (currentIndex >= 0){
            Commande cde = list.get(currentIndex);
            list.remove(currentIndex);
            currentIndex--;
            cde.undoCommand();
        }
    }

    /**
     * Reinsert the last command removed by undo
     */
    public void redo() throws CommandeImpossibleException {
        if (currentIndex < list.size()-1){
            currentIndex++;
            Commande cde = list.get(currentIndex);
            cde.doCommand();
        }
    }

    /**
     * Permanently remove all commands from the list
     */
    public void reset(){
        currentIndex = -1;
        list.clear();
    }
}
