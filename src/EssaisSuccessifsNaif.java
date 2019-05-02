/***
 * Classe résolvant le problème d'approximation d'un ensemble par une ligne brisée via un méthode par essais successifs de type solution à tous
 */

import utils.*;

import java.util.*;


public class EssaisSuccessifsNaif {

    private static int[] Si; // ensemble des valeurs possibles pour chaque point (pris dans la ligne brisée ou non)
    private static double scoreOpt; // score de la solution optimale
    private static boolean[] Xopt; // tableau représentant la solution optimale

    /**
     * procédure calculant l'approximation optimale d'un ensemble S de points par une ligne brisée.
     * @param points, tableau représentant l'ensemble S des points
     * @param X, solution courante du problème
     * @param i, numéro du point que l'on considère
     */
    private static void appligibri(Point[] points,boolean[] X,int i) {
        Si = new int[]{0,1};
        for(int xi: Si){
            if(satisfaisant(i,X)){ // on vérifie que xi est un choix acceptable
                enregistrer(i,xi,X); // X[i] = xi et on met à jour les variables d'état du problème
                if(soltrouvee(i,X)){ // on vérifie si l'on a une solution complète
                    if(optimal(X,points)){ // on vérifie si cette solution est optimale
                        Xopt = X.clone(); // on enregistre la solution
                    }
                }else {
                    appligibri(points,X,i+1);
                }
                defaire(i,X); // inverse de enregistrer, on rétablit les variables d'état du problème
            }
        }
    }

    /**
     * vérification de la condition satisfaisant, le point i courant appartient à l'ensemble S et n'a pas encore été considéré
     * @param i
     * @param X
     * @return boolean, résultat du test
     */
    private static boolean satisfaisant(int i,boolean[] X){
        return (i <= X.length);
    }

    /**
     * Ajoute le point courant ou non à la ligne brisée
     * @param i
     * @param xi
     * @param X
     */
    private static void enregistrer(int i,int xi,boolean[] X){
            X[i-1] = (xi == 1);
    }

    /**
     * Enlève le point considéré de la ligne brisée
     * @param i
     * @param X
     */
    private static void defaire(int i, boolean[] X){
        X[i-1] = false;
    }

    /**
     * vérifie si une solution a été trouvée, ie on a parcouru tous les points de l'ensemble S
     * @param i
     * @param X
     * @return boolean, résultat du test
     */
    private static boolean soltrouvee(int i,boolean[] X) {
        return (i == X.length);
    }

    /**
     * Vérifie si la solution actuelle est meilleure que la solution optimale.
     * Si c'est le cas, on met à jour la solution optimale et le score optimal
     * @param X
     * @param points
     * @return boolean, résultat du test
     */
    private static boolean optimal(boolean [] X, Point[] points){
        if(UtilsSolver.calculCout(X,points) < scoreOpt){
            scoreOpt = UtilsSolver.calculCout(X,points);
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        HashSet<Point> setPoint = (HashSet<Point>) Parser.recuperePoints();    //On récupère un set de points

        Point[] points;
        points = UtilsSolver.transformToTab(setPoint); // on construit un tableau contenant les points que l'on a récupéré

        boolean[] X =  new boolean[setPoint.size() - 2]; // initilisation
        for(boolean x : X) x = false;
        Xopt = X.clone();

        scoreOpt = UtilsSolver.calculCout(X,points); // on initialise le coût optimal à la valeur du coup de la ligne brisée ne contenant que le premier et le dernier point

        long tpsDebut = System.currentTimeMillis();
        appligibri(points,X,1);
        long tpsFin= System.currentTimeMillis();
        System.out.println("Temps total d'exécution :" + (tpsFin - tpsDebut) + " ms.");

        UtilsSolver.visualizeRes(points,Xopt,scoreOpt); //tracé de la solution optimale et affichage du score correspondant
    }
}