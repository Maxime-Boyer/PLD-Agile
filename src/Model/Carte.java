package Model;

import Observer.Observable;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Carte extends Observable {

    private Map<Long,Adresse> listeAdresses;
    private List<Segment> listeSegments;
    private String nomCarte;

    private Color[] couleurInterieurChemin;
    private Color[] couleurExterieurChemin;

    /**
     * constructeur de la carte
     * @param nomCarte
     */
    public Carte(String nomCarte) {
        this();
        this.nomCarte = nomCarte;
    }

    /**
     * constreucteur vide de la carte
     */
    public Carte() {
        listeAdresses = new HashMap<Long,Adresse>();
        listeSegments = new ArrayList<Segment>();

        listeAdresses = new HashMap<Long,Adresse>();
        listeSegments = new ArrayList<Segment>();

        couleurExterieurChemin = new Color[]{
                new Color(14, 100, 182),
                new Color(143, 106,0),
                new Color(151, 58, 37),
                new Color(99, 0, 6),
                new Color(0, 0, 0)
        };

        couleurInterieurChemin = new Color[]{
                new Color(50,200,255),
                new Color(228, 221,0),
                new Color(229, 135, 49),
                new Color(217, 31, 41),
                new Color(58, 10, 18)
        };
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

    public double distanceEntreAdresse(Adresse a, Adresse b){
        double distance = Math.pow(a.getLongitude() - b.getLongitude(), 2) + Math.pow(a.getLatitude() - b.getLatitude(), 2);
        return distance;
    }

    public Adresse recherche(Adresse a){
        double distanceMin = Double.MAX_VALUE;
        double distance;
        Adresse plusProche = new Adresse();
        for(Map.Entry<Long, Adresse> entre : listeAdresses.entrySet()){
            distance = distanceEntreAdresse(a, entre.getValue());
            if( distance < distanceMin){
                distanceMin = distance;
                plusProche = entre.getValue();
            }
        }

        return plusProche;
    }

    public Color[] getCouleurInterieurChemin() {
        return couleurInterieurChemin;
    }

    public void setCouleurInterieurChemin(Color[] couleurInterieurChemin) {
        this.couleurInterieurChemin = couleurInterieurChemin;
    }

    public Color[] getCouleurExterieurChemin() {
        return couleurExterieurChemin;
    }

    public void setCouleurExterieurChemin(Color[] couleurExterieurChemin) {
        this.couleurExterieurChemin = couleurExterieurChemin;
    }
}
