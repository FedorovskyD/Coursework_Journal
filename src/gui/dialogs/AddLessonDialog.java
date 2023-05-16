package gui.dialogs;

import gui.MainFrame;
import entity.Group;
import listeners.AddLessonDialogListener;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddLessonDialog extends JDialog {
	private final JRadioButton radioButtonLecture;
	private final JSpinner dateSpinner;
	private final JComboBox<Group> groupComboBox;
	private final JButton addButton;

	private final JRadioButton isHolidayRadioButton;

	private final MainFrame mainFrame;

	public AddLessonDialog(JFrame parent) {
		super(parent, "Добавление занятия", true);
		mainFrame = (MainFrame) parent;
		JLabel lessonTypeLabel = new JLabel("Тип занятия:");
		JLabel holidayLabel = new JLabel("Праздничный день:");
		JLabel dateLabel = new JLabel("Дата:");
		JLabel groupLabel = new JLabel("Группа:");
		JRadioButton radioButtonLab = new JRadioButton("Лабораторная", mainFrame.getRadioBtnLab().isSelected());
		radioButtonLecture = new JRadioButton("Лекция",mainFrame.getRadioBtnLecture().isSelected());
		ButtonGroup buttonGroupLessonType = new ButtonGroup();
		ButtonGroup buttonGroupHoliday = new ButtonGroup();
		buttonGroupLessonType.add(radioButtonLab);
		buttonGroupLessonType.add(radioButtonLecture);
		JPanel panelLessonType = new JPanel(new GridLayout(1, 2));
		panelLessonType.add(radioButtonLab);
		panelLessonType.add(radioButtonLecture);
		isHolidayRadioButton = new JRadioButton("Да", false);
		JRadioButton notHoliday = new JRadioButton("Нет", true);
		buttonGroupHoliday.add(isHolidayRadioButton);
		buttonGroupHoliday.add(notHoliday);
		JPanel holidayOptionPanel = new JPanel(new GridLayout(1, 2));
		holidayOptionPanel.add(isHolidayRadioButton);
		holidayOptionPanel.add(notHoliday);
		groupComboBox = new JComboBox<>(new DefaultComboBoxModel<>(mainFrame.getGroups().toArray(new Group[0])));
		groupComboBox.setSelectedItem(mainFrame.getCurrentGroup());
		SpinnerDateModel dateModel = new SpinnerDateModel();
		dateSpinner = new JSpinner(dateModel);
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateSpinner, dateFormat.toPattern());
		dateSpinner.setEditor(dateEditor);

		addButton = new JButton("Добавить");
		JButton cancelButton = new JButton("Отмена");

		JPanel panel = new JPanel(new GridLayout(5, 2));
		panel.add(lessonTypeLabel);
		panel.add(panelLessonType);
		panel.add(holidayLabel);
		panel.add(holidayOptionPanel);
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


	public JComboBox<Group> getGroupComboBox() {
		return groupComboBox;
	}

	public Date getDate() {
		return ((SpinnerDateModel) dateSpinner.getModel()).getDate();
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

	public JRadioButton getIsHolidayRadioButton() {
		return isHolidayRadioButton;
	}
}
