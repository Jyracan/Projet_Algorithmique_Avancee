import utils.Parser;
import utils.Point;
import utils.UtilsSolver;

import java.util.HashSet;

public class ProgDynamique {

    public static double[] [] SDtable; // Table des valeurs de SD pour gagner du temps.
    public static boolean[]  estPresent;
    public static double[][] approxoptTable;

    public static double[][] calculSD(Point[] points){
        int n = points.length;
        double[][] res = new double[n][n];
        for(int i =0 ; i<n; i++){
            for(int j=0; j<n; j++){
                if(j>=i){
                    res[i][j] = UtilsSolver.SD(i,j,points);
                }
            }
        }
        return res;
    }

    public static void remplissageApproxopt(int n){
        double res=0;
        double tmp;
        int ptIntermediaire=1;
        approxoptTable = new double[n][n];
        for(int i=0 ; i<n; i++){
            for(int j=0; j<n; j++){
                if(i==j){
                    approxoptTable[i][j]=0;
                }else if(j>=i){
                    for (int l=i+1; l<n; l++){
                        tmp = SDtable[i][l] + approxoptTable[l][j];
                        if(tmp<res){
                            res=tmp;
                            ptIntermediaire=l;
                        }
                    }
                    approxoptTable[i][j]=res;
                    estPresent[ptIntermediaire-1] = true;
                }
            }
        }

    }

    public static void main(String[] args) {
        HashSet<Point> setPoint = (HashSet<Point>) Parser.recuperePoints();    //On récupère un set de point
        Point[] points = UtilsSolver.transformToTab(setPoint);
        int n = points.length;
        SDtable=calculSD(points);
        estPresent = new boolean[n];
        remplissageApproxopt(n);
        double score=approxoptTable[n-1][1];
        for (int cpt=2; cpt<n; cpt++) {
            if(approxoptTable[n-1][cpt]<score) score=approxoptTable[n-1][cpt];
        }
        UtilsSolver.visualizeRes (points, estPresent, score);
    }
}
