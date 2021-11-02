package Vue;

import Controleur.Controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class EcouteurSurvol implements MouseListener {

    private Fenetre fenetre;
    private RequetePanel requeteSurvolee;

    public EcouteurSurvol(Fenetre fenetre){
        this.fenetre = fenetre;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if(e.getSource() instanceof RequetePanel){
            fenetre.getCartePanel().indiquerPositionRequete(((RequetePanel) e.getSource()).getCollecte(), ((RequetePanel) e.getSource()).getDepot());
        }
        if(e.getSource() instanceof  CartePanel || e.getSource() instanceof  Bouton){
            fenetre.getCartePanel().supprimerPositionRequete();
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}