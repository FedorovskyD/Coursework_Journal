package entity;

import java.util.ArrayList;
import java.util.List;

public class Group {
	private long id;
	private String name;
	private final List<Student> students;
	private final List<Lesson> lessons;

	public Group(Long id, String name, List<Student> students, List<Lesson> lessons) {
		this.id = id;
		this.name = name;
		this.students = students;
		this.lessons = lessons;
	}

	public Group() {
		students = new ArrayList<>();
		lessons = new ArrayList<>();
	}

	public Long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Student> getStudents() {
		return students;
	}

	public List<Lesson> getLessons() {
		return lessons;
	}

	public List<Lesson> getLabs() {
		List<Lesson> labs = new ArrayList<>();
		for (Lesson lesson : lessons) {
			if (!lesson.isLecture()) {
				labs.add(lesson);
			}
		}
		return labs;
	}

	public List<Lesson> getLectures() {
		List<Lesson> lectures = new ArrayList<>();
		for (Lesson lesson : lessons) {
			if (lesson.isLecture()) {
				lectures.add(lesson);
			}
		}
		return lectures;
	}

	public int getLectureHours() {
		return getLectures().size() * 2;
	}

	public int getLabHours() {
		return getLabs().size() * 2;
	}

	@Override
	public String toString() {
		return name;
	}
}