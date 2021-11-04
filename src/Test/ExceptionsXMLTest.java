package Test;

import Exceptions.*;
import Model.Carte;
import Model.LecteurXML;
import Model.Tournee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ExceptionsXMLTest {

    private Carte carte;
    private Tournee tournee;

    /**
     * Instancie une carte et une tournée vide
     *
     */
    @BeforeEach
    void init() {
        carte = new Carte();
        tournee = new Tournee();
    }

    @Test
    void exceptionAbsenceVersionEncodage() {
        try {
            LecteurXML lecteurXML = new LecteurXML();
            Carte carte = new Carte();
            carte = lecteurXML.lectureCarte("./src/FichiersXMLTest/mediumMapEncodageVersionTest.xml");
        } catch (AttributsIntersectionsExceptions attributsIntersectionsExceptions) {
            attributsIntersectionsExceptions.printStackTrace();
            fail();
        } catch (PresenceEncodingEtVersionException e) {
            //Test passe
        } catch (AttributsSegmentsExceptions attributsSegmentsExceptions) {
            fail();
        } catch (ParserConfigurationException e) {
            fail();
        } catch (IOException e) {
            fail();
        } catch (NegatifLongitudeException e) {
            fail();
        } catch (TagNameMapException e) {
            fail();
        } catch (NegatifLatitudeException e) {
            fail();
        } catch (SAXException e) {
            fail();
        }

    }

    @Test
    void exceptionNombreAttributBaliseSegment() {
        try {
            LecteurXML lecteurXML = new LecteurXML();
            Carte carte = new Carte();
            carte = lecteurXML.lectureCarte("./src/FichiersXMLTest/mediumMapAttributSegmentNombreTest.xml");
        } catch (AttributsIntersectionsExceptions attributsIntersectionsExceptions) {
            attributsIntersectionsExceptions.printStackTrace();
            fail();
        } catch (PresenceEncodingEtVersionException e) {
            e.printStackTrace();
            fail();
        } catch (AttributsSegmentsExceptions attributsSegmentsExceptions) {
            attributsSegmentsExceptions.printStackTrace();

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            fail();
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        } catch (NegatifLongitudeException e) {
            e.printStackTrace();
            fail();
        } catch (TagNameMapException e) {
            e.printStackTrace();
            fail();
        } catch (NegatifLatitudeException e) {
            e.printStackTrace();
            fail();
        } catch (SAXException e) {
            e.printStackTrace();
            fail();
        }

    }




    @Test
    void exceptionMauvaisAttributSegment() {
        try {
            LecteurXML lecteurXML = new LecteurXML();
            Carte carte = new Carte();
            carte = lecteurXML.lectureCarte("./src/FichiersXMLTest/mediumMapMauvaisAttributSegment.xml");
        } catch (AttributsIntersectionsExceptions attributsIntersectionsExceptions) {
            attributsIntersectionsExceptions.printStackTrace();
            fail();
        } catch (PresenceEncodingEtVersionException e) {
            e.printStackTrace();
            fail();
        } catch (AttributsSegmentsExceptions attributsSegmentsExceptions) {
            //attributsSegmentsExceptions.printStackTrace();

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            fail();
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        } catch (NegatifLongitudeException e) {
            e.printStackTrace();
            fail();
        } catch (TagNameMapException e) {
            e.printStackTrace();
            fail();
        } catch (NegatifLatitudeException e) {
            e.printStackTrace();
            fail();
        } catch (SAXException e) {
            e.printStackTrace();
            fail();
        }

    }

    @Test
    void exceptionLongitudeNegative() {
        try {
            LecteurXML lecteurXML = new LecteurXML();
            Carte carte = new Carte();
            carte = lecteurXML.lectureCarte("./src/FichiersXMLTest/mediumMapLongitudeTest.xml");
        } catch (AttributsIntersectionsExceptions attributsIntersectionsExceptions) {
            attributsIntersectionsExceptions.printStackTrace();
            fail();
        } catch (PresenceEncodingEtVersionException e) {
            e.printStackTrace();
            fail();
        } catch (AttributsSegmentsExceptions attributsSegmentsExceptions) {
            attributsSegmentsExceptions.printStackTrace();
            fail();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            fail();
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        } catch (NegatifLongitudeException e) {
            e.printStackTrace();

        } catch (TagNameMapException e) {
            e.printStackTrace();
            fail();
        } catch (NegatifLatitudeException e) {
            e.printStackTrace();
            fail();
        } catch (SAXException e) {
            e.printStackTrace();
            fail();
        }

    }

    @Test
    void exceptionLatitudeNegative() {
        try {
            LecteurXML lecteurXML = new LecteurXML();
            Carte carte = new Carte();
            carte = lecteurXML.lectureCarte("./src/FichiersXMLTest/mediumMapLatitudeTest.xml");
        } catch (AttributsIntersectionsExceptions attributsIntersectionsExceptions) {
            attributsIntersectionsExceptions.printStackTrace();
            fail();
        } catch (PresenceEncodingEtVersionException e) {
            e.printStackTrace();
            fail();
        } catch (AttributsSegmentsExceptions attributsSegmentsExceptions) {
            attributsSegmentsExceptions.printStackTrace();
            fail();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            fail();
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        } catch (NegatifLongitudeException e) {
            e.printStackTrace();
            fail();
        } catch (TagNameMapException e) {
            e.printStackTrace();
            fail();
        } catch (NegatifLatitudeException e) {
            e.printStackTrace();

        } catch (SAXException e) {
            e.printStackTrace();
            fail();
        }

    }


    @Test
    void exceptionNombreAttributBaliseIntersection() {
        try {
            LecteurXML lecteurXML = new LecteurXML();
            Carte carte = new Carte();
            carte = lecteurXML.lectureCarte("./src/FichiersXMLTest/mediumMapAttributIntersectionNombreTest.xml");
        } catch (AttributsIntersectionsExceptions attributsIntersectionsExceptions) {
            attributsIntersectionsExceptions.printStackTrace();

        } catch (PresenceEncodingEtVersionException e) {
            e.printStackTrace();
            fail();
        } catch (AttributsSegmentsExceptions attributsSegmentsExceptions) {
            attributsSegmentsExceptions.printStackTrace();
            fail();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            fail();
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        } catch (NegatifLongitudeException e) {
            e.printStackTrace();
            fail();
        } catch (TagNameMapException e) {
            e.printStackTrace();
            fail();
        } catch (NegatifLatitudeException e) {
            e.printStackTrace();
            fail();
        } catch (SAXException e) {
            e.printStackTrace();
            fail();
        }

    }
}