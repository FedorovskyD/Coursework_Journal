package AboutTheAuthor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AboutDialog extends JDialog {
    public AboutDialog() {

        // Димон
        JPanel leftPanel = new JPanel(new BorderLayout());

        JLabel leftNameLabel = new JLabel("Имя: Дмитрий");
        JLabel leftSurnameLabel = new JLabel("Фамилия: Федоровский");
        JLabel leftEmailLabel = new JLabel("Адрес почты: fedos@mail.ru");

        ImageIcon leftImageIcon = new ImageIcon("img/Ulyana.jpg");
        JLabel leftPhotoLabel = new JLabel(leftImageIcon);

        leftPanel.add(leftNameLabel, BorderLayout.NORTH);
        leftPanel.add(leftSurnameLabel, BorderLayout.CENTER);
        leftPanel.add(leftEmailLabel, BorderLayout.SOUTH);
        leftPanel.add(leftPhotoLabel, BorderLayout.WEST);

        // Улик
        JPanel rightPanel = new JPanel(new BorderLayout());

        JLabel rightNameLabel = new JLabel("Имя: Ульяна");
        JLabel rightSurnameLabel = new JLabel("Фамилия: Синявская");
        JLabel rightEmailLabel = new JLabel("Адрес почты: sinavsakku03@gmail.com");


        ImageIcon rightImageIcon = new ImageIcon("img/Ulyana.jpg");
        Image image = rightImageIcon.getImage(); // получаем объект Image
        Image scaledImage = image.getScaledInstance(300, 400, Image.SCALE_SMOOTH); // изменяем размер
        ImageIcon newIcon = new ImageIcon(scaledImage); // создаем новый ImageIcon из измененного Image

        JLabel rightPhotoLabel = new JLabel(newIcon);

        rightPanel.add(rightNameLabel, BorderLayout.NORTH);
        rightPanel.add(rightSurnameLabel, BorderLayout.CENTER);
        rightPanel.add(rightEmailLabel, BorderLayout.SOUTH);
        rightPanel.add(rightPhotoLabel, BorderLayout.EAST);

        // Кнопка "Назад"
        JButton backButton = new JButton("Назад");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                dispose();
            }
        });

        // Общая панель
        JPanel panel = new JPanel(new GridLayout(1, 2));
        panel.add(leftPanel);
        panel.add(rightPanel);

        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(backButton, BorderLayout.PAGE_END);


    }

    public static void main(String[] args) {
        AboutDialog aboutauthor=new AboutDialog();
        aboutauthor.setTitle("Authors");
        aboutauthor.setSize(800,600);
        aboutauthor.setLocationRelativeTo(null);
        aboutauthor.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        aboutauthor.setVisible(true);
    }
}
