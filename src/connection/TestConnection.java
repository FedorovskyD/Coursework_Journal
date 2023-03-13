package connection;

import entity.Student;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TestConnection {
    public static void main(String[] args) {
        try {
            ResultSet resultSet = MySQLConnector.executeQuery("SELECT * FROM student");
            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String name = resultSet.getString("FirstName");
                String email = resultSet.getString("Email");
                // do something with the data
                System.out.printf(id+name+email);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        Student student1 = new Student(1, "Иван", "Иванов", 18);
        MySQLConnector.addStudent(student1);




    }
}
