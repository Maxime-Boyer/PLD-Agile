package Vue;

import Model.Etape;
import Model.Requete;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class EtapePanel extends JPanel {

    private Requete requeteEtape;
    private Etape etape;
    private Color couleurBordure;

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
    public EtapePanel(Etape etape, Requete requeteEtape, int parentWidth, int valMarginBase, Font policeTexte, Font policeTexteImportant, EcouteurSurvol ecouteurSurvol, EcouteurBoutons ecouteurBoutons){

        this.etape = etape;

        /************************************************************************************/
        /*                              Panel principal                                     */
        /************************************************************************************/
        BoxLayout boxlayout = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(boxlayout);
        this.setPreferredSize(new Dimension(parentWidth - 24, 110 + 26 * (etape.getNomAdresse().length()/64)));
        this.addMouseListener(ecouteurSurvol);

        this.requeteEtape = requeteEtape;

        Color couleurBordure;
        if(requeteEtape != null) {
            couleurBordure = requeteEtape.getCouleurRequete();
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
        this.couleurBordure = couleurBordure;

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

            String dureeEtape = String.valueOf(etape.getDureeEtape()/60) + "min ";

            if(etape.getDureeEtape()%60 > 0){
                dureeEtape += String.valueOf(etape.getDureeEtape()%60) + "sec";
            }

            if (requeteEtape.getEtapeCollecte().getIdAdresse().equals(etape.getIdAdresse())) {
                texteTitreEtape = "Collecte - ";
            } else {
                texteTitreEtape = "Dépôt - ";
            }

            texteTitreEtape += dureeEtape;
        } else {
            texteTitreEtape = "Entrepôt";
        }

        JPanel firstLine = new JPanel();
        firstLine.setLayout(new BoxLayout(firstLine, BoxLayout.LINE_AXIS));


        firstLine.setSize(this.getWidth() - 4 * valMarginBase, 30);
        firstLine.setOpaque(false);
        firstLine.addMouseListener(ecouteurSurvol);

        JLabel labelTitreCollecte = new JLabel(texteTitreEtape);
        labelTitreCollecte.setFont(policeTexteImportant);
        firstLine.add(labelTitreCollecte, BorderLayout.LINE_START);
        labelTitreCollecte.addMouseListener(ecouteurSurvol);
        firstLine.add(Box.createHorizontalGlue());

        if(requeteEtape != null) {
            BoutonSuppressionRequete bouttonSuppr = new BoutonSuppressionRequete(Fenetre.SUPPRIMER_REQUETE, policeTexte, ecouteurBoutons, requeteEtape);
            bouttonSuppr.addActionListener(ecouteurBoutons);
            firstLine.add(bouttonSuppr);
        }

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
        labelAdresseCollecte.setPreferredSize(labelAdresseCollecte.getPreferredSize());
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

    /**
     * Getter sur l'étape liée à EtapePanel
     * @return l'étape liée à EtapePanel
     */
    public Etape getEtape() {
        return etape;
    }

    /**
     * Getter sur la couleur de la bordue de l'EtapePanel
     * @return la couleur de la bordue de l'EtapePanel
     */
    public Color getCouleurBordure() {
        return couleurBordure;
    }
}
