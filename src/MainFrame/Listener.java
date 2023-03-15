package MainFrame;

import connection.MySQLConnector;
import dialogs.AddStudentDialog;
import entity.Student;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Listener implements ActionListener {
	private MainWindow mainWindow;
	private AddStudentDialog addStudentDialog;

	public Listener(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
	}

	public Listener(AddStudentDialog addStudentDialog) {
		this.addStudentDialog = addStudentDialog;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == mainWindow.addStudentbtn) {
			AddStudentDialog dialog = new AddStudentDialog(mainWindow);
			dialog.setVisible(true);
		} else if (e.getSource() == mainWindow.groupNumberCmb) {
			String selectedGroup = (String) mainWindow.groupNumberCmb.getSelectedItem();
			List<Student> students = MySQLConnector.getAllStudentsByGroup(selectedGroup);
			DefaultTableModel model = (DefaultTableModel) mainWindow.studentTable.getModel();
			model.setRowCount(0); // удаление всех строк
			for (Student student : students) {
				model.addRow(new Object[]{student.getSurname(), student.getName(), student.getMiddleName(), student.getEmail()});
			}
		}
	}
}
