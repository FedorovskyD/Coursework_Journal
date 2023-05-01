package entity;

public class Grade {
	private int id;
	private long lab;
	private long student;
	private int grade;
	public Grade(long lab,long student, int gradeValue) {
		this.lab = lab;
		this.grade = gradeValue;
		this.student = student;
	}
	public Grade(){

	}
	public long getLab() {
		return lab;
	}

	public int getGrade() {
		return grade;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setLab(long lab) {
		this.lab = lab;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public long getStudent() {
		return student;
	}

	public void setStudent(long student) {
		this.student = student;
	}
}
