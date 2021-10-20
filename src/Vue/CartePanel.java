package Vue;

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
    private Carte carte = new Carte();
    private Tournee tournee = new Tournee();
    private LecteurXML lecteur = new LecteurXML();

    public CartePanel(int largeurEcran, int hauteurEcran, Font policeTexte){

        this.largeur = (int) 3*largeurEcran/4;
        this.hauteur = (int) hauteurEcran;
        this.tourneeAppelee = false;
        this.setBounds(0, 0, largeur, hauteur);
        this.setBackground(Color.WHITE);
        this.setLayout(null);

        tracerCarte();
    }

    public Tournee getTournee(){
        return tournee;
    }

    public void tracerCarte(){
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

        try{
            carte = lecteur.lectureCarte(filename);
        }catch(Exception e){
            System.out.println(e);
        }

        frameSelectCarte.dispose();
        maxLongitudeLatitudeCarte();
    }

    public void tracerRequetes(){

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

        try{
            tournee = lecteur.lectureRequete(filename);
        }catch(Exception e){
            System.out.println(e);
        }
        frameSelectRequetes.dispose();
        tourneeAppelee = true;
    }

    public void repaint(Graphics g) {
        super.repaint();
        paintComponent(g);
    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        //System.out.println("hauteur ecran : " + hauteurEcran + " largeur ecran : " + largeurEcran);
        Graphics2D g2 = (Graphics2D) g;
        dessinerCarte(g2);

        if(tourneeAppelee){
            dessinerTournee(g2);
        }
    }

    public int valeurX(double longitude){
        double ecartLongitude = maxLongitudeCarte - minLongitudeCarte;
        double coeffX = largeur / ecartLongitude;
        int valeurXPixel = (int) Math.ceil((longitude - minLongitudeCarte)*coeffX);

        return valeurXPixel;
    }

    public int valeurY(double latitude){
        double ecartLatitude = maxLatitudeCarte - minLatitudeCarte;
        double coeffY = hauteur / ecartLatitude;
        int valeurYPixel = (int) Math.ceil((maxLatitudeCarte - latitude)*coeffY);

        return valeurYPixel;
    }

    public void dessinerCarte(Graphics g2){
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

    public void dessinerTournee(Graphics g2){
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

            Random rand = new Random();
            int maximumCouleur = 255;
            int r = rand.nextInt(maximumCouleur);
            int gr = rand.nextInt(maximumCouleur);
            int b = rand.nextInt(maximumCouleur);

            g2.setColor(new Color(r,gr,b));

            g2.fillRoundRect(valeurXCollecte-7,valeurYCollecte-7,14, 14, 14, 14);
            g2.fillRect(valeurXDepot-7,valeurYDepot-7,14, 14);


        }
    }

    public void afficherTournee(){

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


    public void maxLongitudeLatitudeCarte(){
        double maxLongitude = 0.0D;
        double maxLatitude = 0.0D;
        double minLongitude = 1000.0D;
        double minLatitude = 1000.0D;
        for (Map.Entry mapentry : carte.getListeAdresses().entrySet()) {
            Adresse adresseCourante = (Adresse) mapentry.getValue();
            if(adresseCourante.getLongitude() > maxLongitude){
                maxLongitude = adresseCourante.getLongitude();
            }
            if(adresseCourante.getLatitude() > maxLatitude){
                maxLatitude = adresseCourante.getLatitude();
            }
            if(adresseCourante.getLatitude() < minLatitude){
                minLatitude = adresseCourante.getLatitude();
            }
            if(adresseCourante.getLongitude() < minLongitude){
                minLongitude = adresseCourante.getLongitude();
            }
        }
        maxLongitudeCarte = maxLongitude;
        maxLatitudeCarte = maxLatitude;
        minLatitudeCarte = minLatitude;
        minLongitudeCarte = minLongitude;

        System.out.println("maxLongitude : "+maxLongitude
                + " | maxLatitude: " + maxLatitude
                + " | minLatitude: " + minLatitude
                + " | minLongitude: " + minLongitude);
    }
}
