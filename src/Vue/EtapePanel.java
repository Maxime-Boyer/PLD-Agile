package Vue;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class EtapePanel extends JPanel {

    public EtapePanel(String heurePassage, boolean estEtapeCollecte, int duree, String adresse , int parentWidth, int valMarginBase, Font policeTexte, Font policeTexteImportant){

        /************************************************************************************/
        /*                              Panel principal                                     */
        /************************************************************************************/
        this.setBounds(0, 0, parentWidth, 110);
        this.setLayout(null);

        /************************************************************************************/
        /*                               Label heure de passage                             */
        /************************************************************************************/
        JLabel labelHeurePassage = new JLabel(heurePassage);
        labelHeurePassage.setBounds(0, valMarginBase, this.getWidth(), 30);
        labelHeurePassage.setFont(policeTexte);
        this.add(labelHeurePassage);

        /************************************************************************************/
        /*                               Panel corps Etape                                  */
        /************************************************************************************/
        JPanel panelCorpsEtape = new JPanel();
        panelCorpsEtape.setBounds(0, labelHeurePassage.getY() + labelHeurePassage.getHeight(), this.getWidth(), 70);
        panelCorpsEtape.setLayout(null);

        if(estEtapeCollecte){
            panelCorpsEtape.setBorder(new LineBorder(new Color(255, 255, 58), 2, true));
            panelCorpsEtape.setBackground(new Color(255, 255, 220));
        }
        else{
            panelCorpsEtape.setBorder(new LineBorder(new Color(78, 112, 255), 2, true));
            panelCorpsEtape.setBackground(new Color(219, 225, 255));
        }

        this.add(panelCorpsEtape);

        /************************************************************************************/
        /*                            Label type Etape + duree                              */
        /************************************************************************************/
        JLabel labelTitreEtape = new JLabel();

        if(estEtapeCollecte){
            labelTitreEtape.setText("Collecte - " + duree + " min");
        }
        else{
            labelTitreEtape.setText("Dépôt - " + duree + " min");
        }

        labelTitreEtape.setBounds(2 * valMarginBase, valMarginBase, 200, 30);
        labelTitreEtape.setFont(policeTexteImportant);
        panelCorpsEtape.add(labelTitreEtape);

        /************************************************************************************/
        /*                                    Label croix                                   */
        /************************************************************************************/
        JLabel labelCroix = new JLabel("X", SwingConstants.RIGHT);
        labelCroix.setBounds(panelCorpsEtape.getWidth() - 2 * valMarginBase - 20, 0, 20, 30);
        labelCroix.setFont(policeTexte);
        panelCorpsEtape.add(labelCroix);

        /************************************************************************************/
        /*                                   Label Adresse                                  */
        /************************************************************************************/
        JLabel labelAdresse = new JLabel(adresse);
        labelAdresse.setBounds(2 * valMarginBase, labelTitreEtape.getHeight() + labelTitreEtape.getY(), panelCorpsEtape.getWidth() - 4*valMarginBase, 30);
        labelAdresse.setFont(policeTexte);
        panelCorpsEtape.add(labelAdresse);
    }
}
