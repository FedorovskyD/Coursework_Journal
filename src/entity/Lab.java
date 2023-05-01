package entity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Lab {
	private long id;
	private Date date;
	private String classroom;
	private long group;
	private String LabName;
public Lab(){}
	public Lab(String classroom, Date date, long group,String labName) {
		this.classroom = classroom;
		this.date = date;
		this.group = group;
		this.LabName = labName;
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

	public long getGroup() {
		return group;
	}

	public String getLabName() {
		return LabName;
	}

	public void setLabName(String labName) {
		LabName = labName;
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

	public void setGroup(long group) {
		this.group = group;
	}

	@Override
	public String toString() {
		return new SimpleDateFormat("dd.MM.yyyy").format(date);
	}
}
