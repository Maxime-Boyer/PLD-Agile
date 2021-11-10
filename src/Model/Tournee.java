package Model;

import Algorithmie.Astar;
import Algorithmie.Astar2;
import Exceptions.CommandeImpossibleException;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import Observer.Observable;


public class Tournee extends Observable {
    private Adresse adresseDepart;
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
        adresseDepart = null;
        heureDepart = null;
        tourneeEstChargee = false;
        tourneeEstOrdonee = false;
    }

    /**
     * methode qui renvoie l'adresse de départ
     * @return
     */
    public Adresse getAdresseDepart() {
        return adresseDepart;
    }

    /**
     * methode qui renvoie l'heure de départ
     * @return
     */
    public LocalTime getDateDepart() {
        return heureDepart;
    }

    /**
     * methode qui renvoie la liste des requêtes
     * @return
     */
    public List<Requete> getListeRequetes() {
        return listeRequetes;
    }

    /**
     * methode qui renvoie la liste des chemins entre étapes
     * @return
     */
    public List<CheminEntreEtape> getListeChemins() {
        return listeChemins;
    }

    /**
     * methode qui renvoie true si la tournee est chargée
     * @return
     */
    public boolean getTourneeEstChargee() {
        return tourneeEstChargee;
    }

    /**
     * methode qui renvoie true si la tournee est ordonnée
     * @return
     */
    public boolean getTourneeEstOrdonee() {
        return tourneeEstOrdonee;
    }

    /**
     * methode qui place l'adresse de départ
     * @param adresseDepart: adresse de départ
     */
    public void setAdresseDepart(Adresse adresseDepart) {
        this.adresseDepart = adresseDepart;
    }

    /**
     * methode qui place l'heure de départ
     * @param heureDepart:cheure de départ
     */
    public void setHeureDepart(LocalTime heureDepart) {
        this.heureDepart = heureDepart;
    }

    /**
     * methode qui place la liste de requêtes
     * @param listeRequetes: liste des requêtes à placer
     */
    public void setListeRequetes(List<Requete> listeRequetes) {
        this.listeRequetes = listeRequetes;
    }
    /**
     * methode qui place la liste de chemins entre étape
     * @param listeChemins: liste des chemins entre étape
     */
    public void setListeChemins(List<CheminEntreEtape> listeChemins) {
        this.listeChemins = listeChemins;
    }

    /**
     * methode qui place le boolean si la tournee est chargée
     * @param tourneeEstChargee: boolean tournée est chargée
     */
    public void setTourneeEstChargee(boolean tourneeEstChargee) {
        this.tourneeEstChargee = tourneeEstChargee;
    }

    /**
     * methode qui place le boolean si la tournee est ordonée
     * @param tourneeEstOrdonee: boolean tournée est ordonnée
     */
    public void setTourneeEstOrdonee(boolean tourneeEstOrdonee) {
        this.tourneeEstOrdonee = tourneeEstOrdonee;
    }

    /**
     * methode qui supprime une requete de la liste des requetes et de la liste de chemins entre etape.
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
     * methode qui emplace tous les attibuts de la tournee par celle d'une autre tournee
     * @param touneeACloner la tournee de laquelle les attributs sont récupérés
     */
    public void clone(Tournee touneeACloner) {
        this.adresseDepart = touneeACloner.adresseDepart;
        this.heureDepart = touneeACloner.heureDepart;
        this.listeRequetes = touneeACloner.listeRequetes;
        this.listeChemins = touneeACloner.listeChemins;
        this.tourneeEstChargee = touneeACloner.tourneeEstChargee;
        this.tourneeEstOrdonee = touneeACloner.tourneeEstOrdonee;
    }

    /**
     * methode qui vide la tournee
     */
    public void reset() {
        listeRequetes = new ArrayList<>();
        listeChemins = new ArrayList<>();
        adresseDepart = null;
        heureDepart = null;
        tourneeEstChargee = false;
        tourneeEstOrdonee = false;

        //Notifie les observateurs que la tournee a été mofifié
        notifyObservers(this);
    }

    /**
     * methode qui affiche la tournée
     * @return
     */
    @Override
    public String toString() {
        return "Tournee{" +
                "adresseDepart=" + adresseDepart +
                ", heureDepart=" + heureDepart +
                ", listeRequetes=" + listeRequetes +
                ", listeChemins=" + listeChemins +
                '}';
    }

    /**
     * methode qui permet d'obtenir une étape de la tournée grâce à l'id
     * @param id: id de l'étape à rechercher
     * @return
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
     * methode qui permet de renvoyer la distance entre les deux adresses 1 et 2
     * @param a:Adresse 1
     * @param b: Adresse é
     * @return
     */
    public double distanceEntreAdresse(Adresse a, Adresse b){
        double distance = Math.pow(a.getLongitude() - b.getLongitude(), 2) + Math.pow(a.getLatitude() - b.getLatitude(), 2);
        return distance;
    }

    /**
     * methode qui renvoie l'etape la plus proche en focntion de l'adresse en paramètre.
     * @param a: adresse récupéré au clic de l'utilisateur
     * @return
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
     * methode qui permet de rechercher l'étape entre l'adresse cliquée et l'adresse déjà placée sur le carte dans l'état ajout requête
     * @param a: adresse cliquée
     * @param collectePlacee: nouvelle adresse de collecte
     * @return
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
     * methode qui ajoute le chemin lors de l'ajout de requête
     * @param adresse: étape à ajouter
     * @param precedent: étape précédente
     * @param carte: permet d'obtenir les informations en temps réel sur la carte
     */
    public void ajoutChemin(Etape adresse, Etape precedent, Carte carte){
        int index = 0;
        Etape suivant = null;
        for (CheminEntreEtape chemin : listeChemins){
            if(chemin.getEtapeDepart().getIdAdresse() == precedent.getIdAdresse()){
                suivant = new Etape(chemin.getEtapeArrivee().getLatitude(),chemin.getEtapeArrivee().getLongitude(),chemin.getEtapeArrivee().getIdAdresse(),chemin.getEtapeArrivee().getDureeEtape(),chemin.getEtapeArrivee().getHeureDePassage());
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
     * methode qui ajoute l'heure de passage à la tournee
     */
    private void ajouteHeureDePassage(){
        int vitesse = 15; //15 km.h-1
        LocalTime heureActuelle = heureDepart;
        for(CheminEntreEtape cee : listeChemins){
            cee.getEtapeDepart().setHeureDePassage(heureActuelle);
            heureActuelle = heureActuelle.plusSeconds(cee.getEtapeDepart().getDureeEtape() + ((cee.distance/(vitesse*1000))*60));
            cee.getEtapeArrivee().setHeureDePassage(heureActuelle);
        }
    }

    /**
     * methode qui renvoie true si le dépot de la requête ajoutée est bien placée après la collecte
     * @param collecte: étape de collacte
     * @param precedentDepot: étape qui préède le dépot
     * @return
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
     * methode qui permet d'ajouter une requete à la liste des requêtes
     * @param requete: requête à placer
     */
    public void ajoutRequete(Requete requete){
        listeRequetes.add(requete);
    }

}
