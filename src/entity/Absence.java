package entity;

public class Absence {
	private long id;
	private long lessonId;
	private long studentId;

	public Absence(long lessonId, long studentId) {
		this.lessonId = lessonId;
		this.studentId = studentId;
	}
public Absence(){}
	public long getId() {
		return id;
	}

	public long getLessonId() {
		return lessonId;
	}

	public long getStudentId() {
		return studentId;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setLessonId(long lessonId) {
		this.lessonId = lessonId;
	}

	public void setStudentId(long studentId) {
		this.studentId = studentId;
	}

}
