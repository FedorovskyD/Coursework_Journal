package dialogs;


import connection.MySQLConnector;
import entity.Student;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class AddStudentDialog extends JDialog {
	private JTextField firstNameField;
	private JTextField lastNameField;
	private JTextField middleNameField;
	private JComboBox groupField;
	private JTextField emailField,telephonefield;
	private JLabel photoLabel;
	private JButton photoButton;
	private JButton okButton;
	private JButton cancelButton;

	private boolean ok;

	public AddStudentDialog(JFrame parent) {
		super(parent, "Добавить студента", true);
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

		groupField = new JComboBox<>(MySQLConnector.getAllGroupNumbers().toArray(new String[0]));
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

		JLabel telephonelbl = new JLabel("Telephone:");
		constraints.gridx = 0;
		constraints.gridy = 5;
		panel.add(telephonelbl, constraints);

		telephonefield = new JTextField(20);
		constraints.gridx = 1;
		constraints.gridy = 5;
		panel.add(telephonefield, constraints);

		photoLabel = new JLabel("Фото:");
		constraints.gridx = 0;
		constraints.gridy = 6;
		panel.add(photoLabel, constraints);

		photoButton = new JButton("Выбрать файл");
		photoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				int result = fileChooser.showOpenDialog(AddStudentDialog.this);
				if (result == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser.getSelectedFile();
					try {
						BufferedImage image = ImageIO.read(selectedFile);
						// Масштабируем изображение и создаем иконку
						ImageIcon icon = new ImageIcon(image.getScaledInstance(photoLabel.getWidth(), photoLabel.getHeight(), Image.SCALE_SMOOTH));
						// Устанавливаем иконку изображения в JLabel
						photoLabel.setIcon(icon);
					} catch (IOException ex) {
						ex.printStackTrace();
					}
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
		okButton.addActionListener(e->{
			Student student = new Student();
			student.setName(getFirstName());
			student.setSurname(getLastName());
			student.setGroup(MySQLConnector.getGroupIDByGroupNumber((String) groupField.getSelectedItem()));
			student.setMiddlename(getMiddleName());
			student.setEmail(getEmail());
			student.setTelephone(getTelephone());
			MySQLConnector.addStudent(student);
		});
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ok = true;
				dispose();
			}
		});
		constraints.gridx = 1;
		constraints.gridy = 7;
		panel.add(okButton, constraints);

		cancelButton = new JButton("Отмена");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		constraints.gridx = 2;
		constraints.gridy = 7;
		panel.add(cancelButton, constraints);

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
	public String getTelephone(){return telephonefield.getText();}

	public ImageIcon getPhoto() {
		return (ImageIcon) photoLabel.getIcon();
	}

	public boolean isOk() {
		return ok;
	}
}

