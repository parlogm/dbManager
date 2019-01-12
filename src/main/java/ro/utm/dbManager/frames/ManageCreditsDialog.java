package ro.utm.dbManager.frames;

import org.apache.log4j.Logger;
import ro.utm.dbManager.beans.CreditsBean;
import ro.utm.dbManager.config.I18N;
import ro.utm.dbManager.dao.DAOConnection;
import ro.utm.dbManager.repository.CreditsRepository;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Random;

public class ManageCreditsDialog extends JDialog {

    final static Logger log = Logger.getLogger(ManageCreditsDialog.class);

    JLabel jLabelId = new JLabel(I18N.lang("manageCreditsDialog.jLabelId"));
    JLabel jLabelName = new JLabel(I18N.lang("manageCreditsDialog.jLabelName"));
    JLabel jLabelNr = new JLabel(I18N.lang("manageCreditsDialog.jLabelNr"));
    JLabel jLabelCode = new JLabel(I18N.lang("manageCreditsDialog.jLabelCode"));

    JTextField jTextFieldId = new JTextField(50);
    JTextField jTextFieldName = new JTextField(50);
    JTextField jTextFieldNr = new JTextField(5);
    JTextField jTextFieldCode = new JTextField(50);

    JButton jButtonSave = new JButton(I18N.lang("manageCreditsDialog.jButtonSave"));
    JButton jButtonCancel = new JButton(I18N.lang("manageCreditsDialog.jButtonCancel"));

    boolean isNew;
    CreditsBean creditToEdit;

    public ManageCreditsDialog(Frame owner, String title, boolean modal, boolean isNew, CreditsBean creditToEdit) {
        super(owner, title, modal);

        // set this param globals to use in other methods :
        this.isNew = isNew;
        this.creditToEdit = creditToEdit;

        setLocation(new Random().nextInt(150), new Random().nextInt(150));
        setSize(350, 200);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // new :
        if (isNew) {
            setLayout(new GridLayout(4, 2));
        } // modification :
        else {
            setLayout(new GridLayout(5, 2));
            getContentPane().add(jLabelId);
            getContentPane().add(jTextFieldId);
            jTextFieldId.setEditable(false);

            // write values in form :
            jTextFieldId.setText("" + creditToEdit.getId());
            jTextFieldName.setText(creditToEdit.getCrdName());
            jTextFieldNr.setText(creditToEdit.getCrdNr());
            jTextFieldCode.setText(creditToEdit.getCrdCode());
        }

        getContentPane().add(jLabelName);
        getContentPane().add(jTextFieldName);

        getContentPane().add(jLabelNr);
        getContentPane().add(jTextFieldNr);

        getContentPane().add(jLabelCode);
        getContentPane().add(jTextFieldCode);

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

        CreditsRepository creditsRepository = new CreditsRepository();
        if (creditsRepository.checkIfTableExists(DAOConnection.getInstance(), "credits")) {
            CreditsBean creditsBean = new CreditsBean();

            creditsBean.setCrdName(jTextFieldName.getText());
            creditsBean.setCrdNr(jTextFieldNr.getText());
            creditsBean.setCrdCode(jTextFieldCode.getText());

            if (this.isNew) {
                if (creditsRepository.create(creditsBean) != null) {
                    this.dispose();
                }
            } else {
                creditsBean.setId(this.creditToEdit.getId());
                if (creditsRepository.update(creditsBean) != null) {
                    this.dispose();
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "CREDITS table does not exist", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void jButtonCancelActionPerformed(ActionEvent ev) {
        log.debug("ActionEvent on " + ev.getActionCommand());

        this.dispose();
    }

}
