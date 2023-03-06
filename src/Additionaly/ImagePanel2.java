package Additionaly;

import javax.swing.*;
import java.awt.*;

public class ImagePanel2 extends JPanel {

    private ImageIcon imageIcon;

    public ImagePanel2(String imagePath) {
        this.imageIcon = new ImageIcon(imagePath);
        setPreferredSize(new Dimension(120, 120)); // установка фиксированного размера панели
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // отрисовка картинки на всю панель
        g.drawImage(imageIcon.getImage(), 0, 0, getWidth(), getHeight(), this);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Image Panel Example");
        ImagePanel2 panel = new ImagePanel2("img/zhenya.jpg");
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }
}

