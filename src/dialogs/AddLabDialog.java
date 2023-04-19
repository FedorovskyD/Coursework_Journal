package dialogs;

import MainFrame.Listener;
import com.toedter.calendar.JDateChooser;
import connection.MySQLConnector;

import javax.swing.*;
import java.awt.*;
import java.util.Date;

public class AddLabDialog extends JDialog  {

	private JLabel nameLabel, roomLabel, dateLabel, groupLabel;
	private JTextField nameField, roomField;
	private JComboBox<String> groupComboBox;
	private JDateChooser dateChooser;
	private JButton addButton, cancelButton;
	private boolean isAddButtonPressed = false;

	public AddLabDialog(JFrame parent) {
		super(parent, "Добавление лабораторного занятия", true);

		nameLabel = new JLabel("Название:");
		roomLabel = new JLabel("Аудитория:");
		dateLabel = new JLabel("Дата:");
		groupLabel = new JLabel("Группа:");

		nameField = new JTextField(20);
		roomField = new JTextField(20);

		groupComboBox = new JComboBox<>(MySQLConnector.getAllGroupNumbers().toArray(new String[0]));
		dateChooser = new JDateChooser(new Date());

		addButton = new JButton("Добавить");
		cancelButton = new JButton("Отмена");



		JPanel panel = new JPanel(new GridLayout(5, 2));
		panel.add(nameLabel);
		panel.add(nameField);
		panel.add(roomLabel);
		panel.add(roomField);
		panel.add(dateLabel);
		panel.add(dateChooser);
		panel.add(groupLabel);
		panel.add(groupComboBox);
		panel.add(addButton);
		panel.add(cancelButton);
		cancelButton.addActionListener(e-> {
		dispose();
		});

addButton.addActionListener(new Listener(this,parent));
		this.setContentPane(panel);
		this.pack();
		this.setLocationRelativeTo(null);
	}

	public JButton getAddButton() {
		return addButton;
	}

	public boolean isAddButtonPressed() {
		return isAddButtonPressed;
	}

	public JLabel getNameLabel() {
		return nameLabel;
	}

	public JLabel getRoomLabel() {
		return roomLabel;
	}

	public JLabel getDateLabel() {
		return dateLabel;
	}

	public JLabel getGroupLabel() {
		return groupLabel;
	}

	public JTextField getNameField() {
		return nameField;
	}

	public JTextField getRoomField() {
		return roomField;
	}

	public JComboBox<String> getGroupComboBox() {
		return groupComboBox;
	}

	public JDateChooser getDateChooser() {
		return dateChooser;
	}

	public JButton getCancelButton() {
		return cancelButton;
	}
}
