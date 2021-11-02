package Vue;

import Algorithmie.CalculateurTournee;
import Model.*;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.swing.border.Border;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;


public class CartePanel extends JPanel {
    private int largeur;
    private int hauteur;
    private double maxLongitudeCarte;
    private double maxLatitudeCarte;
    private double minLatitudeCarte;
    private double minLongitudeCarte;
    private boolean tourneeAppelee;
    private boolean itinerairePrepare;
    private Carte carte = new Carte();
    private Tournee tournee = new Tournee();
    private LecteurXML lecteur = new LecteurXML();
    private JLabel labelPosition1;
    private JLabel labelPosition2;
    private ImageIcon iconPosition;
    private CalculateurTournee calculTournee;
    private Tournee itineraire;

    public CartePanel(int largeurEcran, int hauteurEcran, Font policeTexte, EcouteurSurvol ecouteurSurvol) {

        this.largeur = (int) 3 * largeurEcran / 4;
        this.hauteur = (int) hauteurEcran;
        this.tourneeAppelee = false;
        this.itinerairePrepare = false;
        this.setBounds(0, 0, largeur, hauteur);
        this.setBackground(Color.WHITE);
        this.setLayout(null);
        this.addMouseListener(ecouteurSurvol);

        //initialisation image
        iconPosition = new ImageIcon("src/images/Localisation.png");
        Image imagePosition = iconPosition.getImage(); // transform it
        Image newImagePosition = imagePosition.getScaledInstance(25, 30,  java.awt.Image.SCALE_SMOOTH);
        iconPosition = new ImageIcon(newImagePosition);

        labelPosition1 = new JLabel();
        labelPosition2 = new JLabel();
        labelPosition1.setIcon(iconPosition);
        labelPosition2.setIcon(iconPosition);

        tracerCarte();
    }

    public Tournee getTournee() {
        return tournee;
    }

    public void tracerCarte() {
        JFrame frameSelectCarte = new JFrame();
        FileDialog fd = new FileDialog(frameSelectCarte, "Sélectionnez une carte au format xml", FileDialog.LOAD);
        fd.setDirectory("C:\\");
        fd.setFile("*.xml");
        fd.setVisible(true);
        String filename = fd.getDirectory() + fd.getFile();

        if (filename == null)
            System.out.println("You cancelled the choice");
        else
            System.out.println("You chose " + filename);

        try {
            carte = lecteur.lectureCarte(filename);
        } catch (Exception e) {
            System.out.println(e);
        }

        frameSelectCarte.dispose();
        maxLongitudeLatitudeCarte();
    }

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

    public void supprimerPositionRequete(){
        this.remove(labelPosition1);
        this.remove(labelPosition2);
        this.repaint();
    }

    public void tracerRequetes() {

        JFrame frameSelectRequetes = new JFrame();
        FileDialog fd = new FileDialog(frameSelectRequetes, "Sélectionnez une liste de requêtes au format xml", FileDialog.LOAD);
        fd.setDirectory("C:\\");
        fd.setFile("*.xml");
        fd.setVisible(true);
        String filename = fd.getDirectory() + fd.getFile();
        if (filename == null)
            System.out.println("You cancelled the choice");
        else
            System.out.println("You chose " + filename);

        try {
            tournee = lecteur.lectureRequete(filename);
        } catch (Exception e) {
            System.out.println(e);
        }
        frameSelectRequetes.dispose();
        itinerairePrepare = false;
        tourneeAppelee = true;
    }

    public void tracerItineraire() {
        System.out.println("CartePane : dessinerItineraire");
        this.calculTournee = new CalculateurTournee(carte, tournee);
        calculTournee.calculerTournee();
        itineraire = new Tournee();
        itineraire = calculTournee.getTsp().getTournee();
        itinerairePrepare = true;
    }

    public void repaint(Graphics g) {
        super.repaint();
        paintComponent(g);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        //System.out.println("hauteur ecran : " + hauteurEcran + " largeur ecran : " + largeurEcran);
        Graphics2D g2 = (Graphics2D) g;
        dessinerCarte(g2);
        if (tourneeAppelee && itinerairePrepare)
            dessinerItineraire(g2);

        if (tourneeAppelee) {
            dessinerTournee(g2);
        }
    }

