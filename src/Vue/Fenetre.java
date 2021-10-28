package Vue;

import Controleur.Controleur;
import Controleur.NomEtat;
import Model.Carte;

import javax.swing.*;
import java.awt.*;

public class Fenetre extends JFrame {

    protected final static String IMPORT_CARTE = "Importer carte";
    protected final static String IMPORT_TOURNEE = "Importer tournée";
    protected final static String PREPARER_TOURNEE = "Préparer tournée";

    private EcouteurBoutons ecouteurBoutons;

    // definition des polices
    private Font policeTitre = new Font("SansSerif", Font.BOLD, 28);
    private Font policeSousTitre = new Font("SansSerif", Font.BOLD, 20);
    private Font policeTexteImportant = new Font("SansSerif", Font.BOLD, 16);
    private Font policeTexte = new Font("SansSerif", 400, 14);

    private EcranAccueil ecranAccueil;
    private MenuLateral menuLateral;
    private CartePanel cartePanel;
    private Legende legende;

    public Fenetre(Carte carte, Controleur controleur) {
        this.setTitle("Raccourc'IF - Hexanome Détect'IF");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(null);
        this.setLocationRelativeTo(null);

        // Récupération des dimensions de l'écran
        Dimension dimensionsEcran = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(dimensionsEcran.width, dimensionsEcran.height);

        this.ecouteurBoutons = new EcouteurBoutons(controleur);

        afficherEtat(NomEtat.ETAT_INITIAL);
        this.setResizable(false);
        this.setVisible(true);
    }

    public void afficherEtat(NomEtat etat) {

        switch (etat) {
            case ETAT_INITIAL:
                System.out.println("Frentre.afficherEtat() : ETAT_INITIAL");
                // E0: Vue ecran Accueil
                ecranAccueil = new EcranAccueil(this.getWidth(), this.getHeight(), policeSousTitre, policeTexte, this.ecouteurBoutons);
                this.add(ecranAccueil);
                break;
            case ETAT_PLAN_AFFICHE:
                System.out.println("Frentre.afficherEtat() : ETAT_PLAN_AFFICHE");
                //E1: Carte chargée
                cartePanel = new CartePanel(this.getWidth(), this.getHeight() - 20, policeTexte);
                this.add(cartePanel);
                menuLateral = new MenuLateral(this.getWidth(), this.getHeight() - 20, policeTexte, policeTexteImportant, ecouteurBoutons);
                this.add(menuLateral);
                break;
            case ETAT_TOURNEE_CHARGEE:
                System.out.println("Frentre.afficherEtat() : ETAT_TOURNEE_CHARGEE");
                // E2: Tournee chargee
                cartePanel.tracerRequetes();
                menuLateral.afficherMenuRequete(cartePanel.getTournee());
                legende = new Legende();
                break;
            case ETAT_TOURNEE_PREPAREE:
                System.out.println("Frentre.afficherEtat() : ETAT_TOURNEE_PREPAREE");
                cartePanel.tracerItineraire();
                menuLateral.afficherMenuEtapes();
                break;
        }

        // repaint la fenetre
        this.revalidate();
        this.repaint();
    }

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
