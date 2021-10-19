import javax.swing.*;
import java.awt.*;

public class BoutonRond  extends JButton{
    private int rayonCourbure;
    private int x;
    private int y;
    private int width;
    private int height;

    public BoutonRond(int x, int y, int width, int height, int rayonCourbure){
        this.rayonCourbure = rayonCourbure;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void paintComponent(Graphics g){
        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(1));

        // BackGround

        g2.setColor(Color.WHITE);

        g2.fillRoundRect(0,0,);

        g2.setColor(Color.BLACK);
    }
}
