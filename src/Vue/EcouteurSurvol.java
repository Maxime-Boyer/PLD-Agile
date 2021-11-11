package Vue;

import Model.Etape;
import Model.Requete;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class EcouteurSurvol implements MouseListener {

    private Fenetre fenetre;
    private RequetePanel requeteSurvolee;

    private RequetePanel[] listeRequetes;
    private EtapePanel[] listeEtapes;

    /**
     * Creation d'une classe permettant de gérer les actions de survol
     * @param fenetre: la fenetre de l'application
     */
    public EcouteurSurvol(Fenetre fenetre){
        this.fenetre = fenetre;
        listeRequetes = null;
        listeEtapes = null;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //fenetre.getMenuLateral().setMessageUtilisateur("Clique avec ecouteur survol");
        /*if(e.getSource() instanceof JButton){
            System.out.println("arrive dans mouseClicked");
            //fenetre.getCartePanel().indiquerPositionRequete(((EtapePanel) e.getSource()).getRequeteEtape().getEtapeCollecte(), ((EtapePanel) e.getSource()).getRequeteEtape().getEtapeDepot());
        }*/

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    /**
     * Gestion des survols et modification de la fenetre en fonction
     * @param e: l'evenemen t à gérer
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        //System.out.println("Survol entered : " + e.getSource());
        boolean rpTrouve = false;
        RequetePanel rp = null;
        boolean epTrouve = false;
        EtapePanel ep = null;
        Component c = SwingUtilities.getDeepestComponentAt(e.getComponent(), e.getX(), e.getY());
        //System.out.println(c.getClass());
        if(listeRequetes != null) {
            for (int i = 0; i < listeRequetes.length; i++) {
                if (listeRequetes[i] != null && (SwingUtilities.isDescendingFrom(c, listeRequetes[i]) || c == listeRequetes[i])) {
                    rpTrouve = true;
                    rp = listeRequetes[i];
                }
            }
        }
        if(listeEtapes != null) {
            for (int i = 0; i < listeEtapes.length; i++) {
                if (listeEtapes[i] != null && (SwingUtilities.isDescendingFrom(c, listeEtapes[i]) || c == listeEtapes[i])) {
                    epTrouve = true;
                    ep = listeEtapes[i];
                }
            }
        }

        if (epTrouve) {
            Requete requete = (ep).getRequeteEtape();
            Etape collecte;
            Etape depot;
            if(requete != null){
                collecte = requete.getEtapeCollecte();
                depot = requete.getEtapeDepot();
            } else {
                collecte = fenetre.getTournee().getEtapeDepart();
                depot = null;
            }
            fenetre.getCartePanel().indiquerPositionRequete(collecte,depot);
        } else if(rpTrouve){
            fenetre.getCartePanel().indiquerPositionRequete((rp).getCollecte(), (rp).getDepot());
        } else {
            fenetre.getCartePanel().supprimerPositionRequete();
        }


        if((e.getSource() instanceof JLabel && ((JLabel) e.getSource()).getText().equals("X"))){
            fenetre.setCursor(new Cursor(Cursor.HAND_CURSOR));
        } else{
            fenetre.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }

    }

    @Override
    public void mouseExited(MouseEvent e) {
        //System.out.println("          Survol exited : " + e.getSource());

        //if(!(e.getSource() instanceof RequetePanel) && !(e.getSource() instanceof  JTextField)){
        //    fenetre.getCartePanel().supprimerPositionRequete();
        //}
    }

    public void setListeRequetes(RequetePanel[] listeRequetes) {
        this.listeRequetes = listeRequetes;
    }

    public void setListeEtapes(EtapePanel[] listeEtapes) {
        this.listeEtapes = listeEtapes;
    }
}