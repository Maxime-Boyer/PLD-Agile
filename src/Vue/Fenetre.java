package Vue;

import Controleur.Controleur;
import Controleur.NomEtat;
import Exceptions.NameFile;
import Model.Carte;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.Timer;

public class Fenetre extends JFrame {

    protected final static String IMPORT_CARTE = "Importer carte";
    protected final static String IMPORT_TOURNEE = "Importer tournée";
    protected final static String PREPARER_TOURNEE = "Préparer tournée";

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

    public Fenetre(Carte carte, Controleur controleur) throws NameFile {
        this.setTitle("Raccourc'IF - Hexanome Détect'IF");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(null);
        this.setLocationRelativeTo(null);

        // Récupération des dimensions de l'écran
        Dimension dimensionsEcran = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(dimensionsEcran.width*2/3, dimensionsEcran.height*2/3);
        setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);

        this.setLocationRelativeTo(null);

        this.ecouteurBoutons = new EcouteurBoutons(controleur);
        this.ecouteurSurvol = new EcouteurSurvol(this);

        this.setResizable(true); //TODO: passer à false

        this.setVisible(true);

        afficherEtat(NomEtat.ETAT_INITIAL);
    }

    public void afficherEtat(NomEtat etat) throws NameFile {

        switch (etat) {
            case ETAT_INITIAL:
                System.out.println("Fenetre.afficherEtat() : ETAT_INITIAL");
                // E0: Vue ecran Accueil
                ecranAccueil = new EcranAccueil(this.getWidth(), this.getHeight(), policeSousTitre, policeTexte, this.ecouteurBoutons);
                this.add(ecranAccueil);
                break;
            case ETAT_PLAN_AFFICHE:
                System.out.println("Fenetre.afficherEtat() : ETAT_PLAN_AFFICHE");
                //E1: Carte chargée
                cartePanel = new CartePanel(this.getWidth(), this.getHeight() - 20, policeTexte, ecouteurSurvol);
                this.add(cartePanel);
                menuLateral = new MenuLateral(this.getWidth(), this.getHeight() - 20, policeTexte, policeTexteImportant, ecouteurBoutons, ecouteurSurvol);
                this.add(menuLateral);
                break;
            case ETAT_TOURNEE_CHARGEE:
                System.out.println("Fenetre.afficherEtat() : ETAT_TOURNEE_CHARGEE");
                // E2: Tournee chargee
                cartePanel.tracerRequetes();
                menuLateral.afficherMenuRequete(cartePanel.getTournee());
                legende = new Legende();
                break;
            case ETAT_TOURNEE_PREPAREE:
                System.out.println("Fenetre.afficherEtat() : ETAT_TOURNEE_PREPAREE ");
                cartePanel.tracerItineraire();
                menuLateral.afficherMenuEtapes(cartePanel.getTournee());
                break;
        }

        // repaint la fenetre
        this.revalidate();
        this.repaint();
    }

    public CartePanel getCartePanel(){ return cartePanel; }

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
