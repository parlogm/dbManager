package ro.utm.dbManager.frames;

import org.apache.log4j.Logger;
import ro.utm.dbManager.config.I18N;
import ro.utm.dbManager.dao.DAOConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

public class CreateTablesFrame extends JInternalFrame {

    final static Logger log = Logger.getLogger(CreateTablesFrame.class);

    JButton jCreateStudentsTableButton = new JButton(I18N.lang("createTableFrame.createStudentsTableButton"));
    JButton jCreateCreditsTableButton = new JButton(I18N.lang("createTableFrame.createCreditsTableButton"));
    JButton jCreateGradesTableButton = new JButton(I18N.lang("createTableFrame.createGradesTableButton"));

    public CreateTablesFrame() {
        log.debug("START constructor...");

        setTitle(I18N.lang("createTableFrame.title"));
        setLocation(new Random().nextInt(120) + 10, new Random().nextInt(120) + 10);
        setSize(550, 350);

        setClosable(true);
        setIconifiable(true);
        setMaximizable(false);
        setResizable(false);
        setDefaultCloseOperation(HIDE_ON_CLOSE);

        //add compnent to the frame :
        getContentPane().setLayout(new FlowLayout(FlowLayout.LEFT));
        getContentPane().add(jCreateStudentsTableButton);
        getContentPane().add(jCreateCreditsTableButton);
        getContentPane().add(jCreateGradesTableButton);

        jCreateStudentsTableButton.addActionListener((ActionEvent ev) ->
        {
            createStudentsTable(ev);
        });

        jCreateCreditsTableButton.addActionListener((ActionEvent ev) ->
        {
            createCreditsTable(ev);
        });

        jCreateGradesTableButton.addActionListener((ActionEvent ev) ->
        {
            createGradesTable(ev);
        });

        setVisible(false);

        log.debug("End of constructor.");
    }

    public void createStudentsTable(ActionEvent ev) {
        log.debug("ActionEvent on " + ev.getActionCommand());
        String createQuery = "CREATE TABLE IF NOT EXISTS students("
                + "id INT PRIMARY KEY AUTO_INCREMENT,"
                + "first_name VARCHAR(255),"
                + "last_name VARCHAR(255),"
                + "e_mail VARCHAR(40),"
                + "student_reg_nr VARCHAR (20),"
                + "student_year VARCHAR (4),"
                + "student_group VARCHAR (4),"
                + "phone VARCHAR (11)"
                + ")";
        executeQuery(createQuery, "students");
    }

    public void createCreditsTable(ActionEvent ev) {
        log.debug("ActionEvent on " + ev.getActionCommand());
        String createQuery = "CREATE TABLE IF NOT EXISTS credits("
                + "id INT PRIMARY KEY AUTO_INCREMENT,"
                + "name_crd VARCHAR(50),"
                + "nr_crd VARCHAR(5),"
                + "code_crd VARCHAR(50)"
                + ")";
        executeQuery(createQuery, "credits");
    }

    public void createGradesTable(ActionEvent ev) {
        log.debug("ActionEvent on " + ev.getActionCommand());
        String createQuery = "CREATE TABLE IF NOT EXISTS grades("
                + "id INT PRIMARY KEY AUTO_INCREMENT,"
                + "name_crd VARCHAR(50),"
                + "code_crd VARCHAR(50),"
                + "id_std INT,"
                + "grade VARCHAR(5)"
                + ")";
        executeQuery(createQuery, "grades");
    }

    private void executeQuery(String query, String tableName) {
        try {
            PreparedStatement ps = DAOConnection.getInstance().prepareStatement(query);
            ps.executeUpdate();

            log.info("Table " + tableName + " created.");
            JOptionPane.showMessageDialog(null, tableName.toUpperCase() + " table created successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            log.error("Error creating table students : " + e);
            JOptionPane.showMessageDialog(null, "Error creating table " + tableName.toUpperCase(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

}
