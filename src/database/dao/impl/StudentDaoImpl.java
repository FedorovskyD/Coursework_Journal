package database.dao.impl;

import database.ConnectionFactory;
import database.dao.StudentDao;
import entity.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDaoImpl implements StudentDao {

	private static final String SQL_SELECT_ALL_STUDENTS_BY_GROUP = "SELECT * FROM student WHERE GroupID=?";
	private static final String SQl_INSERT_STUDENT = "INSERT INTO student (LastName,FirstName, MiddleName, GroupID ,Telephone, Email) VALUES ( ?, ?, ?,?, ?, ?)";
	private static final String SQL_SELECT_ALL_STUDENTS = "SELECT * FROM student";
	private static final String SQL_SELECT_STUDENT_BY_ID = "SELECT * FROM student WHERE ID = ?";
	private static final String SQL_UPDATE_STUDENT = "UPDATE student SET LastName=?, FirstName=?, MiddleName=?,GroupID=?," +
			"Telephone=?,Email=?,PhotoPath=? WHERE id=?";
	private static final String SQL_DELETE_STUDENT = "DELETE FROM `student` WHERE ID = ?";
	private static StudentDaoImpl instance;

	private StudentDaoImpl() {

	}

	public static StudentDaoImpl getInstance() {
		if (instance == null) {
			instance = new StudentDaoImpl();
		}
		return instance;
	}

	@Override
	public Student findById(long id) {
		// Установить соединение с базой данных
		Student student = null;
		try (Connection connection = ConnectionFactory.getConnection();
		     PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_STUDENT_BY_ID)) {
			preparedStatement.setLong(1, id);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					String firstName = resultSet.getString("FirstName");
					String lastName = resultSet.getString("LastName");
					String middleName = resultSet.getString("MiddleName");
					String telephone = resultSet.getString("Telephone");
					String email = resultSet.getString("Email");
					String photoPath = resultSet.getString("PhotoPath");
					student = new Student();
					student.setId(id);
					student.setName(firstName);
					student.setSurname(lastName);
					student.setMiddleName(middleName);
					student.setTelephone(telephone);
					student.setEmail(email);
					student.setPhotoPath(photoPath);
				}
			} catch (SQLException ex) {
				throw new RuntimeException(ex);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return student;
	}

	@Override
	public List<Student> findAll() {
		List<Student> students = new ArrayList<>();
		try (Connection connection = ConnectionFactory.getConnection();
		     Statement statement = connection.createStatement()) {
			ResultSet rs = statement.executeQuery(SQL_SELECT_ALL_STUDENTS);
			createStudentList(students, rs);
			rs.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return students;
	}

	@Override
	public long save(Student entity) {
		long id = Long.MAX_VALUE;
		try (Connection connection = ConnectionFactory.getConnection();
		     PreparedStatement statement = connection.prepareStatement(SQl_INSERT_STUDENT, Statement.RETURN_GENERATED_KEYS)) {
			statement.setString(1, entity.getSurname());
			statement.setString(2, entity.getName());
			statement.setString(3, entity.getMiddleName());
			statement.setLong(4, entity.getGroup());
			statement.setString(5, entity.getTelephone());
			statement.setString(6, entity.getEmail());
			int rowsAffected = statement.executeUpdate();
			ResultSet generatedKeys = statement.getGeneratedKeys();
			if (generatedKeys.next()) {
				id = generatedKeys.getLong(1);
			}
			System.out.println(rowsAffected + " row(s) affected");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}

	@Override
	public boolean update(Student entity) {
		try (Connection connection = ConnectionFactory.getConnection();
		     PreparedStatement ps = connection.prepareStatement(SQL_UPDATE_STUDENT)) {
			ps.setString(1, entity.getSurname());
			ps.setString(2, entity.getName());
			ps.setString(3, entity.getMiddleName());
			ps.setLong(4, entity.getGroup());
			ps.setString(5, entity.getTelephone());
			ps.setString(6, entity.getEmail());
			ps.setString(7, entity.getPhotoPath());
			ps.setLong(8, entity.getId());
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean delete(Student entity) {
		try (Connection connection = ConnectionFactory.getConnection();
		     PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_STUDENT)) {
			preparedStatement.setLong(1, entity.getId());
			// Выполняем запрос
			int rowsAffected = preparedStatement.executeUpdate();
			return rowsAffected > 0;
		} catch (SQLException ex) {
			System.out.println("Error deleting group: " + ex.getMessage());
			return false;
		}
	}

	@Override
	public List<Student> getStudentsByGroupId(long groupId) {
		List<Student> students = new ArrayList<>();
		try (Connection connection = ConnectionFactory.getConnection();
		     PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ALL_STUDENTS_BY_GROUP)) {
			preparedStatement.setLong(1, groupId);
			ResultSet rs = preparedStatement.executeQuery();
			// Цикл по всем строкам результата запроса
			createStudentList(students, rs);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return students;
	}

	private void createStudentList(List<Student> students, ResultSet rs) throws SQLException {
		while (rs.next()) {
			// Создание экземпляра класса-сущности и заполнение полей
			Student student = new Student();
			student.setId(rs.getInt("ID"));
			student.setName(rs.getString("FirstName"));
			student.setSurname(rs.getString("LastName"));
			student.setMiddleName(rs.getString("MiddleName"));
			student.setEmail(rs.getString("Email"));
			student.setPhotoPath(rs.getString("PhotoPath"));
			student.setTelephone(rs.getString("Telephone"));
			student.setGradeList(GradeDaoImpl.getInstance().getGradesByStudent(student));
			student.setAttendanceList(AttendanceDaoImpl.getInstance().getAttendancesByStudent(student));
			students.add(student);
		}
	}
}
