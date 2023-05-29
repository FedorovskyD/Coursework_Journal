package listeners;

import gui.MainFrame;
import gui.StudentTable;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Слушатель нажатия клавиш на таблице студентов, наследуется от класса KeyAdapter.
 */
public class StudentTableKeyListener extends KeyAdapter {
	private final MainFrame mainFrame;

	public StudentTableKeyListener(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		StudentTable studentTable = (StudentTable) e.getSource();

		if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_DOWN) {
			int selectedRow = studentTable.getSelectedRow();
			if (selectedRow != -1) {
				int nextRow = selectedRow + (keyCode == KeyEvent.VK_UP ? -1 : 1);
				while (nextRow >= 0 && nextRow < studentTable.getRowCount() &&
						mainFrame.getStudentTable().getStudentTableModel().isBlankRow(nextRow)) {
					nextRow += (keyCode == KeyEvent.VK_UP ? -1 : 1);
				}
				if (nextRow >= 0 && nextRow < studentTable.getRowCount()) {
					int firstIndex = mainFrame.getStudentTable().getStudentTableModel().getFIRST_LAB_COLUMN_INDEX();
					studentTable.setRowSelectionInterval(nextRow, nextRow);
					studentTable.scrollRectToVisible(studentTable.getCellRect(nextRow, mainFrame.getJcmbCurrentDate().getSelectedIndex() + firstIndex, true));
					studentTable.repaint();
				}
			}
		} else if (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_RIGHT) {
			int itemCount = mainFrame.getJcmbCurrentDate().getItemCount();
			int selectedIndex = mainFrame.getJcmbCurrentDate().getSelectedIndex();
			if (keyCode == KeyEvent.VK_LEFT && selectedIndex > 0) {
				mainFrame.getJcmbCurrentDate().setSelectedIndex(selectedIndex - 1);
				studentTable.scrollRectToVisible(studentTable.getCellRect(studentTable.getSelectedRow(), selectedIndex - 2, true));
			} else if (keyCode == KeyEvent.VK_RIGHT && selectedIndex < itemCount - 1) {
				mainFrame.getJcmbCurrentDate().setSelectedIndex(selectedIndex + 1);
				studentTable.scrollRectToVisible(studentTable.getCellRect(studentTable.getSelectedRow(), selectedIndex + 4, true));
			}
			mainFrame.repaint();
		}
		e.consume();
	}
}
