package utils;

/**
 *
 * @author Equipe pédagogique d'algorithmique avancées 2013-2014
 *
 */
public class Point {
    /**
     * Un point est composé d'une abscisse de type flottant et d'une ordonnée de type double
     */
    private double x, y;

    /**
     * Constructeur de la classe utils.Point.
     * @param abs Valeur de l'abscisse du point à créer.
     * @param ord Valeur de l'ordonnée du point à créer.
     */
    public Point(double abs, double ord){
        x = abs;
        y = ord;
    }


    /**
     * Récupération de la valeur de l'abscisse du point.
     * @return la valeur de l'abscisse du point.
     */
    public double getx(){return x;}

    /**
     * Récupération de la valeur de l'ordonnée du point.
     * @return la valeur de l'ordonnée du point.
     */
    public double gety(){return y;}


    /**
     * Réinitialisation de l'abscisse.
     * @param xIn La nouvelle valeur de l'abscisse.
     */
    public void setx(double xIn){x=xIn;}

    /**
     * Réinitialisation de l'ordonnée.
     * @param yIn La nouvelle valeur de l'ordonnée.
     */
    public void sety(double yIn){x=yIn;}

    /**
     * Réinitialisation du point.
     * @param abs La nouvelle valeur de l'abscisse.
     * @param ord La nouvelle valeur de l'ordonnée.
     */
    public void initialise(double abs, double ord) {
        x = abs;
        y = ord;
    }

    @Override
    public String toString(){
        return "("+x+","+y+")";
    }

    /**
     * Deux points sont égaux s'ils ont la même valeur pour abscisse et la même valeur pour ordonnée.
     * @param obj Objet avec lequel comparer le point courant.
     * @return vrai si les points sont égaux, faux sinon.
     */
    @Override
    public boolean equals(Object obj){
        if (obj==this){return true;}
        if (!(obj instanceof Point)){return false;}
        Point p= (Point) obj;
        return ((x == p.x) && (y == p.y));
    }


    /**
     * Affichage de la valeur de this.toString sur la sortie standard.
     */
    public void affiche() {
        System.out.println(this.toString());
    }

    /**
     * Calcul de la projection orthogonale de ce point sur une ligne
     * @param ligne
     * @return La projection orthogonale de ce point à un ligne
     */
    public Point projectionOrthogonale(Ligne ligne)
    {
        if(this.equals(ligne.getp1())) {
            return this;
        }
        else if (this.equals(ligne.getp2())) {
            return this;
        }
        else {
            double a = (ligne.getp2()).getx() - ligne.getp1().getx();
            double b = (ligne.getp2()).gety() - ligne.getp1().gety();

            double n = ( a * ( this.getx() - ligne.getp2().getx() ) ) + ( b*(this.gety() - (ligne.getp2() ).gety()));
            n /= (Math.pow(2, a) + Math.pow(b, 2));

            Point H=new Point(ligne.getp2().getx() + a * n, (ligne.getp2()).gety() + n * b);
            return H;
        }
    }

    /**
     * Calcul de la distance algébrique entre 2 points
     * @param point
     * @return La distance entre deux points
     */
    public double distance (Point point){
        double x=Math.pow(((point.getx())-(this.getx())), 2.0);
        double y=Math.pow(((point.gety())-(this.gety())), 2.0);
        return Math.sqrt(x+y);
    }
}

