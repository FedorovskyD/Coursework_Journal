package listeners;

import entity.Student;
import gui.MainFrame;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Слушатель событий мыши на таблице со студентами наследуется от MouseAdapter.
 */
public class StudentTableMouseListener extends MouseAdapter {
	private final MainFrame mainFrame;

	public StudentTableMouseListener(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int row = mainFrame.getStudentTable().rowAtPoint(e.getPoint());
		int column = mainFrame.getStudentTable().columnAtPoint(e.getPoint());

		if (row >= 0) {
			Object value = mainFrame.getStudentTable().getValueAt(row, 0);
			if (value instanceof Student student && student == mainFrame.getCurrentStudentFromTable()) {
				if (column > (mainFrame.getJradiobtnLecture().isSelected() ? 1 : 2)) {
					mainFrame.getJcmbCurrentDate().setSelectedIndex(column - (mainFrame.getJradiobtnLecture().isSelected() ? 2 : 3));
				}
				mainFrame.getJDialogStudentCard().updateStudentCard(student);
				mainFrame.getJDialogStudentCard().setVisible(true);
			}
		}
	}
}
