package Model;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import Exceptions.*;
import org.junit.platform.commons.util.StringUtils;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import static java.lang.Float.isNaN;

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

            if(!nomFichier.toLowerCase(Locale.ROOT).contains("map")) {
                throw new NameFile("Erreur ouverture du fichier le nom du fichier ne contient pas le mot map");
            }
            File file = new File(nomFichier);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document = db.parse(file);
            document.getDocumentElement().normalize();
            //Presence première ligne du xml <? ...?>
            String line = Files.readAllLines(Paths.get(nomFichier)).get(0);
            //System.out.println(line);
            if (!(line.contains("<?") && line.contains("?>"))) {
                throw new PresenceEncodingEtVersionException("Erreur lors de la lecture du fichier xml, il manque l'encodage et la version du fichier ");
            }

            //Renvoie toutes les balises du fichier xml
            NodeList nodeList = document.getElementsByTagName("*");
            for (int i = 0; i < nodeList.getLength(); i++) {
                //Get element
                Element element = (Element) nodeList.item(i);
                if ((element.getNodeName().equals("map") == false) && (element.getNodeName().equals("intersection") == false) && (element.getNodeName().equals("segment") == false)) {
                    throw new TagNameMapException("Erreur lors de la lecture du fichier xml de la carte, des balises incorrectes apparaissent dans le document. NOM BALISE INCORRECTE : " + element.getNodeName());
                }
            }

            NodeList nListAdresse = document.getElementsByTagName("intersection");
            for (int temp = 0; temp < nListAdresse.getLength(); temp++) {
                Node nNodeAdresse = nListAdresse.item(temp);
                if (nNodeAdresse.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNodeAdresse;
                    //liste de tous les attributs du tagname intersection
                    NamedNodeMap listeAttributs = nNodeAdresse.getAttributes();
                    if (listeAttributs.getLength() != 3) {
                        throw new AttributsIntersectionsExceptions("Erreur, le nombre d'attributs de la balise intersection n° " + temp + " est différent du nombre attendu ");

                    } else {
                        for (int i = 0; i < listeAttributs.getLength(); i++) {
                            //System.out.println(listeAttributs.item(i).getNodeName());

                            if (!listeAttributs.item(i).getNodeName().equals("id") && !listeAttributs.item(i).getNodeName().equals("latitude") && !listeAttributs.item(i).getNodeName().equals("longitude")) {
                                throw new AttributsIntersectionsExceptions("Erreur, les attributs de la balise intersection n° " + temp + " ne sont pas corrects");
                            }
                        }
                    }


                    String stringLatitude = eElement.getAttribute("latitude");
                    String stringLongitude = eElement.getAttribute("longitude");
                    String stringId = eElement.getAttribute("id");

                    if (stringLongitude.chars().allMatch(Character::isWhitespace)) {
                        throw new AttributsIntersectionsExceptions("Erreur manque de l'attribut Longitude dans une balise intersection de la carte");
                    }
                    if (stringId.chars().allMatch(Character::isWhitespace)) {
                        throw new AttributsIntersectionsExceptions("Erreur manque de l'attribut Id dans une balise intersection de la carte");
                    }
                    if (stringLatitude.chars().allMatch(Character::isWhitespace)) {
                        throw new AttributsIntersectionsExceptions("Erreur manque de l'attribut Latitude dans une balise intersection de la carte");
                    }

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
                }
            }

            NodeList nListSegment = document.getElementsByTagName("segment");
            for (int temp = 0; temp < nListSegment.getLength(); temp++) {
                Node nNodeSegment = nListSegment.item(temp);
                if (nNodeSegment.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNodeSegment;

                    NamedNodeMap listeAttributsSegment = nNodeSegment.getAttributes();
                    if (listeAttributsSegment.getLength() != 4) {
                        throw new AttributsSegmentsExceptions("Erreur, le nombre d'attributs de la balise  segment n° " + temp + " est différent du nombre attendu ");

                    } else {
                        for (int i = 0; i < listeAttributsSegment.getLength(); i++) {
                            //System.out.println(listeAttributs.item(i).getNodeName());

                            if (!listeAttributsSegment.item(i).getNodeName().equals("length") && !listeAttributsSegment.item(i).getNodeName().equals("name") && !listeAttributsSegment.item(i).getNodeName().equals("origin") && !listeAttributsSegment.item(i).getNodeName().equals("destination")) {
                                throw new AttributsIntersectionsExceptions("Erreur, les attributs de la balise segment n° " + temp + " ne sont pas corrects");
                            }

                        }

                    }
                    String slongueur = eElement.getAttribute("length");
                    String nom = eElement.getAttribute("name");
                    String origine = eElement.getAttribute("origin");
                    String destination = eElement.getAttribute("destination");

                    if (slongueur.chars().allMatch(Character::isWhitespace)) {
                        throw new AttributsSegmentsExceptions("Erreur manque de l'attribut length dans une balise segment de la carte");
                    }
                    if (origine.chars().allMatch(Character::isWhitespace)) {
                        throw new AttributsSegmentsExceptions("Erreur manque de l'attribut origin dans une balise segment de la carte");
                    }
                    if (destination.chars().allMatch(Character::isWhitespace)) {
                        throw new AttributsSegmentsExceptions("Erreur manque de l'attribut destination dans une balise segment de la carte");
                    }

                    Double longueur = Double.parseDouble(eElement.getAttribute("length"));
                    Long idOrigine = Long.parseLong(eElement.getAttribute("origin"));
                    Long idDestination = Long.parseLong(eElement.getAttribute("destination"));
                    Segment segment = new Segment(carte.obtenirAdresseParId(idOrigine), carte.obtenirAdresseParId(idDestination), nom, longueur);
                    carte.getListeSegments().add(segment);
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

            if(!nomFichier.toLowerCase(Locale.ROOT).contains("request")) {
                throw new NameFile("Erreur ouverture du fichier le nom du fichier ne contient pas le mot request");
            }

            File file = new File(nomFichier);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document =  db.parse(file);
            document.getDocumentElement().normalize();

            String line = Files.readAllLines(Paths.get(nomFichier)).get(0);
            //System.out.println(line);
            if ( !(line.contains("<?") && line.contains("?>"))){

                throw new PresenceEncodingEtVersionException("Erreur lors de la lecture du fichier xml, il manque l'encodage et la version du fichier ");
            }

            //Renvoie toutes les balises du fichier xml
            NodeList nodeList=document.getElementsByTagName("*");
            for (int i=0; i<nodeList.getLength(); i++)
            {
                //Get element
                Element element = (Element)nodeList.item(i);
                if ( (element.getNodeName().equals("planningRequest") == false ) && (element.getNodeName().equals("depot") == false ) && (element.getNodeName().equals("request") == false )){
                    throw new TagNameMapException("Erreur lors de la lecture du fichier xml de la tournée, des balises incorrectes apparaissent dans le document. NOM BALISE INCORRECTE : "+element.getNodeName());
                }

            }

            Node depot = document.getElementsByTagName("depot").item(0);

            Element eElement = (Element) depot;

            if(document.getElementsByTagName("depot").getLength() == 0){
                throw new AbsenceBaliseDepot("Erreur la balise depot n'existe pas dans le fichier");
            }
            if(document.getElementsByTagName("request").getLength() == 0){
                throw new AbsenceBaliseRequest("Erreur aucune balise request dans le fichier");
            }

            //On vérifie si la balise depot a des attributs
            if (!eElement.hasAttributes()) {
                throw new AttributsDepotExceptions("Erreur la balise depot n'as pas d'attribut dans le fichier");
            }

            //On vérifie si les attributs de la balise depot ne sont pas inexistant
            String stringAdresseDepot = eElement.getAttribute("address");
            String stringHeureDepart = eElement.getAttribute("departureTime");

            NamedNodeMap listeAttributsDepot = depot.getAttributes();
            if(listeAttributsDepot.getLength() != 2){
                throw new AttributsDepotExceptions("Erreur, le nombre d'attributs de la balise depot est différent du nombre attendu");

            }else{
                for (int i = 0 ; i< listeAttributsDepot.getLength(); i++){
                    //System.out.println(listeAttributs.item(i).getNodeName());

                    if(!listeAttributsDepot.item(i).getNodeName().equals("address") && !listeAttributsDepot.item(i).getNodeName().equals("departureTime")){
                        throw new AttributsIntersectionsExceptions("Erreur, les noms d'attributs de la balise depot ne sont pas corrects");
                    }
                }
            }

            if (stringHeureDepart.isEmpty()) {
                throw new AttributsDepotExceptions("Erreur l'attribut departureTime de la balise depot est inexistant ou n'a pas de valeur");
            }
            if (stringAdresseDepot.isEmpty()) {
                throw new AttributsDepotExceptions("Erreur l'attribut adresse de la balise depot est inexistant ou n'a pas de valeur");
            }

            Long idAdresseDepot = Long.parseLong(eElement.getAttribute("address"));
            System.out.println("idAdresseDepot " + idAdresseDepot);
            Adresse adresseDepot;
            if (!(carte.getListeAdresses().containsKey(idAdresseDepot))) {
                throw new IncompatibleAdresseException("Erreur d'adresse de départ, cette adresse n'appartient pas à la carte chargée ");
            } else {
                adresseDepot = carte.obtenirAdresseParId(idAdresseDepot);
            }

            double latitudeAdresseDepot = adresseDepot.getLatitude();
            double longitudeAdresseDepot = adresseDepot.getLongitude();

            if (latitudeAdresseDepot < 0.0) {
                throw new NegatifLatitudeException("Erreur, la latitude du départ est négative");
            }

            if (longitudeAdresseDepot < 0.0) {
                throw new NegatifLongitudeException("Erreur, la longitude du départ est négative");
            }

            if ((latitudeAdresseDepot < minLatitude) || (latitudeAdresseDepot > maxLatitude)) {
                throw new IncompatibleLatitudeException("Erreur, la latitude de l'adresse de départ n'apparatient pas au plan chargé");
            }
            if ((longitudeAdresseDepot < minLongitude) || (longitudeAdresseDepot > maxLongitude)) {
                throw new IncompatibleLongitudeException("Erreur, la longitude de l'adresse de départ n'apparatient au  plan chargé");
            }


            //String depart = eElement.getAttribute("departureTime");
            tournee.setAdresseDepart(adresseDepot);

            if(verificationFormatDate(stringHeureDepart)) {
                LocalTime heureDepart = LocalTime.parse(stringHeureDepart, DateTimeFormatter.ofPattern("H:m:s"));
                tournee.setHeureDepart(heureDepart);
            }else {
                throw new AttributsDepotExceptions("Erreur, l'attribut departureTime de la balise depot n'est pas au bon format");
            }

            NodeList nListRequetes = document.getElementsByTagName("request");

            if(nListRequetes.getLength() == 0){
                throw new AbsenceBaliseRequest("Erreur aucune requête n'est présente dans le fichier");
            }

            if(nListRequetes.getLength() > 0) {

                for (int temp = 0; temp < nListRequetes.getLength(); temp++) {
                    Node nNodeRequest = nListRequetes.item(temp);

                    //On vérifie si la balise request a des attributs
                    if (!eElement.hasAttributes()) {
                        throw new AttributsRequestsExceptions("Erreur la balise request n°" + temp + " n'as pas d'attribut dans le fichier");
                    }

                    NamedNodeMap listeAttributsRequest = nNodeRequest.getAttributes();
                    if(listeAttributsRequest.getLength() != 4){
                        throw new AttributsDepotExceptions("Erreur, le nombre d'attributs de la balise request n°" + temp + " est différent du nombre attendu");

                    }else{
                        for (int i = 0 ; i< listeAttributsRequest.getLength(); i++){
                            //System.out.println(listeAttributs.item(i).getNodeName());

                            if(!listeAttributsRequest.item(i).getNodeName().equals("pickupAddress") && !listeAttributsRequest.item(i).getNodeName().equals("deliveryAddress") && !listeAttributsRequest.item(i).getNodeName().equals("pickupDuration") && !listeAttributsRequest.item(i).getNodeName().equals("deliveryDuration")){
                                throw new AttributsIntersectionsExceptions("Erreur, les noms d'attributs de la balise request n°" + temp + " ne sont pas corrects");
                            }

                        }

                    }

                    if (nNodeRequest.getNodeType() == Node.ELEMENT_NODE) {
                        eElement = (Element) nNodeRequest;

                        String stringPickupAddress = eElement.getAttribute("pickupAddress");
                        String stringDeliveryAddress = eElement.getAttribute("deliveryAddress");
                        String stringPickupDuration = eElement.getAttribute("pickupDuration");
                        String stringDeliveryDuration = eElement.getAttribute("deliveryDuration");

                        if (stringPickupAddress.isEmpty()) {
                            throw new AttributsRequestsExceptions("Erreur manque de l'attribut PickUpAddress (Adresse de collecte) dans une balise Requests de la Tournee");
                        }
                        if (stringDeliveryAddress.isEmpty()) {
                            throw new AttributsRequestsExceptions("Erreur manque de l'attribut DeliveryAddress (Adresse de depot) dans une balise Requests de la Tournee");
                        }
                        if (stringPickupDuration.isEmpty()) {
                            throw new AttributsRequestsExceptions("Erreur manque de l'attribut PickUpDuration (Durée de collecte) dans une balise Requests de la Tournee");
                        }
                        if (stringDeliveryDuration.isEmpty()) {
                            throw new AttributsRequestsExceptions("Erreur manque de l'attribut DeliveryDuration (Durée de depot) dans une balise Requests de la Tournee");
                        }

                        Long idAdresseRetrait = Long.parseLong(stringPickupAddress);
                        Long idAdresseLivraison = Long.parseLong(stringDeliveryAddress);

                        if (!(carte.getListeAdresses().containsKey(idAdresseRetrait))) {
                            throw new IncompatibleAdresseException("Erreur sur une adresse de retrait, l'adresse n'appartient pas à la carte chargée ");
                        }

                        if (!(carte.getListeAdresses().containsKey(idAdresseLivraison))) {
                            throw new IncompatibleAdresseException("Erreur sur une adresse de livraison, l'adresse n'appartient pas à la carte chargée ");
                        }

                        Integer tempsRetrait = Integer.parseInt(stringPickupDuration);
                        Integer tempsLivraison = Integer.parseInt(stringDeliveryDuration);
                        Adresse adresseRetrait = carte.obtenirAdresseParId(idAdresseRetrait);
                        Adresse adresseLivraison = carte.obtenirAdresseParId(idAdresseLivraison);

                        double latitudeAdresseRetrait = adresseRetrait.getLatitude();
                        double longitudeAdresseRetrait = adresseRetrait.getLongitude();


                        if ((latitudeAdresseRetrait < minLatitude) || (latitudeAdresseRetrait > maxLatitude)) {
                            throw new IncompatibleLatitudeException("Erreur, la latitude de d'une adresse n'apparatient pas au plan chargé");
                        }
                        if ((longitudeAdresseRetrait < minLongitude) || (longitudeAdresseRetrait > maxLongitude)) {
                            throw new IncompatibleLongitudeException("Erreur, la longitude d'une adresse n'apparatient au  plan chargé");
                        }

                        double latitudeAdresseLivraison = adresseLivraison.getLatitude();
                        double longitudeAdresseLivraison = adresseLivraison.getLongitude();


                        if ((latitudeAdresseLivraison < minLatitude) || (latitudeAdresseLivraison > maxLatitude)) {
                            throw new IncompatibleLatitudeException("Erreur, la latitude de l'adresse de départ n'apparatient pas au plan chargé");
                        }
                        if ((longitudeAdresseLivraison < minLongitude) || (longitudeAdresseLivraison > maxLongitude)) {
                            throw new IncompatibleLongitudeException("Erreur, la longitude de l'adresse de départ n'apparatient au le plan chargé");
                        }


                        Etape etapeRetrait = new Etape(adresseRetrait.getLatitude(), adresseRetrait.getLongitude(), idAdresseRetrait, tempsRetrait, null);
                        Etape etapeLivraison = new Etape(adresseLivraison.getLatitude(), adresseLivraison.getLongitude(), idAdresseLivraison, tempsLivraison, null);
                        Requete requete = new Requete(etapeRetrait, etapeLivraison);
                        listeRequetes.add(requete);
                    }
                    tournee.setListeRequetes(listeRequetes);
                }
            }
        }
        catch(IOException e){
            System.out.println(e);
        }
        finally {
            return tournee;
        }
    }

    public boolean verificationFormatDate(String date) {

        int len = date.length();
        int occurrence = 0;
        char ch[] = new char[date.length()];
        for (int i = 0; i < len; i++) {

            if (date.charAt(i) == ':') {
                occurrence++;
            }
        }

        if(occurrence == 2) {

            String[] separationDate = date.split(":");
            int nbEnCours = 0;
            Boolean heure = false;
            Boolean minute = false;
            Boolean seconde= false;

            for(int i = 0; i < separationDate.length; i++) {
                nbEnCours = Integer.parseInt(separationDate[i]);

                if (i == 0 && nbEnCours >= 0 && nbEnCours <= 24) {
                    heure = true;
                }
                if (i == 1 && nbEnCours >= 0 && nbEnCours <= 60) {
                    minute = true;
                }
                if (i == 2 && nbEnCours >= 0 && nbEnCours <= 60) {
                    seconde = true;
                }
            }

            if(heure && minute && seconde){
                return true;
            }
        }
        return false;
    }

}
