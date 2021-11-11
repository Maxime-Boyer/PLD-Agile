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
import org.w3c.dom.*;
import org.xml.sax.SAXException;

public class LecteurXML {
    /**
     * Constructeur vide du lecteur XML qui permet les ouverture et les placement dans les structures de données des choses présentes dans les fichiers xml
     */
    public LecteurXML() {
    }

    /**
     * méthode qui permet de lire la carte.xm et de remplir les liste d'adresse et de segment de la carte
     * @param nomFichier: nom du fichier à ouvrir
     * @throws ParserConfigurationException
     * @throws SAXException
     */
    public Carte lectureCarte(String nomFichier, Carte carte) throws ParserConfigurationException, SAXException, PresenceEncodingEtVersionException, IOException, TagNameMapException, AttributsIntersectionsException, NegatifLatitudeException, NegatifLongitudeException, AttributsSegmentsException {

            Carte newCarte = new Carte(nomFichier);

            File file = new File(nomFichier);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document = db.parse(file);
            document.getDocumentElement().normalize();
            //Presence première ligne du xml <? ...?>
            String line = Files.readAllLines(Paths.get(nomFichier)).get(0);
            if (!(line.contains("<?") && line.contains("?>"))) {
                throw new PresenceEncodingEtVersionException("Erreur lors de la lecture du fichier xml, il manque l'encodage et la version du fichier ");
            }

            //Renvoie toutes les balises du fichier xml
            NodeList nodeList = document.getElementsByTagName("*");
            for (int i = 0; i < nodeList.getLength(); i++) {
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
                        throw new AttributsIntersectionsException("Erreur, le nombre d'attributs de la balise intersection n° " + temp + " est différent du nombre attendu ");

                    } else {
                        for (int i = 0; i < listeAttributs.getLength(); i++) {
                            if (!listeAttributs.item(i).getNodeName().equals("id") && !listeAttributs.item(i).getNodeName().equals("latitude") && !listeAttributs.item(i).getNodeName().equals("longitude")) {
                                throw new AttributsIntersectionsException("Erreur, les attributs de la balise intersection n° " + temp + " ne sont pas corrects");
                            }
                        }
                    }
                    String stringLatitude = eElement.getAttribute("latitude");
                    String stringLongitude = eElement.getAttribute("longitude");
                    String stringId = eElement.getAttribute("id");
                    if (stringLongitude.chars().allMatch(Character::isWhitespace)) {
                        throw new AttributsIntersectionsException("Erreur manque de l'attribut Longitude dans une balise intersection de la carte");
                    }
                    if (stringId.chars().allMatch(Character::isWhitespace)) {
                        throw new AttributsIntersectionsException("Erreur manque de l'attribut Id dans une balise intersection de la carte");
                    }
                    if (stringLatitude.chars().allMatch(Character::isWhitespace)) {
                        throw new AttributsIntersectionsException("Erreur manque de l'attribut Latitude dans une balise intersection de la carte");
                    }
                    double latitude = Double.parseDouble(stringLatitude);
                    if (latitude < 0.0) {
                        throw new NegatifLatitudeException("Erreur, la latitude d'un point de retrait est négative");
                    }
                    double longitude = Double.parseDouble(stringLongitude);
                    if (longitude < 0.0) {
                        throw new NegatifLongitudeException("Erreur, la longitude d'un point de retrait  est négative");
                    }
                    Long id = Long.parseLong(stringId);
                    Adresse adresse = new Adresse(latitude, longitude, id);
                    newCarte.getListeAdresses().put(id, adresse);
                }
            }
            NodeList nListSegment = document.getElementsByTagName("segment");
            for (int temp = 0; temp < nListSegment.getLength(); temp++) {
                Node nNodeSegment = nListSegment.item(temp);
                if (nNodeSegment.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNodeSegment;
                    NamedNodeMap listeAttributsSegment = nNodeSegment.getAttributes();
                    if (listeAttributsSegment.getLength() != 4) {
                        throw new AttributsSegmentsException("Erreur, le nombre d'attributs de la balise  segment n° " + temp + " est différent du nombre attendu ");
                    } else {
                        for (int i = 0; i < listeAttributsSegment.getLength(); i++) {
                            if (!listeAttributsSegment.item(i).getNodeName().equals("length") && !listeAttributsSegment.item(i).getNodeName().equals("name") && !listeAttributsSegment.item(i).getNodeName().equals("origin") && !listeAttributsSegment.item(i).getNodeName().equals("destination")) {
                                throw new AttributsSegmentsException("Erreur, les attributs de la balise segment n° " + temp + " ne sont pas corrects");
                            }
                        }
                    }
                    String slongueur = eElement.getAttribute("length");
                    String nom = eElement.getAttribute("name");
                    String origine = eElement.getAttribute("origin");
                    String destination = eElement.getAttribute("destination");
                    if (slongueur.chars().allMatch(Character::isWhitespace)) {
                        throw new AttributsSegmentsException("Erreur manque de l'attribut length dans une balise segment de la carte");
                    }
                    if (origine.chars().allMatch(Character::isWhitespace)) {
                        throw new AttributsSegmentsException("Erreur manque de l'attribut origin dans une balise segment de la carte");
                    }
                    if (destination.chars().allMatch(Character::isWhitespace)) {
                        throw new AttributsSegmentsException("Erreur manque de l'attribut destination dans une balise segment de la carte");
                    }

                    Double longueur = Double.parseDouble(eElement.getAttribute("length"));
                    Long idOrigine = Long.parseLong(eElement.getAttribute("origin"));
                    Long idDestination = Long.parseLong(eElement.getAttribute("destination"));
                    Segment segment = new Segment(newCarte.obtenirAdresseParId(idOrigine), newCarte.obtenirAdresseParId(idDestination), nom, longueur);
                    newCarte.obtenirAdresseParId(idOrigine).ajouterSegmentSortant(segment);
                    newCarte.getListeSegments().add(segment);
                }
            }
        carte.clone(newCarte);
        //Notifie les observateurs que la carte a été mofifié
        carte.notifyObservers(carte);
        return carte;
    }

        /**
     *méthode qui permet de lire le fichier requete.xml et de remplir la liste des requetes de la Tournee et l'adresse de départ/dépot
     * @param nomFichier: nom du fichier requete.xml
     * @throws ParserConfigurationException
     * @throws SAXException
     */
    public Tournee lectureRequete(String nomFichier, Carte carte, Tournee tournee) throws ParserConfigurationException, SAXException, PresenceEncodingEtVersionException, TagNameMapException, AbsenceBaliseDepotException, AttributsDepotException, IncompatibleAdresseException, NegatifLatitudeException, NegatifLongitudeException, IOException, IncompatibleLatitudeException, IncompatibleLongitudeException, AbsenceBaliseRequestException, AttributsRequestsException {
        Tournee nouvelleTournee = new Tournee();
        List<Requete> listeRequetes = new  ArrayList<Requete>();
        File file = new File(nomFichier);
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document document =  db.parse(file);
        document.getDocumentElement().normalize();
        String line = Files.readAllLines(Paths.get(nomFichier)).get(0);
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
            throw new AbsenceBaliseDepotException("Erreur la balise depot n'existe pas dans le fichier");
        }

        //On vérifie si la balise depot a des attributs
        if (!eElement.hasAttributes()) {
            throw new AttributsDepotException("Erreur la balise depot n'as pas d'attribut dans le fichier");
        }
        //On vérifie si les attributs de la balise depot ne sont pas inexistant
        String stringAdresseDepot = eElement.getAttribute("address");
        String stringHeureDepart = eElement.getAttribute("departureTime");
        NamedNodeMap listeAttributsDepot = depot.getAttributes();
        if(listeAttributsDepot.getLength() != 2){
            throw new AttributsDepotException("Erreur, le nombre d'attributs de la balise depot est différent du nombre attendu");
        }else{
            for (int i = 0 ; i< listeAttributsDepot.getLength(); i++){
                if(!listeAttributsDepot.item(i).getNodeName().equals("address") && !listeAttributsDepot.item(i).getNodeName().equals("departureTime")){
                    throw new AttributsDepotException("Erreur, les noms d'attributs de la balise depot ne sont pas corrects");
                }
            }
        }
        if (stringHeureDepart.isEmpty()) {
            throw new AttributsDepotException("Erreur l'attribut departureTime de la balise depot est inexistant ou n'a pas de valeur");
        }
        if (stringAdresseDepot.isEmpty()) {
            throw new AttributsDepotException("Erreur l'attribut adresse de la balise depot est inexistant ou n'a pas de valeur");
        }
        Long idAdresseDepot = Long.parseLong(eElement.getAttribute("address"));

        Etape etapeDepot;
        Adresse adresseDepot;
        if(carte.getListeAdresses() != null) {
            if (!(carte.getListeAdresses().containsKey(idAdresseDepot))) {
                throw new IncompatibleAdresseException("Erreur d'adresse de départ, cette adresse n'appartient pas à la carte chargée ");
            } else {
                adresseDepot = carte.obtenirAdresseParId(idAdresseDepot);
                etapeDepot = new Etape(adresseDepot.getLatitude(), adresseDepot.getLongitude(), idAdresseDepot, 0, LocalTime.of(0,0,0,0));
            }
            double latitudeAdresseDepot = adresseDepot.getLatitude();
            double longitudeAdresseDepot = adresseDepot.getLongitude();

            if (latitudeAdresseDepot < 0.0) {
                throw new NegatifLatitudeException("Erreur, la latitude du départ est négative");
            }
            if (longitudeAdresseDepot < 0.0) {
                throw new NegatifLongitudeException("Erreur, la longitude du départ est négative");
            }

            //String depart = eElement.getAttribute("departureTime");
            nouvelleTournee.setEtapeDepart(etapeDepot);
        }
        if(verificationFormatDate(stringHeureDepart)) {
            LocalTime heureDepart = LocalTime.parse(stringHeureDepart, DateTimeFormatter.ofPattern("H:m:s"));
            nouvelleTournee.setHeureDepart(heureDepart);
        }else {
            throw new AttributsDepotException("Erreur, l'attribut departureTime de la balise depot n'est pas au bon format");
        }
        NodeList nListRequetes = document.getElementsByTagName("request");
        if(nListRequetes.getLength() == 0){
            throw new AbsenceBaliseRequestException("Erreur aucune requête n'est présente dans le fichier");
        }
        if(nListRequetes.getLength() > 0) {

            for (int temp = 0; temp < nListRequetes.getLength(); temp++) {
                Node nNodeRequest = nListRequetes.item(temp);
                //On vérifie si la balise request a des attributs
                if (!eElement.hasAttributes()) {
                    throw new AttributsRequestsException("Erreur la balise request n°" + temp + " n'as pas d'attribut dans le fichier");
                }
                NamedNodeMap listeAttributsRequest = nNodeRequest.getAttributes();
                if(listeAttributsRequest.getLength() != 4){
                    throw new AttributsRequestsException("Erreur, le nombre d'attributs de la balise request n°" + temp + " est différent du nombre attendu");
                }else{
                    for (int i = 0 ; i< listeAttributsRequest.getLength(); i++){
                        if(!listeAttributsRequest.item(i).getNodeName().equals("pickupAddress") && !listeAttributsRequest.item(i).getNodeName().equals("deliveryAddress") && !listeAttributsRequest.item(i).getNodeName().equals("pickupDuration") && !listeAttributsRequest.item(i).getNodeName().equals("deliveryDuration")){
                            throw new AttributsRequestsException("Erreur, les noms d'attributs de la balise request n°" + temp + " ne sont pas corrects");
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
                        throw new AttributsRequestsException("Erreur manque de l'attribut PickUpAddress (Adresse de collecte) dans une balise Requests de la Tournee");
                    }
                    if (stringDeliveryAddress.isEmpty()) {
                        throw new AttributsRequestsException("Erreur manque de l'attribut DeliveryAddress (Adresse de depot) dans une balise Requests de la Tournee");
                    }
                    if (stringPickupDuration.isEmpty()) {
                        throw new AttributsRequestsException("Erreur manque de l'attribut PickUpDuration (Durée de collecte) dans une balise Requests de la Tournee");
                    }
                    if (stringDeliveryDuration.isEmpty()) {
                        throw new AttributsRequestsException("Erreur manque de l'attribut DeliveryDuration (Durée de depot) dans une balise Requests de la Tournee");
                    }
                    Long idAdresseRetrait = Long.parseLong(stringPickupAddress);
                    Long idAdresseLivraison = Long.parseLong(stringDeliveryAddress);
                    if (carte.getListeAdresses() != null) {
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
                        Etape etapeRetrait = new Etape(adresseRetrait.getLatitude(), adresseRetrait.getLongitude(), idAdresseRetrait, tempsRetrait, null);
                        Etape etapeLivraison = new Etape(adresseLivraison.getLatitude(), adresseLivraison.getLongitude(), idAdresseLivraison, tempsLivraison, null);
                        Requete requete = new Requete(etapeRetrait, etapeLivraison);
                        listeRequetes.add(requete);
                    }
                    nouvelleTournee.setListeRequetes(listeRequetes);
                    determinerNomAdresseEtapes(nouvelleTournee, carte);
                }
            }
        }
        tournee.clone(nouvelleTournee);
        tournee.setTourneeEstChargee(true);
        //Notifie les observateurs que la tournee a été mofifié
        tournee.notifyObservers(tournee);
        return tournee;
    }

    /**
     * retourne true si la date est au bon format
     * @param date: date à vérifier
     * @return: true si la date est au bon format
     */
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

    /**
     * méthode qui permer de parcourir chaque etape et tous les segments de la carte pour obtenir le nom des adresses
     * @param tournee: pour récolter la liste des requêtes
     * @param carte: pour récuperer les segments et les adresses
     */

    private void determinerNomAdresseEtapes(Tournee tournee, Carte carte){

        Requete requete;
        Segment segment, segmentCollecte1, segmentCollecte2, segmentDepot1, segmentDepot2;
        String nomAdresseCollecte = "", nomAdresseDepot = "";
        // parcours de toutes les requetes
        for(int i = 0; i < tournee.getListeRequetes().size(); i++){
            requete = tournee.getListeRequetes().get(i);
            segmentCollecte1 = null;
            segmentCollecte2 = null;
            segmentDepot1 = null;
            segmentDepot2 = null;
            // recuperation de tous les segments
            for(int j = 0; j < carte.getListeSegments().size(); j++){
                segment = carte.getListeSegments().get(j);
                // si les coordonnes de l'étape de collecte concordent avec celles d'une extremite du segment
                if(requete.getEtapeCollecte().getIdAdresse().equals(segment.getOrigine().getIdAdresse()) || requete.getEtapeCollecte().getIdAdresse().equals(segment.getDestination().getIdAdresse()) ){
                    if(segmentCollecte1 == null){
                        segmentCollecte1 = segment;
                    }
                    else if(segmentCollecte2 == null || !segment.getNom().equals(segmentCollecte1.getNom())){
                        segmentCollecte2 = segment;
                    }
                }
                // si les coordonnes de l'étape de depot concordent avec celles d'une extremite du segment
                if(requete.getEtapeDepot().getIdAdresse().equals(segment.getOrigine().getIdAdresse()) || requete.getEtapeDepot().getIdAdresse().equals(segment.getDestination().getIdAdresse())){
                    if(segmentDepot1 == null){
                        segmentDepot1 = segment;
                    }
                    else if(segmentDepot2 == null || !segment.getNom().equals(segmentDepot1.getNom())){
                        segmentDepot2 = segment;
                    }
                }
                if(segmentCollecte1 != null && segmentDepot1 != null && segmentCollecte2 != null && segmentDepot2 != null
                        && !segmentCollecte1.getNom().equals(segmentCollecte2.getNom()) && !segmentDepot1.getNom().equals(segmentDepot2.getNom())){
                    break;
                }
            }
            // Création et attribution des noms des adresses
            nomAdresseCollecte = nommerAdresseAvecDeuxSegments(segmentCollecte1, segmentCollecte2);
            nomAdresseDepot = nommerAdresseAvecDeuxSegments(segmentDepot1, segmentDepot2);
            requete.getEtapeCollecte().setNomAdresse(nomAdresseCollecte);
            requete.getEtapeDepot().setNomAdresse(nomAdresseDepot);
        }
    }

    /**
     * Determine le nom d'un adresse à partir de deux segments
     * @param s1 premier segment
     * @param s2 second segment
     * @return nom de l'adresss
     */
    private String nommerAdresseAvecDeuxSegments(Segment s1, Segment s2) {
        String nomAdresse = "";
        if (s1 == null || s1.getNom() == "") {
            if (s2 == null || s2.getNom() == "")
                nomAdresse = "Aucun nom associé à cette intersection.";
            else
                nomAdresse = s2.getNom();
        } else {
            if (s2 == null || s2.getNom() == "")
                nomAdresse = s1.getNom();
            else
                nomAdresse = "Intersection entre " + s1.getNom() + " et " + s2.getNom();
        }
        return nomAdresse;
    }
}
