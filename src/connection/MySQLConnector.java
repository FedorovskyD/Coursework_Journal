package connection;

import entity.Student;

import java.sql.*;

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
                    "INSERT INTO student (LastName,FirstName, MiddleName, `Group` ,Telephone, Email) VALUES ( ?, ?, ?,1, ?, ?)");

            statement.setString(1, "'"+student.getSurname()+"'");
            statement.setString(2, student.getName());
            statement.setString(3, student.getMiddleName());
            statement.setString(4, student.getTelephone());
            statement.setString(5, student.getEmail());



            int rowsAffected = statement.executeUpdate();
            System.out.println(rowsAffected + " row(s) affected");
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

