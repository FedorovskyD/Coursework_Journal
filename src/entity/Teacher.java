package entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class Teacher {
	private int id;
	private String name;

	public Teacher(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Lab createLab(String classroom, Date date, int group,String name) {
		return new Lab(classroom, date, group,name);
	}

	public Grade gradeStudent(Lab lab, Student student, int gradeValue) {
		Grade grade = new Grade(lab, student, gradeValue);
		student.addGrade(grade);
		return grade;
	}
}