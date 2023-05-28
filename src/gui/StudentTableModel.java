package gui;

import entity.*;
import utils.Constants;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.List;

/**
 * Модель таблицы для отображения
 * экземпляров класса Student
 */
public class StudentTableModel extends AbstractTableModel {
	private static final int STUDENT_COLUMN_INDEX = 0;//Индекс колонки с информацие о студенете
	private  int AVERAGE_GRADE_COLUMN_INDEX;//Индекс колонки с информацией о среднем балле
	private final int ATTENDANCE_PERCENTAGE_COLUMN_INDEX = 1;// Индекс колонки с информацией о проценте посещаемости
	private  int FIRST_LAB_COLUMN_INDEX;//Индекс с колонки, с которой начинаются даты занятий
	public static final int COUNT_SEPARATOR_ROW = 4;//Количество строк после, которых вставляется пустая строка
	private  List<Student> students;//Список студентов
	private  List<Lesson> lessons;//Список занятий
	private  Group group;// Группа студентов
	private  boolean isLecture;// Являются ли занятия лекциями

	/**
	 * Конструктор для создания новой модели таблицы
	 *
	 * @param group     группа, которую будет отображать таблица
	 * @param isLecture устанавливается true, если таблица будет
	 *                  отображать статистику лекционных занятий,
	 *                  если false - лабораторных занятий
	 */
	public StudentTableModel(Group group, boolean isLecture) {
		//Получаем список студентов из группы
		if(group==null)return;
		students = group.getStudents();
		//Заполняем список lessons, в зависимости от типа занятий, которые отображает таблица
		if (isLecture) {
			lessons = group.getLectures();
			FIRST_LAB_COLUMN_INDEX = 2;
			AVERAGE_GRADE_COLUMN_INDEX = -1;
		} else {
			lessons = group.getLabs();
			FIRST_LAB_COLUMN_INDEX = 3;
			AVERAGE_GRADE_COLUMN_INDEX = 2;
		}
		this.group = group;
		this.isLecture = isLecture;
	}

	/**
	 * Возвращает количество строк в таблице.
	 *
	 * @return Количество строк в таблице
	 */
	@Override
	public int getRowCount() {
		int rowCount = students.size();
		if (rowCount > 0) {
			// Добавляем к количеству строк пустые строки
			rowCount += (rowCount - 1) / COUNT_SEPARATOR_ROW;
		}
		return rowCount;
	}

	/**
	 * Возвращает количество столбцов в таблице.
	 *
	 * @return Количество столбцов в таблице
	 */
	@Override
	public int getColumnCount() {
		//Вычисляем количество столбцов
		if(lessons == null)return 0;
		return lessons.size() + FIRST_LAB_COLUMN_INDEX;
	}

	/**
	 * Возращает значение в ячейке таблицыы
	 *
	 * @param rowIndex    строка в которой находся возвращаемое значение
	 * @param columnIndex столбец в которой находся возвращаемое значение
	 * @return значение в ячейке таблицы
	 */
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
		if (columnIndex == ATTENDANCE_PERCENTAGE_COLUMN_INDEX) {
			Student student = students.get(getStudentIndex(rowIndex));
			double persantage;
			if (isLecture) {
				persantage = (group.getLectureHours() - student.getLectureAbsenceHours()) / (double) group.getLectureHours() * 100;
			} else {
				persantage = (group.getLabHours() - student.getLabAbsenceHours()) / (double) group.getLabHours() * 100;
			}
			return String.format("%.1f", persantage) + "%";
		}
		//Иначе возвращаем панель, отвечающую за отображение занятия
		return getLessonLabel(getStudentIndex(rowIndex), columnIndex);
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
	private JLabel getLessonLabel(int rowIndex, int columnIndex) {
		//Получаем студента, по которому нужно отобразить данные
		Student student = students.get(rowIndex);
		//Получаем занятие
		Lesson lesson = lessons.get(columnIndex - FIRST_LAB_COLUMN_INDEX);
		//Проверяем отсутствие студента на занятии
		Absence absence = student.getLessonAbsence(lesson);
		JLabel lblTableCell = new JLabel();
		//В зависимости от посещения занятия устанавливаем цвет панели
		if (!lesson.isHoliday()) {
			Color color;
			if (absence != null) {
				if (absence.isHalf()) {
					color = Constants.HALF_ABSENCE_COLOR;
				} else {
					color = Constants.ABSENCE_COLOR;
				}
			} else {
				color = Color.WHITE;
			}
			lblTableCell.setBackground(color);
			//Проверяем, есть ли у студента оценка на текущем занятии
			Grade grade = student.getLessonGrade(lesson);
			if (grade != null) {
				lblTableCell.setText(String.valueOf(grade.getGrade()));
			}

		} else {
			lblTableCell.setBackground(Color.ORANGE);
		}

		return lblTableCell;
	}

