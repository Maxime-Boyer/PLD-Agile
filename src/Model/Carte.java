package Model;

import Observer.Observable;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Une carte se caractérise par une liste d'adresse et de segments les reliants.
 */
public class Carte extends Observable {

    private Map<Long, Adresse> listeAdresses;
    private List<Segment> listeSegments;
    private String nomCarte;

    private Color[] couleurInterieurChemin;
    private Color[] couleurExterieurChemin;

    /**
     * Constructeur de la carte, une carte est un ensemble d’adresses et de segments
     *
     * @param nomCarte: nom du fichier xml à ouvrir
     */
    public Carte(String nomCarte) {
        this();
        this.nomCarte = nomCarte;
    }

    /**
     * Constructeur par défaut de la carte
     */
    public Carte() {
        listeAdresses = new HashMap<>();
        listeSegments = new ArrayList<>();

        listeAdresses = new HashMap<>();
        listeSegments = new ArrayList<>();

        couleurExterieurChemin = new Color[]{
                new Color(14, 100, 182),
                new Color(143, 106, 0),
                new Color(151, 58, 37),
                new Color(99, 0, 6),
                new Color(0, 0, 0)
        };

        couleurInterieurChemin = new Color[]{
                new Color(50, 200, 255),
                new Color(228, 221, 0),
                new Color(229, 135, 49),
                new Color(217, 31, 41),
                new Color(58, 10, 18)
        };
    }

    /**
     * Méthode qui retourne l'adresse en entrant l'id de celle ci
     *
     * @param id id de l'adresse à retourner
     * @return l'Adresse qu'il y a dans la liste d'adresse depuis son id
     */
    public Adresse obtenirAdresseParId(Long id) {
        return listeAdresses.get(id);

    }

    /**
     * Méthode qui retourne la liste des adresses de la carte
     *
     * @return la liste (map) d'adresse dont la clé est l'id
     */
    public Map<Long, Adresse> getListeAdresses() {
        return listeAdresses;
    }

    /**
     * Méthode qui retourne la liste des segments de la carte
     *
     * @return la liste des segments
     */
    public List<Segment> getListeSegments() {
        return listeSegments;
    }

    /**
     * Méthode qui remplace tous les attibuts de la carte par celle d'une autre carte
     *
     * @param carteACloner: la carte de laquelle les attributs sont récupérés
     */
    public void clone(Carte carteACloner) {
        this.listeAdresses = carteACloner.listeAdresses;
        this.listeSegments = carteACloner.listeSegments;
        this.nomCarte = carteACloner.nomCarte;
    }

    /**
     * Méthode qui retourne la distance entre les Adresse a et b pour le calcul de l'heuristique (distance en vol d'oiseau)
     *
     * @param a Adresse 1
     * @param b Adresse 2
     * @return la distance entre les deux Adresse
     */
    public double distanceEntreAdresse(Adresse a, Adresse b) {
        double distance = Math.pow(a.getLongitude() - b.getLongitude(), 2) + Math.pow(a.getLatitude() - b.getLatitude(), 2);
        return distance;
    }

    /**
     * Méthode qui retourne l'adresse la plus proche lors du clique dans l'etat ajout de requête
     *
     * @param a adresse qui est cliquée
     * @return l'Adresse la plus proche
     */
    public Adresse recherche(Adresse a) {
        double distanceMin = Double.MAX_VALUE;
        double distance;
        Adresse plusProche = new Adresse();
        for (Map.Entry<Long, Adresse> entre : listeAdresses.entrySet()) {
            distance = distanceEntreAdresse(a, entre.getValue());
            if (distance < distanceMin) {
                distanceMin = distance;
                plusProche = entre.getValue();
            }
        }

        return plusProche;
    }

    /**
     * Méthode qui retourne les couleur du chemin qui est différente en focntion du nombre de passage
     *
     * @return un tableau de couleur
     */
    public Color[] getCouleurInterieurChemin() {
        return couleurInterieurChemin;
    }


    /**
     * Méthode qui retourne les couleur extérieur du chemin (les contours) qui est différente en focntion du nombre de passage
     *
     * @return un tableau de couleur
     */
    public Color[] getCouleurExterieurChemin() {
        return couleurExterieurChemin;
    }


    /**
     * Méthode permettant l'affichage textuel de la carte
     *
     * @return L'objet carte sous forme textuel
     */
    @Override
    public String toString() {
        return "Carte{" +
                "listeAdresses=" + listeAdresses.size() +
                ", listeSegments=" + listeSegments.size() +
                ", nomCarte='" + nomCarte + '\'' +
                '}';
    }

}
