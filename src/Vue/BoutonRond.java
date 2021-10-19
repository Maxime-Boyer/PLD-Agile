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
        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(Color.PINK);
        g2.setStroke(new BasicStroke(1));

        g2.fillRoundRect(x,y,width, height, rayonCourbure, rayonCourbure);
    }

}
