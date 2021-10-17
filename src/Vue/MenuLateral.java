package Vue;

import javax.swing.*;
import java.awt.*;

public class MenuLateral extends JPanel {

    public MenuLateral(int largeurEcran, int hauteurEcran, Font policeTexte, Font policeTexteImportant){

        int largeurPanelPrincipal = (int) (largeurEcran * 1/4);
        int valMarginBase = 5;
        int hauteurBouton = 50;

        // propriétés du panel principal
        this.setBounds(largeurEcran - largeurPanelPrincipal, 0, largeurPanelPrincipal, hauteurEcran);
        this.setLayout(null);


        /************************************************************************************/
        /*                          Panel boutons d'import                                  */
        /************************************************************************************/

        JPanel panelImport = new JPanel();
        panelImport.setBounds(valMarginBase, valMarginBase, largeurPanelPrincipal-2*valMarginBase, hauteurBouton );
        panelImport.setLayout(null);
        this.add(panelImport);

        Bouton boutonImporterPlan = new Bouton("Importer plan", policeTexte);
        boutonImporterPlan.setBounds( 0,0, panelImport.getWidth()/2 - valMarginBase/2, hauteurBouton);
        panelImport.add(boutonImporterPlan);

        Bouton boutonImporterTournee = new Bouton("Importer tournée", policeTexte);
        boutonImporterTournee.setBounds(panelImport.getWidth()/2 + valMarginBase/2,0, panelImport.getWidth()/2 - valMarginBase/2, hauteurBouton);
        panelImport.add(boutonImporterTournee);


        /************************************************************************************/
        /*                      Panel undo / redo / Ajouter requête                         */
        /************************************************************************************/

        JPanel panelBoutonsE4 = new JPanel();
        panelBoutonsE4.setBounds(valMarginBase, panelImport.getY() + panelImport.getHeight() + valMarginBase, largeurPanelPrincipal-2*valMarginBase, hauteurBouton );
        panelBoutonsE4.setLayout(null);
        this.add(panelBoutonsE4);

        Bouton boutonUndo = new Bouton("<--", policeTexte);
        boutonUndo.setBounds( 0,0, panelBoutonsE4.getWidth()/4 - valMarginBase*3/4, 50);
        panelBoutonsE4.add(boutonUndo);

        Bouton boutonRedo = new Bouton("-->", policeTexte);
        boutonRedo.setBounds(boutonUndo.getX() + boutonUndo.getWidth() + valMarginBase,0, panelBoutonsE4.getWidth()/4 - valMarginBase*3/4 -1, hauteurBouton);
        panelBoutonsE4.add(boutonRedo);

        Bouton boutonAjouterEtape = new Bouton("Ajouter étape", policeTexte);
        boutonAjouterEtape.setBounds(boutonRedo.getX() + boutonRedo.getWidth() + valMarginBase,0, panelBoutonsE4.getWidth()/2 - valMarginBase/2, hauteurBouton);
        panelBoutonsE4.add(boutonAjouterEtape);


        /************************************************************************************/
        /*                      Panel liste étapes ou liste requêtes                        */
        /************************************************************************************/

        int yDebutPanelConsultation = (int) (panelBoutonsE4.getY() + panelBoutonsE4.getHeight() + 2 * valMarginBase +2);
        int yFinPanelConsultation = (int) (this.getHeight() - hauteurBouton - 4 * valMarginBase - 4);
        JPanel panelConsultation = new JPanel();
        panelConsultation.setBounds(valMarginBase+2, yDebutPanelConsultation, largeurPanelPrincipal-2*valMarginBase - 4, yFinPanelConsultation - yDebutPanelConsultation );
        panelConsultation.setLayout(null);
        this.add(panelConsultation);

        // TO REMOVE, action du controleur ici
        Requete[] listeRequetes = new Requete[10];
        var positionTop = 0;
        for (int i = 0; i < 10; i++) {
            listeRequetes[i] = new Requete(13, 10, "37 rue du lac", "20 Av. Albert Einstein", panelConsultation.getWidth(), valMarginBase, policeTexte, policeTexteImportant);

            if(i > 0)
                positionTop = listeRequetes[i-1].getY() + listeRequetes[i-1].getHeight() + 2*valMarginBase;

            listeRequetes[i].setBounds(0, positionTop, panelConsultation.getWidth(), 130);
            panelConsultation.add(listeRequetes[i]);
        }

        /************************************************************************************/
        /*                         Panel bouton en bas du menu                              */
        /************************************************************************************/

        /* Bouton boutonCalculItineraire = new Bouton("Calculer itinéraire", policeTexte);
        boutonCalculItineraire.setBounds( valMarginBase, this.getHeight() - hauteurBouton - valMarginBase, this.getWidth() - 2*valMarginBase, hauteurBouton);
        this.add(boutonCalculItineraire); */

        Bouton boutonExporterFeuilleRoute = new Bouton("Exporter feuille de route", policeTexte);
        boutonExporterFeuilleRoute.setBounds( valMarginBase, this.getHeight() - hauteurBouton - valMarginBase, this.getWidth() - 2*valMarginBase, hauteurBouton);
        this.add(boutonExporterFeuilleRoute);
    }
}
