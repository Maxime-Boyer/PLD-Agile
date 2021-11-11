package Model;

import Algorithmie.Astar;
import Algorithmie.Astar2;
import Exceptions.CommandeImpossibleException;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import Observer.Observable;

public class Tournee extends Observable {
    private Etape etapeDepart;
    private LocalTime heureDepart;
    private List<Requete> listeRequetes;
    private List<CheminEntreEtape> listeChemins;
    private boolean tourneeEstChargee;
    private boolean tourneeEstOrdonee;

    public Tournee(){
        listeRequetes = new ArrayList<>();
        listeChemins = new ArrayList<>();
        etapeDepart = null;
        heureDepart = null;
        tourneeEstChargee = false;
        tourneeEstOrdonee = false;
    }

    public Etape getEtapeDepart() {
        return etapeDepart;
    }

    public LocalTime getDateDepart() {
        return heureDepart;
    }

    public List<Requete> getListeRequetes() {
        return listeRequetes;
    }

    public List<CheminEntreEtape> getListeChemins() {
        return listeChemins;
    }

    public boolean getTourneeEstChargee() {
        return tourneeEstChargee;
    }

    public boolean getTourneeEstOrdonee() {
        return tourneeEstOrdonee;
    }

    public void setEtapeDepart(Etape etapeDepart) {
        this.etapeDepart = etapeDepart;
    }

    public void setHeureDepart(LocalTime heureDepart) {
        this.heureDepart = heureDepart;
    }

    public void setListeRequetes(List<Requete> listeRequetes) {
        this.listeRequetes = listeRequetes;
    }

    public void setListeChemins(List<CheminEntreEtape> listeChemins) {
        this.listeChemins = listeChemins;
    }

    public void setTourneeEstChargee(boolean tourneeEstChargee) {
        this.tourneeEstChargee = tourneeEstChargee;
    }

    public void setTourneeEstOrdonee(boolean tourneeEstOrdonee) {
        this.tourneeEstOrdonee = tourneeEstOrdonee;
    }

