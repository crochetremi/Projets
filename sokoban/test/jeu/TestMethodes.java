package jeu;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class TestMethodes {

    @Test
    /**
     * verification de l'écriture des méthodes
     */
    public void verificationJeu() throws FichierIncorrectException, IOException, ActionInconnueException {
        Jeu jeu = Chargement.chargerJeu("laby/laby.txt");
        jeu.deplacerPerso(Jeu.HAUT);

        // verification des coordonnees (si bien chargé)
        int x = jeu.getPj().getX();
        int y = jeu.getPj().getY();
        assertEquals(5, x);
        assertEquals(2, y);

        // le jeu n'est pas fini
        assertFalse(jeu.etreFini());
        String s = jeu.jeuToString();
    }
}