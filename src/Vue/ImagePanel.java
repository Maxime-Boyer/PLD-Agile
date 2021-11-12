package Vue;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImagePanel extends JPanel {

    private BufferedImage image;

    /**
     * Classe permettant de charger et d'afficher une image
     *
     * @param pathImage: le path de l'image a afficher
     */
    public ImagePanel(String pathImage) {
        try {
            image = ImageIO.read(new File(pathImage));
        } catch (IOException ex) {
            System.out.println(ex.toString());
        }
    }

    /**
     * Affichage de l'image
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this);
    }

}
