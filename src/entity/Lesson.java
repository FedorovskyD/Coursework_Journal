package entity;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Класс Lesson представляет занятие.
 */
public class Lesson {
	private long id;
	private Date date;
	private long groupId;
	private boolean isLecture;
	private boolean isHoliday;

	/**
	 * Конструктор по умолчанию. Создает пустой объект Lesson.
	 */
	public Lesson() {
	}

	/**
	 * Конструирует новый объект Lesson с указанной датой, идентификатором группы, признаком лекции и признаком праздника.
	 *
	 * @param date      дата занятия
	 * @param groupId   идентификатор группы
	 * @param isLecture признак лекции
	 * @param isHoliday признак праздника
	 */
	public Lesson(Date date, long groupId, boolean isLecture, boolean isHoliday) {
		this.date = date;
		this.groupId = groupId;
		this.isLecture = isLecture;
		this.isHoliday = isHoliday;
	}

	/**
	 * Возвращает идентификатор занятия.
	 *
	 * @return идентификатор занятия
	 */
	public long getId() {
		return id;
	}

	/**
	 * Возвращает дату занятия.
	 *
	 * @return дата занятия
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * Возвращает идентификатор группы.
	 *
	 * @return идентификатор группы
	 */
	public long getGroupId() {
		return groupId;
	}

	/**
	 * Устанавливает идентификатор занятия.
	 *
	 * @param id идентификатор занятия
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Устанавливает дату занятия.
	 *
	 * @param date дата занятия
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * Устанавливает идентификатор группы.
	 *
	 * @param groupId идентификатор группы
	 */
	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}

	/**
	 * Проверяет, является ли занятие лекцией.
	 *
	 * @return true, если занятие - лекция, false в противном случае
	 */
	public boolean isLecture() {
		return isLecture;
	}

	/**
	 * Устанавливает признак лекции для занятия.
	 *
	 * @param lecture признак лекции
	 */
	public void setLecture(boolean lecture) {
		isLecture = lecture;
	}

	/**
	 * Проверяет, является ли занятие праздничным.
	 *
	 * @return true, если занятие - праздничное, false в противном случае
	 */
	public boolean isHoliday() {
		return isHoliday;
	}

	/**
	 * Устанавливает признак праздника для занятия.
	 *
	 * @param holiday признак праздника
	 */
	public void setHoliday(boolean holiday) {
		isHoliday = holiday;
	}

	/**
	 * Возвращает строковое представление даты занятия в формате "дд.ММ.гггг".
	 *
	 * @return строковое представление даты занятия
	 */
	@Override
	public String toString() {
		return new SimpleDateFormat("dd.MM.yyyy").format(date);
	}
}
