package gui.studentTable.studentTableListener;

import entity.Student;
import gui.MainFrame;
import gui.studentTable.StudentTableCellRender;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class StudentTableMouseListener extends MouseAdapter {
	private MainFrame mainFrame;

	public StudentTableMouseListener(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
	}

	public void mouseClicked(MouseEvent e) {
		int row = mainFrame.getStudentTable().rowAtPoint(e.getPoint());
		int column = mainFrame.getStudentTable().columnAtPoint(e.getPoint());

		Object value = mainFrame.getStudentTable().getValueAt(row, 0);
		// обработка щелчка на ячейке
		if (value instanceof Student student) {
			if (student == mainFrame.getCurrentStudent()) {
				mainFrame.getStudentTable().repaint();
				mainFrame.getJDialogStudentCard().updateStudentCard(student);
				if (column > 1) {
					mainFrame.getCurrDateCmb().setSelectedIndex(column - 2);
				}
			}
		}
		// перемещаем получение номера столбца внутрь условия
		if (row >= 0) {
			value = mainFrame.getStudentTable().getValueAt(row, 0);
			// обработка щелчка на ячейке
			if (value instanceof Student student) {
				if (student == mainFrame.getCurrentStudent()) {
					mainFrame.getJDialogStudentCard().updateStudentCard(student);
					mainFrame.getJDialogStudentCard().setVisible(true);
				}
			}
			// Добавьте здесь свой код, использующий номер столбца
		}
	}
}

