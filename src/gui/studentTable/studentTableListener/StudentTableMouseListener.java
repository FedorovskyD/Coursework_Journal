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
		if (column > 1) {
			Object value = mainFrame.getStudentTable().getValueAt(row, 0);
			// обработка щелчка на ячейке
			if (value instanceof Student student) {
				if (student == mainFrame.getCurrentStudent()) {
					mainFrame.getStudentTable().setDefaultRenderer(Object.class, new StudentTableCellRender(column));
					mainFrame.getCurrDateCmb().setSelectedIndex(column-2);
					mainFrame.getStudentTable().repaint();

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
}
