package entity;

public class Attendance {
	private int id;
	private long lab;
	private long student;
	private boolean isLecture;

	public Attendance(long lab, long student, boolean isLecture) {
		this.lab = lab;
		this.isLecture = isLecture;
	}
public Attendance(){}
	public int getId() {
		return id;
	}

	public long getLab() {
		return lab;
	}

	public long getStudent() {
		return student;
	}

	public boolean isLecture() {
		return isLecture;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setLab(long lab) {
		this.lab = lab;
	}

	public void setStudent(long student) {
		this.student = student;
	}

	public void setLecture(boolean lecture) {
		isLecture = lecture;
	}


}
