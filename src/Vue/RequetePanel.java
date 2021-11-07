package Vue;

import Model.Etape;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;


public class RequetePanel extends JPanel {

    private Etape collecte;
    private Etape depot;

    /**
     * Panel permettant d'afficher la vue textuelle d'une requete
     * @param collecte: l'etape de collecte de la requete
     * @param depot: l'étape de depot de la requete
     * @param couleurBordure: couleur de requete (servira pour la bordure)
     * @param parentWidth: largeur du panel où sera affichee la requete
     * @param valMarginBase: valeur de l'ecart standard en vigueur dans l'application
     * @param policeTexte: la police a appliquer au texte de la requete
     * @param policeTexteImportant: la police a appliquer au texte a mettre en evidence dans la requete
     * @param ecouteurSurvol: l'ecouteur gerant les evenements de survol afin de pointer la requete sur la carte
     */
    public RequetePanel(Etape collecte, Etape depot, Color couleurBordure, int parentWidth, int valMarginBase, Font policeTexte, Font policeTexteImportant, EcouteurSurvol ecouteurSurvol){

        this.collecte = collecte;
        this.depot = depot;

        /************************************************************************************/
        /*                              Panel principal                                     */
        /************************************************************************************/
        BoxLayout boxlayout = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(boxlayout);
        this.setPreferredSize(new Dimension(parentWidth - 24, 170));
        this.addMouseListener(ecouteurSurvol);

        float teinteRouge = (float) couleurBordure.getRed() / (float) 255;
        float teinteVert = (float) couleurBordure.getGreen() / (float) 255;
        float teinteBleue = (float) couleurBordure.getBlue() / (float) 255;
        Color couleurFond = new Color(teinteRouge, teinteVert, teinteBleue, (float) 0.1);
        this.setBackground(couleurFond);
        this.setBorder(new LineBorder(couleurBordure, 2, true));

        JPanel panelInside = new JPanel();
        BoxLayout boxlayoutInside = new BoxLayout(panelInside, BoxLayout.Y_AXIS);
        panelInside.setLayout(boxlayoutInside);
        panelInside.setOpaque(false);
        panelInside.setBorder(new EmptyBorder(10, 10, 10, 10));

        /************************************************************************************/
        /*                               Label duree de collecte                            */
        /************************************************************************************/

        JTextArea labelTitreCollecte = new JTextArea("Collecte - " + String.valueOf(collecte.getDureeEtape()) + " sec");
        labelTitreCollecte.setSize(this.getWidth() - 4 * valMarginBase, 30);
        labelTitreCollecte.setFont(policeTexteImportant);
        labelTitreCollecte.setEditable(false);
        labelTitreCollecte.setLineWrap(true);
        labelTitreCollecte.setWrapStyleWord(true);
        labelTitreCollecte.setOpaque(false);
        panelInside.add(labelTitreCollecte);
        panelInside.add(Box.createRigidArea(new Dimension(0, valMarginBase/2)));

        /************************************************************************************/
        /*                            Label adresse de collecte                             */
        /************************************************************************************/
        JTextArea labelAdresseCollecte = new JTextArea(collecte.getNomAdresse());
        labelAdresseCollecte.setSize(this.getWidth() - 4 * valMarginBase, 45);
        labelAdresseCollecte.setFont(policeTexte);
        labelAdresseCollecte.setEditable(false);
        labelAdresseCollecte.setLineWrap(true);
        labelAdresseCollecte.setWrapStyleWord(true);
        labelAdresseCollecte.setOpaque(false);
        panelInside.add(labelAdresseCollecte);
        panelInside.add(Box.createRigidArea(new Dimension(0, 2*valMarginBase)));

        /************************************************************************************/
        /*                                 Label duree depot                                */
        /************************************************************************************/
        JTextArea labelTitreDepot = new JTextArea("Dépôt - " + String.valueOf(depot.getDureeEtape()) + " sec");
        labelTitreDepot.setSize(this.getWidth() - 4 * valMarginBase, 30);
        labelTitreDepot.setFont(policeTexteImportant);
        labelTitreDepot.setEditable(false);
        labelTitreDepot.setLineWrap(true);
        labelTitreDepot.setWrapStyleWord(true);
        labelTitreDepot.setOpaque(false);
        panelInside.add(labelTitreDepot);
        panelInside.add(Box.createRigidArea(new Dimension(0, valMarginBase/2)));

        /************************************************************************************/
        /*                                Label adresse de depot                            */
        /************************************************************************************/
        JTextArea labelAdresseDepot = new JTextArea(depot.getNomAdresse());
        labelAdresseDepot.setSize(this.getWidth() - 4 * valMarginBase, 45);
        labelAdresseDepot.setFont(policeTexte);
        labelAdresseDepot.setEditable(false);
        labelAdresseDepot.setLineWrap(true);
        labelAdresseDepot.setWrapStyleWord(true);
        labelAdresseDepot.setOpaque(false);
        panelInside.add(labelAdresseDepot);

        this.add(panelInside);
    }

    /**
     * Geteur
     * @return etape de collecte
     */
    public Etape getCollecte() {
        return collecte;
    }

    /**
     * Geteur
     * @return etape de depot
     */
    public Etape getDepot() {
        return depot;
    }
}
