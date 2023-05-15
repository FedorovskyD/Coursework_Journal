package entity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Lesson {
	private long id;
	private Date date;
	private String classroom;
	private long groupId;
	private String LessonName;
	private boolean isLecture;
	private boolean isHoliday;

	public Lesson() {
	}

	public Lesson(String classroom, Date date, long groupId, String lessonName, boolean isLecture ,boolean isHoliday) {
		this.classroom = classroom;
		this.date = date;
		this.groupId = groupId;
		this.LessonName = lessonName;
		this.isLecture = isLecture;
		this.isHoliday= isHoliday;
	}

	public long getId() {
		return id;
	}

	public Date getDate() {
		return date;
	}

	public String getClassroom() {
		return classroom;
	}

	public long getGroupId() {
		return groupId;
	}

	public String getLessonName() {
		return LessonName;
	}

	public void setLessonName(String lessonName) {
		LessonName = lessonName;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setClassroom(String classroom) {
		this.classroom = classroom;
	}

	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}

	public boolean isLecture() {
		return isLecture;
	}

	public void setLecture(boolean lecture) {
		isLecture = lecture;
	}

	public boolean isHoliday() {
		return isHoliday;
	}

	public void setHoliday(boolean holiday) {
		isHoliday = holiday;
	}

	@Override
	public String toString() {
		return new SimpleDateFormat("dd.MM.yyyy").format(date);
	}
}
