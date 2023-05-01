package dialogs;

import MainFrame.MainWindow;

import MainFrame.StudentTableModel;
import database.dao.impl.GroupDaoImpl;
import entity.Group;
import entity.Student;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class DeleteGroupDialog extends JDialog {

	private final JComboBox<Group> groupComboBox;
	private final MainWindow mainWindow;

	public DeleteGroupDialog(JFrame parent) {
		super(parent, "Delete Group", true);
		mainWindow = (MainWindow) parent;

		// Создаем JComboBox для выбора группы
		groupComboBox = new JComboBox<>(mainWindow.getGroups().toArray(new Group[0]));

		// Создаем кнопку для удаления группы
		JButton deleteButton = new JButton("Удалить");
		deleteButton.addActionListener(e -> {
			// Получаем выбранную группу
			Group selectedGroup = (Group) groupComboBox.getSelectedItem();
			// Удаляем группу из базы данных
			// ...
			if (selectedGroup != null) {
				GroupDaoImpl.getInstance().delete(selectedGroup);
				mainWindow.getGroups().remove(selectedGroup);
				mainWindow.updateGroupCmb();
			}
			ComboBoxModel<Group> newModel = new DefaultComboBoxModel<>((GroupDaoImpl.getInstance().findAll()).toArray(new Group[0]));
			groupComboBox.setModel(newModel);
			// Устанавливаем новую модель в JComboBox
			selectedGroup = (Group) groupComboBox.getSelectedItem();
			List<Student> students = null;
			if (selectedGroup != null) {
				students = GroupDaoImpl.getInstance().getStudents(selectedGroup);
			}
			StudentTableModel model = (StudentTableModel) mainWindow.getStudentTable().getModel();
			model.setData(students);
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
