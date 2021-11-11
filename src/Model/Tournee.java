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

    /**
     * Constructeur de Tournee , elle à deux états , un état initial : Ensemble non ordonné d’étapes. Elle contient aussi une adresse de départ, d’arrivée et une heure de départ.
     * un état ordonné : Liste ordonnée d’étapes démarrant et terminant à une même adresse donnée. Il est également composé d’une suite de segments reliant ces étapes ainsi qu’une heure de départ.
     */
    public Tournee(){
        listeRequetes = new ArrayList<>();
        listeChemins = new ArrayList<>();
        etapeDepart = null;
        heureDepart = null;
        tourneeEstChargee = false;
        tourneeEstOrdonee = false;
    }

    /**
     * méthode qui renvoie l'adresse de départ
     * @return: l'Etape de départ
     */
    public Etape getEtapeDepart() {
        return etapeDepart;
    }

    /**
     * méthode qui renvoie l'heure de départ
     * @return: l'heure de départ
     */
    public LocalTime getDateDepart() {
        return heureDepart;
    }

    /**
     * méthode qui renvoie la liste des requêtes
     * @return: la liste des Requete
     */
    public List<Requete> getListeRequetes() {
        return listeRequetes;
    }

    /**
     * méthode qui renvoie la liste des chemins entre étapes
     * @return la liste des CheminEntreEtape
     */
    public List<CheminEntreEtape> getListeChemins() {
        return listeChemins;
    }

    /**
     * méthode qui renvoie true si la tournee est chargée
     * @return: true si la Tournee est chargée
     */
    public boolean getTourneeEstChargee() {
        return tourneeEstChargee;
    }

    /**
     * méthode qui renvoie true si la tournee est ordonnée
     * @return: true si la Tournee est ordonnée
     */
    public boolean getTourneeEstOrdonee() {
        return tourneeEstOrdonee;
    }

        /**
         * méthode qui place l'adresse de départ
         * @param etapeDepart: Etape de départ
         */
    public void setEtapeDepart(Etape etapeDepart) {
        this.etapeDepart = etapeDepart;
    }

    /**
     * méthode qui place l'heure de départ
     * @param heureDepart:cheure de départ
     */
    public void setHeureDepart(LocalTime heureDepart) {
        this.heureDepart = heureDepart;
    }

    /**
     * méthode qui place la liste de requêtes
     * @param listeRequetes: liste des requêtes à placer
     */
    public void setListeRequetes(List<Requete> listeRequetes) {
        this.listeRequetes = listeRequetes;
    }
    /**
     * méthode qui place la liste de chemins entre étape
     * @param listeChemins: liste des chemins entre étape
     */
    public void setListeChemins(List<CheminEntreEtape> listeChemins) {
        this.listeChemins = listeChemins;
    }

    /**
     * méthode qui place le boolean si la tournee est chargée
     * @param tourneeEstChargee: boolean tournée est chargée
     */
    public void setTourneeEstChargee(boolean tourneeEstChargee) {
        this.tourneeEstChargee = tourneeEstChargee;
    }

    /**
     * méthode qui place le boolean si la tournee est ordonée
     * @param tourneeEstOrdonee: boolean tournée est ordonnée
     */
    public void setTourneeEstOrdonee(boolean tourneeEstOrdonee) {
        this.tourneeEstOrdonee = tourneeEstOrdonee;
    }

    /**
     * méthode qui supprime une requete de la liste des requetes et de la liste de chemins entre etape.
     *  Pour cela recalcul le plus court chemin entre l'étape précédente et l'étape suivante du point de collecte et de depot de la requete
     * @param requeteASupprimer: la requete qui doit etre supprimee
     * @param carte: la carte a partir de laquelle les chemins ont ete calcules
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
            if (cheminEntreEtape.getEtapeArrivee().getIdAdresse() == requeteASupprimer.getEtapeCollecte().getIdAdresse()) {
                cheminEntreEtapePrecedentCollecteEtCollecte = cheminEntreEtape;
                indexEtapePrecedentCollecte = index;
            }
            //Cherche l'étape suivante de l'étape de collecte de la requette
            if (cheminEntreEtape.getEtapeDepart().getIdAdresse() == requeteASupprimer.getEtapeCollecte().getIdAdresse())
                cheminEntreCollecteEtEtapeSuivantCollecte = cheminEntreEtape;
            //Cherche l'étape précédente de l'étape de depot de la requette
             if (cheminEntreEtape.getEtapeArrivee().getIdAdresse() == requeteASupprimer.getEtapeDepot().getIdAdresse()) {
                cheminEntreEtapePrecedentDepotEtDepot = cheminEntreEtape;
                indexEtapePrecedentDepot = index;
            }
            //Cherche l'étape suivante de l'étape de depot de la requette
             if (cheminEntreEtape.getEtapeDepart().getIdAdresse() == requeteASupprimer.getEtapeDepot().getIdAdresse())
                cheminEntreDepotEtEtapeSuivantDepot = cheminEntreEtape;
        }


        //Si un des chemins n'est pas touvée, renvoi une erreure
        if (cheminEntreEtapePrecedentCollecteEtCollecte == null || cheminEntreCollecteEtEtapeSuivantCollecte == null || cheminEntreEtapePrecedentDepotEtDepot == null || cheminEntreDepotEtEtapeSuivantDepot == null)
            throw new CommandeImpossibleException("Impossible de supprimer la requete : la tournée est mal formée");

        //Supprime la requête
        listeRequetes.remove(requeteASupprimer);

        Astar astar = new Astar2(carte);
        //Suppression de l'étape de collecte de la requete
        //Calcul le plus court chemin entre l'étape précente et l'étape suivante du point de collecte
        CheminEntreEtape nouveauCheminEntrePrecedentEtSuivantCollecte;
        CheminEntreEtape nouveauCheminEntrePrecedentEtSuivantDepot;
        CheminEntreEtape nouveauCheminEntrePrecedentCollecteEtSuivantDepot;
        if(!cheminEntreCollecteEtEtapeSuivantCollecte.equals(cheminEntreEtapePrecedentDepotEtDepot)){
            nouveauCheminEntrePrecedentEtSuivantCollecte = astar.chercherCheminEntreEtape(cheminEntreEtapePrecedentCollecteEtCollecte.getEtapeDepart(), cheminEntreCollecteEtEtapeSuivantCollecte.getEtapeArrivee());
            nouveauCheminEntrePrecedentEtSuivantDepot = astar.chercherCheminEntreEtape(cheminEntreEtapePrecedentDepotEtDepot.getEtapeDepart(), cheminEntreDepotEtEtapeSuivantDepot.getEtapeArrivee());
        }else {
            nouveauCheminEntrePrecedentCollecteEtSuivantDepot = astar.chercherCheminEntreEtape(cheminEntreEtapePrecedentCollecteEtCollecte.getEtapeDepart(), cheminEntreDepotEtEtapeSuivantDepot.getEtapeArrivee());
        }
        //Supprime le chemin entre l'étape précédente de la collecte et la collecte
        listeChemins.remove(indexEtapePrecedentCollecte);
        //Supprime le chemin entre la collecte et l'étape suivante de la collecte
        listeChemins.remove(indexEtapePrecedentCollecte);
        //Ajoute le chemin entre l'étape précente et l'étape suivante du point de collecte
        listeChemins.add(indexEtapePrecedentCollecte, nouveauCheminEntrePrecedentEtSuivantCollecte);

        //Suppression de l'étape de depot de la requete
        //Calcul le plus court chemin entre l'étape précente et l'étape suivante du point de depot

        //Supprime le chemin entre l'étape précédente de le depot et le depot
        if(!cheminEntreCollecteEtEtapeSuivantCollecte.equals(cheminEntreEtapePrecedentDepotEtDepot)){
            listeChemins.remove(indexEtapePrecedentDepot);
            listeChemins.remove(indexEtapePrecedentDepot);
        }
        else{
            listeChemins.remove(indexEtapePrecedentDepot);
        }

        //Ajoute le chemin entre l'étape précente et l'étape suivante du point de depot
        listeChemins.add(indexEtapePrecedentDepot, nouveauCheminEntrePrecedentEtSuivantDepot);

        //Notifie les observateurs que la tournee a été mofifié
        notifyObservers(this);
    }

    /**
     * méthode qui emplace tous les attibuts de la tournee par celle d'une autre tournee
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
     * méthode qui vide la tournee
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

    /**
     * méthode qui affiche la tournée
     * @return: la Tournee this
     */
    @Override
    public String toString() {
        return "Tournee{" +
                "adresseDepart=" + etapeDepart +
                ", heureDepart=" + heureDepart +
                ",\n    listeRequetes=" + listeRequetes +
                ",\n    listeChemins=" + listeChemins +
                "\n}";
    }

    /**
     * méthode qui permet d'obtenir une étape de la tournée grâce à l'id
     * @param id: id de l'étape à rechercher
     * @return: l'Etape en fonction de l'id entrée dans la liste desCheminEntreEtape
     */
    public Etape obtenirEtapeParId(Long id){
        Etape etapeCherchee = null;
        for(CheminEntreEtape chemin : listeChemins){
            if(chemin.getEtapeDepart().getIdAdresse() == id){
                etapeCherchee = chemin.getEtapeDepart();
                return etapeCherchee;
            }
            if(chemin.getEtapeArrivee().getIdAdresse() == id){
                etapeCherchee = chemin.getEtapeArrivee();
                return etapeCherchee;
            }
        }
        return etapeCherchee;
    }

    /**
     * méthode qui permet de renvoyer la distance entre les deux adresses 1 et 2
     * @param a:Adresse 1
     * @param b: Adresse é
     * @return: la distance
     */
    public double distanceEntreAdresse(Adresse a, Adresse b){
        double distance = Math.pow(a.getLongitude() - b.getLongitude(), 2) + Math.pow(a.getLatitude() - b.getLatitude(), 2);
        return distance;
    }

    /**
     * méthode qui renvoie l'Adresse la plus proche en focntion de l'Adresse en paramètre.
     * @param a: adresse récupéré au clic de l'utilisateur
     * @return: l'Adresse recherchée
     */
    public Adresse rechercheEtape (Adresse a){
        double distanceMin = Double.MAX_VALUE;
        double distanceCollecte;
        double distanceDepot;
        Adresse plusProche = null;
        for(Requete r : listeRequetes){
            Adresse collecte = new Adresse (r.getEtapeCollecte().getLatitude(),r.getEtapeCollecte().getLongitude(),r.getEtapeCollecte().getIdAdresse());
            Adresse depot = new Adresse (r.getEtapeDepot().getLatitude(),r.getEtapeDepot().getLongitude(),r.getEtapeDepot().getIdAdresse());
            distanceCollecte = distanceEntreAdresse(a, collecte);
            distanceDepot = distanceEntreAdresse(a, depot);
            if( distanceCollecte < distanceMin){
                distanceMin = distanceCollecte;
                plusProche = collecte;
            }
            if( distanceDepot < distanceMin){
                distanceMin = distanceDepot;
                plusProche = depot;
            }
        }

        return plusProche;
    }

    /**
     * méthode qui permet de rechercher l'Adresse entre l'adresse cliquée et l'adresse déjà placée sur le carte dans l'état ajout requête
     * @param a: adresse cliquée
     * @param collectePlacee: nouvelle adresse de collecte
     * @return: l'Adresse entre l'Adresse cliquée et l'Adresse déjà placée
     */
    public Adresse rechercheEtape (Adresse a, Adresse collectePlacee){
        double distanceMin = Double.MAX_VALUE;
        double distanceCollecte;
        double distanceDepot;
        double distanceCollectePlacee;
        Adresse plusProche = null;
        for(CheminEntreEtape chemin : listeChemins){
            Adresse collecte = new Adresse (chemin.getEtapeDepart().getLatitude(),chemin.getEtapeDepart().getLongitude(),chemin.getEtapeArrivee().getIdAdresse());
            Adresse depot = new Adresse (chemin.getEtapeArrivee().getLatitude(),chemin.getEtapeArrivee().getLongitude(),chemin.getEtapeArrivee().getIdAdresse());
            distanceCollecte = distanceEntreAdresse(a, collecte);
            distanceDepot = distanceEntreAdresse(a, depot);
            distanceCollectePlacee = distanceEntreAdresse(a, collectePlacee);
            if( distanceCollecte < distanceMin){
                distanceMin = distanceCollecte;
                plusProche = collecte;
            }
            if( distanceDepot < distanceMin){
                distanceMin = distanceDepot;
                plusProche = depot;
            }
            if(distanceCollectePlacee < distanceMin){
                distanceMin = distanceCollectePlacee;
                plusProche = collectePlacee;
            }
        }

        return plusProche;
    }

    /**
     * méthode qui ajoute le chemin lors de l'ajout de requête
     * @param adresse: étape à ajouter
     * @param precedent: étape précédente
     * @param carte: permet d'obtenir les informations en temps réel sur la carte
     */
    public void ajoutChemin(Etape adresse, Etape precedent, Carte carte){
        int index = 0;
        Etape suivant = null;
        for (CheminEntreEtape chemin : listeChemins){
            if(chemin.getEtapeDepart().getIdAdresse().equals(precedent.getIdAdresse())){
                suivant = chemin.getEtapeArrivee();//new Etape(chemin.getEtapeArrivee().getLatitude(),chemin.getEtapeArrivee().getLongitude(),chemin.getEtapeArrivee().getIdAdresse(),chemin.getEtapeArrivee().getDureeEtape(),chemin.getEtapeArrivee().getHeureDePassage());
                listeChemins.remove(chemin);
                break;
            }
            index++;
        }
        Astar2 astar = new Astar2(carte);
        CheminEntreEtape precedentActuel = astar.chercherCheminEntreEtape(precedent,adresse);
        CheminEntreEtape actuelSuivant = astar.chercherCheminEntreEtape(adresse, suivant);
        listeChemins.add(index, precedentActuel);
        listeChemins.add(index+1, actuelSuivant);
        ajouteHeureDePassage();
    }

    /**
     * méthode qui ajoute l'heure de passage à la tournee
     */
    private void ajouteHeureDePassage(){
        int vitesse = 15; //15 km.h-1
        LocalTime heureActuelle = heureDepart;
        for(CheminEntreEtape cee : listeChemins){
            cee.getEtapeDepart().setHeureDePassage(heureActuelle);
            heureActuelle = heureActuelle.plusSeconds(cee.getEtapeDepart().getDureeEtape() + (int)Math.ceil((cee.distance / 1000. /(vitesse))*3600));
            cee.getEtapeArrivee().setHeureDePassage(heureActuelle);
        }
    }

    /**
     * méthode qui renvoie true si le dépot de la requête ajoutée est bien placée après la collecte
     * @param collecte: étape de collacte
     * @param precedentDepot: étape qui préède le dépot
     * @return: true si le dépot de la requête ajoutée est bien placée après la collecte
     */
    public boolean collectePrecedeDepot(Etape collecte, Etape precedentDepot) {
        boolean depotTrouvee = false;
        for (CheminEntreEtape chemin : listeChemins){
            double departLongitude = chemin.getEtapeDepart().getLongitude();
            double departLatitude = chemin.getEtapeDepart().getLatitude();
            double arriveeLongitude = chemin.getEtapeDepart().getLongitude();
            double arriveeLatitude = chemin.getEtapeDepart().getLatitude();
            if(collecte.getLongitude() == precedentDepot.getLongitude() && collecte.getLatitude() == precedentDepot.getLatitude()){
                return true;
            }
            if(collecte.getLongitude() == departLongitude && collecte.getLatitude() == departLatitude && precedentDepot.getLongitude() == arriveeLongitude && precedentDepot.getLatitude() == arriveeLatitude){
                return true;
            }
            if(departLongitude == precedentDepot.getLongitude() && departLatitude == precedentDepot.getLatitude()){
                depotTrouvee = true;
            }
            if(arriveeLongitude == precedentDepot.getLongitude() && arriveeLatitude == precedentDepot.getLatitude()){
                depotTrouvee = true;
            }
            if(!depotTrouvee && (departLongitude == collecte.getLongitude() || departLatitude == collecte.getLatitude())){
                return true;
            }
            else if(!depotTrouvee && (arriveeLongitude == collecte.getLongitude() || arriveeLatitude == collecte.getLatitude())){
                return true;
            }
        }
        return false;
    }

    /**
     * méthode qui permet d'ajouter une requete à la liste des requêtes
     * @param requete: requête à placer
     */
    public void ajoutRequete(Requete requete){
        listeRequetes.add(requete);
    }

}
