package Vue;


import Controleur.Controleur;
import Exceptions.ValeurNegativeException;
import Model.Carte;
import Model.Etape;
import Model.Tournee;

import javax.swing.*;
import java.awt.*;

public class Fenetre extends JFrame {

    protected final static String IMPORT_CARTE = "Importer carte";
    protected final static String IMPORT_TOURNEE = "Importer tournée";
    protected final static String PREPARER_TOURNEE = "Préparer tournée";
    protected final static String AJOUT_REQUETE = "Ajouter requête";
    protected final static String VALIDER_AJOUT_DUREE_COLLECTE_REQUETE = "Valider";
    protected final static String SUPPRIMER_REQUETE = "X";
    protected final static String EXPORTER_FEUILLE_ROUTE = "Exporter feuille de route";
    protected final static String ANNULER_AJOUT_REQUETE = "Annuler";
    protected final static String UNDO = "<--";
    protected final static String REDO = "-->";

    protected final static int valMarginBase = 5;
    protected final static int hauteurBouton = 50;

    private EcouteurBoutons ecouteurBoutons;
    private EcouteurSurvol ecouteurSurvol;
    private EcouteurSouris ecouteurSouris;
    private EcouteurDragDrop ecouteurDragDrop;

    // definition des polices
    private Font policeTitre = new Font("SansSerif", Font.BOLD, 28);
    private Font policeSousTitre = new Font("SansSerif", Font.BOLD, 20);
    private Font policeTexteImportant = new Font("SansSerif", Font.BOLD, 16);
    private Font policeTexte = new Font("SansSerif", 400, 14);

    private EcranAccueil ecranAccueil;
    private MenuLateral menuLateral;
    public CartePanel cartePanel;
    private PopUpSaisieDuree popUpSaisieDuree;

    private Carte carte;
    private Tournee tournee;

    private boolean authorisationCliquerBoutonUndo = false;
    private boolean authorisationCliquerBoutonRedo = false;

    /**
     * Cree la fenetre dans laquelle s'ouvre l'application
     *
     * @param carte:      la carte a afficher
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
        this.setSize(dimensionsEcran.width, dimensionsEcran.height);
        setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
        this.setLocationRelativeTo(null);

        //Cré les écouteurs
        this.ecouteurBoutons = new EcouteurBoutons(controleur);
        this.ecouteurSouris = new EcouteurSouris(controleur, cartePanel, this);
        this.ecouteurSurvol = new EcouteurSurvol(controleur, this);
        this.ecouteurDragDrop = new EcouteurDragDrop();

        this.setResizable(false);

        this.setVisible(true);

        //Après avoir tout initialisé, affiche l'état initial
        afficherEtat();
    }

    public PopUpSaisieDuree getPopUpSaisieDuree() {
        return popUpSaisieDuree;
    }

    /**
     * Affiche le système de fichier et retourne le nom du fichier choisi
     *
     * @return nom du fichier choisi
     */
    public String afficherChoixFichier() {
        MenuChoixFichier menuChoixFichier = new MenuChoixFichier();
        return menuChoixFichier.getNomFichier();
    }

    /**
     * Affichage de l'état juste après le chargement de la carte : affiche / cache les pannels selon leur utilité
     *
     * @param carte
     */
    public void afficherEtatPlanAffiche(Carte carte) {

        supprimerPositionRequete();

        if (cartePanel == null) {
            cartePanel = new CartePanel(carte, tournee, this.getContentPane().getWidth(), this.getContentPane().getHeight(), policeTexte, ecouteurBoutons, ecouteurSouris, ecouteurSurvol, ecouteurDragDrop);
            this.add(cartePanel);
        }
        if (menuLateral == null) {
            menuLateral = new MenuLateral(tournee, this.getContentPane().getWidth(), this.getContentPane().getHeight(), policeTexte, policeTexteImportant, ecouteurBoutons, ecouteurSurvol);
            this.add(menuLateral);
        }

        menuLateral.setMessageUtilisateur("Veuillez importer une tournée au format xml pour l'afficher sur la carte.");

        //Configure les visibilités
        menuLateral.visibilitePannelImportation(true);
        menuLateral.visibiliteMenuPreparerTournee(false);
        menuLateral.visibiliteBoutonExporterFeuilleRoute(false);
        menuLateral.visibiliteBoutonAjouterRequete(false);
        menuLateral.visibiliteBoutonUndo(false);
        menuLateral.visibiliteBoutonRedo(false);
        menuLateral.visibiliteBoutonAnnulerAjoutRequete(false);
        menuLateral.retirerMenuRequete();
        menuLateral.retirerMenuEtape();

        // repaint la fenetre
        this.revalidate();
        this.repaint();
    }

