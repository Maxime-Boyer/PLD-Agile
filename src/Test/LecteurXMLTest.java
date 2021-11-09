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

/**
 * Classe de test de la classe LecteurXML
 */
class LecteurXMLTest {

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

    /**
     * Test chargement d'une carte à partir d'un fichier XML correct sans exception
     */
    @Test
    void lectureCarte() {
        try {
            LecteurXML lecteurXML = new LecteurXML();
            Carte carte = new Carte();
            carte = lecteurXML.lectureCarte("./src/FichiersXML/mediumMap.xml", carte);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            fail();
        } catch (SAXException e) {
            e.printStackTrace();
            fail();
        } catch (AttributsIntersectionsException attributsIntersectionsException) {
            attributsIntersectionsException.printStackTrace();
        } catch (PresenceEncodingEtVersionException e) {
            e.printStackTrace();
            fail();
        } catch (AttributsSegmentsException attributsSegmentsException) {
            attributsSegmentsException.printStackTrace();
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
        }
    }

    /**
     * Test chargement d'une carte à partir d'un fichier XML correct pour vérifier si on lit bien le bon nombre d'Adresse
     */
    @Test
    void lectureAddresseCarte() throws ParserConfigurationException, SAXException, AttributsIntersectionsException, PresenceEncodingEtVersionException, AttributsSegmentsException, IOException, NegatifLongitudeException, TagNameMapException, NegatifLatitudeException {
        LecteurXML lecteurXML = new LecteurXML();
        Carte carte = new Carte();
        carte = lecteurXML.lectureCarte("./src/FichiersXML/mediumMap.xml", carte);
        assertEquals(1448, carte.getListeAdresses().size());
    }

    /**
     * Test chargement d'une carte à partir d'un fichier XML correct pour vérifier si on lit bien le bon nombre d'Adresse
     */
    @Test
    void lectureSegmentCarte() throws ParserConfigurationException, SAXException, AttributsIntersectionsException, PresenceEncodingEtVersionException, AttributsSegmentsException, IOException, NegatifLongitudeException, TagNameMapException, NegatifLatitudeException {

        LecteurXML lecteurXML = new LecteurXML();
        Carte carte = new Carte();
        carte = lecteurXML.lectureCarte("./src/FichiersXML/mediumMap.xml", carte);
        assertEquals(3097, carte.getListeSegments().size());

    }

    /**
     * Test chargement d'une carte à partir d'un fichier XML correct pour vérifier la présence d'une Adresse précise
     */
    @Test
    void presenceAdressePreciseDansCarte() throws ParserConfigurationException, SAXException, AttributsIntersectionsException, PresenceEncodingEtVersionException, AttributsSegmentsException, IOException, NegatifLongitudeException, TagNameMapException, NegatifLatitudeException {

        LecteurXML lecteurXML = new LecteurXML();
        Carte carte = new Carte();
        carte = lecteurXML.lectureCarte("./src/FichiersXML/mediumMap.xml", carte);
        assertTrue(carte.getListeAdresses().containsKey(3370328152L));
    }

    /**
     * Test vérification d'une Adresse d'un fichier XML a bien les bonnes valeurs de longitude et latitude
     */
    @Test
    void verificationLonEtLatSurAdresse() throws ParserConfigurationException, SAXException, AttributsIntersectionsException, PresenceEncodingEtVersionException, AttributsSegmentsException, IOException, NegatifLongitudeException, TagNameMapException, NegatifLatitudeException {
        LecteurXML lecteurXML = new LecteurXML();
        Carte carte = new Carte();
        carte = lecteurXML.lectureCarte("./src/FichiersXML/mediumMap.xml", carte);
        assertEquals(carte.obtenirAdresseParId(1701038065L).getLatitude(), 45.743904);
        assertEquals(carte.obtenirAdresseParId(1701038065L).getLongitude(), 4.893847);

    }

    /**
     * Test vérification du remplissage de la tournee en lisant un fichier xml de requetes bien formé
     */
    @Test
    void lectureRequete() throws ParserConfigurationException, SAXException {

        try {
            LecteurXML lecteurXML = new LecteurXML();
            Carte carte = new Carte();
            Tournee tournee = new Tournee();
            carte = lecteurXML.lectureCarte("./src/FichiersXML/mediumMap.xml", carte);
            tournee = lecteurXML.lectureRequete("./src/FichiersXML/requestsMedium5.xml", carte, tournee);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }



}