package Model;

import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.util.List;

public class Main {
    public static void main(String[] args) throws ParserConfigurationException, SAXException {

        //explorateur fichier
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

        // lecture de la carte
        LecteurXML lecteur = new LecteurXML();
        lecteur.lectureCarte(filename);


        fd.setDirectory("C:\\");
        fd.setFile("*.xml");
        fd.setVisible(true);
        filename = fd.getDirectory() + fd.getFile();
        if (filename == null)
            System.out.println("You cancelled the choice");
        else
            System.out.println("You chose " + filename);
        //lecteur.lectureRequete(filename, tournee);

        //List<Requete> requetes = lecteur.lectureRequete("/Users/bramss/Documents/4_IF/4IF_MOI/S1/AGILE/PLD-Agile/src/FichiersXML/requestsLarge9.xml");

        yourJFrame.dispose();
    }
}
