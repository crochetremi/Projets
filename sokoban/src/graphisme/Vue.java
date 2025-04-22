package graphisme;

import jeu.Jeu;
import jeu.Labyrinthe;

import javax.swing.*;
import java.awt.*;

public class Vue extends JPanel {

    /**
     * taille des cases
     */
    public static final int TAILLE = 80;

    /**
     * le modele a afficher
     */
    Jeu jeu;

    /**
     * construction d'une vue a partie d'un jeu.
     * construit une vue a la bonne taille a partir du descriptif du jeu.
     *
     * @param jeu le jeu qu'on souhaite dessiner
     */
    public Vue(Jeu jeu) {
        // stocke la reference
        this.jeu = jeu;

        // initialise la frame
        String descriptif = jeu.jeuToString();

        // recupere les lignes
        String[] lignes = descriptif.split("\n");
        // utilise le descriptif pour avoir la taille
        int width = lignes[0].length() * TAILLE;
        int height = lignes.length * TAILLE;

        // met a jour fenetre
        this.setPreferredSize(new Dimension(width, height));

        // demande mise à jour de l'affichage
        this.repaint();
    }

    /**
     * redefinit la methode d'affoichage
     *
     * @param g le graphics du panel dans lequel dessiner
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // recupere le descriptif
        String desc = this.jeu.jeuToString();
        String[] lignes = desc.split("\n");

        // pour chaque ligne
        for (int numLigne = 0; numLigne < lignes.length; numLigne++) {

            // pour chaque colonne
            String ligneAffiche = lignes[numLigne];

            // parcour chaque colonne de la ligne (equivalent aux lettres de la ligne)
            for (int numCol = 0; numCol < ligneAffiche.length(); numCol++) {

                // traite caractere par caractere
                char charAffiche = ligneAffiche.charAt(numCol);
                dessinerChar(g, numLigne, numCol, charAffiche);
            }
        }
        // fin d'affichage

        // si le jeu est fini, on affiche bravo
        if (this.jeu.etreFini())
        {
            g.setColor(Color.ORANGE);
            int decal = 100;
            g.fillRect(decal, decal,this.getWidth()- 2*decal,this.getHeight()- 2*decal);
            g.setColor(Color.black);
            g.drawString("BRAVO",this.getWidth()/2-50,this.getHeight()/2);
        }
    }

    /**
     * dessine le caractere passe en parametre dans l'image
     *
     * @param g           graphics pour dessiner
     * @param numLigne    numero de ligne
     * @param numCol      numero de colonne
     * @param charAffiche le caractere a trduire dans l'affichage
     */
    private void dessinerChar(Graphics g, int numLigne, int numCol, char charAffiche) {
        // positions (pour etre sur de ne pas faire d'inversion)
        int x = numCol;
        int y = numLigne;

        switch (charAffiche) {

            // si c'est un mur
            case Labyrinthe.MUR:
                this.dessinerMur(g, x, y);
                break;

            // si c'est le personnage
            case Labyrinthe.PJ:
                this.dessinerPersonnage(g, x, y);
                break;

            // si c'est une caisse
            case Labyrinthe.CAISSE:
                this.dessinerCaisse(g, x, y);
                break;

            // si c'est un vide
            case Labyrinthe.VIDE:
                this.dessinerVide(g, x, y);
                break;

            // si c'est un lieu de depot
            case Labyrinthe.DEPOT:
                this.dessinerDepot(g, x, y);
                break;
        }
    }

    /**
     * permet de dessiner un lieu de depot
     * juste un carre dans la case
     *
     * @param g  graphics ou dessiner
     * @param dx numero de colonne
     * @param dy numero de ligne
     */
    private void dessinerDepot(Graphics g, int dx, int dy) {
        g.setColor(Color.black);
        // dessine case vide
        this.dessinerVide(g, dx, dy);

        // utilise variable intermedaire, coin de la case
        int x = dx * TAILLE;
        int y = dy * TAILLE;

        // on dessine une caisse plus petite
        int PROP = (int) (TAILLE * 0.2);
        g.setColor(Color.yellow);
        g.fillRect(x + PROP, y + PROP, TAILLE - 2 * PROP, TAILLE - 2 * PROP);
        g.setColor(Color.black);
        g.drawRect(x + PROP, y + PROP, TAILLE - 2 * PROP, TAILLE - 2 * PROP);

    }

    /**
     * dessine une case vide
     *
     * @param g  graphics pour dessiner
     * @param dx numero de colonne
     * @param dy numero de ligne
     */
    private void dessinerVide(Graphics g, int dx, int dy) {
        g.setColor(Color.black);

        // on dessine une case vide
        int x = dx * TAILLE;
        int y = dy * TAILLE;
        g.drawRect(x, y, TAILLE, TAILLE);
    }

    /**
     * dessine une caisse = carre avec des diagonales
     *
     * @param g  graphics ou dessiner
     * @param dx numero de colonne
     * @param dy numero de ligne
     */
    private void dessinerCaisse(Graphics g, int dx, int dy) {
        // dessine case vide
        this.dessinerVide(g, dx, dy);

        // utilise variable intermedaire, coin de la case
        int x = dx * TAILLE;
        int y = dy * TAILLE;
        g.setColor(Color.ORANGE);

        // on dessine une caisse plus petite
        int PROP = (int) (TAILLE * 0.2);
        g.fillRect(x + PROP, y + PROP, TAILLE - 2 * PROP, TAILLE - 2 * PROP);

        g.setColor(Color.black);
        g.drawRect(x + PROP, y + PROP, TAILLE - 2 * PROP, TAILLE - 2 * PROP);

        // diagonales
        g.drawLine(x + PROP, y + PROP, x + TAILLE - PROP, y + TAILLE - PROP);
        g.drawLine(x + TAILLE - PROP, y + PROP, x + PROP, y + TAILLE - PROP);
    }

    /**
     * dessine le personnage (juste un ovale)
     *
     * @param g  graphics pour dessiner
     * @param dx numero de colonne
     * @param dy numero de ligne
     */
    private void dessinerPersonnage(Graphics g, int dx, int dy) {
        g.setColor(Color.blue);
        g.fillOval(dx * TAILLE, dy * TAILLE, TAILLE, TAILLE);

        g.setColor(Color.black);
        g.drawOval(dx * TAILLE, dy * TAILLE, TAILLE, TAILLE);
    }

    /**
     * dessine un mur
     *
     * @param g  graphics pour dessiner
     * @param dx numero de colonne
     * @param dy numero de ligne
     */
    private void dessinerMur(Graphics g, int dx, int dy) {
        // fait un rectangle autour du mur
        g.setColor(Color.black);
        g.fillRect(dx * TAILLE, dy * TAILLE, TAILLE, TAILLE);

        g.setColor(Color.white);
        g.drawRect(dx * TAILLE, dy * TAILLE, TAILLE, TAILLE);


    }

}
