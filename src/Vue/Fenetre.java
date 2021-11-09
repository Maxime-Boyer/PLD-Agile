package Vue;


import Controleur.Controleur;
import Controleur.NomEtat;
import Model.Carte;
import Model.Tournee;

import javax.swing.*;
import java.awt.*;

public class Fenetre extends JFrame {

    protected final static String IMPORT_CARTE = "Importer carte";
    protected final static String IMPORT_TOURNEE = "Importer tournée";
    protected final static String PREPARER_TOURNEE = "Préparer tournée";
    protected final static String EXPORTER_FEUILLE_ROUTE = "Exporter feuille de route";


    protected final static int valMarginBase = 5;
    protected final static int hauteurBouton = 50;

    private EcouteurBoutons ecouteurBoutons;
    private EcouteurSurvol ecouteurSurvol;
    private EcouteurDragDrop ecouteurSouris;

    // definition des polices
    private Font policeTitre = new Font("SansSerif", Font.BOLD, 28);
    private Font policeSousTitre = new Font("SansSerif", Font.BOLD, 20);
    private Font policeTexteImportant = new Font("SansSerif", Font.BOLD, 16);
    private Font policeTexte = new Font("SansSerif", 400, 14);

    private EcranAccueil ecranAccueil;
    private MenuLateral menuLateral;
    private CartePanel cartePanel;

    private Carte carte;
    private Tournee tournee;

    /**
     * Cree la fenetre dans laquelle s'ouvre l'application
     * @param carte: la carte a afficher
     * @param controleur: lke controleur du MVC
     */
    public Fenetre(Carte carte, Tournee tournee, Controleur controleur) {

        this.carte = carte;
        this.tournee = tournee;

        this.setTitle("Raccourc'IF - Hexanome Détect'IF");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(null);
        this.setLocationRelativeTo(null);

        // Récupération des dimensions de l'écran
        Dimension dimensionsEcran = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(dimensionsEcran.width*2/3, dimensionsEcran.height*2/3);
        setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);

        this.setLocationRelativeTo(null);

        //Cré les écouteurs
        this.ecouteurBoutons = new EcouteurBoutons(controleur);
        this.ecouteurSurvol = new EcouteurSurvol(this);
        this.ecouteurSouris = new EcouteurDragDrop();

        this.setResizable(true); //TODO: passer à false

        this.setVisible(true);

