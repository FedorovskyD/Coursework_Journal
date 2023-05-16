package entity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Lesson {
	private long id;
	private Date date;
	private long groupId;
	private boolean isLecture;
	private boolean isHoliday;

	public Lesson() {
	}

	public Lesson(Date date, long groupId, boolean isLecture, boolean isHoliday) {
		this.date = date;
		this.groupId = groupId;
		this.isLecture = isLecture;
		this.isHoliday = isHoliday;
	}

	public long getId() {
		return id;
	}

	public Date getDate() {
		return date;
	}


	public long getGroupId() {
		return groupId;
	}


	public void setId(long id) {
		this.id = id;
	}

	public void setDate(Date date) {
		this.date = date;
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
