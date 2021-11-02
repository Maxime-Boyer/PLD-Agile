package Vue;

import Model.Etape;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class EtapePanel extends JPanel {

    public EtapePanel(Etape etape, Boolean estCollecte, Color couleurBordure, int parentWidth, int valMarginBase, Font policeTexte, Font policeTexteImportant){

        /************************************************************************************/
        /*                              Panel principal                                     */
        /************************************************************************************/
        BoxLayout boxlayout = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(boxlayout);
        this.setPreferredSize(new Dimension(parentWidth - 24, 110));

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

        String texteTitreEtape = "";
        if(estCollecte){
            texteTitreEtape = "Collecte - " + etape.getDureeEtape() + " sec";
        }
        else{
            texteTitreEtape = "Dépôt - " + etape.getDureeEtape() + " sec";
        }

        JTextArea labelTitreCollecte = new JTextArea(texteTitreEtape);
        labelTitreCollecte.setSize(this.getWidth() - 4 * valMarginBase, 30);
        labelTitreCollecte.setFont(policeTexteImportant);
        labelTitreCollecte.setLineWrap(true);
        labelTitreCollecte.setWrapStyleWord(true);
        labelTitreCollecte.setOpaque(false);
        panelInside.add(labelTitreCollecte);
        panelInside.add(Box.createRigidArea(new Dimension(0, valMarginBase/2)));

        /************************************************************************************/
        /*                            Label heure de passage                                */
        /************************************************************************************/
        String heurePassage = etape.getHeureDePassage().toString();
        JTextArea labelHeurePassage = new JTextArea("Heure de passage: "+heurePassage);
        labelHeurePassage.setSize(this.getWidth() - 4 * valMarginBase, 45);
        labelHeurePassage.setFont(policeTexte);
        labelHeurePassage.setLineWrap(true);
        labelHeurePassage.setWrapStyleWord(true);
        labelHeurePassage.setOpaque(false);
        panelInside.add(labelHeurePassage);
        panelInside.add(Box.createRigidArea(new Dimension(0, valMarginBase/2)));

        /************************************************************************************/
        /*                            Label adresse de collecte                             */
        /************************************************************************************/
        JTextArea labelAdresseCollecte = new JTextArea(etape.getNomAdresse());
        labelAdresseCollecte.setSize(this.getWidth() - 4 * valMarginBase, 45);
        labelAdresseCollecte.setFont(policeTexte);
        labelAdresseCollecte.setLineWrap(true);
        labelAdresseCollecte.setWrapStyleWord(true);
        labelAdresseCollecte.setOpaque(false);
        panelInside.add(labelAdresseCollecte);

        this.add(panelInside);
    }
}
