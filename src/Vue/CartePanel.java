package Vue;

import Algorithmie.CalculateurTournee;
import Exceptions.AStarImpossibleException;
import Exceptions.IncompatibleAdresseException;
import Model.Adresse;
import Model.Carte;
import Model.LecteurXML;
import Model.Tournee;

import Model.*;

import org.xml.sax.SAXException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Locale;
import java.util.HashMap;
import java.util.Map;


public class CartePanel extends JPanel {
    private int largeur;
    private int hauteur;
    private double maxLongitudeCarte;
    private double maxLatitudeCarte;
    private double minLatitudeCarte;
    private double minLongitudeCarte;
    private boolean tourneeAppelee;
    private boolean itinerairePrepare;
    private double ecartLatitude;
    private double coeffY;
    private double ecartLongitude;
    private double coeffX;
    private ArrayList<Adresse> nouvelleAdresse = new ArrayList<>();

    private Tournee tournee = new Tournee();
    private LecteurXML lecteur = new LecteurXML();
    private JLabel labelPosition1;
    private JLabel labelPosition2;
    private ImageIcon iconPosition;
    private CalculateurTournee calculTournee;
    private Tournee itineraire;
    private Carte carte;
    private PopUpSaisieDuree popUpSaisieDuree;
    private Graphics g;


    /**
     * Panel où est tracée la carte importée par l'utilisateur
     * @param carte: l'objet carte du Modele
     * @param largeurEcran: largeur de la fenetre
     * @param hauteurEcran: hauteur de la fenetre
     * @param policeTexte: police a appliquer dans ce panel
     * @param ecouteurBoutons: ecouteur permettant de saisir des evenements liés aux boutons
     * @param ecouteurSouris: ecouteur permettant de saisir des evenements liés au survol de la souris
     */
    public CartePanel(Carte carte, int largeurEcran, int hauteurEcran, Font policeTexte, EcouteurBoutons ecouteurBoutons, EcouteurSouris ecouteurSouris) {
        super();
        this.carte = carte;
        maxLongitudeLatitudeCarte();
        this.largeur = (int) 3 * largeurEcran / 4;
        this.hauteur = (int) hauteurEcran;
        this.tourneeAppelee = false;
        this.itinerairePrepare = false;
        this.setBounds(0, 0, largeur, hauteur);
        this.setBackground(Color.WHITE);
        this.setLayout(null);
        this.addMouseListener(ecouteurSouris);

        //initialisation image
        iconPosition = new ImageIcon("src/images/Localisation.png");
        Image imagePosition = iconPosition.getImage(); // transform it
        Image newImagePosition = imagePosition.getScaledInstance(25, 30,  java.awt.Image.SCALE_SMOOTH);
        iconPosition = new ImageIcon(newImagePosition);

        labelPosition1 = new JLabel();
        labelPosition2 = new JLabel();
        labelPosition1.setIcon(iconPosition);
        labelPosition2.setIcon(iconPosition);

        //ininitialisation du popup de saisie des durees lors de l'ajout d'une etape
        popUpSaisieDuree = new PopUpSaisieDuree(policeTexte, ecouteurBoutons);
        /* - Exemple d'utilisation -
        popUpSaisieDuree.setPosition(200, 300);
        this.add(popUpSaisieDuree);*/
    }

    /**
     * geteur
     * @return: la tournee affichee sur la carte
     */
    public Tournee getTournee() {
        return tournee;
    }

    //INUTILE
    public void tracerCarte() {}

    /**
     * Place les images permettant de pointer une requete sur la carte a l'utilisateur
     * @param collecte: Etape de collecte de la requete à identifier
     * @param depot: Etape de dépot de la requete à identifier
     */
    public void indiquerPositionRequete(Etape collecte, Etape depot){
        int x1 = valeurX(collecte.getLongitude()) - iconPosition.getIconWidth()/2;
        int y1 = valeurY(collecte.getLatitude()) - iconPosition.getIconHeight()/2 - 25;
        int x2 = valeurX(depot.getLongitude()) - iconPosition.getIconWidth()/2;
        int y2 = valeurY(depot.getLatitude()) - iconPosition.getIconHeight()/2 - 25;
        labelPosition1.setBounds(x1, y1, iconPosition.getIconWidth(), iconPosition.getIconHeight());
        labelPosition2.setBounds(x2, y2, iconPosition.getIconWidth(), iconPosition.getIconHeight());
        this.add(labelPosition1);
        this.add(labelPosition2);
        this.repaint();
    }

    /**
     * Cache les images permettant de pointer une requete sur la carte a l'utilisateur
     */
    public void supprimerPositionRequete(){
        this.remove(labelPosition1);
        this.remove(labelPosition2);
        this.repaint();
    }

