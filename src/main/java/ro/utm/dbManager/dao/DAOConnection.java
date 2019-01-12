package ro.utm.dbManager.dao;

import org.apache.log4j.Logger;
import ro.utm.dbManager.config.PropertyHolder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DAOConnection {

    final static Logger log = Logger.getLogger(DAOConnection.class);

    private static String driver = PropertyHolder.getProperty("db.driver");
    private static String url = PropertyHolder.getProperty("db.url");
    private static String user = PropertyHolder.getProperty("db.user");
    private static String password = PropertyHolder.getProperty("db.password");
    private static Connection connection;

    /**
     * Private constructor so this class cannot be instantiated only by it self.
     */
    private DAOConnection() {
    }

    /**
     * Create and return the Connection if not exist.
     *
     * @return The connection object
     */
    public static Connection getInstance() {
        if (connection == null) {
            try {
                Class.forName(driver);
            } catch (ClassNotFoundException e) {
                log.error("DB Driver error : " + e);
            }

            try {
                connection = DriverManager.getConnection(url, user, password);
            } catch (SQLException e) {
                log.error("DB Connection error : " + e);
            }
        }
        return connection;
    }

}
