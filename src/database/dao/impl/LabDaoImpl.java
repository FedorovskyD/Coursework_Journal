package database.dao.impl;

import database.ConnectionFactory;
import database.dao.LabDao;
import entity.Group;
import entity.Lab;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LabDaoImpl implements LabDao {
	private static final String SQL_SELECT_LAB_BY_ID = "SELECT * FROM lab WHERE id = ?";
	private static final String SQL_SELECT_ALL_LAB = "SELECT * FROM lab";
	private static final String SQL_INSERT_LAB = "INSERT INTO lab (date, classroom, groupID, lab_name) VALUES (?, ?, ?, ?)";
	private static final String SQL_UPDATE_LAB = "UPDATE lab SET date = ?, classroom = ?, groupID = ?, lab_name = ? WHERE id = ?";
	private static final String SQL_DELETE_LAB = "DELETE FROM lab WHERE ID = ?";
	private static final String SQL_SELECT_LAB_BY_GROUP = "SELECT * FROM lab WHERE groupID = ?";


	private static LabDaoImpl instance;

	private LabDaoImpl() {

	}

	public static LabDaoImpl getInstance() {
		if (instance == null) {
			instance = new LabDaoImpl();
		}
		return instance;
	}

	@Override
	public Lab findById(long id) {
		try (Connection connection = ConnectionFactory.getConnection();
		     PreparedStatement statement = connection.prepareStatement(SQL_SELECT_LAB_BY_ID)) {
			statement.setLong(1, id);
			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				return createLabFromResultSet(resultSet);
			}
		} catch (SQLException e) {
			System.err.println("Error while finding Lab by ID: " + e.getMessage());
		}

		return null;
	}

	@Override
	public List<Lab> findAll() {
		List<Lab> labs = new ArrayList<>();
		try (Connection connection = ConnectionFactory.getConnection();
		     Statement statement = connection.createStatement();
		     ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_LAB)) {

			while (resultSet.next()) {
				Lab lab = createLabFromResultSet(resultSet);
				labs.add(lab);
			}

		} catch (SQLException e) {
			System.err.println("Error while finding all Labs: " + e.getMessage());
		}

		return labs;
	}

	@Override
	public List<Lab> getAllLabByGroupId(long id) {
		List<Lab> labs = new ArrayList<>();
		try (Connection connection = ConnectionFactory.getConnection();
		     PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_LAB_BY_GROUP)) {
			preparedStatement.setLong(1,id);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {

				while (resultSet.next()) {
					Lab lab = createLabFromResultSet(resultSet);
					labs.add(lab);
				}

			} catch (SQLException e) {
				System.err.println("Error while finding all Labs: " + e.getMessage());
			}

			return labs;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public long save(Lab entity) {

		try (Connection connection = ConnectionFactory.getConnection();
		     PreparedStatement statement = connection.prepareStatement(SQL_INSERT_LAB, Statement.RETURN_GENERATED_KEYS)) {
			statement.setDate(1, new Date(entity.getDate().getTime()));
			statement.setString(2, entity.getClassroom());
			statement.setLong(3, entity.getGroup());
			statement.setString(4, entity.getLabName());
			int rowsAffected = statement.executeUpdate();

			if (rowsAffected == 0) {
				throw new SQLException("Creating Lab failed, no rows affected.");
			}

			try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					return generatedKeys.getLong(1);
				} else {
					throw new SQLException("Creating Lab failed, no ID obtained.");
				}
			}

		} catch (SQLException e) {
			System.err.println("Error while saving Lab: " + e.getMessage());
		}

		return -1;
	}

	@Override
	public boolean update(Lab entity) {

		try (Connection connection = ConnectionFactory.getConnection();
		     PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_LAB)) {
			statement.setDate(1, new Date(entity.getDate().getTime()));
			statement.setString(2, entity.getClassroom());
			statement.setLong(3, entity.getGroup());
			statement.setString(4, entity.getLabName());
			statement.setLong(5, entity.getId());
			int rowsAffected = statement.executeUpdate();

			return rowsAffected > 0;

		} catch (SQLException e) {
			System.err.println("Error while updating Lab: " + e.getMessage());
		}

		return false;
	}

	@Override
	public boolean delete(Lab entity) {

		try (Connection connection = ConnectionFactory.getConnection();
		     PreparedStatement statement = connection.prepareStatement(SQL_DELETE_LAB)) {
			statement.setLong(1, entity.getId());
			int rowsAffected = statement.executeUpdate();
			return rowsAffected > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	private Lab createLabFromResultSet(ResultSet resultSet) throws SQLException {
		long id = resultSet.getLong("ID");
		Date date = resultSet.getDate("date");
		String classroom = resultSet.getString("classroom");
		int groupId = resultSet.getInt("groupID");
		String labName = resultSet.getString("lab_name");

		Lab lab = new Lab();
		lab.setId(id);
		lab.setDate(date);
		lab.setClassroom(classroom);
		lab.setGroup(groupId);
		lab.setLabName(labName);
		return lab;
	}

}
