package MainFrame;

import MainFrame.studentTable.StudentTable;
import dialogs.*;
import dialogs.studentCard.JDialogStudentCard;
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
						StudentTable studentTable = mainWindow.getStudentTable();
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
						studentTable.repaint();
					}
				}
				return false;
			};
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		StudentTable studentTable = mainWindow.getStudentTable();
		if (e.getSource() == mainWindow.getBtnAddStudent()) {
			new JDialogAddStudent(mainWindow).setVisible(true);
		} else if (e.getSource() == mainWindow.getBtnAddGroup()) {
			new JDialogAddGroup(mainWindow).setVisible(true);
		} else if (e.getSource() == mainWindow.getBtnDeleteGroup()) {
			new JDialogDeleteGroup(mainWindow).setVisible(true);
		} else if (e.getSource() == mainWindow.getBtnAboutAuthor()) {
			new JDialogAbout(mainWindow).setVisible(true);
		} else if (e.getSource() == mainWindow.getBtnAddLab()) {
			new JDialogAddLab(mainWindow).setVisible(true);
		} else if (e.getSource() == mainWindow.getCmbGroupNumber()) {
			studentTable.getStudentTableModel().setStudents(mainWindow.getCurrentGroup().getStudents());
			SwingUtilities.invokeLater(() -> mainWindow.getStudentTable().repaint());
			mainWindow.getStudentCardDialog().dispose();
			mainWindow.jDialogStudentCard = new JDialogStudentCard(mainWindow, "Карточка студента");
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (e.getSource() == mainWindow.getStudentTable().getSelectionModel()) {
			StudentTable studentTable = mainWindow.getStudentTable();
			if (!e.getValueIsAdjusting() && studentTable.getSelectedRow() != -1) {
				int selectedRowIndex = studentTable.getSelectedRow();
				Student selectedStudent = studentTable.getStudentAt(selectedRowIndex);
				mainWindow.jDialogStudentCard.updateStudentCard(selectedStudent);
				mainWindow.jDialogStudentCard.setVisible(true);
			}
		}
	}

	public void enableKeyboardListener() {
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(keyEventDispatcher);
	}

	public void disableKeyboardListener() {
		KeyboardFocusManager.getCurrentKeyboardFocusManager().removeKeyEventDispatcher(keyEventDispatcher);
	}
}

