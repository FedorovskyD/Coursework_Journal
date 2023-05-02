package database.dao.impl;

import database.ConnectionFactory;
import database.dao.GradeDao;
import entity.Grade;
import entity.Lab;
import entity.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GradeDaoImpl implements GradeDao {
	private static GradeDaoImpl instance;

	private GradeDaoImpl() {

	}

	public static GradeDaoImpl getInstance() {
		if (instance == null) {
			instance = new GradeDaoImpl();
		}
		return instance;
	}

	// SQL-запросы
	private static final String SQL_FIND_GRADE_BY_ID = "SELECT * FROM grade WHERE ID=?";
	private static final String SQL_FIND_ALL_GRADES = "SELECT * FROM grade";
	private static final String SQL_INSERT_GRADE = "INSERT INTO grade (LabID, StudentID, Grade) VALUES (?, ?, ?)";
	private static final String SQL_UPDATE_GRADE = "UPDATE grade SET LabID=?, StudentID=?, Grade=? WHERE ID=?";
	private static final String SQL_DELETE_GRADE = "DELETE FROM grade WHERE ID=?";
	private static final String SQL_FIND_GRADES_BY_STUDENT = "SELECT * FROM grade WHERE StudentID=?";
	private static final String SQL_FIND_GRADES_BY_LAB = "SELECT * FROM grade WHERE LabID=?";

	@Override
	public Grade findById(long id) {
		Grade grade = null;
		try (Connection connection = ConnectionFactory.getConnection();
		     PreparedStatement statement = connection.prepareStatement(SQL_FIND_GRADE_BY_ID)) {
			statement.setLong(1, id);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				grade = createGradeFromResultSet(resultSet);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return grade;
	}

	@Override
	public List<Grade> findAll() {
		List<Grade> grades = new ArrayList<>();
		try (Connection connection = ConnectionFactory.getConnection();
		     PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL_GRADES)) {
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Grade grade = createGradeFromResultSet(resultSet);
				grades.add(grade);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return grades;
	}

	@Override
	public long save(Grade grade) {
		long id = -1;
		try (Connection connection = ConnectionFactory.getConnection();
		     PreparedStatement statement = connection.prepareStatement(SQL_INSERT_GRADE, Statement.RETURN_GENERATED_KEYS)) {
			statement.setLong(1, grade.getLab());
			statement.setLong(2, grade.getStudent());
			statement.setInt(3, grade.getGrade());
			statement.executeUpdate();
			ResultSet generatedKeys = statement.getGeneratedKeys();
			if (generatedKeys.next()) {
				id = generatedKeys.getLong(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}

	@Override
	public boolean update(Grade grade) {
		boolean result = false;
		try (Connection connection = ConnectionFactory.getConnection();
		     PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_GRADE)) {
			statement.setLong(1, grade.getLab());
			statement.setLong(2, grade.getStudent());
			statement.setInt(3, grade.getGrade());
			statement.setLong(4, grade.getId());
			int rowsAffected = statement.executeUpdate();
			if (rowsAffected > 0) {
				result = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;

	}
	@Override
	public boolean delete(Grade entity) {
		String query = SQL_DELETE_GRADE;
		try (Connection connection = ConnectionFactory.getConnection();
		     PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setLong(1, entity.getId());
			int affectedRows = statement.executeUpdate();
			return affectedRows != 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<Grade> getGradesByStudent(Student student) {
		List<Grade> grades = new ArrayList<>();
		try (Connection connection = ConnectionFactory.getConnection()) {
			String sql = "SELECT * FROM grade WHERE studentID = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setLong(1, student.getId());
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Grade grade = createGradeFromResultSet(resultSet);
				grades.add(grade);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return grades;
	}


	@Override
	public List<Grade> getGradesByLab(Lab lab) {
		List<Grade> grades = new ArrayList<>();
		try (Connection connection = ConnectionFactory.getConnection()) {
			String sql = "SELECT * FROM grade WHERE labID = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setLong(1, lab.getId());
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				Grade grade = createGradeFromResultSet(resultSet);
				grades.add(grade);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return grades;
	}
	private Grade createGradeFromResultSet(ResultSet rs) throws SQLException {
		int id = rs.getInt("ID");
		int gradeValue = rs.getInt("Grade");
		int labId = rs.getInt("LabID");
		int studentId = rs.getInt("StudentID");

		// Create the grade object with the fetched data
		Grade grade = new Grade(labId, studentId, gradeValue);
		grade.setId(id);
		return grade;
	}

}