    /**
     * Initialise l'attribut tournee avec la tournee passee en entree. Indique a la methode paint que la tournee peut etre affichee
     * @param tournee: la tournee a tracer
     */
    public void tracerRequetes(Tournee tournee) {
        this.tournee = tournee;
            System.out.println("        Tournee = " + tournee);
        itinerairePrepare = false;
        tourneeAppelee = true;
    }

    /**
     * Lance le calcule de l'itineraire et indique à la méthode paint que l'itinéraire est pret a etre trace
     */
    public void tracerItineraire(Tournee tournee) {
        System.out.println("tracerItineraire");
        System.out.println("    tracerItineraire : carte = "+carte + ", tournee = "+tournee);
        /*
        calculTournee = new CalculateurTournee(carte, tournee);

        try {
            calculTournee.calculerTournee();
        } catch (AStarImpossibleException e) {
            e.printStackTrace();
        }

        itineraire = new Tournee();
        itineraire = calculTournee.getTournee();
        */
        itineraire = tournee;
        itinerairePrepare = true;
    }

    /**
     * methode repaint
     */
    public void repaint(Graphics g) {
        super.repaint();
        paintComponent(g);
    }

    /**
     * Méthode paint permettant d'effectuer tous les tracés graphiques
     * @param g
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.g = g;

        dessinerCarte();
        if (tourneeAppelee && itinerairePrepare)
            dessinerItineraire();

        if (tourneeAppelee) {
            try {
                dessinerTournee();
            } catch (IncompatibleAdresseException e) {
                e.printStackTrace();

            }
        }

        dessinerNouvelleRequete();
    }

    /**
     * Traduit une coordonnée de longitude à px sur l'axe x
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
     * @param latitude: la latitude a convertir
     * @return: l'équivalent en px sur y de la latitude entree
     */
    public int valeurY(double latitude) {
        double ecartLatitude = maxLatitudeCarte - minLatitudeCarte;
        double coeffY = hauteur / ecartLatitude;
        int valeurYPixel = (int) Math.ceil((maxLatitudeCarte - latitude) * coeffY);

        return valeurYPixel;
    }

    public double valeurLongitude(int posX) {
        double ecartLongitude = maxLongitudeCarte - minLongitudeCarte;
        double coeffX = largeur / ecartLongitude;
        double valeurLongitude = (posX / coeffX) + minLongitudeCarte;

        return valeurLongitude;
    }

    public double valeurLatitude(int posY) {
        double ecartLatitude = maxLatitudeCarte - minLatitudeCarte;
        double coeffY = hauteur / ecartLatitude;

        double valeurLatitude = -(posY / coeffY) + maxLatitudeCarte;

        return valeurLatitude;
    }

