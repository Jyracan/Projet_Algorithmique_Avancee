import utils.Parser;
import utils.Point;
import utils.UtilsSolver;

import java.util.HashSet;

public class ProgDynamique {

    private static double[] [] SDtable; // Table des valeurs de SD pour gagner du temps.
    private static boolean[]  estPresent;   // Structure pour connaître les points appartenant à la droite brisé
    private static double[][] approxoptTable;    // Structure tabulaire utilisé pour la programmation dynamique

    /**
     * Fonction pour remplir SDtable qui est un tableau sotckant SD(i,j) à la position [i][j] pour éviter de devoir le calculer plusieurs fois
     * @param points tableau de points
     */
    private static void calculSD(Point[] points){
        int n = points.length;
        SDtable = new double[n][n];
        for(int i =0 ; i<n; i++){
            for(int j=0; j<n; j++){
                if(j>=i){
                    SDtable[i][j] = UtilsSolver.SD(i,j,points);
                }
            }
        }
        System.out.println("Fin d'initialisation du tableau SD");
        affiche(SDtable);
    }

    /**
     * Permet d'afficher un tableau de double pour le debug !
     * @param tableau tableau à afficher
     */
    private static void affiche (double[][] tableau){
        for (double[] doubles : tableau) {
            for (int j = 0; j < tableau.length; j++) {
                System.out.print(doubles[j] + " | ");
            }
            System.out.println();
        }
    }

    /**
     * Fonction pour remplir la structure tabulaire permettant de calculer la solution optimale via programmation dynamique
     * @param n nombre de point
     */
    private static void remplissageApproxopt(int n){
        System.out.println("Début de la procédure de programmation dynamique");
        double res=-1;
        double tmp;
        int ptIntermediaire=1;
        approxoptTable = new double[n][n];
        for(int i=0 ; i<n; i++){
            for(int j=n-1; j>=0; j--){
                if(i==j){
                    approxoptTable[i][j]=0;
                }else if(i>j){
                    System.out.println("\n  calcul approxopt [" + j +"," +i +"]\n###");
                    for (int l=j+1; l<=i; l++){
                        tmp = (SDtable[j][l] + UtilsSolver.PENALITE) + approxoptTable[l][i];
                        System.out.println("SD["+j+","+l+"] + " + 1.5 + " + approxopt["+l+","+i+"]" );
                        if(res == -1){
                            System.out.println("Premier minimum trouvé " + tmp);
                            res = tmp;
                            ptIntermediaire=l;
                        }else if(tmp<res){
                            System.out.println("Nouveau minimum trouvé " + tmp);
                            res=tmp;
                            ptIntermediaire=l;
                        }
                    }
                    approxoptTable[j][i]=res;
                    estPresent[ptIntermediaire-1] = true;
                    res = -1;
                }
            }
        }
        System.out.println("Fin d'initialisation du tableau d'approximation");
        affiche(approxoptTable);

    }

    public static void main(String[] args) {
        System.out.println("Lancement de la résolution par programmation dynamique");
        HashSet<Point> setPoint = (HashSet<Point>) Parser.recuperePoints();    //On récupère un set de point
        System.out.println("Initialisation des structures de données\n###");
        Point[] points = UtilsSolver.transformToTab(setPoint);
        int n = points.length;
        calculSD(points);
        estPresent = new boolean[n];
        remplissageApproxopt(n);
        double score=approxoptTable[n-1][0];
        UtilsSolver.visualizeRes (points, estPresent, score);
    }
}
