package database.dao.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import database.ConnectionFactory;
import database.dao.AttendanceDao;
import entity.Attendance;
import entity.Lesson;
import entity.Student;

public class AttendanceDaoImpl implements AttendanceDao {

	private static AttendanceDaoImpl instance;

	private AttendanceDaoImpl() {

	}

	public static AttendanceDaoImpl getInstance() {
		if (instance == null) {
			instance = new AttendanceDaoImpl();
		}
		return instance;
	}

	private static final String GET_ATTENDANCES_BY_STUDENT_SQL = "SELECT * FROM attendance WHERE student_id = ?";
	private static final String GET_ATTENDANCES_BY_LAB_SQL = "SELECT * FROM attendance WHERE lesson_ID = ?";
	private static final String FIND_BY_ID_SQL = "SELECT * FROM attendance WHERE id = ?";
	private static final String FIND_ALL_SQL = "SELECT * FROM attendance";
	private static final String SAVE_SQL = "INSERT INTO attendance(student_id,lesson_ID) VALUES (?, ?)";
	private static final String UPDATE_SQL = "UPDATE attendance SET lesson_ID = ?, student_id = ? WHERE id = ?";
	private static final String DELETE_SQL = "DELETE FROM attendance WHERE ID = ?";

	private static Attendance createAttendanceFromResultSet(ResultSet rs) throws SQLException {
		Attendance attendance = new Attendance();
		attendance.setId(rs.getInt("id"));

		attendance.setLessonId(rs.getLong("lesson_ID"));

		attendance.setStudentId(rs.getLong("student_ID"));
		return attendance;
	}

	@Override
	public List<Attendance> getAttendancesByStudent(Student student) {
		List<Attendance> attendances = new ArrayList<>();
		try (Connection connection = ConnectionFactory.getConnection();
		     PreparedStatement ps = connection.prepareStatement(GET_ATTENDANCES_BY_STUDENT_SQL)) {
			ps.setLong(1, student.getId());
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					attendances.add(createAttendanceFromResultSet(rs));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return attendances;
	}

	@Override
	public List<Attendance> getAttendancesByLab(Lesson lesson) {
		List<Attendance> attendances = new ArrayList<>();
		try (Connection connection = ConnectionFactory.getConnection();
		     PreparedStatement ps = connection.prepareStatement(GET_ATTENDANCES_BY_LAB_SQL)) {
			ps.setLong(1, lesson.getId());
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					attendances.add(createAttendanceFromResultSet(rs));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return attendances;
	}

	@Override
	public Attendance findById(long id) {
		Attendance attendance = null;
		try (Connection connection = ConnectionFactory.getConnection();
		     PreparedStatement ps = connection.prepareStatement(FIND_BY_ID_SQL)) {
			ps.setLong(1, id);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					attendance = createAttendanceFromResultSet(rs);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return attendance;
	}

	@Override
	public List<Attendance> findAll() {
		List<Attendance> attendances = new ArrayList<>();
		try (Connection connection = ConnectionFactory.getConnection();
		     PreparedStatement ps = connection.prepareStatement(FIND_ALL_SQL)) {
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				attendances.add(createAttendanceFromResultSet(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return attendances;
	}

	@Override
	public long save(Attendance entity) {
		long generatedId = -1;
		try (Connection connection = ConnectionFactory.getConnection();
		     PreparedStatement ps = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
			ps.setLong(2, entity.getLessonId());
			ps.setLong(1, entity.getStudentId());
			ps.executeUpdate();
			ResultSet generatedKeys = ps.getGeneratedKeys();
			if (generatedKeys.next()) {
				generatedId = generatedKeys.getLong(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return generatedId;
	}

	@Override
	public boolean update(Attendance entity) {
		try (Connection connection = ConnectionFactory.getConnection();
		     PreparedStatement ps = connection.prepareStatement(UPDATE_SQL)) {
			ps.setLong(1, entity.getLessonId());
			ps.setLong(2, entity.getStudentId());
			ps.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean delete(Attendance entity) {
		try (Connection connection = ConnectionFactory.getConnection();
		     PreparedStatement ps = connection.prepareStatement(DELETE_SQL)) {
			ps.setLong(1, entity.getId());
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}
