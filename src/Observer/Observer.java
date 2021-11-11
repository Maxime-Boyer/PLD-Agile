package Observer;

//Credit : PlaCo by Christine Solnon (INSA Lyon)

public interface Observer {
    /**
     * Lorsqu'un objet Observable est mis à jour, il appelle la méthode update() de chacun de ses Observers pour le notifier. Il est modifié.
     *
     * @param observed obesrvable qui est observé
     * @param arg argument passé à l'observeur
     */
    public void update(Observable observed, Object arg);
}