	/**
	 * Возвращает заголовок столбца по индексу
	 *
	 * @param columnIndex индекс столбец
	 * @return заголовок столбца
	 */
	@Override
	public String getColumnName(int columnIndex) {
		if (columnIndex == STUDENT_COLUMN_INDEX) {
			return "Студенты\\Даты";
		}
		if (columnIndex == AVERAGE_GRADE_COLUMN_INDEX) {
			return "Средний балл";
		}
		if (columnIndex == ATTENDANCE_PERCENTAGE_COLUMN_INDEX) {
			return "Посещаемость";
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		if (columnIndex - FIRST_LAB_COLUMN_INDEX < lessons.size()) {
			return dateFormat.format(lessons.get(columnIndex - FIRST_LAB_COLUMN_INDEX).getDate());
		}
		return "";
	}

	/**
	 * Возвращает тип данных хранимый в столбце
	 *
	 * @param columnIndex индекс столбца
	 * @return тип данных
	 */
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		if (columnIndex == STUDENT_COLUMN_INDEX) {
			return Student.class;
		}

		if (columnIndex == AVERAGE_GRADE_COLUMN_INDEX) {
			return String.class;
		}
		if (columnIndex == ATTENDANCE_PERCENTAGE_COLUMN_INDEX) {
			return String.class;
		}
		return JLabel.class;
	}

	/**
	 * Возвращает индекс строки, в которой отображается
	 * требуемый студент
	 *
	 * @param student требуемый студент
	 * @return индекс строки
	 */
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
	 *
	 * @param isInc true, если нужно сортировать по возрастанию
	 */
	public void sortByAlphabet(boolean isInc) {
		students.sort(Comparator.comparing(Student::getFullName, isInc ? Comparator.naturalOrder() : Comparator.reverseOrder()));
		fireTableDataChanged();
	}

	/**
	 * Сортировка по среднему баллу
	 *
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
	 *
	 * @param isLecture true, если сортируется посещение лекционных занятий
	 * @param isInc     true, если нужно сортировать по возрастанию
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

	/**
	 * Возвращает список студентов.
	 *
	 * @return список студентов
	 */
	public List<Student> getStudents() {
		return students;
	}

	/**
	 * Возвращает список занятий.
	 *
	 * @return список занятий
	 */
	public List<Lesson> getLessons() {
		return lessons;
	}

	/**
	 * Возвращает номер группы.
	 *
	 * @return номер группы
	 */
	public String getGroupNumber() {
		return group.getName();
	}

	/**
	 * Проверяет, является ли текущее занятие лекцией.
	 *
	 * @return true, если текущее занятие - лекция, иначе false
	 */
	public boolean isLecture() {
		return isLecture;
	}

	/**
	 * Возвращает индекс первого столбца лабораторных работ.
	 *
	 * @return индекс первого столбца лабораторных работ
	 */
	public int getFIRST_LAB_COLUMN_INDEX() {
		return FIRST_LAB_COLUMN_INDEX;
	}

	/**
	 * Возвращает объект группы.
	 *
	 * @return объект группы
	 */
	public Group getGroup() {
		return group;
	}
}
