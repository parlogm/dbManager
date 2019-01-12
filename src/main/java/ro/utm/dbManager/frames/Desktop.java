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
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Desktop extends JFrame {

    final static Logger log = Logger.getLogger(Desktop.class);

    JDesktopPane jDesktopPane = new JDesktopPane();
    JLabel jLabelFooterState = new JLabel(I18N.lang("desktop.jLabelFooterState") + System.getProperty("os.name"));

    // internal frames :
    FrameAbout frameAbout = new FrameAbout();
    CreateTablesFrame createTablesFrame = new CreateTablesFrame();
    ManageStudentsFrame manageStudentsFrame = new ManageStudentsFrame();
    ManageCreditsFrame manageCreditsFrame = new ManageCreditsFrame();
    ManageGradesFrame manageGradesFrame = new ManageGradesFrame();

    // menu :
    MenuBar menuBar = new MenuBar();

    /**
     * Constructor.
     */
    public Desktop() {

        // init frame :
        setTitle(I18N.lang("desktop.title"));
        Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(0, 0, screenSize.width, screenSize.height);

        // init desktop :
        getContentPane().add(jDesktopPane, BorderLayout.CENTER);
        getContentPane().add(jLabelFooterState, BorderLayout.SOUTH);

        // add internal frames to desktop :
        jDesktopPane.add(frameAbout);
        jDesktopPane.add(createTablesFrame);
        jDesktopPane.add(manageStudentsFrame);
        jDesktopPane.add(manageCreditsFrame);
        jDesktopPane.add(manageGradesFrame);

        // add the menu bar :
        setJMenuBar(menuBar);

        // menu listeners :
        // jMenuItemQuit :
        menuBar.jMenuItemQuit.addActionListener((ActionEvent ev) ->
        {
            log.debug("ActionEvent on " + ev.getActionCommand());

            if (confirmBeforeExit()) {
                System.exit(0);
            }
        });

        // jMenuItemFrameAbout :
        menuBar.jMenuItemFrameAbout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                log.debug("ActionEvent on " + ev.getActionCommand());

                frameAbout.setVisible(true);
            }
        });

        // jMenuItemCreateTables :
        menuBar.jMenuItemCreateTables.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                log.debug("ActionEvent on " + ev.getActionCommand());

                createTablesFrame.setVisible(true);
            }
        });

        // window closing event :
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent ev) {
                log.debug("WindowEvent on " + ev.paramString());

                if (confirmBeforeExit()) {
                    System.exit(0);
                }
            }
        });

        // jMenuItemManageStudents :
        menuBar.jMenuItemManageStudents.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                log.debug("ActionEvent on " + ev.getActionCommand());

                // refresh the Table Model :
                manageStudentsFrame.jTable1.setModel(manageStudentsFrame.getData());

                manageStudentsFrame.setVisible(true);
            }
        });

        // jMenuItemManageCredits :
        menuBar.jMenuItemManageCredits.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                log.debug("ActionEvent on " + ev.getActionCommand());

                // refresh the Table Model :
                manageCreditsFrame.jTable1.setModel(manageCreditsFrame.getData());

                manageCreditsFrame.setVisible(true);
            }
        });

        // jMenuItemManageGrades :
        menuBar.jMenuItemManageGrades.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                log.debug("ActionEvent on " + ev.getActionCommand());

                // refresh the Table Model :
                manageGradesFrame.jTable1.setModel(manageGradesFrame.getData());

                manageGradesFrame.setVisible(true);
            }
        });

        setVisible(true);

        StudentRepository studentRepository = new StudentRepository();
        CreditsRepository creditsRepository = new CreditsRepository();
        GradesRepository gradesRepository = new GradesRepository();

        StringBuffer sb = new StringBuffer();

        if (studentRepository.checkIfTableExists(DAOConnection.getInstance(), "students") &&
                creditsRepository.checkIfTableExists(DAOConnection.getInstance(), "credits") &&
                gradesRepository.checkIfTableExists(DAOConnection.getInstance(), "grades")) {
            sb.append("Tables STUDENTS, CREDITS & GRADES has been created due to db.createTables parameter set to true!");
        }

        if (!studentRepository.findAll().isEmpty() && !creditsRepository.findAll().isEmpty() &&
                !gradesRepository.findAll().isEmpty()) {
            sb.append("\nTables STUDENTS, CREDITS & GRADES have some dummy data due to db.populateTables parameter set to true!");
        }

        if (!sb.toString().isEmpty()) {
            JOptionPane.showMessageDialog(null, sb.toString(), "Database information", JOptionPane.INFORMATION_MESSAGE);
        }

    }

    /**
     * Show confirm dialog before closing the window.
     *
     * @return boolean true user answer Yes.
     */
    public boolean confirmBeforeExit() {
        log.debug("Display confirm dialog...");

        if (JOptionPane.showConfirmDialog(this, I18N.lang("desktop.confirmbeforeexitdialog.text"),
                I18N.lang("desktop.confirmbeforeexitdialog.title"), JOptionPane.YES_NO_OPTION) == 0) {
            log.debug("User answer YES.");
            return true;
        }

        log.debug("User answer NO.");
        return false;
    }
}
