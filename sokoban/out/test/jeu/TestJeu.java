package jeu;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestJeu {

    @Test
    void testDeplacementVersEspaceVide() throws ActionInconnueException {
        // Créer un labyrinthe simple pour le test
        Labyrinthe laby = new Labyrinthe(3, 3);
        for (int x = 0; x < 3; x++) {
            laby.setMur(x, 0);
            laby.setMur(x, 2);
        }
        laby.setMur(0, 1);
        laby.setMur(2, 1);

        // Personnage au milieu avec espace vide autour
        Perso perso = new Perso(1, 1);

        // Pas de caisses pour ce test
        ListeElements caisses = new ListeElements();
        ListeElements depots = new ListeElements();

        Jeu jeu = new Jeu(perso, caisses, depots, laby);

        // Position initiale
        assertEquals(1, jeu.getPj().getX());
        assertEquals(1, jeu.getPj().getY());

        // Déplacement vers le haut (impossible à cause du mur)
        jeu.deplacerPerso(Jeu.HAUT);
        assertEquals(1, jeu.getPj().getX());
        assertEquals(1, jeu.getPj().getY());

        // Déplacement vers le bas (impossible à cause du mur)
        jeu.deplacerPerso(Jeu.BAS);
        assertEquals(1, jeu.getPj().getX());
        assertEquals(1, jeu.getPj().getY());
    }

    @Test
    void testPousserCaisse() throws ActionInconnueException {
        // Créer un labyrinthe 5x3
        // #####
        // #@$ #
        // #####
        Labyrinthe laby = new Labyrinthe(5, 3);

        // Mettre des murs autour
        for (int x = 0; x < 5; x++) {
            laby.setMur(x, 0);
            laby.setMur(x, 2);
        }
        laby.setMur(0, 1);
        laby.setMur(4, 1);

        // Personnage à gauche, caisse au milieu
        Perso perso = new Perso(1, 1);

        // Une caisse à droite du personnage
        ListeElements caisses = new ListeElements();
        caisses.ajouter(new Caisse(2, 1));

        // Un dépôt quelque part
        ListeElements depots = new ListeElements();
        depots.ajouter(new Depot(3, 1));

        Jeu jeu = new Jeu(perso, caisses, depots, laby);

        // Pousser la caisse vers la droite
        jeu.deplacerPerso(Jeu.DROITE);

        // Le personnage doit être à la place de la caisse
        assertEquals(2, jeu.getPj().getX());
        assertEquals(1, jeu.getPj().getY());

        // La caisse doit avoir été poussée
        Element caisse = jeu.getCaisses().getElements()[0];
        assertEquals(3, caisse.getX());
        assertEquals(1, caisse.getY());
    }

    @Test
    void testPousserCaisseImpossible() throws ActionInconnueException {
        // Créer un labyrinthe où la caisse ne peut pas être poussée
        // ####
        // #@$#
        // ####
        Labyrinthe laby = new Labyrinthe(4, 3);

        // Mettre des murs autour
        for (int x = 0; x < 4; x++) {
            laby.setMur(x, 0);
            laby.setMur(x, 2);
        }
        laby.setMur(0, 1);
        laby.setMur(3, 1);

        // Personnage à gauche, caisse au milieu contre un mur
        Perso perso = new Perso(1, 1);

        // Une caisse à droite du personnage
        ListeElements caisses = new ListeElements();
        caisses.ajouter(new Caisse(2, 1));

        // Un dépôt quelque part
        ListeElements depots = new ListeElements();
        depots.ajouter(new Depot(2, 1));

        Jeu jeu = new Jeu(perso, caisses, depots, laby);

        // Essayer de pousser la caisse contre le mur (impossible)
        jeu.deplacerPerso(Jeu.DROITE);

        // Le personnage ne doit pas avoir bougé
        assertEquals(1, jeu.getPj().getX());
        assertEquals(1, jeu.getPj().getY());

        // La caisse ne doit pas avoir bougé
        Element caisse = jeu.getCaisses().getElements()[0];
        assertEquals(2, caisse.getX());
        assertEquals(1, caisse.getY());
    }

    @Test
    void testActionInconnue() {
        // Créer un jeu simple
        Labyrinthe laby = new Labyrinthe(3, 3);
        Perso perso = new Perso(1, 1);
        ListeElements caisses = new ListeElements();
        ListeElements depots = new ListeElements();

        Jeu jeu = new Jeu(perso, caisses, depots, laby);

        // Vérifier qu'une action inconnue lève l'exception appropriée
        assertThrows(ActionInconnueException.class, () -> {
            jeu.deplacerPerso("ActionInconnue");
        });
    }

    @Test
    void testEtreFini() {
        // Créer un jeu simple
        Labyrinthe laby = new Labyrinthe(3, 3);
        Perso perso = new Perso(0, 0);

        // Une caisse et un dépôt aux mêmes coordonnées
        ListeElements caisses = new ListeElements();
        Caisse caisse = new Caisse(1, 1);
        caisses.ajouter(caisse);

        ListeElements depots = new ListeElements();
        depots.ajouter(new Depot(2, 2)); // Dépôt à un endroit différent

        Jeu jeu = new Jeu(perso, caisses, depots, laby);

        // Au départ, le jeu n'est pas fini
        assertFalse(jeu.etreFini());

        // Déplacer manuellement la caisse sur le dépôt
        caisse.setX(2);
        caisse.setY(2);

        // Maintenant le jeu devrait être fini
        assertTrue(jeu.etreFini());
    }
}