package connection;

import entity.Authors;
import entity.Student;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLConnector {
	public static Connection getConnection() throws SQLException {
		String url = "jdbc:mysql://localhost:3306/studentjournaldb";
		String username = "root";
		String password = "root";
		return DriverManager.getConnection(url, username, password);
	}

	public static ResultSet executeQuery(String query) throws SQLException {
		Connection connection = getConnection();
		Statement statement = connection.createStatement();
		return statement.executeQuery(query);
	}

	public static int executeUpdate(String query) throws SQLException {
		Connection connection = getConnection();
		Statement statement = connection.createStatement();
		return statement.executeUpdate(query);
	}

	public static long addStudent(Student student) {
		long id = -1;
		try {
			Connection connection = MySQLConnector.getConnection();
			PreparedStatement statement = connection.prepareStatement(
					//language=SQL
					"INSERT INTO student (LastName,FirstName, MiddleName, GroupID ,Telephone, Email) VALUES ( ?, ?, ?,?, ?, ?)",Statement.RETURN_GENERATED_KEYS);

			statement.setString(1, student.getSurname());
			statement.setString(2, student.getName());
			statement.setString(3, student.getMiddleName());
			statement.setLong(4,student.getGroup());
			statement.setString(5, student.getTelephone());
			statement.setString(6, student.getEmail());
			int rowsAffected = statement.executeUpdate();
			ResultSet generatedKeys= statement.getGeneratedKeys();

			if (generatedKeys.next()){
				id = generatedKeys.getLong(1);
			}
			System.out.println(rowsAffected + " row(s) affected");
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}

	public static List<String> getAllGroupNumbers() {
		List<String> groupNumbers = new ArrayList<>();
		try {
			Connection connection = MySQLConnector.getConnection();
			// Создаем SQL запрос для получения номеров групп
			//language=sql
			String sql = "SELECT GroupNumber FROM `group`";
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				String groupNumber = resultSet.getString("GroupNumber");
				groupNumbers.add(groupNumber);
			}
			resultSet.close();
			statement.close();
			connection.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return groupNumbers;
	}

	public static List<Student> getAllStudentsByGroup(String groupNumber) {
		List<Student> students = new ArrayList<>();
		try {
			Connection conn = getConnection();
			PreparedStatement stmt = conn.prepareStatement("SELECT s.*, g.GroupNumber" +
					" FROM student  s" +
					" JOIN `group` g ON s.GroupID = g.ID WHERE GroupNumber=?;");
			stmt.setString(1, groupNumber);
			ResultSet rs = stmt.executeQuery();
			// Цикл по всем строкам результата запроса
			while (rs.next()) {
				// Создание экземпляра класса-сущности и заполнение полей
				Student student = new Student();
				student.setId(rs.getInt("ID"));
				student.setName(rs.getString("FirstName"));
				student.setSurname(rs.getString("LastName"));
				student.setMiddleName(rs.getString("MiddleName"));
				student.setEmail(rs.getString("Email"));
				//student.setGroup(rs.getString("GroupNumber"));
				// Добавление экземпляра в список студентов
				students.add(student);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return students;
	}
	public static Long getGroupIDByGroupNumber(String groupNumber){
		long groupId = -1L; // если группа не найдена, возвращаем -1

		try {
			Connection connection = getConnection();
			PreparedStatement statement = connection.prepareStatement("SELECT ID FROM `group` WHERE GroupNumber = ?");
			statement.setString(1,groupNumber);
			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				groupId = resultSet.getInt("ID");
			}

			resultSet.close();
			statement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return groupId;
	}

	public static Student getStudentById(long id)  {
		// Установить соединение с базой данных
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Student student = new Student();
		try {
			Connection connection = getConnection();

			// Создать объект PreparedStatement
			preparedStatement = connection.prepareStatement("SELECT * FROM student WHERE id=?");

			// Установить значение параметра id
			preparedStatement.setLong(1, id);

			// Выполнить запрос
			resultSet = preparedStatement.executeQuery();

			// Извлечь данные студента из ResultSet

			if (resultSet.next()) {
				String firstName = resultSet.getString("FirstName");
				String lastName = resultSet.getString("LastName");
				String middleName = resultSet.getString("MiddleName");
				String telephone = resultSet.getString("Telephone");
				String email = resultSet.getString("Email");
				String photoPath = resultSet.getString("PhotoPath");
				student = new Student();
				student.setName(firstName);
				student.setSurname(lastName);
				student.setMiddleName(middleName);
				student.setTelephone(telephone);
				student.setEmail(email);
				student.setPhotoPath(photoPath);
				// Закрыть ResultSet и PreparedStatement
				resultSet.close();
				preparedStatement.close();

			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		// Возвращаем найденного студента
		return student;
	}
	public static void addGroup(String groupName) {
		try {
			// Создаем подключение к базе данных
			Connection conn = getConnection();

			// Создаем SQL-запрос для добавления новой группы
			String sql = "INSERT INTO `group` (groupnumber) VALUES (?)";

			// Подготавливаем запрос и задаем параметры
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, groupName);

			// Выполняем запрос
			statement.executeUpdate();

// Создаем новый экземпляр модели ComboBoxModel

			// Закрываем ресурсы
			statement.close();
			conn.close();

			System.out.println("Group added successfully!");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static boolean deleteGroup(String groupName) {
		try (Connection conn = getConnection()) {
			// Создаем подготовленный запрос для удаления группы
			String sql = "DELETE FROM `group` WHERE GroupNumber = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, groupName);
			// Выполняем запрос
			int rowsAffected = stmt.executeUpdate();
			return rowsAffected > 0;
		} catch (SQLException ex) {
			System.out.println("Error deleting group: " + ex.getMessage());
			return false;
		}
	}
	public static boolean deleteStudent(long id) {
		try (Connection conn = getConnection()) {
			// Создаем подготовленный запрос для удаления группы
			String sql = "DELETE FROM `student` WHERE ID = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setLong(1, id);
			// Выполняем запрос
			int rowsAffected = stmt.executeUpdate();
			return rowsAffected > 0;
		} catch (SQLException ex) {
			System.out.println("Error deleting group: " + ex.getMessage());
			return false;
		}
	}
	public static void addPhotoPath(String photoPath,long id){
		String sql = "UPDATE student SET PhotoPath = ? WHERE ID = ?";
		try (Connection conn = getConnection();
		     PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, photoPath);
			pstmt.setLong(2, id);

			int rowsUpdated = pstmt.executeUpdate();
			if (rowsUpdated > 0) {
				System.out.println("Photo path has been updated for student with ID " + id);
			} else {
				System.out.println("No student found with ID " + id);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	/*public List<Authors> getProjectAuthors(long projectId) throws SQLException {
		String query = "SELECT FirstName, LastName, Email FROM student WHERE projectId = ?";
		Connection connection = getConnection();
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setLong( 1,projectId);
		ResultSet resultSet = statement.executeQuery();
		List<Authors> authors = new ArrayList<>();
		while (resultSet.next()) {
			String firstName = resultSet.getString("first_name");
			String lastName = resultSet.getString("last_name");
			String email = resultSet.getString("email");
			Authors author = new Authors();
			authors.add(author);
		}
		connection.close();
		return authors;
		}
	 */

}

