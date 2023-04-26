package entity;

public class Attendance {
	private int id;
	private Lab lab;
	private Student student;
	private boolean isLecture;

	public Attendance(Lab lab, Student student, boolean isLecture) {
		this.lab = lab;
		this.student = student;
		this.isLecture = isLecture;
	}
public Attendance(){}
	public int getId() {
		return id;
	}

	public Lab getLab() {
		return lab;
	}

	public Student getStudent() {
		return student;
	}

	public boolean isLecture() {
		return isLecture;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setLab(Lab lab) {
		this.lab = lab;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public void setLecture(boolean lecture) {
		isLecture = lecture;
	}

}
