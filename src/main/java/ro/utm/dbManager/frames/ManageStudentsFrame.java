package ro.utm.dbManager.frames;

import org.apache.log4j.Logger;
import ro.utm.dbManager.beans.StudentBean;
import ro.utm.dbManager.config.I18N;
import ro.utm.dbManager.repository.StudentRepository;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Random;

public class ManageStudentsFrame extends JInternalFrame {

    final static Logger log = Logger.getLogger(ManageStudentsFrame.class);

    JPanel jPanelHeader = new JPanel();
    JLabel jLabel1 = new JLabel(I18N.lang("manageStudentsFrame.jLabel1"));
    JButton jButtonDelete = new JButton(I18N.lang("manageStudentsFrame.jButtonDelete"));
    JButton jButtonAdd = new JButton(I18N.lang("manageStudentsFrame.jButtonAdd"));
    JButton jButtonEdit = new JButton(I18N.lang("manageStudentsFrame.jButtonEdit"));
    public JTable jTable1;

    public ManageStudentsFrame() {
        setTitle(I18N.lang("manageStudentsFrame.title"));
        setLocation(new Random().nextInt(100), new Random().nextInt(100));
        setSize(550, 350);
        setVisible(false);
        setClosable(true);
        setIconifiable(true);
        //setMaximizable(false);
        //setResizable(false);
        setDefaultCloseOperation(HIDE_ON_CLOSE);

        // header :
        jPanelHeader.setBorder(BorderFactory.createTitledBorder(I18N.lang("manageStudentsFrame.jPanelHeader")));

        jPanelHeader.add(jButtonDelete);
        jButtonDelete.addActionListener((ActionEvent ev) ->
        {
            jButtonDeleteActionPerformed(ev);
        });

        jPanelHeader.add(jButtonAdd);
        jButtonAdd.addActionListener((ActionEvent ev) ->
        {
            jButtonAddActionPerformed(ev);
        });

        jPanelHeader.add(jButtonEdit);
        jButtonEdit.addActionListener((ActionEvent ev) ->
        {
            jButtonEditActionPerformed(ev);
        });

        getContentPane().add(jPanelHeader, BorderLayout.NORTH);

        // Table :
        jTable1 = new JTable(this.getData());
        jTable1.setAutoCreateRowSorter(true);
        jTable1.setDefaultEditor(Object.class, null);

        getContentPane().add(new JScrollPane(jTable1), BorderLayout.CENTER);
    }

    /**
     * Method to get the data from Repository and return it in DefaultTableModel
     * object. Very useful for refreshing JTable after data modification.
     *
     * @return
     */
    public DefaultTableModel getData() {
        // Comumns :
        String[] columns = new String[]
                {
                        I18N.lang("manageStudentsFrame.jTable1.column.id"),
                        I18N.lang("manageStudentsFrame.jTable1.column.firstName"),
                        I18N.lang("manageStudentsFrame.jTable1.column.lastName"),
                        I18N.lang("manageStudentsFrame.jTable1.column.eMail"),
                        I18N.lang("manageStudentsFrame.jTable1.column.regNr"),
                        I18N.lang("manageStudentsFrame.jTable1.column.group"),
                        I18N.lang("manageStudentsFrame.jTable1.column.year"),
                        I18N.lang("manageStudentsFrame.jTable1.column.phone")
                };

        DefaultTableModel model = new DefaultTableModel(columns, 0);

        // get data rows :
        StudentRepository studentRepository = new StudentRepository();
        ArrayList<StudentBean> students = studentRepository.findAll();

        // transform ArrayList<> to Object[][] :
        for (StudentBean student : students) {
            model.addRow(new Object[]
                    {
                            student.getId(),
                            student.getFirstName(),
                            student.getLastName(),
                            student.geteMail(),
                            student.getStudentRegNr(),
                            student.getStudentGroup(),
                            student.getStudentYear(),
                            student.getPhone()
                    });
        }

        return model;
    }

    public void jButtonDeleteActionPerformed(ActionEvent ev) {
        log.debug("ActionEvent on " + ev.getActionCommand());

        log.debug("selectedRowCount : " + jTable1.getSelectedRowCount());

        if (jTable1.getSelectedRowCount() > 0) {
            StudentRepository studentRepository = new StudentRepository();
            int[] selectedRows = jTable1.getSelectedRows();
            for (int index = 0; index < selectedRows.length; index++) {
                log.debug("Delete row with id=" + jTable1.getValueAt(selectedRows[index], 0));
                studentRepository.delete((long) jTable1.getValueAt(selectedRows[index], 0));
            }

            // refresh the Table Model :
            jTable1.setModel(this.getData());
        }
    }

    public void jButtonAddActionPerformed(ActionEvent ev) {
        log.debug("ActionEvent on " + ev.getActionCommand());

        new ManageStudentsDialog(null, I18N.lang("manageStudentDialog.addTitle"), true, true, null);

        // refresh the Table Model :
        jTable1.setModel(this.getData());
    }

    public void jButtonEditActionPerformed(ActionEvent ev) {
        log.debug("ActionEvent on " + ev.getActionCommand());

        if (jTable1.getSelectedRowCount() > 0) {
            long student_id = (long) jTable1.getValueAt(jTable1.getSelectedRow(), 0);
            log.debug("Trying to edit student with id : " + student_id);

            StudentRepository studentRepository = new StudentRepository();

            new ManageStudentsDialog(null, I18N.lang("manageStudentDialog.editTitle"), true, false, studentRepository.find(student_id));

            // refresh the Table Model :
            jTable1.setModel(this.getData());
        }
    }

}
