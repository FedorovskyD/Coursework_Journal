package gui.studentTable;

import utils.Constants;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

/**
 * проверен
 */
public class StudentTableCellRender extends DefaultTableCellRenderer {
	public StudentTableCellRender(int column) {
		columnToHighlight = column;
	}

	private final int columnToHighlight; // номер колонки для подсветки


	private boolean isColumnHighlighted(int column) {
		return column == columnToHighlight;
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		// Определяем, нужно ли подсветить колонку
		boolean isColumnHighlighted = isColumnHighlighted(column);
		if (column == 0) {
			setFont(getFont().deriveFont(Font.ITALIC, 14)); // Меняем шрифт на курсив
		} else {
			setFont(table.getFont()); // Используем шрифт по умолчанию для остальных колонок
		}
		boolean isHolidayColumn = false;
		StudentTable studentTable = (StudentTable) table;
		int indexFirstLessonColumn = studentTable.getStudentTableModel().getFIRST_LAB_COLUMN_INDEX();
		if ((column > 0 && indexFirstLessonColumn == 1) || (column > 1 && indexFirstLessonColumn == 2)) {
			isHolidayColumn = studentTable.getStudentTableModel().getLessons().get(column - indexFirstLessonColumn).isHoliday();
		}
		if (value instanceof JPanel panel) {
			if (isSelected || isColumnHighlighted) {
				if (panel.getBackground().equals(Constants.ABSENCE_COLOR)) {
					panel.setBackground(new Color(250, 192, 140));
				} else if (panel.getBackground().equals(Color.ORANGE)) {
					panel.setBackground(new Color(255, 228, 75));
				} else if (panel.getBackground().equals(Constants.HALF_ABSENCE_COLOR)) {
					panel.setBackground(Constants.HALF_ABSENCE_COLOR_SELECTED);
				} else {
					panel.setBackground(Constants.SELECTED_COLOR);
				}
			} else if (panel.getBackground().equals(Constants.ABSENCE_COLOR)) {
				panel.setBackground(Constants.ABSENCE_COLOR);
			} else if (panel.getBackground().equals(Color.ORANGE)) {
				panel.setBackground(Color.ORANGE);
			} else if (panel.getBackground().equals(Constants.HALF_ABSENCE_COLOR)) {
				panel.setBackground(Constants.HALF_ABSENCE_COLOR);
			} else {
				panel.setBackground(row % 2 == 0 ? Constants.FIRST_ROW_COLOR : Constants.SECOND_ROW_COLOR);
			}
			return panel;
		}

		if (isHolidayColumn && (isSelected || isColumnHighlighted)) {
			cellComponent.setBackground(new Color(255, 228, 75));
		} else if (isSelected || isColumnHighlighted) {
			cellComponent.setBackground(Constants.SELECTED_COLOR);

		} else if (isHolidayColumn) {
			cellComponent.setBackground(Color.ORANGE);

		} else {
			cellComponent.setBackground(row % 2 == 0 ? Constants.FIRST_ROW_COLOR : Constants.SECOND_ROW_COLOR);
		}
		return cellComponent;
	}
}
