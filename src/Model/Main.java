package Model;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;

public class Main {
    public static void main(String[] args) throws ParserConfigurationException, SAXException {
        LecteurXML lecteur = new LecteurXML();
        lecteur.lectureCarte("/Users/mac/Desktop/PLD-Agile/src/Model/smallMap.xml");

    }
}
