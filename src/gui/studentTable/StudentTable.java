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
		super(new StudentTableModel(group, isLecture));
		setDefaultRenderer(Object.class, new StudentTableCellRender(getStudentTableModel().getFIRST_LAB_COLUMN_INDEX()));
		setRowHeight(35);
		TableCellRenderer headerRenderer = getColumnModel().getColumn(0).getHeaderRenderer();
		// Если рендер заголовка не установлен, то установить его по умолчанию
		if (headerRenderer == null) {
			headerRenderer = getTableHeader().getDefaultRenderer();
		}
		TableCellRenderer finalHeaderRenderer = headerRenderer;
		getTableHeader().setDefaultRenderer((table, value, isSelected, hasFocus, row, column1) -> {
			Component headerComponent = finalHeaderRenderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column1);
			StudentTable studentTable = (StudentTable) table;
			boolean isHolidayColumn = false;
			int indexFirstLessonColumn = studentTable.getStudentTableModel().getFIRST_LAB_COLUMN_INDEX();
			if ((column1 > 0 && indexFirstLessonColumn==1) || (column1 > 1 && indexFirstLessonColumn==2)) {
				isHolidayColumn = studentTable.getStudentTableModel().getLessons().get(column1- indexFirstLessonColumn).isHoliday();
			}
			if (column1 == lessonJComboBox.getSelectedIndex() + indexFirstLessonColumn && isHolidayColumn) {
				headerComponent.setBackground(new Color(255, 228, 75)); // здесь можно установить любой нужный цвет
			} else if (column1 == lessonJComboBox.getSelectedIndex() + indexFirstLessonColumn) {
				headerComponent.setBackground(Constants.SELECTED_COLOR);
			} else if (isHolidayColumn) {
				headerComponent.setBackground(Color.ORANGE);
			}
			return headerComponent;
		});
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}


	public Student getStudentAt(int selectedRow) {
		return getStudentTableModel().getStudentAt(selectedRow);
	}

	public StudentTableModel getStudentTableModel() {
		return (StudentTableModel) getModel();
	}

}
