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
	private static final Color ATTENDANCE_COLOR = new Color(144, 238, 144);

	private List<Student> students;
	private List<Lab> labs;

	public StudentLabTableModel(Group group) {
		students = group.getStudents();
		labs = group.getLabs();
	}

	@Override
	public int getRowCount() {
		return students.size();
	}

	@Override
	public int getColumnCount() {
		return labs.size() + 2;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (columnIndex == STUDENT_COLUMN_INDEX) {
			return students.get(rowIndex);
		}

		if (columnIndex == AVERAGE_GRADE_COLUMN_INDEX) {
			return String.format("%.2f", students.get(rowIndex).getAverageGrade());
		}

		return getLabPanel(rowIndex, columnIndex);
	}

	public Student getStudentAt(int selectedRow) {
		return students.get(selectedRow);
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

	public void setLabs(List<Lab> labs) {
		this.labs = labs;
	}

	private JPanel getLabPanel(int rowIndex, int columnIndex) {
		Student student = students.get(rowIndex);
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
		return dateFormat.format(labs.get(columnIndex - FIRST_LAB_COLUMN_INDEX).getDate());
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

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	public void sortByAlphabet(boolean isInc) {
		students.sort(Comparator.comparing(Student::getFullName, isInc ? Comparator.naturalOrder() : Comparator.reverseOrder()));
		fireTableDataChanged();
	}

	public void sortByGrade(boolean isInc) {
		students.sort((o1, o2) -> {
			int result = Double.compare(o1.getAverageGrade(),o2.getAverageGrade());
			return isInc?result:result*(-1);
		});
				fireTableDataChanged();
	}

	public void sortByAttendance(boolean isInc) {
		students.sort((o1, o2) -> {
			int result = Integer.compare(o1.getAttendanceList().size(),o2.getAttendanceList().size());
			return isInc?result:result*(-1);
		});
		fireTableDataChanged();
	}

}
