package gui.studentTable;

import entity.Group;
import entity.Lesson;
import entity.Student;
import utils.Constants;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

/**
 * проверен
 */
public class StudentTable extends JTable {

	public StudentTable(Group group, boolean isLecture, JComboBox<Lesson> lessonJComboBox) {
		super(new StudentLabTableModel(group, isLecture));
		setDefaultRenderer(Object.class, new StudentTableCellRender(2));
		setRowHeight(35);
		TableCellRenderer headerRenderer = getColumnModel().getColumn(0).getHeaderRenderer();
		// Если рендер заголовка не установлен, то установить его по умолчанию
		if (headerRenderer == null) {
			headerRenderer =getTableHeader().getDefaultRenderer();
		}
		TableCellRenderer finalHeaderRenderer = headerRenderer;
		getTableHeader().setDefaultRenderer((table, value, isSelected, hasFocus, row, column1) -> {
			Component headerComponent = finalHeaderRenderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column1);
			if(column1 == lessonJComboBox.getSelectedIndex()+2) {
				headerComponent.setBackground(Constants.SELECTED_COLOR); // здесь можно установить любой нужный цвет
			}
			return headerComponent;
		});
		repaint();
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}


	public Student getStudentAt(int selectedRow) {
		return getStudentTableModel().getStudentAt(selectedRow);
	}

	public StudentLabTableModel getStudentTableModel() {
		return (StudentLabTableModel) getModel();
	}

}
