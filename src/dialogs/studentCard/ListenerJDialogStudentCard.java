package dialogs.studentCard;

import database.dao.impl.StudentDaoImpl;
import entity.Student;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class ListenerJDialogStudentCard implements ActionListener {
	private final JDialogStudentCard jDialogStudentCard;
	public ListenerJDialogStudentCard(JDialogStudentCard jDialogStudentCard){
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
				String photoPath = jDialogStudentCard.mainWindow.getCurrentStudent().getPhotoPath();
				if (photoPath != null) {
					File fileToDelete = new File(photoPath);
					if (fileToDelete.delete()) {
						System.out.println("File deleted successfully.");
					} else {
						System.out.println("Failed to delete the file.");
					}
				}
				if (StudentDaoImpl.getInstance().delete(jDialogStudentCard.mainWindow.getCurrentStudent())) {
					jDialogStudentCard.setVisible(false);
					jDialogStudentCard.txtFullName.setEditable(false);
					jDialogStudentCard.txtEmail.setEditable(false);
					jDialogStudentCard.txtPhone.setEditable(false);
					jDialogStudentCard.deleteButton.setVisible(false);
					jDialogStudentCard.editButton.setText("Редактировать");
					jDialogStudentCard.btnEditPhoto.setVisible(false);
					jDialogStudentCard.mainWindow.getCurrentGroup().getStudents().remove(jDialogStudentCard.mainWindow.getCurrentStudent());
					jDialogStudentCard.mainWindow.refreshStudentTable();

					System.out.println("Student was deleted");
				}
			}
		}else if(e.getSource() == jDialogStudentCard.editButton){
			if (jDialogStudentCard.editButton.getText().equalsIgnoreCase("Редактировать")) {
				jDialogStudentCard.txtFullName.setEditable(true);
				jDialogStudentCard.txtEmail.setEditable(true);
				jDialogStudentCard.txtPhone.setEditable(true);
				jDialogStudentCard.deleteButton.setVisible(true);
				jDialogStudentCard.editButton.setText("Сохранить");
				jDialogStudentCard.calendarPanel.setEnabled(false);
				jDialogStudentCard.btnEditPhoto.setVisible(true);

			} else {
				int result = JOptionPane.showConfirmDialog(jDialogStudentCard, "Вы уверены, что хотите изменить данные студента?", "Подтверждение", JOptionPane.YES_NO_OPTION);
				if (result == JOptionPane.YES_OPTION) {
					// выполнить изменения
					Student student1 = jDialogStudentCard.mainWindow.getCurrentStudent();
					String fullName = jDialogStudentCard.txtFullName.getText();
					String[] fio = fullName.split(" ");
					student1.setFirstName(fio[1]);
					student1.setLastName(fio[0]);
					student1.setMiddleName(fio[2]);
					student1.setGroup(jDialogStudentCard.currStudent.getGroup());
					student1.setTelephone(jDialogStudentCard.txtPhone.getText());
					student1.setEmail(jDialogStudentCard.txtEmail.getText());
					StudentDaoImpl.getInstance().update(student1);
					SwingUtilities.invokeLater(jDialogStudentCard.mainWindow.getStudentTable()::repaint);
				}
				jDialogStudentCard.txtFullName.setEditable(false);
				jDialogStudentCard.txtEmail.setEditable(false);
				jDialogStudentCard.txtPhone.setEditable(false);
				jDialogStudentCard.deleteButton.setVisible(false);
				jDialogStudentCard.editButton.setText("Редактировать");
				jDialogStudentCard.calendarPanel.setEnabled(true);
				jDialogStudentCard.btnEditPhoto.setVisible(false);
				jDialogStudentCard.getCurrLabButton().requestFocus();
			}
		}
	}
}
