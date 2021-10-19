package Test;

import Model.Carte;
import Model.LecteurXML;
import Model.Tournee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;

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
            carte = lecteurXML.lectureCarte("C:\\Users\\maxim\\IdeaProjects\\PLD-Agile\\src\\FichiersXML\\mediumMap.xml");
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            fail();
        } catch (SAXException e) {
            e.printStackTrace();
            fail();
        }
    }

    /**
     * Test chargement d'une carte à partir d'un fichier XML correct pour vérifier si on lit bien le bon nombre d'Adresse
     */
    @Test
    void lectureAddresseCarte() throws ParserConfigurationException, SAXException {
        LecteurXML lecteurXML = new LecteurXML();
        Carte carte = new Carte();
        carte = lecteurXML.lectureCarte("C:\\Users\\maxim\\IdeaProjects\\PLD-Agile\\src\\FichiersXML\\mediumMap.xml");
        assertEquals(1448, carte.getListeAdresses().size());
    }

    /**
     * Test chargement d'une carte à partir d'un fichier XML correct pour vérifier si on lit bien le bon nombre d'Adresse
     */
    @Test
    void lectureSegmentCarte() throws ParserConfigurationException, SAXException {

        LecteurXML lecteurXML = new LecteurXML();
        Carte carte = new Carte();
        carte = lecteurXML.lectureCarte("C:\\Users\\maxim\\IdeaProjects\\PLD-Agile\\src\\FichiersXML\\mediumMap.xml");
        assertEquals(3097, carte.getListeSegments().size());

    }

    /**
     * Test chargement d'une carte à partir d'un fichier XML correct pour vérifier la présence d'une Adresse précise
     */
    @Test
    void presenceAdressePreciseDansCarte() throws ParserConfigurationException, SAXException {

        LecteurXML lecteurXML = new LecteurXML();
        Carte carte = new Carte();
        carte = lecteurXML.lectureCarte("C:\\Users\\maxim\\IdeaProjects\\PLD-Agile\\src\\FichiersXML\\mediumMap.xml");
        assertTrue(carte.getListeAdresses().containsKey(3370328152L));
    }

    /**
     * Test vérification d'une Adresse d'un fichier XML a bien les bonnes valeurs de longitude et latitude
     */
    @Test
    void verificationLonEtLatSurAdresse() throws ParserConfigurationException, SAXException {

        LecteurXML lecteurXML = new LecteurXML();
        Carte carte = new Carte();
        carte = lecteurXML.lectureCarte("C:\\Users\\maxim\\IdeaProjects\\PLD-Agile\\src\\FichiersXML\\mediumMap.xml");
        assertEquals(carte.obtenirAdresseParId(1701038065L).getLatitude(), 45.743904);
        assertEquals(carte.obtenirAdresseParId(1701038065L).getLongitude(), 4.893847);
    }

    /**
     * Test vérification d'une Adresse d'un fichier XML a bien les bonnes valeurs de longitude et latitude
     */
    @Test
    void lectureRequete() throws ParserConfigurationException, SAXException {

        try {
            LecteurXML lecteurXML = new LecteurXML();
            Carte carte = new Carte();
            carte = lecteurXML.lectureCarte("C:\\Users\\maxim\\IdeaProjects\\PLD-Agile\\src\\FichiersXML\\mediumMap.xml");
            Tournee tournee = lecteurXML.lectureRequete("C:\\Users\\maxim\\IdeaProjects\\PLD-Agile\\src\\FichiersXML\\requestsMedium5.xml");

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }


}