import utils.*;

import java.util.*;

import static com.sun.org.apache.xml.internal.security.keys.keyresolver.KeyResolver.iterator;

public class EssaisSuccessifs {

    private static HashMap<Double,boolean[]> solutionsPossibles;
    private static int cpt = 0;

    public static void appligibri(Point[] points,boolean[] X,int i) {
        int[] Si = new int[]{0,1};
        for(int xi: Si){
            if(satisfaisant(i,X)){
                enregistrer(i,xi,X);
                if(soltrouvee(i,X)){
                    cpt++;
                    solutionsPossibles.put(UtilsSolver.calculCout(X,points),X.clone());
                }else {
                    appligibri(points,X,i+1);
                }
                defaire(i,X); // ici ne sert à rien car il n'y a pas de mauvaise solution
            }
        }
    }

    public static boolean satisfaisant(int i,boolean[] X){
        if(i <= X.length){
            return true;
        }
        return false;
    }

    public static void enregistrer(int i,int xi,boolean[] X){
        if(xi == 1){
            X[i-1] = true;
        }
        else{
            X[i-1] = false;
        }
    }

    public static void defaire(int i, boolean[] X){
        X[i-1] = false;
    }

    public static boolean soltrouvee(int i,boolean[] X){
        if(i == X.length){
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        HashSet<Point> setPoint = (HashSet<Point>) Parser.recuperePoints();    //On récupère un set de point

        Point[] points;
        points = UtilsSolver.transformToTab(setPoint);

        boolean[] X =  new boolean[setPoint.size() - 2];
        for(boolean b: X) b = false;

        double dernierPointExplore =  points[0].getx();
        double scoreOpt = UtilsSolver.calculCout(X,points);
        solutionsPossibles = new HashMap<>();

        appligibri(points,X,1);
        System.out.println("Le programme a trouvé "+solutionsPossibles.size()+" solutions possibles, en calculant "+cpt+" combinaisons différentes");

        double meilleurScore = UtilsSolver.calculCout(X,points);; //on initialise le meilleur score à la pire valeur possible
        boolean[] Xopt;

        Set<Double> keys = solutionsPossibles.keySet();
        Iterator<Double> it = keys.iterator();
        while (it.hasNext()){
            double scoreCourant = it.next();
            if(scoreCourant < meilleurScore){
                meilleurScore = scoreCourant;
            }
        }
        Xopt = solutionsPossibles.get(meilleurScore);
        UtilsSolver.visualizeRes(points,Xopt,meilleurScore);
    }
}