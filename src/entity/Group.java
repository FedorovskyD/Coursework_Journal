package entity;

import java.util.List;

public class Group {
	public void setId(long id) {
		this.id = id;
	}

	private long id ;
	private String name;
	private List<Student> students;

	public Group(Long id, String name,List<Student> students) {
		this.students = students;
		this.id = id;
		this.name = name;
	}
	public Group(){}

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

	public void setName(String name) {
		this.name = name;
	}
}