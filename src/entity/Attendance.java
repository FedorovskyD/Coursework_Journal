package entity;

class Attendance {
	private int id;
	private Lab lab;
	private Student student;
	private boolean isLecture;

	public Attendance(Lab lab, Student student, boolean isLecture) {
		this.lab = lab;
		this.student = student;
		this.isLecture = isLecture;
	}

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
}
