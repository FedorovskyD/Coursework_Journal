package MainFrame;

import MainFrame.studentTable.StudentTableModel;
import dialogs.*;
import dialogs.studentCard.StudentCardDialog;
import entity.Student;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;

public class MainWindowListener implements ActionListener, ListSelectionListener {
	private final MainWindow mainWindow;

	public MainWindowListener(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(event -> {
			if (event.getID() == KeyEvent.KEY_RELEASED && (event.getKeyCode() == KeyEvent.VK_UP || event.getKeyCode() == KeyEvent.VK_DOWN)) {
				if (event.getKeyCode() == KeyEvent.VK_UP || event.getKeyCode() == KeyEvent.VK_DOWN) {
					// получаем текущую выделенную строку в таблице
					JTable studentTable = mainWindow.getStudentTable();
					int selectedRow = studentTable.getSelectedRow();
					// вычисляем номер следующей строки в зависимости от нажатой клавиши
					if (selectedRow != -1) {
						int nextRow = event.getKeyCode() == KeyEvent.VK_UP ? selectedRow - 1 : selectedRow + 1;
						// проверяем, что следующая строка существует
						if (nextRow >= 0 && nextRow < studentTable.getRowCount()) {
							// обновляем выделение строки в таблице
							studentTable.setRowSelectionInterval(nextRow, nextRow);
							// прокручиваем таблицу к следующей строке
							studentTable.scrollRectToVisible(studentTable.getCellRect(nextRow, 0, true));
						}
					}
				}
			}
			return false;
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JTable studentTable = mainWindow.getStudentTable();
		if (e.getSource() == mainWindow.getBtnAddStudent()) {
			AddStudentDialog dialog = new AddStudentDialog(mainWindow);
			dialog.setVisible(true);
		} else if (e.getSource() == mainWindow.getBtnAddGroup()) {
			AddGroupDialog addGroupDialog = new AddGroupDialog(mainWindow);
			addGroupDialog.setVisible(true);
		} else if (e.getSource() == mainWindow.getBtnDeleteGroup()) {
			DeleteGroupDialog deleteGroupDialog = new DeleteGroupDialog(mainWindow);
			deleteGroupDialog.setVisible(true);
		} else if (e.getSource() == mainWindow.getBtnAboutAuthor()) {
			AboutDialog aboutAuthorDialog = new AboutDialog(mainWindow);
			aboutAuthorDialog.setVisible(true);
		} else if (e.getSource() == mainWindow.getBtnAddPhoto()) {
			if (studentTable.getSelectedRow() != -1) {
				AddPhotoDialog addPhotoDialog = new AddPhotoDialog(mainWindow, mainWindow.getCurrentStudent());
				addPhotoDialog.setVisible(true);
			}
		} else if (e.getSource() == mainWindow.getBtnAddLab()) {
			AddLabDialog addLabDialog = new AddLabDialog(mainWindow);
			addLabDialog.setVisible(true);
		} else if (e.getSource() == mainWindow.getCmbGroupNumber()) {
			StudentTableModel studentTableModel1 = (StudentTableModel) studentTable.getModel();
			studentTableModel1.setData(mainWindow.getCurrentGroup().getStudents());
			mainWindow.getStudentCardDialog().dispose();
			mainWindow.studentCardDialog = new StudentCardDialog(mainWindow,"Карточка студента");
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (e.getSource() == mainWindow.getStudentTable().getSelectionModel()) {
			JTable studentTable = mainWindow.getStudentTable();
			StudentCardDialog studentCardDialog = mainWindow.getStudentCardDialog();
			if (!e.getValueIsAdjusting()) {
				int selectedRow = studentTable.getSelectedRow();
				if (selectedRow != -1) {
					Student selectedStudent = mainWindow.getStudentTable().getStudentAt(selectedRow);
					selectedStudent.setGroup(mainWindow.getCurrentGroup().getId());
					studentCardDialog.update(selectedStudent);
					SwingUtilities.invokeLater(() -> studentCardDialog.setVisible(true));
					SwingUtilities.invokeLater(()->studentCardDialog.getCurrLabButton().requestFocus());
				}
			}
		}
	}
}
