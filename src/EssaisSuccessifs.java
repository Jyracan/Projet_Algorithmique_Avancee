import utils.*;

import java.util.*;

import static com.sun.org.apache.xml.internal.security.keys.keyresolver.KeyResolver.iterator;

public class EssaisSuccessifs {

    private static HashMap<Double,boolean[]> solutionsPossibles;


    private static void  appligbri_rec(Point[] points,boolean[] X, double dernierPointExplore,double scoreOpt,int i){

        if(points[i].getx() > dernierPointExplore && i <= X.length) { // satisfaisant(xi) : xi n'a pas encore été considéré
           dernierPointExplore = points[i].getx();
           X[i-1] =true;
           System.out.println("score optimal : "+scoreOpt);
           for(boolean b: X) System.out.print(b+" ");
           System.out.println("\n"+"score en prenant le point i+1 : "+ utilsSolver.calculCout(X,points));
           if(utilsSolver.calculCout(X,points) < scoreOpt){
               scoreOpt = utilsSolver.calculCout(X,points);
                if(i == X.length){
                    utilsSolver.visualizeRes(points,X,scoreOpt);
                }else {
                    appligbri_rec(points,X,dernierPointExplore,scoreOpt,i+1);
                }
               X[i-1] = false;
               scoreOpt = utilsSolver.calculCout(X,points);
               //System.out.println("Retour en arrière avec i :"+i+"et X : ");
               //for(boolean b: X) System.out.print(b+" ");
               appligbri_rec(points,X,dernierPointExplore,scoreOpt,i+1);

           }else{
               X[i-1] = false;
               if(i == X.length){
                   utilsSolver.visualizeRes(points,X,scoreOpt);
               }else {
                   appligbri_rec(points,X,dernierPointExplore,scoreOpt,i+1);
               }
           }
        }
    }

    private static void appligbri_elagage(Point[] points,boolean[] X,double scoreOpt, int i){
        if(i <= X.length){
            X[i-1] = true;
            if(utilsSolver.calculCout(X,points) < scoreOpt){
                double score = utilsSolver.calculCout(X,points);
                    if(i == X.length){
                        //for(boolean b: X) System.out.print(b+" ");
                        //System.out.println(score + "\n");
                        solutionsPossibles.put(score,X.clone());
                        //visualizeRes(points,X,scoreOpt);
                    }else{
                        appligbri_elagage(points,X,score,i+1);
                    }
                X[i-1] = false;
                score = utilsSolver.calculCout(X,points);
                appligbri_elagage(points,X,score,i+1);
            }else{
                X[i-1] = false;
                appligbri_elagage(points,X,scoreOpt,i+1);
            }
        }
    }

    public static void main(String[] args) {
        HashSet<Point> setPoint = (HashSet<Point>) Parser.recuperePoints();    //On récupère un set de point

        Point[] points;
        points = utilsSolver.transformToTab(setPoint);

        boolean[] X =  new boolean[setPoint.size() - 2];
        for(boolean b: X) b = false;

        double dernierPointExplore =  points[0].getx();
        double scoreOpt = utilsSolver.calculCout(X,points);
        solutionsPossibles = new HashMap<>();

        appligbri_rec(points,X, dernierPointExplore,scoreOpt,1); // TODO : Pourquoi c'était commenté ?

        appligbri_elagage(points,X,scoreOpt,1);
        System.out.println("Le programme a trouvé "+solutionsPossibles.size()+" solutions possibles");

        double meilleurScore = scoreOpt; //on initialise le meilleur score à la pire valeur possible
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
        utilsSolver.visualizeRes(points,Xopt,meilleurScore);

    }
}