package Vue;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;


public class RequetePanel extends JPanel {

    public RequetePanel(int dureeCollecte, int dureeDepot, String adresseCollecte, String adresseDepot, Color couleurBordure, int parentWidth, int valMarginBase, Font policeTexte, Font policeTexteImportant){

        /************************************************************************************/
        /*                              Panel principal                                     */
        /************************************************************************************/
        BoxLayout boxlayout = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(boxlayout);
        this.setPreferredSize(new Dimension(parentWidth - 24, 170));

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

        JTextArea labelTitreCollecte = new JTextArea("Collecte - " + String.valueOf(dureeCollecte) + " sec");
        labelTitreCollecte.setSize(this.getWidth() - 4 * valMarginBase, 30);
        labelTitreCollecte.setFont(policeTexteImportant);
        labelTitreCollecte.setLineWrap(true);
        labelTitreCollecte.setWrapStyleWord(true);
        labelTitreCollecte.setOpaque(false);
        panelInside.add(labelTitreCollecte);
        panelInside.add(Box.createRigidArea(new Dimension(0, valMarginBase/2)));

        /************************************************************************************/
        /*                            Label adresse de collecte                             */
        /************************************************************************************/
        JTextArea labelAdresseCollecte = new JTextArea(adresseCollecte);
        labelAdresseCollecte.setSize(this.getWidth() - 4 * valMarginBase, 45);
        labelAdresseCollecte.setFont(policeTexte);
        labelAdresseCollecte.setLineWrap(true);
        labelAdresseCollecte.setWrapStyleWord(true);
        labelAdresseCollecte.setOpaque(false);
        panelInside.add(labelAdresseCollecte);
        panelInside.add(Box.createRigidArea(new Dimension(0, 2*valMarginBase)));

        /************************************************************************************/
        /*                                 Label duree depot                                */
        /************************************************************************************/
        JTextArea labelTitreDepot = new JTextArea("Dépôt - " + String.valueOf(dureeDepot) + " sec");
        labelTitreDepot.setSize(this.getWidth() - 4 * valMarginBase, 30);
        labelTitreDepot.setFont(policeTexteImportant);
        labelTitreDepot.setLineWrap(true);
        labelTitreDepot.setWrapStyleWord(true);
        labelTitreDepot.setOpaque(false);
        panelInside.add(labelTitreDepot);
        panelInside.add(Box.createRigidArea(new Dimension(0, valMarginBase/2)));

        /************************************************************************************/
        /*                                Label adresse de depot                            */
        /************************************************************************************/
        JTextArea labelAdresseDepot = new JTextArea(adresseDepot);
        labelAdresseDepot.setSize(this.getWidth() - 4 * valMarginBase, 45);
        labelAdresseDepot.setFont(policeTexte);
        labelAdresseDepot.setLineWrap(true);
        labelAdresseDepot.setWrapStyleWord(true);
        labelAdresseDepot.setOpaque(false);
        panelInside.add(labelAdresseDepot);

        this.add(panelInside);
    }
}
