package gui.studentTable;

import entity.Group;
import entity.Student;

import javax.swing.*;

/**
 * проверен
 */
public class StudentTable extends JTable {

	public StudentTable(Group group, boolean isLecture) {
		super(new StudentLabTableModel(group, isLecture));
		setDefaultRenderer(Object.class, new StudentTableCellRender(2));
		setRowHeight(35);
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}


	public Student getStudentAt(int selectedRow) {
		return getStudentTableModel().getStudentAt(selectedRow);
	}

	public StudentLabTableModel getStudentTableModel() {
		return (StudentLabTableModel) getModel();
	}

}
