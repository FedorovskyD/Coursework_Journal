package dialogs;

import MainFrame.MainWindow;
import connection.MySQLConnector;
import entity.Student;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class DeleteGroupDialog extends JDialog {

	private JComboBox<String> groupComboBox;
	private MainWindow mainWindow;

	public DeleteGroupDialog(JFrame parent) {
		super(parent, "Delete Group", true);
		mainWindow = (MainWindow) parent;

		// Создаем JComboBox для выбора группы
		groupComboBox = new JComboBox<>(MySQLConnector.getAllGroupNumbers().toArray(new String[0]));
		// Заполняем JComboBox данными из базы данных или другого источника
		// ...

		// Создаем кнопку для удаления группы
		JButton deleteButton = new JButton("Удалить");
		deleteButton.addActionListener(e -> {
			// Получаем выбранную группу
			String selectedGroup = (String) groupComboBox.getSelectedItem();
			// Удаляем группу из базы данных
			// ...
			MySQLConnector.deleteGroup(selectedGroup);
			ComboBoxModel<String> newModel = new DefaultComboBoxModel<>(MySQLConnector.getAllGroupNumbers().toArray(new String[0]));
			groupComboBox.setModel(newModel);
			// Устанавливаем новую модель в JComboBox

			String selectGroup = (String) mainWindow.getGroupNumberCmb().getSelectedItem();
			List<Student> students = MySQLConnector.getAllStudentsByGroup(selectGroup);
			DefaultTableModel model = (DefaultTableModel) mainWindow.getStudentTable().getModel();
			model.setRowCount(0); // удаление всех строк
			for (Student student : students) {
				model.addRow(new Object[]{student.getSurname(), student.getName(), student.getMiddleName(),
						student.getEmail(), student.getId()});
			}

			// Закрываем диалоговое окно
			dispose();
		});

		// Создаем панель с JComboBox и кнопкой
		JPanel panel = new JPanel();
		panel.add(new JLabel("Группа:"));
		panel.add(groupComboBox);
		panel.add(deleteButton);

		// Добавляем панель на диалоговое окно
		getContentPane().add(panel);

		// Устанавливаем размер и отображаем диалоговое окно
		setSize(350, 200);
		setLocationRelativeTo(parent);
		setVisible(true);
	}
}
