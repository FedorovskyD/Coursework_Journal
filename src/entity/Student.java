package entity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс Student представляет студента.
 */
public class Student {
	private long id;
	private String firstName;
	private String lastName;
	private String middleName;
	private String telephone;
	private String email;
	private File photoPath;
	private List<Absence> lectureAbsenceList;
	private List<Absence> labAbsenceList;
	private List<Grade> gradeList;
	private long groupId;

	/**
	 * Конструктор по умолчанию. Создает пустой объект Student.
	 */
	public Student() {
		lectureAbsenceList = new ArrayList<>();
		labAbsenceList = new ArrayList<>();
		gradeList = new ArrayList<>();
	}

	/**
	 * Возвращает идентификатор студента.
	 *
	 * @return идентификатор студента
	 */
	public long getId() {
		return id;
	}

	/**
	 * Устанавливает идентификатор студента.
	 *
	 * @param id идентификатор студента
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Возвращает имя студента.
	 *
	 * @return имя студента
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Устанавливает имя студента.
	 *
	 * @param firstName имя студента
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Возвращает фамилию студента.
	 *
	 * @return фамилия студента
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Устанавливает фамилию студента.
	 *
	 * @param lastName фамилия студента
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Возвращает отчество студента.
	 *
	 * @return отчество студента
	 */
	public String getMiddleName() {
		return middleName;
	}

	/**
	 * Устанавливает отчество студента.
	 *
	 * @param middleName отчество студента
	 */
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	/**
	 * Возвращает телефон студента.
	 *
	 * @return телефон студента
	 */
	public String getTelephone() {
		return telephone;
	}

	/**
	 * Устанавливает телефон студента.
	 *
	 * @param telephone телефон студента
	 */
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	/**
	 * Возвращает электронную почту студента.
	 *
	 * @return электронная почта студента
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Устанавливает электронную почту студента.
	 *
	 * @param email электронная почта студента
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Возвращает идентификатор группы, к которой принадлежит студент.
	 *
	 * @return идентификатор группы
	 */
	public long getGroupId() {
		return groupId;
	}

	/**
	 * Устанавливает идентификатор группы, к которой принадлежит студент.
	 *
	 * @param groupId идентификатор группы
	 */
	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}

	/**
	 * Возвращает путь к фотографии студента.
	 *
	 * @return путь к фотографии студента
	 */
	public File getPhotoPath() {
		return photoPath;
	}

	/**
	 * Устанавливает путь к фотографии студента.
	 *
	 * @param photoPath путь к фотографии студента
	 */
	public void setPhotoPath(File photoPath) {
		this.photoPath = photoPath;
	}

	/**
	 * Возвращает список пропусков на лекциях студента.
	 *
	 * @return список пропусков на лекциях
	 */
	public List<Absence> getLectureAbsenceList() {
		return lectureAbsenceList;
	}

	/**
	 * Устанавливает список пропусков на лекциях студента.
	 *
	 * @param lectureAbsenceList список пропусков на лекциях
	 */
	public void setLectureAttendanceList(List<Absence> lectureAbsenceList) {
		this.lectureAbsenceList = lectureAbsenceList;
	}

	/**
	 * Возвращает список пропусков на лабораторных работах студента.
	 *
	 * @return список пропусков на лабораторных работах
	 */
	public List<Absence> getLabAbsenceList() {
		return labAbsenceList;
	}

	/**
	 * Устанавливает список пропусков на лабораторных работах студента.
	 *
	 * @param labAbsenceList список пропусков на лабораторных работах
	 */
	public void setLabAttendanceList(List<Absence> labAbsenceList) {
		this.labAbsenceList = labAbsenceList;
	}

	/**
	 * Возвращает список оценок студента.
	 *
	 * @return список оценок
	 */
	public List<Grade> getGradeList() {
		return gradeList;
	}

	/**
	 * Устанавливает список оценок студента.
	 *
	 * @param gradeList список оценок
	 */
	public void setGradeList(List<Grade> gradeList) {
		this.gradeList = gradeList;
	}

	/**
	 * Возвращает средний балл студента.
	 *
	 * @return средний балл
	 */
	public double getAverageGrade() {
		return gradeList.stream()
				.mapToInt(Grade::getGrade)
				.average()
				.orElse(0.0);
	}

	/**
	 * Возвращает оценку студента за заданное занятие.
	 *
	 * @param lesson занятие
	 * @return оценка студента за занятие
	 */
	public Grade getLessonGrade(Lesson lesson) {
		if (lesson == null) {
			return null;
		}
		return gradeList.stream()
				.filter(grade -> grade.getLessonId() == lesson.getId())
				.findFirst()
				.orElse(null);
	}

	/**
	 * Возвращает пропуск студента на заданном занятии.
	 *
	 * @param lesson занятие
	 * @return пропуск студента на занятии
	 */
	public Absence getLessonAbsence(Lesson lesson) {
		if (lesson == null) {
			return null;
		}
		if (lesson.isLecture()) {
			return lectureAbsenceList.stream()
					.filter(a -> a.getLessonId() == lesson.getId())
					.findFirst()
					.orElse(null);
		} else {
			return labAbsenceList.stream()
					.filter(a -> a.getLessonId() == lesson.getId())
					.findFirst()
					.orElse(null);
		}
	}

	/**
	 * Проверяет, имеет ли студент пропуск на заданном занятии.
	 *
	 * @param lesson занятие
	 * @return true, если студент имеет пропуск на занятии, иначе false
	 */
	public boolean isAbsence(Lesson lesson) {
		return isAbsenceOnLab(lesson) || isAbsenceOnLecture(lesson);
	}

	/**
	 * Проверяет, имеет ли студент пропуск на заданной лабораторной работе.
	 *
	 * @param lesson занятие
	 * @return true, если студент имеет пропуск на лабораторной работе, иначе false
	 */
	public boolean isAbsenceOnLab(Lesson lesson) {
		return labAbsenceList.stream().anyMatch(absence -> absence.getLessonId() == lesson.getId());
	}

	/**
	 * Проверяет, имеет ли студент пропуск на заданной лекции.
	 *
	 * @param lesson занятие
	 * @return true, если студент имеет пропуск на лекции, иначе false
	 */
	public boolean isAbsenceOnLecture(Lesson lesson) {
		return lectureAbsenceList.stream().anyMatch(absence -> absence.getLessonId() == lesson.getId());
	}

	/**
	 * Возвращает полное имя студента в формате "Фамилия Имя Отчество".
	 *
	 * @return полное имя студента
	 */
	public String getFullName() {
		return lastName + " " +
				firstName + " " +
				middleName;
	}

	/**
	 * Возвращает количество пропущенных часов на лекциях.
	 *
	 * @return количество пропущенных часов на лекциях
	 */
	public int getLectureAbsenceHours() {
		int hours = 0;
		for (Absence absence : lectureAbsenceList) {
			if (absence.isHalf()) {
				hours++;
			} else {
				hours += 2;
			}
		}
		return hours;
	}

	/**
	 * Возвращает количество пропущенных часов на лабораторных работах.
	 *
	 * @return количество пропущенных часов на лабораторных работах
	 */
	public int getLabAbsenceHours() {
		int hours = 0;
		for (Absence absence : labAbsenceList) {
			if (absence.isHalf()) {
				hours++;
			} else {
				hours += 2;
			}
		}
		return hours;
	}
	@Override
	public String toString() {
		return getFullName();
	}
}
