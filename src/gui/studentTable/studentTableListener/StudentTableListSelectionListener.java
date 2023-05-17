package gui.studentTable.studentTableListener;

import entity.Student;
import gui.MainFrame;
import gui.studentTable.StudentTable;
import gui.studentTable.StudentTableCellRender;

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
				// Получаем значение в ячейке первого столбца строки
				Object value = mainFrame.getStudentTable().getValueAt(selectedRowIndex, 0); // Получаем значение в ячейке первого столбца строки
				if (value == null) {
					if (column > (mainFrame.getRadioBtnLecture().isSelected()?1:2)) {
						mainFrame.getStudentTable().repaint();
						mainFrame.getCurrDateCmb().setSelectedIndex(column-(mainFrame.getRadioBtnLecture().isSelected()?2:3));
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
						if(column>(mainFrame.getRadioBtnLecture().isSelected()?1:2)) {
							mainFrame.getCurrDateCmb().setSelectedIndex(column - (mainFrame.getRadioBtnLecture().isSelected()?2:3));
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
}