    public int valeurX(double longitude) {
        double ecartLongitude = maxLongitudeCarte - minLongitudeCarte;
        double coeffX = largeur / ecartLongitude;
        int valeurXPixel = (int) Math.ceil((longitude - minLongitudeCarte) * coeffX);

        return valeurXPixel;
    }

    public int valeurY(double latitude) {
        double ecartLatitude = maxLatitudeCarte - minLatitudeCarte;
        double coeffY = hauteur / ecartLatitude;
        int valeurYPixel = (int) Math.ceil((maxLatitudeCarte - latitude) * coeffY);

        return valeurYPixel;
    }

    public void dessinerCarte(Graphics g2) {
        g2.setColor(Color.BLACK);
        // BackGround

        g2.setColor(Color.WHITE);

        g2.fillRect(0, 0, getSize().width, getSize().height);

        g2.setColor(Color.BLACK);

        for (int i = 0; i < carte.getListeSegments().size(); i++) {
            Adresse origine = carte.getListeSegments().get(i).getOrigine();
            Adresse destination = carte.getListeSegments().get(i).getDestination();
            int origineX = valeurX(origine.getLongitude());
            int origineY = valeurY(origine.getLatitude());
            int destinationX = valeurX(destination.getLongitude());
            int destinationY = valeurY(destination.getLatitude());

            g2.drawLine(origineX, origineY, destinationX, destinationY);
        }
    }

    public void dessinerTournee(Graphics g2) {
        Adresse depart = tournee.getAdresseDepart();
        double lonDepart = depart.getLongitude();
        double latDepart = depart.getLatitude();
        int valeurXDepart = valeurX(lonDepart);
        int valeurYDepart = valeurY(latDepart);
        g2.setColor(Color.RED);
        int valeurXBasGauche = valeurXDepart - 11;
        int valeurYBasGauche = valeurYDepart + 5;

        int valeurXBasDroite = valeurXDepart + 11;
        int valeurYBasDroite = valeurYDepart + 5;

        int valeurXHaute = valeurXDepart;
        int valeurYHaute = valeurYDepart - 10;

        int []XPoints = {valeurXBasGauche,valeurXBasDroite,valeurXHaute};
        int []YPoints = {valeurYBasGauche,valeurYBasDroite,valeurYHaute};

        g2.fillPolygon(XPoints,YPoints,3);



        //zg2.fillOval(valeurXDepart, valeurYDepart, 25, 12);


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

            //g2.setColor(tournee.getListeRequetes().get(i).getCouleur());


            g2.setColor(tournee.getListeRequetes().get(i).getCouleurRequete());

            g2.fillRoundRect(valeurXCollecte - 7, valeurYCollecte - 7, 14, 14, 14, 14);
            g2.fillRect(valeurXDepot - 7, valeurYDepot - 7, 14, 14);


        }
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

    public void dessinerItineraire(Graphics g2) {

        for (int i = 0; i < itineraire.getListeChemins().size(); i++) {
            for (int j = 0; j < itineraire.getListeChemins().get(i).getListeSegment().size(); j++) {
                Adresse origine = itineraire.getListeChemins().get(i).getListeSegment().get(j).getOrigine();
                Adresse destination = itineraire.getListeChemins().get(i).getListeSegment().get(j).getDestination();
                int origineX = valeurX(origine.getLongitude());
                int origineY = valeurY(origine.getLatitude());
                int destinationX = valeurX(destination.getLongitude());
                int destinationY = valeurY(destination.getLatitude());
                g2.setColor(Color.RED);
                g2.drawLine(origineX, origineY, destinationX, destinationY);

            }
        }
    }

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

        System.out.println("maxLongitude : " + maxLongitude
                + " | maxLatitude: " + maxLatitude
                + " | minLatitude: " + minLatitude
                + " | minLongitude: " + minLongitude);
    }
}
