package gui.studentTable;

import entity.Group;
import entity.Student;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * проверен
 */
public class StudentTable extends JTable {
	int currColumn = 2;

	public StudentTable(Group group, boolean isLecture) {
		super(new StudentLabTableModel(group, isLecture));
		setDefaultRenderer(Object.class, new StudentTableCellRender(2));
		setRowHeight(35);
	}

	public int getCurrColumn() {
		return currColumn;
	}

	public void setCurrColumn(int currColumn) {
		this.currColumn = currColumn;
	}

	public Student getStudentAt(int selectedRow) {
		return getStudentTableModel().getStudentAt(selectedRow);
	}

	public StudentLabTableModel getStudentTableModel() {
		return (StudentLabTableModel) getModel();
	}

}
