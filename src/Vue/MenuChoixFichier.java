package Vue;

import javax.swing.*;
import java.awt.*;


public class MenuChoixFichier extends JPanel {
    private String nomFichier;

    /**
     * Ouverture de la fenetre de l'explorateur du fichier afin d'importer un fichier dans l'application
     */
    public MenuChoixFichier() {
        JFrame frameSelectCarte = new JFrame();

        FileDialog fd = new FileDialog(frameSelectCarte, "Sélectionnez une carte au format xml", FileDialog.LOAD);
        fd.setDirectory("C:\\");
        fd.setFile("*.xml");
        fd.setVisible(true);
        this.nomFichier = fd.getDirectory() + fd.getFile();
        frameSelectCarte.dispose();
    }

    /**
     * geteur
     * @return: le nom du ficheir importé
     */
    public String getNomFichier() {
        return nomFichier;
    }
}
