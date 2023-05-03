package entity;

import java.util.ArrayList;
import java.util.List;

public class Student {
	private long id;
	private String firstName;
	private String lastName;
	private String middleName;
	private String telephone;
	private String email;
	private String photoPath;
	private List<Attendance> attendanceList;
	private List<Grade> gradeList;
	private int age;
	private long group;

	public Student() {
		attendanceList = new ArrayList<>();
		gradeList = new ArrayList<>();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public long getGroup() {
		return group;
	}

	public void setGroup(long group) {
		this.group = group;
	}

	public String getPhotoPath() {
		return photoPath;
	}

	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}

	public List<Attendance> getAttendanceList() {
		return attendanceList;
	}

	public void setAttendanceList(List<Attendance> attendanceList) {
		this.attendanceList = attendanceList;
	}

	public List<Grade> getGradeList() {
		return gradeList;
	}

	public void setGradeList(List<Grade> gradeList) {
		this.gradeList = gradeList;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public double getAverageGrade() {
		return gradeList.stream()
				.mapToInt(Grade::getGrade)
				.average()
				.orElse(0.0);
	}

	public Grade getLabGrade(Lab lab) {
		if (lab == null) {
			return null;
		}
		return gradeList.stream()
				.filter(grade -> grade.getLab() == lab.getId())
				.findFirst()
				.orElse(null);
	}

	public Attendance getLabAttendance(Lab lab) {
		if (lab == null) {
			return null;
		}
		return attendanceList.stream()
				.filter(a -> a.getLab() == lab.getId())
				.findFirst()
				.orElse(null);
	}


	public boolean isAttendance(Lab lab) {
		return attendanceList.stream().anyMatch(attendance -> attendance.getLab() == lab.getId());
	}


	public String getFullName() {
		return lastName + " " +
				firstName + " " +
				middleName;
	}
	public int getCountLabAttendance(){
		return attendanceList.size();
	}

	@Override
	public String toString() {
		return getFullName();
	}
}
