package Vue;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.LineBorder;


public class RequetePanel extends JPanel {

    public RequetePanel(int dureeCollecte, int dureeDepot, String adresseCollecte, String adresseDepot, int parentWidth, int valMarginBase, Font policeTexte, Font policeTexteImportant){

        /************************************************************************************/
        /*                              Panel principal                                     */
        /************************************************************************************/
        this.setBackground(Color.WHITE);
        this.setBounds(0, 0, parentWidth, 130);
        this.setLayout(null);
        this.setBorder(new LineBorder(Color.BLACK, 1, true));

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
        JLabel labelAdresseCollecte = new JLabel(adresseCollecte);
        labelAdresseCollecte.setBounds(2*valMarginBase, labelTitreCollecte.getY() + labelTitreCollecte.getHeight() - valMarginBase, this.getWidth() - 4 * valMarginBase, 30);
        labelAdresseCollecte.setFont(policeTexte);
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
        JLabel labelAdresseDepot = new JLabel(adresseDepot);
        labelAdresseDepot.setBounds(2*valMarginBase, labelTitreDepot.getY() + labelTitreDepot.getHeight() - valMarginBase, this.getWidth() - 4 * valMarginBase, 30);
        labelAdresseDepot.setFont(policeTexte);
        this.add(labelAdresseDepot);
    }
}
