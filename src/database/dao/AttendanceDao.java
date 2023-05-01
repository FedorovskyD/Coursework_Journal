package database.dao;


import entity.Attendance;
import entity.Lab;
import entity.Student;

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

	/**
	 * Возвращает список записей о посещении для определенной лабораторной работы.
	 *
	 * @param lab лабораторная работа
	 * @return список записей о посещении для данной лабораторной работы
	 */
	List<Attendance> getAttendancesByLab(Lab lab);
}

