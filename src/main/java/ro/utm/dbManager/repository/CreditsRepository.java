package ro.utm.dbManager.repository;

import org.apache.log4j.Logger;
import ro.utm.dbManager.beans.CreditsBean;
import ro.utm.dbManager.dao.DAOConnection;

import java.sql.*;
import java.util.ArrayList;

public class CreditsRepository implements Repository<CreditsBean> {

    final static Logger log = Logger.getLogger(CreditsRepository.class);

    @Override
    public CreditsBean find(long id) {
        CreditsBean obj = null;

        try {
            PreparedStatement prepared = DAOConnection.getInstance().prepareStatement(
                    "SELECT * FROM credits WHERE id=?");

            prepared.setLong(1, id);

            ResultSet result = prepared.executeQuery();

            if (result.first()) {
                obj = map(result);
            }

        } catch (SQLException e) {
            log.error("Error finding credit : " + e);
        }

        return obj;
    }

    @Override
    public ArrayList<CreditsBean> findAll() {
        ArrayList<CreditsBean> credits = new ArrayList<>();

        try {
            PreparedStatement prepared = DAOConnection.getInstance().prepareStatement(
                    "SELECT * FROM credits");

            ResultSet result = prepared.executeQuery();

            while (result.next()) {
                credits.add(map(result));
            }

        } catch (SQLException e) {
            log.error("Error finding credits : " + e);
        }

        return credits;
    }

    @Override
    public CreditsBean create(CreditsBean obj) {
        CreditsBean objectToReturn = null;

        try {
            PreparedStatement prepared = DAOConnection.getInstance().prepareStatement(
                    " INSERT INTO credits (name_crd, nr_crd, code_crd) "
                            + " VALUES(?, ?, ?) ", Statement.RETURN_GENERATED_KEYS);

            prepared.setString(1, obj.getCrdName());
            prepared.setString(2, obj.getCrdNr());
            prepared.setString(3, obj.getCrdCode());

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
            log.error("Error creating new credit : " + e);
        }

        return objectToReturn;
    }

    @Override
    public CreditsBean update(CreditsBean obj) {
        CreditsBean objectToReturn = null;

        try {
            PreparedStatement prepared = DAOConnection.getInstance().prepareStatement(
                    " UPDATE credits "
                            + " SET name_crd=?, "
                            + " nr_crd=?, "
                            + " code_crd=? "
                            + " WHERE id=? ");

            prepared.setString(1, obj.getCrdName());
            prepared.setString(2, obj.getCrdNr());
            prepared.setString(3, obj.getCrdCode());
            prepared.setLong(4, obj.getId());

            // execute query and get the affected rows number :
            int affectedRows = prepared.executeUpdate();
            if (affectedRows != 0) {
                log.debug("Updated id : " + obj.getId());
                objectToReturn = this.find(obj.getId());
            }

        } catch (SQLException e) {
            log.error("Error updating credit : " + e);
        }

        return objectToReturn;
    }

    @Override
    public int delete(long id) {
        int affectedRows = 0;

        try {
            PreparedStatement prepared = DAOConnection.getInstance().prepareStatement(
                    " DELETE FROM credits "
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
    private static CreditsBean map(ResultSet resultSet) throws SQLException {
        return new CreditsBean(resultSet.getLong("id"), resultSet.getString("name_crd"),
                resultSet.getString("nr_crd"), resultSet.getString("code_crd"));
    }
}
