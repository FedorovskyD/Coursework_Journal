package listeners;

import entity.Student;
import gui.MainFrame;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class StudentTableListSelectionListener implements ListSelectionListener {
	private final MainFrame mainFrame;

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
						mainFrame.getJcmbCurrentDate().setSelectedIndex(column - getColumnOffset() - 1);
					}
					int index = mainFrame.getStudentTable().getStudentTableModel().getRowIndex(mainFrame.getCurrentStudent());
					if (index != -1) {
						mainFrame.getStudentTable().setRowSelectionInterval(index, index);
						mainFrame.getJDialogStudentCard().setVisible(false);
					}
				} else {
					Student selectedStudent = (Student) value;
					boolean isVisible = mainFrame.getCurrentStudent() != selectedStudent
							&& mainFrame.getCurrentStudentFromTable() != null
							&& mainFrame.getCurrentStudentFromTable().getGroupId() == selectedStudent.getGroupId();
					if (isVisible) {
						if (column > getColumnOffset()) {
							mainFrame.getJcmbCurrentDate().setSelectedIndex(column - getColumnOffset() - 1);
						}
						mainFrame.setCurrentStudent(selectedStudent);
						mainFrame.getJDialogStudentCard().updateStudentCard(selectedStudent);
						mainFrame.getStudentTable().repaint();
						mainFrame.getJDialogStudentCard().setVisible(true);
					}
				}
			}
		}
	}
	private int getColumnOffset() {
		return mainFrame.getJradiobtnLecture().isSelected() ? 1 : 2;
	}
}
