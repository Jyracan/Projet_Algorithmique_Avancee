package utils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class UtilsSolver {

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

        cout += segmentDroite.size() * PENALITE;
        for (Point point : pointHorsLigne){
            cout += segmentCourant.distance(point);
        }
        return cout;
    }

    /**
     * Fonction pour calculer SD utile
     * @param i indice du premier point du segment
     * @param j indice du deuxième point du segment
     * @param points tableau de points
     * @return La somme des distances des points d'indice entre i et j à la droite formé par i et j
     */
    public static double SD (int i, int j, Point[] points){
        Ligne ligne = new Ligne(points[i],points[j]);
        double res =0;
        for(int cpt = i+1; cpt < j; cpt ++){
            res += ligne.distance(points[cpt]);
        }
        return res;
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

    public static Point[] transformToTab (HashSet<Point> setPoint){
        int nbPoints = setPoint.size();
        //Initialisation du tableau de point pour les obtenirs plus facilement
        Point[] points = new Point[nbPoints];
        Iterator<Point> it = setPoint.iterator();
        Point tmp;
        while(it.hasNext()){
            tmp = it.next();
            points[(int)tmp.getx() - 1] = tmp;
        }
        return points;
    }
}
