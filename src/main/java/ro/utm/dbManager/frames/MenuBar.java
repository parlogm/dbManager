package ro.utm.dbManager.frames;

import org.apache.log4j.Logger;
import ro.utm.dbManager.config.I18N;
import ro.utm.dbManager.dao.DAOConnection;
import ro.utm.dbManager.repository.StudentRepository;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class MenuBar extends JMenuBar
{
    final static Logger log = Logger.getLogger(MenuBar.class);

    // Database actions :
    JMenu jMenuDatabaseActions = new JMenu(I18N.lang("menubar.jMenuDatabaseActions"));
    JMenuItem jMenuItemCreateTables = new JMenuItem(I18N.lang("menubar.jMenuItemCreateTables"));
    JMenuItem jMenuItemManageStudents = new JMenuItem(I18N.lang("menubar.jMenuItemStudents"));
    JMenuItem jMenuItemManageCredits = new JMenuItem(I18N.lang("menubar.jMenuItemCredits"));
    JMenuItem jMenuItemManageGrades = new JMenuItem(I18N.lang("menubar.jMenuItemGrades"));
    JMenuItem jMenuItemQuit = new JMenuItem(I18N.lang("menubar.jMenuItemQuit"));

    // help :
    JMenu jMenuHelp = new JMenu(I18N.lang("menubar.jMenuHelp"));
    JMenuItem jMenuItemFrameAbout = new JMenuItem(I18N.lang("menubar.jMenuItemFrameAbout"));

    /**
     * Constructor.
     */
    public MenuBar()
    {
        log.debug("START constructor...");

        StudentRepository studentRepository = new StudentRepository();

        // file :
        add(jMenuDatabaseActions);
        jMenuDatabaseActions.setMnemonic(KeyEvent.VK_F);

        jMenuItemCreateTables.setAccelerator(KeyStroke.getKeyStroke('C', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        jMenuDatabaseActions.add(jMenuItemCreateTables);
        jMenuDatabaseActions.addSeparator();

        /*if (studentRepository.checkIfTableExists(DAOConnection.getInstance(), "students")) {
            jMenuDatabaseActions.add(jMenuItemManageStudents);
            jMenuDatabaseActions.addSeparator();
        }*/

        jMenuItemManageStudents.setAccelerator(KeyStroke.getKeyStroke('S', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        jMenuDatabaseActions.add(jMenuItemManageStudents);
        jMenuDatabaseActions.addSeparator();

        jMenuItemQuit.setAccelerator(KeyStroke.getKeyStroke('Q', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        jMenuDatabaseActions.add(jMenuItemQuit);

        // help :
        add(jMenuHelp);
        jMenuHelp.setMnemonic(KeyEvent.VK_H);

        jMenuItemFrameAbout.setAccelerator(KeyStroke.getKeyStroke('A', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        jMenuHelp.add(jMenuItemFrameAbout);

        log.debug("End of constructor.");
    }
}