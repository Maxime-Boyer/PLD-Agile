package Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Carte {
    private Map<Long,Adresse> listeAdresses ;
    private List <Segment>listeSegments;
    private String nomCarte;

    public Carte(String nomCarte) {
        this.nomCarte = nomCarte;
        listeAdresses = new HashMap<Long,Adresse>();
        listeSegments = new ArrayList<Segment>();
    }

    public Adresse obtenirAdresseParId(Long id){
        return listeAdresses.get(id);

    }

    public Map<Long, Adresse> getListeAdresses() {
        return listeAdresses;
    }

    public List<Segment> getListeSegments() {
        return listeSegments;
    }
}
