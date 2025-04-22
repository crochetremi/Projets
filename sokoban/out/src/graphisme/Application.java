package graphisme;

import jeu.ActionInconnueException;
import jeu.Jeu;

import javax.swing.*;
import java.awt.*;

public class Application {

    /**
     * le label qui compte les coups
     */
    private final VueCoups vueCoups;

    /**
     * la frame pour la mise a jour
     */
    private final JFrame frame;

    /**
     * le modele de jeu
     */
    Jeu jeu;

    /**
     * construit une application avec le jeu J
     *
     * @param j jeu
     */
    public Application(Jeu j) {
        // copie du jeu
        this.jeu = j;

        // creation de la frame
        frame = new JFrame("Sokoban");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // creation de la vue
        Vue vue = new Vue(this.jeu);
        frame.setLayout(new BorderLayout());
        frame.add(vue, BorderLayout.CENTER);

        // creation vueCoups
        vueCoups = new VueCoups();
        frame.add(vueCoups, BorderLayout.NORTH);

        // creation des controleurs (qui connaissent la vue)
        // pas de MVC pour éviter d'avoir un modèle
        Controleur control = new Controleur(this);
        frame.add(control, BorderLayout.SOUTH);

        // controleur clavier
        Clavier clavier = new Clavier(this);
        control.addKeyListener(clavier);

        // setvisible
        frame.pack();
        control.requestFocus();
        frame.setVisible(true);
    }


    /**
     * methode d'execution du jeu (en  charge du controle du modele
     * permet de centraliser les deplacements
     */
    public void deplacerPerso(String action) throws ActionInconnueException {
        this.jeu.deplacerPerso(action);
        this.vueCoups.miseAJour();

        // gere la mise à jour
        this.frame.repaint();
    }
}
