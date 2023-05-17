package gui.studentTable.studentTableListener;

import entity.Student;
import gui.MainFrame;
import gui.studentTable.StudentTable;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class StudentTableListSelectionListener implements ListSelectionListener {
	private MainFrame mainFrame;

	public StudentTableListSelectionListener(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (e.getSource() == mainFrame.getStudentTable().getSelectionModel()) {
			int column = mainFrame.getStudentTable().getSelectedColumn();
			int selectedRowIndex = mainFrame.getStudentTable().getSelectedRow();

			if (selectedRowIndex != -1) {
				Object value = mainFrame.getStudentTable().getValueAt(selectedRowIndex, 0);

				if (value == null) {
					if (column > getColumnOffset()) {
						mainFrame.getStudentTable().repaint();
						mainFrame.getCurrDateCmb().setSelectedIndex(column - getColumnOffset() - 1);
					}
					int index = mainFrame.getStudentTable().getStudentTableModel().getRowIndex(mainFrame.getCurrStudent());
					if (index != -1) {
						mainFrame.getStudentTable().setRowSelectionInterval(index, index);
						mainFrame.getJDialogStudentCard().setVisible(false);
					}
				} else {
					Student selectedStudent = (Student) value;
					boolean isVisible = mainFrame.getCurrStudent() != selectedStudent
							&& mainFrame.getCurrStudent() != null
							&& mainFrame.getCurrStudent().getGroupId() == selectedStudent.getGroupId();
					if (isVisible) {
						if (column > getColumnOffset()) {
							mainFrame.getCurrDateCmb().setSelectedIndex(column - getColumnOffset() - 1);
						}
						mainFrame.setCurrStudent(selectedStudent);
						mainFrame.getJDialogStudentCard().updateStudentCard(selectedStudent);
						mainFrame.getStudentTable().repaint();
						mainFrame.getJDialogStudentCard().setVisible(true);
					}
				}
			}
		}
	}
	private int getColumnOffset() {
		return mainFrame.getRadioBtnLecture().isSelected() ? 1 : 2;
	}
}
