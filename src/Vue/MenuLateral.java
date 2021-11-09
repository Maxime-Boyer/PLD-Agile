package Vue;

import Model.Carte;
import Model.Requete;
import Model.Tournee;
import Model.Etape;
import Observer.Observer;
import Observer.Observable;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class MenuLateral extends JPanel implements Observer {

    private Font policeTexte;
    private Font policeTexteImportant;
    private EcouteurBoutons ecouteurBoutons;
    private EcouteurSurvol ecouteurSurvol;

    private JPanel panelImport;
    private Bouton boutonImporterPlan;
    private Bouton boutonImporterTournee;
    private JPanel panelBoutonsE4;
    private JScrollPane scrollPanel;
    private JPanel panelInsideScrollPanel;
    private Bouton boutonPreparerTournee;
    private Bouton boutonUndo;
    private Bouton boutonRedo;
    private Bouton boutonAjouterEtape;
    private Bouton boutonExporterFeuilleRoute;
    private JTextArea messageUtilisateur;

    private Tournee tournee;

    /**
     * Constructeur du panel de manu associé a la carte
     * @param largeurFenetre: largeur de la fenetre
     * @param hauteurEcran: hauteur de la fenetre
     * @param policeTexte: la police a appliquer au texte du menu
     * @param policeTexteImportant: la police a appliquer au texte du menu a mettre en evidence
     * @param ecouteurBoutons: l'ecouteur gerant les clics des boutons
     * @param ecouteurSurvol: l'ecouteur gerant les survols de la souris
     */
    public MenuLateral(Tournee tournee, int largeurFenetre, int hauteurEcran, Font policeTexte, Font policeTexteImportant, EcouteurBoutons ecouteurBoutons, EcouteurSurvol ecouteurSurvol){

        tournee.addObserver(this); // this observe la carte
        this.tournee = tournee;

        this.policeTexte = policeTexte;
        this.policeTexteImportant = policeTexteImportant;
        this.ecouteurBoutons = ecouteurBoutons;
        this.ecouteurSurvol = ecouteurSurvol;

        // propriétés du panel principal
        this.setBounds(largeurFenetre * 3/4, 0, largeurFenetre/4, hauteurEcran);
        this.setLayout(null);

        messageUtilisateur = new JTextArea();
        messageUtilisateur.setFont(policeTexte);
        messageUtilisateur.setEditable(false);
        messageUtilisateur.setLineWrap(true);
        messageUtilisateur.setWrapStyleWord(true);
        messageUtilisateur.setOpaque(false);
        messageUtilisateur.setBounds(2*Fenetre.valMarginBase, Fenetre.valMarginBase, this.getWidth()-4*Fenetre.valMarginBase, 35 );
        this.add(messageUtilisateur);

        /************************************************************************************/
        /*                          Panel boutons d'import                                  */
        /************************************************************************************/
        creerMenuImportation();
        creerBoutonPreparerTournee();
        creerMenuRequete();
        cacherMenuRequete();

    }

    /**
     * Affichage de la vue textuelle de la tournee triee
     * @param tournee: la tournee a afficher
     */
    public void afficherMenuEtapes(Tournee tournee) {

        // Undo, Redo, Ajouter Etape
        panelBoutonsE4 = new JPanel();
        panelBoutonsE4.setBounds(Fenetre.valMarginBase, panelImport.getY() + panelImport.getHeight() + Fenetre.valMarginBase, this.getWidth() - 2 * Fenetre.valMarginBase, Fenetre.hauteurBouton);
        panelBoutonsE4.setLayout(null);
        this.add(panelBoutonsE4);

        boutonUndo = new Bouton("<--", policeTexte, ecouteurBoutons);
        boutonUndo.setBounds(0, 0, panelBoutonsE4.getWidth() / 4 - Fenetre.valMarginBase * 3 / 4, 50);
        panelBoutonsE4.add(boutonUndo);

        boutonRedo = new Bouton("-->", policeTexte, ecouteurBoutons);
        boutonRedo.setBounds(boutonUndo.getX() + boutonUndo.getWidth() + Fenetre.valMarginBase, 0, panelBoutonsE4.getWidth() / 4 - Fenetre.valMarginBase * 3 / 4 - 1, Fenetre.hauteurBouton);
        panelBoutonsE4.add(boutonRedo);

        boutonAjouterEtape = new Bouton(Fenetre.AJOUT_REQUETE, policeTexte, ecouteurBoutons);
        boutonAjouterEtape.setBounds(boutonRedo.getX() + boutonRedo.getWidth() + Fenetre.valMarginBase, 0, panelBoutonsE4.getWidth() / 2 - Fenetre.valMarginBase / 2, Fenetre.hauteurBouton);
        panelBoutonsE4.add(boutonAjouterEtape);

        // suppr ancien panel consultation et construiction du nouveau
        panelInsideScrollPanel.removeAll();
        int yDebutPanelConsultation = (int) (panelBoutonsE4.getY() + panelBoutonsE4.getHeight() + 2 * Fenetre.valMarginBase + 2);
        int yFinPanelConsultation = (int) (this.getHeight() - Fenetre.hauteurBouton - 4 * Fenetre.valMarginBase - 4);
        scrollPanel.setBounds(Fenetre.valMarginBase + 2, yDebutPanelConsultation, this.getWidth() - 2 * Fenetre.valMarginBase - 4, yFinPanelConsultation - yDebutPanelConsultation);

        //affichage du détaille des étapes
        EtapePanel[] listeEtapes = new EtapePanel[tournee.getListeChemins().size()];
        int positionTop = 0;
        Etape etapeFinChemin; //on base notre affichage sur l'étape de fin uniquement
        Etape etapeDepot, etapeCollecte;
        Requete requete = null;
        String heurePassage = "";

        for (int i = 0; i < tournee.getListeChemins().size(); i++) {
            etapeFinChemin = tournee.getListeChemins().get(i).getEtapeArrivee();

            //on determine dans quelle requete se trouve l'étape
            for (int j = 0; j < tournee.getListeRequetes().size(); j++) {
                etapeDepot = tournee.getListeRequetes().get(j).getEtapeDepot();
                etapeCollecte = tournee.getListeRequetes().get(j).getEtapeCollecte();

                if (etapeDepot.getIdAdresse().equals(etapeFinChemin.getIdAdresse()) || etapeCollecte.getIdAdresse().equals(etapeFinChemin.getIdAdresse())) {
                    requete = tournee.getListeRequetes().get(j);
                    break;
                }
            }

            // affichage de l'etape
            listeEtapes[i] = new EtapePanel(etapeFinChemin, requete, panelInsideScrollPanel.getWidth(), Fenetre.valMarginBase, policeTexte, policeTexteImportant, ecouteurSurvol);
            panelInsideScrollPanel.add(listeEtapes[i]);
            panelInsideScrollPanel.add(Box.createRigidArea(new Dimension(0, 2 * Fenetre.valMarginBase)));
        }
        this.add(scrollPanel);

        // bouton Exporter feuille de route
        this.remove(boutonPreparerTournee);
        boutonExporterFeuilleRoute = new Bouton("Exporter feuille de route", policeTexte, ecouteurBoutons);
        boutonExporterFeuilleRoute.setBounds(Fenetre.valMarginBase, this.getHeight() - Fenetre.hauteurBouton - Fenetre.valMarginBase, this.getWidth() - 2 * Fenetre.valMarginBase, Fenetre.hauteurBouton);
        this.add(boutonExporterFeuilleRoute);
    }

    /**
     * seteur
     * @param texte: texte permettant de guider l'utilisateur
     */
    public void setMessageUtilisateur(String texte){
        messageUtilisateur.setText(texte);
    }

    /**
     * Permet de retirer l'affichage textuel de la requete non triee
     */
    public void retirerMenuRequete() {
        if (scrollPanel != null)
            this.remove(scrollPanel);
        if (boutonPreparerTournee != null)
            this.remove(boutonPreparerTournee);
    }

    /**
     * Permet de retirer l'affichage textuel de la requete triee
     */
    public void retirerMenuEtape() {
        if (panelBoutonsE4 != null)
            this.remove(panelBoutonsE4);
        //this.remove(boutonUndo);
        //this.remove(boutonRedo);
        //this.remove(boutonAjouterEtape);
        if (scrollPanel != null)
            this.remove(scrollPanel);
        if (boutonExporterFeuilleRoute != null)
            this.remove(boutonExporterFeuilleRoute);
    }

    /**
     * Methode appelée par les objets qui sont observés par cette fenêtre à chaque fois qu'ils sont mofifiés.
     */
    @Override
    public void update(Observable observed, Object arg) {
        System.out.println("..... update MenuLateral");
        if (arg != null){ // arg est soit une carte, soit une tournée qui a été mise à jour
            viderMenuRequete();
            retirerMenuEtape();
            //Met à jour la tournee
            if (arg instanceof Tournee) {
                tournee = (Tournee) arg;
                if (tournee.getTourneeEstChargee()){
                    //Si la tournee est chargee mais pas ordonnée, affiche la liste des requêtes
                    if (!tournee.getTourneeEstOrdonee()){
                        mettreAJourMenuRequete(tournee);
                    }
                    //Si la tournee est chargee et ordonnée, affiche la liste des étapes
                    else {
                        afficherMenuEtapes(tournee);
                        afficherMenuImportation();
                    }
                }
            }
        }
        revalidate();
        repaint();
    }

    /**
     * Permet de retirer l'affichage textuel de tous les boutons
     */
    public void retirerBoutonsMenu() {
        if(boutonImporterTournee != null)
        //boutonPreparerTournee.setVisible(false);
        if(boutonPreparerTournee != null)
            this.remove(boutonPreparerTournee);
        this.remove(panelBoutonsE4);
        //panelBoutonsE4.setVisible(false);
        //panelImport.setVisible(false);
        this.remove(panelImport);
        this.remove(boutonAjouterEtape);
        //boutonAjouterEtape.setVisible(false);
        this.remove(scrollPanel);
        //scrollPanel.setVisible(false);
        this.remove(boutonExporterFeuilleRoute);
        //boutonExporterFeuilleRoute.setVisible(false);
        this.remove(boutonImporterPlan);
        //boutonImporterPlan.setVisible(false);
        //boutonImporterTournee.setVisible(false);

    }

    public void creerMenuImportation(){
        panelImport = new JPanel();
        panelImport.setBounds(Fenetre.valMarginBase, messageUtilisateur.getY()+messageUtilisateur.getHeight()+Fenetre.valMarginBase, this.getWidth()-2*Fenetre.valMarginBase, Fenetre.hauteurBouton );
        panelImport.setLayout(null);
        this.add(panelImport);

        boutonImporterPlan = new Bouton(Fenetre.IMPORT_CARTE, policeTexte, ecouteurBoutons);
        boutonImporterPlan.setBounds( 0,0, panelImport.getWidth()/2 - Fenetre.valMarginBase/2, Fenetre.hauteurBouton);
        panelImport.add(boutonImporterPlan);

        boutonImporterTournee = new Bouton(Fenetre.IMPORT_TOURNEE, policeTexte, ecouteurBoutons);
        boutonImporterTournee.setBounds(panelImport.getWidth()/2 + Fenetre.valMarginBase/2,0, panelImport.getWidth()/2 - Fenetre.valMarginBase/2, Fenetre.hauteurBouton);
        panelImport.add(boutonImporterTournee);
    }

    public void afficherMenuImportation() {
        if(boutonImporterTournee != null)
            boutonImporterTournee.setVisible(true);
        if(boutonImporterPlan != null)
            boutonImporterPlan.setVisible(true);
        if(panelImport != null)
            panelImport.setVisible(true);
    }

    /**
     * Permet de retirer l'affichage textuel de tous les boutons
     */
    public void cacherMenuImportation() {
        if(boutonImporterTournee != null)
            boutonImporterTournee.setVisible(false);
        if(boutonPreparerTournee != null)
            boutonPreparerTournee.setVisible(false);
        if(panelImport != null)
            panelImport.setVisible(false);
    }

    /**
     * Affichage de la vue textuelle de la tournee non triee
     */
    public void creerMenuRequete(){

        //affichage du détaillé des requêtes
        this.panelInsideScrollPanel = new JPanel(null);
        //panelInsideScrollPanel.setOpaque(true);


    }

    public void viderMenuRequete() {
        panelInsideScrollPanel.removeAll();
    }

    public void mettreAJourMenuRequete(Tournee tournee) {
        BoxLayout boxlayout = new BoxLayout(this.panelInsideScrollPanel, BoxLayout.Y_AXIS);
        this.panelInsideScrollPanel.setLayout(boxlayout);

        RequetePanel[] listeRequetes = new RequetePanel[tournee.getListeRequetes().size()];
        int positionTop = 0;
        Etape collecte, depot;
        for (int i = 0; i < tournee.getListeRequetes().size(); i++) {
            collecte = tournee.getListeRequetes().get(i).getEtapeCollecte();
            depot = tournee.getListeRequetes().get(i).getEtapeDepot();

            listeRequetes[i] = new RequetePanel(collecte, depot, tournee.getListeRequetes().get(i).getCouleur(), this.getWidth()-2*Fenetre.valMarginBase - 8, Fenetre.valMarginBase, policeTexte, policeTexteImportant, ecouteurSurvol);
            panelInsideScrollPanel.add(listeRequetes[i]);
            panelInsideScrollPanel.add(Box.createRigidArea(new Dimension(0, 2*Fenetre.valMarginBase)));
        }

        this.scrollPanel = new JScrollPane(panelInsideScrollPanel);

        //repaint au scroll
        scrollPanel.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);

        scrollPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        int yDebutPanelConsultation = (int) (panelImport.getY() + panelImport.getHeight() + 2 * Fenetre.valMarginBase +2);
        int yFinPanelConsultation = (int) (this.getHeight() - Fenetre.hauteurBouton - 4 * Fenetre.valMarginBase - 4);
        scrollPanel.setBounds(Fenetre.valMarginBase+2, yDebutPanelConsultation, this.getWidth()-2*Fenetre.valMarginBase - 4, yFinPanelConsultation - yDebutPanelConsultation );
        Border border = BorderFactory.createEmptyBorder( 0, 0, 0, 0 );
        scrollPanel.setViewportBorder( border );
        scrollPanel.setBorder(border);
        this.add(scrollPanel);
    }

    public void creerBoutonPreparerTournee() {
        //creation du bouton de calcul d'itineraire
        boutonPreparerTournee = new Bouton(Fenetre.PREPARER_TOURNEE, policeTexte, ecouteurBoutons);
        boutonPreparerTournee.addMouseListener(ecouteurSurvol);
        boutonPreparerTournee.setBounds( Fenetre.valMarginBase, this.getHeight() - Fenetre.hauteurBouton - Fenetre.valMarginBase, this.getWidth() - 2*Fenetre.valMarginBase, Fenetre.hauteurBouton);
        this.add(boutonPreparerTournee);
    }

    public void cacherMenuRequete() {

        if(panelInsideScrollPanel != null)
            panelInsideScrollPanel.setVisible(false);

        if(scrollPanel != null)
            scrollPanel.setVisible(false);
    }

    public void cacherBoutonPreparerTournee(){
        if(boutonPreparerTournee != null){
            boutonPreparerTournee.setVisible(false);
        }
    }

    public void afficherMenuRequete() {

        if(panelInsideScrollPanel != null)
            panelInsideScrollPanel.setVisible(true);

        if(scrollPanel != null)
            scrollPanel.setVisible(true);
    }

    public void afficherBoutonPreparerTournee(){
        if(boutonPreparerTournee != null){
            boutonPreparerTournee.setVisible(true);
        }
    }
}
