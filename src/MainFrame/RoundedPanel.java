package MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class RoundedPanel extends JPanel {

    RoundedPanelHeader person1=new RoundedPanelHeader("Ulik","Sinyavskaya","sinavskaau@gmail.com",new ImageIcon("img/zhenya.jpg"));


    private int cornerRadius;

    public RoundedPanel(int cornerRadius) {
        this.cornerRadius = cornerRadius;
        person1.setOpaque(false);
        add(person1);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        RoundRectangle2D rect = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);
        g2.clip(rect);
        super.paintComponent(g);
    }

    public RoundedPanelHeader getPerson1() {
        return person1;
    }
}


