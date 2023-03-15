package entity;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

public class StudentTableModel extends DefaultTableModel {
	private static final int COLUMN_FIRST_NAME = 0;
	private static final int COLUMN_LAST_NAME = 1;
	private static final int COLUMN_ID = 2; // невидимая колонка

	private List<Student> students;

	public StudentTableModel(List<Student> students) {
		this.students = students;
	}

	@Override
	public int getRowCount() {
		if(students==null)
			return 0;
		return students.size();
	}

	@Override
	public int getColumnCount() {
		return 2; // только две колонки: имя и фамилия
	}

	@Override
	public String getColumnName(int columnIndex) {
		switch (columnIndex) {
			case COLUMN_FIRST_NAME:
				return "Имя";
			case COLUMN_LAST_NAME:
				return "Фамилия";
			default:
				return null;
		}
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Student student = students.get(rowIndex);
		switch (columnIndex) {
			case COLUMN_FIRST_NAME:
				return student.getName();
			case COLUMN_LAST_NAME:
				return student.getSurname();
			case COLUMN_ID:
				return student.getId();
			default:
				return null;
		}
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	public Student getStudent(int rowIndex) {
		return students.get(rowIndex);
	}
}