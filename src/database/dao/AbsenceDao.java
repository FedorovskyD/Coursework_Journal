package database.dao;


import entity.Absence;
import entity.Student;

import java.util.List;

/**
 * Интерфейс для доступа к данным по посещаемости лабораторных занятий.
 */
public interface AbsenceDao extends CrudDao<Absence> {

	/**
	 * Возвращает список записей о посещении для определенного студента.
	 *
	 * @param student студент
	 * @return список записей о посещении для данного студента
	 */
	List<Absence> getAttendancesByStudent(Student student);

	List<Absence> getLessonAttendancesByStudent(Student student, boolean isLecture);
}

