package Algorithmie;

/**
 * Stocke un noeud qui représente une adresse avec son cout associe pour le calcul Astar
 */
public class NoeudAdresse implements Comparable<NoeudAdresse> {
    public double cout;
    public Long idAdresse;

    /**
     * Constructeur de NoeudAdresse
     *
     * @param idAdresse L'id de l'adresse du noeud
     * @param cout      Le cout associe au noeud pour Astar
     */
    public NoeudAdresse(Long idAdresse, double cout) {
        this.idAdresse = idAdresse;
        this.cout = cout;
    }

    /**
     * Permet de savoir si deux noeuds sont égaux
     *
     * @param vertex Le noeud qui doit être comparé
     * @return Retourne un booléen indiquant si deux noeuds sont égaux
     */
    public boolean equals(NoeudAdresse vertex) {
        return vertex.cout == cout && vertex.idAdresse.equals(idAdresse);
    }

    @Override
    public int compareTo(NoeudAdresse other) {
        return Double.compare(this.cout, other.cout);
    }

    /**
     * Modifie la valeur du cout
     *
     * @param cout La nouvelle valeur du cout
     */
    public void setCout(double cout) {
        this.cout = cout;
    }

    /**
     * Modifie la valeur de l'id de l'adresse associée au noeud
     *
     * @return La nouvelle valeur du noeud
     */
    public Long getIdAdresse() {
        return idAdresse;
    }

    @Override
    public String toString() {
        return "NoeudAdresse{" +
                "cout=" + cout +
                ", idAdresse=" + idAdresse +
                '}';
    }
}
