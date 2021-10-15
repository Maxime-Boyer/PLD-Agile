package Model;

import java.util.List;
import java.util.Map;

public class Carte {
    private Map<String,Adresse> listeAdresses;
    private Map <String,Adresse>listeSegments;
    private String nomCarte;

    public Carte(String nomCarte) {
        this.nomCarte = nomCarte;
    }

    public Adresse obtenirAdresseParId(String id){
        return listeAdresses.get(id);

    }
}
