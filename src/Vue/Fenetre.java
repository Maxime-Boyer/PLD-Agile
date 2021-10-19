package Vue;

import Controleur.Controleur;
import Controleur.NomEtat;
import Model.Carte;

import javax.swing.*;
import java.awt.*;

public class Fenetre extends JFrame{

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

    public Fenetre(Carte carte, Controleur controleur){
        this.setTitle("Raccourc'IF - Hexanome Détect'IF");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(null);
        this.setLocationRelativeTo(null);

        // Récupération des dimensions de l'écran
        Dimension dimensionsEcran = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(dimensionsEcran.width, dimensionsEcran.height);

        this.ecouteurBoutons = new EcouteurBoutons(controleur);

        afficherEtat(NomEtat.ETAT_INITIAL);
        this.setVisible(true);
    }

    public void afficherEtat(NomEtat etat){

        switch (etat){
            case ETAT_INITIAL:
                // E0: Vue ecran Accueil
                ecranAccueil = new EcranAccueil(this.getWidth(), this.getHeight(), policeSousTitre, policeTexte, this.ecouteurBoutons);
                this.add(ecranAccueil);
                break;
            case ETAT_PLAN_AFFICHE:
                //E1: Carte chargée
                cartePanel = new CartePanel(this.getWidth(), this.getHeight(), policeTexte);
                this.add(cartePanel);
                menuLateral = new MenuLateral(this.getWidth(), this.getHeight(), policeTexte, policeTexteImportant, ecouteurBoutons);
                this.add(menuLateral);
                break;
            case ETAT_TOURNEE_CHARGEE:
                // E2: Tournee chargee
                cartePanel.tracerRequetes();
                menuLateral.afficherMenuRequete(cartePanel.getTournee());
                break;
            case ETAT_TOURNEE_PREPAREE:
                menuLateral.afficherMenuEtapes();
                break;
        }

        // repaint la fenetre
        this.revalidate();
        this.repaint();
    }

    public void retirerElment(NomEtat etatPrecedent){
        switch (etatPrecedent){
            case ETAT_INITIAL:
                if(ecranAccueil != null)
                    this.remove(ecranAccueil);
                break;
        }
    }
}
