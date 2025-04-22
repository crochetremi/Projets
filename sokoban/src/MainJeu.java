import jeu.*;
import java.io.IOException;
import java.util.Scanner;

/**
 * Classe principale pour exécuter le jeu Sokoban en mode texte
 */
public class MainJeu {

    public static void main(String[] args) {
        // Vérifier si un nom de fichier a été fourni
        if (args.length != 1) {
            System.out.println("Usage: java MainJeu <nom_fichier>");
            return;
        }

        String nomFichier = args[0];
        Scanner scanner = new Scanner(System.in);
        int nbDeplacements = 0;

        try {
            // Charger le jeu à partir du fichier
            Jeu jeu = Chargement.chargerJeu(nomFichier);
            boolean jeuEnCours = true;

            System.out.println("Bienvenue dans Sokoban !");
            System.out.println("Déplacez toutes les caisses ($) sur les lieux de dépôt (.)");

            // Boucle principale du jeu
            while (jeuEnCours) {
                // Afficher l'état actuel du labyrinthe
                System.out.println("\nDéplacements: " + nbDeplacements);
                System.out.println(jeu.jeuToString());

                // Vérifier si le jeu est terminé
                if (jeu.etreFini()) {
                    System.out.println("Félicitations ! Vous avez résolu le niveau en " + nbDeplacements + " déplacements !");
                    jeuEnCours = false;
                    continue; // Sortir de la boucle pour éviter de demander une action
                }

                // Afficher les actions possibles
                System.out.println("Actions possibles:");
                System.out.println("h - Haut");
                System.out.println("b - Bas");
                System.out.println("g - Gauche");
                System.out.println("d - Droite");
                System.out.println("q - Quitter");

                // Demander l'action au joueur
                System.out.print("Votre action: ");
                String input = scanner.nextLine().toLowerCase();

                try {
                    boolean deplacementEffectue = false;

                    if (input.equals("q")) {
                        System.out.println("Jeu terminé. Merci d'avoir joué !");
                        jeuEnCours = false;
                    } else if (input.equals("h")) {
                        jeu.deplacerPerso(Jeu.HAUT);
                        deplacementEffectue = true;
                    } else if (input.equals("b")) {
                        jeu.deplacerPerso(Jeu.BAS);
                        deplacementEffectue = true;
                    } else if (input.equals("g")) {
                        jeu.deplacerPerso(Jeu.GAUCHE);
                        deplacementEffectue = true;
                    } else if (input.equals("d")) {
                        jeu.deplacerPerso(Jeu.DROITE);
                        deplacementEffectue = true;
                    } else {
                        System.out.println("Action non reconnue. Veuillez réessayer.");
                    }

                    // N'incrémenter le compteur que si un déplacement a réellement été effectué
                    if (deplacementEffectue) {
                        nbDeplacements++;
                    }
                } catch (ActionInconnueException e) {
                    // Cette exception ne devrait pas se produire avec notre interface
                    System.out.println("Erreur: " + e.getMessage());
                }
            }

        } catch (FichierIncorrectException e) {
            System.out.println("Erreur dans le fichier de labyrinthe: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Erreur lors de la lecture du fichier: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}