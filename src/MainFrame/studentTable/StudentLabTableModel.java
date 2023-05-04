package MainFrame.studentTable;

import entity.Grade;
import entity.Group;
import entity.Lab;
import entity.Student;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.List;

/**
 * проверен
 */
public class StudentLabTableModel extends AbstractTableModel {
	private static final int STUDENT_COLUMN_INDEX = 0;
	private static final int AVERAGE_GRADE_COLUMN_INDEX = 1;
	private static final int FIRST_LAB_COLUMN_INDEX = 2;
	private static final int SEPARATOR = 4;
	private static final Color ATTENDANCE_COLOR = new Color(144, 238, 144);

	private List<Student> students;
	private List<Lab> labs;

	public StudentLabTableModel(Group group) {
		students = group.getStudents();
		labs = group.getLabs();
	}

	@Override
	public int getRowCount() {
		// Добавляем пустые строки каждые 4 строки таблицы
		int rowCount = students.size();
		if (rowCount > 0) {
			rowCount += (rowCount - 1) / SEPARATOR;
		}
		return rowCount;
	}

	@Override
	public int getColumnCount() {
		return labs.size() + 2;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if ((rowIndex + 1) % (SEPARATOR+1) == 0) { // проверяем, является ли строка пустой строкой
			return null;
		}

		int studentIndex = rowIndex - rowIndex / (SEPARATOR+1); // вычисляем индекс студента в списке

		if (columnIndex == STUDENT_COLUMN_INDEX) {
			return students.get(studentIndex);
		}

		if (columnIndex == AVERAGE_GRADE_COLUMN_INDEX) {
			return String.format("%.2f", students.get(studentIndex).getAverageGrade());
		}

		return getLabPanel(studentIndex, columnIndex);
	}

	public Student getStudentAt(int selectedRow) {
		int studentIndex = selectedRow - selectedRow / (SEPARATOR+1);
		return students.get(studentIndex);
	}

	public void setStudents(List<Student> students) {
		this.students = students;
		fireTableDataChanged();
	}

	private JPanel getLabPanel(int rowIndex, int columnIndex) {
		int studentIndex = rowIndex - rowIndex / (SEPARATOR+1);
		Student student = students.get(studentIndex);
		Lab lab = labs.get(columnIndex - FIRST_LAB_COLUMN_INDEX);

		boolean isAttendance = student.isAttendance(lab);

		JPanel panelTableCell = new JPanel();
		panelTableCell.setBackground(isAttendance ? ATTENDANCE_COLOR : Color.WHITE);

		if (isAttendance) {
			Grade grade = student.getLabGrade(lab);
			String text = (grade != null) ? String.valueOf(grade.getGrade()) : "Нет оценки";

			JLabel label = new JLabel(text);
			panelTableCell.add(label);
		}

		return panelTableCell;
	}

	@Override
	public String getColumnName(int columnIndex) {
		if (columnIndex == STUDENT_COLUMN_INDEX) {
			return "Студенты\\Даты";
		}
		if (columnIndex == AVERAGE_GRADE_COLUMN_INDEX) {
			return "Средний балл";
		}

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		if (columnIndex - FIRST_LAB_COLUMN_INDEX < labs.size()) {
			return dateFormat.format(labs.get(columnIndex - FIRST_LAB_COLUMN_INDEX).getDate());
		}
		return "";
	}


	@Override
	public Class<?> getColumnClass(int columnIndex) {
		if (columnIndex == STUDENT_COLUMN_INDEX) {
			return Student.class;
		}

		if (columnIndex == AVERAGE_GRADE_COLUMN_INDEX) {
			return String.class;
		}

		return JPanel.class;
	}

	public int getRowIndex(Student student) {
		for (int i = 0; i < students.size(); i++) {
			if (students.get(i - i/(SEPARATOR+1)).equals(student)) {
				return  i ;
			}
		}
		return -1; // если студент не найден, вернуть -1
	}


	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	public boolean isCellSelectable(int rowIndex, int columnIndex) {
		return (rowIndex + 1) % (SEPARATOR + 1) != 0; // пустые строки не являются выбираемыми
// остальные строки можно выбирать
	}
	public void sortByAlphabet(boolean isInc) {
		students.sort(Comparator.comparing(Student::getFullName, isInc ? Comparator.naturalOrder() : Comparator.reverseOrder()));
		fireTableDataChanged();
	}

	public void sortByGrade(boolean isInc) {
		students.sort((o1, o2) -> {
			int result = Double.compare(o1.getAverageGrade(), o2.getAverageGrade());
			return isInc ? result : result * (-1);
		});
		fireTableDataChanged();
	}

	public void sortByAttendance(boolean isInc) {
		students.sort((o1, o2) -> {
			int result = Integer.compare(o1.getAttendanceList().size(), o2.getAttendanceList().size());
			return isInc ? result : result * (-1);
		});
		fireTableDataChanged();
	}

}
