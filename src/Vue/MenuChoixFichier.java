package Vue;

import javax.swing.*;
import java.awt.*;


public class MenuChoixFichier extends JPanel {
    private String nomFichier;

    /**
     * TODO
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

    public String getNomFichier() {
        return nomFichier;
    }
}
