package Controleur;

import Algorithmie.RunnableAlgorithmie;
import Model.Carte;
import Model.LecteurXML;
import Model.Tournee;
import Vue.Fenetre;

import javax.swing.*;

public class EtatTourneeChargee implements Etat {

    @Override
    public void preparerTournee (Controleur controleur, Fenetre fenetre, Carte carte, Tournee tournee) {
        System.out.println("EtatTourneeChargee : preparerTournee");

        //On demarre le minuteur
        controleur.getBooleanThread().setBoolMinuteurDemarre(true);

        //On attend le resultat
        while(!controleur.getBooleanThread().isResultatTrouve()){
            try {
                //System.out.println("Main : Waiting...");
                Thread.sleep(1000);
            } catch(InterruptedException e){
                e.printStackTrace();
            }
        }




            //Calcul la tounee
            //Change vers l'état etatTourneeOrdonnee avec la nouvelle carte
            fenetre.afficherEtatTourneePreparee(tournee);
            controleur.setEtatActuel(controleur.etatTourneeOrdonnee);

        //Passe la tournee à ordonne et notifie l'observer que l'objet tournée a été modifié
        tournee.setTourneeEstOrdonee(true);
        tournee.notifyObservers(tournee);

        /*} catch (AStarImpossibleException e) {
            String messageErreur = e.getMessage();
            System.out.println("ERREUR "+e);
            JOptionPane.showMessageDialog(null, messageErreur);
            //Reste dans l'état actuel
        }*/
    }

    @Override
    public void chargerListeRequete (Controleur controleur, Fenetre fenetre, Carte carte, Tournee tournee) {
        //On arrete le precedent Thread si il existe
        BooleanThread ancienBooleanThread = controleur.getBooleanThread();
        if(ancienBooleanThread != null){
            ancienBooleanThread.setStopThread(true);
            while(!ancienBooleanThread.isResultatTrouve()){
                try {
                    Thread.sleep(200);
                } catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
        }


        System.out.println("EtatTourneeChargee : preparerTournee");
        /*fenetre.retirerMenuRequete();
        fenetre.afficherEtat(NomEtat.ETAT_TOURNEE_CHARGEE);
        controleur.setEtatActuel(controleur.etatTourneeChargee);*/
        //Récupère le nom du fichier choisi

        String nomFichier = fenetre.afficherChoixFichier();
        //Appel la méthode qui vérifie si le fichier est valide et récupère la tournee
        LecteurXML lecteur = new LecteurXML();
        try {
            tournee = lecteur.lectureRequete(nomFichier, carte, tournee);
            //Change vers l'état PlanAffiche avec la nouvelle carte
            fenetre.afficherEtatTourneChargee(tournee);
            controleur.setEtatActuel(controleur.etatTourneeChargee);
        } catch (Exception e) {
            //En cas d'erreur
            String messageErreur = e.getMessage();
            System.out.println("ERREUR "+e);
            JOptionPane.showMessageDialog(null, messageErreur);
            //Reste dans l'état actuel
        }

        //On cree le nouveau thread
        BooleanThread booleanThread = new BooleanThread(false,false,false);
        controleur.setBooleanThread(booleanThread);
        RunnableAlgorithmie runnableAlgorithmie = new RunnableAlgorithmie(carte,tournee,booleanThread);
        controleur.setRunnableAlgorithmie(runnableAlgorithmie);
        Thread thread = new Thread(runnableAlgorithmie);
        controleur.setThreadAlgorithmie(thread);
        thread.start();
    }

    @Override
    public void chargerPlan (Controleur controleur, Fenetre fenetre, Carte carte, Tournee tournee) {
        System.out.println("EtatTourneeChargee : chargerPlan");
        /*
        fenetre.retirerCartePanel();
        fenetre.retirerMenuLateral();
        fenetre.afficherEtat(NomEtat.ETAT_PLAN_AFFICHE);
        controleur.setEtatActuel(controleur.etatPlanAffiche);*/


        //Récupère le nom du fichier choisi
        String nomFichier = fenetre.afficherChoixFichier();
        //Appel la méthode qui vérifie si le fichier est valide et récupère la carte
        LecteurXML lecteur = new LecteurXML();
        try {
            carte = lecteur.lectureCarte(nomFichier, carte);
            //Change vers l'état PlanAffiche avec la nouvelle carte
            //fenetre.retirerCartePanel();
            tournee.reset();
            //fenetre.retirerMenuLateral();
            fenetre.afficherEtatPlanAffiche(carte);
            controleur.setEtatActuel(controleur.etatPlanAffiche);
        } catch (Exception e) {
            //En cas d'erreur
            String messageErreur = e.getMessage();
            System.out.println("ERREUR "+e);
            JOptionPane.showMessageDialog(null, messageErreur);
            //Reste dans l'état actuel
        }
    }

}