    /**
     * Dessine la carte dans le panel
     */
    public void dessinerCarte() {
        g.setColor(Color.BLACK);
    public void dessinerCarte(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // BackGround

        g.setColor(Color.WHITE);

        g.fillRect(0, 0, getSize().width, getSize().height);
        g2.setColor(Color.GRAY);
        g2.fillRect(0, 0, getSize().width, getSize().height);

        //Contour Segments
        if(!carte.getListeSegments().isEmpty()) {

            for (int i = 0; i < carte.getListeSegments().size(); i++) {
                Adresse origine = carte.getListeSegments().get(i).getOrigine();
                Adresse destination = carte.getListeSegments().get(i).getDestination();
                int origineX = valeurX(origine.getLongitude());
                int origineY = valeurY(origine.getLatitude());
                int destinationX = valeurX(destination.getLongitude());
                int destinationY = valeurY(destination.getLatitude());

                Stroke s = g2.getStroke();
                g2.setStroke(new BasicStroke(7));
                g2.setColor(Color.DARK_GRAY);
                g2.drawLine(origineX, origineY, destinationX, destinationY);
                g2.setStroke(s);
            }
        }

        //Interieur Segments
        if(!carte.getListeSegments().isEmpty()) {

            for (int i = 0; i < carte.getListeSegments().size(); i++) {
                Adresse origine = carte.getListeSegments().get(i).getOrigine();
                Adresse destination = carte.getListeSegments().get(i).getDestination();
                int origineX = valeurX(origine.getLongitude());
                int origineY = valeurY(origine.getLatitude());
                int destinationX = valeurX(destination.getLongitude());
                int destinationY = valeurY(destination.getLatitude());

                Stroke s = g2.getStroke();
                g2.setStroke(new BasicStroke(3));
                g2.setColor(Color.WHITE);
                g2.drawLine(origineX, origineY, destinationX, destinationY);
                g2.setStroke(s);
            }
        }
    }

    /**
     * Dessine les carres, ronds et triangles indiquant les différentes Etapes de la requete
     * @throws IncompatibleAdresseException: //TODO
     */
    public void dessinerTournee(Graphics g) throws IncompatibleAdresseException {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        //triangle depart tournee
        Adresse depart = tournee.getAdresseDepart();
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
        int []XPointsContour = {valeurXBasGauche,valeurXBasDroite,valeurXHaute};
        int []YPointsContour = {valeurYBasGauche,valeurYBasDroite,valeurYHaute};
        int []XPointsInterieur = {valeurXBasGauche+2,valeurXBasDroite-2,valeurXHaute};
        int []YPointsInterieur = {valeurYBasGauche-2,valeurYBasDroite-2,valeurYHaute+2};

        g2.setColor(new Color(128, 0, 0));
        g2.fillPolygon(XPointsContour,YPointsContour,3);
        g2.setColor(Color.RED);
        g2.fillPolygon(XPointsInterieur,YPointsInterieur,3);

        //Contour des marqueurs depots / collecte
        if(!tournee.getListeRequetes().isEmpty()){

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
                int rouge=(int)(couleurRequete.getRed()*0.6);
                int vert=(int)(couleurRequete.getGreen()*0.6);
                int bleu=(int)(couleurRequete.getBlue()*0.6);
                Color couleurContourRequete = new Color(rouge,vert,bleu);
                g2.setColor(couleurContourRequete);

                int taille = 14;
                g2.fillOval(valeurXCollecte - taille/2, valeurYCollecte - taille/2, taille+1, taille+1);
                g2.fillRoundRect(valeurXDepot - taille/2, valeurYDepot - taille/2, taille+1, taille+1, taille/2, taille/2);
            }
        }

        //Interieur des marqueurs depots / collecte
        if(!tournee.getListeRequetes().isEmpty()){

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

                g2.setColor(tournee.getListeRequetes().get(i).getCouleurRequete());

                int taille = 10;
                g2.fillOval(valeurXCollecte - taille/2, valeurYCollecte - taille/2, taille+1, taille+1);
                g2.fillRoundRect(valeurXDepot - taille/2, valeurYDepot - taille/2, taille+1, taille+1, taille/2, taille/2);
            }
        }
        /*else {
            throw new IncompatibleAdresseException("Erreur d'adresse de départ, cette adresse n'appartient pas à la carte chargée ");
        }*/

    }

    public void afficherTournee() {

        tourneeAppelee = true;

             /*System.out.println("valeurXCollecte " + valeurXCollecte);
            System.out.println("valeurYCollecte " + valeurYCollecte);
            System.out.println("valeurXDepot " + valeurXDepot);
            System.out.println("valeurYDepot " + valeurYDepot);*/

            /*BoutonRond boutonCollecte = new BoutonRond();
            JButton boutonDepot = new JButton();

            boutonCollecte.setBounds(valeurXCollecte-7,valeurYCollecte-7, 15, 15);
            boutonDepot.setBounds(valeurXDepot-7,valeurYDepot-7, 15, 15);
            boutonCollecte.setBorderPainted(false);
            boutonDepot.setBorderPainted(false);
            boutonCollecte.setOpaque(true);
            boutonDepot.setOpaque(true);
            Random rand = new Random();
            int maximumCouleur = 255;
            int r = rand.nextInt(maximumCouleur);
            int g = rand.nextInt(maximumCouleur);
            int b = rand.nextInt(maximumCouleur);

            //boutonCollecte.setBackground(new Color( r,g,b));
            boutonDepot.setBackground(new Color( r,g,b));

            this.add(boutonCollecte);
            this.add(boutonDepot);*/

        //}
    }

    /**
     * trace l'itineraire sur la carte
     */
    public void dessinerItineraire(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        //dessine contour du trajet
        for (int i = 0; i < itineraire.getListeChemins().size(); i++) {
            for (int j = 0; j < itineraire.getListeChemins().get(i).getListeSegment().size(); j++) {
                Adresse origine = itineraire.getListeChemins().get(i).getListeSegment().get(j).getOrigine();
                Adresse destination = itineraire.getListeChemins().get(i).getListeSegment().get(j).getDestination();
                int origineX = valeurX(origine.getLongitude());
                int origineY = valeurY(origine.getLatitude());
                int destinationX = valeurX(destination.getLongitude());
                int destinationY = valeurY(destination.getLatitude());

                Stroke s = g2.getStroke();
                g2.setStroke(new BasicStroke(8));
                g2.setColor(Color.BLUE);
                g2.drawLine(origineX, origineY, destinationX, destinationY);
                g2.setStroke(s);
            }
        }

        //dessine interieur des lignes du trajet
        for (int i = 0; i < itineraire.getListeChemins().size(); i++) {
            for (int j = 0; j < itineraire.getListeChemins().get(i).getListeSegment().size(); j++) {
                Adresse origine = itineraire.getListeChemins().get(i).getListeSegment().get(j).getOrigine();
                Adresse destination = itineraire.getListeChemins().get(i).getListeSegment().get(j).getDestination();
                int origineX = valeurX(origine.getLongitude());
                int origineY = valeurY(origine.getLatitude());
                int destinationX = valeurX(destination.getLongitude());
                int destinationY = valeurY(destination.getLatitude());

                Stroke s = g2.getStroke();
                g2.setStroke(new BasicStroke(6));
                g2.setColor(new Color(51, 204, 255));
                g2.drawLine(origineX, origineY, destinationX, destinationY);
                g2.setStroke(s);
            }
        }

        //dessine les flèches de direction
        Polygon teteFleche = new Polygon();
        teteFleche.addPoint(0,9);
        teteFleche.addPoint(-4,1);
        teteFleche.addPoint(4,1);
        for (int i = 0; i < itineraire.getListeChemins().size(); i++) {
            for (int j = 0; j < itineraire.getListeChemins().get(i).getListeSegment().size(); j++) {
                Adresse origine = itineraire.getListeChemins().get(i).getListeSegment().get(j).getOrigine();
                Adresse destination = itineraire.getListeChemins().get(i).getListeSegment().get(j).getDestination();
                int origineX = valeurX(origine.getLongitude());
                int origineY = valeurY(origine.getLatitude());
                int destinationX = valeurX(destination.getLongitude());
                int destinationY = valeurY(destination.getLatitude());

                //Si le segment est trop petit, on n'affiche pas la fleche
                //FIXME prend en compte les segments 1 par 1, et non la liste de segment sur la meme rue
                if((origineX-destinationX)*(origineX-destinationX)+(origineY-destinationY)*(origineY-destinationY) > 20*20){
                    g2.setColor(Color.white);

                    AffineTransform at1 = g2.getTransform();
                    AffineTransform at2 = (AffineTransform) at1.clone();
                    at2.translate((origineX+destinationX)/2.,(origineY+destinationY)/2.);
                    double angle = Math.atan2(destinationY-origineY,destinationX-origineX);
                    at2.rotate(angle - Math.PI/2);
                    g2.setTransform(at2);
                    g2.fill(teteFleche);
                    g2.setTransform(at1);

                }
            }
        }


        //dessine un rond pour chaque changement de rue
        String nomAdressePrecedente = "";
        for (int i = 0; i < itineraire.getListeChemins().size(); i++) {
            for (int j = 0; j < itineraire.getListeChemins().get(i).getListeSegment().size(); j++) {
                Adresse origine = itineraire.getListeChemins().get(i).getListeSegment().get(j).getOrigine();
                Adresse destination = itineraire.getListeChemins().get(i).getListeSegment().get(j).getDestination();
                int origineX = valeurX(origine.getLongitude());
                int origineY = valeurY(origine.getLatitude());
                int destinationX = valeurX(destination.getLongitude());
                int destinationY = valeurY(destination.getLatitude());

                //On n'affiche pas toutes les adresses, mais uniquement les changements de rue
                if(!nomAdressePrecedente.equals(itineraire.getListeChemins().get(i).getListeSegment().get(j).getNom())){
                    g2.setColor(Color.DARK_GRAY);
                    g2.fillOval(origineX-3,origineY-3,7,7);
                    g2.setColor(Color.WHITE);
                    g2.fillOval(origineX-2,origineY-2,5,5);
                }
                nomAdressePrecedente = itineraire.getListeChemins().get(i).getListeSegment().get(j).getNom();
            }
        }
    }

    public void dessinerNouvelleRequete() {
        for(int i = 0; i < nouvelleAdresse.size(); i++){
            double lonEtape = nouvelleAdresse.get(i).getLongitude();
            double latEtape = nouvelleAdresse.get(i).getLatitude();
            int valeurXEtape = valeurX(lonEtape);
            int valeurYEtape = valeurY(latEtape);
            g.setColor(Color.RED);
            if(i == 0){
                g.fillRoundRect(valeurXEtape - 7, valeurYEtape - 7, 14, 14, 14, 14);
            }else if(i == 1){
                g.fillRect(valeurXEtape - 7, valeurYEtape - 7, 14, 14);
            }
        }
    }

    public ArrayList<Adresse> getNouvelleAdresse() {
        return nouvelleAdresse;
    }

    public void ajouterAdresseNouvelleRequete(Adresse a){
        nouvelleAdresse.add(a);
        repaint();
    }

    public void viderNouvelleRequete(){
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

        ecartLatitude = maxLatitudeCarte - minLatitudeCarte;
        coeffY = hauteur / ecartLatitude;

        ecartLongitude = maxLongitudeCarte - minLongitudeCarte;
        coeffX = largeur / ecartLongitude;

    }
}
