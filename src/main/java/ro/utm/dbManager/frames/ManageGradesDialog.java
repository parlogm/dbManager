package ro.utm.dbManager.frames;

import org.apache.log4j.Logger;
import ro.utm.dbManager.beans.GradesBean;
import ro.utm.dbManager.config.I18N;
import ro.utm.dbManager.dao.DAOConnection;
import ro.utm.dbManager.repository.GradesRepository;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Random;

public class ManageGradesDialog extends JDialog {

    final static Logger log = Logger.getLogger(ManageGradesDialog.class);

    JLabel jLabelId = new JLabel(I18N.lang("manageGradesDialog.jLabelId"));
    JLabel jLabelName = new JLabel(I18N.lang("manageGradesDialog.jLabelName"));
    JLabel jLabelCode = new JLabel(I18N.lang("manageGradesDialog.jLabelCode"));
    JLabel jLabelStdId = new JLabel(I18N.lang("manageGradesDialog.jLabelStdId"));
    JLabel jLabelGrade = new JLabel(I18N.lang("manageGradesDialog.jLabelGrade"));

    JTextField jTextFieldId = new JTextField(50);
    JTextField jTextFieldName = new JTextField(50);
    JTextField jTextFieldCode = new JTextField(50);
    JTextField jTextFieldStdId = new JTextField(20);
    JTextField jTextFieldGrade = new JTextField(5);

    JButton jButtonSave = new JButton(I18N.lang("manageGradesDialog.jButtonSave"));
    JButton jButtonCancel = new JButton(I18N.lang("manageGradesDialog.jButtonCancel"));

    boolean isNew;
    GradesBean gradeToEdit;

    public ManageGradesDialog(Frame owner, String title, boolean modal, boolean isNew, GradesBean gradeToEdit) {
        super(owner, title, modal);

        // set this param globals to use in other methods :
        this.isNew = isNew;
        this.gradeToEdit = gradeToEdit;

        setLocation(new Random().nextInt(150), new Random().nextInt(150));
        setSize(350, 200);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // new :
        if (isNew) {
            setLayout(new GridLayout(5, 2));
        } // modification :
        else {
            setLayout(new GridLayout(6, 2));
            getContentPane().add(jLabelId);
            getContentPane().add(jTextFieldId);
            jTextFieldId.setEditable(false);

            // write values in form :
            jTextFieldId.setText("" + gradeToEdit.getId());
            jTextFieldName.setText(gradeToEdit.getCrdName());
            jTextFieldCode.setText(gradeToEdit.getCrdCode());
            jTextFieldStdId.setText(String.valueOf(gradeToEdit.getStdId()));
            jTextFieldGrade.setText(gradeToEdit.getGrade());
        }

        getContentPane().add(jLabelName);
        getContentPane().add(jTextFieldName);

        getContentPane().add(jLabelCode);
        getContentPane().add(jTextFieldCode);

        getContentPane().add(jLabelStdId);
        getContentPane().add(jTextFieldStdId);

        getContentPane().add(jLabelGrade);
        getContentPane().add(jTextFieldGrade);

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

        GradesRepository gradesRepository = new GradesRepository();
        if (gradesRepository.checkIfTableExists(DAOConnection.getInstance(), "grades")) {
            GradesBean gradesBean = new GradesBean();

            gradesBean.setCrdName(jTextFieldName.getText());
            gradesBean.setCrdCode(jTextFieldCode.getText());
            gradesBean.setStdId(Long.valueOf(jTextFieldStdId.getText()));
            gradesBean.setGrade(jTextFieldGrade.getText());

            if (this.isNew) {
                if (gradesRepository.create(gradesBean) != null) {
                    this.dispose();
                }
            } else {
                gradesBean.setId(this.gradeToEdit.getId());
                if (gradesRepository.update(gradesBean) != null) {
                    this.dispose();
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "GRADES table does not exist", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void jButtonCancelActionPerformed(ActionEvent ev) {
        log.debug("ActionEvent on " + ev.getActionCommand());

        this.dispose();
    }

}