package dialogs;

import MainFrame.MainWindow;
import connection.MySQLConnector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddGroupDialog extends JDialog {

	private JTextField groupNameField;
	private JButton okButton, cancelButton;
	private boolean okPressed;
	private MainWindow mainWindow;

	public AddGroupDialog(JFrame parent) {

		super(parent, "Add Group", true); // Устанавливаем заголовок и режим модальности
		setSize(300, 150);
		setLocationRelativeTo(null); // Центрируем диалоговое окно
		mainWindow = (MainWindow)parent;
		// Создаем компоненты для ввода данных
		JLabel groupNameLabel = new JLabel("Group name:");
		groupNameField = new JTextField();

		// Создаем кнопки
		okButton = new JButton("OK");
		cancelButton = new JButton("Cancel");

		// Добавляем обработчики событий для кнопок
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			MySQLConnector.addGroup(groupNameField.getText());
				okPressed = true;
				dispose(); // Закрываем диалоговое окно
			}
		});

		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				okPressed = false;
				dispose(); // Закрываем диалоговое окно
			}
		});

		// Создаем панель для компонентов ввода данных
		JPanel inputPanel = new JPanel(new GridLayout(1, 2, 5, 5));
		inputPanel.add(groupNameLabel);
		inputPanel.add(groupNameField);

		// Создаем панель для кнопок
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
		buttonPanel.add(okButton);
		buttonPanel.add(cancelButton);

		// Добавляем панели на диалоговое окно
		getContentPane().setLayout(new BorderLayout(10, 10));
		getContentPane().add(inputPanel, BorderLayout.CENTER);
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
	}

	public String getGroupName() {
		return groupNameField.getText();
	}

	public boolean isOkPressed() {
		return okPressed;
	}
}
