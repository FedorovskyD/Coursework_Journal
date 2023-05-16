package database.dao.impl;

import database.ConnectionFactory;
import database.dao.LessonDao;
import entity.Lesson;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LessonDaoImpl implements LessonDao {
	private static final String SQL_SELECT_LAB_BY_ID = "SELECT * FROM lesson WHERE id = ?";
	private static final String SQL_SELECT_ALL_LAB = "SELECT * FROM lesson";
	private static final String SQL_INSERT_LAB = "INSERT INTO lesson (date,  groupID, isLecture,isHoliday) VALUES (?, ?, ?, ?)";
	private static final String SQL_UPDATE_LAB = "UPDATE lesson SET date = ?, groupID = ? WHERE id = ?";
	private static final String SQL_DELETE_LAB = "DELETE FROM lesson WHERE ID = ?";
	private static final String SQL_SELECT_LAB_BY_GROUP = "SELECT * FROM lesson WHERE groupID = ?";


	private static LessonDaoImpl instance;

	private LessonDaoImpl() {

	}

	public static LessonDaoImpl getInstance() {
		if (instance == null) {
			instance = new LessonDaoImpl();
		}
		return instance;
	}

	@Override
	public Lesson findById(long id) {
		try (Connection connection = ConnectionFactory.getConnection();
		     PreparedStatement statement = connection.prepareStatement(SQL_SELECT_LAB_BY_ID)) {
			statement.setLong(1, id);
			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				return createLabFromResultSet(resultSet);
			}
		} catch (SQLException e) {
			System.err.println("Error while finding Lesson by ID: " + e.getMessage());
		}

		return null;
	}

	@Override
	public List<Lesson> findAll() {
		List<Lesson> lessons = new ArrayList<>();
		try (Connection connection = ConnectionFactory.getConnection();
		     Statement statement = connection.createStatement();
		     ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_LAB)) {

			while (resultSet.next()) {
				Lesson lesson = createLabFromResultSet(resultSet);
				lessons.add(lesson);
			}

		} catch (SQLException e) {
			System.err.println("Error while finding all Labs: " + e.getMessage());
		}

		return lessons;
	}

	@Override
	public List<Lesson> getAllLabByGroupId(long id) {
		List<Lesson> lessons = new ArrayList<>();
		try (Connection connection = ConnectionFactory.getConnection();
		     PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_LAB_BY_GROUP)) {
			preparedStatement.setLong(1,id);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {

				while (resultSet.next()) {
					Lesson lesson = createLabFromResultSet(resultSet);
					lessons.add(lesson);
				}

			} catch (SQLException e) {
				System.err.println("Error while finding all Labs: " + e.getMessage());
			}

			return lessons;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public long save(Lesson entity) {

		try (Connection connection = ConnectionFactory.getConnection();
		     PreparedStatement statement = connection.prepareStatement(SQL_INSERT_LAB, Statement.RETURN_GENERATED_KEYS)) {
			statement.setDate(1, new Date(entity.getDate().getTime()));
			statement.setLong(2, entity.getGroupId());
			statement.setBoolean(3,entity.isLecture());
			statement.setBoolean(4,entity.isHoliday());
			int rowsAffected = statement.executeUpdate();

			if (rowsAffected == 0) {
				throw new SQLException("Creating Lesson failed, no rows affected.");
			}

			try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					return generatedKeys.getLong(1);
				} else {
					throw new SQLException("Creating Lesson failed, no ID obtained.");
				}
			}

		} catch (SQLException e) {
			System.err.println("Error while saving Lesson: " + e.getMessage());
		}

		return -1;
	}

	@Override
	public boolean update(Lesson entity) {

		try (Connection connection = ConnectionFactory.getConnection();
		     PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_LAB)) {
			statement.setDate(1, new Date(entity.getDate().getTime()));

			statement.setLong(2, entity.getGroupId());

			statement.setLong(3, entity.getId());
			int rowsAffected = statement.executeUpdate();

			return rowsAffected > 0;

		} catch (SQLException e) {
			System.err.println("Error while updating Lesson: " + e.getMessage());
		}

		return false;
	}

	@Override
	public boolean delete(Lesson entity) {

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

	private Lesson createLabFromResultSet(ResultSet resultSet) throws SQLException {
		long id = resultSet.getLong("ID");
		Date date = resultSet.getDate("date");
		int groupId = resultSet.getInt("groupID");
		boolean isLecture = resultSet.getBoolean("isLecture");
		boolean isHoliday = resultSet.getBoolean("isHoliday");

		Lesson lesson = new Lesson();
		lesson.setId(id);
		lesson.setDate(date);
		lesson.setGroupId(groupId);
		lesson.setLecture(isLecture);
		lesson.setHoliday(isHoliday);
		return lesson;
	}

}
