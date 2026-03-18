/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package MyGui;

import MyLib.*;
/**
 *
 * @author Gabriel Sioson
 */
public class ReserveLotFrame extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(ReserveLotFrame.class.getName());

    /**
     * Creates new form ReserveLotFrame
     */
    public ReserveLotFrame() {
        setTitle("Reserve a Lot");
        initComponents();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        loadBlocks();
        loadAgents();
        loadLots();
    }

    private void loadBlocks() {
        cmbBlock.removeAllItems();
        for (Block b : Agency.getInstance().getMainProperty().getBlocks()) {
            cmbBlock.addItem("Block " + b.getBlockNumber());
        }
    }

    private void loadLots() {
        cmbLot.removeActionListener(this::cmbLotActionPerformed);
        cmbLot.removeAllItems();

        if (cmbBlock.getSelectedItem() == null) {
            cmbLot.addActionListener(this::cmbLotActionPerformed);
            return;
        }

        int blockNum = Integer.parseInt(((String) cmbBlock.getSelectedItem()).split(" ")[1]);
        Block b = Agency.getInstance().getMainProperty().getBlock(blockNum);

        for (Lot l : b.getLots()) {
            if (l.getStatus().equals(Lot.AVAILABLE)) {
                cmbLot.addItem("Lot " + l.getLotNumber()
                        + " | " + l.getModelName()
                        + " | PHP " + String.format("%,.2f", l.getTotalContractPrice())
                        + " [AVAILABLE]");
            } else if (l.getStatus().equals(Lot.RESERVED)) {
                cmbLot.addItem("Lot " + l.getLotNumber()
                        + " | " + l.getModelName()
                        + " | PHP " + String.format("%,.2f", l.getTotalContractPrice())
                        + " [RESERVED by: " + l.getOwner().getName() + "]");
            }
        }

        if (cmbLot.getItemCount() == 0) {
            cmbLot.addItem("No available lots");
        }

        cmbLot.addActionListener(this::cmbLotActionPerformed);
        updateButtons();
    }

    private void updateButtons() {
        if (cmbLot.getSelectedItem() == null
                || cmbLot.getSelectedItem().toString().startsWith("No")) {
            reserve.setEnabled(false);
            forfeit.setEnabled(false);
            return;
        }

        String lotText = (String) cmbLot.getSelectedItem();
        boolean isReserved = lotText.contains("[RESERVED");
        boolean isAvailable = lotText.contains("[AVAILABLE]");

        reserve.setEnabled(isAvailable);
        forfeit.setEnabled(isReserved);
    }

    private void loadAgents() {
        cmbAgent.removeAllItems();
        for (Agent a : Agency.getInstance().getAgents()) {
            cmbAgent.addItem(a.getName());
        }
    }

    private void processReservation() {
        String clientName = txtName.getText().trim();
        String clientContact = txtContact.getText().trim();

        if (clientName.isEmpty() || clientContact.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this, "Please fill in all fields.");
            return;
        }

        if (cmbLot.getSelectedItem() == null || cmbLot.getSelectedItem().toString().startsWith("No")) {
            javax.swing.JOptionPane.showMessageDialog(this, "No available lots in this block.");
            return;
        }

        int blockNum = Integer.parseInt(((String) cmbBlock.getSelectedItem()).split(" ")[1]);
        Block b = Agency.getInstance().getMainProperty().getBlock(blockNum);
        String lotText = (String) cmbLot.getSelectedItem();
        int lotNum = Integer.parseInt(lotText.split(" ")[1]);
        Lot selectedLot = b.getLot(lotNum);

        Agency agency = Agency.getInstance();
        Client client = agency.findClientByName(clientName);

        if (client == null) {
            client = new Client(clientName, clientContact);
            agency.addClient(client);
        }

        Agent selectedAgent = agency.getAgents().get(cmbAgent.getSelectedIndex());
        selectedAgent.processReservation(client, selectedLot);

        javax.swing.JOptionPane.showMessageDialog(this,
            "Reservation Successful!\n\n"
            + "Client      : " + clientName + "\n"
            + "Block " + blockNum + " - Lot " + lotNum + "\n"
            + "House Model : " + selectedLot.getModelName() + "\n"
            + "TCP         : PHP " + String.format("%,.2f", selectedLot.getTotalContractPrice()) + "\n"
            + "Res. Fee    : PHP " + String.format("%,.2f", selectedLot.getReservationFee()) + "\n"
            + "Expiry Date : " + selectedLot.getReservation().getExpiryDate());

        txtName.setText("");
        txtContact.setText("");
        loadLots();
    }

    private void processForfeit() {
        if (cmbLot.getSelectedItem() == null
                || cmbLot.getSelectedItem().toString().startsWith("No")) {
            return;
        }

        int blockNum = Integer.parseInt(((String) cmbBlock.getSelectedItem()).split(" ")[1]);
        Block b = Agency.getInstance().getMainProperty().getBlock(blockNum);
        String lotText = (String) cmbLot.getSelectedItem();
        int lotNum = Integer.parseInt(lotText.split(" ")[1]);
        Lot selectedLot = b.getLot(lotNum);

        String ownerName = selectedLot.getOwner() != null ? selectedLot.getOwner().getName() : "Unknown";

        int confirm = javax.swing.JOptionPane.showConfirmDialog(this,
            "Are you sure you want to forfeit the reservation?\n\n"
            + "Client      : " + ownerName + "\n"
            + "Block " + blockNum + " - Lot " + lotNum + "\n"
            + "House Model : " + selectedLot.getModelName() + "\n\n"
            + "This will set the lot back to AVAILABLE.",
            "Confirm Forfeit",
            javax.swing.JOptionPane.YES_NO_OPTION,
            javax.swing.JOptionPane.WARNING_MESSAGE);

        if (confirm == javax.swing.JOptionPane.YES_OPTION) {
            selectedLot.getReservation().forfeit();
            selectedLot.setStatus(Lot.AVAILABLE);
            selectedLot.updateOwner(null);
            selectedLot.setReservation(null);

            Log.record("FOR-" + lotNum, "FORFEITED - Lot " + lotNum
                + " Block " + blockNum + " by " + ownerName);

            javax.swing.JOptionPane.showMessageDialog(this,
                "Reservation forfeited.\n"
                + "Lot " + lotNum + " (Block " + blockNum + ") is now AVAILABLE.");

            loadLots();
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        cmbBlock = new javax.swing.JComboBox<>();
        cmbLot = new javax.swing.JComboBox<>();
        cmbAgent = new javax.swing.JComboBox<>();
        reserve = new javax.swing.JButton();
        close = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        txtContact = new javax.swing.JTextField();
        forfeit = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Client Name:");

        jLabel2.setText("Contact Info:");

        jLabel3.setText("Block:");

        jLabel4.setText("Lot:");

        jLabel5.setText("Agent:");

        cmbBlock.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbBlock.addActionListener(this::cmbBlockActionPerformed);

        cmbLot.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbLot.addActionListener(this::cmbLotActionPerformed);

        cmbAgent.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        reserve.setBackground(new java.awt.Color(0, 102, 0));
        reserve.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        reserve.setForeground(new java.awt.Color(255, 255, 255));
        reserve.setText("Reserve");
        reserve.addActionListener(this::reserveActionPerformed);

        close.setBackground(new java.awt.Color(153, 0, 0));
        close.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        close.setForeground(new java.awt.Color(255, 255, 255));
        close.setText("Close");
        close.addActionListener(this::closeActionPerformed);

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MyGui/Images/Logo.png"))); // NOI18N

        forfeit.setText("Forfeit");
        forfeit.addActionListener(this::forfeitActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(reserve)
                        .addGap(18, 18, 18)
                        .addComponent(forfeit)
                        .addGap(18, 18, 18)
                        .addComponent(close, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(38, 38, 38)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtName)
                            .addComponent(txtContact)
                            .addComponent(cmbAgent, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbLot, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cmbBlock, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(139, 139, 139))
            .addGroup(layout.createSequentialGroup()
                .addGap(111, 111, 111)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel6)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtContact, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(cmbBlock, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(cmbLot, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(cmbAgent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(close)
                    .addComponent(reserve)
                    .addComponent(forfeit))
                .addContainerGap(52, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void reserveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reserveActionPerformed
        // TODO add your handling code here:
        processReservation();
    }//GEN-LAST:event_reserveActionPerformed

    private void cmbBlockActionPerformed(java.awt.event.ActionEvent evt) {
        loadLots();
    }

    private void closeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_closeActionPerformed

    private void forfeitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_forfeitActionPerformed
        // TODO add your handling code here:
        processForfeit();
    }//GEN-LAST:event_forfeitActionPerformed

    private void cmbLotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbLotActionPerformed
        // TODO add your handling code here:
        updateButtons();
    }//GEN-LAST:event_cmbLotActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new ReserveLotFrame().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton close;
    private javax.swing.JComboBox<String> cmbAgent;
    private javax.swing.JComboBox<String> cmbBlock;
    private javax.swing.JComboBox<String> cmbLot;
    private javax.swing.JButton forfeit;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JButton reserve;
    private javax.swing.JTextField txtContact;
    private javax.swing.JTextField txtName;
    // End of variables declaration//GEN-END:variables
}
