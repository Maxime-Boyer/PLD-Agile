package Vue;

import javax.swing.*;
import java.awt.*;

public class BoutonRond extends JButton{

    public BoutonRond(){

        //this.setContentAreaFilled(false);
        //this.setVisible(true);
    }

    @Override
    public void paintComponent(Graphics g){


        Graphics2D g2 = (Graphics2D) g.create();

        g2.setColor(Color.PINK);
        System.out.println("paint bouton rond");
        System.out.println("getX()" + getX());
        System.out.println("getY()" + getY());
        System.out.println("getWidth()" + getWidth());
        System.out.println("getHeight()" + getHeight());
        g2.fillRoundRect(getX(),getY(),getWidth(), getHeight(), getWidth(), getHeight());
        //
        //super.paintComponent(g);
        //this.setVisible(true);
    }

}
