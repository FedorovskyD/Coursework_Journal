package dialogs.studentCard;

import database.dao.impl.StudentDaoImpl;
import entity.Student;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class StudentCardDialogListener implements ActionListener {
	private final StudentCardDialog studentCardDialog;
	public StudentCardDialogListener (StudentCardDialog studentCardDialog){
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
				String photoPath = studentCardDialog.mainWindow.getCurrentStudent().getPhotoPath();
				if (photoPath != null) {
					File fileToDelete = new File(photoPath);
					if (fileToDelete.delete()) {
						System.out.println("File deleted successfully.");
					} else {
						System.out.println("Failed to delete the file.");
					}
				}
				if (StudentDaoImpl.getInstance().delete(studentCardDialog.mainWindow.getCurrentStudent())) {
					studentCardDialog.setVisible(false);
					studentCardDialog.txtFullName.setEditable(false);
					studentCardDialog.txtEmail.setEditable(false);
					studentCardDialog.txtPhone.setEditable(false);
					studentCardDialog.deleteButton.setVisible(false);
					studentCardDialog.editButton.setText("Редактировать");
					studentCardDialog.btnEditPhoto.setVisible(false);
					studentCardDialog.mainWindow.getCurrentGroup().getStudents().remove(studentCardDialog.mainWindow.getCurrentStudent());
					studentCardDialog.mainWindow.updateStudentTable();
					studentCardDialog.mainWindow.getMainWindowListener().keyboardListenerOn();
					System.out.println("Student was deleted");
				}
			}
		}else if(e.getSource() == studentCardDialog.editButton){
			if (studentCardDialog.editButton.getText().equalsIgnoreCase("Редактировать")) {
				studentCardDialog.mainWindow.getMainWindowListener().keyboardListenerOf();
				studentCardDialog.txtFullName.setEditable(true);
				studentCardDialog.txtEmail.setEditable(true);
				studentCardDialog.txtPhone.setEditable(true);
				studentCardDialog.deleteButton.setVisible(true);
				studentCardDialog.editButton.setText("Сохранить");
				studentCardDialog.calendarPanel.setEnabled(false);
				studentCardDialog.btnEditPhoto.setVisible(true);

			} else {
				int result = JOptionPane.showConfirmDialog(studentCardDialog, "Вы уверены, что хотите изменить данные студента?", "Подтверждение", JOptionPane.YES_NO_OPTION);
				if (result == JOptionPane.YES_OPTION) {
					// выполнить изменения
					Student student1 = studentCardDialog.mainWindow.getCurrentStudent();
					String fullName = studentCardDialog.txtFullName.getText();
					String[] fio = fullName.split(" ");
					student1.setFirstName(fio[1]);
					student1.setLastName(fio[0]);
					student1.setMiddleName(fio[2]);
					student1.setTelephone(studentCardDialog.txtPhone.getText());
					student1.setEmail(studentCardDialog.txtEmail.getText());
					StudentDaoImpl.getInstance().update(student1);
					SwingUtilities.invokeLater(studentCardDialog.mainWindow.getStudentTable()::repaint);
				}
				studentCardDialog.txtFullName.setEditable(false);
				studentCardDialog.txtEmail.setEditable(false);
				studentCardDialog.txtPhone.setEditable(false);
				studentCardDialog.deleteButton.setVisible(false);
				studentCardDialog.editButton.setText("Редактировать");
				studentCardDialog.calendarPanel.setEnabled(true);
				studentCardDialog.btnEditPhoto.setVisible(false);
				studentCardDialog.mainWindow.getMainWindowListener().keyboardListenerOn();
				studentCardDialog.getCurrLabButton().requestFocus();
			}
		}
	}
}
