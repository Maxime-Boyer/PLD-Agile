package Vue;

import Model.Requete;
import Model.Tournee;
import Model.Etape;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class MenuLateral extends JPanel {

    private int valMarginBase;
    private int hauteurBouton;
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

    public MenuLateral(int largeurFenetre, int hauteurEcran, Font policeTexte, Font policeTexteImportant, EcouteurBoutons ecouteurBoutons, EcouteurSurvol ecouteurSurvol){

        this.valMarginBase = 5;
        this.hauteurBouton = 50;
        this.policeTexte = policeTexte;
        this.policeTexteImportant = policeTexteImportant;
        this.ecouteurBoutons = ecouteurBoutons;
        this.ecouteurSurvol = ecouteurSurvol;

        // propriétés du panel principal
        this.setBounds(largeurFenetre - largeurFenetre * 1/4, 0, largeurFenetre * 1/4, hauteurEcran);
        this.setLayout(null);


        /************************************************************************************/
        /*                          Panel boutons d'import                                  */
        /************************************************************************************/

        panelImport = new JPanel();
        panelImport.setBounds(valMarginBase, valMarginBase, this.getWidth()-2*valMarginBase, hauteurBouton );
        panelImport.setLayout(null);
        this.add(panelImport);

        boutonImporterPlan = new Bouton(Fenetre.IMPORT_CARTE, policeTexte, ecouteurBoutons);
        boutonImporterPlan.setBounds( 0,0, panelImport.getWidth()/2 - valMarginBase/2, hauteurBouton);
        panelImport.add(boutonImporterPlan);

        boutonImporterTournee = new Bouton(Fenetre.IMPORT_TOURNEE, policeTexte, ecouteurBoutons);
        boutonImporterTournee.setBounds(panelImport.getWidth()/2 + valMarginBase/2,0, panelImport.getWidth()/2 - valMarginBase/2, hauteurBouton);
        panelImport.add(boutonImporterTournee);
    }

    public void afficherMenuRequete(Tournee tournee){

        //affichage du détaillé des requêtes
        this.panelInsideScrollPanel = new JPanel(null);
        BoxLayout boxlayout = new BoxLayout(panelInsideScrollPanel, BoxLayout.Y_AXIS);
        panelInsideScrollPanel.setLayout(boxlayout);
        RequetePanel[] listeRequetes = new RequetePanel[tournee.getListeRequetes().size()];
        int positionTop = 0;
        Etape collecte, depot;
        for (int i = 0; i < tournee.getListeRequetes().size(); i++) {
            collecte = tournee.getListeRequetes().get(i).getEtapeCollecte();
            depot = tournee.getListeRequetes().get(i).getEtapeDepot();

            listeRequetes[i] = new RequetePanel(collecte, depot, tournee.getListeRequetes().get(i).getCouleur(), this.getWidth()-2*valMarginBase - 8, valMarginBase, policeTexte, policeTexteImportant, ecouteurSurvol);
            panelInsideScrollPanel.add(listeRequetes[i]);
            panelInsideScrollPanel.add(Box.createRigidArea(new Dimension(0, 2*valMarginBase)));
        }

        this.scrollPanel = new JScrollPane(panelInsideScrollPanel);
        scrollPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        int yDebutPanelConsultation = (int) (panelImport.getY() + panelImport.getHeight() + 2 * valMarginBase +2);
        int yFinPanelConsultation = (int) (this.getHeight() - hauteurBouton - 4 * valMarginBase - 4);
        scrollPanel.setBounds(valMarginBase+2, yDebutPanelConsultation, this.getWidth()-2*valMarginBase - 4, yFinPanelConsultation - yDebutPanelConsultation );
        Border border = BorderFactory.createEmptyBorder( 0, 0, 0, 0 );
        scrollPanel.setViewportBorder( border );
        scrollPanel.setBorder(border);
        this.add(scrollPanel);

        //creation du bouton de calcul d'itineraire
        boutonPreparerTournee = new Bouton(Fenetre.PREPARER_TOURNEE, policeTexte, ecouteurBoutons);
        boutonPreparerTournee.addMouseListener(ecouteurSurvol);
        boutonPreparerTournee.setBounds( valMarginBase, this.getHeight() - hauteurBouton - valMarginBase, this.getWidth() - 2*valMarginBase, hauteurBouton);
        this.add(boutonPreparerTournee);
    }

    public void afficherMenuEtapes(Tournee tournee){

        // Undo, Redo, Ajouter Etape
        panelBoutonsE4 = new JPanel();
        panelBoutonsE4.setBounds(valMarginBase, panelImport.getY() + panelImport.getHeight() + valMarginBase, this.getWidth()-2*valMarginBase, hauteurBouton );
        panelBoutonsE4.setLayout(null);
        this.add(panelBoutonsE4);

        boutonUndo = new Bouton("<--", policeTexte, ecouteurBoutons);
        boutonUndo.setBounds( 0,0, panelBoutonsE4.getWidth()/4 - valMarginBase*3/4, 50);
        panelBoutonsE4.add(boutonUndo);

        boutonRedo = new Bouton("-->", policeTexte, ecouteurBoutons);
        boutonRedo.setBounds(boutonUndo.getX() + boutonUndo.getWidth() + valMarginBase,0, panelBoutonsE4.getWidth()/4 - valMarginBase*3/4 -1, hauteurBouton);
        panelBoutonsE4.add(boutonRedo);

        boutonAjouterEtape = new Bouton("Ajouter étape", policeTexte, ecouteurBoutons);
        boutonAjouterEtape.setBounds(boutonRedo.getX() + boutonRedo.getWidth() + valMarginBase,0, panelBoutonsE4.getWidth()/2 - valMarginBase/2, hauteurBouton);
        panelBoutonsE4.add(boutonAjouterEtape);

        // suppr ancien panel consultation et construiction du nouveau
        panelInsideScrollPanel.removeAll();
        int yDebutPanelConsultation = (int) (panelBoutonsE4.getY() + panelBoutonsE4.getHeight() + 2 * valMarginBase +2);
        int yFinPanelConsultation = (int) (this.getHeight() - hauteurBouton - 4 * valMarginBase - 4);
        scrollPanel.setBounds(valMarginBase+2, yDebutPanelConsultation, this.getWidth()-2*valMarginBase - 4, yFinPanelConsultation - yDebutPanelConsultation );

        //affichage du détaille des étapes
        EtapePanel[] listeEtapes = new EtapePanel[tournee.getListeChemins().size()];
        int positionTop = 0;
        Etape etapeFinChemin; //on base notre affichage sur l'étape de fin uniquement
        Etape etapeDepot, etapeCollecte;
        Requete requete = null;
        String heurePassage = "";
        System.out.println("-----------Affichage de " + tournee.getListeChemins().size() + "etapes");
        for (int i = 0; i < tournee.getListeChemins().size(); i++) {
            etapeFinChemin = tournee.getListeChemins().get(i).getEtapeArrivee();

            //on determine dans quelle requete se trouve l'étape
            for(int j = 0; j < tournee.getListeRequetes().size(); j++){
                etapeDepot = tournee.getListeRequetes().get(j).getEtapeDepot();
                etapeCollecte = tournee.getListeRequetes().get(j).getEtapeCollecte();

                if(etapeDepot.getIdAdresse().equals(etapeFinChemin.getIdAdresse()) || etapeCollecte.getIdAdresse().equals(etapeFinChemin.getIdAdresse())){
                    requete = tournee.getListeRequetes().get(j);
                    break;
                }
            }

            // affichage de l'etape
            listeEtapes[i] = new EtapePanel(etapeFinChemin, requete, panelInsideScrollPanel.getWidth(), valMarginBase, policeTexte, policeTexteImportant, ecouteurSurvol);
            panelInsideScrollPanel.add(listeEtapes[i]);
            panelInsideScrollPanel.add(Box.createRigidArea(new Dimension(0, 2*valMarginBase)));
        }

        // bouton Exporter feuille de route
        this.remove(boutonPreparerTournee);
        boutonExporterFeuilleRoute = new Bouton("Exporter feuille de route", policeTexte, ecouteurBoutons);
        boutonExporterFeuilleRoute.setBounds( valMarginBase, this.getHeight() - hauteurBouton - valMarginBase, this.getWidth() - 2*valMarginBase, hauteurBouton);
        this.add(boutonExporterFeuilleRoute);
    }

    //FIXME : ajouter if not null
    public void retirerMenuRequete() {
        this.remove(scrollPanel);
        this.remove(boutonPreparerTournee);
    }

    //FIXME : ajouter if not null
    public void retirerMenuEtape() {
        this.remove(panelBoutonsE4);
        //this.remove(boutonUndo);
        //this.remove(boutonRedo);
        //this.remove(boutonAjouterEtape);
        this.remove(scrollPanel);
        this.remove(boutonExporterFeuilleRoute);
    }
}
