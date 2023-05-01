package dialogs;

import MainFrame.MainWindow;
import database.dao.impl.StudentDaoImpl;
import entity.Student;
import utils.PhotoUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class AddPhotoDialog extends JDialog implements ActionListener {
	private JButton chooseFileButton;
	private JTextField filePathTextField;
	private JButton okButton;
	private JButton cancelButton;
	private File selectedFile;
	private Student student;
	private MainWindow mainWindow;

	public AddPhotoDialog(Frame parent, Student student) {
		super(parent, "Добавить фото", true);
		mainWindow = (MainWindow)parent;
		this.student = student;
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		chooseFileButton = new JButton("Выбрать фото ");
		chooseFileButton.addActionListener(this);

		filePathTextField = new JTextField(20);
		filePathTextField.setEditable(false);

		okButton = new JButton("OK");
		okButton.addActionListener(this);

		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(this);

		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 5, 5, 5);
		panel.add(new JLabel("File path:"), gbc);
		gbc.gridx = 1;
		panel.add(filePathTextField, gbc);
		gbc.gridx = 2;
		panel.add(chooseFileButton, gbc);

		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		buttonPanel.add(okButton);
		buttonPanel.add(cancelButton);

		getContentPane().add(panel, BorderLayout.CENTER);
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);

		pack();
		setLocationRelativeTo(null);
		setResizable(false);
	}

	public File getSelectedFile() {
		return selectedFile;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == chooseFileButton) {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			int result = fileChooser.showOpenDialog(this);
			if (result == JFileChooser.APPROVE_OPTION) {
				selectedFile = fileChooser.getSelectedFile();
				filePathTextField.setText(selectedFile.getAbsolutePath());
			}
		} else if (e.getSource() == okButton) {
			try {
				PhotoUtils.getInstance().savePhoto(student,selectedFile);
			} catch (IOException ex) {
				throw new RuntimeException(ex);
			}
			StudentDaoImpl.getInstance().update(student);
			dispose();
		} else if (e.getSource() == cancelButton) {
			selectedFile = null;
			dispose();
		}
	}
}
