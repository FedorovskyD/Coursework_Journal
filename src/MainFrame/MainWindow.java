package MainFrame;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import static javax.swing.BorderFactory.createLineBorder;

public class MainWindow extends JFrame {
    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JButton aboutAuthorBtn;
    private JButton aboutProgramBtn;
    private  JButton exitBtn;
    private JLabel groupNumberLbl;
    private JComboBox groupNumberCmb;
    private JPanel studentCard;
    public MainWindow (){

        // Создание пунктов меню
        JMenuItem aboutAuthorItem = new JMenuItem("About Author");
        JMenuItem aboutProgrammItem = new JMenuItem("About programm");
        JMenuItem exitItem = new JMenuItem("Exit");

        exitBtn = new JButton("Exit");
        aboutProgramBtn = new JButton("aboutProgramm");
        aboutAuthorBtn = new JButton("aboutAuthor");

        groupNumberLbl=new JLabel("Group Number");
        groupNumberCmb=new JComboBox();
        groupNumberCmb.setPreferredSize(new Dimension(15,20));
        groupNumberCmb.setMaximumSize(new Dimension(200, 24));

//        studentCard.setForeground(Color.WHITE);

        JPanel studentCard = new RoundedPanel(100);
        studentCard.setBackground(new Color(193, 193, 209));
        studentCard.setBorder(createLineBorder(new Color(117, 117, 142), 100, true));

        JButton btn1=new JButton("Button 1");
        JButton btn2=new JButton("Button 2");
        JButton btn3=new JButton("Button 3");

        fileMenu = new JMenu("File");
        menuBar = new JMenuBar();

        fileMenu.add(aboutAuthorItem);
        fileMenu.add(aboutProgrammItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        menuBar.add(fileMenu);
        menuBar.add(aboutAuthorBtn);
        menuBar.add(aboutProgramBtn);
        menuBar.add(exitBtn);
        setJMenuBar(menuBar);

        Object[][] data = {
                {"Иванов", "Иван", "Иванович"},
                {"Петров", "Петр", "Петрович"},
                {"Сидоров", "Сидор", "Сидорович"}
        };

        // Создание заголовков столбцов
        String[] columns = {"Фамилия", "Имя", "Отчество"};
        DefaultTableModel model = new DefaultTableModel(data, columns);

        // Создание таблицы и установка модели
        JTable table = new JTable(model);

        // Установка цвета для четных строк
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (row % 2 == 0) {
                    c.setBackground(Color.LIGHT_GRAY);
                } else {
                    c.setBackground(Color.WHITE);
                }
                return c;
            }
        });

        // Создание панели с прокруткой и добавление таблицы на нее
        JScrollPane scrollPane = new JScrollPane(table);

        // Компоновка
        GroupLayout groupLayout=new GroupLayout(getContentPane());
        getContentPane().setLayout(groupLayout);
        groupLayout.setAutoCreateGaps(true);
        groupLayout.setAutoCreateContainerGaps(true);

        groupLayout.setHorizontalGroup(groupLayout.createParallelGroup()
                .addGroup(groupLayout.createSequentialGroup()
                        .addComponent(groupNumberLbl)
                        .addComponent(groupNumberCmb))
                .addGroup(groupLayout.createSequentialGroup()
                        .addComponent(table)
                        .addComponent(studentCard)
                        .addGroup(groupLayout.createParallelGroup()
                                .addComponent(btn1)
                                .addComponent(btn2)
                                .addComponent(btn3)))

        );
        groupLayout.setVerticalGroup(groupLayout.createParallelGroup()
                .addGroup(groupLayout.createSequentialGroup()
                        .addGroup(groupLayout.createParallelGroup()
                                .addComponent(groupNumberLbl)
                                .addComponent(groupNumberCmb))
                        .addComponent(table))
                .addComponent(studentCard)
                .addGroup(groupLayout.createSequentialGroup()
                        .addComponent(btn1)
                        .addComponent(btn2)
                        .addComponent(btn3))
        );


    }

    public static void main(String[] args) {
        JFrame mainFrame=new MainWindow();
        mainFrame.setTitle("Student journal");
        mainFrame.setSize(new Dimension(1920,1080));
        mainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
        //mainFrame.pack();
    }

}
