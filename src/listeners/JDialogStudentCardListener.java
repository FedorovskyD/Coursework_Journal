package listeners;

import gui.studentTable.studentCard.JDialogStudentCard;
import database.dao.impl.StudentDaoImpl;
import entity.Student;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class JDialogStudentCardListener implements ActionListener {
	private final JDialogStudentCard jDialogStudentCard;
	public JDialogStudentCardListener(JDialogStudentCard jDialogStudentCard){
		this.jDialogStudentCard = jDialogStudentCard;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == jDialogStudentCard.getDeleteButton()){
			JOptionPane optionPane = new JOptionPane("Вы действительно хотите удалить студента?", JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION);
			JDialog dialog = optionPane.createDialog("Подтверждение");
			dialog.setModal(true);
			dialog.setVisible(true);
			int result = (int)optionPane.getValue();
			if (result == JOptionPane.YES_OPTION) {
				String photoPath = jDialogStudentCard.getMainWindow().getCurrentStudent().getPhotoPath();
				if (photoPath != null) {
					File fileToDelete = new File(photoPath);
					if (fileToDelete.delete()) {
						System.out.println("File deleted successfully.");
					} else {
						System.out.println("Failed to delete the file.");
					}
				}
				if (StudentDaoImpl.getInstance().delete(jDialogStudentCard.getMainWindow().getCurrentStudent())) {
					jDialogStudentCard.setVisible(false);
					jDialogStudentCard.getTxtFullName().setEditable(false);
					jDialogStudentCard.getTxtEmail().setEditable(false);
					jDialogStudentCard.getTxtPhone().setEditable(false);
					jDialogStudentCard.getDeleteButton().setVisible(false);
					jDialogStudentCard.getEditButton().setText("Редактировать");
					jDialogStudentCard.getBtnEditPhoto().setVisible(false);
					jDialogStudentCard.getMainWindow().getCurrentGroup().getStudents().remove(jDialogStudentCard.getMainWindow().getCurrentStudent());
					jDialogStudentCard.getMainWindow().refreshStudentTable();

					System.out.println("Student was deleted");
				}
			}
		}else if(e.getSource() == jDialogStudentCard.getEditButton()){
			if (jDialogStudentCard.getEditButton().getText().equalsIgnoreCase("Редактировать")) {
				jDialogStudentCard.getTxtFullName().setEditable(true);
				jDialogStudentCard.getTxtEmail().setEditable(true);
				jDialogStudentCard.getTxtPhone().setEditable(true);
				jDialogStudentCard.getDeleteButton().setVisible(true);
				jDialogStudentCard.getEditButton().setText("Сохранить");
				jDialogStudentCard.getCalendarPanel().setEnabled(false);
				jDialogStudentCard.getBtnEditPhoto().setVisible(true);

			} else {
				int result = JOptionPane.showConfirmDialog(jDialogStudentCard, "Вы уверены, что хотите изменить данные студента?", "Подтверждение", JOptionPane.YES_NO_OPTION);
				if (result == JOptionPane.YES_OPTION) {
					// выполнить изменения
					Student student1 = jDialogStudentCard.getMainWindow().getCurrentStudent();
					String fullName = jDialogStudentCard.getTxtFullName().getText();
					String[] fio = fullName.split(" ");
					student1.setFirstName(fio[1]);
					student1.setLastName(fio[0]);
					student1.setMiddleName(fio[2]);
					student1.setGroupId(jDialogStudentCard.getCurrStudent().getGroupId());
					student1.setTelephone(jDialogStudentCard.getPhoneLabel().getText());
					student1.setEmail(jDialogStudentCard.getEmailLabel().getText());
					StudentDaoImpl.getInstance().update(student1);
					SwingUtilities.invokeLater(jDialogStudentCard.getMainWindow().getStudentTable()::repaint);
				}
				jDialogStudentCard.getTxtFullName().setEditable(false);
				jDialogStudentCard.getTxtEmail().setEditable(false);
				jDialogStudentCard.getTxtPhone().setEditable(false);
				jDialogStudentCard.getDeleteButton().setVisible(false);
				jDialogStudentCard.getEditButton().setText("Редактировать");
				jDialogStudentCard.getCalendarPanel().setEnabled(true);
				jDialogStudentCard.getBtnEditPhoto().setVisible(false);
				jDialogStudentCard.getCurrLessonButton().requestFocus();
			}
		}
	}
}
