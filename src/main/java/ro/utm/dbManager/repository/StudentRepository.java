package ro.utm.dbManager.repository;

import org.apache.log4j.Logger;
import ro.utm.dbManager.beans.StudentBean;
import ro.utm.dbManager.dao.DAOConnection;

import java.sql.*;
import java.util.ArrayList;

public class StudentRepository implements Repository<StudentBean> {

    final static Logger log = Logger.getLogger(StudentRepository.class);

    @Override
    public StudentBean find(long id) {
        StudentBean obj = null;

        try {
            PreparedStatement prepared = DAOConnection.getInstance().prepareStatement(
                    "SELECT * FROM students WHERE id=?");

            prepared.setLong(1, id);

            ResultSet result = prepared.executeQuery();

            if (result.first()) {
                obj = map(result);
            }

        } catch (SQLException e) {
            log.error("Error finding student : " + e);
        }

        return obj;
    }

    @Override

    public ArrayList<StudentBean> findAll() {
        ArrayList<StudentBean> students = new ArrayList<>();

        try {
            PreparedStatement prepared = DAOConnection.getInstance().prepareStatement(
                    "SELECT * FROM students");

            ResultSet result = prepared.executeQuery();

            while (result.next()) {
                students.add(map(result));
            }

        } catch (SQLException e) {
            log.error("Error finding students : " + e);
        }

        return students;
    }

    @Override
    public StudentBean create(StudentBean obj) {
        StudentBean objectToReturn = null;

        try {
            PreparedStatement prepared = DAOConnection.getInstance().prepareStatement(
                    " INSERT INTO students (first_name, last_name, e_mail, student_reg_nr, student_year, student_group, phone) "
                            + " VALUES(?, ?, ?, ?, ?, ?, ?) ", Statement.RETURN_GENERATED_KEYS);

            prepared.setString(1, obj.getFirstName());
            prepared.setString(2, obj.getLastName());
            prepared.setString(3, obj.geteMail());
            prepared.setString(4, obj.getStudentRegNr());
            prepared.setString(5, obj.getStudentYear());
            prepared.setString(6, obj.getStudentGroup());
            prepared.setString(7, obj.getPhone());

            // execute query and get the affected rows number :
            int affectedRows = prepared.executeUpdate();
            if (affectedRows != 0) {
                // get the latest inserted id :
                ResultSet generatedKeys = prepared.getGeneratedKeys();
                if (generatedKeys.next()) {
                    log.debug("Inserted id : " + generatedKeys.getLong(1));
                    objectToReturn = this.find(generatedKeys.getLong(1));
                }
            }

        } catch (SQLException e) {
            log.error("Error creating new student : " + e);
        }

        return objectToReturn;
    }

    @Override
    public StudentBean update(StudentBean obj) {
        StudentBean objectToReturn = null;

        try {
            PreparedStatement prepared = DAOConnection.getInstance().prepareStatement(
                    " UPDATE students "
                            + " SET first_name=?, "
                            + " last_name=?, "
                            + " e_mail=?, "
                            + " student_reg_nr=?, "
                            + " student_year=?, "
                            + " student_group=?, "
                            + " phone=?, "
                            + " WHERE id=? ");

            prepared.setString(1, obj.getFirstName());
            prepared.setString(2, obj.getLastName());
            prepared.setString(3, obj.geteMail());
            prepared.setString(4, obj.getStudentRegNr());
            prepared.setString(5, obj.getStudentYear());
            prepared.setString(6, obj.getStudentGroup());
            prepared.setString(7, obj.getPhone());
            prepared.setLong(8, obj.getId());

            // execute query and get the affected rows number :
            int affectedRows = prepared.executeUpdate();
            if (affectedRows != 0) {
                log.debug("Updated id : " + obj.getId());
                objectToReturn = this.find(obj.getId());
            }

        } catch (SQLException e) {
            log.error("Error updating student : " + e);
        }

        return objectToReturn;
    }

    @Override
    public int delete(long id) {
        int affectedRows = 0;

        try {
            PreparedStatement prepared = DAOConnection.getInstance().prepareStatement(
                    " DELETE FROM students "
                            + " WHERE id=? ");

            prepared.setLong(1, id);

            // execute query and get the affected rows number :
            affectedRows = prepared.executeUpdate();

        } catch (SQLException e) {
            log.error("Error deleting student : " + e);
        }

        return affectedRows;
    }

    @Override
    public boolean checkIfTableExists(Connection connection, String tableName) {
        boolean tExists = false;
        try (ResultSet rs = connection.getMetaData().getTables(null, null, tableName.toUpperCase(), null)) {
            while (rs.next()) {
                String tName = rs.getString("TABLE_NAME");
                if (tName != null && tName.equalsIgnoreCase(tableName)) {
                    tExists = true;
                    break;
                }
            }
        } catch (SQLException e) {
            log.error("Error checking for table " + tableName + " existence", e);
        }
        return tExists;
    }

    /**
     * Map the current row of the given ResultSet to an Object.
     *
     * @param resultSet
     * @return The mapped Object from the current row of the given ResultSet.
     * @throws SQLException If something fails at database level.
     */
    private static StudentBean map(ResultSet resultSet) throws SQLException {
        return new StudentBean(resultSet.getLong("id"), resultSet.getString("first_name"),
                resultSet.getString("last_name"), resultSet.getString("e_mail"),
                resultSet.getString("student_reg_nr"), resultSet.getString("student_year"),
                resultSet.getString("student_group"), resultSet.getString("phone"));
    }
}
