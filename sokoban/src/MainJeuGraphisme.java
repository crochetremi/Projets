import graphisme.Application;
import jeu.ActionInconnueException;
import jeu.Chargement;
import jeu.FichierIncorrectException;
import jeu.Jeu;

import java.io.IOException;

public class MainJeuGraphisme {
    public static void main(String[] args) throws IOException, FichierIncorrectException, ActionInconnueException {
        Jeu jeu = Chargement.chargerJeu("laby/laby.txt");
        Application a = new Application(jeu);
    }
}
