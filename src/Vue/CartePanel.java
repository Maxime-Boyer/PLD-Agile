package Vue;

import Algorithmie.CalculateurTournee;
import Exceptions.IncompatibleAdresseException;
import Model.*;
import Observer.Observable;
import Observer.Observer;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class CartePanel extends JPanel implements Observer {
    private int largeur;
    private int hauteur;

    //Utilise pour l'affichage
    private double maxLongitudeCarte;
    private double maxLatitudeCarte;
    private double minLatitudeCarte;
    private double minLongitudeCarte;

    //Utilise pour limiter le zoom
    private double maxLongitudeInitialeCarte;
    private double maxLatitudeInitialeCarte;
    private double minLatitudeInitialeCarte;
    private double minLongitudeInitialeCarte;

    private boolean tourneeAppelee;
    private boolean itinerairePrepare;

    private Tournee tournee;

    private double ecartLatitude;
    private double coeffY;
    private double ecartLongitude;
    private double coeffX;
    private ArrayList<Adresse> nouvelleAdresse = new ArrayList<>();

    private LecteurXML lecteur = new LecteurXML();
    private JLabel labelPosition1;
    private JLabel labelPosition2;
    private ImageIcon iconPosition;
    private CalculateurTournee calculTournee;
    private Carte carte;
    private PopUpSaisieDuree popUpSaisieDuree;
    private Graphics2D g;
    private Legende legende;

    private Etape indiquerDepot;
    private Etape indiquerCollecte;
    private boolean indiquerEtape;


    /**
     * Panel où est tracée la carte importée par l'utilisateur
     *
     * @param carte:            l'objet carte du Modele
     * @param tournee           : l'objet tournee du modèle
     * @param largeurEcran:     largeur de la fenetre
     * @param hauteurEcran:     hauteur de la fenetre
     * @param policeTexte:      police a appliquer dans ce panel
     * @param ecouteurBoutons:  ecouteur permettant de saisir des evenements liés aux boutons
     * @param ecouteurSurvol:   ecouteur permettant de saisir des evenements liés au survol de la souris
     * @param ecouteurDragDrop: ecouteur permettant la gestion du drag & drop de la legende
     * @param ecouteurSouris:   ecouteur permettant de saisir des evenements liés au survol de la souris
     */

    public CartePanel(Carte carte, Tournee tournee, int largeurEcran, int hauteurEcran, Font policeTexte, EcouteurBoutons ecouteurBoutons, EcouteurSouris ecouteurSouris, EcouteurSurvol ecouteurSurvol, EcouteurDragDrop ecouteurDragDrop) {
        super();

        carte.addObserver(this); // this observe la carte

        this.carte = carte;
        tournee.addObserver(this); // this observe la tournee
        this.tournee = tournee;

        maxLongitudeLatitudeCarte();
        this.largeur = (int) (3 * largeurEcran / 4.);
        this.hauteur = (int) hauteurEcran;
        this.tourneeAppelee = false;
        this.itinerairePrepare = false;
        this.setBounds(0, 0, largeur, hauteur);
        this.setBackground(Color.WHITE);
        this.setLayout(null);

        this.addMouseListener(ecouteurSurvol);
        this.addMouseWheelListener(new EcouteurZoom(this, .002));
        this.addMouseMotionListener(new EcouteurDrag(this));
        this.addMouseListener(ecouteurSouris);

        //initialisation image
        iconPosition = new ImageIcon("src/images/Localisation.png");
        Image imagePosition = iconPosition.getImage(); // transform it
        Image newImagePosition = imagePosition.getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH);
        iconPosition = new ImageIcon(newImagePosition);

        labelPosition1 = new JLabel();
        labelPosition2 = new JLabel();
        labelPosition1.setIcon(iconPosition);
        labelPosition2.setIcon(iconPosition);

        //ininitialisation du popup de saisie des durees lors de l'ajout d'une etape
        popUpSaisieDuree = new PopUpSaisieDuree(policeTexte, ecouteurBoutons);

        legende = new Legende(this.carte, this.getWidth(), this.getHeight(), ecouteurDragDrop);

        this.setVisible(true);

        //Affichage des images au dessus des etapes lorsqu'on les selectionne
        indiquerEtape = false;
        indiquerCollecte = null;
        indiquerDepot = null;

        ResetZoomPanel resetZoomPanel = new ResetZoomPanel(this, 40, this.getLargeur() - 60, this.getHauteur() - 60);
        this.add(resetZoomPanel);
    }

    /**
     * Place les images permettant de pointer une requete sur la carte a l'utilisateur
     * @param collecte: Etape de collecte de la requete à identifier
     * @param depot:    Etape de dépot de la requete à identifier
     */
    public void indiquerPositionRequete(Etape collecte, Etape depot) {
        this.indiquerCollecte = collecte;
        this.indiquerDepot = depot;
        supprimerPositionRequete();
        this.indiquerEtape = true;
        if (collecte != null) {
            int x1 = valeurX(collecte.getLongitude()) - iconPosition.getIconWidth() / 2;
            int y1 = valeurY(collecte.getLatitude()) - iconPosition.getIconHeight() / 2 - 25;
            labelPosition1.setBounds(x1, y1, iconPosition.getIconWidth(), iconPosition.getIconHeight());
            this.add(labelPosition1);
        }
        if (depot != null) {
            int x2 = valeurX(depot.getLongitude()) - iconPosition.getIconWidth() / 2;
            int y2 = valeurY(depot.getLatitude()) - iconPosition.getIconHeight() / 2 - 25;
            labelPosition2.setBounds(x2, y2, iconPosition.getIconWidth(), iconPosition.getIconHeight());
            this.add(labelPosition2);
        }

        this.validate();
        this.repaint();
    }

    /**
     * Cache les images permettant de pointer une requete sur la carte a l'utilisateur
     */
    public void supprimerPositionRequete() {
        this.indiquerEtape = false;
        this.remove(labelPosition1);
        this.remove(labelPosition2);
        this.repaint();
    }

    /**
     * Methode appelée par les objets qui sont observés par cette fenêtre à chaque fois qu'ils sont mofifiés.
     */
    @Override
    public void update(Observable observed, Object arg) {
        if (arg != null) { // arg est soit une carte, soit une tournée qui a été mise à jour
            //Met à jour tournee ou carte
            if (arg instanceof Carte) {
                carte = (Carte) arg;
                maxLongitudeLatitudeCarte();
            } else if (arg instanceof Tournee) {
                tournee = (Tournee) arg;
            }
        }
        repaint();
    }

    /**
     * Méthode paint permettant d'effectuer tous les tracés graphiques
     * @param g graphics
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        //Affiche la carte uniquement si la carte est non vide
        if (carte != null)
            dessinerCarte(g2);
        if (tourneeAppelee && itinerairePrepare)
            dessinerItineraire(g2);


        if (indiquerEtape) {
            indiquerPositionRequete(indiquerCollecte, indiquerDepot);
        } else {
            supprimerPositionRequete();
        }

        //Affichage de la tournee
        if (tournee.getTourneeEstChargee()) {
            //Si la tournee est ordonnee trace l'itineraire
            if (tournee.getTourneeEstOrdonee())
                dessinerItineraire(g2);

            //Affiche la liste des requêtes
            try {
                dessinerTournee(g2);
            } catch (IncompatibleAdresseException e) {
                e.printStackTrace();
            }

        }

        dessinerNouvelleRequete(g2);
    }

    /**
     * Traduit une coordonnée de longitude à px sur l'axe x
     *
     * @param longitude: la logitude a convertir
     * @return: l'équivalent en px sur x de la longitude entree
     */
    public int valeurX(double longitude) {
        double ecartLongitude = maxLongitudeCarte - minLongitudeCarte;
        double coeffX = largeur / ecartLongitude;
        int valeurXPixel = (int) Math.ceil((longitude - minLongitudeCarte) * coeffX);

        return valeurXPixel;
    }

    /**
     * Traduit une coordonnée de latitude à px sur l'axe y
     *
     * @param latitude: la latitude a convertir
     * @return: l'équivalent en px sur y de la latitude entree
     */
    public int valeurY(double latitude) {
        double ecartLatitude = maxLatitudeCarte - minLatitudeCarte;
        double coeffY = hauteur / ecartLatitude;
        int valeurYPixel = (int) Math.ceil((maxLatitudeCarte - latitude) * coeffY);

        return valeurYPixel;
    }

    /**
     * Permet de convertir l'abscisse px en longitude
     * @param posX
     * @return la longitude
     */
    public double valeurLongitude(int posX) {
        double ecartLongitude = maxLongitudeCarte - minLongitudeCarte;
        double coeffX = largeur / ecartLongitude;
        double valeurLongitude = (posX / coeffX) + minLongitudeCarte;

        return valeurLongitude;
    }

    /**
     * Permet de convertir l'odronnée py en latitude
     * @param posY
     * @return la latitude
     */
    public double valeurLatitude(int posY) {
        double ecartLatitude = maxLatitudeCarte - minLatitudeCarte;
        double coeffY = hauteur / ecartLatitude;

        double valeurLatitude = -(posY / coeffY) + maxLatitudeCarte;

        return valeurLatitude;
    }

    /**
     * Dessine la carte dans le panel
     * @param g le graphique
     */
    public void dessinerCarte(Graphics2D g) {

        this.remove(legende);

        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // BackGround

        g.setColor(Color.WHITE);

        g.fillRect(0, 0, getSize().width, getSize().height);
        g.setColor(Color.GRAY);
        g.fillRect(0, 0, getSize().width, getSize().height);

        //Contour Segments
        if (!carte.getListeSegments().isEmpty()) {

            for (int i = 0; i < carte.getListeSegments().size(); i++) {
                Adresse origine = carte.getListeSegments().get(i).getOrigine();
                Adresse destination = carte.getListeSegments().get(i).getDestination();
                int origineX = valeurX(origine.getLongitude());
                int origineY = valeurY(origine.getLatitude());
                int destinationX = valeurX(destination.getLongitude());
                int destinationY = valeurY(destination.getLatitude());

                Stroke s = g.getStroke();
                g.setStroke(new BasicStroke(7));
                g.setColor(Color.DARK_GRAY);
                g.drawLine(origineX, origineY, destinationX, destinationY);
                g.setStroke(s);
            }
        }

        //Interieur Segments
        if (!carte.getListeSegments().isEmpty()) {

            for (int i = 0; i < carte.getListeSegments().size(); i++) {
                Adresse origine = carte.getListeSegments().get(i).getOrigine();
                Adresse destination = carte.getListeSegments().get(i).getDestination();
                int origineX = valeurX(origine.getLongitude());
                int origineY = valeurY(origine.getLatitude());
                int destinationX = valeurX(destination.getLongitude());
                int destinationY = valeurY(destination.getLatitude());

                Stroke s = g.getStroke();
                g.setStroke(new BasicStroke(3));
                g.setColor(Color.WHITE);
                g.drawLine(origineX, origineY, destinationX, destinationY);
                g.setStroke(s);
            }
        }
    }

    /**
     * Dessine les carres, ronds et triangles indiquant les différentes Etapes de la requete
     * @param g le graphique
     * @throws IncompatibleAdresseException:
     */
    public void dessinerTournee(Graphics2D g) throws IncompatibleAdresseException {
        this.add(legende);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        //triangle depart tournee
        Etape depart = tournee.getEtapeDepart();
        double lonDepart = depart.getLongitude();
        double latDepart = depart.getLatitude();
        int valeurXDepart = valeurX(lonDepart);
        int valeurYDepart = valeurY(latDepart);

        int valeurXBasGauche = valeurXDepart - 11;
        int valeurYBasGauche = valeurYDepart + 5;
        int valeurXBasDroite = valeurXDepart + 11;
        int valeurYBasDroite = valeurYDepart + 5;
        int valeurXHaute = valeurXDepart;
        int valeurYHaute = valeurYDepart - 14;
        int[] XPointsContour = {valeurXBasGauche, valeurXBasDroite, valeurXHaute};
        int[] YPointsContour = {valeurYBasGauche, valeurYBasDroite, valeurYHaute};
        int[] XPointsInterieur = {valeurXBasGauche + 2, valeurXBasDroite - 2, valeurXHaute};
        int[] YPointsInterieur = {valeurYBasGauche - 2, valeurYBasDroite - 2, valeurYHaute + 2};

        g.setColor(new Color(128, 0, 0));
        g.fillPolygon(XPointsContour, YPointsContour, 3);
        g.setColor(Color.RED);
        g.fillPolygon(XPointsInterieur, YPointsInterieur, 3);

        //Contour des marqueurs depots / collecte
        if (!tournee.getListeRequetes().isEmpty()) {

            for (int i = 0; i < tournee.getListeRequetes().size(); i++) {

                Adresse collecte = tournee.getListeRequetes().get(i).getEtapeCollecte();
                Adresse depot = tournee.getListeRequetes().get(i).getEtapeDepot();

                double lonCollecte = collecte.getLongitude();
                double latCollecte = collecte.getLatitude();
                double lonDepot = depot.getLongitude();
                double latDepot = depot.getLatitude();

                int valeurXCollecte = valeurX(lonCollecte);
                int valeurYCollecte = valeurY(latCollecte);
                int valeurXDepot = valeurX(lonDepot);
                int valeurYDepot = valeurY(latDepot);

                Color couleurRequete = tournee.getListeRequetes().get(i).getCouleurRequete();
                int rouge = (int) (couleurRequete.getRed() * 0.6);
                int vert = (int) (couleurRequete.getGreen() * 0.6);
                int bleu = (int) (couleurRequete.getBlue() * 0.6);
                Color couleurContourRequete = new Color(rouge, vert, bleu);
                g.setColor(couleurContourRequete);

                int taille = 14;
                g.fillOval(valeurXCollecte - taille / 2, valeurYCollecte - taille / 2, taille + 1, taille + 1);
                g.fillRoundRect(valeurXDepot - taille / 2, valeurYDepot - taille / 2, taille + 1, taille + 1, taille / 2, taille / 2);
            }
        }

        //Interieur des marqueurs depots / collecte
        if (!tournee.getListeRequetes().isEmpty()) {

            for (int i = 0; i < tournee.getListeRequetes().size(); i++) {

                Adresse collecte = tournee.getListeRequetes().get(i).getEtapeCollecte();
                Adresse depot = tournee.getListeRequetes().get(i).getEtapeDepot();

                double lonCollecte = collecte.getLongitude();
                double latCollecte = collecte.getLatitude();
                double lonDepot = depot.getLongitude();
                double latDepot = depot.getLatitude();

                int valeurXCollecte = valeurX(lonCollecte);
                int valeurYCollecte = valeurY(latCollecte);
                int valeurXDepot = valeurX(lonDepot);
                int valeurYDepot = valeurY(latDepot);

                g.setColor(tournee.getListeRequetes().get(i).getCouleurRequete());

                int taille = 10;
                g.fillOval(valeurXCollecte - taille / 2, valeurYCollecte - taille / 2, taille + 1, taille + 1);
                g.fillRoundRect(valeurXDepot - taille / 2, valeurYDepot - taille / 2, taille + 1, taille + 1, taille / 2, taille / 2);
            }
        }
    }

    /**
     * Trace l'itineraire sur la carte
     * @param g graphique
     */
    public void dessinerItineraire(Graphics2D g) {
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        //On compte le nombre de passage par segment
        HashMap<Adresse, HashMap<Adresse, Integer>> nbPassage = new HashMap<>();
        for (int i = 0; i < tournee.getListeChemins().size(); i++) {
            for (int j = 0; j < tournee.getListeChemins().get(i).getListeSegment().size(); j++) {
                Adresse origine = tournee.getListeChemins().get(i).getListeSegment().get(j).getOrigine();
                Adresse destination = tournee.getListeChemins().get(i).getListeSegment().get(j).getDestination();
                if (nbPassage.containsKey(origine) && nbPassage.get(origine).containsKey(destination)) {
                    nbPassage.get(origine).put(destination, nbPassage.get(origine).get(destination) + 1);
                } else if (nbPassage.containsKey(destination) && nbPassage.get(destination).containsKey(origine)) {
                    nbPassage.get(destination).put(origine, nbPassage.get(destination).get(origine) + 1);
                } else {
                    if (nbPassage.containsKey(origine)) {
                        nbPassage.get(origine).put(destination, 1);
                    } else {
                        HashMap<Adresse, Integer> hs = new HashMap<>();
                        hs.put(destination, 1);
                        nbPassage.put(origine, hs);
                    }
                }
            }
        }

        //dessine contour du trajet
        for (Map.Entry<Adresse, HashMap<Adresse, Integer>> entry : nbPassage.entrySet()) {
            for (Map.Entry<Adresse, Integer> entryBis : entry.getValue().entrySet()) {
                Adresse origine = entry.getKey();
                Adresse destination = entryBis.getKey();
                int origineX = valeurX(origine.getLongitude());
                int origineY = valeurY(origine.getLatitude());
                int destinationX = valeurX(destination.getLongitude());
                int destinationY = valeurY(destination.getLatitude());

                Stroke s = g.getStroke();
                g.setStroke(new BasicStroke(8));

                switch (entryBis.getValue()) {
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                        g.setColor(carte.getCouleurExterieurChemin()[entryBis.getValue() - 1]);
                        break;
                    default:
                        g.setColor(carte.getCouleurExterieurChemin()[4]);
                        break;
                }

                g.drawLine(origineX, origineY, destinationX, destinationY);
                g.setStroke(s);

            }
        }

        //dessine interieur des lignes du trajet
        for (Map.Entry<Adresse, HashMap<Adresse, Integer>> entry : nbPassage.entrySet()) {
            for (Map.Entry<Adresse, Integer> entryBis : entry.getValue().entrySet()) {
                Adresse origine = entry.getKey();
                Adresse destination = entryBis.getKey();
                int origineX = valeurX(origine.getLongitude());
                int origineY = valeurY(origine.getLatitude());
                int destinationX = valeurX(destination.getLongitude());
                int destinationY = valeurY(destination.getLatitude());

                Stroke s = g.getStroke();
                g.setStroke(new BasicStroke(6));

                switch (entryBis.getValue()) {
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                        g.setColor(carte.getCouleurInterieurChemin()[entryBis.getValue() - 1]);
                        break;
                    default:
                        g.setColor(carte.getCouleurInterieurChemin()[4]);
                        break;
                }

                g.drawLine(origineX, origineY, destinationX, destinationY);
                g.setStroke(s);

            }
        }

        //dessine les flèches de direction
        Polygon teteFleche = new Polygon();
        teteFleche.addPoint(0, 9);
        teteFleche.addPoint(-4, 1);
        teteFleche.addPoint(4, 1);
        for (int i = 0; i < tournee.getListeChemins().size(); i++) {
            for (int j = 0; j < tournee.getListeChemins().get(i).getListeSegment().size(); j++) {
                Adresse origine = tournee.getListeChemins().get(i).getListeSegment().get(j).getOrigine();
                Adresse destination = tournee.getListeChemins().get(i).getListeSegment().get(j).getDestination();

                int origineX = valeurX(origine.getLongitude());
                int origineY = valeurY(origine.getLatitude());
                int destinationX = valeurX(destination.getLongitude());
                int destinationY = valeurY(destination.getLatitude());

                //Si le segment est trop petit, on n'affiche pas la fleche
                if ((origineX - destinationX) * (origineX - destinationX) + (origineY - destinationY) * (origineY - destinationY) > 25 * 25) {
                    g.setColor(Color.white);

                    AffineTransform at1 = g.getTransform();
                    AffineTransform at2 = (AffineTransform) at1.clone();
                    at2.translate((origineX + destinationX) / 2., (origineY + destinationY) / 2.);
                    double angle = Math.atan2(destinationY - origineY, destinationX - origineX);
                    at2.rotate(angle - Math.PI / 2);
                    g.setTransform(at2);
                    g.fill(teteFleche);
                    g.setTransform(at1);

                }
            }
        }


        //dessine un rond pour chaque changement de rue
        String nomAdressePrecedente = "";
        for (int i = 0; i < tournee.getListeChemins().size(); i++) {
            for (int j = 0; j < tournee.getListeChemins().get(i).getListeSegment().size(); j++) {
                Adresse origine = tournee.getListeChemins().get(i).getListeSegment().get(j).getOrigine();
                Adresse destination = tournee.getListeChemins().get(i).getListeSegment().get(j).getDestination();
                int origineX = valeurX(origine.getLongitude());
                int origineY = valeurY(origine.getLatitude());
                int destinationX = valeurX(destination.getLongitude());
                int destinationY = valeurY(destination.getLatitude());

                //On n'affiche pas toutes les adresses, mais uniquement les changements de rue
                if (!nomAdressePrecedente.equals(tournee.getListeChemins().get(i).getListeSegment().get(j).getNom())) {
                    g.setColor(Color.DARK_GRAY);
                    g.fillOval(origineX - 3, origineY - 3, 7, 7);
                    g.setColor(Color.WHITE);
                    g.fillOval(origineX - 2, origineY - 2, 5, 5);
                }
                nomAdressePrecedente = tournee.getListeChemins().get(i).getListeSegment().get(j).getNom();
            }
        }
    }

    /**
     * Dessine la liste de requete dans la fenetre
     * @param g le graphique
     */
    public void dessinerNouvelleRequete(Graphics2D g) {
        for (int i = 0; i < nouvelleAdresse.size(); i++) {
            double lonEtape = nouvelleAdresse.get(i).getLongitude();
            double latEtape = nouvelleAdresse.get(i).getLatitude();
            int valeurXEtape = valeurX(lonEtape);
            int valeurYEtape = valeurY(latEtape);
            g.setColor(Color.RED);
            int taille = 10;
            if (i == 0) {

                g.fillOval(valeurXEtape - taille / 2, valeurYEtape - taille / 2, taille + 1, taille + 1);
                g.fillRoundRect(valeurXEtape - 7, valeurYEtape - 7, 14, 14, 14, 14);
            } else if (i == 1) {
                g.fillRoundRect(valeurXEtape - taille / 2, valeurYEtape - taille / 2, taille + 1, taille + 1, taille / 2, taille / 2);

            }
        }
    }

    /**
     * Renvoie la liste des adresses temporaires de collecte et de dépot
     * @return la liste d'adresses temporaires
     */
    public ArrayList<Adresse> getNouvelleAdresse() {
        return nouvelleAdresse;
    }

    /**
     * Ajoute une nouvelle adresse à la liste d'adresses temporaires
     * @param a, l'adresse à ajouter à la liste
     */
    public void ajouterAdresseNouvelleRequete(Adresse a) {
        nouvelleAdresse.add(a);
        repaint();
    }


    /**
     * Vide la liste d'adresses temporaires
     */
    public void viderNouvelleRequete() {
        nouvelleAdresse.clear();
    }


    /**
     * Definit les coordonnées en px des extremites du panel
     */
    public void maxLongitudeLatitudeCarte() {
        double maxLongitude = 0.0D;
        double maxLatitude = 0.0D;
        double minLongitude = 1000.0D;
        double minLatitude = 1000.0D;
        //On recupere la longitude et latitude maximale des adresses de la carte
        for (Map.Entry mapentry : carte.getListeAdresses().entrySet()) {
            Adresse adresseCourante = (Adresse) mapentry.getValue();
            if (adresseCourante.getLongitude() > maxLongitude) {
                maxLongitude = adresseCourante.getLongitude();
            }
            if (adresseCourante.getLatitude() > maxLatitude) {
                maxLatitude = adresseCourante.getLatitude();
            }
            if (adresseCourante.getLatitude() < minLatitude) {
                minLatitude = adresseCourante.getLatitude();
            }
            if (adresseCourante.getLongitude() < minLongitude) {
                minLongitude = adresseCourante.getLongitude();
            }
        }
        maxLongitudeCarte = maxLongitude;
        maxLatitudeCarte = maxLatitude;
        minLatitudeCarte = minLatitude;
        minLongitudeCarte = minLongitude;

        maxLongitudeInitialeCarte = maxLongitude;
        maxLatitudeInitialeCarte = maxLatitude;
        minLatitudeInitialeCarte = minLatitude;
        minLongitudeInitialeCarte = minLongitude;

        ecartLatitude = maxLatitudeCarte - minLatitudeCarte;
        coeffY = hauteur / ecartLatitude;

        ecartLongitude = maxLongitudeCarte - minLongitudeCarte;
        coeffX = largeur / ecartLongitude;
    }

    /**
     * Getter sur la longitude maximum de la carte affichée à l'écran
     * @return la longitude maximum de la carte affichée à l'écran
     */
    public double getMaxLongitudeCarte() {
        return maxLongitudeCarte;
    }

    /**
     * Setter sur la longitude maximum de la carte affichée à l'écran
     * @param maxLongitudeCarte : la longitude maximum de la carte affichée à l'écran
     */
    public void setMaxLongitudeCarte(double maxLongitudeCarte) {
        this.maxLongitudeCarte = maxLongitudeCarte;
    }

    /**
     * Getter sur la latitude maximum de la carte affichée à l'écran
     * @return la latitude maximum de la carte affichée à l'écran
     */
    public double getMaxLatitudeCarte() {
        return maxLatitudeCarte;
    }

    /**
     * Setter sur la latitude maximum de la carte affichée à l'écran
     * @param maxLatitudeCarte : la latitude maximum de la carte affichée à l'écran
     */
    public void setMaxLatitudeCarte(double maxLatitudeCarte) {
        this.maxLatitudeCarte = maxLatitudeCarte;
    }

    /**
     * Getter sur la latitude minimum de la carte affichée à l'écran
     * @return la latitude minimum de la carte affichée à l'écran
     */
    public double getMinLatitudeCarte() {
        return minLatitudeCarte;
    }

    /**
     * Setter sur la latitude minimum de la carte affichée à l'écran
     * @param minLatitudeCarte : la latitude minimum de la carte affichée à l'écran
     */
    public void setMinLatitudeCarte(double minLatitudeCarte) {
        this.minLatitudeCarte = minLatitudeCarte;
    }

    /**
     * Getter sur la longitude minimum de la carte affichée à l'écran
     * @return la longitude minimum de la carte affichée à l'écran
     */
    public double getMinLongitudeCarte() {
        return minLongitudeCarte;
    }

    /**
     * Setter sur la longitude minimum de la carte affichée à l'écran
     * @param minLongitudeCarte : la longitude minimum de la carte affichée à l'écran
     */
    public void setMinLongitudeCarte(double minLongitudeCarte) {
        this.minLongitudeCarte = minLongitudeCarte;
    }

    /**
     * Getter sur la largeur du panel en pixel
     * @return la largeur du panel en pixel
     */
    public int getLargeur() {
        return largeur;
    }

    /**
     * Getter sur la hauteur du panel en pixel
     * @return la hauteur du panel en pixel
     */
    public int getHauteur() {
        return hauteur;
    }

    /**
     * Getter sur la longitude maximale de la carte
     * @return la longitude maximale de la carte
     */
    public double getMaxLongitudeInitialeCarte() {
        return maxLongitudeInitialeCarte;
    }

    /**
     * Getter sur la latitude maximale de la carte
     * @return la latitude maximale de la carte
     */
    public double getMaxLatitudeInitialeCarte() {
        return maxLatitudeInitialeCarte;
    }

    /**
     * Getter sur la latitude minimum de la carte
     * @return la latitude minimum de la carte
     */
    public double getMinLatitudeInitialeCarte() {
        return minLatitudeInitialeCarte;
    }

    /**
     * Getter sur la longitude minimum de la carte
     * @return la longitude minimum de la carte
     */
    public double getMinLongitudeInitialeCarte() {
        return minLongitudeInitialeCarte;
    }
}
