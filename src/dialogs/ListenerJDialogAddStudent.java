package dialogs;

import database.dao.impl.StudentDaoImpl;
import entity.Group;
import entity.Student;
import utils.PhotoUtils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ListenerJDialogAddStudent implements ActionListener {
	private JDialogAddStudent jDialogAddStudent;

	public ListenerJDialogAddStudent(JDialogAddStudent jDialogAddStudent) {
		this.jDialogAddStudent = jDialogAddStudent;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == jDialogAddStudent.getOkButton()) {
			Student student = new Student();
			student.setFirstName(jDialogAddStudent.getFirstName());
			student.setLastName(jDialogAddStudent.getLastName());
			student.setMiddleName(jDialogAddStudent.getMiddleName());
			student.setEmail(jDialogAddStudent.getEmail());
			student.setTelephone(jDialogAddStudent.getTelephone());
			student.setGroupId(((Group) jDialogAddStudent.getGroupField().getSelectedItem()).getId());
			long id = StudentDaoImpl.getInstance().save(student);;
			System.out.println("Student with id = "+id+" was added");
			student.setId(id);
			if(jDialogAddStudent.getPhotoPath()!=null) {
				try {
					PhotoUtils.getInstance().savePhoto(student, jDialogAddStudent.getPhotoPath());
				} catch (IOException ex) {
					throw new RuntimeException(ex);
				}
			}
			jDialogAddStudent.mainWindow.getCurrentGroup().getStudents().add(student);
			jDialogAddStudent.mainWindow.refreshStudentTable();
		}
	}
}
