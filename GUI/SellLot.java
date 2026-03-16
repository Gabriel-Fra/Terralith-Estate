package GUI;

import MyLib.*;

public class SellLot extends javax.swing.JFrame
{
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(SellLot.class.getName());

    public SellLot()
    {
        initComponents();
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        loadBlocks();
        loadAgents();
        loadLots();
        togglePaymentFields();
    }

    private void loadBlocks()
    {
        jComboBox1.removeAllItems();
        for (Block b : Agency.getInstance().getMainProperty().getBlocks())
        {
            jComboBox1.addItem("Block " + b.getBlockNumber());
        }
    }

    private void loadLots()
    {
        jComboBox2.removeAllItems();
        if (jComboBox1.getSelectedItem() == null)
        {
            return;
        }
        int blockNum = jComboBox1.getSelectedIndex() + 1;
        Block block = Agency.getInstance().getMainProperty().getBlock(blockNum);
        for (Lot lot : block.getLots())
        {
            if (!lot.getStatus().equals(Lot.SOLD))
            {
                jComboBox2.addItem("Lot " + lot.getLotNumber() + " [" + lot.getStatus() + "] | PHP " + String.format("%.2f", lot.getPrice()));
            }
        }
        if (jComboBox2.getItemCount() == 0)
        {
            jComboBox2.addItem("No lots available");
        }
    }

    private void loadAgents()
    {
        jComboBox3.removeAllItems();
        for (Agent a : Agency.getInstance().getAgents())
        {
            jComboBox3.addItem(a.getName());
        }
    }

    private void togglePaymentFields()
    {
        boolean isCash = "Cash".equals(jComboBox4.getSelectedItem());
        jLabel7.setVisible(isCash);
        jTextField3.setVisible(isCash);
        jLabel8.setVisible(!isCash);
        jTextField4.setVisible(!isCash);
        jLabel9.setVisible(!isCash);
        jTextField5.setVisible(!isCash);
    }

    private void sell()
    {
        String name = jTextField1.getText().trim();
        String contact = jTextField2.getText().trim();

        if (name.isEmpty() || contact.isEmpty())
        {
            javax.swing.JOptionPane.showMessageDialog(this, "Please fill in all fields.");
            return;
        }

        if (jComboBox2.getSelectedItem() == null || jComboBox2.getSelectedItem().toString().startsWith("No"))
        {
            javax.swing.JOptionPane.showMessageDialog(this, "No lots available.");
            return;
        }

        int blockNum = jComboBox1.getSelectedIndex() + 1;
        Block block = Agency.getInstance().getMainProperty().getBlock(blockNum);
        String lotText = (String) jComboBox2.getSelectedItem();
        int lotNum = Integer.parseInt(lotText.split(" ")[1]);
        Lot lot = block.getLot(lotNum);

        Agency agency = Agency.getInstance();
        Client client = agency.findClientByName(name);
        if (client == null)
        {
            client = new Client(name, contact);
            agency.addClient(client);
        }

        double resFee = 5000;
        double closeFee = 3000;
        Payment payment;

        try
        {
            if ("Cash".equals(jComboBox4.getSelectedItem()))
            {
                double disc = Double.parseDouble(jTextField3.getText().trim());
                payment = new Cash(lot.getPrice(), resFee, closeFee, disc);
            }
            else
            {
                int months = Integer.parseInt(jTextField4.getText().trim());
                double interest = Double.parseDouble(jTextField5.getText().trim());
                payment = new Installment(lot.getPrice(), resFee, closeFee, months, interest);
            }
        }
        catch (NumberFormatException ex)
        {
            javax.swing.JOptionPane.showMessageDialog(this, "Invalid number in payment fields.");
            return;
        }

        Agent agent = agency.getAgents().get(jComboBox3.getSelectedIndex());
        Transaction trans = agent.processSelling(client, lot, payment);

        if (trans != null)
        {
            agency.addTransaction(trans);
            javax.swing.JOptionPane.showMessageDialog(this,
                "Sale Successful!\nTransaction ID: " + trans.getTransactionId() + "\n\n" + payment.getPaymentSummary());
            jTextField1.setText("");
            jTextField2.setText("");
            loadLots();
        }
        else
        {
            javax.swing.JOptionPane.showMessageDialog(this, "Lot is already sold.");
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox<>();
        jComboBox2 = new javax.swing.JComboBox<>();
        jComboBox3 = new javax.swing.JComboBox<>();
        jComboBox4 = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sell a Lot");

        jLabel1.setText("Client Name:");
        jLabel2.setText("Contact Info:");
        jLabel3.setText("Block:");
        jLabel4.setText("Lot:");
        jLabel5.setText("Agent:");
        jLabel6.setText("Payment Type:");
        jLabel7.setText("Discount Rate (e.g. 0.05):");
        jLabel8.setText("Duration (months):");
        jLabel9.setText("Interest Rate (e.g. 0.08):");

        jTextField3.setText("0.05");
        jTextField4.setText("60");
        jTextField5.setText("0.08");

        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Cash", "Installment" }));
        jComboBox1.addActionListener(this::jComboBox1ActionPerformed);
        jComboBox4.addActionListener(this::jComboBox4ActionPerformed);

        jButton1.setText("Sell");
        jButton1.addActionListener(this::jButton1ActionPerformed);

        jButton2.setText("Close");
        jButton2.addActionListener(this::jButton2ActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
                    .addComponent(jTextField2)
                    .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboBox2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboBox3, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboBox4, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextField3)
                    .addComponent(jTextField4)
                    .addComponent(jTextField5))
                .addContainerGap(30, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(18, 18, 18)
                .addComponent(jButton2)
                .addGap(30, 30, 30))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1).addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2).addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3).addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4).addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5).addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6).addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7).addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8).addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9).addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1).addComponent(jButton2))
                .addGap(20, 20, 20))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt)
    {
        loadLots();
    }

    private void jComboBox4ActionPerformed(java.awt.event.ActionEvent evt)
    {
        togglePaymentFields();
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt)
    {
        sell();
    }

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt)
    {
        dispose();
    }

    public static void main(String args[])
    {
        try
        {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels())
            {
                if ("Nimbus".equals(info.getName()))
                {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        }
        catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex)
        {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> new SellLot().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    // End of variables declaration//GEN-END:variables
}
