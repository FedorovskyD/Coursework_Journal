package MainFrame.studentTable;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

/**
 * проверен
 */
public class StudentTableCellRender extends DefaultTableCellRenderer {

	private static final Color LIGHT_GRAY = new Color(240, 240, 240);
	private static final Color SELECTED_COLOR = new Color(255, 255, 150);
	private static final Color ATTENDANCE_COLOR = new Color(144, 238, 144);

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

		if (value instanceof JPanel panel) {
			if (isSelected) {
				panel.setBackground(SELECTED_COLOR);
			} else if (panel.getBackground().equals(ATTENDANCE_COLOR)) {
				panel.setBackground(ATTENDANCE_COLOR);
			} else {
				panel.setBackground(row % 2 == 0 ? LIGHT_GRAY : Color.WHITE);
			}
			return panel;
		}

		if (isSelected) {
			cellComponent.setBackground(SELECTED_COLOR);
		} else {
			cellComponent.setBackground(row % 2 == 0 ? LIGHT_GRAY : Color.WHITE);
		}
		return cellComponent;
	}
}
