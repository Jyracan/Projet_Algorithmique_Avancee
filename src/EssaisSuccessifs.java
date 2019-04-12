import utils.Ligne;
import utils.Parser;
import utils.Point;
import utils.Visu;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class EssaisSuccessifs {

    public static final double PENALITE = 1.5;

    public static void appligbri (HashSet<Point> setPoint){
        int nbPoints = setPoint.size();
        boolean[] X = new boolean[nbPoints-2]; // On crée notre tableau de point, on ne met pas le premier ni le dernier point dans ce tableau
        for (boolean point: X) point = false;   //On dit que tous les points ne sont pas dans le segment de droite

        Point [] points = transformToTab(setPoint);
        int cpt = 0;
        double scoreTMP = 0;
        double scoreOpt = 0;
        boolean[] Xopt = X;

        while (cpt <nbPoints){
            for (int i = cpt; i<nbPoints-2; i++){
                X[i]= true;
                scoreTMP = calculCout(X,points);
                if(scoreTMP < scoreOpt) {scoreOpt = scoreTMP;  Xopt=X;}
            }
            for (boolean point: X) point = false;
            cpt ++;
        }
        visualizeRes(points, Xopt, scoreOpt);

    }

    private static Point[] transformToTab (HashSet<Point> setPoint){
        int nbPoints = setPoint.size();
        //Initialisation du tableau de point pour les obtenirs plus facilement
        Point[] points = new Point[nbPoints];
        Iterator<Point> it = setPoint.iterator();
        Point tmp;
        while(it.hasNext()){
            tmp = it.next();
            System.out.println("Ajout du point X=" + tmp.getx() + " Y=" + tmp.gety());
            points[(int)tmp.getx() - 1] = tmp;
        }
        return points;
    }

    private static void visualizeRes (Point[] points, boolean[] present, double score){
        Set<Point> p = new HashSet<Point>();
        for (Point point: points){
            p.add(point);
        }
        Set<Ligne> segmentDroite = new HashSet<Ligne>();
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
        Visu v = new Visu(p,segmentDroite,"Solution de score "+ score);
    }

    private static double calculCout (boolean[] present,  Point[] points){    // TODO : Refactor !
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
                cout += pointHorsLigne.size() * PENALITE; // TODO : vérifier que l'ajout de la pénalité est bien appliqué
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


        cout += pointHorsLigne.size() * PENALITE;
        System.out.println();
        for (Point point : pointHorsLigne){
            cout += segmentCourant.distance(point);
            System.out.println("Distance du point n°" + point.getx() + " avec le segment courant = " + segmentCourant.distance(point));
        }

        //visualizeRes(points, segmentDroite, cout);
        return cout;
    }

    public static void main(String[] args) {
        HashSet<Point> setPoint = (HashSet<Point>) Parser.recuperePoints();    //On récupère un set de point

        appligbri(setPoint);


    }
}