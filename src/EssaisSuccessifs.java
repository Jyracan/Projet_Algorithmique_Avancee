import utils.*;

import java.util.*;


public class EssaisSuccessifs {

    private static HashMap<Double,boolean[]> solutionsPossibles;
    private static int cpt = 0;
    private static double scoreOpt;

    private static void appligibri_opt(Point[] points,boolean[] X,int i) {
       int[] Si = new int[]{0,1};
       for(int xi: Si){
           if (satisfaisant(i, X)){
               enregistrer(i,xi,X);
               cpt++;
               if (soltrouvee(i,X)){
                   solutionsPossibles.put(scoreOpt,X.clone());
               }else {
                   if(elagage(points,X)){
                       scoreOpt = UtilsSolver.calculCout(X,points);
                       appligibri_opt(points,X,i+1);
                   }
               }
               defaire(i,X);
           }
       }
    }

    private static void enregistrer(int i,int xi,boolean[] X){
        X[i-1] = (xi == 1);
    }

    private static void defaire(int i, boolean[] X){
        X[i-1] = false;
    }

    private static boolean soltrouvee(int i,boolean[] X){
        return (i == X.length);
    }


    private static boolean satisfaisant(int i,boolean[] X){
        return (i <= X.length);
    }

    private static boolean elagage(Point[] points, boolean[] X){
       return (UtilsSolver.calculCout(X,points) <= scoreOpt);
    }


    public static void main(String[] args) {
        HashSet<Point> setPoint = (HashSet<Point>) Parser.recuperePoints();    //On récupère un set de point

        Point[] points;
        points = UtilsSolver.transformToTab(setPoint);

        boolean[] X =  new boolean[setPoint.size() - 2];

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