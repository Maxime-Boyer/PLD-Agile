package Vue;

import Model.Carte;
import Model.LecteurXML;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;

public class Plan extends JPanel {
    int largeurEcran;
    int hauteurEcran;
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

        yourJFrame.dispose();
    }

    public void paint(Graphics g)
    { super.paint(g);
        
        g.drawString("HELLO JAVA");
    }

    public float valeurY(float longitude, float latitude){
        (hauteurEcran/(2*Math.PI))*Math.cos(latitude)*Math.sin(longitude);
        return 0.0F;
    }

    public float valeurX(float longitude, float latitude){
        (largeurEcran/(2*Math.PI))*Math.cos(latitude)*Math.cos(longitude) ;
        return 0.0F;
    }
}
