package entity;

/**
 * Класс Grade представляет оценку, присвоенную студенту за занятие.
 */
public class Grade {
	private long id;
	private long lessonId;
	private long studentId;
	private int grade;

	/**
	 * Конструирует новый объект Grade с указанным идентификатором занятия, идентификатором студента и оценкой.
	 *
	 * @param lessonId   идентификатор занятия
	 * @param studentId  идентификатор студента
	 * @param gradeValue оценка
	 */
	public Grade(long lessonId, long studentId, int gradeValue) {
		this.lessonId = lessonId;
		this.grade = gradeValue;
		this.studentId = studentId;
	}

	/**
	 * Конструктор по умолчанию. Создает пустой объект Grade.
	 */
	public Grade() {
	}

	/**
	 * Возвращает идентификатор занятия, для которого присвоена оценка.
	 *
	 * @return идентификатор занятия
	 */
	public long getLessonId() {
		return lessonId;
	}

	/**
	 * Возвращает оценку, присвоенную студенту.
	 *
	 * @return оценка
	 */
	public int getGrade() {
		return grade;
	}

	/**
	 * Возвращает идентификатор записи об оценке.
	 *
	 * @return идентификатор записи об оценке
	 */
	public long getId() {
		return id;
	}

	/**
	 * Устанавливает идентификатор записи об оценке.
	 *
	 * @param id идентификатор записи об оценке
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Устанавливает идентификатор занятия, для которого присвоена оценка.
	 *
	 * @param lessonId идентификатор занятия
	 */
	public void setLessonId(long lessonId) {
		this.lessonId = lessonId;
	}

	/**
	 * Устанавливает оценку, присвоенную студенту.
	 *
	 * @param grade оценка
	 */
	public void setGrade(int grade) {
		this.grade = grade;
	}

	/**
	 * Возвращает идентификатор студента, которому присвоена оценка.
	 *
	 * @return идентификатор студента
	 */
	public long getStudentId() {
		return studentId;
	}

	/**
	 * Устанавливает идентификатор студента, которому присвоена оценка.
	 *
	 * @param studentId идентификатор студента
	 */
	public void setStudentId(long studentId) {
		this.studentId = studentId;
	}
}
