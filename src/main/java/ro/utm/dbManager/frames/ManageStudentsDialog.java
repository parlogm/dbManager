package ro.utm.dbManager.frames;

import org.apache.log4j.Logger;
import ro.utm.dbManager.beans.StudentBean;
import ro.utm.dbManager.config.I18N;
import ro.utm.dbManager.dao.DAOConnection;
import ro.utm.dbManager.repository.StudentRepository;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Random;

public class ManageStudentsDialog extends JDialog {

    final static Logger log = Logger.getLogger(ManageStudentsDialog.class);

    JLabel jLabelId = new JLabel(I18N.lang("manageStudentDialog.jLabelId"));
    JLabel jLabelFirstName = new JLabel(I18N.lang("manageStudentDialog.jLabelFirstName"));
    JLabel jLabelLastName = new JLabel(I18N.lang("manageStudentDialog.jLabelLastName"));
    JLabel jLabelEmail = new JLabel(I18N.lang("manageStudentDialog.jLabelEmail"));
    JLabel jLabelRegNr = new JLabel(I18N.lang("manageStudentDialog.jLabelRegNr"));
    JLabel jLabelYear = new JLabel(I18N.lang("manageStudentDialog.jLabelYear"));
    JLabel jLabelGroup = new JLabel(I18N.lang("manageStudentDialog.jLabelGroup"));
    JLabel jLabelPhone = new JLabel(I18N.lang("manageStudentDialog.jLabelPhone"));

    JTextField jTextFieldId = new JTextField(40);
    JTextField jTextFieldFirstName = new JTextField(40);
    JTextField jTextFieldLastName = new JTextField(40);
    JTextField jTextFieldEmail = new JTextField(40);
    JTextField jTextFieldRegNr = new JTextField(20);
    JTextField jTextFieldYear = new JTextField(4);
    JTextField jTextFieldGroup = new JTextField(4);
    JTextField jTextFieldPhone = new JTextField(11);

    JButton jButtonSave = new JButton(I18N.lang("manageStudentDialog.jButtonSave"));
    JButton jButtonCancel = new JButton(I18N.lang("manageStudentDialog.jButtonCancel"));

    boolean isNew;
    StudentBean studentToEdit;

    public ManageStudentsDialog(Frame owner, String title, boolean modal, boolean isNew, StudentBean studentToEdit) {
        super(owner, title, modal);

        // set this param globals to use in other methods :
        this.isNew = isNew;
        this.studentToEdit = studentToEdit;

        setLocation(new Random().nextInt(150), new Random().nextInt(150));
        setSize(350, 200);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // new :
        if (isNew) {
            setLayout(new GridLayout(8, 2));
        } // modification :
        else {
            setLayout(new GridLayout(9, 2));
            getContentPane().add(jLabelId);
            getContentPane().add(jTextFieldId);
            jTextFieldId.setEditable(false);

            // write values in form :
            jTextFieldId.setText("" + studentToEdit.getId());
            jTextFieldFirstName.setText(studentToEdit.getFirstName());
            jTextFieldLastName.setText(studentToEdit.getLastName());
            jTextFieldEmail.setText(studentToEdit.geteMail());
            jTextFieldRegNr.setText(studentToEdit.getStudentRegNr());
            jTextFieldYear.setText(studentToEdit.getStudentYear());
            jTextFieldGroup.setText(studentToEdit.getStudentGroup());
            jTextFieldPhone.setText(studentToEdit.getPhone());
        }

        getContentPane().add(jLabelFirstName);
        getContentPane().add(jTextFieldFirstName);

        getContentPane().add(jLabelLastName);
        getContentPane().add(jTextFieldLastName);

        getContentPane().add(jLabelEmail);
        getContentPane().add(jTextFieldEmail);

        getContentPane().add(jLabelRegNr);
        getContentPane().add(jTextFieldRegNr);

        getContentPane().add(jLabelYear);
        getContentPane().add(jTextFieldYear);

        getContentPane().add(jLabelGroup);
        getContentPane().add(jTextFieldGroup);

        getContentPane().add(jLabelPhone);
        getContentPane().add(jTextFieldPhone);

        getContentPane().add(jButtonSave);
        jButtonSave.addActionListener((ActionEvent ev) ->
        {
            jButtonSaveActionPerformed(ev);
        });

        getContentPane().add(jButtonCancel);
        jButtonCancel.addActionListener((ActionEvent ev) ->
        {
            jButtonCancelActionPerformed(ev);
        });

        setVisible(true);
    }

    public void jButtonSaveActionPerformed(ActionEvent ev) {
        log.debug("ActionEvent on " + ev.getActionCommand());

        StudentRepository studentRepository = new StudentRepository();
        if (studentRepository.checkIfTableExists(DAOConnection.getInstance(), "students")) {
            StudentBean studentBean = new StudentBean();

            studentBean.setFirstName(jTextFieldFirstName.getText());
            studentBean.setLastName(jTextFieldLastName.getText());
            studentBean.seteMail(jTextFieldEmail.getText());
            studentBean.setStudentRegNr(jTextFieldRegNr.getText());
            studentBean.setStudentYear(jTextFieldYear.getText());
            studentBean.setStudentGroup(jTextFieldGroup.getText());
            studentBean.setPhone(jTextFieldPhone.getText());

            if (this.isNew) {
                if (studentRepository.create(studentBean) != null) {
                    this.dispose();
                }
            } else {
                studentBean.setId(this.studentToEdit.getId());
                if (studentRepository.update(studentBean) != null) {
                    this.dispose();
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "STUDENTS table does not exist", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void jButtonCancelActionPerformed(ActionEvent ev) {
        log.debug("ActionEvent on " + ev.getActionCommand());

        this.dispose();
    }

}
