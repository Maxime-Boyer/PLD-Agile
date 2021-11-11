package Vue;

import Model.Etape;
import Model.Requete;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class EtapePanel extends JPanel {

    private Requete requeteEtape;

    /**
     * Panel gerant l'affichage d'une atape dans l'affichage textuel des étapes triées de la tournee
     * @param etape: l'etape a afficher
     * @param requeteEtape: la requete auquel appartient l'étape
     * @param parentWidth: la largeur du composant où doit etre affichee l'etape
     * @param valMarginBase: l'ecart standard en vigeur dans l'application
     * @param policeTexte: la police a appliquer au texte
     * @param policeTexteImportant: la police a appliquer aux textes à mettre en evidence
     * @param ecouteurSurvol: l'ecouteur gerant les evenements de survol afin de pointer la requete sur la carte
     */
    public EtapePanel(Etape etape, Requete requeteEtape, int parentWidth, int valMarginBase, Font policeTexte, Font policeTexteImportant, EcouteurSurvol ecouteurSurvol){

        /************************************************************************************/
        /*                              Panel principal                                     */
        /************************************************************************************/
        BoxLayout boxlayout = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(boxlayout);
        this.setPreferredSize(new Dimension(parentWidth - 24, 110));
        this.addMouseListener(ecouteurSurvol);

        this.requeteEtape = requeteEtape;

        Color couleurBordure;
        if(requeteEtape != null) {
            couleurBordure = requeteEtape.getCouleur();
        } else {
            couleurBordure = new Color(50,50,50);
        }
        double mult = 0.1;
        double plus = 255*(1-mult);
        float teinteRouge = ((float) ((couleurBordure.getRed()) * mult+plus) / (float) 255) > 1 ? 1 : ((float) ((couleurBordure.getRed()) * mult+plus) / (float) 255);
        float teinteVert = ((float) ((couleurBordure.getGreen()) * mult+plus) / (float) 255) > 1 ? 1 : ((float) ((couleurBordure.getGreen()) * mult+plus) / (float) 255) ;
        float teinteBleue = ((float) ((couleurBordure.getBlue()) * mult+plus) / (float) 255) > 1 ? 1 : ((float) ((couleurBordure.getBlue()) * mult+plus) / (float) 255);
        Color couleurFond = new Color(teinteRouge, teinteVert, teinteBleue);
        this.setBackground(couleurFond);
        this.setBorder(new LineBorder(couleurBordure, 2, true));
        this.setOpaque(true);

        JPanel panelInside = new JPanel();
        BoxLayout boxlayoutInside = new BoxLayout(panelInside, BoxLayout.Y_AXIS);
        panelInside.setLayout(boxlayoutInside);
        panelInside.setOpaque(false);
        panelInside.setBorder(new EmptyBorder(10, 10, 10, 10));
        panelInside.addMouseListener(ecouteurSurvol);

        /************************************************************************************/
        /*                               Label duree de collecte                            */
        /************************************************************************************/

        String texteTitreEtape = "";
        if(requeteEtape != null) {
            if (requeteEtape.getEtapeCollecte().getIdAdresse().equals(etape.getIdAdresse())) {
                texteTitreEtape = "Collecte - " + etape.getDureeEtape() + " sec";
            } else {
                texteTitreEtape = "Dépôt - " + etape.getDureeEtape() + " sec";
            }
        } else {
            texteTitreEtape = "Entrepôt";
        }

        JPanel firstLine = new JPanel(new BorderLayout());
        firstLine.setSize(this.getWidth() - 4 * valMarginBase, 30);
        firstLine.setOpaque(false);
        firstLine.addMouseListener(ecouteurSurvol);

        JLabel labelTitreCollecte = new JLabel(texteTitreEtape);
        labelTitreCollecte.setFont(policeTexteImportant);
        firstLine.add(labelTitreCollecte, BorderLayout.LINE_START);
        labelTitreCollecte.addMouseListener(ecouteurSurvol);

        JLabel labelSuppr = new JLabel("X");
        labelSuppr.addMouseListener(ecouteurSurvol);
        labelSuppr.addMouseListener(ecouteurSurvol);
        firstLine.add(labelSuppr, BorderLayout.LINE_END);

        panelInside.add(firstLine);
        panelInside.add(Box.createRigidArea(new Dimension(0, valMarginBase/2)));

        /************************************************************************************/
        /*                            Label heure de passage                                */
        /************************************************************************************/
        String heurePassage = etape.getHeureDePassage().getHour() + "h" + (etape.getHeureDePassage().getMinute()>9?etape.getHeureDePassage().getMinute():"0"+etape.getHeureDePassage().getMinute());
        JTextArea labelHeurePassage = new JTextArea("Heure de passage: "+heurePassage);
        labelHeurePassage.setSize(this.getWidth() - 4 * valMarginBase, 45);
        labelHeurePassage.setFont(policeTexte);
        labelHeurePassage.setEditable(false);
        labelHeurePassage.setLineWrap(true);
        labelHeurePassage.setWrapStyleWord(true);
        labelHeurePassage.setOpaque(false);
        labelHeurePassage.addMouseListener(ecouteurSurvol);
        panelInside.add(labelHeurePassage);
        panelInside.add(Box.createRigidArea(new Dimension(0, valMarginBase/2)));

        /************************************************************************************/
        /*                            Label adresse de collecte                             */
        /************************************************************************************/
        JTextArea labelAdresseCollecte = new JTextArea(etape.getNomAdresse());
        labelAdresseCollecte.setSize(this.getWidth() - 4 * valMarginBase, 45);
        labelAdresseCollecte.setFont(policeTexte);
        labelAdresseCollecte.setEditable(false);
        labelAdresseCollecte.setLineWrap(true);
        labelAdresseCollecte.setWrapStyleWord(true);
        labelAdresseCollecte.setOpaque(false);
        labelAdresseCollecte.addMouseListener(ecouteurSurvol);
        panelInside.add(labelAdresseCollecte);

        this.add(panelInside);
    }

    /**
     * Getter sur requete liée à EtapePanel
     * @return Requete liée à EtapePanel
     */
    public Requete getRequeteEtape() {
        return requeteEtape;
    }
}
