package dialogs;

import MainFrame.MainWindow;

import javax.swing.*;
import java.awt.*;

public class JDialogAbout extends JDialog {
    private MainWindow mainWindow;
    public JDialogAbout(JFrame parent) {
        super(parent, "Delete Group", true);
        mainWindow = (MainWindow) parent;

        // Общая панель
        JPanel authorsPanel = new JPanel(new BorderLayout());
//        authorsPanel.setPreferredSize(new Dimension(400, 250));
        JLabel center=new JLabel("Информация об авторах");
        center.setHorizontalAlignment(JLabel.CENTER);
        authorsPanel.add(center,BorderLayout.NORTH);


        // Димон
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel,BoxLayout.Y_AXIS));
        JLabel leftNameLabel = new JLabel("Имя: Дмитрий");
        JLabel leftSurnameLabel = new JLabel("Фамилия: Федоровский");
        JLabel leftEmailLabel = new JLabel("Адрес почты: fedos@mail.ru");

        ImageIcon leftImageIcon = new ImageIcon("img/Ulyana.jpg");
        Image image1 = leftImageIcon.getImage(); // получаем объект Image
        Image scaledImage1 = image1.getScaledInstance(300, 400, Image.SCALE_SMOOTH); // изменяем размер
        ImageIcon newIcon1 = new ImageIcon(scaledImage1); // создаем новый ImageIcon из измененного Image
        JLabel leftPhotoLabel = new JLabel(newIcon1);

        leftPanel.add(leftPhotoLabel);
        leftPanel.add(leftNameLabel);
        leftPanel.add(leftSurnameLabel);
        leftPanel.add(leftEmailLabel);
        authorsPanel.add(leftPanel,BorderLayout.WEST);

        // Улик
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel,BoxLayout.Y_AXIS));
        JLabel rightNameLabel = new JLabel("Имя: Ульяна");
        JLabel rightSurnameLabel = new JLabel("Фамилия: Синявская");
        JLabel rightEmailLabel = new JLabel("Адрес почты: sinavsakku03@gmail.com");

        ImageIcon rightImageIcon = new ImageIcon("img/Ulyana.jpg");
        Image image2 = rightImageIcon.getImage(); // получаем объект Image
        Image scaledImage2 = image2.getScaledInstance(300, 400, Image.SCALE_SMOOTH); // изменяем размер
        ImageIcon newIcon2 = new ImageIcon(scaledImage2); // создаем новый ImageIcon из измененного Image
        JLabel rightPhotoLabel = new JLabel(newIcon2);

        rightPanel.add(rightPhotoLabel);
        rightPanel.add(rightNameLabel);
        rightPanel.add(rightSurnameLabel);
        rightPanel.add(rightEmailLabel);

        authorsPanel.add(rightPanel,BorderLayout.EAST);

        // Кнопка "Назад"
        JButton backButton = new JButton("Назад");
        authorsPanel.add(backButton,BorderLayout.SOUTH);
        getContentPane().add(authorsPanel);
        setSize(700, 600);
        setLocationRelativeTo(parent);
        setVisible(true);
    }


//    public static void main(String[] args) {
//        JDialogAbout aboutauthor=new JDialogAbout();
//
//        aboutauthor.setTitle("Authors");
//    aboutauthor.setSize(800,600);
//        aboutauthor.setLocationRelativeTo(null);
//        aboutauthor.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        aboutauthor.setVisible(true);
//    }
}
