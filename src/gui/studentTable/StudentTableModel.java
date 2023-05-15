package gui.studentTable;

import entity.Grade;
import entity.Group;
import entity.Lesson;
import entity.Student;
import utils.Constants;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.List;

/**
 * проверен
 */
public class StudentTableModel extends AbstractTableModel {
	private static final int STUDENT_COLUMN_INDEX = 0;//Индекс колонки с информацие о студенете
	private int AVERAGE_GRADE_COLUMN_INDEX;//Индекс колонки с информацией о среднем балле
	private final int FIRST_LAB_COLUMN_INDEX ;//Индекс с колонки, с которой начинаются даты занятий
	public static final int COUNT_SEPARATOR_ROW = 4;//Количество строк после, которых вставляется пустая строка
	private final List<Student> students;//Список студентов
	private final List<Lesson> lessons;//Список занятий
	private final String groupNumber;
	private final boolean isLecture;

	/**
	 * Конструктор для создания новой модели таблицы
	 *
	 * @param group     - группа, которую будет отображать таблица
	 * @param isLecture - устанавливается true, если таблица будет
	 *                  отображать статистику лекционных занятий,
	 *                  если false - лабораторных занятий
	 */
	public StudentTableModel(Group group, boolean isLecture) {
		//Получаем список студентов из группы
		students = group.getStudents();
		//Заполняем список lessons, в зависимости от типа занятий, которые отображает таблица
		if (isLecture) {
			lessons = group.getLectures();
			FIRST_LAB_COLUMN_INDEX = 1;
			AVERAGE_GRADE_COLUMN_INDEX = -1;
		} else {
			lessons = group.getLabs();
			FIRST_LAB_COLUMN_INDEX = 2;
			AVERAGE_GRADE_COLUMN_INDEX = 1;
		}
		this.isLecture = isLecture;
		groupNumber = group.getName();
	}

	@Override
	public int getRowCount() {
		int rowCount = students.size();
		if (rowCount > 0) {
			// Добавляем к количеству строк пустые строки
			rowCount += (rowCount - 1) / COUNT_SEPARATOR_ROW;
		}
		return rowCount;
	}

	@Override
	public int getColumnCount() {
		//Вычисляем количество столбцов
		return lessons.size() + FIRST_LAB_COLUMN_INDEX;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		//Проверяем строку, является ли она разделителем
		if (isBlankRow(rowIndex)) {
			return null;
		}
		//Если столбец хранит объект студента получаем его со списка студентов
		if (columnIndex == STUDENT_COLUMN_INDEX) {
			return students.get(getStudentIndex(rowIndex));
		}
		//Если столбец отвечает за средний балл получаем его у соответствующего студена
		if (columnIndex == AVERAGE_GRADE_COLUMN_INDEX) {
			return String.format("%.2f", students.get(getStudentIndex(rowIndex)).getAverageGrade());
		}
		//Иначе возвращаем панель, отвечающую за отображение занятия
		return getLessonPanel(getStudentIndex(rowIndex), columnIndex);
	}

	/**
	 * Проверяет является ли строка
	 * пустой строкой для разделения
	 *
	 * @param rowIndex - индекс строки для проверки
	 * @return true, если строка пустая, иначе - false
	 */
	public boolean isBlankRow(int rowIndex) {
		return (rowIndex + 1) % (COUNT_SEPARATOR_ROW + 1) == 0;
	}

	private int getStudentIndex(int rowIndex) {
		/*Возвражаем порядковый индекс студента в списке
		 по номеру строки, в котором студент отображается*/
		return rowIndex - (rowIndex + 1) / (COUNT_SEPARATOR_ROW + 1);
	}

	/**
	 * Метод для получения объекта студента
	 * по строке, в которой он отображается
	 *
	 * @param selectedRow - строка, где отображается студет
	 * @return объект студента
	 */
	public Student getStudentAt(int selectedRow) {
		int index = getStudentIndex(selectedRow);
		if (index >= 0 && index < students.size())
			return students.get(index);
		return null;
	}

