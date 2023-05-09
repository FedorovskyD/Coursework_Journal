package MainFrame.studentTable;

import utils.Constants;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

/**
 * проверен
 */
public class StudentTableCellRender extends DefaultTableCellRenderer {

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		if (column == 0) {
			setFont(getFont().deriveFont(Font.ITALIC,14)); // Меняем шрифт на курсив
		} else {
			setFont(table.getFont()); // Используем шрифт по умолчанию для остальных колонок
		}

		if (value instanceof JPanel panel) {
			if (isSelected) {
				if(panel.getBackground().equals(Constants.ATTENDANCE_COLOR)){
					panel.setBackground(new Color(200,247,147));
					table.getTableHeader().getColumnModel().getColumn(column).setCellRenderer(this);
				}else {
					panel.setBackground(Constants.SELECTED_COLOR);
				}

			} else if (panel.getBackground().equals(Constants.ATTENDANCE_COLOR)) {
				panel.setBackground(Constants.ATTENDANCE_COLOR);
			} else {
				panel.setBackground(row % 2 == 0 ? Constants.FIRST_ROW_COLOR : Constants.SECOND_ROW_COLOR);
			}
			return panel;
		}

		if (isSelected) {
			cellComponent.setBackground(Constants.SELECTED_COLOR);
		} else {
			if (table.isColumnSelected(column)) {
				cellComponent.setBackground(Constants.SELECTED_COLOR);
			}
			cellComponent.setBackground(row % 2 == 0 ? Constants.FIRST_ROW_COLOR : Constants.SECOND_ROW_COLOR);
		}

		return cellComponent;
	}
}
