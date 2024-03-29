package Model;

import Algorithmie.Astar;
import Algorithmie.Astar2;
import Exceptions.CommandeImpossibleException;
import Observer.Observable;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Une tournée à deux états, elle est soit ordonnée, soit chargée.
 * Une tournée chargée est composée d'une liste de requettes, obtenue après le chargement des requettes.
 * Une tournée chargée possède en plus une liste ordonnée d'étapes, obtenue suite au calcul de la tournée.
 * Remarque : à sa création, la tournée n'est ni ordonnée ni chargée.
 */
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
    public Tournee() {
        listeRequetes = new ArrayList<>();
        listeChemins = new ArrayList<>();
        etapeDepart = null;
        heureDepart = null;
        tourneeEstChargee = false;
        tourneeEstOrdonee = false;
    }

    /**
     * Méthode qui renvoie l'adresse de départ
     *
     * @return l'Etape de départ
     */
    public Etape getEtapeDepart() {
        return etapeDepart;
    }

    /**
     * Méthode qui renvoie l'heure de départ
     *
     * @return l'heure de départ
     */
    public LocalTime getDateDepart() {
        return heureDepart;
    }

    /**
     * Méthode qui renvoie la liste des requêtes
     *
     * @return la liste des Requete
     */
    public List<Requete> getListeRequetes() {
        return listeRequetes;
    }

    /**
     * Méthode qui renvoie la liste des chemins entre étapes
     *
     * @return la liste des CheminEntreEtape
     */
    public List<CheminEntreEtape> getListeChemins() {
        return listeChemins;
    }

    /**
     * Méthode qui renvoie true si la tournee est chargée
     *
     * @return true si la Tournee est chargée
     */
    public boolean getTourneeEstChargee() {
        return tourneeEstChargee;
    }

    /**
     * Méthode qui renvoie true si la tournee est ordonnée
     *
     * @return true si la Tournee est ordonnée
     */
    public boolean getTourneeEstOrdonee() {
        return tourneeEstOrdonee;
    }

    /**
     * Méthode qui place l'adresse de départ
     *
     * @param etapeDepart: Etape de départ
     */
    public void setEtapeDepart(Etape etapeDepart) {
        this.etapeDepart = etapeDepart;
    }

    /**
     * Méthode qui place l'heure de départ
     *
     * @param heureDepart:cheure de départ
     */
    public void setHeureDepart(LocalTime heureDepart) {
        this.heureDepart = heureDepart;
    }

    /**
     * Méthode qui place la liste de requêtes
     *
     * @param listeRequetes: liste des requêtes à placer
     */
    public void setListeRequetes(List<Requete> listeRequetes) {
        this.listeRequetes = listeRequetes;
    }

    /**
     * Méthode qui place la liste de chemins entre étape
     *
     * @param listeChemins: liste des chemins entre étape
     */
    public void setListeChemins(List<CheminEntreEtape> listeChemins) {
        this.listeChemins = listeChemins;
    }

    /**
     * Méthode qui place le boolean si la tournee est chargée
     *
     * @param tourneeEstChargee: boolean tournée est chargée
     */
    public void setTourneeEstChargee(boolean tourneeEstChargee) {
        this.tourneeEstChargee = tourneeEstChargee;
    }

    /**
     * Méthode qui place le boolean si la tournee est ordonée
     *
     * @param tourneeEstOrdonee: boolean tournée est ordonnée
     */
    public void setTourneeEstOrdonee(boolean tourneeEstOrdonee) {
        this.tourneeEstOrdonee = tourneeEstOrdonee;
    }

    /**
     * Méthode qui supprime une requete de la liste des requetes et de la liste de chemins entre etape.
     * Pour cela recalcul le plus court chemin entre l'étape précédente et l'étape suivante du point de collecte et de depot de la requete
     *
     * @param requeteASupprimer: la requete qui doit etre supprimee
     * @param carte:             la carte a partir de laquelle les chemins ont ete calcules
     */
    public void supprimerRequete(Requete requeteASupprimer, Carte carte) throws CommandeImpossibleException {

        int index = 0;
        int indexEtapePrecedentCollecte = 0;
        int indexEtapePrecedentDepot = 0;
        CheminEntreEtape cheminEntreEtapePrecedentCollecteEtCollecte = null;
        CheminEntreEtape cheminEntreCollecteEtEtapeSuivantCollecte = null;
        CheminEntreEtape cheminEntreEtapePrecedentDepotEtDepot = null;
        CheminEntreEtape cheminEntreDepotEtEtapeSuivantDepot = null;
        //Trouve les étapes précédentes et suivantes des 2 étapes de la requête
        for (CheminEntreEtape cheminEntreEtape : listeChemins) {

            //Cherche l'étape précédente de l'étape de collecte de la requette
            if (cheminEntreEtape.getEtapeArrivee().getIdAdresse().equals(requeteASupprimer.getEtapeCollecte().getIdAdresse())) {
                cheminEntreEtapePrecedentCollecteEtCollecte = cheminEntreEtape;
                indexEtapePrecedentCollecte = index;
            }
            //Cherche l'étape suivante de l'étape de collecte de la requette
            if (cheminEntreEtape.getEtapeDepart().getIdAdresse().equals(requeteASupprimer.getEtapeCollecte().getIdAdresse()))
                cheminEntreCollecteEtEtapeSuivantCollecte = cheminEntreEtape;

            //Cherche l'étape précédente de l'étape de depot de la requette
            if (cheminEntreEtape.getEtapeArrivee().getIdAdresse().equals(requeteASupprimer.getEtapeDepot().getIdAdresse())) {
                cheminEntreEtapePrecedentDepotEtDepot = cheminEntreEtape;
                indexEtapePrecedentDepot = index;
            }
            //Cherche l'étape suivante de l'étape de depot de la requette
            if (cheminEntreEtape.getEtapeDepart().getIdAdresse().equals(requeteASupprimer.getEtapeDepot().getIdAdresse()))
                cheminEntreDepotEtEtapeSuivantDepot = cheminEntreEtape;

            index++;
        }

        //Si un des chemins n'est pas touvée, renvoi une erreure
        if (cheminEntreEtapePrecedentCollecteEtCollecte == null || cheminEntreCollecteEtEtapeSuivantCollecte == null || cheminEntreEtapePrecedentDepotEtDepot == null || cheminEntreDepotEtEtapeSuivantDepot == null)
            throw new CommandeImpossibleException("Impossible de supprimer la requete : la tournée est mal formée");

        Astar astar = new Astar2(carte);
        //Calcul le plus court chemin entre l'étape précente et l'étape suivante du point de collecte
        CheminEntreEtape nouveauCheminEntrePrecedentEtSuivantCollecte;
        CheminEntreEtape nouveauCheminEntrePrecedentEtSuivantDepot;
        CheminEntreEtape nouveauCheminEntrePrecedentCollecteEtSuivantDepot;
        //Si le chemin entre collecte et suivant collecte n'est pas le même que celui entre precedent depot et depot on créer 2 nouveaux chemin
        if (!((cheminEntreCollecteEtEtapeSuivantCollecte.getEtapeArrivee().getIdAdresse().equals(cheminEntreEtapePrecedentDepotEtDepot.getEtapeArrivee().getIdAdresse())) && (cheminEntreCollecteEtEtapeSuivantCollecte.getEtapeDepart().getIdAdresse().equals(cheminEntreEtapePrecedentDepotEtDepot.getEtapeDepart().getIdAdresse())))) {
            nouveauCheminEntrePrecedentEtSuivantCollecte = astar.chercherCheminEntreEtape(cheminEntreEtapePrecedentCollecteEtCollecte.getEtapeDepart(), cheminEntreCollecteEtEtapeSuivantCollecte.getEtapeArrivee());
            nouveauCheminEntrePrecedentEtSuivantDepot = astar.chercherCheminEntreEtape(cheminEntreEtapePrecedentDepotEtDepot.getEtapeDepart(), cheminEntreDepotEtEtapeSuivantDepot.getEtapeArrivee());
            //Supprime le chemin entre l'étape précédente de la collecte et la collecte
            listeChemins.remove(indexEtapePrecedentCollecte);
            //Supprime le chemin entre l'étape collecte et la suivante de collecte
            listeChemins.remove(indexEtapePrecedentCollecte);
            //Ajoute le chemin entre l'étape precedent collecte et la suivante de collecte
            listeChemins.add(indexEtapePrecedentCollecte, nouveauCheminEntrePrecedentEtSuivantCollecte);
            //Supprime le chemin entre l'étape précédente du depot et le depot
            listeChemins.remove(indexEtapePrecedentDepot - 1);
            //Supprime le chemin entre l'étape de depot et la suivante de depot
            listeChemins.remove(indexEtapePrecedentDepot - 1);
            //Ajoute le chemin entre l'étape precedent depot et la suivante de depot
            listeChemins.add(indexEtapePrecedentDepot - 1, nouveauCheminEntrePrecedentEtSuivantDepot);
            //Si les points de collecte et depot se suivent dans l'itinéraire on créer un seul nouveau chemin
        } else {
            nouveauCheminEntrePrecedentCollecteEtSuivantDepot = astar.chercherCheminEntreEtape(cheminEntreEtapePrecedentCollecteEtCollecte.getEtapeDepart(), cheminEntreDepotEtEtapeSuivantDepot.getEtapeArrivee());
            //Supprime le chemin entre l'étape précédente de la collecte et la collecte
            listeChemins.remove(indexEtapePrecedentCollecte);
            //Supprime le chemin entre l'étape la collecte et le depot
            listeChemins.remove(indexEtapePrecedentCollecte);
            //Supprime le chemin entre l'étape de depot et l'étape suivante de depot
            listeChemins.remove(indexEtapePrecedentCollecte);
            //Ajoute le chemin entre l'étape precedent collecte et la suivante de depot
            listeChemins.add(indexEtapePrecedentCollecte, nouveauCheminEntrePrecedentCollecteEtSuivantDepot);
        }

        //Supprime la requête
        listeRequetes.remove(requeteASupprimer);

        ajouteHeureDePassage();

        //Notifie les observateurs que la tournee a été mofifié
        notifyObservers(this);
    }

    /**
     * Méthode qui emplace tous les attibuts de la tournee par celle d'une autre tournee
     *
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
     * Méthode qui vide la tournee
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
     * Méthode qui affiche la tournée
     *
     * @return la Tournee this
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
     * Méthode qui permet d'obtenir une étape de la tournée grâce à l'id
     *
     * @param id: id de l'étape à rechercher
     * @return l'Etape en fonction de l'id entrée dans la liste desCheminEntreEtape
     */
    public Etape obtenirEtapeParId(Long id) {
        Etape etapeCherchee = null;
        //On parcourt tous les chemins de la tournée et on vérifie si l'id des étapes de départ ou arrivée correspond à notre id de l'étape en paramêtre
        for (CheminEntreEtape chemin : listeChemins) {
            if (chemin.getEtapeDepart().getIdAdresse().equals(id)) {
                etapeCherchee = chemin.getEtapeDepart();
                return etapeCherchee;
            }
            if (chemin.getEtapeArrivee().getIdAdresse().equals(id)) {
                etapeCherchee = chemin.getEtapeArrivee();
                return etapeCherchee;
            }
        }
        //On retourne l'étape trouvée
        return etapeCherchee;
    }

    /**
     * Méthode qui permet de renvoyer la distance entre les deux adresses 1 et 2
     *
     * @param a Adresse 1
     * @param b Adresse 2
     * @return la distance
     */
    public double distanceEntreAdresse(Adresse a, Adresse b) {
        return Math.pow(a.getLongitude() - b.getLongitude(), 2) + Math.pow(a.getLatitude() - b.getLatitude(), 2);
    }

    /**
     * méthode qui renvoie l'Adresse la plus proche en fonction de l'Adresse en paramètre.
     * @param a: adresse récupéré au clic de l'utilisateur
     * @return: l'Adresse recherchée
     */
    public Adresse rechercheEtape (Adresse a){
        double distanceMin = Double.MAX_VALUE;
        double distanceCollecte;
        double distanceDepot;
        Adresse plusProche = null;
        double distanceEtapeDepart = distanceEntreAdresse(a, etapeDepart);
        //On vérifie si l'étape la plus proche est l'étape de collecte (placée) précédemment cliquée
        if(distanceEtapeDepart < distanceMin){
            distanceMin = distanceEtapeDepart;
            plusProche = etapeDepart;
        }
        //On parcours aussi la liste de toutes les requetes de la tournée pour vérifier pour chacune si le départ ou l'arrivée
        //est l'étape la plus proche du clique sur la carte (pour ajouter le dépot)
        for(Requete r : listeRequetes){
            Adresse collecte = new Adresse (r.getEtapeCollecte().getLatitude(),r.getEtapeCollecte().getLongitude(),r.getEtapeCollecte().getIdAdresse());
            if(r.getEtapeDepot() != null) {
                Adresse depot = new Adresse(r.getEtapeDepot().getLatitude(), r.getEtapeDepot().getLongitude(), r.getEtapeDepot().getIdAdresse());
                distanceDepot = distanceEntreAdresse(a, depot);
                if( distanceDepot < distanceMin){
                    distanceMin = distanceDepot;
                    plusProche = depot;
                }
            }
            distanceCollecte = distanceEntreAdresse(a, collecte);

            if( distanceCollecte < distanceMin){
                distanceMin = distanceCollecte;
                plusProche = collecte;
            }
        }

        return plusProche;
    }

    /**
     * Méthode qui renvoie l'Adresse la plus proche en fonction de l'Adresse en paramètre.
     *
     * @param a:              adresse cliquée
     * @param collectePlacee: nouvelle adresse de collecte.
     * @return l'Adresse entre l'Adresse cliquée et l'Adresse déjà placée
     */
    public Adresse rechercheEtape(Adresse a, Adresse collectePlacee) {
        double distanceMin = Double.MAX_VALUE;
        double distanceCollecte;
        double distanceDepot;
        double distanceCollectePlacee = Double.MAX_VALUE;
        Adresse plusProche = null;
        if (collectePlacee != null) {
            distanceCollectePlacee = distanceEntreAdresse(a, collectePlacee);
        }
        //On vérifie si l'étape la plus proche est l'étape de collecte (placée) précédemment cliquée
        if (distanceCollectePlacee < distanceMin) {
            distanceMin = distanceCollectePlacee;
            plusProche = collectePlacee;
        }
        //On parcours aussi la liste de toutes les requetes de la tournée pour vérifier pour chacune si le départ ou l'arrivée
        //est l'étape la plus proche du clique sur la carte (pour ajouter le dépot)
        for (Requete requete : listeRequetes) {
            Adresse collecte = new Adresse(requete.getEtapeCollecte().getLatitude(), requete.getEtapeCollecte().getLongitude(), requete.getEtapeCollecte().getIdAdresse());
            Adresse depot = new Adresse(requete.getEtapeDepot().getLatitude(), requete.getEtapeDepot().getLongitude(), requete.getEtapeDepot().getIdAdresse());
            distanceCollecte = distanceEntreAdresse(a, collecte);
            distanceDepot = distanceEntreAdresse(a, depot);

            if (distanceCollecte < distanceMin) {
                distanceMin = distanceCollecte;
                plusProche = collecte;
            }
            if (distanceDepot < distanceMin) {
                distanceMin = distanceDepot;
                plusProche = depot;
            }
        }

        return plusProche;
    }

    /**
     * Méthode qui ajoute le chemin lors de l'ajout de requête
     *
     * @param adresse:   étape à ajouter
     * @param precedent: étape précédente
     * @param carte:     permet d'obtenir les informations en temps réel sur la carte
     */
    public void ajoutChemin(Etape adresse, Etape precedent, Carte carte) {
        //On instancie un objet Astar2 pour le calcul du plus court chemin entre deux étapes
        Astar2 astar = new Astar2(carte);
        CheminEntreEtape precedentActuel = astar.chercherCheminEntreEtape(precedent, adresse);
        int index = 0;
        Etape suivant = null;
        //Pour chaque chemin on vérifie si le départ est le précédent de notre étape que l'on ajoute
        //Si c'est le cas on récuper l'arrivée de ce chemin et on supprimer ce chemin
        //Ensuite on crée un nouveau chemin entre l'étape précédent notre étape et notre étape
        //Et on crée un nouveau chemin entre l'étape que l'on ajoute et l'arrivée de l'ancien chemin supprimé
        for (CheminEntreEtape chemin : listeChemins) {
            if (chemin.getEtapeDepart().getIdAdresse().equals(precedent.getIdAdresse())) {
                suivant = chemin.getEtapeArrivee();
                listeChemins.remove(chemin);
                break;
            }
            index++;
        }
        listeChemins.add(index, precedentActuel);
        CheminEntreEtape actuelSuivant = astar.chercherCheminEntreEtape(adresse, suivant);
        listeChemins.add(index + 1, actuelSuivant);
        //On met bien à jour les heures de passages obligatoire à cause des nouveaux chemins ajoutés
        ajouteHeureDePassage();
    }

    /**
     * Méthode qui ajoute l'heure de passage à la tournee
     */
    private void ajouteHeureDePassage() {
        int vitesse = 15; //15 km.h-1
        LocalTime heureActuelle = heureDepart;
        //Pour chaque chemin de la liste on calcule l'heure de passage et on l'ajoute pour chaque étape
        for (CheminEntreEtape cee : listeChemins) {
            //On met l'heure de départ du chemin
            cee.getEtapeDepart().setHeureDePassage(heureActuelle);
            //On calcule le temps de trajet entre départ et arrivée du chemin
            heureActuelle = heureActuelle.plusSeconds(cee.getEtapeDepart().getDureeEtape() + (int) Math.ceil((cee.distance / 1000. / (vitesse)) * 3600));
            //On met l'heure d'arrivée du chemin
            cee.getEtapeArrivee().setHeureDePassage(heureActuelle);
        }
    }

    /**
     * Méthode qui renvoie true si le dépot de la requête ajoutée est bien placée après la collecte
     *
     * @param collecte:       étape de collacte
     * @param precedentDepot: étape qui préède le dépot
     * @return true si le dépot de la requête ajoutée est bien placée après la collecte
     */
    public boolean collectePrecedeDepot(Etape collecte, Etape precedentDepot, Etape precedentCollecte) {
        //Si le precedent de depot est la collecte on valide
        if (precedentDepot.getIdAdresse().equals(collecte.getIdAdresse())) {
            return true;
        }

        for (CheminEntreEtape chemin : listeChemins) {
            //Si le depart du chemin est le precedent de collecte
            if (chemin.getEtapeDepart().getIdAdresse().equals(precedentCollecte.getIdAdresse())) {
                //Si l'arrivée de ce chemin est le précedent de dépot on valide
                if (chemin.getEtapeArrivee().getIdAdresse().equals(precedentDepot.getIdAdresse())) {
                    return true;
                }
                //Si le départ de ce chemin est aussi le précedent de dépot on refuse
                if (chemin.getEtapeDepart().getIdAdresse().equals(precedentDepot.getIdAdresse())) {
                    return false;
                }
            }
            //Si l'arrivée du chemin est le precedent de collecte
            if (chemin.getEtapeArrivee().getIdAdresse().equals(precedentCollecte.getIdAdresse())) {
                //Si l'arrivée de ce chemin est aussi le précedent de dépot on refuse
                if (chemin.getEtapeArrivee().getIdAdresse().equals(precedentDepot.getIdAdresse())) {
                    return false;
                }
                //Si le départ de ce chemin est le précedent de dépot on refuse
                if (chemin.getEtapeDepart().getIdAdresse().equals(precedentDepot.getIdAdresse())) {
                    return false;
                }
            }
            //Si on trouve le précédent de depot sur le chemin mais pas le précédent de collecte on refuse
            if (chemin.getEtapeArrivee().getIdAdresse().equals(precedentDepot.getIdAdresse()) || chemin.getEtapeDepart().getIdAdresse().equals(precedentDepot.getIdAdresse())) {
                if (!(chemin.getEtapeArrivee().getIdAdresse().equals(precedentCollecte.getIdAdresse())) || !(chemin.getEtapeDepart().getIdAdresse().equals(precedentCollecte.getIdAdresse()))) {
                    return false;
                }
            }
            //Si on trouve le précédent de collecte sur le chemin mais pas le précédent de depot on valide
            if (chemin.getEtapeArrivee().getIdAdresse().equals(precedentCollecte.getIdAdresse()) || chemin.getEtapeDepart().getIdAdresse().equals(precedentCollecte.getIdAdresse())) {
                if (!(chemin.getEtapeArrivee().getIdAdresse().equals(precedentDepot.getIdAdresse())) || !(chemin.getEtapeDepart().getIdAdresse().equals(precedentDepot.getIdAdresse()))) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Méthode qui permet d'ajouter une requete à la liste des requêtes et les nouveaux chemins reliant cette requete
     *
     * @param requete: requête à placer
     */
    public void ajoutRequete(Requete requete, Etape precedentCollecte, Etape precedentDepot, Carte carte) {
        //On supprimer les chemins nécessaire et créeons les nouveaux chemins pour connecter la nouvelle requete à la tournée
        ajoutChemin(requete.getEtapeCollecte(), precedentCollecte, carte);
        ajoutChemin(requete.getEtapeDepot(), precedentDepot, carte);
        //On ajoute ensuite la requete à la liste des requetes de la tournée
        listeRequetes.add(requete);
    }

    /**
     * Méthode qui permet d'ajouter une requete à la liste des requêtes
     *
     * @param requete: requête à placer
     */
    public void ajouterRequete(Requete requete) {
        //On ajoute une requete dans la liste des requetes de la tournée
        listeRequetes.add(requete);
    }

    /**
     *
     * @param requete : requete dont on cherche le précedent de la collecte de la requete dans la liste des chemins
     * @return
     */
    public Etape precedentCollecte(Requete requete) {
        //On parcourt la liste des chemins pour trouver l'étape précédent notre étape de collecte dans la requete
        for (CheminEntreEtape chemin : listeChemins) {
            if (chemin.getEtapeArrivee().getIdAdresse().equals(requete.getEtapeCollecte().getIdAdresse())) {
                return chemin.getEtapeDepart();
            }
        }
        return null;
    }

    /**
     *
     * @param requete : requete dont on cherche le précedent du depot de la requete dans la liste des chemins
     * @return
     */
    public Etape precedentDepot(Requete requete) {
        //On parcourt la liste des chemins pour trouver l'étape précédent notre étape de dépot dans la requete
        for (CheminEntreEtape chemin : listeChemins) {
            if (chemin.getEtapeArrivee().getIdAdresse().equals(requete.getEtapeDepot().getIdAdresse())) {
                return chemin.getEtapeDepart();
            }
        }
        return null;
    }

}
