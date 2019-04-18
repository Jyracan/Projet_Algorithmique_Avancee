
import utils.*;
import java.util.HashSet;
import java.util.Iterator;

public class StupidSolver {
    /**
     * Algorithme pour trouver le meilleur chemin en les essayants tous !!!
     * @param setPoint
     */
    private static void essayerTout(HashSet<Point> setPoint){
        int nbPoints = setPoint.size();
        boolean[] X = new boolean[nbPoints-2]; // On crée notre tableau de point, on ne met pas le premier ni le dernier point dans ce tableau
        Point [] points = utilsSolver.transformToTab(setPoint);
        int cpt = 0;
        double scoreTMP;
        double scoreOpt= utilsSolver.calculCout(X,points);
        boolean[] Xopt = null;
        while (cpt <nbPoints){
            for (int i = cpt; i<nbPoints-2; i++){
                X[i]= true;
                scoreTMP = utilsSolver.calculCout(X,points);
                if(scoreTMP < scoreOpt) {
                    scoreOpt = scoreTMP;
                    Xopt = X.clone();
                    System.err.println("Xopt à été maj = " + Xopt);
                }
            }
            for (int i = 0 ; i< X.length-1; i++){ // On re met a jour le tableau de booléen
                X[i] = false;
            }
            cpt ++;
        }
        System.out.println("###\nFin de l'algoritme\nMeilleur coût trouvé : " + scoreOpt + " \n###");
        utilsSolver.visualizeRes(points, Xopt, scoreOpt);

    }

    public static void main(String[] args) {
        HashSet<Point> setPoint = (HashSet<Point>) Parser.recuperePoints();    //On récupère un set de point
        essayerTout(setPoint);
    }
}

