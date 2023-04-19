package MainFrame;

import connection.MySQLConnector;
import dialogs.AddLabDialog;
import dialogs.AddStudentDialog;
import entity.Lab;
import entity.Student;
import utils.PhotoUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class Listener implements ActionListener {
	private MainWindow mainWindow;
	private AddStudentDialog addStudentDialog;

	private AddLabDialog  addLabDialog;

	public Listener(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
	}

	public Listener(AddStudentDialog addStudentDialog, JFrame mainWindow) {
		this.mainWindow = (MainWindow) mainWindow;
		this.addStudentDialog = addStudentDialog;
	}
	public Listener(AddLabDialog addStudentDialog, JFrame mainWindow) {
		this.mainWindow = (MainWindow) mainWindow;
		this.addLabDialog = addStudentDialog;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == mainWindow.getAddStudentBtn()) {
			AddStudentDialog dialog = new AddStudentDialog(mainWindow);
			dialog.setVisible(true);
		}  else if (addStudentDialog != null && e.getSource() == addStudentDialog.getOkButton()) {
			Student student = new Student();
			student.setName(addStudentDialog.getFirstName());
			student.setSurname(addStudentDialog.getLastName());
			student.setGroupID(MySQLConnector.getGroupIDByGroupNumber((String) addStudentDialog.getGroupField().getSelectedItem()));
			student.setMiddleName(addStudentDialog.getMiddleName());
			student.setEmail(addStudentDialog.getEmail());
			student.setTelephone(addStudentDialog.getTelephone());
			long id = MySQLConnector.addStudent(student);
			System.out.println("Student with id = "+id+" was added");
			student.setId(id);
			if(addStudentDialog.getPhotoPath()!=null) {
				try {
					PhotoUtils.getInstance().savePhoto(student, addStudentDialog.getPhotoPath());
				} catch (IOException ex) {
					throw new RuntimeException(ex);
				}
				MySQLConnector.addPhotoPath(student.getPhotoPath(), id);
			}
			String selectedGroup = (String) mainWindow.getGroupNumberCmb().getSelectedItem();

		} else if (addLabDialog != null && e.getSource() == addLabDialog.getAddButton()) {
			int groupID = MySQLConnector.getGroupIDByGroupNumber(Objects.requireNonNull(addLabDialog.getGroupComboBox().getSelectedItem()).toString());
			Lab lab = new Lab(addLabDialog.getRoomField().getText(),
					addLabDialog.getDateChooser().getDate(),groupID,addLabDialog.getNameField().getText());
			MySQLConnector.addLab(lab);
		}
	}
}
