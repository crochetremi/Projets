package jeu;

/**
 * Classe représentant le labyrinthe et décrivant les murs
 */
public class Labyrinthe {

    /**
     * Pour un x et un y donnés, murs[x][y] vaut true si et seulement si la case (x,y) est un mur.
     */
    boolean[][] murs;

    /**
     * Constante représentant un mur
     */
    public final static char MUR = '#';

    /**
     * Constante représentant une caisse
     */
    public final static char CAISSE = '$';

    /**
     * Constante représentant un personnage
     */
    public final static char PJ = '@';

    /**
     * Constante représentant un dépot
     */
    public final static char DEPOT = '.';

    /**
     * Constante représentant un espace vide
     */
    public final static char VIDE = ' ';

    public Labyrinthe(int largeur, int hauteur) {
        this.murs = new boolean[largeur][hauteur];
    }

    public boolean etreMur(int x, int y) {
        return murs[x][y];
    }
    
    public boolean[][] getMurs() {
        return this.murs;
    }

    public void setMurs(boolean[][] m){
        this.murs = m;
    }

    public void setMur(int x, int y){this.murs[x][y] = true;}

}
