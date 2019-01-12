package ro.utm.dbManager.frames;

import org.apache.log4j.Logger;
import ro.utm.dbManager.config.I18N;
import ro.utm.dbManager.dao.DAOConnection;
import ro.utm.dbManager.repository.CreditsRepository;
import ro.utm.dbManager.repository.GradesRepository;
import ro.utm.dbManager.repository.StudentRepository;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

public class DropTablesFrame extends JInternalFrame {

    final static Logger log = Logger.getLogger(DropTablesFrame.class);

    JButton jDropStudentsTableButton = new JButton(I18N.lang("dropTableFrame.dropStudentsTableButton"));
    JButton jDropCreditsTableButton = new JButton(I18N.lang("dropTableFrame.dropCreditsTableButton"));
    JButton jDropGradesTableButton = new JButton(I18N.lang("dropTableFrame.dropGradesTableButton"));


    public DropTablesFrame() {
        log.debug("START constructor...");

        setTitle(I18N.lang("dropTableFrame.title"));
        setLocation(new Random().nextInt(120) + 10, new Random().nextInt(120) + 10);
        setSize(550, 350);

        setClosable(true);
        setIconifiable(true);
        setMaximizable(false);
        setResizable(false);
        setDefaultCloseOperation(HIDE_ON_CLOSE);

        //add compnent to the frame :
        getContentPane().setLayout(new FlowLayout(FlowLayout.LEFT));
        getContentPane().add(jDropStudentsTableButton);
        getContentPane().add(jDropCreditsTableButton);
        getContentPane().add(jDropGradesTableButton);


        jDropStudentsTableButton.addActionListener((ActionEvent ev) ->
        {
            dropStudentsTable(ev);
        });

        jDropCreditsTableButton.addActionListener((ActionEvent ev) ->
        {
            dropCreditsTable(ev);
        });

        jDropGradesTableButton.addActionListener((ActionEvent ev) ->
        {
            dropGradesTable(ev);
        });

        setVisible(false);

        log.debug("End of constructor.");
    }

    public void dropStudentsTable(ActionEvent ev) {
        log.debug("ActionEvent on " + ev.getActionCommand());
        StudentRepository studentRepository = new StudentRepository();
        if (!studentRepository.checkIfTableExists(DAOConnection.getInstance(), "students")) {
            JOptionPane.showMessageDialog(null, "Students table does not exist  ", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            String createQuery = "DROP TABLE students";
            executeQuery(createQuery, "students");
        }
    }

    public void dropCreditsTable(ActionEvent ev) {
        log.debug("ActionEvent on " + ev.getActionCommand());
        CreditsRepository creditsRepository = new CreditsRepository();
        if (!creditsRepository.checkIfTableExists(DAOConnection.getInstance(), "credits")) {
            JOptionPane.showMessageDialog(null, "Credits table does not exist  ", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            String createQuery = "DROP TABLE credits";
            executeQuery(createQuery, "credits");
        }
    }

    public void dropGradesTable(ActionEvent ev) {
        log.debug("ActionEvent on " + ev.getActionCommand());
        GradesRepository gradesRepository = new GradesRepository();
        if (!gradesRepository.checkIfTableExists(DAOConnection.getInstance(), "grades")) {
            JOptionPane.showMessageDialog(null, "Grades table does not exist  ", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            String createQuery = "DROP TABLE grades";
            executeQuery(createQuery, "grades");
        }
    }

    private void executeQuery(String query, String tableName) {
        try {
            PreparedStatement ps = DAOConnection.getInstance().prepareStatement(query);
            ps.executeUpdate();

            log.info("Table " + tableName + " dropped.");
            JOptionPane.showMessageDialog(null, tableName.toUpperCase() + " table dropped successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            log.error("Error dropping table : " + tableName + " : " + e);
            JOptionPane.showMessageDialog(null, "Error dropping table " + tableName.toUpperCase(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

}
