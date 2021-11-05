package Vue;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class PopUpSaisieDuree extends JPanel {

    private int largeur;
    private int hauteur;
    private int positionX;
    private int positionY;
    private JLabel titrePopUp;
    private JTextField champValDuree;
    private Bouton boutonValider;

    /**
     * Pop up permettant la saisie d'une duree lors de l'ajout d'une etape
     * @param policeTexte: la police du texte dans le pop up
     * @param ecouteurBoutons: l'ecouteur permettant la gestion du clic sur le bouton
     */
    public PopUpSaisieDuree(Font policeTexte, EcouteurBoutons ecouteurBoutons){

        this.setLayout(null);

        largeur = 200;
        hauteur = 115;
        positionX = 0;
        positionY = 0;

        if(positionY < 0){
            positionY = 0;
        }

        setBounds(positionX,positionY,largeur,hauteur);
        setBackground(new Color(245,245,245));
        setBorder(new LineBorder(Color.black, 1, true));

        titrePopUp = new JLabel("Durée de l'étape");
        titrePopUp.setBounds(2*Fenetre.valMarginBase, Fenetre.valMarginBase, largeur - 4*Fenetre.valMarginBase, 30);
        titrePopUp.setFont(policeTexte);
        this.add(titrePopUp);

        champValDuree = new JTextField("10");
        champValDuree.setBounds(2*Fenetre.valMarginBase, titrePopUp.getHeight() + titrePopUp.getY() + Fenetre.valMarginBase, largeur - 4 * Fenetre.valMarginBase, 30);
        this.add(champValDuree);

        boutonValider = new Bouton("Valider", policeTexte, ecouteurBoutons);
        boutonValider.setBounds((largeur - 100)/2, hauteur-2*Fenetre.valMarginBase-30, 100, 30 );
        this.add(boutonValider);
    }

    /**
     * Permet d'ajuster la position du pop up en fonction des besoins
     * @param x: coordonnee x en px
     * @param y: coordonnee y en px
     */
    public void setPosition(int x, int y){
        positionX = x + 10;
        positionY = (int) (y - hauteur/2);
        setBounds(positionX,positionY,largeur,hauteur);
    }

    /**
     * Extrait la duree du champ de saisie
     * @return: la duree entree par l'utilisateur
     */
    public int getDureePopUp(){
        return Integer.parseInt(champValDuree.getText());
    }

}