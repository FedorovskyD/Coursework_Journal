package dialogs;

import database.dao.impl.StudentDaoImpl;
import entity.Group;
import entity.Student;
import utils.PhotoUtils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class AddStudentDialogListener implements ActionListener {
	private AddStudentDialog addStudentDialog;

	public AddStudentDialogListener(AddStudentDialog addStudentDialog) {
		this.addStudentDialog = addStudentDialog;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == addStudentDialog.getOkButton()) {
			Student student = new Student();
			student.setFirstName(addStudentDialog.getFirstName());
			student.setLastName(addStudentDialog.getLastName());
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
			}
			StudentDaoImpl.getInstance().update(student);
			addStudentDialog.mainWindow.getCurrentGroup().getStudents().add(student);
			addStudentDialog.mainWindow.updateStudentTable();
		}
	}
}
