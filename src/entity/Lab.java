package entity;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Lab {
	private int id;
	private Date date;
	private String classroom;
	private int group;
	private String LabName;
public Lab(){}
	public Lab(String classroom, Date date, int group,String labName) {
		this.classroom = classroom;
		this.date = date;
		this.group = group;
		this.LabName = labName;
	}

	public int getId() {
		return id;
	}

	public Date getDate() {
		return date;
	}

	public String getClassroom() {
		return classroom;
	}

	public int getGroup() {
		return group;
	}

	public String getLabName() {
		return LabName;
	}

	public void setLabName(String labName) {
		LabName = labName;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setClassroom(String classroom) {
		this.classroom = classroom;
	}

	public void setGroup(int group) {
		this.group = group;
	}

}
