package entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс Group представляет группу студентов.
 */
public class Group {
	private long id;
	private String name;
	private final List<Student> students;
	private final List<Lesson> lessons;

	/**
	 * Конструирует новый объект Group с указанным идентификатором, именем, списком студентов и списком занятий.
	 *
	 * @param id       идентификатор группы
	 * @param name     имя группы
	 * @param students список студентов в группе
	 * @param lessons  список занятий группы
	 */
	public Group(Long id, String name, List<Student> students, List<Lesson> lessons) {
		this.id = id;
		this.name = name;
		this.students = students;
		this.lessons = lessons;
	}

	/**
	 * Конструктор по умолчанию. Создает пустой объект Group и инициализирует пустые списки студентов и занятий.
	 */
	public Group() {
		students = new ArrayList<>();
		lessons = new ArrayList<>();
	}

	/**
	 * Возвращает идентификатор группы.
	 *
	 * @return идентификатор группы
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Устанавливает идентификатор группы.
	 *
	 * @param id идентификатор группы
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Возвращает имя группы.
	 *
	 * @return имя группы
	 */
	public String getName() {
		return name;
	}

	/**
	 * Устанавливает имя группы.
	 *
	 * @param name имя группы
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Возвращает список студентов в группе.
	 *
	 * @return список студентов
	 */
	public List<Student> getStudents() {
		return students;
	}

	/**
	 * Возвращает список занятий группы.
	 *
	 * @return список занятий
	 */
	public List<Lesson> getLessons() {
		return lessons;
	}

	/**
	 * Возвращает список лабораторных занятий группы.
	 *
	 * @return список лабораторных занятий
	 */
	public List<Lesson> getLabs() {
		List<Lesson> labs = new ArrayList<>();
		for (Lesson lesson : lessons) {
			if (!lesson.isLecture()) {
				labs.add(lesson);
			}
		}
		return labs;
	}

	/**
	 * Возвращает список лекционных занятий группы.
	 *
	 * @return список лекционных занятий
	 */
	public List<Lesson> getLectures() {
		List<Lesson> lectures = new ArrayList<>();
		for (Lesson lesson : lessons) {
			if (lesson.isLecture()) {
				lectures.add(lesson);
			}
		}
		return lectures;
	}

	/**
	 * Возвращает количество часов лекционных занятий в группе.
	 *
	 * @return количество часов лекционных занятий
	 */
	public int getLectureHours() {
		return getLectures().size() * 2;
	}

	/**
	 * Возвращает количество часов лабораторных занятий в группе.
	 *
	 * @return количество часов лабораторных занятий
	 */
	public int getLabHours() {
		return getLabs().size() * 2;
	}

	/**
	 * Возвращает строковое представление группы (имя группы).
	 *
	 * @return строковое представление группы
	 */
	@Override
	public String toString() {
		return name;
	}
}
