package MainFrame;

import MainFrame.studentTable.StudentLabTableModel;
import MainFrame.studentTable.StudentTable;
import MainFrame.studentTable.StudentTableCellRender;
import database.dao.impl.GroupDaoImpl;
import dialogs.*;
import dialogs.studentCard.JDialogStudentCard;
import entity.Group;
import entity.Lab;
import entity.Student;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;

public class MainWindowListener implements ActionListener, ListSelectionListener {

	private final MainWindow mainWindow;
	private final KeyEventDispatcher keyEventDispatcher;

	public MainWindowListener(MainWindow mainWindow) {
		this.mainWindow = mainWindow;keyEventDispatcher = e -> {
			if (e.getID() == KeyEvent.KEY_RELEASED && (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN)) {
				StudentTable studentTable = mainWindow.getStudentTable();
				int selectedRow = studentTable.getSelectedRow();

				if (selectedRow != -1) {
					int nextRow = e.getKeyCode() == KeyEvent.VK_UP ? selectedRow - 1 : selectedRow + 1;

					while (nextRow >= 0 && nextRow < studentTable.getRowCount() && !studentTable.getStudentTableModel().isCellSelectable(nextRow, 0)) {
						nextRow += e.getKeyCode() == KeyEvent.VK_UP ? -1 : 1;
					}

					if (nextRow >= 0 && nextRow < studentTable.getRowCount()) {
						studentTable.setRowSelectionInterval(nextRow, nextRow);
						studentTable.scrollRectToVisible(studentTable.getCellRect(nextRow, 0, true));
					}
				}
			}
			return false;
		};

		enableKeyboardListener();


	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == mainWindow.getBtnAddStudent()) {
			new JDialogAddStudent(mainWindow).setVisible(true);
		} else if (e.getSource() == mainWindow.getBtnAddGroup()) {
			String groupName = JOptionPane.showInputDialog(mainWindow,
					"Введите название группы:", "Добавление группы", JOptionPane.PLAIN_MESSAGE);
			if (groupName != null && !groupName.isEmpty()) {
				addNewGroup(groupName);
			}
		} else if (e.getSource() == mainWindow.getBtnDeleteGroup()) {
			Group[] groups = mainWindow.getGroups().toArray(new Group[0]);
			int index = mainWindow.getCmbGroupNumber().getSelectedIndex();
			Group selectedGroup = (Group) JOptionPane.showInputDialog(
					mainWindow,
					"Выберите группу для удаления:",
					"Удаление группы",
					JOptionPane.QUESTION_MESSAGE,
					null,
					groups,
					groups[index]);
			deleteGroup(selectedGroup);

		} else if (e.getSource() == mainWindow.getBtnAboutAuthor()) {
			new JDialogAbout(mainWindow).setVisible(true);
		} else if (e.getSource() == mainWindow.getBtnAddLab()) {
			new JDialogAddLab(mainWindow).setVisible(true);
		} else if (e.getSource() == mainWindow.getCmbGroupNumber()) {
			Group group = (Group) mainWindow.getCmbGroupNumber().getSelectedItem();
			mainWindow.getCurrDateCmb().setModel(new DefaultComboBoxModel<>(group.getLabs().toArray(new Lab[0])));
			mainWindow.getStudentTable().setModel(new StudentLabTableModel(group));
			mainWindow.updateCurrDateCmb();
			mainWindow.sortTable();
			mainWindow.studentTable.revalidate();
			mainWindow.studentTable.repaint();

		}
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (e.getSource() == mainWindow.studentTable.getSelectionModel()) {
			int selectedRowIndex = mainWindow.studentTable.getSelectedRow();
			if (selectedRowIndex != -1 && mainWindow.studentTable.getStudentTableModel().isCellSelectable(selectedRowIndex, 0)) {
				Student selectedStudent = mainWindow.studentTable.getStudentAt(selectedRowIndex);
				mainWindow.studentTable.repaint();
				if (mainWindow.currStudent != selectedStudent) {
					mainWindow.jDialogStudentCard.updateStudentCard(selectedStudent);
					if (!mainWindow.jDialogStudentCard.isVisible()) {
						mainWindow.jDialogStudentCard.setVisible(true);
					}
				}
			} else {
				// Выбранная ячейка не может быть выбрана пользователем, поэтому выбираем первую доступную ячейку
				int row = mainWindow.studentTable.getStudentTableModel().getRowIndex(mainWindow.currStudent);
				if (row != -1) {
					mainWindow.studentTable.setRowSelectionInterval(row, row);
				}
			}
		}
	}


	public void enableKeyboardListener() {
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(keyEventDispatcher);
	}

	public void disableKeyboardListener() {
		KeyboardFocusManager.getCurrentKeyboardFocusManager().removeKeyEventDispatcher(keyEventDispatcher);
	}

	private void addNewGroup(String groupName) {
		Group group = new Group();
		group.setName(groupName);
		group.setId(GroupDaoImpl.getInstance().save(group));
		mainWindow.getGroups().add(group);
		mainWindow.updateGroupCmb();
	}

	private void deleteGroup(Group group) {
		boolean isUpdate = group == mainWindow.getCurrentGroup();
		// Удаляем группу из базы данных
		if (group != null) {
			if (GroupDaoImpl.getInstance().delete(group)) {
				mainWindow.getGroups().remove(group);
				mainWindow.updateGroupCmb();
				// Устанавливаем новую модель в JComboBox
				Group selectedGroup = mainWindow.getCurrentGroup();
				if (selectedGroup != null && isUpdate) {
					mainWindow.getStudentTable().setModel(new StudentLabTableModel(selectedGroup));
				}
			}
		}
	}
}

