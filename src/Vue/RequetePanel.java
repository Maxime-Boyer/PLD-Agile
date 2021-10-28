package Vue;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.LineBorder;


public class RequetePanel extends JPanel {

    public RequetePanel(int dureeCollecte, int dureeDepot, String adresseCollecte, String adresseDepot, Color couleurBordure, int parentWidth, int valMarginBase, Font policeTexte, Font policeTexteImportant){

        /************************************************************************************/
        /*                              Panel principal                                     */
        /************************************************************************************/
        this.setBackground(Color.WHITE);
        this.setBounds(0, 0, parentWidth, 200);
        this.setLayout(null);

        float teinteRouge = (float) couleurBordure.getRed() / (float) 255;
        float teinteVert = (float) couleurBordure.getGreen() / (float) 255;
        float teinteBleue = (float) couleurBordure.getBlue() / (float) 255;
        Color couleurFond = new Color(teinteRouge, teinteVert, teinteBleue, (float) 0.1);
        this.setBackground(couleurFond);
        this.setBorder(new LineBorder(couleurBordure, 2, true));

        /************************************************************************************/
        /*                               Label duree de collecte                            */
        /************************************************************************************/
        JLabel labelTitreCollecte = new JLabel("Collecte - " + String.valueOf(dureeCollecte) + " sec");
        labelTitreCollecte.setBounds(2*valMarginBase, valMarginBase, this.getWidth() - 4 * valMarginBase, 30);
        labelTitreCollecte.setFont(policeTexteImportant);
        this.add(labelTitreCollecte);

        /************************************************************************************/
        /*                            Label adresse de collecte                             */
        /************************************************************************************/
        JTextArea labelAdresseCollecte = new JTextArea(adresseCollecte);
        labelAdresseCollecte.setBounds(2*valMarginBase, labelTitreCollecte.getY() + labelTitreCollecte.getHeight() - valMarginBase, this.getWidth() - 4 * valMarginBase, 60);
        labelAdresseCollecte.setFont(policeTexte);
        labelAdresseCollecte.setLineWrap(true);
        this.add(labelAdresseCollecte);

        /************************************************************************************/
        /*                                 Label duree depot                                */
        /************************************************************************************/
        JLabel labelTitreDepot = new JLabel("Dépôt - " + String.valueOf(dureeDepot) + " min");
        labelTitreDepot.setBounds(2*valMarginBase, labelAdresseCollecte.getY() + labelAdresseCollecte.getHeight() + valMarginBase, this.getWidth() - 4 * valMarginBase, 30);
        labelTitreDepot.setFont(policeTexteImportant);
        this.add(labelTitreDepot);

        /************************************************************************************/
        /*                                Label adresse de depot                            */
        /************************************************************************************/
        JTextArea labelAdresseDepot = new JTextArea(adresseDepot);
        labelAdresseDepot.setBounds(2*valMarginBase, labelTitreDepot.getY() + labelTitreDepot.getHeight() - valMarginBase, this.getWidth() - 4 * valMarginBase, 60);
        labelAdresseDepot.setFont(policeTexte);
        labelAdresseDepot.setLineWrap(true);
        this.add(labelAdresseDepot);
    }
}
