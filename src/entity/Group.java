package entity;
import entity.Student;

import java.util.ArrayList;
import java.util.List;

public class Group {
	private long id;
	private String name;
	private List<Student> students;
	private List<Lab> labs;

	public Group(Long id, String name, List<Student> students,List<Lab> labs) {
		this.id = id;
		this.name = name;
		this.students = students;
		this.labs = labs;
	}

	public Group() {
		students = new ArrayList<>();
		labs = new ArrayList<>();
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

	public List<Lab> getLabs() {
		return labs;
	}

	public void setLabs(List<Lab> labs) {
		this.labs = labs;
	}

	@Override
	public String toString() {
		return name;
	}
}