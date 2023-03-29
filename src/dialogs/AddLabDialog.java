package dialogs;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.Objects;

import com.toedter.calendar.JDateChooser;
import connection.MySQLConnector;
import entity.Group;
import entity.Lab;

import javax.swing.*;

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

		groupComboBox = new JComboBox<String>(MySQLConnector.getAllGroupNumbers().toArray(new String[0]));
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
		addButton.addActionListener(e -> {
			int groupID = MySQLConnector.getGroupIDByGroupNumber(Objects.requireNonNull(groupComboBox.getSelectedItem()).toString());
			Lab lab = new Lab(roomField.getText(),dateChooser.getDate(),groupID,nameField.getText());
			MySQLConnector.addLab(lab);
		});

		this.setContentPane(panel);
		this.pack();
		this.setLocationRelativeTo(null);
	}

	public boolean isAddButtonPressed() {
		return isAddButtonPressed;
	}
}