    /**
     * Supprime une requete de la liste des requetes et de la liste de chemins entre etape.
     *  Pour cela recalcul le plus court chemin entre l'étape précédente et l'étape suivante du point de collecte et de depot de la requete
     * @param requeteASupprimer la requete qui doit etre supprimee
     * @param carte la carte a partir de laquelle les chemins ont ete calcules
     */
    public void supprimerRequete(Requete requeteASupprimer, Carte carte) throws CommandeImpossibleException {
        //Supprime la requête
        listeRequetes.remove(requeteASupprimer);

        int index = 0;
        int indexEtapePrecedentCollecte = 0;
        int indexEtapePrecedentDepot = 0;
        CheminEntreEtape cheminEntreEtapePrecedentCollecteEtCollecte = null;
        CheminEntreEtape cheminEntreCollecteEtEtapeSuivantCollecte = null;
        CheminEntreEtape cheminEntreEtapePrecedentDepotEtDepot = null;
        CheminEntreEtape cheminEntreDepotEtEtapeSuivantDepot = null;
        //Trouve les étapes précédentes et suivantes des 2 étapes de la requête
        for (CheminEntreEtape cheminEntreEtape : listeChemins) {
            index ++;
            //Cherche l'étape précédente de l'étape de collecte de la requette
            if (cheminEntreEtape.getEtapeArrivee().equals(requeteASupprimer.getEtapeCollecte())) {
                cheminEntreEtapePrecedentCollecteEtCollecte = cheminEntreEtape;
                indexEtapePrecedentCollecte = index;
            }
            //Cherche l'étape suivante de l'étape de collecte de la requette
            else if (cheminEntreEtape.getEtapeDepart().equals(requeteASupprimer.getEtapeCollecte()))
                cheminEntreCollecteEtEtapeSuivantCollecte = cheminEntreEtape;
            //Cherche l'étape précédente de l'étape de depot de la requette
            else if (cheminEntreEtape.getEtapeArrivee().equals(requeteASupprimer.getEtapeCollecte())) {
                cheminEntreEtapePrecedentDepotEtDepot = cheminEntreEtape;
                indexEtapePrecedentDepot = index;
            }
            //Cherche l'étape suivante de l'étape de depot de la requette
            else if (cheminEntreEtape.getEtapeDepart().equals(requeteASupprimer.getEtapeCollecte()))
                cheminEntreDepotEtEtapeSuivantDepot = cheminEntreEtape;
        }

        //Si un des chemins n'est pas touvée, renvoi une erreure
        if (cheminEntreEtapePrecedentCollecteEtCollecte == null || cheminEntreCollecteEtEtapeSuivantCollecte == null || cheminEntreEtapePrecedentDepotEtDepot == null || cheminEntreDepotEtEtapeSuivantDepot == null)
            throw new CommandeImpossibleException("Impossible de supprimer la requete : la tournée est mal formée");

        Astar astar = new Astar2(carte);
        //Suppression de l'étape de collecte de la requete
        //Calcul le plus court chemin entre l'étape précente et l'étape suivante du point de collecte
        CheminEntreEtape nouveauCheminEntrePrecedentEtSuivantCollecte = astar.chercherCheminEntreEtape(cheminEntreEtapePrecedentCollecteEtCollecte.getEtapeDepart(), cheminEntreCollecteEtEtapeSuivantCollecte.getEtapeArrivee());
        //Supprime le chemin entre l'étape précédente de la collecte et la collecte
        listeChemins.remove(indexEtapePrecedentCollecte);
        //Supprime le chemin entre la collecte et l'étape suivante de la collecte
        listeChemins.remove(indexEtapePrecedentCollecte+1);
        //Ajoute le chemin entre l'étape précente et l'étape suivante du point de collecte
        listeChemins.add(indexEtapePrecedentCollecte, nouveauCheminEntrePrecedentEtSuivantCollecte);

        //Suppression de l'étape de depot de la requete
        //Calcul le plus court chemin entre l'étape précente et l'étape suivante du point de depot
        CheminEntreEtape nouveauCheminEntrePrecedentEtSuivantDepot = astar.chercherCheminEntreEtape(cheminEntreEtapePrecedentDepotEtDepot.getEtapeDepart(), cheminEntreDepotEtEtapeSuivantDepot.getEtapeArrivee());
        //Supprime le chemin entre l'étape précédente de le depot et le depot
        listeChemins.remove(indexEtapePrecedentDepot);
        //Supprime le chemin entre le depot et l'étape suivante du depot
        listeChemins.remove(indexEtapePrecedentDepot+1);
        //Ajoute le chemin entre l'étape précente et l'étape suivante du point de depot
        listeChemins.add(indexEtapePrecedentDepot, nouveauCheminEntrePrecedentEtSuivantDepot);

        //Notifie les observateurs que la tournee a été mofifié
        notifyObservers(this);

    }

    /**
     * Remplaces tous les attibuts de la tournee par celle d'une autre tournee
     * @param touneeACloner la tournee de laquelle les attributs sont récupérés
     */
    public void clone(Tournee touneeACloner) {
        this.etapeDepart = touneeACloner.etapeDepart;
        this.heureDepart = touneeACloner.heureDepart;
        this.listeRequetes = touneeACloner.listeRequetes;
        this.listeChemins = touneeACloner.listeChemins;
        this.tourneeEstChargee = touneeACloner.tourneeEstChargee;
        this.tourneeEstOrdonee = touneeACloner.tourneeEstOrdonee;
    }

    /**
     * Vide la tournee
     */
    public void reset() {
        listeRequetes = new ArrayList<>();
        listeChemins = new ArrayList<>();
        etapeDepart = null;
        heureDepart = null;
        tourneeEstChargee = false;
        tourneeEstOrdonee = false;

        //Notifie les observateurs que la tournee a été mofifié
        notifyObservers(this);
    }

    @Override
    public String toString() {
        return "Tournee{" +
                "adresseDepart=" + etapeDepart +
                ", heureDepart=" + heureDepart +
                ", listeRequetes=" + listeRequetes +
                ", listeChemins=" + listeChemins +
                '}';
    }

}
