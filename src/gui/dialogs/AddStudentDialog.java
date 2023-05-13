package gui.dialogs;

import gui.MainFrame;
import entity.Group;
import listeners.AddStudentDialogListener;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class AddStudentDialog extends JDialog {
	private final JTextField firstNameField;
	private final JTextField lastNameField;
	private final JTextField middleNameField;
	private final JTextField photoPathField;
	private final JComboBox<Group> groupField;
	private final JTextField emailField;
	private final JTextField telephoneField;
	private final JButton okButton;
	private File photoPath;
	protected MainFrame mainFrame;

	public File getPhotoPath() {
		return photoPath;
	}

	public JButton getOkButton() {
		return okButton;
	}

	public AddStudentDialog(JFrame parent) {
		super(parent, "Добавить студента", true);
		mainFrame = (MainFrame) parent;
		photoPath = new File("photos/default.jpg");
		JLabel firstNameLabel = new JLabel("Имя:");
		firstNameField = new JTextField(20);
		JLabel lastNameLabel = new JLabel("Фамилия:");
		lastNameField = new JTextField(20);
		JLabel middleNameLabel = new JLabel("Отчество:");
		middleNameField = new JTextField(20);
		JLabel groupLabel = new JLabel("Группа:");
		groupField = new JComboBox<>(((MainFrame) parent).getGroups().toArray(new Group[0]));
		groupField.setSelectedItem(mainFrame.getCurrentGroup());
		JLabel emailLabel = new JLabel("Email:");
		emailField = new JTextField(20);
		JLabel telephoneLbl = new JLabel("Telephone:");
		telephoneField = new JTextField(20);
		JLabel photoLabel = new JLabel("Путь к фото:");
		JButton photoButton = new JButton("Выбрать файл");
		photoPathField = new JTextField(20);

		JPanel studentDataPanel = new JPanel(new GridLayout(10,2));
		studentDataPanel.add(lastNameLabel);
		studentDataPanel.add(lastNameField);
		studentDataPanel.add(firstNameLabel);
		studentDataPanel.add(firstNameField);
		studentDataPanel.add(middleNameLabel);
		studentDataPanel.add(middleNameField);
		studentDataPanel.add(groupLabel);
		studentDataPanel.add(groupField);
		studentDataPanel.add(emailLabel);
		studentDataPanel.add(emailField);
		studentDataPanel.add(telephoneLbl);
		studentDataPanel.add(telephoneField);
		studentDataPanel.add(photoLabel);
		studentDataPanel.add(photoPathField);
		studentDataPanel.add(new JLabel());
		studentDataPanel.add(photoButton);
		studentDataPanel.add(new JLabel());
		studentDataPanel.add(new JLabel());


		setLayout(new BorderLayout());
	getContentPane().add(studentDataPanel,BorderLayout.CENTER);


		photoButton.addActionListener(e -> {
			JFileChooser fileChooser = new JFileChooser();
			int result = fileChooser.showOpenDialog(AddStudentDialog.this);
			if (result == JFileChooser.APPROVE_OPTION) {
				photoPath = fileChooser.getSelectedFile();
				photoPathField.setText(photoPath.getPath());
			}
		});

		okButton = new JButton("ОК");
		okButton.addActionListener(e -> dispose());


		JButton cancelButton = new JButton("Отмена");
		cancelButton.addActionListener(e -> dispose());
		studentDataPanel.add(okButton);
		studentDataPanel.add(cancelButton);
		AddStudentDialogListener AddStudentDialogListener = new AddStudentDialogListener(this);
		okButton.addActionListener(AddStudentDialogListener);

		pack();
		setLocationRelativeTo(parent);
	}


	public String getFirstName() {
		return firstNameField.getText();
	}

	public String getLastName() {
		return lastNameField.getText();
	}

	public String getMiddleName() {
		return middleNameField.getText();
	}

	public String getEmail() {
		return emailField.getText();
	}

	public JComboBox<Group> getGroupField() {
		return groupField;
	}

	public String getTelephone() {
		return telephoneField.getText();
	}
	public MainFrame getMainWindow() {
		return mainFrame;
	}
}

