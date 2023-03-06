package Additionaly;

import javax.swing.*;
import java.awt.*;

// пример использования
public class RoundedPanelExample extends JFrame {

    public RoundedPanelExample() {
        setTitle("Rounded Panel Example");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.setBackground(Color.WHITE);
        setContentPane(contentPane);

        RoundedPanel roundedPanel2 = new RoundedPanel(80, Color.GRAY, Color.BLUE);
        roundedPanel2.setBorder(BorderFactory.createLineBorder(Color.BLUE));
        roundedPanel2.setPreferredSize(new Dimension(200, 200));
        contentPane.add(roundedPanel2, BorderLayout.CENTER);

        pack();
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(RoundedPanelExample::new);
    }
}
