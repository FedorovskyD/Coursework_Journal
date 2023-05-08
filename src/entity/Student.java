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
	private long groupId;

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

	public long getGroupId() {
		return groupId;
	}

	public void setGroupId(long groupId) {
		this.groupId = groupId;
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

	public double getAverageGrade() {
		return gradeList.stream()
				.mapToInt(Grade::getGrade)
				.average()
				.orElse(0.0);
	}

	public Grade getLabGrade(Lesson lesson) {
		if (lesson == null) {
			return null;
		}
		return gradeList.stream()
				.filter(grade -> grade.getLessonId() == lesson.getId())
				.findFirst()
				.orElse(null);
	}

	public Attendance getLabAttendance(Lesson lesson) {
		if (lesson == null) {
			return null;
		}
		return attendanceList.stream()
				.filter(a -> a.getLessonId() == lesson.getId())
				.findFirst()
				.orElse(null);
	}


	public boolean isAttendance(Lesson lesson) {
		return attendanceList.stream().anyMatch(attendance -> attendance.getLessonId() == lesson.getId());
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
	public void getCountOfLectureAttendance(){
		for(Attendance attendance: attendanceList){

		}
	}
}
