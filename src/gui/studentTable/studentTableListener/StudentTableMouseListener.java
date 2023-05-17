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

	@Override
	public void mouseClicked(MouseEvent e) {
		int row = mainFrame.getStudentTable().rowAtPoint(e.getPoint());
		int column = mainFrame.getStudentTable().columnAtPoint(e.getPoint());

		if (row >= 0) {
			Object value = mainFrame.getStudentTable().getValueAt(row, 0);
			if (value instanceof Student student && student == mainFrame.getCurrentStudent()) {
				mainFrame.getJDialogStudentCard().updateStudentCard(student);
				if (column > (mainFrame.getRadioBtnLecture().isSelected() ? 1 : 2)) {
					mainFrame.getCurrDateCmb().setSelectedIndex(column - (mainFrame.getRadioBtnLecture().isSelected() ? 2 : 3));
				}
				mainFrame.getJDialogStudentCard().setVisible(true);
			}
		}
	}
}
