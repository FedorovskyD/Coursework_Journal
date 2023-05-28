package listeners;

import gui.StudentCard;
import database.dao.impl.StudentDaoImpl;
import entity.Student;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class StudentCardDialogListener implements ActionListener {
	private final StudentCard studentCard;
	public StudentCardDialogListener(StudentCard studentCard){
		this.studentCard = studentCard;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == studentCard.getJbtnDeleteStudent()){
			JOptionPane optionPane = new JOptionPane("Вы действительно хотите удалить студента?", JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION);
			JDialog dialog = optionPane.createDialog("Подтверждение");
			dialog.setModal(true);
			dialog.setVisible(true);
			int result = (int)optionPane.getValue();
			if (result == JOptionPane.YES_OPTION) {
				File photoPath = studentCard.getMainWindow().getCurrentStudentFromTable().getPhotoPath();
				if (photoPath != null && !photoPath.equals(new File("photos/default.jpg"))) {
					if (photoPath.delete()) {
						System.out.println("File deleted successfully.");
					} else {
						System.out.println("Failed to delete the file.");
					}
				}
				if (StudentDaoImpl.getInstance().delete(studentCard.getMainWindow().getCurrentStudentFromTable())) {
					studentCard.setVisible(false);
					studentCard.getJTextFieldLastName().setEditable(false);
					studentCard.getJTextFieldEmail().setEditable(false);
					studentCard.getJTextFieldPhone().setEditable(false);
					studentCard.getJTextFieldFirstName().setEditable(false);
					studentCard.getJTextFieldMiddleName().setEditable(false);
					studentCard.getJbtnDeleteStudent().setVisible(false);
					studentCard.getJbtnEditStudent().setText("Редактировать");
					studentCard.getJbtnEditPhoto().setVisible(false);
					studentCard.getMainWindow().getCurrentGroup().getStudents().remove(studentCard.getMainWindow().getCurrentStudentFromTable());
					studentCard.getMainWindow().refreshStudentTable();

					System.out.println("Student was deleted");
				}
			}
		}else if(e.getSource() == studentCard.getJbtnEditStudent()){
			if (studentCard.getJbtnEditStudent().getText().equalsIgnoreCase("Редактировать")) {
				studentCard.getJTextFieldLastName().setEditable(true);
				studentCard.getJTextFieldFirstName().setEditable(true);
				studentCard.getJTextFieldMiddleName().setEditable(true);
				studentCard.getJTextFieldEmail().setEditable(true);
				studentCard.getJTextFieldPhone().setEditable(true);
				studentCard.getJbtnDeleteStudent().setVisible(true);
				studentCard.getJbtnEditStudent().setText("Сохранить");
				studentCard.getJpanelCalendar().setEnabled(false);
				studentCard.getJbtnEditPhoto().setVisible(true);

			} else {
				int result = JOptionPane.showConfirmDialog(studentCard, "Вы уверены, что хотите изменить данные студента?", "Подтверждение", JOptionPane.YES_NO_OPTION);
				if (result == JOptionPane.YES_OPTION) {
					// выполнить изменения
					Student student1 = studentCard.getMainWindow().getCurrentStudentFromTable();
					String lastName = studentCard.getJTextFieldLastName().getText();
					String firstName = studentCard.getJTextFieldFirstName().getText();
					String middleName = studentCard.getJTextFieldMiddleName().getText();
					student1.setLastName(lastName);
					student1.setFirstName(firstName);
					student1.setMiddleName(middleName);
					student1.setGroupId(studentCard.getCurrStudent().getGroupId());
					student1.setTelephone(studentCard.getJTextFieldPhone().getText());
					student1.setEmail(studentCard.getJTextFieldEmail().getText());
					StudentDaoImpl.getInstance().update(student1);
					SwingUtilities.invokeLater(studentCard.getMainWindow().getStudentTable()::repaint);
				}
				studentCard.getJTextFieldLastName().setEditable(false);
				studentCard.getJTextFieldFirstName().setEditable(false);
				studentCard.getJTextFieldMiddleName().setEditable(false);
				studentCard.getJTextFieldEmail().setEditable(false);
				studentCard.getJTextFieldPhone().setEditable(false);
				studentCard.getJbtnDeleteStudent().setVisible(false);
				studentCard.getJbtnEditStudent().setText("Редактировать");
				studentCard.getJpanelCalendar().setEnabled(true);
				studentCard.getJbtnEditPhoto().setVisible(false);
				studentCard.getCurrentLessonButton().requestFocus();
			}
		}
	}
}
