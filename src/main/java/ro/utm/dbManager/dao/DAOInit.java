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

        if ("true".equalsIgnoreCase(PropertyHolder.getProperty("db.populateTables"))) {
            DAOInit.populateTables();
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

    private static void populateTables() {
        populateStudentsTable();
        populateCreditsTable();
        populateGradesTable();
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

    public static void populateStudentsTable() {
        String query = "INSERT INTO students VALUES" +
                "  (1, 'Mihai', 'Parlog', 'mihai.parlog@student.utm.ro', '19283', '2019', '333A', '0723817212')," +
                "  (2, 'Ionut', 'Baldochin', 'ionut.baldochin@student.utm.ro', '15313', '2019', '333B', '0727847421')," +
                "  (3, 'Maria', 'Ion', 'maria.ion@student.utm.ro', '18123', '2018', '323C', '0715217826')," +
                "  (4, 'Ioana', 'Paslaru', 'ioana.paslaru@student.utm.ro', '65348', '2019', '313D', '0765885421')";
        executeQuery(query, "students");
    }

    public static void populateCreditsTable() {
        String query = "INSERT INTO credits VALUES" +
                "  (1, 'Analiza numerica I', '5', 'AN1')," +
                "  (2, 'Analiza numerica II', '6', 'AN2')," +
                "  (3, 'Fizica', '4', 'FZ')," +
                "  (4, 'Tehnologii WEB', '6', 'TW')," +
                "  (5, 'Informatica aplicata in industrie', '5', 'IAI')," +
                "  (6, 'Programare orientata pe obiecte', '6', 'POO')";
        executeQuery(query, "credits");
    }

    public static void populateGradesTable() {
        String query = "INSERT INTO grades VALUES" +
                "  (1, 'Analiza numerica I', 'AN1', 1, '6')," +
                "  (2, 'Analiza numerica II', 'AN2', 1, '8')," +
                "  (3, 'Fizica', 'FZ', 2, '9')," +
                "  (4, 'Tehnologii WEB', 'TW', 2, '5')," +
                "  (5, 'Informatica aplicata in industrie', 'IAI', 2, '9')," +
                "  (6, 'Programare orientata pe obiecte', 'POO', 3, '6')";
        executeQuery(query, "grades");
    }

    private static void executeQuery (String query, String tableName) {
        try {
            PreparedStatement ps = DAOConnection.getInstance().prepareStatement(query);
            ps.executeUpdate();
        } catch (SQLException e) {
            log.error("Error executing query : " + e);
            e.printStackTrace();
        }
    }

}
