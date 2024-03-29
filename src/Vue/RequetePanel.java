package Vue;

import Model.Etape;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;


public class RequetePanel extends JPanel {

    private Etape collecte;
    private Etape depot;
    private Color couleurFond;
    private Color couleurBordure;

    /**
     * Panel permettant d'afficher la vue textuelle d'une requete
     *
     * @param collecte:             l'etape de collecte de la requete
     * @param depot:                l'étape de depot de la requete
     * @param couleurBordure:       couleur de requete (servira pour la bordure)
     * @param parentWidth:          largeur du panel où sera affichee la requete
     * @param valMarginBase:        valeur de l'ecart standard en vigueur dans l'application
     * @param policeTexte:          la police a appliquer au texte de la requete
     * @param policeTexteImportant: la police a appliquer au texte a mettre en evidence dans la requete
     * @param ecouteurSurvol:       l'ecouteur gerant les evenements de survol afin de pointer la requete sur la carte
     */
    public RequetePanel(Etape collecte, Etape depot, Color couleurBordure, int parentWidth, int valMarginBase, Font policeTexte, Font policeTexteImportant, EcouteurSurvol ecouteurSurvol) {

        this.collecte = collecte;
        this.depot = depot;

        /************************************************************************************/
        /*                              Panel principal                                     */
        /************************************************************************************/
        BoxLayout boxlayout = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(boxlayout);
        this.setPreferredSize(new Dimension(parentWidth - 24, 170));
        this.addMouseListener(ecouteurSurvol);

        double mult = 0.1;
        double plus = 255 * 0.9;
        float teinteRouge = ((float) ((couleurBordure.getRed()) * mult + plus) / (float) 255) > 1 ? 1 : ((float) ((couleurBordure.getRed()) * mult + plus) / (float) 255);
        float teinteVert = ((float) ((couleurBordure.getGreen()) * mult + plus) / (float) 255) > 1 ? 1 : ((float) ((couleurBordure.getGreen()) * mult + plus) / (float) 255);
        float teinteBleue = ((float) ((couleurBordure.getBlue()) * mult + plus) / (float) 255) > 1 ? 1 : ((float) ((couleurBordure.getBlue()) * mult + plus) / (float) 255);
        Color couleurFond = new Color(teinteRouge, teinteVert, teinteBleue);
        this.couleurFond = couleurFond;
        this.setBackground(couleurFond);
        this.setOpaque(true);
        this.setBorder(new LineBorder(couleurBordure, 2, true));
        this.couleurBordure = couleurBordure;

        JPanel panelInside = new JPanel();
        BoxLayout boxlayoutInside = new BoxLayout(panelInside, BoxLayout.Y_AXIS);
        panelInside.setLayout(boxlayoutInside);
        panelInside.setOpaque(false);
        panelInside.setBackground(couleurFond);
        panelInside.setBorder(new EmptyBorder(10, 10, 10, 10));
        panelInside.addMouseListener(ecouteurSurvol);

        /************************************************************************************/
        /*                               Label duree de collecte                            */
        /************************************************************************************/

        String dureeCollecte = String.valueOf(collecte.getDureeEtape() / 60) + "min ";

        if (collecte.getDureeEtape() % 60 > 0) {
            if (collecte.getDureeEtape() % 60 > 9) {
                dureeCollecte += "0" + String.valueOf(collecte.getDureeEtape() % 60) + "sec";
            } else {
                dureeCollecte += String.valueOf(collecte.getDureeEtape() % 60) + "sec";
            }
        }

        JTextArea labelTitreCollecte = new JTextArea("Collecte - " + dureeCollecte);
        labelTitreCollecte.setSize(this.getWidth() - 4 * valMarginBase, 30);
        labelTitreCollecte.setFont(policeTexteImportant);
        labelTitreCollecte.setEditable(false);
        labelTitreCollecte.setLineWrap(true);
        labelTitreCollecte.setWrapStyleWord(true);
        labelTitreCollecte.setOpaque(false);
        labelTitreCollecte.addMouseListener(ecouteurSurvol);
        panelInside.add(labelTitreCollecte);
        panelInside.add(Box.createRigidArea(new Dimension(0, valMarginBase / 2)));

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
        labelAdresseCollecte.addMouseListener(ecouteurSurvol);
        panelInside.add(labelAdresseCollecte);
        panelInside.add(Box.createRigidArea(new Dimension(0, 2 * valMarginBase)));

        /************************************************************************************/
        /*                                 Label duree depot                                */
        /************************************************************************************/
        String dureeDepot = String.valueOf(depot.getDureeEtape() / 60) + "min ";

        if (depot.getDureeEtape() % 60 > 0) {
            dureeDepot += String.valueOf(depot.getDureeEtape() % 60) + "sec";
        }

        JTextArea labelTitreDepot = new JTextArea("Dépôt - " + dureeDepot);
        labelTitreDepot.setSize(this.getWidth() - 4 * valMarginBase, 30);
        labelTitreDepot.setFont(policeTexteImportant);
        labelTitreDepot.setEditable(false);
        labelTitreDepot.setLineWrap(true);
        labelTitreDepot.setWrapStyleWord(true);
        labelTitreDepot.setOpaque(false);
        labelTitreDepot.addMouseListener(ecouteurSurvol);
        panelInside.add(labelTitreDepot);
        panelInside.add(Box.createRigidArea(new Dimension(0, valMarginBase / 2)));

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
        labelAdresseDepot.addMouseListener(ecouteurSurvol);
        panelInside.add(labelAdresseDepot);

        this.add(panelInside);
    }

    /**
     * Paint component du requete panel
     * @param g, le graphique
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(couleurFond);

    }

    /**
     * Getter de la collecte
     * @return etape de collecte
     */
    public Etape getCollecte() {
        return collecte;
    }

    /**
     * Getter du depot
     * @return etape de depot
     */
    public Etape getDepot() {
        return depot;
    }

    /**
     * Getter de la couleur de la bordure du panel
     * @return la couleur de la bordure du panel
     */
    public Color getCouleurBordure() {
        return couleurBordure;
    }
}