        //Après avoir tout initialisé, affiche l'état initial
        afficherEtat(NomEtat.ETAT_INITIAL);
    }

    /**
     * Affiche le système de fichier et retourne le nom du fichier choisi
     * @return nom du fichier choisi
     */
    public String afficherChoixFichier() {
        System.out.println("Frentre.afficherChoixFichierCarte() : ETAT_CHOIX_FICHIER_CARTE");
        MenuChoixFichier menuChoixFichier = new MenuChoixFichier();
        System.out.println("    menuChoixFichier.getNomFichier(); = " + menuChoixFichier.getNomFichier());
        return menuChoixFichier.getNomFichier();
    }

    /**
     * TODO
     * @param carte
     */
    public void afficherEtatPlanAffiche(Carte carte) {
        System.out.println("Frentre.afficherEtatPlanAffiche(carte) : ETAT_PLAN_AFFICHE");
        //E1: Carte chargée
        if (cartePanel == null) {
            cartePanel = new CartePanel(carte, tournee, this.getContentPane().getWidth(), this.getContentPane().getHeight(), policeTexte, ecouteurBoutons, ecouteurSurvol, ecouteurSouris);
            this.add(cartePanel);
        }
        if (menuLateral == null) {
            menuLateral = new MenuLateral(tournee, this.getContentPane().getWidth(), this.getContentPane().getHeight(), policeTexte, policeTexteImportant, ecouteurBoutons, ecouteurSurvol);
            this.add(menuLateral);
        }

        // repaint la fenetre
        this.revalidate();
        this.repaint();
    }

    /**
     * Affichage déclenchée lorsque la tournée a été chargée : permet d'afficher les requêtes sur la vue graphique et la vue textuelle
     * @param tournee la liste de requêtes qui doit être affichée
     */
    public void afficherEtatTourneChargee (Tournee tournee) {
        System.out.println("Frentre.afficherEtatTourneChargee(tounee) : ETAT_TOURNEE_CHARGEE");
        //cartePanel.tracerRequetes(tournee);
        //menuLateral.afficherMenuRequete(tournee);

        // repaint la fenetre
        this.revalidate();
        this.repaint();
    }

    /**
     * Affichage déclenchée lorsque la tournée a été calculé : permet d'afficher la tournee calculée sur la vue graphique et la vue textuelle
     * @param tournee la tournee calculée qui doit être affichée
     */
    public void afficherEtatTourneePreparee (Tournee tournee) {
        System.out.println("Fenetre.afficherEtatTourneePreparee(tournee) : ETAT_TOURNEE_PREPAREE ");
        //cartePanel.tracerItineraire(tournee);
        //menuLateral.afficherMenuEtapes(tournee);
    }

    /**
     * Affiche les etas détermines par le controleur
     * @param etat: l'etat a afficher
     */
    public void afficherEtat(NomEtat etat) {

        switch (etat) {
            case ETAT_INITIAL:
                System.out.println("Fenetre.afficherEtat() : ETAT_INITIAL");
                // E0: Vue ecran Accueil
                ecranAccueil = new EcranAccueil(this.getWidth(), this.getHeight(), policeSousTitre, policeTexte, this.ecouteurBoutons);
                this.add(ecranAccueil);
                break;

            /*case ETAT_PLAN_AFFICHE:
                System.out.println("Frentre.afficherEtat() : ETAT_PLAN_AFFICHE");
                //E1: Carte chargée
                cartePanel = new CartePanel(this.getWidth(), this.getHeight() - 20, policeTexte, ecouteurSurvol);
                this.add(cartePanel);
                menuLateral = new MenuLateral(this.getWidth(), this.getHeight() - 20, policeTexte, policeTexteImportant, ecouteurBoutons, ecouteurSurvol);
                this.add(menuLateral);
                menuLateral.setMessageUtilisateur("Veuillez importer une tournée au format xml pour l'afficher sur la carte.");
                break;
            case ETAT_TOURNEE_CHARGEE:
                System.out.println("Fenetre.afficherEtat() : ETAT_TOURNEE_CHARGEE");
                // E2: Tournee chargee
                cartePanel.tracerRequetes();
                menuLateral.afficherMenuRequete(cartePanel.getTournee());
                menuLateral.setMessageUtilisateur("Veuillez préparer la tournée pour visualiser l'itinéraire sur la carte.");
                legende = new Legende();
                break;
            case ETAT_TOURNEE_PREPAREE:
                System.out.println("Fenetre.afficherEtat() : ETAT_TOURNEE_PREPAREE ");
                cartePanel.tracerItineraire();
                menuLateral.afficherMenuEtapes(cartePanel.getTournee());
                menuLateral.setMessageUtilisateur("Maintenant vous pouvez éditer votre tournée ou exporter la feuille de route.");
                break;*/
        }

        // repaint la fenetre
        this.revalidate();
        this.repaint();
    }

    /**
     * geteur
     * @return: le panel d'affichage de la carte
     */
    public CartePanel getCartePanel(){ return cartePanel; }

    /**
     * Les methodes suivantes permenttent de retirer des elements de la fenetre
     */
    //Permet de retirer des pannel
    public void retirerEcranAccueil() {
        this.remove(ecranAccueil);
    }

    /*public void retirerCartePanel() {
        cartePanel.setVisible(false);
        //this.remove(cartePanel);
    }*/
    /*
    public void retirerMenuLateral() {
        this.remove(menuLateral);
    }

    public void retirerMenuRequete() {
        menuLateral.retirerMenuRequete();
    }

    public void retirerMenuEtape() {
        menuLateral.retirerMenuEtape();
    }*/
}
