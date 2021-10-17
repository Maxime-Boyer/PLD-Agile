package Vue;

import Model.Adresse;
import Model.Carte;
import Model.LecteurXML;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.util.Map;

public class Plan extends JPanel {
    int largeurEcran;
    int hauteurEcran;
    float maxLongitudeCarte;
    float maxLatitudeCarte;
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

        yourJFrame.dispose();
    }

    public void paint(Graphics g)
    { super.paint(g);
        
        //g.drawString("HELLO JAVA");
    }

    /*public float valeurY(float longitude, float latitude){
        (hauteurEcran/(2*Math.PI))*Math.cos(latitude)*Math.sin(longitude);
        return 0.0F;
    }

    public float valeurX(float longitude, float latitude){
        (largeurEcran/(2*Math.PI))*Math.cos(latitude)*Math.cos(longitude) ;
        return 0.0F;
    }*/

    public void maxLongitudeLatitudeCarte(){
        float maxLongitude = 0.0F;
        float maxLatitude = 0.0F;
        for (Map.Entry mapentry : carte.getListeAdresses().entrySet()) {
            Adresse adresseCourante = (Adresse) mapentry.getValue();
            if(adresseCourante.getLongitude() > maxLongitude){
                maxLongitude = adresseCourante.getLongitude();
            }
            if(adresseCourante.getLatitude() > maxLatitude){
                maxLatitude = adresseCourante.getLatitude();
            }


            System.out.println("maxLongitude : "+maxLongitude
                        + " | maxLatitude: " + maxLatitude);
        }
    }
}
