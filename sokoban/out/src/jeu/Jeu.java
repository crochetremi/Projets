package jeu;

/**
 * Classe représentant le jeu et décrivant le labyrinthe, le personnage et l'ensemble des caisses et des lieux de dépôt
 */
public class Jeu {

    /**
     * Constante liée aux déplacement vers le haut
     */
    public final static String HAUT = "HAUT";

    /**
     * Constante liée aux déplacement vers le bas
     */
    public final static String BAS = "BAS";

    /**
     * Constante liée aux déplacement vers la gauche
     */
    public final static String GAUCHE = "GAUCHE";

    /**
     * Constante liée aux déplacement vers la droite
     */
    public final static String DROITE = "DROITE";


    /**
     * Le personnage
     */
    public Perso perso;

    /**
     * Les caisses
     */
    public ListeElements caisses;

    /**
     * Le dépot
     */
    public ListeElements depots;

    /**
     * Le labyrinthe
     */
    public Labyrinthe laby;

    public Jeu(){
        this.perso = null;
        this.caisses = null;
        this.depots = null;
        this.laby = null;
    }

    public Jeu(Perso p, ListeElements caisses, ListeElements depots, Labyrinthe laby) {
        this.perso = p;
        this.caisses = caisses;
        this.depots = depots;
        this.laby = laby;
    }

    /**
     * Retourne le caractère correspondant à la case (x,y)
     * @param x Coordonnée x
     * @param y Coordonnée y
     * @return Le caractère représentant l'élément à cette position
     */
    /**
     * Retourne le caractère correspondant à la case (x,y) du jeu
     * @param x coordonnée x de la case
     * @param y coordonnée y de la case
     * @return le caractère représentant ce qui se trouve sur la case
     */
    public char getChar(int x, int y) {
        // Vérifier si c'est un mur
        if (laby.etreMur(x, y)) {
            return Labyrinthe.MUR;
        }

        // Vérifier si c'est le personnage
        if (perso.getX() == x && perso.getY() == y) {
            return Labyrinthe.PJ;
        }

        // Vérifier si c'est une caisse
        Element caisse = caisses.getElement(x, y);
        if (caisse != null) {
            // Vérifier si la caisse est sur un dépôt
            Element depot = depots.getElement(x, y);
            if (depot != null) {
                return '*'; // Caractère spécial pour une caisse sur un dépôt (non défini dans les constantes)
            }
            return Labyrinthe.CAISSE;
        }

        // Vérifier si c'est un dépôt
        Element depot = depots.getElement(x, y);
        if (depot != null) {
            return Labyrinthe.DEPOT;
        }

        // Sinon c'est une case vide
        return Labyrinthe.VIDE;
    }

    /**
     * Génère une représentation textuelle de l'état du jeu
     * @return Une chaîne de caractères représentant l'état du jeu
     */
    public String jeuToString() {
        StringBuilder sb = new StringBuilder();

        // Récupérer les dimensions du labyrinthe
        int largeur = laby.getMurs().length;
        int hauteur = laby.getMurs()[0].length;

        // Parcourir chaque case du labyrinthe
        for (int y = 0; y < hauteur; y++) {
            for (int x = 0; x < largeur; x++) {
                sb.append(getChar(x, y));
            }
            // Ajouter un retour à la ligne à la fin de chaque ligne
            sb.append("\n");
        }

        return sb.toString();
    }

    public void deplacerPerso(String action) throws ActionInconnueException {
        // Vérifier si l'action est valide
        if (!action.equals(HAUT) && !action.equals(BAS) && !action.equals(GAUCHE) && !action.equals(DROITE)) {
            throw new ActionInconnueException("Action inconnue: " + action);
        }

        // Calculer les coordonnées de la case suivante
        int[] suivant = getSuivant(perso.getX(), perso.getY(), action);
        int suivantX = suivant[0];
        int suivantY = suivant[1];

        // Vérifier si la case suivante est un mur
        if (laby.etreMur(suivantX, suivantY)) {
            // On ne peut pas se déplacer sur un mur
            return;
        }

        // Vérifier si la case suivante contient une caisse
        Element caisseElement = caisses.getElement(suivantX, suivantY);
        if (caisseElement != null) {
            // C'est une caisse, il faut vérifier si on peut la pousser

            // Calculer les coordonnées de la case derrière la caisse
            int[] derriereCaisse = getSuivant(suivantX, suivantY, action);
            int derriereCaisseX = derriereCaisse[0];
            int derriereCaisseY = derriereCaisse[1];

            // Vérifier si la case derrière la caisse est libre (pas de mur ni de caisse)
            if (!laby.etreMur(derriereCaisseX, derriereCaisseY) &&
                    caisses.getElement(derriereCaisseX, derriereCaisseY) == null) {

                // On peut pousser la caisse
                Caisse caisse = (Caisse) caisseElement;
                caisse.setX(derriereCaisseX);
                caisse.setY( derriereCaisseY);

                // Puis déplacer le personnage
                perso.setX(suivantX) ;
                perso.setY(suivantY) ;
            }
            // Sinon, on ne peut pas pousser la caisse, donc pas de déplacement
        } else {
            // Pas de caisse, on peut simplement déplacer le personnage
            perso.setX(suivantX);
            perso.setY(suivantY) ;
        }
    }

    /**
     * Calcule les coordonnées de la case suivante en fonction de l'action
     * @param x coordonnée x actuelle
     * @param y coordonnée y actuelle
     * @param action direction du déplacement (HAUT, BAS, GAUCHE, DROITE)
     * @return tableau de deux entiers contenant les nouvelles coordonnées [x, y]
     */
    public static int[] getSuivant(int x, int y, String action) {
        int[] resultat = new int[2];
        resultat[0] = x;
        resultat[1] = y;

        if (action.equals(HAUT)) {
            resultat[1] = y - 1;  // En haut, diminue y
        } else if (action.equals(BAS)) {
            resultat[1] = y + 1;  // En bas, augmente y
        } else if (action.equals(GAUCHE)) {
            resultat[0] = x - 1;  // À gauche, diminue x
        } else if (action.equals(DROITE)) {
            resultat[0] = x + 1;  // À droite, augmente x
        }

        return resultat;
    }

    /**
     * Vérifie si le jeu est terminé, c'est-à-dire si toutes les caisses sont sur des lieux de dépôt
     * @return true si le jeu est terminé, false sinon
     */
    public boolean etreFini() {
        // On va parcourir toutes les caisses et vérifier qu'elles sont toutes sur un dépôt
        for (Element caisse : caisses.getElements()) {
            boolean surDepot = false;

            // Pour chaque caisse, vérifier si elle est sur un dépôt
            for (Element depot : depots.getElements()) {
                if (caisse.getX() == depot.getX() && caisse.getY() == depot.getY()) {
                    surDepot = true;
                    break;
                }
            }

            // Si une caisse n'est pas sur un dépôt, le jeu n'est pas fini
            if (!surDepot) {
                return false;
            }
        }

        // Toutes les caisses sont sur des dépôts, le jeu est fini
        return true;
    }

    public Perso getPj(){
        return perso;
    }

    public ListeElements getDepots(){return depots;}

    public ListeElements getCaisses() {return caisses;}

    public Labyrinthe getLaby(){return laby;};
}