    /**
     * Affichage déclenchée lorsque la tournée a été chargée : permet d'afficher les requêtes sur la vue graphique et la vue textuelle
     *
     * @param tournee la liste de requêtes qui doit être affichée
     */
    public void afficherEtatTourneChargee(Tournee tournee) {

        supprimerPositionRequete();

        menuLateral.setMessageUtilisateur("Veuillez préparer la tournée pour visualiser l'itinéraire sur la carte.");

        //Configure les visibilités
        menuLateral.visibilitePannelImportation(true);
        menuLateral.visibiliteMenuPreparerTournee(true);
        menuLateral.visibiliteBoutonExporterFeuilleRoute(false);
        menuLateral.visibiliteBoutonAjouterRequete(false);
        menuLateral.visibiliteBoutonUndo(false);
        menuLateral.visibiliteBoutonRedo(false);
        menuLateral.visibiliteBoutonAnnulerAjoutRequete(false);
        //Affiche la liste des requetes
        menuLateral.afficherMenuRequete();

        // repaint la fenetre
        this.revalidate();
        this.repaint();
    }

    /**
     * Affichage déclenchée lorsque la tournée a été calculé : permet d'afficher la tournee calculée sur la vue graphique et la vue textuelle
     *
     * @param tournee la tournee calculée qui doit être affichée
     */
    public void afficherEtatTourneePreparee(Tournee tournee) {

        //On enleve les indications au dessus des adresses selectionnées
        supprimerPositionRequete();

        menuLateral.setMessageUtilisateur("Maintenant vous pouvez éditer votre tournée ou exporter la feuille de route.");

        //Configure les visibilités
        menuLateral.visibilitePannelImportation(true);
        menuLateral.visibiliteMenuPreparerTournee(false);
        menuLateral.visibiliteBoutonExporterFeuilleRoute(true);
        menuLateral.visibiliteBoutonAjouterRequete(true);
        menuLateral.visibiliteBoutonUndo(true);
        menuLateral.visibiliteBoutonRedo(true);
        menuLateral.visibiliteBoutonAnnulerAjoutRequete(false);

        menuLateral.authoriseCliquerBoutonUndo(authorisationCliquerBoutonUndo);
        menuLateral.authoriseCliquerBoutonRedo(authorisationCliquerBoutonRedo);
        //Affiche la tournee ordonnee
        menuLateral.retirerMenuRequete();
        menuLateral.afficherMenuEtapes();

        this.revalidate();
        this.repaint();
    }

    /**
     * Fait l'affichage des boutons et des panels utiles pour cet état
     */

    public void afficherEtatAjoutRequete() {

        supprimerPositionRequete();

        //Configure les visibilités
        menuLateral.visibilitePannelImportation(false);
        menuLateral.visibiliteMenuPreparerTournee(false);
        menuLateral.visibiliteBoutonExporterFeuilleRoute(false);
        menuLateral.visibiliteBoutonAjouterRequete(false);
        menuLateral.visibiliteBoutonUndo(false);
        menuLateral.visibiliteBoutonRedo(false);
        menuLateral.visibiliteBoutonAnnulerAjoutRequete(true);
        menuLateral.retirerMenuEtape();

        //menuLateral.retirerBoutonsMenu();
        menuLateral.setMessageUtilisateur("Ajouter une Etape de collecte: [Clique Gauche] sur une Adresse de la Carte " + "[Clique Droit] pour annuler");
        this.ecouteurSouris.setVueGraphique(cartePanel);
        this.revalidate();
        this.repaint();
    }

    /**
     * Fait l'affichage des boutons et des panels utiles pour cet état
     */

    public void afficherEtatAjoutRequete2() {
        popUpSaisieDuree = new PopUpSaisieDuree(policeTexte, ecouteurBoutons);
        popUpSaisieDuree.setPosition(cartePanel.getLargeur() / 2, cartePanel.getHauteur() / 2);
        menuLateral.setMessageUtilisateur("Entrer la durée de l'étape collecte et Valider");
        cartePanel.add(popUpSaisieDuree);
        this.revalidate();
        this.repaint();
    }

