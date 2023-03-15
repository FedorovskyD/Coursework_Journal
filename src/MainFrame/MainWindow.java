package MainFrame;

import connection.MySQLConnector;
import dialogs.AddStudentDialog;
import entity.Student;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import static javax.swing.BorderFactory.createLineBorder;

public class MainWindow extends JFrame {
    private JMenuBar menuBar;
    private JMenu fileMenu;
    private final JLabel groupNumberLbl;
    private JComboBox<String> groupNumberCmb;
    private StudentCardPanel studentCard;
    private JButton addStudentBtn;

    public JComboBox<String> getGroupNumberCmb() {
        return groupNumberCmb;
    }

    public JTable getStudentTable() {
        return studentTable;
    }

    private JTable studentTable;

    public JButton getAddStudentBtn() {
        return addStudentBtn;
    }

    public MainWindow() {

        // Устанавливаем компоновку GridLayout
        GroupLayout groupLayout = new GroupLayout(getContentPane());
        getContentPane().setLayout(groupLayout);
        groupLayout.setAutoCreateGaps(true);
        groupLayout.setAutoCreateContainerGaps(true);
        // Создание меню
        fileMenu = new JMenu("Файл");
        menuBar = new JMenuBar();
        // Создание пунктов меню
        JMenuItem aboutAuthorItem = new JMenuItem("О авторе");
        JMenuItem aboutProgramItem = new JMenuItem("О программе");
        JMenuItem exitItem = new JMenuItem("Выход");
        // Добавление пунктов в меню
        fileMenu.add(aboutAuthorItem);
        fileMenu.add(aboutProgramItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);
        // Добавление меню на фрейм
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);
        //Добавляем label и combobox для выбора номера группы
        groupNumberLbl = new JLabel("Номер группы:");
        groupNumberCmb = new JComboBox<>(MySQLConnector.getAllGroupNumbers().toArray(new String[0]));
        groupNumberCmb.setPreferredSize(new Dimension(15, 20));
        groupNumberCmb.setMaximumSize(new Dimension(24, 15));
        // Создаем кнопки
        addStudentBtn = new JButton("Добавить студента");
        MainWindow mainWindow = this;
        addStudentBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddStudentDialog addStudentDialog = new AddStudentDialog(mainWindow);
                addStudentDialog.setVisible(true);
            }
        });


//        RoundedPanel studentCard1 = new RoundedPanel(100);
//        studentCard1.setBackground(new Color(193, 193, 209));
//        studentCard1.setBorder(createLineBorder(new Color(117, 117, 142), 100, true));

        // Создаем таблицу для отображения студентов
        // Создание заголовков столбцов
        String[] columns = {"Фамилия", "Имя", "Отчество", "Почта", "ID"};
        // Запрещаем редактирование таблицы
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        // Установка заголовков
        model.setColumnIdentifiers(columns);
        // Создание таблицы и установка модели
        studentTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(studentTable);
        // Установка цвета для четных строк
        studentTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                // Если строка выделена, установить цвет фона
                if (isSelected) {
                    setBackground(table.getSelectionBackground());
                } else {
                    setBackground(table.getBackground());
                }
//				if (row % 2 == 0) {
//					c.setBackground(Color.LIGHT_GRAY);
//				} else {
//					c.setBackground(Color.WHITE);
//				}
                return c;
            }
        });
        // Получить объект ListSelectionModel
        ListSelectionModel selectionModel = studentTable.getSelectionModel();
        // Установить режим выделения
        selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        // Установить цвет фона для выделения
        studentTable.setSelectionBackground(Color.YELLOW);
        studentCard = new StudentCardPanel();
        // Добавляем слушателя событий выбора строки
        studentTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                // Получаем индекс выбранной строки
                int row = studentTable.getSelectedRow();
                // Получаем данные из выбранной строки
                long id = Long.parseLong(studentTable.getValueAt(row, 4).toString());

                Student student = MySQLConnector.getStudentById(id);
                studentCard.getFullNameLabel().setText(student.getSurname() + " " + student.getName() +
                        " " + student.getMiddleName());
                studentCard.getEmailLabel().setText(student.getEmail());
                studentCard.getPhoneLabel().setText(student.getTelephone());
            }
        });

        TableColumn column = studentTable.getColumnModel().getColumn(4);

        // Устанавливаем ширину колонки в 0
        column.setMinWidth(0);
        column.setMaxWidth(0);
        column.setWidth(0);
        column.setPreferredWidth(0);
        // Заполнение таблицы начальными данными
        String selectedGroup = (String) groupNumberCmb.getSelectedItem();
        List<Student> students = MySQLConnector.getAllStudentsByGroup(selectedGroup);
        model.setRowCount(0); // удаление всех строк
        for (Student student : students) {
            model.addRow(new Object[]{student.getSurname(), student.getName(), student.getMiddleName(), student.getEmail(), student.getId()});

            // Компановка главной панели
            groupLayout.setHorizontalGroup(groupLayout.createParallelGroup()
                    .addGroup(groupLayout.createSequentialGroup()
                            .addComponent(groupNumberLbl)
                            .addComponent(groupNumberCmb))
                    .addGroup(groupLayout.createSequentialGroup()
                            .addComponent(scrollPane)
                            .addComponent(studentCard)
                            .addGroup(groupLayout.createParallelGroup()

                                    .addComponent(addStudentBtn))
                    )
            );
            groupLayout.setVerticalGroup(groupLayout.createParallelGroup()
                    .addGroup(groupLayout.createSequentialGroup()
                            .addGroup(groupLayout.createParallelGroup()
                                    .addComponent(groupNumberLbl)
                                    .addComponent(groupNumberCmb))
                            .addComponent(scrollPane))
                    .addComponent(studentCard)
                    .addGroup(groupLayout.createSequentialGroup()

                            .addComponent(addStudentBtn))
            );
            // Добавление слушателей


            groupNumberCmb.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String selectedGroup = (String) mainWindow.getGroupNumberCmb().getSelectedItem();
                    List<Student> students = MySQLConnector.getAllStudentsByGroup(selectedGroup);
                    DefaultTableModel model = (DefaultTableModel) mainWindow.getStudentTable().getModel();
                    model.setRowCount(0); // удаление всех строк
                    for (Student student : students) {
                        model.addRow(new Object[]{student.getSurname(), student.getName(), student.getMiddleName(), student.getEmail()});
                    }
                }
            });
        }
    }

    public static void main(String[] args) {
        JFrame mainFrame = new MainWindow();
        mainFrame.setTitle("Student journal");
        mainFrame.setSize(new Dimension(1920, 1080));
        mainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

}
