package dialogs;

import MainFrame.MainWindow;
import entity.Group;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class JDialogAddStudent extends JDialog {
	private JTextField firstNameField;
	private JTextField lastNameField;
	private JTextField middleNameField;
	private JTextField photoPathField;
	private JComboBox<Group> groupField;
	private JTextField emailField, telephoneField;
	private JLabel photoLabel;
	private JButton photoButton;
	private JButton okButton;
	private JButton cancelButton;
	private File photoPath;
	private boolean ok;
	protected MainWindow mainWindow;

	public File getPhotoPath() {
		return photoPath;
	}

	public JButton getOkButton() {
		return okButton;
	}

	public JDialogAddStudent(JFrame parent) {
		super(parent, "Добавить студента", true);
		mainWindow = (MainWindow) parent;
		photoPath = new File("photos/default.jpg");
		JLabel firstNameLabel = new JLabel("Имя:");
		firstNameField = new JTextField(20);
		JLabel lastNameLabel = new JLabel("Фамилия:");
		lastNameField = new JTextField(20);
		JLabel middleNameLabel = new JLabel("Отчество:");
		middleNameField = new JTextField(20);
		JLabel groupLabel = new JLabel("Группа:");
		groupField = new JComboBox<>(((MainWindow) parent).getGroups().toArray(new Group[0]));
		groupField.setSelectedItem(mainWindow.getCurrentGroup());
		JLabel emailLabel = new JLabel("Email:");
		emailField = new JTextField(20);
		JLabel telephoneLbl = new JLabel("Telephone:");
		telephoneField = new JTextField(20);
		photoLabel = new JLabel("Путь к фото:");
		photoButton = new JButton("Выбрать файл");
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
			int result = fileChooser.showOpenDialog(JDialogAddStudent.this);
			if (result == JFileChooser.APPROVE_OPTION) {
				photoPath = fileChooser.getSelectedFile();
				photoPathField.setText(photoPath.getPath());
			}
		});

		okButton = new JButton("ОК");
		okButton.addActionListener(e -> {
			ok = true;
			dispose();
		});


		cancelButton = new JButton("Отмена");
		cancelButton.addActionListener(e -> dispose());
		studentDataPanel.add(okButton);
		studentDataPanel.add(cancelButton);
		ListenerJDialogAddStudent listenerJDialogAddStudent = new ListenerJDialogAddStudent(this);
		okButton.addActionListener(listenerJDialogAddStudent);

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

	public ImageIcon getPhoto() {
		return (ImageIcon) photoLabel.getIcon();
	}

	public boolean isOk() {
		return ok;
	}
}

