package gui.studentTable.studentTableListener;

import entity.Student;
import gui.MainFrame;
import gui.studentTable.StudentTable;
import gui.studentTable.StudentTableCellRender;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class StudentTableListSelectionListener implements ListSelectionListener {
	private MainFrame mainFrame;
	public StudentTableListSelectionListener(MainFrame mainFrame){
		this.mainFrame = mainFrame;
	}
	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (e.getSource() == mainFrame.getStudentTable().getSelectionModel()) {
			int column = mainFrame.getStudentTable().getSelectedColumn();
			int selectedRowIndex = mainFrame.getStudentTable().getSelectedRow();
			Object value = mainFrame.getStudentTable().getValueAt(selectedRowIndex, 0); // Получаем значение в ячейке первого столбца строки
			if (value == null) {
				if (column > 1) {
					mainFrame.getStudentTable().setDefaultRenderer(Object.class, new StudentTableCellRender(column));
					mainFrame.getStudentTable().repaint();
					mainFrame.getStudentTable().setCurrColumn(column);
					mainFrame.getCurrDateCmb().setSelectedIndex(mainFrame.getStudentTable().getCurrColumn() - 2);
				}
			}
			if (selectedRowIndex != -1) {
				// Получаем значение в ячейке первого столбца строки
				if (value == null) {
					if (column > 1) {
						mainFrame.getStudentTable().setDefaultRenderer(Object.class, new StudentTableCellRender(column));
						mainFrame.getStudentTable().setCurrColumn(column);
						mainFrame.getCurrDateCmb().setSelectedIndex(mainFrame.getStudentTable().getCurrColumn() - 2);
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
						mainFrame.setCurrStudent(selectedStudent);
						mainFrame.getJDialogStudentCard().updateStudentCard(selectedStudent);
						mainFrame.getJDialogStudentCard().setVisible(true);
					}
				}
			}
		}
	}
}
