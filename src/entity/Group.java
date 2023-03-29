package entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Group {
	public void setId(int id) {
		this.id = id;
	}

	private int id;
	private String name;
	private List<Student> studentList;
	private List<Attendance> attendanceList;

	public Group(int id, String name) {
		this.id = id;
		this.name = name;
		studentList = new ArrayList<>();
		attendanceList = new ArrayList<>();
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public List<Student> getStudentList() {
		return studentList;
	}

	public List<Attendance> getAttendanceList() {
		return attendanceList;
	}

//	public void addStudent(Student student) {
//		studentList.add(student);
//		student.getGroup().removeStudent(student);
//		student.setGroupID(this.id);
//	}

	public void removeStudent(Student student) {
		studentList.remove(student);
	}

	public void addAttendance(Attendance attendance) {
		attendanceList.add(attendance);
	}

	public List<Attendance> getAttendanceByDate(Date date) {
		List<Attendance> result = new ArrayList<>();
		for (Attendance attendance : attendanceList) {
			if (attendance.getLab().getDate().equals(date)) {
				result.add(attendance);
			}
		}
		return result;
	}

}