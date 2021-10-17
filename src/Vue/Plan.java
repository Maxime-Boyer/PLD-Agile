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
import java.util.Map;

public class Plan extends JPanel {
    int largeurEcran;
    int hauteurEcran;
    double maxLongitudeCarte;
    double maxLatitudeCarte;
    double minLatitudeCarte;
    double minLongitudeCarte;
    Carte carte;


    public Plan(int largeurEcran, int hauteurEcran, Font policeTexte) throws ParserConfigurationException, SAXException {

        this.largeurEcran = largeurEcran;
        this.hauteurEcran = hauteurEcran;

        // propriétés du pannel principal
        this.setBounds(0, 0, largeurEcran , hauteurEcran);
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

        //Créer le bouton
        JButton btn = new JButton("Cliquez ici");
        //Définir la position du bouton
        btn.setBounds(100,100,100,40);
        //Ajouter le bouton au frame
        frame.add(btn);

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
        //System.out.println("coeff X : " + coeffX);
        //System.out.println("maxLongitudeCarte : " + maxLongitudeCarte );
        return valeurXPixel;
    }

    public int valeurY(float latitude){
        System.out.println("latitude " + latitude);
        //float ecartLatitude = maxLatitudeCarte - minLatitudeCarte;

        float ecartLatitude = 0.022F;
        float coeffY = hauteurEcran / ecartLatitude;
        int valeurYPixel = (int) Math.ceil((maxLatitudeCarte - latitude)*coeffY);
        //System.out.println("coeff Y : " + coeffY);
        //System.out.println("ecartLatitude : " + ecartLatitude);
        return valeurYPixel;
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
        System.out.println("maxLongitude : "+maxLongitude
                + " | maxLatitude: " + maxLatitude
                + " | minLatitude: " + minLatitude
                + " | minLongitude: " + minLongitude);
    }



}
