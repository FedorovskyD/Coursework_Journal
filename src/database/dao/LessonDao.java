package database.dao;

import entity.Lesson;

import java.util.List;

/**
 * Интерфейс для доступа к данным о лабораторных работах.
 */
public interface LessonDao extends CrudDao<Lesson> {
	 List<Lesson> getAllLabByGroupId(long id) ;

}

