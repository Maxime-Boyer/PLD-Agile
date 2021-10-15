package Model;

import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.util.List;

public class Main {
    public static void main(String[] args) throws ParserConfigurationException, SAXException {
        /*JFrame yourJFrame = new JFrame();
        FileDialog fd = new FileDialog(yourJFrame, "Choose a file", FileDialog.LOAD);
        fd.setDirectory("C:\\");
        fd.setFile("*.xml");
        fd.setVisible(true);
        String filename = fd.getDirectory() + fd.getFile();
        if (filename == null) {
            System.out.println("You cancelled the choice");
        }else {
            System.out.println("You chose " + filename);
        }*/



        LecteurXML lecteur = new LecteurXML();
        lecteur.lectureCarte("/Users/bramss/Documents/4_IF/4IF_MOI/S1/AGILE/PLD-Agile/src/FichiersXML/largeMap.xml");


        /*JFrame yourJFrame2 = new JFrame();
        FileDialog fd2 = new FileDialog(yourJFrame2, "Choose a file", FileDialog.LOAD);
        fd2.setDirectory("C:\\");
        fd2.setFile("*.xml");
        fd2.setVisible(true);
        String filename2 = fd2.getDirectory() + fd2.getFile();
        if (filename2 == null) {
            System.out.println("You cancelled the choice");
        }else {
            System.out.println("You chose " + filename2);
        }*/


        List<Requete> requetes = lecteur.lectureRequete("/Users/bramss/Documents/4_IF/4IF_MOI/S1/AGILE/PLD-Agile/src/FichiersXML/requestsLarge9.xml");

        for(Requete r : requetes){
            System.out.println(r);
        }







    }
}
