package jeu;

import java.util.ArrayList;

/**
 * Classe destinée à contenir une liste de plusieurs élements en jeu
 */
public class ListeElements {

    /**
     * Une liste d'élement fonctionnera par le biais d'un ArrayList
     */
    public ArrayList<Element> liste;

    public ListeElements() {
        this.liste = new ArrayList<Element>();
    }

    public void ajouter(Element e){
        this.liste.add(e);
    }

    /**
     * Récupère l'élément situé à la position donnée s'il existe
     * @param x Coordonnée x
     * @param y Coordonnée y
     * @return L'élément à cette position ou null si aucun élément n'est trouvé
     */
    public Element getElement(int x, int y) {
        // Parcourir tous les éléments de la liste
        for (Element e : liste) {
            // Vérifier si un élément a les coordonnées recherchées
            if (e.getX() == x && e.getY() == y) {
                return e;  // Retourner l'élément trouvé
            }
        }
        // Si aucun élément n'est trouvé à ces coordonnées, retourner null
        return null;
    }

    public Element[] getElements() {
        return liste.toArray(new Element[liste.size()]);
    }
    public int taille() {return this.liste.size();}
}
