package Model;
import Algorithmie.CalculateurTournee;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;

import java.util.HashMap;

public class FeuilleRoute {




   public void createFeuilleRoute(){

       Long IdAdEtapArriveeCheminCourant ;
       Requete requeteEtapeArriveeCheminCourant;
       Etape etapeArriveeChemin;


            try
            {
                //essai de faire la creation de fichier interactive


                JFrame frameSelectCarte = new JFrame();

                FileDialog fd = new FileDialog(frameSelectCarte, "Sélectionnez le nom du fichier", FileDialog.LOAD);
                fd.setDirectory("C:\\");
                fd.setFile("*.xml");
                fd.setVisible(true);
                String filename = fd.getDirectory() + fd.getFile();

                File f = new File(filename);
                boolean exists = f.exists();
                if(exists) {
                  //  JOptionPane.showMessageDialog(null, "FileName already exists /n voulez-vous le remplacez");
                  /*  JOptionPane.showInternalConfirmDialog(null,

                            "please choose one", "fichier existe déja /n voulez-vous le remplacez",

                            JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);*/
                    int a=JOptionPane.showConfirmDialog(null,"Are you sure?");
                    if(a==JOptionPane.YES_OPTION){
                        System.out.println("t'as choisit oui");
                    }


                }

         //       File f = new File("/home/besme/my_work_space/S3-IF/FeuilleRoutes/test_feuille_route.html");
                Tournee tournee = new Tournee();

                //debut bloc temporaire

                try {
                    LecteurXML lecteurXML = new LecteurXML();
                    Carte carte = new Carte();
                    carte = lecteurXML.lectureCarte("./src/FichiersXML/mediumMap.xml");
                    tournee = lecteurXML.lectureRequete("./src/FichiersXML/requestsMedium3.xml");
                    CalculateurTournee calculateurTournee = new CalculateurTournee(carte, tournee);
                    calculateurTournee.calculerTournee();
                } catch (Exception e){
                    e.printStackTrace();
                }

                // fin bloc
                HashMap<Long, Requete> mapRequete= new HashMap<>();

                for(Requete requete: tournee.getListeRequetes()){
                    mapRequete.put(requete.getEtapeCollecte().getIdAdresse(),requete) ;
                    mapRequete.put(requete.getEtapeDepot().getIdAdresse(),requete) ;

                }

                BufferedWriter bw = new BufferedWriter(new FileWriter(f));
               // bw.write("<html><body><p> L'heure depart depuis l'entrepot :"+tournee.getDateDepart());
                bw.write("<html><body><p>");
                if(exists)
                    bw.write("fichier remplacé");


                bw.write(" Depart de l'entrepot situe a l'adresse  :"+tournee.getAdresseDepart());


                for(CheminEntreEtape cheminEntreEtape: tournee.getListeChemins() ){
                    //dans ce qui suit on decrit le chemin que doit suivre le cycliste pour arriver a une etape
                    bw.write("<p>");

                    for(Segment segment: cheminEntreEtape.getListeSegment() ){
                        bw.write("<br>Prendre rue "+ segment .getNom()+" pendant "+ segment.getLongueur());
                    }
                   /* Etape etapeArriveeChemin = cheminEntreEtape.getEtapeArrivee();
                    boolean estEtapeArrive=false;

                    for(Requete requete: tournee.getListeRequetes()){
                        if(requete.getEtapeDepot().equals(etapeArriveeChemin)){
                            estEtapeArrive= true;
                        }
                    }*/
                    etapeArriveeChemin = cheminEntreEtape.getEtapeArrivee();

                    IdAdEtapArriveeCheminCourant = cheminEntreEtape.getEtapeArrivee().getIdAdresse();
                    requeteEtapeArriveeCheminCourant = mapRequete.get(IdAdEtapArriveeCheminCourant);

                    //Si l'id de l'adresse de l'etape courante est egale a celui de l'etape depot du requete a laquelle
                    // l'etapeArriveChemin est lié alors l'etape a laquelle on est arrive est une etape de depot sinon
                    //certainement elle est une etape de collecte

                    if(requeteEtapeArriveeCheminCourant.getEtapeDepot().getIdAdresse()==IdAdEtapArriveeCheminCourant)
                        bw.write("<br>Deposer collis a l'adresse "+ etapeArriveeChemin.getNomAdresse() +" à l'heure "+etapeArriveeChemin.getHeureDePassage());
                    else
                        bw.write("<br>Récuperer collis a l'adresse "+ etapeArriveeChemin.getNomAdresse() +" à l'heure "+etapeArriveeChemin.getHeureDePassage());

                    bw.write("</p>");

                }

              //  bw.write("<textarea cols=75 rows=10>");

                //bw.write("</textarea>");
                bw.write("</body></html>");
                bw.close();

            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
}

