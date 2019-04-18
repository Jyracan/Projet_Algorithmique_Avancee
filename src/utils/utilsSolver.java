package utils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class utilsSolver {

    public static final double PENALITE = 1.5;


    public static double calculCout (boolean[] present,  Point[] points){    // TODO : Refactor !
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
                    System.out.println("Distance du point n°" + (int)point.getx() + " avec le segment courant = " + segmentCourant.distance(point));
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
        System.out.println("Cout actuel = " + cout);
        for (Point point : pointHorsLigne){
            cout += segmentCourant.distance(point);
            System.out.println("Distance du point n°" + point.getx() + " avec le segment courant = " + segmentCourant.distance(point));
        }
        return cout;
    }


    public static void visualizeRes (Point[] points, boolean[] present, double score){
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
}
