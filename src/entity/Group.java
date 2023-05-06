package entity;

import java.util.ArrayList;
import java.util.List;

public class Group {
	private long id;
	private String name;
	private List<Student> students;
	private List<Lesson> lessons;

	public Group(Long id, String name, List<Student> students,List<Lesson> lessons) {
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

	public void setStudents(List<Student> students) {
		this.students = students;
	}

	public List<Lesson> getLabs() {
		return lessons;
	}

	public void setLabs(List<Lesson> lessons) {
		this.lessons = lessons;
	}

	@Override
	public String toString() {
		return name;
	}
}