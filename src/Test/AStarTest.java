package Test;

import Algorithmie.Astar2;
import Algorithmie.CalculateurTournee;
import Exceptions.AStarImpossibleException;
import Model.*;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de test de l'ensemble des classes AStar
 */
public class AStarTest {

    /**
     * Test de detection des AStar impossibles : il n'est pas possible de rejoindre deux adresses du graphe.
     */
    @Test
    void detectionAStarImpossible() throws ParserConfigurationException,SAXException {
        try {
            LecteurXML lecteurXML = new LecteurXML();
            Carte carte = new Carte();
            carte = lecteurXML.lectureCarte("./src/FichiersXML/mapImpossibleTest.xml");
            Tournee tournee = new Tournee();
            tournee = lecteurXML.lectureRequete("./src/FichiersXML/requestsImpossibleTest.xml");
            CalculateurTournee calculateurTournee = new CalculateurTournee(carte, tournee);
            calculateurTournee.calculerGrapheCompletDesEtapes(new Astar2(carte));
        } catch (AStarImpossibleException e){
            e.printStackTrace();
        }
    }

    /**
     * Test du Astar sur un graphe mauvais pour l'heuristique.
     */
    @Test
    void verificationBonFonctionnementAStar1() throws ParserConfigurationException,SAXException {

        LecteurXML lecteurXML = new LecteurXML();
        Carte carte = new Carte();
        carte = lecteurXML.lectureCarte("./src/FichiersXML/mapAstar1Test.xml");
        Tournee tournee = new Tournee();
        tournee = lecteurXML.lectureRequete("./src/FichiersXML/requestsAstar1Test.xml");
        CalculateurTournee calculateurTournee = new CalculateurTournee(carte, tournee);

        /*
        for(Map.Entry<Long,Adresse> entry : carte.getListeAdresses().entrySet()){
            for(Map.Entry<Long,Adresse> entryBis : carte.getListeAdresses().entrySet()){
                System.out.println(entry.getKey()+"  ;  "+entryBis.getKey()+"  ;  "+distance(entry.getValue(),entryBis.getValue()));
            }
        }*/
        Adresse etapeDepart = tournee.getListeRequetes().get(0).getEtapeCollecte();
        Adresse etapeArrivee = tournee.getListeRequetes().get(0).getEtapeCollecte();

        Astar2 astar = new Astar2(carte);
        CheminEntreEtape cee = astar.chercherCheminEntreEtape(new Etape(etapeDepart.getLatitude(),etapeDepart.getLongitude(),etapeDepart.getIdAdresse(),0, LocalTime.of(0,0,0,0)), new Etape(etapeArrivee.getLatitude(),etapeArrivee.getLongitude(),etapeArrivee.getIdAdresse(),0,LocalTime.of(0,0,0,0)));

        long[] idAdresseTheorique = {1, 5, 6, 7, 4};
        Segment s;

        //FIXME size == 0 ...
        System.out.println("size : " + cee.getListeSegment().size());
        for(int i = 0; i<cee.getListeSegment().size()-1 ; i++){
            s = cee.getListeSegment().get(i);
            System.out.println(s);
            if(s.getOrigine().getIdAdresse() != idAdresseTheorique[i] || s.getDestination().getIdAdresse() != idAdresseTheorique[i+1]){
                fail();
            }
        }

    }

    /**
     * Test de Astar sur un graphe ayant beaucoup de branches
     */
    @Test
    void verificationBonFonctionnementAStar2() throws ParserConfigurationException,SAXException {
        try {
            LecteurXML lecteurXML = new LecteurXML();
            Carte carte = new Carte();
            carte = lecteurXML.lectureCarte("./src/FichiersXML/mapLargeTest.xml");
            Tournee tournee = new Tournee();
            tournee = lecteurXML.lectureRequete("./src/FichiersXML/requestsLargeTest.xml");
            CalculateurTournee calculateurTournee = new CalculateurTournee(carte, tournee);
            calculateurTournee.calculerGrapheCompletDesEtapes(new Astar2(carte));
        } catch (AStarImpossibleException e){
            e.printStackTrace();
        }
    }

    /**
     * Verification du bon nombre de chemin sur une grosse tournee
     */
    @Test
    void verificationNombreDeCheminAstar() throws ParserConfigurationException,SAXException,AStarImpossibleException {

        LecteurXML lecteurXML = new LecteurXML();
        Carte carte = new Carte();
        carte = lecteurXML.lectureCarte("./src/FichiersXML/mapLargeTest.xml");
        Tournee tournee = new Tournee();
        tournee = lecteurXML.lectureRequete("./src/FichiersXML/requestsLargeTest.xml");
        CalculateurTournee calculateurTournee = new CalculateurTournee(carte, tournee);
        HashMap<Long, HashMap<Long, CheminEntreEtape>> graphComplet = calculateurTournee.calculerGrapheCompletDesEtapes(new Astar2(carte));

        int nombreDeChemins = 0;
        for(Map.Entry<Long, HashMap<Long, CheminEntreEtape>> entry : graphComplet.entrySet()){
            nombreDeChemins += entry.getValue().size();
        }
        assertEquals((tournee.getListeRequetes().size()*2) * (tournee.getListeRequetes().size()*2+1),nombreDeChemins);

    }


    /**
     * Verification de la presence de chemin non null au retour du Astar sur une grosse tournee
     */
    @Test
    void verificationCheminsNonNullAStar()  throws ParserConfigurationException,SAXException,AStarImpossibleException {

        LecteurXML lecteurXML = new LecteurXML();
        Carte carte = new Carte();
        carte = lecteurXML.lectureCarte("./src/FichiersXML/mapLargeTest.xml");
        Tournee tournee = new Tournee();
        tournee = lecteurXML.lectureRequete("./src/FichiersXML/requestsLargeTest.xml");
        CalculateurTournee calculateurTournee = new CalculateurTournee(carte, tournee);
        HashMap<Long, HashMap<Long, CheminEntreEtape>> graphComplet = calculateurTournee.calculerGrapheCompletDesEtapes(new Astar2(carte));

        for(Map.Entry<Long, HashMap<Long, CheminEntreEtape>> entry : graphComplet.entrySet()){
            for(Map.Entry<Long, CheminEntreEtape> entryBis : entry.getValue().entrySet()){
                if(entryBis.getValue() == null){
                    fail();
                }
            }
        }
    }

    /**
     * Verification du bon fonctionnement du Astar lorsque le depart et l'arrivee correspondent a la meme adresse
     */
    @Test
    void detectionMemeDepartAriveeAStar()  throws ParserConfigurationException,SAXException,AStarImpossibleException {

        LecteurXML lecteurXML = new LecteurXML();
        Carte carte = new Carte();
        carte = lecteurXML.lectureCarte("./src/FichiersXML/mapLargeTest.xml");
        Tournee tournee = new Tournee();
        tournee = lecteurXML.lectureRequete("./src/FichiersXML/requestsLargeTest.xml");
        CalculateurTournee calculateurTournee = new CalculateurTournee(carte, tournee);

        Astar2 astar = new Astar2(carte);
        Etape etape = new Etape(tournee.getAdresseDepart().getLatitude(),tournee.getAdresseDepart().getLongitude(),tournee.getAdresseDepart().getIdAdresse(),0,LocalTime.of(0,0,0,0));
        astar.chercherCheminEntreEtape(etape, etape);

    }
}
