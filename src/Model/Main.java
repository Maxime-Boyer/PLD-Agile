package Model;

import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;

public class Main {
    public static void main(String[] args) throws ParserConfigurationException, SAXException {
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
        lecteur.lectureCarte(filename);

    }
}
