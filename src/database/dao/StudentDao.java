package database.dao;

import entity.Student;

import java.util.List;

public interface StudentDao extends CrudDao<Student> {
	/**
	 * Возвращает студентов по заданной группе
	 *
	 * @param groupId - ID группы
	 * @return - список студентов в группе
	 */
	List<Student> getStudentsByGroupId(long groupId);
}
