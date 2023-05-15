package gui.studentTable.studentTableListener;

import gui.MainFrame;
import gui.studentTable.StudentTable;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class StudentTableKeyListener extends KeyAdapter {
	private MainFrame mainFrame;
	public StudentTableKeyListener(MainFrame mainFrame){
		this.mainFrame = mainFrame;
	}
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN) {
			int selectedRow = ((StudentTable) e.getSource()).getSelectedRow();
			if (selectedRow != -1) {
				int nextRow = selectedRow + (e.getKeyCode() == KeyEvent.VK_UP ? -1 : 1);
				while (nextRow >= 0 && nextRow < ((StudentTable) e.getSource()).getRowCount() &&
						mainFrame.getStudentTable().getStudentTableModel().isBlankRow(nextRow)) {
					nextRow += (e.getKeyCode() == KeyEvent.VK_UP ? -1 : 1);
				}
				if (nextRow >= 0 && nextRow < ((StudentTable) e.getSource()).getRowCount()) {
					int firstIndex = mainFrame.getStudentTable().getStudentTableModel().getFIRST_LAB_COLUMN_INDEX();
					mainFrame.getStudentTable().setRowSelectionInterval(nextRow, nextRow);
					mainFrame.getStudentTable().scrollRectToVisible(((StudentTable) e.getSource()).getCellRect(nextRow, mainFrame.getCurrDateCmb().getSelectedIndex()+firstIndex, true));
					mainFrame.getStudentTable().repaint();
				}
			}
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT) {
			int itemCount = mainFrame.getCurrDateCmb().getItemCount();
			int selectedIndex = mainFrame.getCurrDateCmb().getSelectedIndex();
			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				if (selectedIndex > 0) {
					mainFrame.getCurrDateCmb().setSelectedIndex(selectedIndex-1);
					mainFrame.getStudentTable().scrollRectToVisible(((StudentTable) e.getSource())
							.getCellRect(mainFrame.getStudentTable().getSelectedRow(), selectedIndex-2, true));
				}
			} else {
				if (selectedIndex < itemCount - 1) {
					mainFrame.getCurrDateCmb().setSelectedIndex(selectedIndex+1);
					mainFrame.getStudentTable().scrollRectToVisible(((StudentTable) e.getSource())
							.getCellRect(mainFrame.getStudentTable().getSelectedRow(), selectedIndex+3, true));
				}
			}
			mainFrame.repaint();
		}
		e.consume();
	}
}
