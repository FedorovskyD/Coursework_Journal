package MainFrame;

import entity.Student;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class StudentTableModel extends AbstractTableModel {

		private List<Student> students = new ArrayList<>();

		public void setData(List<Student> data) {
			this.students = data;
			fireTableDataChanged();
		}

		@Override
		public int getRowCount() {
			return students.size();
		}

		@Override
		public int getColumnCount() {
			return 6;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			Student student = students.get(rowIndex);
			switch (columnIndex) {
				case 0:
					return student.getSurname();
				case 1:
					return student.getName();
				case 2:
					return student.getMiddleName();
				case 3:
					return student.getTelephone();
				case 4:
					return student.getEmail();
				case 5:
					return student.getId();
				default:
					return null;
			}
		}

		@Override
		public String getColumnName(int column) {
			switch (column) {
				case 0:
					return "Фамилия";
				case 1:
					return "Имя";
				case 2:
					return "Отчество";
				case 3:
					return "Телефон";
				case 4:
					return "Почта";
				case 5:
					return "ID";
				default:
					return "";
			}
		}
	public Student getStudentAt(int row) {
		return students.get(row);
	}
}
