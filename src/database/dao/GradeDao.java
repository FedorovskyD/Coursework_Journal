package database.dao;

import entity.Grade;
import entity.Lesson;
import entity.Student;

import java.util.List;

/**
 * Интерфейс для работы с оценками
 */
public interface GradeDao extends CrudDao<Grade> {

	/**
	 * Возвращает список оценок студента
	 *
	 * @param student студент, для которого нужно получить список оценок
	 * @return список оценок студента
	 */
	List<Grade> getGradesByStudent(Student student);

	/**
	 * Возвращает список оценок за лабораторную работу
	 *
	 * @param lesson лабораторная работа, для которой нужно получить список оценок
	 * @return список оценок за лабораторную работу
	 */
	List<Grade> getGradesByLab(Lesson lesson);
}

