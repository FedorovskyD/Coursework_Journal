package entity;

public class Grade {
	private int id;
	private Lab lab;
	private Student student;
	private int grade;
	public Grade(Lab lab, Student student, int gradeValue) {
		this.lab = lab;
		this.student = student;
		this.grade = gradeValue;
	}
	public Lab getLab() {
		return lab;
	}

	public Student getStudent() {
		return student;
	}

	public int getGrade() {
		return grade;
	}
}
