package database.dao.impl;

import database.ConnectionFactory;
import database.dao.GroupDao;
import entity.Group;
import entity.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class GroupDaoImpl implements GroupDao {
	private static final String FIND_BY_ID_QUERY = "SELECT * FROM `group` WHERE ID = ?";
	private static final String FIND_ALL_QUERY = "SELECT * FROM `group`";
	private static final String SAVE_QUERY = "INSERT INTO `group` (GroupNumber) VALUES (?)";
	private static final String UPDATE_QUERY = "UPDATE `group` SET GroupNumber = ? WHERE ID = ?";
	private static final String DELETE_QUERY = "DELETE FROM `group` WHERE ID = ?";
	private static GroupDaoImpl instance;

	private GroupDaoImpl() {

	}

	public static GroupDaoImpl getInstance() {
		if (instance == null) {
			instance = new GroupDaoImpl();
		}
		return instance;
	}

	@Override
	public Group findById(long id) {
		Group group = null;
		try (Connection connection = ConnectionFactory.getConnection();
		     PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_QUERY)) {
			statement.setLong(1, id);
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					group = createGroupFromResultSet(resultSet);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return group;
	}

	@Override
	public List<Group> findAll() {
		List<Group> groups = new ArrayList<>();
		try (Connection connection = ConnectionFactory.getConnection();
		     PreparedStatement statement = connection.prepareStatement(FIND_ALL_QUERY);
		     ResultSet resultSet = statement.executeQuery()) {
			while (resultSet.next()) {
				groups.add(createGroupFromResultSet(resultSet));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return groups;
	}

	@Override
	public long save(Group entity) {
		long id = -1;
		try (Connection connection = ConnectionFactory.getConnection();
		     PreparedStatement statement = connection.prepareStatement(SAVE_QUERY, Statement.RETURN_GENERATED_KEYS)) {
			statement.setString(1, entity.getName());
			statement.executeUpdate();
			try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					id = generatedKeys.getLong(1);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}

	@Override
	public boolean update(Group entity) {
		try (Connection connection = ConnectionFactory.getConnection();
		     PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
			statement.setString(1, entity.getName());
			statement.setLong(2, entity.getId());
			return statement.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean delete(Group entity) {
		try (Connection connection = ConnectionFactory.getConnection();
		     PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
			statement.setLong(1, entity.getId());
			return statement.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public List<Student> getStudents(Group group) {
		return StudentDaoImpl.getInstance().getStudentsByGroupId(group.getId());
	}


	@Override
	public boolean addStudent(Group group, Student student) {
		return false;
	}

	@Override
	public boolean removeStudent(Group group, Student student) {
		return false;
	}
	private Group createGroupFromResultSet(ResultSet rs) throws SQLException {
		long id = rs.getLong("ID");
		String groupNumber = rs.getString("GroupNumber");
		return new Group(id, groupNumber,StudentDaoImpl.getInstance().getStudentsByGroupId(id), LessonDaoImpl.getInstance().getAllLabByGroupId(id));
	}
}