    /**
     * Fait l'affichage des boutons et des panels utiles pour cet état
     */

    public void afficherEtatAjoutRequete3() {
        menuLateral.setMessageUtilisateur("Selectionner l'étape qui précéde votre collecte: [Clique Gauche] sur une Etape de la Carte " + "[Clique Droit] pour annuler");
        cartePanel.remove(popUpSaisieDuree);
        this.revalidate();
        this.repaint();
    }

    /**
     * Fait l'affichage des boutons et des panels utiles pour cet état
     */

    public void afficherEtatAjoutRequete4() {
        menuLateral.setMessageUtilisateur("Ajouter une Etape de depot: [Clique Gauche] sur une Adresse de la Carte " + "[Clique Droit] pour annuler");
        this.revalidate();
        this.repaint();
    }

    /**
     * Fait l'affichage des boutons et des panels utiles pour cet état
     */

    public void afficherEtatAjoutRequete5() {
        menuLateral.setMessageUtilisateur("Entrer la durée de l'étape depot et Valider");
        cartePanel.add(popUpSaisieDuree);
        this.revalidate();
        this.repaint();
    }

    /**
     * Fait l'affichage des boutons et des panels utiles pour cet état
     */

    public void afficherEtatAjoutRequete6() {
        menuLateral.setMessageUtilisateur("Selectionner l'étape qui précéde votre depot: [Clique Gauche] sur une Etape de la Carte " + "[Clique Droit] pour annuler");
        cartePanel.remove(popUpSaisieDuree);
        this.revalidate();
        this.repaint();
    }

    /**
     * Affiche les etas détermines par le controleur
     */
    public void afficherEtat() {

        ecranAccueil = new EcranAccueil(this.getWidth(), this.getHeight(), policeSousTitre, policeTexte, this.ecouteurBoutons);
        this.add(ecranAccueil);

        // repaint la fenetre
        this.revalidate();
        this.repaint();
    }

    /**
     * getter sur cartePanel
     *
     * @return: le panel d'affichage de la carte
     */
    public CartePanel getCartePanel() {
        return cartePanel;
    }

    /**
     * Retire l'écran d'accueil de l'affichage
     */
    public void retirerEcranAccueil() {
        this.remove(ecranAccueil);
    }

    /**
     * Set l'autorisation de cliquer sur le bouton undo
     *
     * @param authorisationCliquerBoutonUndo
     */
    public void setAuthorisationCliquerBoutonUndo(boolean authorisationCliquerBoutonUndo) {
        this.authorisationCliquerBoutonUndo = authorisationCliquerBoutonUndo;
    }

    /**
     * Set l'autorisation de cliquer sur le bouton redo
     *
     * @param authorisationCliquerBoutonRedo
     */
    public void setAuthorisationCliquerBoutonRedo(boolean authorisationCliquerBoutonRedo) {
        this.authorisationCliquerBoutonRedo = authorisationCliquerBoutonRedo;
    }

    /**
     * Retourne le temps maximum de calcul entré par l'utilisateur
     *
     * @return le temps maximum de calcul entré par l'utilisateur
     * @throws ValeurNegativeException
     */
    public int obtenirTempsMaxCalcul() throws ValeurNegativeException {
        return menuLateral.obtenirTempsMaxCalcul();
    }

    /**
     * Getter sur la tournee
     *
     * @return la tournee
     */
    public Tournee getTournee() {
        return tournee;
    }

    /**
     * Ajoute l'icone au dessus des étapes sélectionnées
     *
     * @param collecte l'étape de collecte
     * @param depot    l'étape de dépot
     */
    public void indiquerPositionRequete(Etape collecte, Etape depot) {
        supprimerPositionRequete();
        cartePanel.indiquerPositionRequete(collecte, depot);
        menuLateral.indiquerPositionRequete(collecte, depot);
    }

    /**
     * Supprime les icones au dessus des étapes
     */
    public void supprimerPositionRequete() {
        if (cartePanel != null) {
            cartePanel.supprimerPositionRequete();
        }
        if (menuLateral != null) {
            menuLateral.supprimerPositionRequete();
        }
    }


}
