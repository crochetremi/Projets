package graphisme;

import javax.swing.*;
import java.awt.*;

public class VueCoups extends JLabel {

    /**
     * stoke le nombre de coups joués
     */
    int nbCoups = 0;


    public VueCoups(){
        // centre jlabel
        super("",SwingConstants.CENTER);


        this.nbCoups = 0;
        this.setText("Deplacements: "+nbCoups);

        // modifie hauteur fenetre
        this.setPreferredSize(new Dimension(400,50));

        // modifie police
        this.setFont(new Font("Arial",Font.PLAIN,30));

        //centrer texte


    }

    /**
     * met à jour le label et le nombre de coups
     */
    public void miseAJour(){
        this.nbCoups++;
        this.setText("Deplacements: "+nbCoups);
    }
}
