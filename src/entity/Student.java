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
	private List<Absence> lectureAbsenceList;
	private List<Absence> labAbsenceList;

	private List<Grade> gradeList;
	private long groupId;

	public Student() {
		lectureAbsenceList = new ArrayList<>();
		labAbsenceList = new ArrayList<>();
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

	public List<Absence> getLectureAttendanceList() {
		return lectureAbsenceList;
	}

	public void setLectureAttendanceList(List<Absence> lectureAbsenceList) {
		this.lectureAbsenceList = lectureAbsenceList;
	}

	public List<Absence> getLabAttendanceList() {
		return labAbsenceList;
	}

	public void setLabAttendanceList(List<Absence> labAbsenceList) {
		this.labAbsenceList = labAbsenceList;
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

	public Grade getLessonGrade(Lesson lesson) {
		if (lesson == null) {
			return null;
		}
		return gradeList.stream()
				.filter(grade -> grade.getLessonId() == lesson.getId())
				.findFirst()
				.orElse(null);
	}

	public Absence getLessonAbsence(Lesson lesson) {
		if (lesson == null) {
			return null;
		}
		if (lesson.isLecture()) {
			return lectureAbsenceList.stream()
					.filter(a -> a.getLessonId() == lesson.getId())
					.findFirst()
					.orElse(null);
		}
		else {
			return labAbsenceList.stream()
					.filter(a -> a.getLessonId() == lesson.getId())
					.findFirst()
					.orElse(null);
		}
	}

	public boolean isAbsence(Lesson lesson) {
		return isAbsenceOnLab(lesson) || isAbsenceOnLecture(lesson);
	}

	public boolean isAbsenceOnLab(Lesson lesson) {
		return labAbsenceList.stream().anyMatch(absence -> absence.getLessonId() == lesson.getId());
	}
	public boolean isAbsenceOnLecture(Lesson lesson) {
		return lectureAbsenceList.stream().anyMatch(absence -> absence.getLessonId() == lesson.getId());
	}


	public String getFullName() {
		return lastName + " " +
				firstName + " " +
				middleName;
	}

	public int getCountLabAttendance() {
		return labAbsenceList.size();
	}

	@Override
	public String toString() {
		return getFullName();
	}
}
