package listeners;

import gui.studentTable.studentCard.StudentCardDialog;
import database.dao.impl.StudentDaoImpl;
import entity.Student;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class StudentCardDialogListener implements ActionListener {
	private final StudentCardDialog studentCardDialog;
	public StudentCardDialogListener(StudentCardDialog studentCardDialog){
		this.studentCardDialog = studentCardDialog;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == studentCardDialog.getDeleteButton()){
			JOptionPane optionPane = new JOptionPane("Вы действительно хотите удалить студента?", JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION);
			JDialog dialog = optionPane.createDialog("Подтверждение");
			dialog.setModal(true);
			dialog.setVisible(true);
			int result = (int)optionPane.getValue();
			if (result == JOptionPane.YES_OPTION) {
				String photoPath = studentCardDialog.getMainWindow().getCurrentStudent().getPhotoPath();
				if (photoPath != null) {
					File fileToDelete = new File(photoPath);
					if (fileToDelete.delete()) {
						System.out.println("File deleted successfully.");
					} else {
						System.out.println("Failed to delete the file.");
					}
				}
				if (StudentDaoImpl.getInstance().delete(studentCardDialog.getMainWindow().getCurrentStudent())) {
					studentCardDialog.setVisible(false);
					studentCardDialog.getTxtFullName().setEditable(false);
					studentCardDialog.getTxtEmail().setEditable(false);
					studentCardDialog.getTxtPhone().setEditable(false);
					studentCardDialog.getDeleteButton().setVisible(false);
					studentCardDialog.getEditButton().setText("Редактировать");
					studentCardDialog.getBtnEditPhoto().setVisible(false);
					studentCardDialog.getMainWindow().getCurrentGroup().getStudents().remove(studentCardDialog.getMainWindow().getCurrentStudent());
					studentCardDialog.getMainWindow().refreshStudentTable();

					System.out.println("Student was deleted");
				}
			}
		}else if(e.getSource() == studentCardDialog.getEditButton()){
			if (studentCardDialog.getEditButton().getText().equalsIgnoreCase("Редактировать")) {
				studentCardDialog.getTxtFullName().setEditable(true);
				studentCardDialog.getTxtEmail().setEditable(true);
				studentCardDialog.getTxtPhone().setEditable(true);
				studentCardDialog.getDeleteButton().setVisible(true);
				studentCardDialog.getEditButton().setText("Сохранить");
				studentCardDialog.getCalendarPanel().setEnabled(false);
				studentCardDialog.getBtnEditPhoto().setVisible(true);

			} else {
				int result = JOptionPane.showConfirmDialog(studentCardDialog, "Вы уверены, что хотите изменить данные студента?", "Подтверждение", JOptionPane.YES_NO_OPTION);
				if (result == JOptionPane.YES_OPTION) {
					// выполнить изменения
					Student student1 = studentCardDialog.getMainWindow().getCurrentStudent();
					String fullName = studentCardDialog.getTxtFullName().getText();
					String[] fio = fullName.split(" ");
					student1.setFirstName(fio[1]);
					student1.setLastName(fio[0]);
					student1.setMiddleName(fio[2]);
					student1.setGroupId(studentCardDialog.getCurrStudent().getGroupId());
					student1.setTelephone(studentCardDialog.getTxtPhone().getText());
					student1.setEmail(studentCardDialog.getTxtEmail().getText());
					StudentDaoImpl.getInstance().update(student1);
					SwingUtilities.invokeLater(studentCardDialog.getMainWindow().getStudentTable()::repaint);
				}
				studentCardDialog.getTxtFullName().setEditable(false);
				studentCardDialog.getTxtEmail().setEditable(false);
				studentCardDialog.getTxtPhone().setEditable(false);
				studentCardDialog.getDeleteButton().setVisible(false);
				studentCardDialog.getEditButton().setText("Редактировать");
				studentCardDialog.getCalendarPanel().setEnabled(true);
				studentCardDialog.getBtnEditPhoto().setVisible(false);
				studentCardDialog.getCurrLessonButton().requestFocus();
			}
		}
	}
}
