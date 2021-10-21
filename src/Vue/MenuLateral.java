package Vue;

import Model.Tournee;
import Model.Etape;

import javax.swing.*;
import java.awt.*;

public class MenuLateral extends JPanel {

    private int valMarginBase;
    private int hauteurBouton;
    private Font policeTexte;
    private Font policeTexteImportant;
    private EcouteurBoutons ecouteurBoutons;

    private JPanel panelImport;
    private Bouton boutonImporterPlan;
    private Bouton boutonImporterTournee;
    private JPanel panelBoutonsE4;
    private JPanel panelConsultation;
    private Bouton boutonPreparerTournee;
    private Bouton boutonUndo;
    private Bouton boutonRedo;
    private Bouton boutonAjouterEtape;
    private Bouton boutonExporterFeuilleRoute;

    public MenuLateral(int largeurFenetre, int hauteurEcran, Font policeTexte, Font policeTexteImportant, EcouteurBoutons ecouteurBoutons){

        this.valMarginBase = 5;
        this.hauteurBouton = 50;
        this.policeTexte = policeTexte;
        this.policeTexteImportant = policeTexteImportant;
        this.ecouteurBoutons = ecouteurBoutons;

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

        //creation du pannel permettant d'afficher le détail des requêtes ou des étapes
        int yDebutPanelConsultation = (int) (panelImport.getY() + panelImport.getHeight() + 2 * valMarginBase +2);
        int yFinPanelConsultation = (int) (this.getHeight() - hauteurBouton - 4 * valMarginBase - 4);
        panelConsultation = new JPanel();
        panelConsultation.setBounds(valMarginBase+2, yDebutPanelConsultation, this.getWidth()-2*valMarginBase - 4, yFinPanelConsultation - yDebutPanelConsultation );
        panelConsultation.setLayout(null);
        this.add(panelConsultation);

        //affichage du détaillé des requêtes
        RequetePanel[] listeRequetes = new RequetePanel[10];
        int positionTop = 0;
        Etape collecte, depot;
        for (int i = 0; i < tournee.getListeRequetes().size(); i++) {
            collecte = tournee.getListeRequetes().get(i).getEtapeCollecte();
            depot = tournee.getListeRequetes().get(i).getEtapeDepot();

            // TODO: remplacer les ID par les adresses
            listeRequetes[i] = new RequetePanel(collecte.getDureeEtape(), depot.getDureeEtape(), "Adresse ID: " + collecte.getIdAdresse().toString(), "Adresse ID: " + depot.getIdAdresse().toString(), panelConsultation.getWidth(), valMarginBase, policeTexte, policeTexteImportant);

            if(i > 0)
                positionTop = listeRequetes[i-1].getY() + listeRequetes[i-1].getHeight() + 2*valMarginBase;

            listeRequetes[i].setBounds(0, positionTop, panelConsultation.getWidth(), 130);
            panelConsultation.add(listeRequetes[i]);
        }

        //creation du bouton de calcul d'itineraire
        boutonPreparerTournee = new Bouton(Fenetre.PREPARER_TOURNEE, policeTexte, ecouteurBoutons);
        boutonPreparerTournee.setBounds( valMarginBase, this.getHeight() - hauteurBouton - valMarginBase, this.getWidth() - 2*valMarginBase, hauteurBouton);
        this.add(boutonPreparerTournee);
    }

    public void afficherMenuEtapes(){

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
        System.out.println("MENULATERAL : afficherMenuEtape");
        panelConsultation.removeAll();
        int yDebutPanelConsultation = (int) (panelBoutonsE4.getY() + panelBoutonsE4.getHeight() + 2 * valMarginBase +2);
        int yFinPanelConsultation = (int) (this.getHeight() - hauteurBouton - 4 * valMarginBase - 4);
        panelConsultation.setBounds(valMarginBase+2, yDebutPanelConsultation, this.getWidth()-2*valMarginBase - 4, yFinPanelConsultation - yDebutPanelConsultation );

        //affichage du détaille des étapes
        EtapePanel[] listeEtapes = new EtapePanel[10];
        int positionTop = 0;
        for (int i = 0; i < 10; i++) {
            listeEtapes[i] = new EtapePanel("8h18", false, 12, "20 Av. Albert Einstein", panelConsultation.getWidth(), valMarginBase, policeTexte, policeTexteImportant);

            if(i > 0)
                positionTop = listeEtapes[i-1].getY() + listeEtapes[i-1].getHeight() + 2*valMarginBase;

            listeEtapes[i].setBounds(0, positionTop, panelConsultation.getWidth(), 110);
            panelConsultation.add(listeEtapes[i]);
        }

        // bouton Exporter feuille de route
        this.remove(boutonPreparerTournee);
        boutonExporterFeuilleRoute = new Bouton("Exporter feuille de route", policeTexte, ecouteurBoutons);
        boutonExporterFeuilleRoute.setBounds( valMarginBase, this.getHeight() - hauteurBouton - valMarginBase, this.getWidth() - 2*valMarginBase, hauteurBouton);
        this.add(boutonExporterFeuilleRoute);
    }

    //FIXME : ajouter if not null
    public void retirerMenuRequete() {
        this.remove(panelConsultation);
        this.remove(boutonPreparerTournee);
    }

    //FIXME : ajouter if not null
    public void retirerMenuEtape() {
        this.remove(panelBoutonsE4);
        this.remove(boutonUndo);
        this.remove(boutonRedo);
        this.remove(boutonAjouterEtape);
        this.remove(panelConsultation);
        this.remove(boutonExporterFeuilleRoute);
    }
}
