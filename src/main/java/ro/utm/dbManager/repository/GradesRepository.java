package ro.utm.dbManager.repository;

import org.apache.log4j.Logger;
import ro.utm.dbManager.beans.GradesBean;
import ro.utm.dbManager.dao.DAOConnection;

import java.sql.*;
import java.util.ArrayList;

public class GradesRepository implements Repository<GradesBean> {

    final static Logger log = Logger.getLogger(GradesRepository.class);

    @Override
    public GradesBean find(long id) {
        GradesBean obj = null;

        try {
            PreparedStatement prepared = DAOConnection.getInstance().prepareStatement(
                    "SELECT * FROM grades WHERE id=?");

            prepared.setLong(1, id);

            ResultSet result = prepared.executeQuery();

            if (result.first()) {
                obj = map(result);
            }

        } catch (SQLException e) {
            log.error("Error finding grade : " + e);
        }

        return obj;
    }

    @Override
    public ArrayList<GradesBean> findAll() {
        ArrayList<GradesBean> grades = new ArrayList<>();

        try {
            PreparedStatement prepared = DAOConnection.getInstance().prepareStatement(
                    "SELECT * FROM grades");

            ResultSet result = prepared.executeQuery();

            while (result.next()) {
                grades.add(map(result));
            }

        } catch (SQLException e) {
            log.error("Error finding grades : " + e);
        }

        return grades;
    }

    @Override
    public GradesBean create(GradesBean obj) {
        GradesBean objectToReturn = null;

        try {
            PreparedStatement prepared = DAOConnection.getInstance().prepareStatement(
                    " INSERT INTO grades (name_crd, code_crd, id_std, grade) "
                            + " VALUES(?, ?, ?, ?) ", Statement.RETURN_GENERATED_KEYS);

            prepared.setString(1, obj.getCrdName());
            prepared.setString(2, obj.getCrdCode());
            prepared.setLong(3, obj.getStdId());
            prepared.setString(4, obj.getGrade());

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
            log.error("Error creating new grade : " + e);
        }

        return objectToReturn;
    }

    @Override
    public GradesBean update(GradesBean obj) {
        GradesBean objectToReturn = null;

        try {
            PreparedStatement prepared = DAOConnection.getInstance().prepareStatement(
                    " UPDATE grades "
                            + " SET name_crd=?, "
                            + " code_crd=?, "
                            + " id_std=?, "
                            + " grade=?"
                            + " WHERE id=? ");

            prepared.setString(1, obj.getCrdName());
            prepared.setString(2, obj.getCrdCode());
            prepared.setLong(3, obj.getStdId());
            prepared.setString(4, obj.getGrade());
            prepared.setLong(5, obj.getId());

            // execute query and get the affected rows number :
            int affectedRows = prepared.executeUpdate();
            if (affectedRows != 0) {
                log.debug("Updated id : " + obj.getId());
                objectToReturn = this.find(obj.getId());
            }

        } catch (SQLException e) {
            log.error("Error updating grade : " + e);
        }

        return objectToReturn;
    }

    @Override
    public int delete(long id) {
        int affectedRows = 0;

        try {
            PreparedStatement prepared = DAOConnection.getInstance().prepareStatement(
                    " DELETE FROM grades "
                            + " WHERE id=? ");

            prepared.setLong(1, id);

            // execute query and get the affected rows number :
            affectedRows = prepared.executeUpdate();

        } catch (SQLException e) {
            log.error("Error deleting credit : " + e);
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
    private static GradesBean map(ResultSet resultSet) throws SQLException {
        return new GradesBean(resultSet.getLong("id"), resultSet.getString("name_crd"),
                resultSet.getString("code_crd"), Long.valueOf(resultSet.getString("id_std")), resultSet.getString("grade"));
    }
}
