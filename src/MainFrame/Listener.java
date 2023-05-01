package MainFrame;

import database.dao.impl.LabDaoImpl;
import database.dao.impl.StudentDaoImpl;
import dialogs.AddLabDialog;
import dialogs.AddStudentDialog;
import entity.Group;
import entity.Lab;
import entity.Student;
import utils.PhotoUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
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
		if (addStudentDialog != null && e.getSource() == addStudentDialog.getOkButton()) {
			Student student = new Student();
			student.setName(addStudentDialog.getFirstName());
			student.setSurname(addStudentDialog.getLastName());
			student.setMiddleName(addStudentDialog.getMiddleName());
			student.setEmail(addStudentDialog.getEmail());
			student.setTelephone(addStudentDialog.getTelephone());
			student.setGroup(((Group)addStudentDialog.getGroupField().getSelectedItem()).getId());
			long id = StudentDaoImpl.getInstance().save(student);;
			System.out.println("Student with id = "+id+" was added");
			student.setId(id);
			if(addStudentDialog.getPhotoPath()!=null) {
				try {
					PhotoUtils.getInstance().savePhoto(student, addStudentDialog.getPhotoPath());
				} catch (IOException ex) {
					throw new RuntimeException(ex);
				}

			}else {
				student.setPhotoPath("photos/default.jpg");
			}
			StudentDaoImpl.getInstance().update(student);
			mainWindow.getCurrentGroup().getStudents().add(student);
			mainWindow.updateStudentTable();
		} else if (addLabDialog != null && e.getSource() == addLabDialog.getAddButton()) {
			long groupID = ((Group)addLabDialog.getGroupComboBox().getSelectedItem()).getId();
			Lab lab = new Lab(addLabDialog.getRoomField().getText(),
					addLabDialog.getDateChooser().getDate(),groupID,addLabDialog.getNameField().getText());
			LabDaoImpl.getInstance().save(lab);
		}
	}
}
