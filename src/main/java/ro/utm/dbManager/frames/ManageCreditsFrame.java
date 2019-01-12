package ro.utm.dbManager.frames;

import org.apache.log4j.Logger;
import ro.utm.dbManager.beans.CreditsBean;
import ro.utm.dbManager.config.I18N;
import ro.utm.dbManager.repository.CreditsRepository;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Random;

public class ManageCreditsFrame extends JInternalFrame {

    final static Logger log = Logger.getLogger(ManageCreditsFrame.class);

    JPanel jPanelHeader = new JPanel();
    JButton jButtonDelete = new JButton(I18N.lang("manageCreditsFrame.jButtonDelete"));
    JButton jButtonAdd = new JButton(I18N.lang("manageCreditsFrame.jButtonAdd"));
    JButton jButtonEdit = new JButton(I18N.lang("manageCreditsFrame.jButtonEdit"));
    public JTable jTable1;

    public ManageCreditsFrame() {
        setTitle(I18N.lang("manageCreditsFrame.title"));
        setLocation(new Random().nextInt(100), new Random().nextInt(100));
        setSize(550, 350);
        setVisible(false);
        setClosable(true);
        setIconifiable(true);
        //setMaximizable(false);
        //setResizable(false);
        setDefaultCloseOperation(HIDE_ON_CLOSE);

        // header :
        jPanelHeader.setBorder(BorderFactory.createTitledBorder(I18N.lang("manageCreditsFrame.jPanelHeader")));

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
                        I18N.lang("manageCreditsFrame.jTable1.column.id"),
                        I18N.lang("manageCreditsFrame.jTable1.column.name"),
                        I18N.lang("manageCreditsFrame.jTable1.column.nr"),
                        I18N.lang("manageCreditsFrame.jTable1.column.code")
                };

        DefaultTableModel model = new DefaultTableModel(columns, 0);

        // get data rows :
        CreditsRepository creditsRepository = new CreditsRepository();
        ArrayList<CreditsBean> credits = creditsRepository.findAll();

        // transform ArrayList<> to Object[][] :
        for (CreditsBean credit : credits) {
            model.addRow(new Object[]
                    {
                            credit.getId(),
                            credit.getCrdName(),
                            credit.getCrdNr(),
                            credit.getCrdCode()
                    });
        }

        return model;
    }

    public void jButtonDeleteActionPerformed(ActionEvent ev) {
        log.debug("ActionEvent on " + ev.getActionCommand());

        log.debug("selectedRowCount : " + jTable1.getSelectedRowCount());

        if (jTable1.getSelectedRowCount() > 0) {
            CreditsRepository creditsRepository = new CreditsRepository();
            int[] selectedRows = jTable1.getSelectedRows();
            for (int index = 0; index < selectedRows.length; index++) {
                log.debug("Delete row with id=" + jTable1.getValueAt(selectedRows[index], 0));
                creditsRepository.delete((long) jTable1.getValueAt(selectedRows[index], 0));
            }

            // refresh the Table Model :
            jTable1.setModel(this.getData());
        }
    }

    public void jButtonAddActionPerformed(ActionEvent ev) {
        log.debug("ActionEvent on " + ev.getActionCommand());

        new ManageCreditsDialog(null, I18N.lang("manageCreditsDialog.addTitle"), true, true, null);

        // refresh the Table Model :
        jTable1.setModel(this.getData());
    }

    public void jButtonEditActionPerformed(ActionEvent ev) {
        log.debug("ActionEvent on " + ev.getActionCommand());

        if (jTable1.getSelectedRowCount() > 0) {
            long credit_id = (long) jTable1.getValueAt(jTable1.getSelectedRow(), 0);
            log.debug("Trying to edit credit with id : " + credit_id);

            CreditsRepository creditsRepository = new CreditsRepository();

            new ManageCreditsDialog(null, I18N.lang("manageCreditsDialog.editTitle"), true, false, creditsRepository.find(credit_id));

            // refresh the Table Model :
            jTable1.setModel(this.getData());
        }
    }

}

