package Vue;

import javax.swing.*;
import java.awt.*;

public class MenuLateral extends JPanel {

    public MenuLateral(int largeurEcran, int hauteurEcran, Font policeTexte){

        int largeurPanelPrincipal = (int) (largeurEcran * 1/4);
        int valMarginBase = 5;
        int hauteurBouton = 50;

        // propriétés du panel principal
        this.setBounds(largeurEcran - largeurPanelPrincipal, 0, largeurPanelPrincipal, hauteurEcran);
        this.setLayout(null);

        // Création du panel contenant les boutons d'import
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

        // création du panel contenant les boutons undo / redo / Ajouter requête
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

        // création du panel contenant les étapes ou les requêtes
        int yDebutPanelConsultation = (int) (panelBoutonsE4.getY() + panelBoutonsE4.getHeight() + valMarginBase);
        int yFinPanelConsultation = (int) (this.getHeight() - hauteurBouton - 2 * valMarginBase);
        JPanel panelConsultation = new JPanel();
        panelConsultation.setBounds(valMarginBase, yDebutPanelConsultation, largeurPanelPrincipal-2*valMarginBase, yFinPanelConsultation - yDebutPanelConsultation );
        panelConsultation.setLayout(null);
        panelConsultation.setBackground(Color.RED);
        this.add(panelConsultation);

        // Création du panel contenant le bouton en bas du menu (Calculer itinéraire ou extraire feuille de route en fonction de l'etat)
        /*Bouton boutonCalculItineraire = new Bouton("Calculer itinéraire", policeTexte);
        boutonCalculItineraire.setBounds( valMarginBase, this.getHeight() - hauteurBouton - valMarginBase, this.getWidth() - 2*valMarginBase, hauteurBouton);
        this.add(boutonCalculItineraire);*/

        Bouton boutonExporterFeuilleRoute = new Bouton("Exporter feuille de route", policeTexte);
        boutonExporterFeuilleRoute.setBounds( valMarginBase, this.getHeight() - hauteurBouton - valMarginBase, this.getWidth() - 2*valMarginBase, hauteurBouton);
        this.add(boutonExporterFeuilleRoute);
    }
}
