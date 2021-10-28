package Algorithmie;

public class NoeudAdresse implements Comparable<NoeudAdresse> {
    public double cout;
    public Long idAdresse;

    public NoeudAdresse(Long idAdresse, double cout) {
        this.idAdresse = idAdresse;
        this.cout = cout;
    }

    public boolean equals(Object obj) {
        NoeudAdresse vertex = (NoeudAdresse) obj;
        return vertex.cout == cout && vertex.idAdresse == idAdresse;
    }

    @Override
    public int compareTo(NoeudAdresse other) {
        if (this.cout == other.cout)
            return 0;
        else if (this.cout > other.cout)
            return 0;
        else
            return -1;
    }

    public double getCout() {
        return cout;
    }

    public void setCout(double cout) {
        this.cout = cout;
    }

    public Long getIdAdresse() {
        return idAdresse;
    }

    public void setIdAdresse(Long idAdresse) {
        this.idAdresse = idAdresse;
    }

    @Override
    public String toString() {
        return "NoeudAdresse{" +
                "cout=" + cout +
                ", idAdresse=" + idAdresse +
                '}';
    }
}
