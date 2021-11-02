package Algorithmie;

import Model.*;

import java.util.*;

public abstract class TemplateTSP implements TSP{

    protected Carte carte;
    protected Tournee tournee;
    protected HashMap<Long, HashMap<Long, CheminEntreEtape>> grapheCompletDesEtapes;
    protected HashMap<Long, Requete> mapRequete;

    private long tempsDepart;
    private int tempsLimite;
    private int coutMeilleureSolution;

    TemplateTSP(Carte carte, Tournee tournee, HashMap<Long, HashMap<Long, CheminEntreEtape>> grapheCompletDesEtapes, int tempsLimite){
        this.carte = carte;
        this.tournee = tournee;
        this.grapheCompletDesEtapes = grapheCompletDesEtapes;
        this.tempsLimite = tempsLimite;

        mapRequete = new HashMap<>();
        for(Requete req : tournee.getListeRequetes()){
            mapRequete.put(req.getEtapeCollecte().getIdAdresse(),req);
            mapRequete.put(req.getEtapeDepot().getIdAdresse(),req);
        }
    }

    protected abstract int evaluation(Adresse adresseActuelle,List<Adresse> nonVisite);

    protected abstract Iterator<Adresse> iterateur(Adresse adresseActuelle,List<Adresse> nonVisite);

    @Override
    public void chercherSolution() {
        if(tempsLimite<=0)return;
        tempsDepart = System.currentTimeMillis();

        //Initialisation des listes des sommets visites / non visites
        List<Adresse> nonVisite = new ArrayList<Adresse>(tournee.getListeRequetes().size()*2);
        for (int i=0; i<tournee.getListeRequetes().size(); i++){
            nonVisite.add(tournee.getListeRequetes().get(i).getEtapeCollecte());
            nonVisite.add(tournee.getListeRequetes().get(i).getEtapeDepot());
        }
        List<Adresse> visite = new ArrayList<Adresse>(tournee.getListeRequetes().size()*2+1);
        visite.add(tournee.getAdresseDepart());

        coutMeilleureSolution = Integer.MAX_VALUE;

        //On execute l'algorithme
        separationEtEvaluation(tournee.getAdresseDepart(), nonVisite, visite, 0);
    }

    private void separationEtEvaluation(Adresse adresseActuelle, List<Adresse> nonVisite, List<Adresse> visite, int coutActuel){

        //On s'arrete au temps limite
        if (System.currentTimeMillis() - tempsDepart > tempsLimite) return;

        if (nonVisite.size() == 0){
            //On retourne au point de depart
            if (adresseActuelle.getIdAdresse() != tournee.getAdresseDepart().getIdAdresse()){
                //Si on trouve une solution meilleure que celles deja trouvees
                if (coutActuel+grapheCompletDesEtapes.get(adresseActuelle.getIdAdresse()).get(tournee.getAdresseDepart().getIdAdresse()).distance < coutMeilleureSolution){

                    List<CheminEntreEtape> listeCee = new LinkedList<>();
                    tournee.setListeChemins(listeCee);
                    //On exporte la meilleure sol
                    for(int i=0 ; i<visite.size()-1 ; i++){
                        listeCee.add(grapheCompletDesEtapes.get(visite.get(i).getIdAdresse()).get(visite.get(i+1).getIdAdresse()));
                    }
                    listeCee.add(grapheCompletDesEtapes.get(visite.get(visite.size()-1).getIdAdresse()).get(tournee.getAdresseDepart().getIdAdresse()));

                    //On change le meilleur cout
                    coutMeilleureSolution = coutActuel+grapheCompletDesEtapes.get(adresseActuelle.getIdAdresse()).get(tournee.getAdresseDepart().getIdAdresse()).distance;

                }
            }
        } else if (coutActuel+evaluation(adresseActuelle,nonVisite) < coutMeilleureSolution){
            Iterator<Adresse> it = iterateur(adresseActuelle, nonVisite);
            while (it.hasNext()){
                Adresse prochaineAdresse = it.next();
                visite.add(prochaineAdresse);
                nonVisite.remove(prochaineAdresse);
                separationEtEvaluation(prochaineAdresse, nonVisite, visite,
                        coutActuel+grapheCompletDesEtapes.get(adresseActuelle.getIdAdresse()).get(prochaineAdresse.getIdAdresse()).distance);
                visite.remove(prochaineAdresse);
                nonVisite.add(prochaineAdresse);
            }
        }

    }

    @Override
    public Tournee obtenirSolution() {
        return tournee;
    }

    @Override
    public int obtenirCoutSolution() {
        return coutMeilleureSolution;
    }

}
