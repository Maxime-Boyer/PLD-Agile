package Vue;

import Model.Etape;
import Model.Requete;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class EtapePanel extends JPanel {

    private Requete requeteEtape;

    public EtapePanel(Etape etape, Requete requeteEtape, int parentWidth, int valMarginBase, Font policeTexte, Font policeTexteImportant, EcouteurSurvol ecouteurSurvol){

        /************************************************************************************/
        /*                              Panel principal                                     */
        /************************************************************************************/
        BoxLayout boxlayout = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(boxlayout);
        this.setPreferredSize(new Dimension(parentWidth - 24, 110));
        this.addMouseListener(ecouteurSurvol);

        this.requeteEtape = requeteEtape;

        Color couleurBordure = requeteEtape.getCouleur();
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
        if(requeteEtape.getEtapeCollecte().getIdAdresse().equals(etape.getIdAdresse())){
            texteTitreEtape = "Collecte - " + etape.getDureeEtape() + " sec";
        }
        else{
            texteTitreEtape = "Dépôt - " + etape.getDureeEtape() + " sec";
        }

        JPanel firstLine = new JPanel(new BorderLayout());
        firstLine.setSize(this.getWidth() - 4 * valMarginBase, 30);
        firstLine.setOpaque(false);

        JLabel labelTitreCollecte = new JLabel(texteTitreEtape);
        labelTitreCollecte.setFont(policeTexteImportant);
        firstLine.add(labelTitreCollecte, BorderLayout.LINE_START);

        JLabel labelSuppr = new JLabel("X");
        labelSuppr.addMouseListener(ecouteurSurvol);
        firstLine.add(labelSuppr, BorderLayout.LINE_END);

        panelInside.add(firstLine);
        panelInside.add(Box.createRigidArea(new Dimension(0, valMarginBase/2)));

        /************************************************************************************/
        /*                            Label heure de passage                                */
        /************************************************************************************/
        String heurePassage = etape.getHeureDePassage().getHours() + "h" + etape.getHeureDePassage().getMinutes();
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

    public Requete getRequeteEtape(){
        return requeteEtape;
    }
}
