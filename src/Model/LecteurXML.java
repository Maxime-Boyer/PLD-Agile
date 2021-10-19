package Model;

import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class LecteurXML {
    Carte carte = new Carte();

    public LecteurXML() {
    }

    /**
     *
     * @param nomFichier
     * @throws ParserConfigurationException
     * @throws SAXException
     */
    public Carte lectureCarte(String nomFichier) throws ParserConfigurationException, SAXException {
        try{
            carte = new Carte(nomFichier);
            File file = new File(nomFichier);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document =  db.parse(file);
            document.getDocumentElement().normalize();
            NodeList nListAdresse = document.getElementsByTagName("intersection");

            for (int temp = 0; temp < nListAdresse.getLength(); temp++) {
                Node nNodeAdresse = nListAdresse.item(temp);
                if (nNodeAdresse.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNodeAdresse;
                    Float latitude = Float.parseFloat(eElement.getAttribute("latitude"));
                    Float longitude = Float.parseFloat(eElement.getAttribute("longitude"));
                    Long id = Long.parseLong(eElement.getAttribute("id"));

                    Adresse adresse = new Adresse(latitude,longitude,id);
                    carte.getListeAdresses().put(id,adresse);
                    //System.out.println(carte.getListeAdresses().get(id));

                }
            }

            NodeList nListSegment = document.getElementsByTagName("segment");
            for (int temp = 0; temp < nListSegment.getLength(); temp++) {
                Node nNodeSegment = nListSegment.item(temp);
                if (nNodeSegment.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNodeSegment;
                    Double longueur = Double.parseDouble(eElement.getAttribute("length"));
                    String nom = eElement.getAttribute("name");
                    Long idOrigine = Long.parseLong(eElement.getAttribute("origin"));
                    Long idDestination = Long.parseLong(eElement.getAttribute("destination"));
                    Segment segment = new Segment(carte.obtenirAdresseParId(idOrigine),carte.obtenirAdresseParId(idDestination),nom,longueur);
                    carte.getListeSegments().add(segment);
                    //System.out.println(segment);

                }
            }
        }
        catch(IOException e){
            System.out.println(e);
        }

        finally{
            return carte;
        }
    }

    /**
     *
     * @param nomFichier
     * @throws ParserConfigurationException
     * @throws SAXException
     */
    public Tournee lectureRequete(String nomFichier) throws ParserConfigurationException, SAXException {
        List<Requete> listeRequetes = new  ArrayList<Requete>();
        Tournee tournee = new Tournee();
        try {
            File file = new File(nomFichier);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document =  db.parse(file);
            document.getDocumentElement().normalize();
            Node depot = document.getElementsByTagName("depot").item(0);
            Element eElement = (Element) depot;
            Long idAdresseDepot = Long.parseLong(eElement.getAttribute("address"));
            Adresse adresseDepot = carte.obtenirAdresseParId(idAdresseDepot);
            String depart = eElement.getAttribute("departureTime");
            tournee.setAdresseDepart(adresseDepot);
            LocalTime heureDepart = LocalTime.parse(depart, DateTimeFormatter.ofPattern("H:m:s"));
            /*int hour = heureDepart.get(ChronoField.CLOCK_HOUR_OF_DAY);
            int minute = heureDepart.get(ChronoField.MINUTE_OF_HOUR);
            int second = heureDepart.get(ChronoField.SECOND_OF_MINUTE);*/

            tournee.setHeureDepart(heureDepart);


            NodeList nListRequetes = document.getElementsByTagName("request");


            for (int temp = 0; temp < nListRequetes.getLength(); temp++) {
                Node nNodeRequest = nListRequetes.item(temp);
                if (nNodeRequest.getNodeType() == Node.ELEMENT_NODE) {
                    eElement = (Element) nNodeRequest;

                    Long idAdresseRetrait = Long.parseLong(eElement.getAttribute("pickupAddress"));
                    Long idAdresseLivraison = Long.parseLong(eElement.getAttribute("deliveryAddress"));
                    Integer tempsRetrait = Integer.parseInt(eElement.getAttribute("pickupDuration"));
                    Integer tempsLivraison = Integer.parseInt(eElement.getAttribute("deliveryDuration"));
                    Adresse adresseRetrait = carte.obtenirAdresseParId(idAdresseRetrait);
                    Adresse adresseLivraison = carte.obtenirAdresseParId(idAdresseLivraison);

                    Etape etapeRetrait = new Etape(adresseRetrait.getLatitude(),adresseRetrait.getLongitude(),idAdresseRetrait,tempsRetrait, null);
                    Etape etapeLivraison = new Etape(adresseLivraison.getLatitude(),adresseLivraison.getLongitude(),idAdresseLivraison,tempsLivraison, null);
                    Requete requete = new Requete(etapeRetrait, etapeLivraison);
                    listeRequetes.add(requete);
                }
            }
            tournee.setListeRequetes(listeRequetes);
        }
        catch(IOException e){
            System.out.println(e);
        }
        finally {
            return tournee;
        }
    }
}
