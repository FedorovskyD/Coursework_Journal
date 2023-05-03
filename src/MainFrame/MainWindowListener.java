package MainFrame;

import MainFrame.studentTable.StudentLabTableModel;
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
	private final KeyEventDispatcher keyEventDispatcher;

	public MainWindowListener(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
		keyEventDispatcher = e -> {
			if (e.getID() == KeyEvent.KEY_RELEASED && (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN)) {
				if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN) {
					// получаем текущую выделенную строку в таблице
					JTable studentTable = mainWindow.getStudentTable();
					int selectedRow = studentTable.getSelectedRow();
					// вычисляем номер следующей строки в зависимости от нажатой клавиши
					if (selectedRow != -1) {
						int nextRow = e.getKeyCode() == KeyEvent.VK_UP ? selectedRow - 1 : selectedRow + 1;
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
		};
		keyboardListenerOn();
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
		} else if (e.getSource() == mainWindow.getBtnAddLab()) {
			AddLabDialog addLabDialog = new AddLabDialog(mainWindow);
			addLabDialog.setVisible(true);
		} else if (e.getSource() == mainWindow.getCmbGroupNumber()) {
			StudentLabTableModel studentTableModel1 = (StudentLabTableModel) studentTable.getModel();
			studentTableModel1.setStudents(mainWindow.getCurrentGroup().getStudents());
			SwingUtilities.invokeLater(()->mainWindow.getStudentTable().repaint());
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
					studentCardDialog.getCalendarPanel().repaint();
					//
					SwingUtilities.invokeLater(() -> studentCardDialog.setVisible(true));
					SwingUtilities.invokeLater(()->studentCardDialog.getCurrLabButton().requestFocus());
				}
			}
		}
	}
	public void keyboardListenerOn(){
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(keyEventDispatcher);
	}
	public void keyboardListenerOf(){
		KeyboardFocusManager.getCurrentKeyboardFocusManager().removeKeyEventDispatcher(keyEventDispatcher);
	}
}
