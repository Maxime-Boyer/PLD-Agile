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
            carte = lecteurXML.lectureCarte("./src/FichiersXMLTest/mediumMapEncodageVersionTest.xml", carte);
        } catch (AttributsIntersectionsException attributsIntersectionsException) {
            attributsIntersectionsException.printStackTrace();
            fail();
        } catch (PresenceEncodingEtVersionException e) {
            //Test passe
        } catch (AttributsSegmentsException attributsSegmentsException) {
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
            carte = lecteurXML.lectureCarte("./src/FichiersXMLTest/mediumMapAttributSegmentNombreTest.xml", carte);
        } catch (AttributsIntersectionsException attributsIntersectionsException) {
            attributsIntersectionsException.printStackTrace();
            fail();
        } catch (PresenceEncodingEtVersionException e) {
            e.printStackTrace();
            fail();
        } catch (AttributsSegmentsException attributsSegmentsException) {
            attributsSegmentsException.printStackTrace();

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
            carte = lecteurXML.lectureCarte("./src/FichiersXMLTest/mediumMapMauvaisAttributSegment.xml", carte);
        } catch (AttributsIntersectionsException attributsIntersectionsException) {
            attributsIntersectionsException.printStackTrace();
            fail();
        } catch (PresenceEncodingEtVersionException e) {
            e.printStackTrace();
            fail();
        } catch (AttributsSegmentsException attributsSegmentsException) {
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
            carte = lecteurXML.lectureCarte("./src/FichiersXMLTest/mediumMapLongitudeTest.xml", carte);
        } catch (AttributsIntersectionsException attributsIntersectionsException) {
            attributsIntersectionsException.printStackTrace();
            fail();
        } catch (PresenceEncodingEtVersionException e) {
            e.printStackTrace();
            fail();
        } catch (AttributsSegmentsException attributsSegmentsException) {
            attributsSegmentsException.printStackTrace();
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
            carte = lecteurXML.lectureCarte("./src/FichiersXMLTest/mediumMapLatitudeTest.xml", carte);
        } catch (AttributsIntersectionsException attributsIntersectionsException) {
            attributsIntersectionsException.printStackTrace();
            fail();
        } catch (PresenceEncodingEtVersionException e) {
            e.printStackTrace();
            fail();
        } catch (AttributsSegmentsException attributsSegmentsException) {
            attributsSegmentsException.printStackTrace();
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
            carte = lecteurXML.lectureCarte("./src/FichiersXMLTest/mediumMapAttributIntersectionNombreTest.xml", carte);
        } catch (AttributsIntersectionsException attributsIntersectionsException) {
            attributsIntersectionsException.printStackTrace();

        } catch (PresenceEncodingEtVersionException e) {
            e.printStackTrace();
            fail();
        } catch (AttributsSegmentsException attributsSegmentsException) {
            attributsSegmentsException.printStackTrace();
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

    @Test
    void exceptionAbsenceVersionEncodageRequests() {
        try {
            LecteurXML lecteurXML = new LecteurXML();
            Tournee tournee = new Tournee();
            Carte carte = new Carte();
            tournee = lecteurXML.lectureRequete("./src/FichiersXMLTest/mediumRequestsEncodageVersionTest.xml", carte);
        } catch (IncompatibleLatitudeException e) {
            e.printStackTrace();
            fail();
        } catch (AttributsRequestsException attributsRequestsException) {
            attributsRequestsException.printStackTrace();
            fail();
        } catch (AbsenceBaliseDepotException AbsenceBaliseDepotException) {
            AbsenceBaliseDepotException.printStackTrace();
            fail();
        } catch (PresenceEncodingEtVersionException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        } catch (IncompatibleLongitudeException e) {
            e.printStackTrace();
            fail();
        } catch (NegatifLongitudeException e) {
            e.printStackTrace();
            fail();
        } catch (TagNameMapException e) {
            e.printStackTrace();
            fail();
        } catch (SAXException e) {
            e.printStackTrace();
            fail();
        } catch (AbsenceBaliseRequestException e) {
            e.printStackTrace();
            fail();
        } catch (IncompatibleAdresseException e) {
            e.printStackTrace();
            fail();
        } catch (AttributsDepotException e) {
            e.printStackTrace();
            fail();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            fail();
        } catch (NegatifLatitudeException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void exceptionBaliseIncorrectRequests() {
        try {
            LecteurXML lecteurXML = new LecteurXML();
            Tournee tournee = new Tournee();
            Carte carte = new Carte();
            tournee = lecteurXML.lectureRequete("./src/FichiersXMLTest/mediumRequestsBaliseIncorrectTest.xml", carte);
        } catch (IncompatibleLatitudeException e) {
            e.printStackTrace();
            fail();
        } catch (AttributsRequestsException e) {
            e.printStackTrace();
            fail();
        } catch (AbsenceBaliseDepotException e) {
            e.printStackTrace();
            fail();
        } catch (PresenceEncodingEtVersionException e) {
            e.printStackTrace();
            fail();
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        } catch (IncompatibleLongitudeException e) {
            e.printStackTrace();
            fail();
        } catch (NegatifLongitudeException e) {
            e.printStackTrace();
            fail();
        } catch (TagNameMapException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
            fail();
        } catch (AbsenceBaliseRequestException e) {
            e.printStackTrace();
            fail();
        } catch (IncompatibleAdresseException e) {
            e.printStackTrace();
            fail();
        } catch (AttributsDepotException e) {
            e.printStackTrace();
            fail();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            fail();
        } catch (NegatifLatitudeException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void exceptionAbsenceBaliseDepotException() {
        try {
            LecteurXML lecteurXML = new LecteurXML();
            Tournee tournee = new Tournee();
            Carte carte = new Carte();
            tournee = lecteurXML.lectureRequete("./src/FichiersXMLTest/mediumRequestsAbsenceBaliseDepotTest.xml", carte);
        } catch (IncompatibleLatitudeException e) {
            e.printStackTrace();
            fail();
        } catch (AttributsRequestsException e) {
            e.printStackTrace();
            fail();
        } catch (AbsenceBaliseDepotException absenceBaliseDepotException) {
            absenceBaliseDepotException.printStackTrace();
        } catch (PresenceEncodingEtVersionException e) {
            e.printStackTrace();
            fail();
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        } catch (IncompatibleLongitudeException e) {
            e.printStackTrace();
            fail();
        } catch (NegatifLongitudeException e) {
            e.printStackTrace();
            fail();
        } catch (TagNameMapException e) {
            e.printStackTrace();
            fail();
        } catch (SAXException e) {
            e.printStackTrace();
            fail();
        } catch (AbsenceBaliseRequestException e) {
            e.printStackTrace();
            fail();
        } catch (IncompatibleAdresseException e) {
            e.printStackTrace();
            fail();
        } catch (AttributsDepotException e) {
            e.printStackTrace();
            fail();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            fail();
        } catch (NegatifLatitudeException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void exceptionAttributDepotExceptions() {
        try {
            LecteurXML lecteurXML = new LecteurXML();
            Tournee tournee = new Tournee();
            Carte carte = new Carte();
            tournee = lecteurXML.lectureRequete("./src/FichiersXMLTest/mediumRequestsAttributDepotNombreTest.xml", carte);
        } catch (IncompatibleLatitudeException e) {
            e.printStackTrace();
            fail();
        } catch (AttributsRequestsException attributsRequestsException) {
            attributsRequestsException.printStackTrace();
            fail();
        } catch (AbsenceBaliseDepotException AbsenceBaliseDepotException) {
            AbsenceBaliseDepotException.printStackTrace();
            fail();
        } catch (PresenceEncodingEtVersionException e) {
            e.printStackTrace();
            fail();
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        } catch (IncompatibleLongitudeException e) {
            e.printStackTrace();
            fail();
        } catch (NegatifLongitudeException e) {
            e.printStackTrace();
            fail();
        } catch (TagNameMapException e) {
            e.printStackTrace();
            fail();
        } catch (SAXException e) {
            e.printStackTrace();
            fail();
        } catch (AbsenceBaliseRequestException e) {
            e.printStackTrace();
            fail();
        } catch (IncompatibleAdresseException e) {
            e.printStackTrace();
            fail();
        } catch (AttributsDepotException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            fail();
        } catch (NegatifLatitudeException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void exceptionMauvaisAttributDepotExceptions() {
        try {
            LecteurXML lecteurXML = new LecteurXML();
            Tournee tournee = new Tournee();
            Carte carte = new Carte();
            tournee = lecteurXML.lectureRequete("./src/FichiersXMLTest/mediumRequestsMauvaisAttributDepotTest.xml", carte);
        } catch (IncompatibleLatitudeException e) {
            e.printStackTrace();
            fail();
        } catch (AttributsRequestsException e) {
            e.printStackTrace();
            fail();
        } catch (AbsenceBaliseDepotException e) {
            e.printStackTrace();
            fail();
        } catch (PresenceEncodingEtVersionException e) {
            e.printStackTrace();
            fail();
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        } catch (IncompatibleLongitudeException e) {
            e.printStackTrace();
            fail();
        } catch (NegatifLongitudeException e) {
            e.printStackTrace();
            fail();
        } catch (TagNameMapException e) {
            e.printStackTrace();
            fail();
        } catch (SAXException e) {
            e.printStackTrace();
            fail();
        } catch (AbsenceBaliseRequestException e) {
            e.printStackTrace();
            fail();
        } catch (IncompatibleAdresseException e) {
            e.printStackTrace();
            fail();
        } catch (AttributsDepotException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            fail();
        } catch (NegatifLatitudeException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void exceptionMauvaiseValeurAttributDepotExceptions() {
        try {
            LecteurXML lecteurXML = new LecteurXML();
            Tournee tournee = new Tournee();
            Carte carte = new Carte();
            tournee = lecteurXML.lectureRequete("./src/FichiersXMLTest/mediumRequestsMauvaiseValeurAttributDepotTest.xml", carte);
        } catch (IncompatibleLatitudeException e) {
            e.printStackTrace();
            fail();
        } catch (AttributsRequestsException e) {
            e.printStackTrace();
            fail();
        } catch (AbsenceBaliseDepotException e) {
            e.printStackTrace();
            fail();
        } catch (PresenceEncodingEtVersionException e) {
            e.printStackTrace();
            fail();
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        } catch (IncompatibleLongitudeException e) {
            e.printStackTrace();
            fail();
        } catch (NegatifLongitudeException e) {
            e.printStackTrace();
            fail();
        } catch (TagNameMapException e) {
            e.printStackTrace();
            fail();
        } catch (SAXException e) {
            e.printStackTrace();
            fail();
        } catch (AbsenceBaliseRequestException e) {
            e.printStackTrace();
            fail();
        } catch (IncompatibleAdresseException e) {
            e.printStackTrace();
            fail();
        } catch (AttributsDepotException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            fail();
        } catch (NegatifLatitudeException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void exceptionMauvaiseValeurAttributDepotExceptions2() {
        try {
            LecteurXML lecteurXML = new LecteurXML();
            Tournee tournee = new Tournee();
            carte = lecteurXML.lectureCarte("./src/FichiersXML/mediumMap.xml", carte);
            tournee = lecteurXML.lectureRequete("./src/FichiersXMLTest/mediumRequestsMauvaiseValeurAttributDepotTest2.xml", carte);
        } catch (IncompatibleLatitudeException e) {
            e.printStackTrace();
            fail();
        } catch (AttributsRequestsException e) {
            e.printStackTrace();
            fail();
        } catch (AbsenceBaliseDepotException e) {
            e.printStackTrace();
            fail();
        } catch (PresenceEncodingEtVersionException e) {
            e.printStackTrace();
            fail();
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        } catch (IncompatibleLongitudeException e) {
            e.printStackTrace();
            fail();
        } catch (NegatifLongitudeException e) {
            e.printStackTrace();
            fail();
        } catch (TagNameMapException e) {
            e.printStackTrace();
            fail();
        } catch (SAXException e) {
            e.printStackTrace();
            fail();
        } catch (AbsenceBaliseRequestException e) {
            e.printStackTrace();
            fail();
        } catch (IncompatibleAdresseException e) {
            e.printStackTrace();
            fail();
        } catch (AttributsDepotException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            fail();
        } catch (NegatifLatitudeException e) {
            e.printStackTrace();
            fail();
        } catch (AttributsSegmentsException e) {
            e.printStackTrace();
            fail();
        } catch (AttributsIntersectionsException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void exceptionBaliseRequestsManquanteExceptions() {
        try {
            LecteurXML lecteurXML = new LecteurXML();
            Tournee tournee = new Tournee();
            carte = lecteurXML.lectureCarte("./src/FichiersXML/mediumMap.xml", carte);
            tournee = lecteurXML.lectureRequete("./src/FichiersXMLTest/mediumRequestsBaliseRequestsManquanteTest.xml", carte);
        } catch (IncompatibleLatitudeException e) {
            e.printStackTrace();
            fail();
        } catch (AttributsRequestsException e) {
            e.printStackTrace();
            fail();
        } catch (AbsenceBaliseDepotException e) {
            e.printStackTrace();
            fail();
        } catch (PresenceEncodingEtVersionException e) {
            e.printStackTrace();
            fail();
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        } catch (IncompatibleLongitudeException e) {
            e.printStackTrace();
            fail();
        } catch (NegatifLongitudeException e) {
            e.printStackTrace();
            fail();
        } catch (TagNameMapException e) {
            e.printStackTrace();
            fail();
        } catch (SAXException e) {
            e.printStackTrace();
            fail();
        } catch (AbsenceBaliseRequestException e) {
            e.printStackTrace();
        } catch (IncompatibleAdresseException e) {
            e.printStackTrace();

            fail();
        } catch (AttributsDepotException e) {
            e.printStackTrace();
            fail();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            fail();
        } catch (NegatifLatitudeException e) {
            e.printStackTrace();
            fail();
        } catch (AttributsSegmentsException e) {
            e.printStackTrace();
            fail();
        } catch (AttributsIntersectionsException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void exceptionNombreAttributBaliseRequest() {
        try {
            LecteurXML lecteurXML = new LecteurXML();
            Tournee tournee = new Tournee();
            carte = lecteurXML.lectureCarte("./src/FichiersXML/mediumMap.xml", carte);
            tournee = lecteurXML.lectureRequete("./src/FichiersXMLTest/mediumRequestsAttributRequestNombreTest.xml", carte);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            fail();
        } catch (SAXException e) {
            e.printStackTrace();
            fail();
        } catch (IncompatibleAdresseException e) {
            e.printStackTrace();
            fail();
        } catch (AbsenceBaliseDepotException e) {
            e.printStackTrace();
            fail();
        } catch (PresenceEncodingEtVersionException e) {
            e.printStackTrace();
            fail();
        } catch (AttributsDepotException e) {
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
        } catch (AbsenceBaliseRequestException e) {
            e.printStackTrace();
            fail();
        } catch (IncompatibleLatitudeException e) {
            e.printStackTrace();
            fail();
        } catch (AttributsRequestsException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        } catch (IncompatibleLongitudeException e) {
            e.printStackTrace();
            fail();
        } catch (AttributsSegmentsException e) {
            e.printStackTrace();
            fail();
        } catch (AttributsIntersectionsException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void exceptionMauvaisAttributBaliseRequest() {
        try {
            LecteurXML lecteurXML = new LecteurXML();
            Tournee tournee = new Tournee();
            carte = lecteurXML.lectureCarte("./src/FichiersXML/mediumMap.xml", carte);
            tournee = lecteurXML.lectureRequete("./src/FichiersXMLTest/mediumRequestsAttributRequestNombreTest.xml", carte);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            fail();
        } catch (SAXException e) {
            e.printStackTrace();
            fail();
        } catch (IncompatibleAdresseException e) {
            e.printStackTrace();
            fail();
        } catch (AbsenceBaliseDepotException e) {
            e.printStackTrace();
            fail();
        } catch (PresenceEncodingEtVersionException e) {
            e.printStackTrace();
            fail();
        } catch (AttributsDepotException e) {
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
        } catch (AbsenceBaliseRequestException e) {
            e.printStackTrace();
            fail();
        } catch (IncompatibleLatitudeException e) {
            e.printStackTrace();
            fail();
        } catch (AttributsRequestsException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        } catch (IncompatibleLongitudeException e) {
            e.printStackTrace();
            fail();
        } catch (AttributsSegmentsException e) {
            e.printStackTrace();
            fail();
        } catch (AttributsIntersectionsException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void exceptionAdresseRetrait() {
        try {
            LecteurXML lecteurXML = new LecteurXML();
            Tournee tournee = new Tournee();
            tournee = lecteurXML.lectureRequete("./src/FichiersXMLTest/requestsMediumAdresseRetrait.xml", carte);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            fail();
        } catch (SAXException e) {
            e.printStackTrace();
            fail();
        } catch (IncompatibleAdresseException e) {
            e.printStackTrace();
        } catch (AbsenceBaliseDepotException e) {
            e.printStackTrace();
            fail();
        } catch (PresenceEncodingEtVersionException e) {
            e.printStackTrace();
            fail();
        } catch (AttributsDepotException e) {
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
        } catch (AbsenceBaliseRequestException e) {
            e.printStackTrace();
            fail();
        } catch (IncompatibleLatitudeException e) {
            e.printStackTrace();
            fail();
        } catch (AttributsRequestsException e) {
            e.printStackTrace();
            fail();
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        } catch (IncompatibleLongitudeException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void exceptionAdresseDepot() {
        try {
            LecteurXML lecteurXML = new LecteurXML();
            Tournee tournee = new Tournee();
            tournee = lecteurXML.lectureRequete("./src/FichiersXMLTest/requestsMediumAdresseDepot.xml", carte);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            fail();
        } catch (SAXException e) {
            e.printStackTrace();
            fail();
        } catch (IncompatibleAdresseException e) {
            e.printStackTrace();
        } catch (AbsenceBaliseDepotException e) {
            e.printStackTrace();
            fail();
        } catch (PresenceEncodingEtVersionException e) {
            e.printStackTrace();
            fail();
        } catch (AttributsDepotException e) {
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
        } catch (AbsenceBaliseRequestException e) {
            e.printStackTrace();
            fail();
        } catch (IncompatibleLatitudeException e) {
            e.printStackTrace();
            fail();
        } catch (AttributsRequestsException e) {
            e.printStackTrace();
            fail();
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        } catch (IncompatibleLongitudeException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    void exceptionAdresseDepart() {
        try {
            LecteurXML lecteurXML = new LecteurXML();
            Tournee tournee = new Tournee();
            tournee = lecteurXML.lectureRequete("./src/FichiersXMLTest/requestsMediumAdresseDepart.xml", carte);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            fail();
        } catch (SAXException e) {
            e.printStackTrace();
            fail();
        } catch (IncompatibleAdresseException e) {
            e.printStackTrace();
        } catch (AbsenceBaliseDepotException e) {
            e.printStackTrace();
            fail();
        } catch (PresenceEncodingEtVersionException e) {
            e.printStackTrace();
            fail();
        } catch (AttributsDepotException e) {
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
        } catch (AbsenceBaliseRequestException e) {
            e.printStackTrace();
            fail();
        } catch (IncompatibleLatitudeException e) {
            e.printStackTrace();
            fail();
        } catch (AttributsRequestsException e) {
            e.printStackTrace();
            fail();
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        } catch (IncompatibleLongitudeException e) {
            e.printStackTrace();
            fail();
        }
    }
}