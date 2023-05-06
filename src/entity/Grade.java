package entity;

public class Grade {
	private long id;
	private long lessonId;
	private long studentId;
	private int grade;
	public Grade(long lessonId, long studentId, int gradeValue) {
		this.lessonId = lessonId;
		this.grade = gradeValue;
		this.studentId = studentId;
	}
	public Grade(){

	}
	public long getLessonId() {
		return lessonId;
	}

	public int getGrade() {
		return grade;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setLessonId(long lessonId) {
		this.lessonId = lessonId;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public long getStudentId() {
		return studentId;
	}

	public void setStudentId(long studentId) {
		this.studentId = studentId;
	}
}
