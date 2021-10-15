package Model;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class LecteurXML {

    public LecteurXML() {
    }

    /**
     *
     * @param nomFichier
     * @throws ParserConfigurationException
     * @throws SAXException
     */
    public void lectureCarte(String nomFichier) throws ParserConfigurationException, SAXException {
        try{

            File file = new File(nomFichier);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document =  db.parse(file);
            document.getDocumentElement().normalize();
            NodeList nListAdresse = document.getElementsByTagName("intersection");
            Carte carte = new Carte(nomFichier);
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
                    System.out.println(segment);

                }
            }
        }
        catch(IOException e){

        }
    }
}
