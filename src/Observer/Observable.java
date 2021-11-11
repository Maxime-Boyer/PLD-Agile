package Observer;

//Credit : PlaCo by Christine Solnon (INSA Lyon)

import java.util.ArrayList;
import java.util.Collection;

public class Observable {
    private Collection<Observer> obs;

    /**
     * Constructeur d'observable
     */
    public Observable(){
        obs = new ArrayList<Observer>();
    }

    /**
     * méthode qui ajoute un observeur à la collection d'observeurs
     * @param o: observeur à ajouté à la liste
     */
    public void addObserver(Observer o){
        if (!obs.contains(o)) obs.add(o);
    }
    /**
     * méthode qui notifie les observeurs
     */
    public void notifyObservers(Object arg){
        for (Observer o : obs)
            o.update(this, arg);
    }

    /**
     * méthode qui notifie les observeurs
     */
    public void notifyObservers(){
        notifyObservers(null);
    }
}
