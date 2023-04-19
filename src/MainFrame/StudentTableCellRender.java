package MainFrame;

import entity.Student;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class StudentTableCellRender extends DefaultTableCellRenderer {
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
	                                               boolean isSelected, boolean hasFocus, int row, int column) {
		Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

		if (value instanceof Student) {
			Student student = (Student) value;
			String text = student.getName() + " (" + student.getAge() + ", " + student.getMiddleName() + ")";
			setText(text);
		}
		return cell;
	}
}
