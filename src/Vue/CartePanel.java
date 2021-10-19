package Vue;

import Model.Adresse;
import Model.Carte;
import Model.LecteurXML;
import Model.Tournee;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Map;

public class CartePanel extends JPanel {
    int largeur;
    int hauteur;
    double maxLongitudeCarte;
    double maxLatitudeCarte;
    double minLatitudeCarte;
    double minLongitudeCarte;
    Carte carte = new Carte();
    Tournee tournee = new Tournee();


    public CartePanel(int largeurEcran, int hauteurEcran, Font policeTexte) throws ParserConfigurationException, SAXException {

        this.largeur = (int) 3*largeurEcran/4;
        this.hauteur = (int) hauteurEcran;

        // propriétés du pannel principal
        this.setBounds(0, 0, largeur, hauteur);
        this.setBackground(Color.WHITE);
        this.setLayout(null);

        // TO REMOVE
        JLabel toRemove = new JLabel("Zone plan");
        toRemove.setFont(policeTexte);
        this.add(toRemove);


        JFrame yourJFrame = new JFrame();
        FileDialog fd = new FileDialog(yourJFrame, "Choose a file", FileDialog.LOAD);
        fd.setDirectory("C:\\");
        fd.setFile("*.xml");
        fd.setVisible(true);
        String filename = fd.getDirectory() + fd.getFile();
        if (filename == null)
            System.out.println("You cancelled the choice");
        else
            System.out.println("You chose " + filename);

        LecteurXML lecteur = new LecteurXML();
        carte = lecteur.lectureCarte(filename);

        maxLongitudeLatitudeCarte();

        //TODO : faire conversion produit croix pour lon lat en pixel
        fd.setDirectory("C:\\");
        fd.setFile("*.xml");
        fd.setVisible(true);
        filename = fd.getDirectory() + fd.getFile();
        if (filename == null)
            System.out.println("You cancelled the choice");
        else
            System.out.println("You chose " + filename);

        tournee = lecteur.lectureRequete(filename);


        yourJFrame.dispose();
        afficherTournee();
    }

    public void repaint(Graphics g) {
        super.repaint();

        paintComponent(g);

    }

    public void paintComponent(Graphics g)
    { super.paintComponent(g);

        //System.out.println("hauteur ecran : " + hauteurEcran + " largeur ecran : " + largeurEcran);

        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(1));

        // BackGround

        g2.setColor(Color.WHITE);

        g2.fillRect(0, 0, getSize().width, getSize().height);

        g2.setColor(Color.BLACK);

        System.out.println("maxLongitudeCarte" + maxLongitudeCarte);
        System.out.println("maxLatitudeCarte" + maxLatitudeCarte);
        System.out.println("minLatitudeCarte" + minLatitudeCarte);
        System.out.println("minLongitudeCarte" + minLongitudeCarte);

        for (int i = 0; i < carte.getListeSegments().size(); i++) {
            Adresse origine = carte.getListeSegments().get(i).getOrigine();
            Adresse destination = carte.getListeSegments().get(i).getDestination();
            int origineX = valeurX(origine.getLongitude());
            int origineY = valeurY(origine.getLatitude());
            int destinationX = valeurX(destination.getLongitude());
            int destinationY = valeurY(destination.getLatitude());
            //System.out.println("x1 : " + origineX + " y1 : " + origineY + " x2 : " + destinationX + " y2 : " + destinationY);

            g2.drawLine(origineX, origineY, destinationX, destinationY);
            //System.out.println("Segment " + i);
        }
        //g.drawString("HELLO JAVA");
    }

    public int valeurX(double longitude){

        //System.out.println("longitude " + longitude);
        //float ecartLongitude = maxLongitudeCarte - minLongitudeCarte;
        double ecartLongitude = maxLongitudeCarte - minLongitudeCarte;
        double coeffX = largeur / ecartLongitude;
        int valeurXPixel = (int) Math.ceil((longitude - minLongitudeCarte)*coeffX);
        //System.out.println("coeff X : " + coeffX);
        //System.out.println("maxLongitudeCarte : " + maxLongitudeCarte );
        return valeurXPixel;
    }

    public int valeurY(double latitude){
        //System.out.println("latitude " + latitude);
        double ecartLatitude = maxLatitudeCarte - minLatitudeCarte;
        double coeffY = hauteur / ecartLatitude;
        int valeurYPixel = (int) Math.ceil((maxLatitudeCarte - latitude)*coeffY);
        //System.out.println("coeff Y : " + coeffY);
        //System.out.println("ecartLatitude : " + ecartLatitude);
        return valeurYPixel;
    }

    public void afficherTournee(){

        for (int i = 0; i < tournee.getListeRequetes().size(); i++) {
            Adresse collecte = tournee.getListeRequetes().get(i).getEtapeCollecte();
            Adresse depot = tournee.getListeRequetes().get(i).getEtapeDepot();

            double lonCollecte = collecte.getLongitude();
            double latCollecte = collecte.getLatitude();
            double lonDepot = depot.getLongitude();
            double latDepot = depot.getLatitude();

            System.out.println("lonCollecte " + lonCollecte);
            System.out.println("latCollecte " + latCollecte);
            System.out.println("lonDepot " + lonDepot);
            System.out.println("latDepot " + latDepot);

            int valeurXCollecte = valeurX(lonCollecte);
            int valeurYCollecte = valeurY(latCollecte);
            int valeurXDepot = valeurX(lonDepot);
            int valeurYDepot = valeurY(latDepot);

            System.out.println("valeurXCollecte " + valeurXCollecte);
            System.out.println("valeurYCollecte " + valeurYCollecte);
            System.out.println("valeurXDepot " + valeurXDepot);
            System.out.println("valeurYDepot " + valeurYDepot);

            JButton boutonCollecte = new JButton();
            JButton boutonDepot = new JButton();

            boutonCollecte.setBounds(valeurXCollecte-2,valeurYCollecte-2, 15, 15);
            boutonDepot.setBounds(valeurXDepot-2,valeurYDepot-2, 15, 15);
            boutonCollecte.setBorderPainted(false);
            boutonDepot.setBorderPainted(false);
            boutonCollecte.setOpaque(true);
            boutonDepot.setOpaque(true);
            boutonCollecte.setBackground(Color.GREEN);
            boutonDepot.setBackground(Color.GREEN);

            this.add(boutonCollecte);
            this.add(boutonDepot);


        }




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
