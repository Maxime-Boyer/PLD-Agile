package Model;

import Observer.Observable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Carte extends Observable {

    private Map<Long,Adresse> listeAdresses;
    private List<Segment> listeSegments;
    private String nomCarte;

    /**
     * constructeur de la carte
     * @param nomCarte
     */
    public Carte(String nomCarte) {
        this.nomCarte = nomCarte;
        listeAdresses = new HashMap<Long,Adresse>();
        listeSegments = new ArrayList<Segment>();
    }

    /**
     * constreucteur vide de la carte
     */
    public Carte() {
        listeAdresses = new HashMap<Long,Adresse>();
        listeSegments = new ArrayList<Segment>();
    }

    public void setNomCarte(String nomCarte) {
        this.nomCarte = nomCarte;
    }

    /**
     * retourne l'adresse en entrant l'id de celle ci
     * @param id
     * @return
     */
    public Adresse obtenirAdresseParId(Long id){
        return listeAdresses.get(id);

    }

    /**
     * retourne la liste des adresses de la carte
     * @return
     */
    public Map<Long, Adresse> getListeAdresses() {
        return listeAdresses;
    }

    /**
     * retourne la liste des segments de la carte
     * @return
     */
    public List<Segment> getListeSegments() {
        return listeSegments;
    }

    public void reset() {
        this.nomCarte = "";
        listeAdresses = new HashMap<Long,Adresse>();
        listeSegments = new ArrayList<Segment>();
    }

    /**
     * Remplaces tous les attibuts de la carte par celle d'une autre carte
     * @param carteACloner la carte de laquelle les attributs sont récupérés
     */
    public void clone(Carte carteACloner) {
        this.listeAdresses = carteACloner.listeAdresses;
        this.listeSegments = carteACloner.listeSegments;
        this.nomCarte = carteACloner.nomCarte;
    }

    @Override
    public String toString() {
        return "Carte{" +
                "listeAdresses=" + listeAdresses.size() +
                ", listeSegments=" + listeSegments.size() +
                ", nomCarte='" + nomCarte + '\'' +
                '}';
    }
}
