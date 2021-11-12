package Model;

import Vue.CartePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

import java.util.HashMap;

/**
 * Permet de créer la feuille de route de la tournée.
 */
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

            //styles
            bw.write("<style>");
            bw.write("h2{ margin: 5px 0 0; }");
            bw.write(".imgCarte, h1{ position: fixed; }");
            bw.write(".imgCarte{ width: 800px; }");
            bw.write(".descriptionTextuelle{ margin-left: 850px; }");
            bw.write(".etape{ border: solid; border-width: 2px; padding: 10px; border-radius: 5px; margin: 20px 0; }");
            bw.write("</style>");

            //contenu
            bw.write("<h1>Feuille de route</h1>");
            bw.write("<img src='./carte.png' class='imgCarte'>");

            HashMap<Long, Requete> mapRequete= new HashMap<>();
            for(Requete requete: tournee.getListeRequetes()){
                mapRequete.put(requete.getEtapeCollecte().getIdAdresse(),requete) ;
                mapRequete.put(requete.getEtapeDepot().getIdAdresse(),requete) ;
            }

            // TODO: Arthur fixé ?
            bw.write(" <div class='descriptionTextuelle'>");
            bw.write("<div class='etape' style='border-color: red; background-color: rgba(255,0,0,0.1);'>");
            bw.write(" <h2>Départ</h2>");
            bw.write("<p>Départ de l'entrepôt situé à l'"+tournee.getEtapeDepart().getNomAdresse()+" à "+tournee.getDateDepart()+".</p>");
            bw.write("</div>");

            Requete requeteEtapeArriveeCheminCourant;
            Etape etapeArriveeChemin;
            String rueActuelle = "";
            Segment segmentActuel;
            Double distanceRueActuelle = 0.0;
            CheminEntreEtape cheminEntreEtape;
            int minutesPassageAvantRetouche = 0;
            String heurePassage = "", minutesPassage = "";
            //pour chaque chemin, affichage de la liste des rues à emprunter
            String couleurCss = "";
            for(int i = 0; i < tournee.getListeChemins().size(); i++ ){

                cheminEntreEtape = tournee.getListeChemins().get(i);
                etapeArriveeChemin = cheminEntreEtape.getEtapeArrivee();
                requeteEtapeArriveeCheminCourant = mapRequete.get(etapeArriveeChemin.getIdAdresse());

                if( i == tournee.getListeChemins().size()-1){
                    bw.write("<div class='etape' style='border-color: red; background-color: rgba(255,0,0,0.1);'>");
                }
                else {
                    couleurCss = requeteEtapeArriveeCheminCourant.getCouleurRequete().getRed() + ",";
                    couleurCss += requeteEtapeArriveeCheminCourant.getCouleurRequete().getGreen() + ",";
                    couleurCss += requeteEtapeArriveeCheminCourant.getCouleurRequete().getBlue() + "";
                    bw.write("<div class='etape' style='border-color: rgb("+couleurCss+"); background-color: rgba("+couleurCss+", 0.1);'>");
                }

                bw.write("<h2>Etape "+(i+1)+"</h2>");

                bw.write("<ol>");

                for(int j = 0; j < cheminEntreEtape.getListeSegment().size(); j++){

                    segmentActuel = cheminEntreEtape.getListeSegment().get(j);
                    if(!rueActuelle.equals(segmentActuel.getNom()) || j == cheminEntreEtape.getListeSegment().size()-1){

                        if(j > 0){
                            String nomRue = "rue";
                            //Cas où la rue n'à pas de nom
                            if (rueActuelle != "")
                                nomRue = rueActuelle;
                            bw.write("<li>Prendre "+ nomRue +" sur "+ (int) Math.round(distanceRueActuelle) + "m.</li>");
                        }

                        rueActuelle = segmentActuel.getNom();
                        distanceRueActuelle = segmentActuel.getLongueur();
                    }
                    else{
                        distanceRueActuelle += segmentActuel.getLongueur();
                    }
                }
                bw.write("</ol>");
                bw.write("<p>");

                //on determine si l'adresse d'arrivee est une collecte ou un depot

                if(i < tournee.getListeChemins().size()-1){
                    if(requeteEtapeArriveeCheminCourant.getEtapeDepot().getIdAdresse().equals(etapeArriveeChemin.getIdAdresse()))
                        bw.write("Déposer");
                    else
                        bw.write("Récupérer");

                    String dureeEtape = String.valueOf(etapeArriveeChemin.getDureeEtape()/60) + "min";

                    if(etapeArriveeChemin.getDureeEtape()%60 > 0){
                        dureeEtape += String.valueOf(etapeArriveeChemin.getDureeEtape()%60) + " sec";
                    }

                    bw.write(" colis à l'"+ etapeArriveeChemin.getNomAdresse() +" à "+etapeArriveeChemin.getHeureDePassage()+". Durée de l'étape: "+dureeEtape+".");
                }
                else{
                    // TODO: Arthur fixé ?
                    bw.write("Retour à l'entrepôt situé à l'"+tournee.getEtapeDepart().getNomAdresse()+" à "+tournee.getEtapeDepart().getHeureDePassage());
                }

                bw.write("</p>");
                bw.write("</div>");
            }
            bw.write(" </div>");

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

