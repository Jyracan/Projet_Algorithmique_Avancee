import utils.Ligne;
import utils.Parser;
import utils.Point;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class EssaisSuccessifs {

    public static final double PENALITE = 1.5;

    public static void appligbri (Set<Point> points){

    }

    public static double calculCout (boolean[] present, Point[] points){    // TODO : Refactor !
        double cout = 0;
        Point pointCourant = points[0];
        Point pointSuivant;
        Ligne segmentCourant;
        Set<Point> pointHorsLigne = new HashSet<Point>();
        Set<Ligne> segmentDroite = new HashSet<Ligne>();
        int cpt=1;
        for (boolean isPresent : present){
            if(isPresent){
                pointSuivant = points[cpt];
                segmentCourant = new Ligne(pointCourant, pointSuivant);
                segmentDroite.add(segmentCourant);
                cout += pointHorsLigne.size() * PENALITE;
                for (Point point : pointHorsLigne){
                    System.out.println(segmentCourant.distance(point));
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
        cout += pointHorsLigne.size() * PENALITE;
        System.out.println();
        for (Point point : pointHorsLigne){
            cout += segmentCourant.distance(point);
        }
        return cout;
    }

    public static void main(String[] args) {
        HashSet<Point> setPoint = (HashSet<Point>) Parser.recuperePoints();    //On récupère un set de point
        int nbPoints = setPoint.size();
        boolean[] X = new boolean[nbPoints-2]; // On crée notre tableau de point, on ne met pas le premier ni le dernier point dans ce tableau
        for (boolean point: X) point = false;   //On dit que tous les points ne sont pas dans le segment de droite

        //Initialisation du tableau de point pour les obtenirs plus facilement
        Point[] points = new Point[nbPoints];
        Iterator<Point> it = setPoint.iterator();
        Point tmp;
        while(it.hasNext()){
            tmp = it.next();
            System.out.println("Ajout du point X=" + tmp.getx() + " Y=" + tmp.gety());
            points[(int)tmp.getx() - 1] = tmp;
        }
        X[2]=true;

        System.out.println("###\nCalcul du cout " + calculCout(X, points));
    }
}