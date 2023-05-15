package gui.dialogs;

import gui.MainFrame;
import entity.Group;
import listeners.AddLessonDialogListener;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddLessonDialog extends JDialog {

	private final JTextField nameField;
	private final JRadioButton radioButtonLecture;
	private final JSpinner dateSpinner;
	private final JTextField roomField;
	private final JComboBox<Group> groupComboBox;
	private final JButton addButton;

	private final JCheckBox holidayCheckBox;

	private final MainFrame mainFrame;

	public AddLessonDialog(JFrame parent) {
		super(parent, "Добавление занятия", true);
		mainFrame = (MainFrame) parent;
		JLabel lessonTypeLabel = new JLabel("Тип занятия:");
		JLabel holidayLabel = new JLabel("Празничный день:");
		JLabel nameLabel = new JLabel("Название:");
		JLabel roomLabel = new JLabel("Аудитория:");
		JLabel dateLabel = new JLabel("Дата:");
		JLabel groupLabel = new JLabel("Группа:");
		JRadioButton radioButtonLab = new JRadioButton("Лабораторная", true);
		radioButtonLecture = new JRadioButton("Лекция");
		ButtonGroup buttonGroupLessonType = new ButtonGroup();
		buttonGroupLessonType.add(radioButtonLab);
		buttonGroupLessonType.add(radioButtonLecture);
		JPanel panelLessonType = new JPanel(new GridLayout(1,2));
		panelLessonType.add(radioButtonLab);
		panelLessonType.add(radioButtonLecture);
		nameField = new JTextField(20);
		roomField = new JTextField(20);
		holidayCheckBox =new JCheckBox();
		groupComboBox = new JComboBox<>(new DefaultComboBoxModel<>(mainFrame.getGroups().toArray(new Group[0])));
		groupComboBox.setSelectedItem(mainFrame.getCurrentGroup());
		SpinnerDateModel dateModel = new SpinnerDateModel();
		dateSpinner = new JSpinner(dateModel);
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateSpinner, dateFormat.toPattern());
		dateSpinner.setEditor(dateEditor);

		addButton = new JButton("Добавить");
		JButton cancelButton = new JButton("Отмена");

		JPanel panel = new JPanel(new GridLayout(7, 2));
		panel.add(lessonTypeLabel);
		panel.add(panelLessonType);
		panel.add(holidayLabel);
		panel.add(holidayCheckBox);
		panel.add(nameLabel);
		panel.add(nameField);
		panel.add(roomLabel);
		panel.add(roomField);
		panel.add(dateLabel);
		panel.add(dateSpinner);
		panel.add(groupLabel);
		panel.add(groupComboBox);
		panel.add(addButton);
		panel.add(cancelButton);
		cancelButton.addActionListener(e -> {
			dispose();
		});
		AddLessonDialogListener addLessonDialogListener = new AddLessonDialogListener(this);
		addButton.addActionListener(addLessonDialogListener);
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

	public Date getDate() {
		return ((SpinnerDateModel)dateSpinner.getModel()).getDate();
	}

	public JRadioButton getRadioButtonLecture() {
		return radioButtonLecture;
	}

	public JButton getAddButton() {
		return addButton;
	}

	public MainFrame getMainWindow() {
		return mainFrame;
	}

	public JCheckBox getHolidayCheckBox() {
		return holidayCheckBox;
	}
}
