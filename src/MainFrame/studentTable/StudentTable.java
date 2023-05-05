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
		disableArrowKeys();
	}

	private void disableArrowKeys() {
		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_UP ||
						e.getKeyCode() == KeyEvent.VK_DOWN) {
					e.consume();
				}
			}
		});
	}
	public Student getStudentAt(int selectedRow) {
		return getStudentTableModel().getStudentAt(selectedRow);
	}

	public StudentLabTableModel getStudentTableModel() {
		return (StudentLabTableModel) getModel();
	}
}
