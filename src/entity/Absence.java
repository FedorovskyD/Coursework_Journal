package entity;

/**
 * Класс Absence представляет запись об отсутствии студента на занятии.
 */
public class Absence {
	private long id;
	private long lessonId;
	private long studentId;
	private boolean isHalf;

	/**
	 * Конструирует новый объект Absence с указанным идентификатором занятия, идентификатором студента и статусом отсутствия.
	 *
	 * @param lessonId  идентификатор занятия
	 * @param studentId идентификатор студента
	 * @param isHalf    статус отсутствия (true - половинчатое отсутствие, false - полное отсутствие)
	 */
	public Absence(long lessonId, long studentId, boolean isHalf) {
		this.lessonId = lessonId;
		this.studentId = studentId;
		this.isHalf = isHalf;
	}

	/**
	 * Конструктор по умолчанию. Создает пустой объект Absence.
	 */
	public Absence() {
	}

	/**
	 * Возвращает идентификатор записи об отсутствии.
	 *
	 * @return идентификатор записи об отсутствии
	 */
	public long getId() {
		return id;
	}

	/**
	 * Возвращает идентификатор занятия, на котором произошло отсутствие.
	 *
	 * @return идентификатор занятия
	 */
	public long getLessonId() {
		return lessonId;
	}

	/**
	 * Возвращает идентификатор студента, отсутствующего на занятии.
	 *
	 * @return идентификатор студента
	 */
	public long getStudentId() {
		return studentId;
	}

	/**
	 * Устанавливает идентификатор записи об отсутствии.
	 *
	 * @param id идентификатор записи об отсутствии
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Устанавливает идентификатор занятия, на котором произошло отсутствие.
	 *
	 * @param lessonId идентификатор занятия
	 */
	public void setLessonId(long lessonId) {
		this.lessonId = lessonId;
	}

	/**
	 * Устанавливает идентификатор студента, отсутствующего на занятии.
	 *
	 * @param studentId идентификатор студента
	 */
	public void setStudentId(long studentId) {
		this.studentId = studentId;
	}

	/**
	 * Проверяет, является ли отсутствие половинчатым.
	 *
	 * @return true, если отсутствие половинчатое, false - в противном случае
	 */
	public boolean isHalf() {
		return isHalf;
	}

	/**
	 * Устанавливает статус отсутствия.
	 *
	 * @param half статус отсутствия (true - половинчатое отсутствие, false - полное отсутствие)
	 */
	public void setHalf(boolean half) {
		isHalf = half;
	}
}

