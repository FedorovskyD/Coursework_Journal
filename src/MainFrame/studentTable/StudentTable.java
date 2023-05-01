package MainFrame.studentTable;

import entity.Student;

import javax.swing.*;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

public class StudentTable extends JTable {
	public StudentTable() {
		super(new StudentTableModel());
		setDefaultRenderer(Object.class,new StudentTableCellRender());
		//Скрываем колонку с ID студента
		TableColumn column = getColumnModel().getColumn(5);
		column.setMinWidth(0);
		column.setMaxWidth(0);
		column.setWidth(0);
		column.setPreferredWidth(0);
		//Отключаем стандартную обработку нажатия клавиш
		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_UP ||
						e.getKeyCode() == KeyEvent.VK_DOWN) {
					e.consume();
				}
			}
		});
	}
	public void setData(List<Student> students){
		((StudentTableModel)getModel()).setData(students);
	}
	public Student getStudentAt(int selectedRow){
		return ((StudentTableModel)getModel()).getStudentAt(selectedRow);
	}
}
