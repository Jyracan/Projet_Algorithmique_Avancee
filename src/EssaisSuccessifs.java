import utils.*;

import java.util.*;


public class EssaisSuccessifs {

    private static HashMap<Double,boolean[]> solutionsPossibles;
    private static int cpt = 0;
    private static Ligne dernierSegment;
    private static double scoreOpt;

    private static void appligibri_opt(Point[] points,boolean[] X,int i) {
        if(i <= X.length){
            if(satisfaisant(i,X) && elagage(points[i])) {
                X[i-1]=true;
                Point point = dernierSegment.getp1();
                dernierSegment = new Ligne(dernierSegment.getp2(),points[i]);
                if(optimal(points,X)){
                    scoreOpt = UtilsSolver.calculCout(X,points);
                    if(i == X.length){
                        cpt++;
                        solutionsPossibles.put(scoreOpt,X.clone());
                    }else{
                        appligibri_opt(points,X,i+1);
                    }
                }
               X[i-1] = false;
                dernierSegment = new Ligne(point,dernierSegment.getp1());
                appligibri_opt(points,X,i+1);
            }else {
                X[i-1] = false;
                if(i == X.length && optimal(points,X)){
                    cpt++;
                    solutionsPossibles.put(scoreOpt,X.clone());
                }
                appligibri_opt(points,X,i+1);
            }
        }
    }

    private static boolean satisfaisant(int i,boolean[] X){
        return (i <= X.length);
    }

    private static boolean optimal(Point[] points, boolean[] X){
        return (UtilsSolver.calculCout(X,points) <= scoreOpt);
    }

    private static boolean elagage(Point point ){
        return (dernierSegment.distance(point) > 2* UtilsSolver.PENALITE);
    }

    public static void main(String[] args) {
        HashSet<Point> setPoint = (HashSet<Point>) Parser.recuperePoints();    //On récupère un set de point

        Point[] points;
        points = UtilsSolver.transformToTab(setPoint);

        boolean[] X =  new boolean[setPoint.size() - 2];

        dernierSegment = new Ligne(points[0],points[setPoint.size()-1]);
        solutionsPossibles = new HashMap<>();
        scoreOpt = UtilsSolver.calculCout(X,points);

        appligibri_opt(points,X,1);
        System.out.println("Le programme a trouvé "+solutionsPossibles.size()+" solutions possibles, en calculant "+cpt+" combinaisons différentes");

        double meilleurScore = UtilsSolver.calculCout(X,points); //on initialise le meilleur score à la pire valeur possible
        boolean[] Xopt;

        Set<Double> keys = solutionsPossibles.keySet();
        for (double scoreCourant : keys) {
            if (scoreCourant < meilleurScore) {
                meilleurScore = scoreCourant;
            }
        }
        Xopt = solutionsPossibles.get(meilleurScore);
        UtilsSolver.visualizeRes(points,Xopt,meilleurScore);
    }
}