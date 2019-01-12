package ro.utm.dbManager.dao;

import org.apache.log4j.Logger;
import org.h2.tools.Server;
import ro.utm.dbManager.config.PropertyHolder;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DAOInit {

    final static Logger log = Logger.getLogger(DAOInit.class);

    /**
     * Initialize the database.
     */
    public static void init() {
        if ("true".equalsIgnoreCase(PropertyHolder.getProperty("db.createTables"))) {
            DAOInit.createTables();
        }

        if ("true".equalsIgnoreCase(PropertyHolder.getProperty("db.startH2Console"))) {
            DAOInit.startH2WebConsole();
        }
    }

    /**
     * Create db console server to access it in web browser.
     */
    private static void startH2WebConsole() {
        log.info("Trying to start H2 Console Web Server...");

        int defaultPort = 2020;
        int port;

        try {
            port = Integer.parseInt(PropertyHolder.getProperty("db.h2ConsolePort"));
        } catch (NumberFormatException e) {
            log.error("Error parsing port number in properties file : " + e);
            log.info("Default port " + defaultPort + " used.");
            port = defaultPort;
        }

        try {
            Server h2Server = Server.createWebServer("-webPort", "" + port).start();
            log.info("H2 Console Web Server started and connection is open.");
            log.info("URL: " + h2Server.getURL());
        } catch (SQLException e) {
            log.error("Error starting H2 Console Web Server : " + e);
        }
    }

    /**
     * Create tables in the database.
     */
    private static void createTables() {
        createStudentsTable();
        createCreditsTable();
        createGradesTable();
    }

    private static void createStudentsTable() {
        String createQuery = "CREATE TABLE IF NOT EXISTS students("
                + "id INT PRIMARY KEY AUTO_INCREMENT,"
                + "first_name VARCHAR(255),"
                + "last_name VARCHAR(255),"
                + "e_mail VARCHAR(255),"
                + "student_reg_nr VARCHAR (20),"
                + "student_year VARCHAR (4),"
                + "student_group VARCHAR (4),"
                + "phone VARCHAR (11)"
                + ")";
        executeQuery(createQuery, "students");
    }

    private static void createCreditsTable() {
        String createQuery = "CREATE TABLE IF NOT EXISTS credits("
                + "id INT PRIMARY KEY AUTO_INCREMENT,"
                + "name_crd VARCHAR(50),"
                + "nr_crd VARCHAR(5),"
                + "code_crd VARCHAR(50)"
                + ")";
        executeQuery(createQuery, "credits");
    }

    private static void createGradesTable() {
        String createQuery = "CREATE TABLE IF NOT EXISTS grades("
                + "id INT PRIMARY KEY AUTO_INCREMENT,"
                + "name_crd VARCHAR(50),"
                + "code_crd VARCHAR(50),"
                + "id_std INT,"
                + "grade VARCHAR(5)"
                + ")";
        executeQuery(createQuery, "grades");
    }

    private static void executeQuery (String query, String tableName) {
        try {
            PreparedStatement ps = DAOConnection.getInstance().prepareStatement(query);
            ps.executeUpdate();

            log.info("Table " + tableName + " created.");
        } catch (SQLException e) {
            log.error("Error creating table students : " + e);
            e.printStackTrace();
        }
    }

}
