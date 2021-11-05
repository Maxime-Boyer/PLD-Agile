package Vue;

import Algorithmie.CalculateurTournee;
import Exceptions.IncompatibleAdresseException;
import Model.Adresse;
import Model.Carte;
import Model.LecteurXML;
import Model.Tournee;

import javax.swing.*;
import java.awt.*;
import java.util.Map;


public class MenuChoixFichier extends JPanel {
    private String nomFichier;

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
