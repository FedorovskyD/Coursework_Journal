package Information;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class StudentJournal extends JFrame implements ActionListener {
    // Объявляем компоненты GUI
    private JLabel lblTitle, lblName, lblGrade;
    private JTextField txtName, txtGrade;
    private JButton btnAdd, btnClear, btnExit;
    private JTextArea txtOutput;

    // Конструктор класса
    public StudentJournal() {
        // Устанавливаем заголовок окна
        setTitle("Электронный журнал студентов");

        // Создаем компоненты GUI
        lblTitle = new JLabel("Добавление оценок студентов");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 16));
        lblName = new JLabel("Имя студента:");
        lblGrade = new JLabel("Оценка:");
        txtName = new JTextField(20);
        txtGrade = new JTextField(3);
        btnAdd = new JButton("Добавить");
        btnClear = new JButton("Очистить");
        btnExit = new JButton("Выйти");
        txtOutput = new JTextArea(10, 30);
        txtOutput.setEditable(false);

        // Создаем панель для кнопок
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnClear);
        buttonPanel.add(btnExit);

        // Создаем панель для ввода данных
        JPanel inputPanel = new JPanel(new GridLayout(2, 2));
        inputPanel.add(lblName);
        inputPanel.add(txtName);
        inputPanel.add(lblGrade);
        inputPanel.add(txtGrade);

        // Добавляем компоненты на форму
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(lblTitle, BorderLayout.NORTH);
        getContentPane().add(inputPanel, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        getContentPane().add(new JScrollPane(txtOutput), BorderLayout.EAST);

        // Добавляем обработчики событий на кнопки
        btnAdd.addActionListener(this);
        btnClear.addActionListener(this);
        btnExit.addActionListener(this);

        // Устанавливаем размеры окна и делаем его видимым
        setSize(500, 300);
        setVisible(true);
    }

    // Метод, вызываемый при нажатии на кнопки
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnAdd) {
            String name = txtName.getText();
            String grade = txtGrade.getText();
            txtOutput.append(name + ": " + grade + "\n");
            txtName.setText("");
            txtGrade.setText("");
        } else if (e.getSource() == btnClear) {
            txtOutput.setText("");
            txtName.setText("");
            txtGrade.setText("");
        } else if (e.getSource() == btnExit) {
            System.exit(0);
        }
    }

    // Точка входа в программу
    public static void main(String[] args) {
        new StudentJournal();
    }
}

