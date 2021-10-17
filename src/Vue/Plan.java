package Vue;

import Model.Adresse;
import Model.Carte;
import Model.LecteurXML;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

public class Plan extends JPanel {
    int largeurEcran;
    int hauteurEcran;
    float maxLongitudeCarte;
    float maxLatitudeCarte;
    float minLatitudeCarte;
    float minLongitudeCarte;
    Carte carte;


    public Plan(int largeurEcran, int hauteurEcran, Font policeTexte) throws ParserConfigurationException, SAXException {

        this.largeurEcran = largeurEcran;
        this.hauteurEcran = hauteurEcran;

        // propriétés du pannel principal
        this.setBounds(0, 0, largeurEcran * 3/4, hauteurEcran);
        this.setBackground(Color.CYAN);

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

        yourJFrame.dispose();
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

        g2.setColor(Color.RED);

        g2.fillRect(0, 0, getSize().width, getSize().height);

        g2.setColor(Color.WHITE);

        g.drawLine(0, 56, 350, 267);
        g.drawLine(56, 5, 40, 27);
        g.drawLine(189, 39, 50, 2);

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
            System.out.println("x1 : " + origineX + " y1 : " + origineY + " x2 : " + destinationX + " y2 : " + destinationY);

            g.drawLine(origineX, origineY, destinationX, destinationY);
            //System.out.println("Segment " + i);
        }
        //g.drawString("HELLO JAVA");
    }

    public int valeurX(float longitude){

        System.out.println("longitude " + longitude);
        //float ecartLongitude = maxLongitudeCarte - minLongitudeCarte;
        float ecartLongitude = 0.022F;
        float coeffX = largeurEcran / ecartLongitude;
        int valeurXPixel = (int) Math.ceil((maxLongitudeCarte - longitude)*coeffX);
        System.out.println("coeff X : " + coeffX);
        System.out.println("ecartLongitude : " + ecartLongitude);
        return valeurXPixel;
    }

    public int valeurY(float latitude){
        System.out.println("latitude " + latitude);
        //float ecartLatitude = maxLatitudeCarte - minLatitudeCarte;

        float ecartLatitude = 0.022F;
        float coeffY = hauteurEcran / ecartLatitude;
        int valeurYPixel = (int) Math.ceil((maxLatitudeCarte - latitude)*coeffY);
        System.out.println("coeff Y : " + coeffY);
        System.out.println("ecartLatitude : " + ecartLatitude);
        return valeurYPixel;
    }

    public int valeurXbis(float longitude){
        float valeurX = (longitude+180)*(largeurEcran/360);
        return (int)Math.ceil(valeurX);
    }

    public int valeurYbis(float latitude){
        float latRad = (float) (latitude*Math.PI/180);
        float mercN = (float) Math.log(Math.tan((Math.PI/4)+(latRad/2)));
        float valeurY = (float) ((hauteurEcran/2)-(largeurEcran*mercN/(2*Math.PI)));
        return (int)Math.ceil(valeurY);
    }

    public void maxLongitudeLatitudeCarte(){
        float maxLongitude = 0.0F;
        float maxLatitude = 0.0F;
        float minLongitude = 1000.0F;
        float minLatitude = 1000.0F;
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
        System.out.println("maxLongitude : "+maxLongitude
                + " | maxLatitude: " + maxLatitude
                + " | minLatitude: " + minLatitude
                + " | minLongitude: " + minLongitude);
    }



}
