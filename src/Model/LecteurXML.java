package Model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import Exceptions.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class LecteurXML {

    private Carte carte = new Carte();
    private double maxLongitude = Double.MIN_VALUE;;
    private double maxLatitude = Double.MIN_VALUE;
    private double minLatitude = Double.MAX_VALUE;
    private double minLongitude = Double.MAX_VALUE;


    public LecteurXML() {
    }

    /**
     *
     * @param nomFichier
     * @throws ParserConfigurationException
     * @throws SAXException
     */
    public Carte lectureCarte(String nomFichier) throws ParserConfigurationException, SAXException{

        try{
            carte = new Carte(nomFichier);
            File file = new File(nomFichier);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document =  db.parse(file);
            document.getDocumentElement().normalize();
            //Presence première ligne du xml <? ...?>
            String line = Files.readAllLines(Paths.get(nomFichier)).get(0);
            //System.out.println(line);
            if ( !(line.contains("<?") && line.contains("?>"))){

                throw new PresenceEncodingEtVersion("Erreur lors de la lecture du fichier xml, il manque l'encodage et la version du fichier ");
            }

            //Renvoie toutes les balises du fichier xml
            NodeList nodeList=document.getElementsByTagName("*");
            for (int i=0; i<nodeList.getLength(); i++)
            {
                //Get element
                Element element = (Element)nodeList.item(i);
                //System.out.println(element.getNodeName());
                //System.out.println("valeur : "+ ((element.getNodeName().equals("map") == false ) && (element.getNodeName().equals("intersection") == false ) && (element.getNodeName().equals("segment") == false )));


                if ( (element.getNodeName().equals("map") == false ) && (element.getNodeName().equals("intersection") == false ) && (element.getNodeName().equals("segment") == false )){
                    throw new TagNameMapException("Erreur lors de la lecture du fichier xml de la carte, des balises incorrectes apparaissent dans le document. NOM BALISE INCORRECTE : "+element.getNodeName());
                }

            }



            NodeList nListAdresse = document.getElementsByTagName("intersection");

            for (int temp = 0; temp < nListAdresse.getLength(); temp++) {
                Node nNodeAdresse = nListAdresse.item(temp);
                if (nNodeAdresse.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNodeAdresse;

                    String stringLatitude = eElement.getAttribute("latitude");
                    String stringLongitude = eElement.getAttribute("longitude");
                    String stringId = eElement.getAttribute("id");

                    if (!stringLatitude.isBlank() && !stringLongitude.isBlank() && !stringId.isBlank()) {

                        double latitude = Double.parseDouble(stringLatitude);
                        if (latitude < 0.0) {
                            throw new NegatifLatitudeException("Erreur, la latitude d'un point de retrait est négative");
                        }


                        if (latitude < minLatitude) {
                            minLatitude = latitude;
                        }
                        if (latitude > maxLatitude) {
                            maxLatitude = latitude;
                        }
                        double longitude = Double.parseDouble(stringLongitude);

                        if (longitude < 0.0) {
                            throw new NegatifLongitudeException("Erreur, la longitude d'un point de retrait  est négative");
                        }

                        if (longitude < minLongitude) {
                            minLongitude = longitude;
                        }
                        if (longitude > maxLongitude) {
                            maxLongitude = longitude;
                        }
                        Long id = Long.parseLong(stringId);

                        Adresse adresse = new Adresse(latitude, longitude, id);
                        carte.getListeAdresses().put(id, adresse);
                        //System.out.println(carte.getListeAdresses().get(id));
                    } else {

                        if (stringLongitude.isBlank()) {
                            throw new AbsenceAttributBaliseIntersection("Erreur manque de l'attribut Longitude dans une balise intersection de la carte");
                        }
                        if (stringId.isBlank()) {
                            throw new AbsenceAttributBaliseIntersection("Erreur manque de l'attribut Id dans une balise intersection de la carte");
                        }
                        if (stringLatitude.isBlank()) {
                            throw new AbsenceAttributBaliseIntersection("Erreur manque de l'attribut Latitude dans une balise intersection de la carte");
                        }
                    }
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
            String line = Files.readAllLines(Paths.get(nomFichier)).get(0);
            //System.out.println(line);
            if ( !(line.contains("<?") && line.contains("?>"))){

                throw new PresenceEncodingEtVersion("Erreur lors de la lecture du fichier xml, il manque l'encodage et la version du fichier ");
            }

            //On vérifie si les attributs de la balise Depot ne sont pas inexistant
            String stringAdresseDepot = eElement.getAttribute("address");
            String stringHeureDepart = eElement.getAttribute("departureTime");;

            Long idAdresseDepot = Long.parseLong(eElement.getAttribute("address"));
            Adresse adresseDepot ;
            if(!(carte.getListeAdresses().containsKey(idAdresseDepot))){
                throw new IncompatibleAdresseException("Erreur d'adresse de départ, cette adresse n'appartient pas à la carte chargée ");
            }else{
                adresseDepot = carte.obtenirAdresseParId(idAdresseDepot);
            }


            double latitudeAdresseDepot = adresseDepot.getLatitude();
            double longitudeAdresseDepot = adresseDepot.getLongitude();

            if (latitudeAdresseDepot < 0.0){
                throw new NegatifLatitudeException("Erreur, la latitude du départ est négative");
            }

            if (longitudeAdresseDepot < 0.0){
                throw new NegatifLongitudeException("Erreur, la longitude du départ est négative");
            }

            if(  (latitudeAdresseDepot< minLatitude) || (latitudeAdresseDepot > maxLatitude) ){
                throw new IncompatibleLatitudeException("Erreur, la latitude de l'adresse de départ n'apparatient pas au plan chargé");
            }
            if(  (longitudeAdresseDepot< minLongitude) || (longitudeAdresseDepot > maxLongitude) ){
                throw new IncompatibleLongitudeException("Erreur, la longitude de l'adresse de départ n'apparatient au  plan chargé");
            }



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

                    if(!(carte.getListeAdresses().containsKey(idAdresseRetrait))){
                        throw new IncompatibleAdresseException("Erreur sur une adresse de retrait, l'adresse n'appartient pas à la carte chargée ");
                    }

                    if(!(carte.getListeAdresses().containsKey(idAdresseLivraison))){
                        throw new IncompatibleAdresseException("Erreur sur une adresse de livraison, l'adresse n'appartient pas à la carte chargée ");
                    }

                    Integer tempsRetrait = Integer.parseInt(eElement.getAttribute("pickupDuration"));
                    Integer tempsLivraison = Integer.parseInt(eElement.getAttribute("deliveryDuration"));
                    Adresse adresseRetrait = carte.obtenirAdresseParId(idAdresseRetrait);
                    Adresse adresseLivraison = carte.obtenirAdresseParId(idAdresseLivraison);

                    double latitudeAdresseRetrait = adresseRetrait.getLatitude();
                    double longitudeAdresseRetrait = adresseRetrait.getLongitude();



                    if(  (latitudeAdresseRetrait< minLatitude) || (latitudeAdresseRetrait > maxLatitude) ){
                        throw new IncompatibleLatitudeException("Erreur, la latitude de d'une adresse n'apparatient pas au plan chargé");
                    }
                    if(  (longitudeAdresseRetrait< minLongitude) || (longitudeAdresseRetrait > maxLongitude) ){
                        throw new IncompatibleLongitudeException("Erreur, la longitude d'une adresse n'apparatient au  plan chargé");
                    }

                    double latitudeAdresseLivraison = adresseLivraison.getLatitude();
                    double longitudeAdresseLivraison = adresseLivraison.getLongitude();




                    if(  (latitudeAdresseLivraison< minLatitude) || (latitudeAdresseLivraison > maxLatitude) ){
                        throw new IncompatibleLatitudeException("Erreur, la latitude de l'adresse de départ n'apparatient pas au plan chargé");
                    }
                    if(  (longitudeAdresseLivraison< minLongitude) || (longitudeAdresseLivraison > maxLongitude) ){
                        throw new IncompatibleLongitudeException("Erreur, la longitude de l'adresse de départ n'apparatient au le plan chargé");
                    }






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
