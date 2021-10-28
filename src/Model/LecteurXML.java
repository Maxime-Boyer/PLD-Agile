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

    private Carte carte = new Carte();

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
                    double latitude = Double.parseDouble(eElement.getAttribute("latitude"));
                    double longitude = Double.parseDouble(eElement.getAttribute("longitude"));
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

            determinerNomAdresseEtapes(tournee);
        }
        catch(IOException e){
            System.out.println(e);
        }
        finally {
            return tournee;
        }
    }

    // Parcourir chaque etape et tous les segments de la carte pour obtenir le nom des adresses
    private void determinerNomAdresseEtapes(Tournee tournee){

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
                if((requete.getEtapeCollecte().getLatitude() == segment.getOrigine().getLatitude() && requete.getEtapeCollecte().getLongitude() == segment.getOrigine().getLongitude()) ||
                        (requete.getEtapeCollecte().getLatitude() == segment.getDestination().getLatitude() && requete.getEtapeCollecte().getLongitude() == segment.getDestination().getLongitude())){
                    if(segmentCollecte1 == null){
                        segmentCollecte1 = segment;
                    }
                    else if(segmentCollecte1 != null && segment.getNom() != segmentCollecte1.getNom()){
                        segmentCollecte2 = segment;
                    }
                }

                // si les coordonnes de l'étape de depot concordent avec celles d'une extremite du segment
                if((requete.getEtapeDepot().getLatitude() == segment.getOrigine().getLatitude() && requete.getEtapeDepot().getLongitude() == segment.getOrigine().getLongitude()) ||
                        (requete.getEtapeDepot().getLatitude() == segment.getDestination().getLatitude() && requete.getEtapeDepot().getLongitude() == segment.getDestination().getLongitude())){
                    if(segmentDepot1 == null){
                        segmentDepot1 = segment;
                    }
                    else if(segmentDepot1 != null && segment.getNom() != segmentDepot1.getNom()){
                        segmentDepot2 = segment;
                    }
                }

                if(segmentCollecte2 != null && segmentDepot2 != null){
                    break;
                }
            }

            // Création et attribution des noms des adresses
            nomAdresseCollecte = "Intersection entre " + segmentCollecte1.getNom() + " et " + segmentCollecte2.getNom();
            nomAdresseDepot = "Intersection entre " + segmentDepot1.getNom() + " et " + segmentDepot2.getNom();
            requete.getEtapeCollecte().setNomAdresse(nomAdresseCollecte);
            requete.getEtapeDepot().setNomAdresse(nomAdresseDepot);
        }
    }
}
