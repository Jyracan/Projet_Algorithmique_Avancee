import utils.*;

import java.util.*;


public class EssaisSuccessifs {

    private static HashMap<Double,boolean[]> solutionsPossibles;

    private static void appligibri_opt(Point[] points,Ligne dernierSegment,double scoreOpt,boolean[] X,int i) {
        //System.out.println("dernier segment d'extrémités : "+dernierSegment.getp1() + " "+ dernierSegment.getp2());
        if(i <= X.length){
            if(satisfaisant(i,dernierSegment,points)){
                enregistrer(i,X);
                //System.out.println("ajout d'un nouveau point à la ligne brisée");
                Ligne nouveauDernierSegment = new Ligne(dernierSegment.getp2(),points[i]);
                if(elagage(points,X,scoreOpt)) {
                    if (i == X.length) {
                        scoreOpt = UtilsSolver.calculCout(X,points);
                        solutionsPossibles.put(scoreOpt, X.clone());
                    } else {
                        appligibri_opt(points, nouveauDernierSegment, scoreOpt,X,i+1);
                    }
                }
                defaire(i,X);
                appligibri_opt(points,dernierSegment,scoreOpt,X,i+1);
            }else{
                if(i == X.length){
                    solutionsPossibles.put(UtilsSolver.calculCout(X,points), X.clone());
                }else {
                    appligibri_opt(points,dernierSegment,scoreOpt,X,i+1);
                }
            }
        }else{
                solutionsPossibles.put(UtilsSolver.calculCout(X, points), X.clone());
        }
    }

    private static void enregistrer(int i,boolean[] X){
        X[i-1] = true;
    }

    private static void defaire(int i, boolean[] X){
        X[i-1] = false;
    }

    private static boolean soltrouvee(int i,boolean[] X){
        return (i == X.length);
    }


    private static boolean satisfaisant(int i,Ligne dernierSegment, Point[] points){
        return dernierSegment.distance(points[i]) > UtilsSolver.PENALITE;
    }

    private static boolean elagage(Point[] points, boolean[] X,double scoreOpt){
       return (UtilsSolver.calculCout(X,points) < scoreOpt);
    }


    public static void main(String[] args) {
        HashSet<Point> setPoint = (HashSet<Point>) Parser.recuperePoints();    //On récupère un set de point

        Point[] points;
        points = UtilsSolver.transformToTab(setPoint);

        boolean[] X =  new boolean[setPoint.size() - 2];

        solutionsPossibles = new HashMap<>();
        double scoreOpt = UtilsSolver.calculCout(X,points);

        solutionsPossibles.put(scoreOpt,X.clone());
        Ligne dernierSegment = new Ligne(points[setPoint.size() - 1],points[0]);
        System.out.println("Début du programme :  dernier segment d'extrémités "+ dernierSegment.getp1() + " et "+dernierSegment.getp2());

        appligibri_opt(points,dernierSegment,scoreOpt,X,1);
        System.out.println("Le programme a trouvé "+solutionsPossibles.size()+" solutions possibles");

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