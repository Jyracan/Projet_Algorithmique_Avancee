
import utils.*;
import java.util.HashSet;

public class StupidSolver {
    /**
     * Algorithme pour trouver le meilleur chemin en les essayants tous !!!
     * @param setPoint
     */
    private static void essayerTout(HashSet<Point> setPoint){
        int cptNbSol =0;
        int nbPoints = setPoint.size();
        boolean[] X = new boolean[nbPoints-2]; // On crée notre tableau de point, on ne met pas le premier ni le dernier point dans ce tableau
        Point [] points = UtilsSolver.transformToTab(setPoint);
        int cpt = 0;
        double scoreTMP;
        double scoreOpt= UtilsSolver.calculCout(X,points);
        boolean[] Xopt = X.clone();
        while (cpt < X.length){
            for (int i = cpt; i<X.length -1; i++){
                X[i]= true;
                scoreTMP = UtilsSolver.calculCout(X,points);
                cptNbSol++;
                if(scoreTMP < scoreOpt) {
                    scoreOpt = scoreTMP;
                    Xopt = X.clone();
                }
            }
            for (int i = 0 ; i< X.length-1; i++){ // On re met a jour le tableau de booléen
                X[i] = false;
            }
            cpt ++;
        }
        System.out.println("###\nFin de l'algoritme\nMeilleur coût trouvé : " + scoreOpt + " \n###");
        System.out.println("Nombre de solution trouvé : " + cptNbSol + " " +cpt);
        UtilsSolver.visualizeRes(points, Xopt, scoreOpt);
    }

    public static void main(String[] args) {
        HashSet<Point> setPoint = (HashSet<Point>) Parser.recuperePoints();    //On récupère un set de point
        essayerTout(setPoint);
    }
}

