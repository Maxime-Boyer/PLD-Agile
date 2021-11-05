package Vue;
//
//import Algorithmie.CalculateurTournee;

//import Algorithmie.CalculateurTournee;

import Algorithmie.CalculateurTournee;
import Exceptions.AStarImpossibleException;
import Exceptions.IncompatibleAdresseException;
import Exceptions.NameFile;
import Model.Adresse;
import Model.Carte;
import Model.LecteurXML;
import Model.Tournee;

import org.xml.sax.SAXException;

import javax.swing.*;
import javax.swing.border.Border;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Locale;
import java.util.HashMap;
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

    public CartePanel(int largeurEcran, int hauteurEcran, Font policeTexte) throws NameFile {

        this.largeur = (int) 3 * largeurEcran / 4;
        this.hauteur = (int) hauteurEcran;
        this.tourneeAppelee = false;
        this.itinerairePrepare = false;
        this.setBounds(0, 0, largeur, hauteur);
        this.setBackground(Color.WHITE);
        this.setLayout(null);

        tracerCarte();
    }

    public Tournee getTournee() {
        return tournee;
    }

    public void tracerCarte() {

        /*

        JFrame frameSelectCarte = new JFrame();

        String nameFile = "";
        String filename = "";

        while(!nameFile.toLowerCase(Locale.ROOT).contains("map")) {
            FileDialog fd = new FileDialog(frameSelectCarte, "Sélectionnez une carte au format xml", FileDialog.LOAD);
            fd.setDirectory("C:\\");
            fd.setFile("*.xml");
            fd.setVisible(true);
            filename = fd.getDirectory() + fd.getFile();
            nameFile = fd.getFile();
        }
         */

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
            maxLongitudeLatitudeCarte();
        } catch (Exception e) {
            System.out.println(e);
        }

        frameSelectCarte.dispose();

    }

    public void tracerRequetes() {

        /*

        String nameFile = "";
        String filename = "";
        JFrame frameSelectRequetes = new JFrame();

        while(!nameFile.toLowerCase(Locale.ROOT).contains("requests")) {

            FileDialog fd = new FileDialog(frameSelectRequetes, "Sélectionnez une liste de requêtes au format xml", FileDialog.LOAD);
            fd.setDirectory("C:\\");
            fd.setFile("*.xml");
            fd.setVisible(true);
            filename = fd.getDirectory() + fd.getFile();
            nameFile = fd.getFile();
        }
         */

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
        System.out.println("tracerItineraire");
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
            try {
                dessinerTournee(g2);
            } catch (IncompatibleAdresseException e) {
                e.printStackTrace();

            }
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

        if(!carte.getListeSegments().isEmpty()) {

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
    }

    public void dessinerTournee(Graphics g2) throws IncompatibleAdresseException {
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

            //g2.setColor(tournee.getListeRequetes().get(i).getCouleur());


                    g2.setColor(tournee.getListeRequetes().get(i).getCouleurRequete());

                    g2.fillRoundRect(valeurXCollecte - 7, valeurYCollecte - 7, 14, 14, 14, 14);
                    g2.fillRect(valeurXDepot - 7, valeurYDepot - 7, 14, 14);
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

    public void dessinerItineraire(Graphics g2) {
        System.out.println("CartePane : dessinerItineraire");

        System.out.println("CartePane : dessinerItineraire -> inside loop");
        CalculateurTournee calculTournee = new CalculateurTournee(carte, tournee);
        try {
            calculTournee.calculerTournee();
        } catch (AStarImpossibleException e) {
            e.printStackTrace();
        }

        Tournee itineraire = new Tournee();
        //todo enlever commentaire
        itineraire = calculTournee.getTournee();
        System.out.println("itineraire : "+itineraire);
        //fin todo

        //HashMap<Long, HashMap<Long, CheminEntreEtape>> itineraire = new HashMap<>();
        //itineraire = calculTournee.calculerTournee();
        //System.out.println(itineraire);


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

        /*
        boucle:
        {
            int ii=0;
            for (HashMap<Long, CheminEntreEtape> listeCheminEntreEtape : itineraire.values()) {
                int i =0;
                ii++;
                for (CheminEntreEtape cee : listeCheminEntreEtape.values()) {
                    i++;
                    //if(i==3 && ii==1) {
                        for (int j = 0; j < cee.getListeSegment().size(); j++) {
                            Adresse origine = cee.getListeSegment().get(j).getOrigine();
                            Adresse destination = cee.getListeSegment().get(j).getDestination();
                            int origineX = valeurX(origine.getLongitude());
                            int origineY = valeurY(origine.getLatitude());
                            int destinationX = valeurX(destination.getLongitude());
                            int destinationY = valeurY(destination.getLatitude());
                            g2.setColor(Color.RED);
                            g2.drawLine(origineX, origineY, destinationX, destinationY);
                        }
                        //break boucle;
                    //}
                }
            }
        }*/


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
