package connection;

import entity.Student;

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

	public static void addStudent(Student student) {
		try {
			Connection connection = MySQLConnector.getConnection();
			PreparedStatement statement = connection.prepareStatement(
					//language=SQL
					"INSERT INTO student (LastName,FirstName, MiddleName, GroupID ,Telephone, Email) VALUES ( ?, ?, ?,?, ?, ?)");

			statement.setString(1, student.getSurname());
			statement.setString(2, student.getName());
			statement.setString(3, student.getMiddleName());
			statement.setLong(4,student.getGroup());
			statement.setString(5, student.getTelephone());
			statement.setString(6, student.getEmail());
			int rowsAffected = statement.executeUpdate();
			System.out.println(rowsAffected + " row(s) affected");
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
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
				student.setMiddlename(rs.getString("MiddleName"));
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
}

