package jeu;

/**
 * Classe décrivant les objets du jeu
 */
public class Element {

    /**
     * Coordonné x d'un elt
     */
    private int x;

    /**
     * Coordonné y d'un elt
     */
    private int y;

    /**
     * Constructeur d'un elt
     * @param x positif
     * @param y positif
     */
    public Element(int x, int y) {
        if( x > 0 && y > 0){
            this.x = x;
            this.y = y;
        }
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    /**
     * Setter
     * @param x nouvelle coordonnée positive
     */
    public void setX(int x) {
        if( x > 0 ) {
            this.x = x;
        }
    }

    /**
     * Setter
     * @param y nouvelle coordonnée positive
     */
    public void setY(int y) {
        if( y > 0 ) {
            this.y = y;
        }
    }

}
