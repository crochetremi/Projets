package jeu;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
import java.net.URL;

public class TestChargement {

    @Test
    void testChargementFichierValide() throws IOException {
        boolean success = true;
        Jeu jeu = null;
        URL ressource = getClass().getResource("laby_simple.txt");
        assertNotNull(ressource, "Fichier laby_simple.txt introuvable");

        try {
            jeu = Chargement.chargerJeu(ressource.getFile());
        } catch (Exception e) {
            success = false;
            fail("Ne devrait pas lever d'exception pour un fichier valide: " + e.getMessage());
        }

        assertTrue(success);
        assertNotNull(jeu);
        assertNotNull(jeu.getPj());
        assertTrue(jeu.getCaisses().taille() > 0);
        assertTrue(jeu.getDepots().taille() > 0);
        assertEquals(jeu.getCaisses().taille(), jeu.getDepots().taille());
    }

    @Test
    void testChargementFichierInexistant() {
        assertThrows(IOException.class, () -> {
            Chargement.chargerJeu("fichier_inexistant.txt");
        });
    }

    @Test
    void testFichierCaractereInconnu() {
        URL ressource = getClass().getResource("laby_caractere_invalide.txt");
        assertNotNull(ressource, "Fichier laby_caractere_invalide.txt introuvable");

        assertThrows(FichierIncorrectException.class, () -> {
            Chargement.chargerJeu(ressource.getFile());
        }, "caractere inconnu");
    }

    @Test
    void testFichierSansPersonnage() {
        URL ressource = getClass().getResource("laby_sans_personnage.txt");
        assertNotNull(ressource, "Fichier laby_sans_personnage.txt introuvable");

        FichierIncorrectException exception = assertThrows(FichierIncorrectException.class, () -> {
            Chargement.chargerJeu(ressource.getFile());
        });
        assertEquals("personnage inconnu", exception.getMessage());
    }

    @Test
    void testFichierSansCaisse() {
        URL ressource = getClass().getResource("laby_sans_caisse.txt");
        assertNotNull(ressource, "Fichier laby_sans_caisse.txt introuvable");

        FichierIncorrectException exception = assertThrows(FichierIncorrectException.class, () -> {
            Chargement.chargerJeu(ressource.getFile());
        });
        assertEquals("caisses inconnues", exception.getMessage());
    }

    @Test
    void testNombreIncorrectDeDepots() {
        URL ressource = getClass().getResource("laby_mauvais_depots.txt");
        assertNotNull(ressource, "Fichier laby_mauvais_depots.txt introuvable");

        FichierIncorrectException exception = assertThrows(FichierIncorrectException.class, () -> {
            Chargement.chargerJeu(ressource.getFile());
        });
        assertTrue(exception.getMessage().contains("Caisses("));
        assertTrue(exception.getMessage().contains("Depots("));
    }
}