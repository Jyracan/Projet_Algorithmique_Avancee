import utils.Ligne;
import utils.Parser;
import utils.Point;
import utils.Visu;

import java.util.*;

import static com.sun.org.apache.xml.internal.security.keys.keyresolver.KeyResolver.iterator;

public class EssaisSuccessifs {

    private static final double PENALITE = 1.5;
    private static HashMap<Double,boolean[]> solutionsPossibles;

    /**
     * Algorithme pour trouver le meilleur chemin en les essayants tous !!!
     * @param setPoint
     */
    private static void appligbri (HashSet<Point> setPoint){
        int nbPoints = setPoint.size();
        boolean[] X = new boolean[nbPoints-2]; // On crée notre tableau de point, on ne met pas le premier ni le dernier point dans ce tableau
        Point [] points = transformToTab(setPoint);
        int cpt = 0;
        double scoreTMP;
        double scoreOpt=calculCout(X,points);
        boolean[] Xopt = null;
        while (cpt <nbPoints){
            for (int i = cpt; i<nbPoints-2; i++){
                X[i]= true;
                scoreTMP = calculCout(X,points);
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
        visualizeRes(points, Xopt, scoreOpt);

    }

    private static void  appligbri_rec(Point[] points,boolean[] X, double dernierPointExplore,double scoreOpt,int i){

        if(points[i].getx() > dernierPointExplore && i <= X.length) { // satisfaisant(xi) : xi n'a pas encore été considéré
           dernierPointExplore = points[i].getx();
           X[i-1] =true;
           System.out.println("score optimal : "+scoreOpt);
           for(boolean b: X) System.out.print(b+" ");
           System.out.println("\n"+"score en prenant le point i+1 : "+calculCout(X,points));
           if(calculCout(X,points) < scoreOpt){
               scoreOpt = calculCout(X,points);
                if(i == X.length){
                    visualizeRes(points,X,scoreOpt);
                }else {
                    appligbri_rec(points,X,dernierPointExplore,scoreOpt,i+1);
                }
               X[i-1] = false;
               scoreOpt = calculCout(X,points);
               //System.out.println("Retour en arrière avec i :"+i+"et X : ");
               //for(boolean b: X) System.out.print(b+" ");
               appligbri_rec(points,X,dernierPointExplore,scoreOpt,i+1);

           }else{
               X[i-1] = false;
               if(i == X.length){
                   visualizeRes(points,X,scoreOpt);
               }else {
                   appligbri_rec(points,X,dernierPointExplore,scoreOpt,i+1);
               }
           }
        }
    }

    private static void appligbri_elagage(Point[] points,boolean[] X,double scoreOpt, int i){
        if(i <= X.length){
            X[i-1] = true;
            if(calculCout(X,points) < scoreOpt){
                double score = calculCout(X,points);
                    if(i == X.length){
                        //for(boolean b: X) System.out.print(b+" ");
                        //System.out.println(score + "\n");
                        solutionsPossibles.put(score,X.clone());
                        //visualizeRes(points,X,scoreOpt);
                    }else{
                        appligbri_elagage(points,X,score,i+1);
                    }
                X[i-1] = false;
                score = calculCout(X,points);
                appligbri_elagage(points,X,score,i+1);
            }else{
                X[i-1] = false;
                appligbri_elagage(points,X,scoreOpt,i+1);
            }
        }
    }

    private static Point[] transformToTab (HashSet<Point> setPoint){
        int nbPoints = setPoint.size();
        //Initialisation du tableau de point pour les obtenirs plus facilement
        Point[] points = new Point[nbPoints];
        Iterator<Point> it = setPoint.iterator();
        Point tmp;
        while(it.hasNext()){
            tmp = it.next();
            System.out.println("Ajout du point X= " + tmp.getx() + " Y= " + tmp.gety());
            points[(int)tmp.getx() - 1] = tmp;
        }
        return points;
    }

    private static void visualizeRes (Point[] points, boolean[] present, double score){
        Set<Point> p = new HashSet<>();
        p.addAll(Arrays.asList(points));
        Set<Ligne> segmentDroite = new HashSet<>();
        Point pointSuivant;
        Point pointCourant= points[0];
        Ligne segmentCourant;
        int cpt=1;
        for (boolean isPresent : present){
            if(isPresent){
                pointSuivant = points[cpt];
                segmentCourant = new Ligne(pointCourant, pointSuivant);
                segmentDroite.add(segmentCourant);
                pointCourant = pointSuivant;
            }
            cpt++;
        }
        segmentCourant = new Ligne(pointCourant, points[points.length -1]);
        segmentDroite.add(segmentCourant);
        Visu v = new Visu(p,segmentDroite,"Solution de score " + score);
    }

    private static double calculCout (boolean[] present,  Point[] points){    // TODO : Refactor !
        double cout = 0;
        Point pointCourant = points[0];
        Point pointSuivant;
        Ligne segmentCourant;
        Set<Point> pointHorsLigne = new HashSet<Point>();
        Set<Ligne> segmentDroite = new HashSet<Ligne>();
        int cpt=1;
        int nbPointSegment=2;
        for (boolean isPresent : present){
            if(isPresent){
                nbPointSegment++;
                pointSuivant = points[cpt];
                segmentCourant = new Ligne(pointCourant, pointSuivant);
                segmentDroite.add(segmentCourant);
                for (Point point : pointHorsLigne){
                    //System.out.println("Distance du point n°" + (int)point.getx() + " avec le segment courant = " + segmentCourant.distance(point));
                    cout += segmentCourant.distance(point);
                }
                pointHorsLigne.clear();
                pointCourant = pointSuivant;
            }else{
                pointHorsLigne.add(points[cpt]);
            }
            cpt++;
        }
        segmentCourant = new Ligne(pointCourant, points[points.length-1]);

        cout += nbPointSegment * PENALITE;
        //System.out.println("Cout actuel = " + cout);
        for (Point point : pointHorsLigne){
            cout += segmentCourant.distance(point);
            //System.out.println("Distance du point n°" + point.getx() + " avec le segment courant = " + segmentCourant.distance(point));
        }
        return cout;
    }

    public static void main(String[] args) {
        HashSet<Point> setPoint = (HashSet<Point>) Parser.recuperePoints();    //On récupère un set de point
        //appligbri(setPoint);

        Point[] points;
        points = transformToTab(setPoint);

        boolean[] X =  new boolean[setPoint.size() - 2];
        for(boolean b: X) b = false;

        double dernierPointExplore =  points[0].getx();
        double scoreOpt = calculCout(X,points);
        solutionsPossibles = new HashMap<>();

        //appligbri_rec(points,X, dernierPointExplore,scoreOpt,1);

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
        visualizeRes(points,Xopt,meilleurScore);

    }
}