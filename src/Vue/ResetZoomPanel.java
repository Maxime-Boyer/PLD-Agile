package Vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ResetZoomPanel extends JPanel implements MouseListener {

    CartePanel cartePanel;
    int taille;

    /**
     * Panel permettant de remettre le zoom à son état initial
     * @param cartePanel: la panel où est affichée la carte
     * @param taille: la longeur du cote du panel que l'on crée
     * @param x: la position sur x du panel que l'on crée
     * @param y: la position sur y du panel que l'on crée
     */
    public ResetZoomPanel(CartePanel cartePanel, int taille, int x, int y) {
        this.cartePanel = cartePanel;
        this.setLayout(null);
        this.setBounds(x, y, taille, taille);
        this.taille = taille;
        this.setOpaque(false);
        this.addMouseListener(this);
    }

    /**
     * Permet l'affichage graphique du panel
     */
    @Override
    public void paintComponent(Graphics g) {

        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(Color.white);
        //background
        g2.fillRoundRect(0, 0, taille, taille, 20, 20);


        g2.setColor(Color.darkGray);
        //bordure
        g2.setStroke(new BasicStroke(2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.drawRoundRect(0, 0, taille - 1, taille - 1, 20, 20);
        //lignes
        g2.drawLine(taille / 8, taille / 2, taille - taille / 8, taille / 2);
        g2.drawLine(taille / 2, taille / 8, taille / 2, taille - taille / 8);
        //ronds
        g2.drawOval(taille / 4, taille / 4, taille / 2, taille / 2);
        g2.fillOval(taille / 3 + 1, taille / 3 + 1, taille / 3, taille / 3);

        super.paintComponent(g);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    /**
     * Au clic sur la souris, remet la carte et le zoom à sa position initiale
     * @param e
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            cartePanel.setMinLongitudeCarte(cartePanel.getMinLongitudeInitialeCarte());
            cartePanel.setMaxLongitudeCarte(cartePanel.getMaxLongitudeInitialeCarte());
            cartePanel.setMinLatitudeCarte(cartePanel.getMinLatitudeInitialeCarte());
            cartePanel.setMaxLatitudeCarte(cartePanel.getMaxLatitudeInitialeCarte());
            cartePanel.repaint();
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