	//Метод для создания панели с информацией о статусе студента на занятии
	private JPanel getLessonPanel(int rowIndex, int columnIndex) {
		//Получаем студента, по которому нужно отобразить данные
		Student student = students.get(rowIndex);
		//Получаем занятие
		Lesson lesson = lessons.get(columnIndex - FIRST_LAB_COLUMN_INDEX);
		//Проверяем отсутствие студента на занятии
		boolean isAbsence = student.isAbsence(lesson);
		JPanel panelTableCell = new JPanel();
		//В зависимости от посещения занятия устанавливаем цвет панели
		if(!lesson.isHoliday()) {
			panelTableCell.setBackground(isAbsence ? Constants.ABSENCE_COLOR : Color.WHITE);
			//Проверяем, есть ли у студента оценка на текущем занятии
			Grade grade = null;
			if (!isAbsence || !lesson.isLecture()) {
				grade = student.getLessonGrade(lesson);
			}
			String text = "";
			//Если студент отсутствовал добавляем "н" на панель
			if (isAbsence) {
				text = "н";
				//Если оценка существует добавляем её на панель
			} else if (grade != null) {
				text = String.valueOf(grade.getGrade());
			}
			JLabel label = new JLabel(text);
			panelTableCell.add(label);
		}else {
			panelTableCell.setBackground(Color.ORANGE);
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
		if (columnIndex - FIRST_LAB_COLUMN_INDEX < lessons.size()) {
			return dateFormat.format(lessons.get(columnIndex - FIRST_LAB_COLUMN_INDEX).getDate());
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
		int studentIndex = students.indexOf(student);
		if (studentIndex == -1) {
			return -1; // если студент не найден, вернуть -1
		}
		// возвращаем индекс строки, увеличенный на 1, так как строки в таблице начинаются с 1, а не с 0
		return studentIndex / COUNT_SEPARATOR_ROW * (COUNT_SEPARATOR_ROW + 1) + studentIndex % COUNT_SEPARATOR_ROW;
	}


	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	/**
	 * Сортировка по алфавиту
	 * @param isInc true, если нужно сортировать по возрастанию
	 */
	public void sortByAlphabet(boolean isInc) {
		students.sort(Comparator.comparing(Student::getFullName, isInc ? Comparator.naturalOrder() : Comparator.reverseOrder()));
		fireTableDataChanged();
	}

	/**
	 * Сортировка по среднему баллу
	 * @param isInc true, если нужно сортировать по возрастанию
	 */
	public void sortByGrade(boolean isInc) {
		students.sort((o1, o2) -> {
			int result = Double.compare(o1.getAverageGrade(), o2.getAverageGrade());
			return isInc ? result : result * (-1);
		});
		fireTableDataChanged();
	}

	/**
	 * Сортировка по посещаемости
	 * @param isLecture true, если сортируется посещение лекционных занятий
	 * @param isInc true, если нужно сортировать по возрастанию
	 */
	public void sortByAttendance(boolean isLecture, boolean isInc) {
		if (isLecture) {
			students.sort((o1, o2) -> {
				int result = Integer.compare(o1.getLectureAbsenceList().size(), o2.getLectureAbsenceList().size());
				return isInc ? result * (-1) : result;
			});
		} else {
			students.sort((o1, o2) -> {
				int result = Integer.compare(o1.getLabAbsenceList().size(), o2.getLabAbsenceList().size());
				return isInc ? result * (-1) : result;
			});
		}

		fireTableDataChanged();
	}

	public List<Student> getStudents() {
		return students;
	}

	public List<Lesson> getLessons() {
		return lessons;
	}

	public String getGroupNumber() {
		return groupNumber;
	}

	public boolean isLecture() {
		return isLecture;
	}

	public int getFIRST_LAB_COLUMN_INDEX() {
		return FIRST_LAB_COLUMN_INDEX;
	}
}
