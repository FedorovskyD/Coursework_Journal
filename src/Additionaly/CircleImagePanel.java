package Additionaly;

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;

public class CircleImagePanel extends JPanel {
    private BufferedImage image;
    private BufferedImage circleImage;


    public CircleImagePanel(String imagePath) {
        try {
            // Load the image from the file system
            image = ImageIO.read(new File(imagePath));

            // Create a BufferedImage for the circle image
            circleImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);

            // Get the graphics context for the circle image
            Graphics2D g2 = circleImage.createGraphics();

            // Create a clipping path for the circle
            Ellipse2D.Double circle = new Ellipse2D.Double(0, 0, image.getWidth(), image.getHeight());
            g2.setClip(circle);

            // Draw the image onto the circle
            g2.drawImage(image, 0, 0, null);

            // Dispose of the graphics context
            g2.dispose();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw the circle image onto the panel
        g.drawImage(circleImage, 0, 0, null);
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       // frame.setSize(400, 400);
        frame.setPreferredSize(new Dimension(400, 300));

        CircleImagePanel panel = new CircleImagePanel("img/zhenya.jpg");
        frame.getContentPane().add(panel);

        frame.setVisible(true);
    }
}

