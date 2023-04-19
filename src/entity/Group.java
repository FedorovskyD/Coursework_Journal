package entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Group {
	public void setId(Long id) {
		this.id = id;
	}

	private Long id;
	private String name;
	private List<Student> students;

	public Group(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

	@Override
	public String toString() {
		return name;
	}

	public void removeStudent(Student student) {
		students.remove(student);
	}

}