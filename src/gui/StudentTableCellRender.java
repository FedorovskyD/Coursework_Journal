package gui;

import utils.Constants;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

/**
 * Класс StudentTableCellRender - настраивает отображение ячеек в таблице студентов.
 * Наследуется от класса DefaultTableCellRenderer.
 */
public class StudentTableCellRender extends DefaultTableCellRenderer {
	/**
	 * Конструктор класса StudentTableCellRender.
	 * @param column Номер колонки для подсветки
	 */
	public StudentTableCellRender(int column) {
		columnToHighlight = column;
	}

	private final int columnToHighlight; // номер колонки для подсветки

	/**
	 * Проверяет, нужно ли подсветить указанную колонку.
	 * @param column Номер колонки
	 * @return true, если колонка нуждается в подсветке, иначе - false
	 */
	private boolean isColumnHighlighted(int column) {
		return column == columnToHighlight;
	}
	/**
	 * Переопределенный метод getTableCellRendererComponent для настройки отображения ячеек таблицы.
	 * @param table Таблица
	 * @param value Значение ячейки
	 * @param isSelected Выбрана ли ячейка
	 * @param hasFocus Имеет ли ячейка фокус
	 * @param row Номер строки
	 * @param column Номер колонки
	 * @return Компонент отображения ячейки
	 */
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		// Определяем, нужно ли подсветить колонку
		boolean isColumnHighlighted = isColumnHighlighted(column) && ((StudentTable)table).getStudentTableModel().getFIRST_LAB_COLUMN_INDEX()<=column;
		if (column == 0) {
			setFont(getFont().deriveFont(Font.ITALIC, 14)); // Меняем шрифт на курсив
		} else {
			setFont(table.getFont()); // Используем шрифт по умолчанию для остальных колонок
		}
		boolean isHolidayColumn = false;
		StudentTable studentTable = (StudentTable) table;
		int indexFirstLessonColumn = studentTable.getStudentTableModel().getFIRST_LAB_COLUMN_INDEX();
		if ((column > 1 && indexFirstLessonColumn == 2) || (column > 2 && indexFirstLessonColumn == 3)) {
			isHolidayColumn = studentTable.getStudentTableModel().getLessons().get(column - indexFirstLessonColumn).isHoliday();
		}
		if (value instanceof JLabel label) {
			JPanel panel1 = new JPanel();
			if (isSelected || isColumnHighlighted) {
				if (label.getBackground().equals(Constants.ABSENCE_COLOR)) {
					panel1.setBackground(new Color(250, 192, 140));
				} else if (label.getBackground().equals(Color.ORANGE)) {
					panel1.setBackground(new Color(255, 228, 75));
				} else if (label.getBackground().equals(Constants.HALF_ABSENCE_COLOR)) {
					panel1.setBackground(Constants.HALF_ABSENCE_COLOR_SELECTED);
				} else {
					panel1.setBackground(Constants.SELECTED_COLOR);
				}
			} else if (label.getBackground().equals(Constants.ABSENCE_COLOR)) {
				panel1.setBackground(Constants.ABSENCE_COLOR);
			} else if (label.getBackground().equals(Color.ORANGE)) {
				panel1.setBackground(Color.ORANGE);
			} else if (label.getBackground().equals(Constants.HALF_ABSENCE_COLOR)) {
				panel1.setBackground(Constants.HALF_ABSENCE_COLOR);
			} else {
				panel1.setBackground(row % 2 == 0 ? Constants.FIRST_ROW_COLOR : Constants.SECOND_ROW_COLOR);
			}
			panel1.add(label);
			return panel1;
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
