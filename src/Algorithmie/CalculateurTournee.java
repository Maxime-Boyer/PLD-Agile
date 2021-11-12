package Algorithmie;

import Exceptions.AStarImpossibleException;
import Model.Carte;
import Model.CheminEntreEtape;
import Model.Etape;
import Model.Tournee;

import java.time.LocalTime;
import java.util.HashMap;

public class CalculateurTournee {

    private Carte carte;
    private Tournee tournee;

    /**
     * Constructeur de CalculateurTournee
     *
     * @param carte   La carte
     * @param tournee La liste des requêtes souhaitée
     */
    public CalculateurTournee(Carte carte, Tournee tournee) {

        //Recuperation des informations
        this.carte = carte;
        this.tournee = tournee;
    }

    /**
     * Methode qui calcul la tournée passant par l'ensemble des étapes
     *
     * @throws AStarImpossibleException Erreur du calcul de chemin entre deux étapes
     */
    public void calculerTournee(int tempsMaxCalcul) throws AStarImpossibleException {

        HashMap<Long, HashMap<Long, CheminEntreEtape>> grapheCompletDesEtapes = calculerGrapheCompletDesEtapes(new Astar2(carte));

        TSP4 tsp4 = new TSP4(carte, tournee, grapheCompletDesEtapes, tempsMaxCalcul * 1000);
        tsp4.chercherSolution();

        ajouteHeureDePassage(tournee);

        //Passe la tournee à ordonne et notifie l'observer que l'objet tournée a été modifié
        tournee.setTourneeEstOrdonee(true);
        tournee.notifyObservers(tournee);

    }


    /**
     * Methode qui calcul le graphe complet de l'ensemble des étapes
     *
     * @param astar Le Astar utilisé pour calculer le plus court court chemin entre deux étapes
     * @return le graphe complet de l'ensemble des étapes
     * @throws AStarImpossibleException Erreur du calcul de chemin entre deux étapes
     */
    public HashMap<Long, HashMap<Long, CheminEntreEtape>> calculerGrapheCompletDesEtapes(Astar astar) throws AStarImpossibleException {
        //Astar1 astar = new Astar1(carte);

        // HashMap< idAresseDepart, HashMap<idAresseArrivee, CheminEntreEtape> >
        HashMap<Long, HashMap<Long, CheminEntreEtape>> grapheCompletDesEtapes = new HashMap<>();

        //Boucler pour construire le graphe complet
        for (int iDepart = 0; iDepart < tournee.getListeRequetes().size() * 2 + 1; iDepart++) {
            Etape etapeDepart;
            if (iDepart == tournee.getListeRequetes().size() * 2) {
                etapeDepart = tournee.getEtapeDepart();
            } else {
                if (iDepart % 2 == 0) {
                    etapeDepart = tournee.getListeRequetes().get(iDepart / 2).getEtapeCollecte();
                } else {
                    etapeDepart = tournee.getListeRequetes().get(iDepart / 2).getEtapeDepot();
                }
            }

            HashMap<Long, CheminEntreEtape> listeCheminEntreEtape = new HashMap<>();
            for (int arr = 0; arr < tournee.getListeRequetes().size() * 2 + 1; arr++) {
                if (arr != iDepart) {
                    Etape etapeArrivee;
                    if (arr == tournee.getListeRequetes().size() * 2) {
                        etapeArrivee = tournee.getEtapeDepart();
                    } else {
                        if (arr % 2 == 0) {
                            etapeArrivee = tournee.getListeRequetes().get(arr / 2).getEtapeCollecte();
                        } else {
                            etapeArrivee = tournee.getListeRequetes().get(arr / 2).getEtapeDepot();
                        }
                    }

                    CheminEntreEtape nouveauChemin = astar.chercherCheminEntreEtape(etapeDepart, etapeArrivee);
                    if (nouveauChemin == null) {
                        throw new AStarImpossibleException("Les adresses " + etapeDepart.getIdAdresse() + " et " + etapeArrivee.getIdAdresse() + " renvoient un chemin null.");
                    }
                    listeCheminEntreEtape.put(etapeArrivee.getIdAdresse(), nouveauChemin);
                }
            }

            grapheCompletDesEtapes.put(etapeDepart.getIdAdresse(), listeCheminEntreEtape);
        }

        return grapheCompletDesEtapes;
    }

    /**
     * Methode qui permet d'ajouter l'heure de passage au Etape de la tournée
     *
     * @param tournee: tournee à modifier
     */
    private void ajouteHeureDePassage(Tournee tournee) {
        int vitesse = 15; //15 km.h-1
        LocalTime heureActuelle = tournee.getDateDepart();
        for (CheminEntreEtape cee : tournee.getListeChemins()) {
            cee.getEtapeDepart().setHeureDePassage(heureActuelle);
            heureActuelle = heureActuelle.plusSeconds(cee.getEtapeDepart().getDureeEtape() + (int) Math.ceil((cee.distance / 1000. / (vitesse)) * 3600));
            cee.getEtapeArrivee().setHeureDePassage(heureActuelle);
        }
    }
}
