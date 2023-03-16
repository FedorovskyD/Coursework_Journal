package MainFrame;

import connection.MySQLConnector;
import dialogs.AddStudentDialog;
import entity.Student;
import utils.PhotoUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

public class Listener implements ActionListener {
	private MainWindow mainWindow;
	private AddStudentDialog addStudentDialog;

	public Listener(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
	}

	public Listener(AddStudentDialog addStudentDialog, JFrame mainWindow) {
		this.mainWindow = (MainWindow) mainWindow;
		this.addStudentDialog = addStudentDialog;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == mainWindow.getAddStudentBtn()) {
			AddStudentDialog dialog = new AddStudentDialog(mainWindow);
			dialog.setVisible(true);
		}  else if (e.getSource() == addStudentDialog.getOkButton()) {
			Student student = new Student();
			student.setName(addStudentDialog.getFirstName());
			student.setSurname(addStudentDialog.getLastName());
			student.setGroup(MySQLConnector.getGroupIDByGroupNumber((String) addStudentDialog.getGroupField().getSelectedItem()));
			student.setMiddleName(addStudentDialog.getMiddleName());
			student.setEmail(addStudentDialog.getEmail());
			student.setTelephone(addStudentDialog.getTelephone());
			System.out.println("Student with id = "+MySQLConnector.addStudent(student)+" was added");
			String selectedGroup = (String) mainWindow.getGroupNumberCmb().getSelectedItem();
			List<Student> students = MySQLConnector.getAllStudentsByGroup(selectedGroup);
			DefaultTableModel model = (DefaultTableModel) mainWindow.getStudentTable().getModel();
			model.setRowCount(0); // удаление всех строк
			for (Student stud : students) {
				model.addRow(new Object[]{stud.getSurname(), stud.getName(), stud.getMiddleName(), stud.getEmail(),stud.getId()});
			}

		}
	}
}
