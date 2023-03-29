package entity;

class Attendance {
	private int id;
	private Lab lab;
	private Student student;
	private boolean isPresent;

	public Attendance(Lab lab, Student student, boolean isPresent) {
		this.lab = lab;
		this.student = student;
		this.isPresent = isPresent;
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

	public boolean isPresent() {
		return isPresent;
	}
}
