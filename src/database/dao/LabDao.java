package database.dao;

import entity.Lab;

import java.util.List;

/**
 * Интерфейс для доступа к данным о лабораторных работах.
 */
public interface LabDao extends CrudDao<Lab> {
	 List<Lab> getAllLabByGroupId(long id) ;

}

