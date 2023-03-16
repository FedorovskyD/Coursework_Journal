package MainFrame;

import dialogs.AboutDialog;
import connection.MySQLConnector;
import dialogs.AddGroupDialog;
import dialogs.AddStudentDialog;
import dialogs.DeleteGroupDialog;
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

public class MainWindow extends JFrame {
    private JMenuBar menuBar;
    private JMenu fileMenu;
    private final JLabel groupNumberLbl;
    private JComboBox<String> groupNumberCmb;
    private StudentCardPanel studentCard;
    private JButton addStudentBtn, addGroupBtn, deleteGroupBtn,aboutAuthorBtn,deleteStudentBtn;

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
        studentCard = new StudentCardPanel();

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
        addGroupBtn = new JButton("Добавить группу");
        deleteGroupBtn = new JButton("Удалить группу");
        aboutAuthorBtn=new JButton("Об авторе");
        deleteStudentBtn = new JButton("Удалить студента");
        MainWindow mainWindow = this;
        addStudentBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddStudentDialog addStudentDialog = new AddStudentDialog(mainWindow);
                addStudentDialog.setVisible(true);
            }
        });
        deleteStudentBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = studentTable.getSelectedRow();
                if (selectedRow != -1) {
                    long data = Long.parseLong(studentTable.getValueAt(selectedRow, 4).toString()); // Получаем данные из выделенной строки

                    studentCard.getPhoneLabel().setText("");
                    studentCard.getFullNameLabel().setText("");
                    studentCard.getEmailLabel().setText("");
                    String selectedGroup = (String) mainWindow.getGroupNumberCmb().getSelectedItem();
                    if(MySQLConnector.deleteStudent(data)){
                        System.out.println("Student was deleted");
                    }
                    List<Student> students = MySQLConnector.getAllStudentsByGroup(selectedGroup);
                    DefaultTableModel model = (DefaultTableModel) mainWindow.getStudentTable().getModel();
                    model.setRowCount(0); // удаление всех строк
                    for (Student student : students) {
                        model.addRow(new Object[]{student.getSurname(), student.getName(), student.getMiddleName(),
                                student.getEmail(),student.getId()});
                    }

                }
            }
        });
        addGroupBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddGroupDialog addGroupDialog = new AddGroupDialog(mainWindow);
                addGroupDialog.setVisible(true);
            }
        });
        deleteGroupBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DeleteGroupDialog deleteGroupDialog = new DeleteGroupDialog(mainWindow);
                deleteGroupDialog.setVisible(true);
            }
        });
        aboutAuthorBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AboutDialog aboutAuthorDialog = new AboutDialog(mainWindow);
                aboutAuthorDialog.setVisible(true);
            }
        });
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
        studentTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Выбор только одной строки
        studentTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (isSelected) {
                    setBackground(Color.YELLOW); // Задаем желтый фон для выделенной строки
                } else {
                    setBackground(table.getBackground()); // Возвращаем цвет фона по умолчанию
                }
                return this;
            }
        });

        studentTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = studentTable.getSelectedRow();
                    if (selectedRow != -1) {
                        long data = Long.parseLong(studentTable.getValueAt(selectedRow, 4).toString()); // Получаем данные из выделенной строки

                        Student student = MySQLConnector.getStudentById(data);
                        studentCard.getFullNameLabel().setText(student.getSurname() + " " + student.getName() +
                                " " + student.getMiddleName());
                        studentCard.getEmailLabel().setText(student.getEmail());
                        studentCard.getPhoneLabel().setText(student.getTelephone());
                    }
                }
            }
        });
        TableColumn column = studentTable.getColumnModel().getColumn(4);

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
        }
        GroupLayout groupLayout = new GroupLayout(getContentPane());
        getContentPane().setLayout(groupLayout);
        groupLayout.setAutoCreateGaps(true);
        groupLayout.setAutoCreateContainerGaps(true);
            // Компановка главной панели
            groupLayout.setHorizontalGroup(groupLayout.createParallelGroup()
                    .addGroup(groupLayout.createSequentialGroup()
                            .addComponent(groupNumberLbl)
                            .addComponent(groupNumberCmb))
                    .addGroup(groupLayout.createSequentialGroup()
                            .addComponent(scrollPane)
                            .addComponent(studentCard)
                            .addGroup(groupLayout.createParallelGroup()

                                    .addComponent(addStudentBtn)
                                    .addComponent(addGroupBtn)
                                    .addComponent(deleteGroupBtn)
                                    .addComponent(deleteStudentBtn)
                                    .addComponent(aboutAuthorBtn))
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

                            .addComponent(addStudentBtn)
                            .addComponent(addGroupBtn)
                            .addComponent(deleteGroupBtn)
                            .addComponent(deleteStudentBtn)
                            .addComponent(aboutAuthorBtn))
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
                        model.addRow(new Object[]{student.getSurname(), student.getName(), student.getMiddleName(),
                                student.getEmail(),student.getId()});
                    }
                }
            });
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
