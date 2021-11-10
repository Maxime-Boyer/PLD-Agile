package Model;

import java.util.List;

public class CheminEntreEtape implements Comparable<CheminEntreEtape> {

    public Etape etapeDepart;
    public Etape etapeArrivee;
    public List<Segment> listeSegment;
    public Integer distance;

    /**
     * constructeur de la classe chemin entre étape qui correspond aux chemins entre deux étapes de la collecte
     * @param etapeDepart: Etape de départ
     * @param etapeArrivee: Etape d'arrivée
     * @param listeSegment: liste des segments entre les deux étapes
     * @param distance: distance en mètre entre les deuc étapes
     */
    public CheminEntreEtape(Etape etapeDepart, Etape etapeArrivee, List<Segment> listeSegment, Integer distance) {
        this.etapeDepart = etapeDepart;
        this.etapeArrivee = etapeArrivee;
        this.listeSegment = listeSegment;
        this.distance = distance;
    }

    /**
     * methode qui retourne l'étape de départ
     * @return
     */
    public Etape getEtapeDepart() {
        return etapeDepart;
    }

    /**
     * methode qui reourne l'étape d'arrivée
     * @return
     */
    public Etape getEtapeArrivee() {
        return etapeArrivee;
    }

    /**
     * methode qui retourne la liste des segments entre les étapes
     * @return
     */
    public List<Segment> getListeSegment() {
        return listeSegment;
    }

    /**
     * methode qui retourne la distance du chemin
     * @return
     */
    public Integer getDistance() {
        return distance;
    }

    /**
     * methode qui compare la distance entre le CheminEntreEtape et celui mis en paramètre
     * @param o: CheminEntreEtape à vérifier
     * @return
     */
    @Override
    public int compareTo(CheminEntreEtape o) {
        return this.getDistance() - o.getDistance();
    }
}
