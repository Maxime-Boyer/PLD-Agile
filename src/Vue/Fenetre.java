package Vue;


import Controleur.Controleur;
import Controleur.NomEtat;
import Model.Carte;
import Model.Tournee;

import javax.swing.*;
import java.awt.*;

import static Controleur.NomEtat.ETAT_INITIAL;

public class Fenetre extends JFrame {

    protected final static String IMPORT_CARTE = "Importer carte";
    protected final static String IMPORT_TOURNEE = "Importer tournée";
    protected final static String PREPARER_TOURNEE = "Préparer tournée";

    protected final static int valMarginBase = 5;
    protected final static int hauteurBouton = 50;

    private EcouteurBoutons ecouteurBoutons;
    private EcouteurSurvol ecouteurSurvol;

    // definition des polices
    private Font policeTitre = new Font("SansSerif", Font.BOLD, 28);
    private Font policeSousTitre = new Font("SansSerif", Font.BOLD, 20);
    private Font policeTexteImportant = new Font("SansSerif", Font.BOLD, 16);
    private Font policeTexte = new Font("SansSerif", 400, 14);

    private EcranAccueil ecranAccueil;
    private MenuLateral menuLateral;
    private CartePanel cartePanel;
    private Legende legende;

    /**
     * Cree la fenetre dans laquelle s'ouvre l'application
     * @param carte: la carte a afficher
     * @param controleur: lke controleur du MVC
     */
    public Fenetre(Carte carte, Controleur controleur) {

        this.setTitle("Raccourc'IF - Hexanome Détect'IF");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(null);
        this.setLocationRelativeTo(null);

        // Récupération des dimensions de l'écran
        Dimension dimensionsEcran = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(dimensionsEcran.width, dimensionsEcran.height);

        this.ecouteurBoutons = new EcouteurBoutons(controleur);
        this.ecouteurSurvol = new EcouteurSurvol(this);

        afficherEtat(ETAT_INITIAL);
        this.setResizable(true); //TODO: passer à false
        this.setVisible(true);
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
        cartePanel = new CartePanel(carte, this.getWidth(), this.getHeight() - 20, policeTexte, ecouteurBoutons, ecouteurSurvol);
        this.add(cartePanel);
        menuLateral = new MenuLateral(this.getWidth(), this.getHeight() - 20, policeTexte, policeTexteImportant, ecouteurBoutons, ecouteurSurvol);
        this.add(menuLateral);

        // repaint la fenetre
        this.revalidate();
        this.repaint();
    }

    /**
     * TODO
     * @param tournee
     */
    public void afficherEtatTourneChargee (Tournee tournee) {
        cartePanel.tracerRequetes(tournee);
        menuLateral.afficherMenuRequete(cartePanel.getTournee());
        legende = new Legende();

        // repaint la fenetre
        this.revalidate();
        this.repaint();
    }

    /**
     * Affiche les etas détermines par le controleur
     * @param etat: l'etat a afficher
     */
    public void afficherEtat(NomEtat etat) {

        switch (etat) {
            case ETAT_INITIAL:
                System.out.println("Frentre.afficherEtat() : ETAT_INITIAL");
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
                System.out.println("Frentre.afficherEtat() : ETAT_TOURNEE_CHARGEE");
                // E2: Tournee chargee
                cartePanel.tracerRequetes();
                menuLateral.afficherMenuRequete(cartePanel.getTournee());
                menuLateral.setMessageUtilisateur("Veuillez préparer la tournée pour visualiser l'itinéraire sur la carte.");
                legende = new Legende();
                break;*/
            case ETAT_TOURNEE_PREPAREE:
                System.out.println("Frentre.afficherEtat() : ETAT_TOURNEE_PREPAREE ");
                cartePanel.tracerItineraire();
                menuLateral.afficherMenuEtapes(cartePanel.getTournee());
                menuLateral.setMessageUtilisateur("Maintenant vous pouvez éditer votre tournée ou exporter la feuille de route.");
                break;
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

    public void retirerCartePanel() {
        this.remove(cartePanel);
    }

    public void retirerMenuLateral() {
        this.remove(menuLateral);
    }

    public void retirerMenuRequete() {
        menuLateral.retirerMenuRequete();
    }

    public void retirerMenuEtape() {
        menuLateral.retirerMenuEtape();
    }
}
