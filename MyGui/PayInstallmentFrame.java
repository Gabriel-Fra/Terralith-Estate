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
public class PayInstallmentFrame extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(PayInstallmentFrame.class.getName());

    public PayInstallmentFrame() {
        setTitle("Pay Installment");
        initComponents();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        loadBlocks();
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
            clearInfo();
            return;
        }

        int blockNum = cmbBlock.getSelectedIndex() + 1;
        Block b = Agency.getInstance().getMainProperty().getBlock(blockNum);

        for (Lot l : b.getLots()) {
            if (l.getStatus().equals(Lot.SOLD)) {
                Transaction t = Agency.getInstance().findTransactionByLot(l);
                if (t != null && t.getPayment() instanceof Installment) {
                    Installment inst = (Installment) t.getPayment();
                    if (!inst.isFullyPaid()) {
                        cmbLot.addItem("Lot " + l.getLotNumber()
                                + " | " + l.getModelName()
                                + " | " + l.getOwner().getName());
                    }
                }
            }
        }

        if (cmbLot.getItemCount() == 0) {
            cmbLot.addItem("No installment lots");
        }

        cmbLot.addActionListener(this::cmbLotActionPerformed);
        updateInfo();
    }

    private void updateInfo() {
        if (cmbLot.getSelectedItem() == null
                || cmbLot.getSelectedItem().toString().startsWith("No")) {
            clearInfo();
            return;
        }

        int blockNum = cmbBlock.getSelectedIndex() + 1;
        Block b = Agency.getInstance().getMainProperty().getBlock(blockNum);
        String lotText = (String) cmbLot.getSelectedItem();
        int lotNum = Integer.parseInt(lotText.split(" ")[1]);
        Lot lot = b.getLot(lotNum);
        Transaction t = Agency.getInstance().findTransactionByLot(lot);

        if (t == null || !(t.getPayment() instanceof Installment)) {
            clearInfo();
            return;
        }

        Installment inst = (Installment) t.getPayment();

        txtClient.setText(lot.getOwner().getName());
        txtModel.setText(lot.getModelName());
        txtMonthly.setText(String.format("PHP %,.2f", inst.getAmortization()));
        txtBalance.setText(String.format("PHP %,.2f", inst.getBalance()));
        txtMonthsPaid.setText(inst.getMonthsPaid() + " / " + inst.getDurationMonths() + " months");
        txtStatus.setText(inst.isFullyPaid() ? "FULLY PAID" : "ONGOING");
    }

    private void clearInfo() {
        txtClient.setText("");
        txtModel.setText("");
        txtMonthly.setText("");
        txtBalance.setText("");
        txtMonthsPaid.setText("");
        txtStatus.setText("");
    }

    private void processPayment() {
        if (cmbLot.getSelectedItem() == null
                || cmbLot.getSelectedItem().toString().startsWith("No")) {
            javax.swing.JOptionPane.showMessageDialog(this, "No lot selected.");
            return;
        }

        int blockNum = cmbBlock.getSelectedIndex() + 1;
        Block b = Agency.getInstance().getMainProperty().getBlock(blockNum);
        String lotText = (String) cmbLot.getSelectedItem();
        int lotNum = Integer.parseInt(lotText.split(" ")[1]);
        Lot lot = b.getLot(lotNum);
        Transaction t = Agency.getInstance().findTransactionByLot(lot);

        if (t == null || !(t.getPayment() instanceof Installment)) {
            javax.swing.JOptionPane.showMessageDialog(this, "No installment record found.");
            return;
        }

        Installment inst = (Installment) t.getPayment();

        if (inst.isFullyPaid()) {
            javax.swing.JOptionPane.showMessageDialog(this, "This lot is already fully paid.");
            return;
        }

        inst.makePayment();

        Log.record("PAY-" + t.getTransactionId(),
            "PAYMENT - Lot " + lot.getLotNumber()
            + " by " + lot.getOwner().getName()
            + " | Remaining: PHP " + String.format("%.2f", inst.getBalance()));

        if (inst.isFullyPaid()) {
            javax.swing.JOptionPane.showMessageDialog(this,
                "Payment Successful!\n\n"
                + "Client : " + lot.getOwner().getName() + "\n"
                + "Lot    : Block " + blockNum + " - Lot " + lotNum + "\n"
                + "Amount : PHP " + String.format("%,.2f", inst.getAmortization()) + "\n\n"
                + "LOAN FULLY PAID! Congratulations!");
            loadLots(); // remove from dropdown
        } else {
            javax.swing.JOptionPane.showMessageDialog(this,
                "Payment Successful!\n\n"
                + "Client           : " + lot.getOwner().getName() + "\n"
                + "Lot              : Block " + blockNum + " - Lot " + lotNum + "\n"
                + "Amount Paid      : PHP " + String.format("%,.2f", inst.getAmortization()) + "\n"
                + "Remaining Balance: PHP " + String.format("%,.2f", inst.getBalance()) + "\n"
                + "Months Remaining : " + inst.getRemainingMonths());
            updateInfo();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        cmbBlock = new javax.swing.JComboBox<>();
        cmbLot = new javax.swing.JComboBox<>();
        txtClient = new javax.swing.JTextField();
        txtModel = new javax.swing.JTextField();
        txtMonthly = new javax.swing.JTextField();
        txtBalance = new javax.swing.JTextField();
        txtMonthsPaid = new javax.swing.JTextField();
        txtStatus = new javax.swing.JTextField();
        btnPay = new javax.swing.JButton();
        btnClose = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Block:");
        jLabel2.setText("Lot:");
        jLabel3.setText("Client:");
        jLabel4.setText("House Model:");
        jLabel5.setText("Monthly Payment:");
        jLabel6.setText("Remaining Balance:");
        jLabel7.setText("Months Paid:");
        jLabel8.setText("Status:");

        cmbBlock.addActionListener(this::cmbBlockActionPerformed);
        cmbLot.addActionListener(this::cmbLotActionPerformed);

        txtClient.setEditable(false);
        txtModel.setEditable(false);
        txtMonthly.setEditable(false);
        txtBalance.setEditable(false);
        txtMonthsPaid.setEditable(false);
        txtStatus.setEditable(false);

        btnPay.setBackground(new java.awt.Color(0, 102, 0));
        btnPay.setFont(new java.awt.Font("Segoe UI", 1, 18));
        btnPay.setForeground(new java.awt.Color(255, 255, 255));
        btnPay.setText("Pay");
        btnPay.addActionListener(this::btnPayActionPerformed);

        btnClose.setBackground(new java.awt.Color(153, 0, 0));
        btnClose.setFont(new java.awt.Font("Segoe UI", 1, 18));
        btnClose.setForeground(new java.awt.Color(255, 255, 255));
        btnClose.setText("Close");
        btnClose.addActionListener(this::btnCloseActionPerformed);

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MyGui/Images/Logo.png")));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(154, 154, 154)
                        .addComponent(btnPay, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40)
                        .addComponent(btnClose, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel8))
                                .addGap(30, 30, 30)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(cmbBlock, 0, 220, Short.MAX_VALUE)
                                    .addComponent(cmbLot, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtClient)
                                    .addComponent(txtModel)
                                    .addComponent(txtMonthly)
                                    .addComponent(txtBalance)
                                    .addComponent(txtMonthsPaid)
                                    .addComponent(txtStatus))))))
                .addContainerGap(52, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel9)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cmbBlock, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cmbLot, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtClient, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtModel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtMonthly, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtBalance, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtMonthsPaid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnPay, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnClose, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cmbBlockActionPerformed(java.awt.event.ActionEvent evt) {
        loadLots();
    }

    private void cmbLotActionPerformed(java.awt.event.ActionEvent evt) {
        updateInfo();
    }

    private void btnPayActionPerformed(java.awt.event.ActionEvent evt) {
        processPayment();
    }

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {
        dispose();
    }

    public static void main(String args[]) {
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

        java.awt.EventQueue.invokeLater(() -> new PayInstallmentFrame().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnPay;
    private javax.swing.JComboBox<String> cmbBlock;
    private javax.swing.JComboBox<String> cmbLot;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JTextField txtBalance;
    private javax.swing.JTextField txtClient;
    private javax.swing.JTextField txtModel;
    private javax.swing.JTextField txtMonthly;
    private javax.swing.JTextField txtMonthsPaid;
    private javax.swing.JTextField txtStatus;
    // End of variables declaration//GEN-END:variables
}
