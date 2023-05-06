package MainFrame.studentTable;

import entity.Group;
import entity.Student;

import javax.swing.*;
import java.awt.event.*;

/**
 * проверен
 */
public class StudentTable extends JTable {

	public StudentTable(Group group) {
		super(new StudentLabTableModel(group));
		setDefaultRenderer(Object.class, new StudentTableCellRender());
		setRowHeight(35);

	}
	public Student getStudentAt(int selectedRow) {
		return getStudentTableModel().getStudentAt(selectedRow);
	}

	public StudentLabTableModel getStudentTableModel() {
		return (StudentLabTableModel) getModel();
	}
}
