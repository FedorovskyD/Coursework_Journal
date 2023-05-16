package database.dao.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import database.ConnectionFactory;
import database.dao.AbsenceDao;
import entity.Absence;
import entity.Student;

public class AbsenceDaoImpl implements AbsenceDao {

	private static AbsenceDaoImpl instance;

	private AbsenceDaoImpl() {

	}

	public static AbsenceDaoImpl getInstance() {
		if (instance == null) {
			instance = new AbsenceDaoImpl();
		}
		return instance;
	}

	private static final String GET_ATTENDANCES_BY_STUDENT_SQL = "SELECT * FROM absence_log WHERE student_id = ?";
	private static final String GET_ATTENDANCES_BY_STUDENT_AND_LESSON_SQL = "SELECT *  FROM absence_log a JOIN lesson l ON a.lesson_ID = l.ID WHERE a.student_ID = ? AND l.isLecture=?";
	private static final String FIND_BY_ID_SQL = "SELECT * FROM absence_log WHERE id = ?";
	private static final String FIND_ALL_SQL = "SELECT * FROM absence_log";
	private static final String SAVE_SQL = "INSERT INTO absence_log(student_id,lesson_ID,isHalf) VALUES (?, ?,?)";
	private static final String UPDATE_SQL = "UPDATE absence_log SET lesson_ID = ?, student_id = ?,isHalf = ? WHERE id = ?";
	private static final String DELETE_SQL = "DELETE FROM absence_log WHERE ID = ?";

	private static Absence createAbsenceFromResultSet(ResultSet rs) throws SQLException {
		Absence absence = new Absence();
		absence.setId(rs.getInt("id"));
		absence.setLessonId(rs.getLong("lesson_ID"));
		absence.setStudentId(rs.getLong("student_ID"));
		absence.setHalf(rs.getBoolean("isHalf"));
		return absence;
	}

	@Override
	public List<Absence> getAttendancesByStudent(Student student) {
		List<Absence> absences = new ArrayList<>();
		try (Connection connection = ConnectionFactory.getConnection();
		     PreparedStatement ps = connection.prepareStatement(GET_ATTENDANCES_BY_STUDENT_SQL)) {
			ps.setLong(1, student.getId());
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					absences.add(createAbsenceFromResultSet(rs));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return absences;
	}

	@Override
	public List<Absence> getLessonAttendancesByStudent(Student student, boolean isLecture) {
		List<Absence> absences = new ArrayList<>();
		try (Connection connection = ConnectionFactory.getConnection();
		     PreparedStatement ps = connection.prepareStatement(GET_ATTENDANCES_BY_STUDENT_AND_LESSON_SQL)) {
			ps.setLong(1, student.getId());
			ps.setBoolean(2,isLecture);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					absences.add(createAbsenceFromResultSet(rs));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return absences;
	}


	@Override
	public Absence findById(long id) {
		Absence absence = null;
		try (Connection connection = ConnectionFactory.getConnection();
		     PreparedStatement ps = connection.prepareStatement(FIND_BY_ID_SQL)) {
			ps.setLong(1, id);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					absence = createAbsenceFromResultSet(rs);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return absence;
	}

	@Override
	public List<Absence> findAll() {
		List<Absence> absences = new ArrayList<>();
		try (Connection connection = ConnectionFactory.getConnection();
		     PreparedStatement ps = connection.prepareStatement(FIND_ALL_SQL)) {
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				absences.add(createAbsenceFromResultSet(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return absences;
	}

	@Override
	public long save(Absence entity) {
		long generatedId = -1;
		try (Connection connection = ConnectionFactory.getConnection();
		     PreparedStatement ps = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
			ps.setLong(2, entity.getLessonId());
			ps.setLong(1, entity.getStudentId());
			ps.setBoolean(3,entity.isHalf());
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
	public boolean update(Absence entity) {
		try (Connection connection = ConnectionFactory.getConnection();
		     PreparedStatement ps = connection.prepareStatement(UPDATE_SQL)) {
			ps.setLong(1, entity.getLessonId());
			ps.setLong(2, entity.getStudentId());
			ps.setBoolean(3,entity.isHalf());
			ps.setLong(4,entity.getId());
			ps.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean delete(Absence entity) {
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
