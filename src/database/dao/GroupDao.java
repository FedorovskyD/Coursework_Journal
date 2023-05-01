package database.dao;

import entity.Group;
import entity.Student;

import java.util.List;

public interface GroupDao extends CrudDao<Group> {

	/**
	 * Получает список студентов, которые учатся в указанной группе
	 *
	 * @param group группа
	 * @return список студентов, учащихся в указанной группе
	 */
	List<Student> getStudents(Group group);

	/**
	 * Добавляет студента в группу
	 *
	 * @param group   группа
	 * @param student студент
	 * @return true, если операция выполнена успешно, false - в противном случае
	 */
	boolean addStudent(Group group, Student student);

	/**
	 * Удаляет студента из группы
	 *
	 * @param group   группа
	 * @param student студент
	 * @return true, если операция выполнена успешно, false - в противном случае
	 */
	boolean removeStudent(Group group, Student student);
}

