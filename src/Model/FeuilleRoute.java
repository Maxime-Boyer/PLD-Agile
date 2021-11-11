package Model;
import Algorithmie.CalculateurTournee;
import Vue.CartePanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;

import java.util.HashMap;

public class FeuilleRoute {

   public FeuilleRoute(Tournee tournee, CartePanel cartePanel){

        try
        {
            //remise à 0 du zoom
            cartePanel.setMinLongitudeCarte(cartePanel.getMinLongitudeInitialeCarte());
            cartePanel.setMaxLongitudeCarte(cartePanel.getMaxLongitudeInitialeCarte());
            cartePanel.setMinLatitudeCarte(cartePanel.getMinLatitudeInitialeCarte());
            cartePanel.setMaxLatitudeCarte(cartePanel.getMaxLatitudeInitialeCarte());
            cartePanel.repaint();

            BufferedImage bi = new BufferedImage(cartePanel.getSize().width, cartePanel.getSize().height, BufferedImage.TYPE_INT_ARGB);
            Graphics g = bi.createGraphics();
            cartePanel.paint(g);  //this == JComponent
            g.dispose();
            try{
                ImageIO.write(bi,"png",new File("carte.png"));
            }catch (Exception e) {
                System.out.println(e);
            }

            //creation du fichier html
            File f = new File("feuilleRoute.html");
            BufferedWriter bw = new BufferedWriter(new FileWriter(f));
            bw.write("<html>");
            bw.write("<head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"/></head>");
            bw.write("<body style='width: 1200px; margin: 50px auto;'>");

            bw.write("<h1>Feuille de route</h1>");
            bw.write("<img src='./carte.png' style='height: 500px;'>");

            HashMap<Long, Requete> mapRequete= new HashMap<>();
            for(Requete requete: tournee.getListeRequetes()){
                mapRequete.put(requete.getEtapeCollecte().getIdAdresse(),requete) ;
                mapRequete.put(requete.getEtapeDepot().getIdAdresse(),requete) ;
            }

            // TODO: Arthur fixé ?
            bw.write(" <h2>Départ</h2><p> Depart de l'entrepot situe à l'"+tournee.getEtapeDepart().getNomAdresse()+" à "+tournee.getDateDepart()+"</p>");

            Requete requeteEtapeArriveeCheminCourant;
            Etape etapeArriveeChemin;
            String rueActuelle = "";
            Segment segmentActuel;
            Double distanceRueActuelle = 0.0;
            CheminEntreEtape cheminEntreEtape;
            int minutesPassageAvantRetouche = 0;
            String heurePassage = "", minutesPassage = "";
            //pour chaque chemin, affichage de la liste des rues à emprunter
            for(int i = 0; i < tournee.getListeChemins().size(); i++ ){

                cheminEntreEtape = tournee.getListeChemins().get(i);

                bw.write("<h2>Etape "+(i+1)+"</h2>");
                bw.write("<p>");

                for(int j = 0; j < cheminEntreEtape.getListeSegment().size(); j++){

                    segmentActuel = cheminEntreEtape.getListeSegment().get(j);
                    if(!rueActuelle.equals(segmentActuel.getNom()) || j == cheminEntreEtape.getListeSegment().size()-1){

                        if(j > 0){
                            bw.write("<br>Prendre rue "+ rueActuelle +" pendant "+ (int) Math.round(distanceRueActuelle) + "m.");
                        }

                        rueActuelle = segmentActuel.getNom();
                        distanceRueActuelle = segmentActuel.getLongueur();
                    }
                    else{
                        distanceRueActuelle += segmentActuel.getLongueur();
                    }
                }

                //on determine si l'adresse d'arrivee est une collecte ou un depot
                etapeArriveeChemin = cheminEntreEtape.getEtapeArrivee();
                requeteEtapeArriveeCheminCourant = mapRequete.get(etapeArriveeChemin.getIdAdresse());

                if(i < tournee.getListeChemins().size()-1){
                    if(requeteEtapeArriveeCheminCourant.getEtapeDepot().getIdAdresse().equals(etapeArriveeChemin.getIdAdresse()))
                        bw.write("<br>Deposer");
                    else
                        bw.write("<br>Récuperer");

                    bw.write(" collis a l'"+ etapeArriveeChemin.getNomAdresse() +" à "+etapeArriveeChemin.getHeureDePassage()+".");
                }
                else{
                    // TODO: Arthur fixé ?
                    bw.write("<br>Retour à l'entrepot situé à l'"+tournee.getEtapeDepart().getNomAdresse()+" à "+tournee.getEtapeDepart().getHeureDePassage());
                }

                bw.write("</p>");
            }

            //fermeture du fichier et affichage
            bw.write("</body></html>");
            bw.close();
            Desktop.getDesktop().browse(f.toURI());
        }
        //catch (IOException e)
        catch(Exception e) {
            e.printStackTrace();
        }
   }
}

