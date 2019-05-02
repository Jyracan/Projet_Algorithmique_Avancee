/***
 * Classe résolvant le problème d'approximation d'un ensemble par une ligne brisée via un méthode par essais successifs de type solution à tous
 * On ajoute une condition d'élagage pour éviter à avoir à calculer certaines solutions et gagner en temps d'éxécution
 */

import utils.*;

import java.util.*;


public class EssaisSuccessifs {

    private static HashMap<Double,boolean[]> solutionsPossibles; // contient chaque solution trouvée par le programme ainsi que le cout associé à cette solution

    /**
     * procédure calculant les différentes solutions du problème avec élagage de certaines solutions
     * @param points, l'ensmble de points S
     * @param dernierSegment, le dernier segment composant la ligne brisée
     * @param scoreOpt, le meilleur score actuel
     * @param X, solution courante
     * @param i, point courant
     */
    private static void appligibri_elagage(Point[] points, Ligne dernierSegment, double scoreOpt, boolean[] X, int i) {
        if(i <= X.length){
            if(satisfaisant(i,dernierSegment,points)){ //on rajoute une condition supplémentaire
                enregistrer(i,X); // si elle est vérifiée, on ajoute le point à la ligne brisée
                Ligne nouveauDernierSegment = new Ligne(dernierSegment.getp2(),points[i]); // et on met à jour le dernier segment de la ligne brisée
                if(estOptimale(X,points,scoreOpt)) { // on teste si la solution est meilleure que celle optimale
                    if (soltrouvee(i,X)) { // si on a trouvé une solution
                        scoreOpt = UtilsSolver.calculCout(X,points); // on met à jour le score optimal
                        solutionsPossibles.put(scoreOpt, X.clone()); //  on ajoute la solution et son score aux solutions trouvées
                    } else { // sinon on continue à construire notre solution
                        appligibri_elagage(points, nouveauDernierSegment, scoreOpt,X,i+1);
                    }
                }
                defaire(i,X); // si notre solution en ajoutant le point n'est pas optimale, on le retire de la ligne brisée
                appligibri_elagage(points,dernierSegment,scoreOpt,X,i+1); // et on continue la construction de la solution
            }else{ // si la condition n'est pas vérifiée, on ne prend pas le point sur la ligne
                if(i == X.length && estOptimale(X,points,scoreOpt)){ // test de solution trouvée
                    solutionsPossibles.put(UtilsSolver.calculCout(X,points), X.clone());
                }else {// sinon on continue à construire notre solution
                    appligibri_elagage(points,dernierSegment,scoreOpt,X,i+1);
                }
            }
        }else if(i == X.length + 1 && estOptimale(X,points,scoreOpt)){
        // traite le cas d'une solution optimale trouvée après un appel à defaire (i vaut alors X.length +1 à cause de l'appel récursif après le défaire)
                solutionsPossibles.put(UtilsSolver.calculCout(X, points), X.clone());
        }
    }


    /**
     * Vérifie si la solution actuelle est meilleure que la solution optimale.
     * @param X
     * @param points
     * @return boolean, résultat du test
     */
    private static boolean estOptimale(boolean [] X, Point[] points,double scoreOpt){
              return  UtilsSolver.calculCout(X,points) < scoreOpt;
    }

    /**
     * on ajoute le point considéré à la ligne brisée
     * @param i
     * @param X
     */
    private static void enregistrer(int i,boolean[] X){
        X[i-1] = true;
    }

    /**
     * retire le point considéré à la ligne brisée
     * @param i
     * @param X
     */
    private static void defaire(int i, boolean[] X){
        X[i-1] = false;
    }

    /**
     * teste si une solution a été trouvée
     * @param i
     * @param X
     * @return boolean, résultat du test
     */
    private static boolean soltrouvee(int i,boolean[] X){
        return (i == X.length);
    }


    /**
     * teste si la distance du point courant au dernier segment de la ligne brisée est plus grande que la pénalité C
     * @param i
     * @param dernierSegment
     * @param points
     * @return boolean, résultat du test
     */
    private static boolean satisfaisant(int i,Ligne dernierSegment, Point[] points){
        return dernierSegment.distance(points[i]) > UtilsSolver.PENALITE;
    }



    public static void main(String[] args) {
        HashSet<Point> setPoint = (HashSet<Point>) Parser.recuperePoints();    //On récupère un set de point

        Point[] points; // on construit un tableau contenant les points que l'on a récupéré
        points = UtilsSolver.transformToTab(setPoint);

        boolean[] X =  new boolean[setPoint.size() - 2];
        /*for (int i = 0; i < X.length; i ++){
            if(i % 2 != 0){
                X[i] = true;
            }
        }*/

        solutionsPossibles = new HashMap<>();
        double scoreOpt = UtilsSolver.calculCout(X,points); // on initialise le coût optimal à la valeur du coup de la ligne brisée ne contenant que le premier et le dernier point

        solutionsPossibles.put(scoreOpt,X.clone());
        Ligne dernierSegment = new Ligne(points[setPoint.size() - 1],points[0]);
        System.out.println("Début du programme :  dernier segment d'extrémités "+ dernierSegment.getp1() + " et "+dernierSegment.getp2());

        long tpsDebut = System.currentTimeMillis();

        appligibri_elagage(points,dernierSegment,scoreOpt,X,1);

        long tpsFin= System.currentTimeMillis(); //calcul du temps d'execution de la procédure
        System.out.println("Temps total d'exécution :" + (tpsFin - tpsDebut) + " ms.");

        System.out.println("Le programme a trouvé "+solutionsPossibles.size()+" solutions possibles");

        boolean[] Xopt;

        Set<Double> keys = solutionsPossibles.keySet();
        for (double scoreCourant : keys) { // on parcourt l'ensemble des solutions trouvées par la procédure pour prendre celle de score optimal
            if (scoreCourant < scoreOpt) {
                scoreOpt = scoreCourant;
            }
        }
        Xopt = solutionsPossibles.get(scoreOpt); // on l'affiche ainsi que son score
        UtilsSolver.visualizeRes(points,Xopt,scoreOpt);
    }
}