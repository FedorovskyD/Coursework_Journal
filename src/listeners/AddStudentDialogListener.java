package listeners;

import database.dao.impl.StudentDaoImpl;
import dialogs.AddStudentDialog;
import entity.Group;
import entity.Student;
import utils.PhotoUtils;

import javax.swing.*;
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
		if (e.getSource() == addStudentDialog.getAddStudentButton()) {
			Student student = new Student();
			student.setFirstName(addStudentDialog.getFirstName());
			student.setLastName(addStudentDialog.getLastName());
			student.setMiddleName(addStudentDialog.getMiddleName());
			student.setEmail(addStudentDialog.getEmail());
			student.setTelephone(addStudentDialog.getTelephone());
			student.setGroupId(((Group) addStudentDialog.getJcmbGroupNumber().getSelectedItem()).getId());
			long id = StudentDaoImpl.getInstance().save(student);
			;
			System.out.println("Студент с id = " + id + " добавлен");
			student.setId(id);
			if (addStudentDialog.getPhotoPath() != null) {
				try {
					PhotoUtils.getInstance().savePhoto(student, addStudentDialog.getPhotoPath());
				} catch (IOException ex) {
					System.out.println("Фото студента с id = " + student.getId() + " не найдено");
				}
			}
			addStudentDialog.getMainWindow().getCurrentGroup().getStudents().add(student);
			addStudentDialog.getMainWindow().refreshStudentTable();
			addStudentDialog.getMainWindow().getStudentTable().requestFocus();
		} else if (e.getSource() == addStudentDialog.getJbtnChoosePhoto()) {
			JFileChooser fileChooser = new JFileChooser();
			int result = fileChooser.showOpenDialog(addStudentDialog);
			if (result == JFileChooser.APPROVE_OPTION) {
				addStudentDialog.setPhotoPath(fileChooser.getSelectedFile());
				addStudentDialog.getJlblPhotoPath().setText(addStudentDialog.getPhotoPath().toString());
			}
		} else if (e.getSource() == addStudentDialog.getJbtnClose()) {
			addStudentDialog.dispose();
		}
	}
}
