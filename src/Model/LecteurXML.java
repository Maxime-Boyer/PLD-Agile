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
            File file = new File("smallMap.xml");
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document =  db.parse(file);
            document.getDocumentElement().normalize();
            NodeList nList = document.getElementsByTagName("intersection");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    Float latitude = Float.parseFloat(eElement.getAttribute("latitude"));
                    Float longitude = Float.parseFloat(eElement.getAttribute("longitude"));
                    Integer id = Integer.parseInt(eElement.getAttribute("id"));

                    Adresse adresse = new Adresse(latitude,longitude,id);
                }
            }
        }
        catch(IOException e){

        }
    }
}
