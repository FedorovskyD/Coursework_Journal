package MainFrame;

import javax.swing.*;
import java.awt.*;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class RoundedPanelHeader extends JPanel {

    //RoundedPanelHeader sp=new RoundedPanelHeader("Ulik","Sinyavskaya","sinavskaau@gmail.com",new ImageIcon("zhenya.jpg"));
    private String firstName;
    private String lastName;
    private String email;
    private ImageIcon logoImage;

    public RoundedPanelHeader(String firstName, String lastName, String email, ImageIcon logoImage) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.email=email;
        this.logoImage = logoImage;

        JPanel headerPanel=new JPanel();
        headerPanel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        JLabel imageJL = new JLabel();

        // Получение изображения из ImageIcon
        Image image = logoImage.getImage();

        Image newImage=image.getScaledInstance(90,90,java.awt.Image.SCALE_SMOOTH);

        // Установка изображения на JLabel
        imageJL.setIcon(new ImageIcon(newImage));

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(10, 40, 5, 5);
        constraints.gridwidth=1;
        constraints.gridheight=3;

        //constraints.weightx=100;
        headerPanel.add(imageJL, constraints);

        Font font = new Font("Century Gothic", Font.BOLD, 15);
        JLabel firstNameLabel = new JLabel(firstName);
        //firstNameLabel.setVerticalAlignment(JLabel.TOP);
        //firstNameLabel.setHorizontalAlignment(JLabel.LEFT);
        firstNameLabel.setFont(font);
        firstNameLabel.setForeground(Color.BLACK);


        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.PAGE_START;
        constraints.gridheight=1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        headerPanel.add(firstNameLabel, constraints);

        JLabel lastNameLable=new JLabel(lastName);
        //lastNameLable.setVerticalAlignment(JLabel.TOP);
        //lastNameLable.setHorizontalAlignment(JLabel.CENTER);
        lastNameLable.setFont(font);
        lastNameLable.setForeground(Color.BLACK);

        constraints.gridy = 1;
        constraints.gridheight=1;
        constraints.anchor = GridBagConstraints.PAGE_START;
        headerPanel.add(lastNameLable, constraints);

        JLabel emailLable=new JLabel(email);
        //emailLable.setVerticalAlignment(JLabel.TOP);
        //emailLable.setHorizontalAlignment(JLabel.CENTER);
        emailLable.setFont(font);
        emailLable.setForeground(Color.BLACK);


        constraints.gridy = 2;
        constraints.gridheight=1;
        headerPanel.add(emailLable, constraints);


        headerPanel.setOpaque(false);
        //headerPanel.setBackground(new Color(193, 193, 209));
        add(headerPanel);

    }
}

