package dialogs;

import MainFrame.MainWindow;
import connection.MySQLConnector;
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
	private long studentId;
	private MainWindow mainWindow;

	public AddPhotoDialog(Frame parent, long id) {
		super(parent, "Добавить фото", true);
		mainWindow = (MainWindow)parent;
		studentId = id;
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
			Student student =MySQLConnector.getStudentById(studentId);
			student.setId(studentId);
			try {
				PhotoUtils.getInstance().savePhoto(student,selectedFile);
			} catch (IOException ex) {
				throw new RuntimeException(ex);
			}
			MySQLConnector.addPhotoPath(student.getPhotoPath(), studentId);
			Image image = PhotoUtils.getInstance().loadPhoto(student).getImage();
			//JLabel photoLabel = mainWindow.getStudentCard().getPhotoLabel();
//			photoLabel.setSize(new Dimension(160,200));
//			ImageIcon icon = new ImageIcon(image.getScaledInstance(photoLabel.getWidth(), photoLabel.getHeight(), Image.SCALE_SMOOTH));
//			photoLabel.setIcon(icon);
			dispose();
		} else if (e.getSource() == cancelButton) {
			selectedFile = null;
			dispose();
		}
	}
}
