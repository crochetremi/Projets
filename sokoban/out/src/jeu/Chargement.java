package jeu;

import java.io.*;
import java.util.ArrayList;

public class Chargement {

    /**
     * Charge un fichier décrivant un labyrinthe et crée un objet Jeu correspondant
     * @param nomFichier Le nom du fichier à charger
     * @return Un objet Jeu configuré selon le contenu du fichier
     * @throws IOException En cas d'erreur de lecture du fichier
     * @throws FichierIncorrectException Si le format du fichier est incorrect
     */
    public static Jeu chargerJeu(String nomFichier) throws IOException, FichierIncorrectException {
        // Étape 1: Lecture du fichier et stockage dans une liste de String
        ArrayList<String> lignes = new ArrayList<>();

        // Lecture du fichier ligne par ligne
        try (BufferedReader br = new BufferedReader(new FileReader(nomFichier))) {
            String ligne;
            while ((ligne = br.readLine()) != null) {
                lignes.add(ligne);
            }
        } catch (IOException e) {
            throw new IOException("Erreur lors de la lecture du fichier : " + e.getMessage());
        }

        // Vérifier que le fichier n'est pas vide
        if (lignes.isEmpty()) {
            throw new FichierIncorrectException("Fichier vide");
        }

        // Étape 2: Détermination des dimensions du labyrinthe
        int hauteur = lignes.size();

        // Trouver la largeur (longueur maximale des lignes)
        int largeur = 0;
        for (String ligne : lignes) {
            if (ligne.length() > largeur) {
                largeur = ligne.length();
            }
        }

        // Étape 3: Initialisation des structures de données
        Labyrinthe laby = new Labyrinthe(largeur, hauteur);
        boolean[][] murs = new boolean[largeur][hauteur];

        // Initialiser les listes pour les caisses et les dépôts
        ListeElements caisses = new ListeElements();
        ListeElements depots = new ListeElements();

        // Variables pour le personnage
        Perso perso = null;
        boolean personnageTrouve = false;

        // Compteurs pour vérifications
        int nbCaisses = 0;
        int nbDepots = 0;

        // Étape 4: Analyse du contenu du fichier et construction des objets
        for (int y = 0; y < hauteur; y++) {
            String ligne = lignes.get(y);
            for (int x = 0; x < ligne.length(); x++) {
                char c = ligne.charAt(x);

                switch (c) {
                    case Labyrinthe.MUR:
                        murs[x][y] = true;
                        break;

                    case Labyrinthe.CAISSE:
                        Caisse caisse = new Caisse(x, y);
                        caisses.ajouter(caisse);
                        nbCaisses++;
                        break;

                    case Labyrinthe.DEPOT:
                        Depot depot = new Depot(x, y);
                        depots.ajouter(depot);
                        nbDepots++;
                        break;

                    case Labyrinthe.PJ:
                        perso = new Perso(x, y);
                        personnageTrouve = true;
                        break;

                    case Labyrinthe.VIDE:
                        // Rien à faire pour les cases vides
                        break;

                    default:
                        throw new FichierIncorrectException("caractere inconnu " + c);
                }
            }
        }

        // Mettre à jour le labyrinthe avec les murs
        laby.setMurs(murs);

        // Étape 5: Vérifications des conditions requises
        // Vérifier que le personnage existe
        if (!personnageTrouve) {
            throw new FichierIncorrectException("personnage inconnu");
        }

        // Vérifier qu'il y a au moins une caisse
        if (nbCaisses == 0) {
            throw new FichierIncorrectException("caisses inconnues");
        }

        // Vérifier que le nombre de caisses correspond au nombre de dépôts
        if (nbCaisses != nbDepots) {
            throw new FichierIncorrectException("Caisses(" + nbCaisses + ") Depots(" + nbDepots + ")");
        }

        // Étape 6: Création et retour de l'objet Jeu
        return new Jeu(perso, caisses, depots, laby);
    }
}