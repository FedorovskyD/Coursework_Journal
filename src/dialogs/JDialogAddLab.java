package dialogs;

import MainFrame.MainWindow;
import com.toedter.calendar.JDateChooser;
import database.dao.impl.GroupDaoImpl;
import entity.Group;

import javax.swing.*;
import java.awt.*;
import java.util.Date;

public class JDialogAddLab extends JDialog {

	private JLabel nameLabel, roomLabel, dateLabel, groupLabel;
	private JTextField nameField, roomField;
	private JComboBox<Group> groupComboBox;
	private JDateChooser dateChooser;
	protected final JButton addButton, cancelButton;
	private boolean isAddButtonPressed = false;
	protected final MainWindow mainWindow;

	public JDialogAddLab(JFrame parent) {
		super(parent, "Добавление лабораторного занятия", true);
		mainWindow = (MainWindow) parent;

		nameLabel = new JLabel("Название:");
		roomLabel = new JLabel("Аудитория:");
		dateLabel = new JLabel("Дата:");
		groupLabel = new JLabel("Группа:");

		nameField = new JTextField(20);
		roomField = new JTextField(20);

		groupComboBox = new JComboBox<>(new DefaultComboBoxModel<>(mainWindow.getGroups().toArray(new Group[0])));
		groupComboBox.setSelectedItem(mainWindow.getCurrentGroup());
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
		cancelButton.addActionListener(e -> {
			dispose();
		});
		ListenerJDialogAddLab listenerJDialogAddLab = new ListenerJDialogAddLab(this);
		addButton.addActionListener(listenerJDialogAddLab);
		this.setContentPane(panel);
		this.pack();
		this.setLocationRelativeTo(null);
	}

	public JTextField getNameField() {
		return nameField;
	}

	public JTextField getRoomField() {
		return roomField;
	}

	public JComboBox<Group> getGroupComboBox() {
		return groupComboBox;
	}

	public JDateChooser getDateChooser() {
		return dateChooser;
	}

}
