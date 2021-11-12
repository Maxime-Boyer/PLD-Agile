package Vue;

import Controleur.Controleur;
import Model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class EcouteurSurvol implements MouseListener {

    private Fenetre fenetre;
    private RequetePanel requeteSurvolee;

    private RequetePanel[] listeRequetes;
    private EtapePanel[] listeEtapes;
    private Controleur controleur;

    /**
     * Creation d'une classe permettant de gérer les actions de clic
     * @param fenetre: la fenetre de l'application
     */
    public EcouteurSurvol(Controleur controleur, Fenetre fenetre){
        this.fenetre = fenetre;
        this.controleur = controleur;
        listeRequetes = null;
        listeEtapes = null;
    }

    /**
     * Gestion des clics et modification de la fenetre en fonction
     * @param e: l'evenemen t à gérer
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        //----- CLIC SUR CARTE PANEL -----
        Component c = SwingUtilities.getDeepestComponentAt(e.getComponent(), e.getX(), e.getY());
        if(c instanceof CartePanel){
            Adresse adresseSelectionnee = coordonnees(e);
            Adresse etapeSelectionnee = fenetre.getTournee().rechercheEtape(adresseSelectionnee,null);

            //si distance entre ad & etape < 30 pixels
            Etape collecte = null;
            Etape depot = null;
            for(Requete req : fenetre.getTournee().getListeRequetes()){
                if(req.getEtapeCollecte().getIdAdresse().equals(etapeSelectionnee.getIdAdresse()) || req.getEtapeDepot().getIdAdresse().equals(etapeSelectionnee.getIdAdresse())){
                    collecte = req.getEtapeCollecte();
                    depot = req.getEtapeDepot();
                    break;
                }
            }
            controleur.afficherIndiquerPositionRequete(collecte, depot);
        } else {



            //----- CLIC SUR MENU LATERAL -----
            boolean rpTrouve = false;
            RequetePanel rp = null;
            boolean epTrouve = false;
            EtapePanel ep = null;
            c = SwingUtilities.getDeepestComponentAt(e.getComponent(), e.getX(), e.getY());
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
                controleur.afficherIndiquerPositionRequete(collecte,depot);
            } else if(rpTrouve){
                controleur.afficherIndiquerPositionRequete((rp).getCollecte(), (rp).getDepot());
            } else {
                controleur.supprimerPositionRequete();
            }

            if((e.getSource() instanceof JLabel && ((JLabel) e.getSource()).getText().equals("X"))){
                fenetre.setCursor(new Cursor(Cursor.HAND_CURSOR));
            } else{
                fenetre.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }


        }

    }

    /**
     * Cree une adresse a partir de la position de la souris
     * @param e MouseEvent
     * @return l'adresse la plus proche de la souris
     */
    private Adresse coordonnees(MouseEvent e){

        if(fenetre.getCartePanel() != null) {
            double longitude = fenetre.getCartePanel().valeurLongitude(e.getX());
            double latitude = fenetre.getCartePanel().valeurLatitude(e.getY());
            Adresse positionClique = new Adresse(latitude, longitude);
            return positionClique;
        }
        return null;
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    /**
     * Setter sur listeRequetes
     * @param listeRequetes
     */
    public void setListeRequetes(RequetePanel[] listeRequetes) {
        this.listeRequetes = listeRequetes;
    }

    /**
     * Setter sur listeEtapes
     * @param listeEtapes
     */
    public void setListeEtapes(EtapePanel[] listeEtapes) {
        this.listeEtapes = listeEtapes;
    }
}