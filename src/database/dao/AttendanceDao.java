package database.dao;


import entity.Attendance;
import entity.Lesson;
import entity.Student;

import java.sql.SQLException;
import java.util.List;

/**
 * Интерфейс для доступа к данным по посещаемости лабораторных занятий.
 */
public interface AttendanceDao extends CrudDao<Attendance> {

	/**
	 * Возвращает список записей о посещении для определенного студента.
	 *
	 * @param student студент
	 * @return список записей о посещении для данного студента
	 */
	List<Attendance> getAttendancesByStudent(Student student);

	List<Attendance> getLessonAttendancesByStudent(Student student, boolean isLecture);
}

