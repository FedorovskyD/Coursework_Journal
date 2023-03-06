package Information;

import javax.swing.*;
import java.awt.*;

import static javax.swing.BoxLayout.PAGE_AXIS;

public class InformationFrame extends JFrame{
    public static void main(String[] args) {
        InformationFrame startFrame = new InformationFrame();
        startFrame.setTitle("Carriculum library");
        startFrame.setSize(800,600);
        startFrame.setLocationRelativeTo(null);
        startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        startFrame.setVisible(true);
    }
    public InformationFrame(){

        setLayout(new BoxLayout(getContentPane(),BoxLayout.Y_AXIS));

        /*Создаем все лейблы*/
        JLabel bntuJL = new JLabel("Белорусский национальный технический универистет",SwingConstants.CENTER);
        JLabel fitrJL = new JLabel("Факультет информационных технологий и робототехники");
        JLabel departmentJL = new JLabel("Кафедра программного обеспечения информационных информационных систем и технологий",SwingConstants.CENTER);
        JLabel courseWorkJL = new JLabel("Курсовая Работа",SwingConstants.CENTER);
        JLabel disciplineJL = new JLabel("по дисциплине \"Программирование на Java\"",SwingConstants.CENTER);
        JLabel courseWorkNameJL = new JLabel("Электронный журнал",SwingConstants.CENTER);
        JLabel authorInfoJL = new JLabel("Выполнили: студенты группы 10702420");
        JLabel author1InitialsJL = new JLabel("Синявская Ульяна Александровна");
        JLabel author2InitialsJL = new JLabel("Федоровский Дмитрий Александрович");
        JLabel reviewerInfoJL = new JLabel("Преподаватель:к.ф.-м.н.,доц.");
        JLabel reviewerInitialsJL = new JLabel("Сидорик Валерий Владимирович");
        JLabel placeJL = new JLabel("Минск, 2023",SwingConstants.CENTER);

        /*Создаем панели*/
        JPanel topJP = new JPanel(new BorderLayout());
        JPanel titleJP = new JPanel(new BorderLayout());
        JPanel middleJP = new JPanel(new GridLayout(1,2,15,0));
        JPanel placeJP = new JPanel(new BorderLayout());
        JPanel buttonsJP = new JPanel(new GridLayout(1,2,0,0));

        /*Добавляем панели на фрейм*/
        add(topJP,0);
        add(titleJP,1);
        add(middleJP,2);
        add(placeJP,3);
        add(buttonsJP,4);

        /*Добавляем лейблы на панели, задаем высоту ширину*/
        JPanel bntuJP = new JPanel();
        bntuJP.setPreferredSize(new Dimension(WIDTH,35));
        bntuJP.add(bntuJL);

        JPanel fitrJP = new JPanel();
        fitrJP.setPreferredSize(new Dimension(WIDTH,25));
        fitrJP.add(fitrJL);

        JPanel departmentJP = new JPanel();
        departmentJP.setPreferredSize(new Dimension(WIDTH,40));
        departmentJP.add(departmentJL);

        /*Добавляем 3 панели на topJP*/
        topJP.add(bntuJP,BorderLayout.NORTH);
        topJP.add(fitrJP,BorderLayout.CENTER);
        topJP.add(departmentJP,BorderLayout.SOUTH);

        /*Добавляем лейблы на панели, задаем высоту ширину*/
        JPanel courseWorkJP = new JPanel();
        courseWorkJP.add(courseWorkJL);

        JPanel disciplineJP = new JPanel();
        disciplineJP.add(disciplineJL);

        JPanel titleNameJP = new JPanel();
        titleNameJP.add(courseWorkNameJL);

        /*Добавляем 3 панели на titleJP*/
        titleJP.add(courseWorkJP,BorderLayout.NORTH);
        titleJP.add(disciplineJP,BorderLayout.CENTER);
        titleJP.add(titleNameJP,BorderLayout.SOUTH);

        /*Добавляем 3 панели на middleJP*/
        JPanel iconImageJP = new JPanel();
        ImageIcon logoImage = new ImageIcon("resources/img/journal.jpg");
        JLabel imageJL = new JLabel(new ImageIcon(logoImage.getImage().getScaledInstance(170,170,Image.SCALE_SMOOTH)));
        iconImageJP.add(imageJL);

        JPanel otherInfoJP = new JPanel(new GridLayout(2,1,0,0));
        JPanel authorInfoJP = new JPanel();

        authorInfoJP.setLayout(new BoxLayout(authorInfoJP,BoxLayout.PAGE_AXIS));
        authorInfoJP.add(authorInfoJL);
        authorInfoJP.add(author1InitialsJL);
        authorInfoJP.add(author2InitialsJL);

        JPanel reviewerInfoJP = new JPanel(new BorderLayout());
        reviewerInfoJP.add(reviewerInfoJL,BorderLayout.CENTER);
        reviewerInfoJP.add(reviewerInitialsJL,BorderLayout.SOUTH);

        otherInfoJP.add(authorInfoJP,0);
        otherInfoJP.add(reviewerInfoJP,1);

        middleJP.add(iconImageJP,0);
        middleJP.add(otherInfoJP,1);

        /*SETTING UP PLACE PANEL*/
        placeJP.add(placeJL, BorderLayout.CENTER);
        placeJP.setPreferredSize(new Dimension(WIDTH,10));

        /*Создаем и настраиваем кнопки*/
        JButton nextButton = new JButton("Далее");
        nextButton.setPreferredSize(new Dimension(60,35));
        JButton exitButton = new JButton("Выход");
        exitButton.setPreferredSize(new Dimension(60,35));
        buttonsJP.add(nextButton);
        buttonsJP.add(exitButton);

        nextButton.addActionListener(e->{
            this.dispose();
            new JFrame();
        });

        exitButton.addActionListener(e->{
            this.dispose();
        });
    }
}
