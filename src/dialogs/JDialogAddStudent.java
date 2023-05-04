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
	private JComboBox<Group> groupField;
	private JTextField emailField, telephoneField;
	private JLabel photoLabel;
	private JButton photoButton;
	private JButton okButton;
	private JButton cancelButton;
	private File photoPath ;
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
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.insets = new Insets(5, 5, 5, 5);

		JLabel firstNameLabel = new JLabel("Имя:");
		constraints.gridx = 0;
		constraints.gridy = 0;
		panel.add(firstNameLabel, constraints);

		firstNameField = new JTextField(20);
		constraints.gridx = 1;
		constraints.gridy = 0;
		panel.add(firstNameField, constraints);

		JLabel lastNameLabel = new JLabel("Фамилия:");
		constraints.gridx = 0;
		constraints.gridy = 1;
		panel.add(lastNameLabel, constraints);

		lastNameField = new JTextField(20);
		constraints.gridx = 1;
		constraints.gridy = 1;
		panel.add(lastNameField, constraints);

		JLabel middleNameLabel = new JLabel("Отчество:");
		constraints.gridx = 0;
		constraints.gridy = 2;
		panel.add(middleNameLabel, constraints);

		middleNameField = new JTextField(20);
		constraints.gridx = 1;
		constraints.gridy = 2;
		panel.add(middleNameField, constraints);

		JLabel groupLabel = new JLabel("Группа:");
		constraints.gridx = 0;
		constraints.gridy = 3;
		panel.add(groupLabel, constraints);

		groupField = new JComboBox<>(((MainWindow)parent).getGroups().toArray(new Group[0]));
		constraints.gridx = 1;
		constraints.gridy = 3;
		panel.add(groupField, constraints);

		JLabel emailLabel = new JLabel("Email:");
		constraints.gridx = 0;
		constraints.gridy = 4;
		panel.add(emailLabel, constraints);

		emailField = new JTextField(20);
		constraints.gridx = 1;
		constraints.gridy = 4;
		panel.add(emailField, constraints);

		JLabel telephoneLbl = new JLabel("Telephone:");
		constraints.gridx = 0;
		constraints.gridy = 5;
		panel.add(telephoneLbl, constraints);

		telephoneField = new JTextField(20);
		constraints.gridx = 1;
		constraints.gridy = 5;
		panel.add(telephoneField, constraints);



		photoLabel = new JLabel("Фото:");
		constraints.gridx = 0;
		constraints.gridy = 6;
		panel.add(photoLabel, constraints);

		photoButton = new JButton("Выбрать файл");
		photoButton.addActionListener(e -> {
			JFileChooser fileChooser = new JFileChooser();
			int result = fileChooser.showOpenDialog(JDialogAddStudent.this);
			if (result == JFileChooser.APPROVE_OPTION) {
				photoPath = fileChooser.getSelectedFile();

				try {
					BufferedImage image = ImageIO.read(photoPath);
					// Масштабируем изображение и создаем иконку
					ImageIcon icon = new ImageIcon(image.getScaledInstance(photoLabel.getWidth(), photoLabel.getHeight(), Image.SCALE_SMOOTH));
					// Устанавливаем иконку изображения в JLabel
					photoLabel.setIcon(icon);
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		});
		constraints.gridx = 1;
		constraints.gridy = 6;
		panel.add(photoButton, constraints);

		photoLabel = new JLabel();
		constraints.gridx = 2;
		constraints.gridy = 6;
		photoLabel.setPreferredSize(new Dimension(160, 200));
		panel.add(photoLabel, constraints);

		okButton = new JButton("ОК");
		okButton.addActionListener(e -> {
			ok = true;
			dispose();
		});
		constraints.gridx = 1;
		constraints.gridy = 7;
		panel.add(okButton, constraints);

		cancelButton = new JButton("Отмена");
		cancelButton.addActionListener(e -> dispose());
		constraints.gridx = 2;
		constraints.gridy = 7;
		panel.add(cancelButton, constraints);
		ListenerJDialogAddStudent listenerJDialogAddStudent = new ListenerJDialogAddStudent(this);
		okButton.addActionListener(listenerJDialogAddStudent);

		getContentPane().add(panel, BorderLayout.CENTER);

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

	public String getTelephone(){return telephoneField.getText();}

	public ImageIcon getPhoto() {
		return (ImageIcon) photoLabel.getIcon();
	}

	public boolean isOk() {
		return ok;
	}
}

