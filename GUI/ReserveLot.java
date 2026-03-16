package GUI;

import MyLib.*;

public class ReserveLot extends javax.swing.JFrame
{
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(ReserveLot.class.getName());

    public ReserveLot()
    {
        initComponents();
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        loadBlocks();
        loadAgents();
        loadLots();
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
        for (Lot lot : block.getAvailableLots())
        {
            jComboBox2.addItem("Lot " + lot.getLotNumber() + " | " + lot.getArea() + " sqm | PHP " + String.format("%.2f", lot.getPrice()));
        }
        if (jComboBox2.getItemCount() == 0)
        {
            jComboBox2.addItem("No available lots");
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

    private void reserve()
    {
        String name = jTextField1.getText().trim();
        String contact = jTextField2.getText().trim();

        if (name.isEmpty() || contact.isEmpty())
        {
            javax.swing.JOptionPane.showMessageDialog(this, "Please fill in all fields.");
            return;
        }

        if (jComboBox2.getItemCount() == 0 || jComboBox2.getSelectedItem().toString().startsWith("No"))
        {
            javax.swing.JOptionPane.showMessageDialog(this, "No available lots in this block.");
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

        Agent agent = agency.getAgents().get(jComboBox3.getSelectedIndex());
        agent.processReservation(client, lot);

        javax.swing.JOptionPane.showMessageDialog(this,
            "Reservation successful!\n"
            + "Lot " + lotNum + " in Block " + blockNum + "\n"
            + "Client: " + name + "\n"
            + "Expiry: " + lot.getReservation().getExpiryDate());

        jTextField1.setText("");
        jTextField2.setText("");
        loadLots();
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
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox<>();
        jComboBox2 = new javax.swing.JComboBox<>();
        jComboBox3 = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Reserve a Lot");

        jLabel1.setText("Client Name:");
        jLabel2.setText("Contact Info:");
        jLabel3.setText("Block:");
        jLabel4.setText("Lot:");
        jLabel5.setText("Agent:");

        jComboBox1.addActionListener(this::jComboBox1ActionPerformed);

        jButton1.setText("Reserve");
        jButton1.addActionListener(this::jButton1ActionPerformed);

        jButton2.setText("Close");
        jButton2.addActionListener(this::jButton2ActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
                    .addComponent(jTextField2)
                    .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboBox2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboBox3, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                    .addComponent(jLabel1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addGap(20, 20, 20))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt)
    {
        loadLots();
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt)
    {
        reserve();
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

        java.awt.EventQueue.invokeLater(() -> new ReserveLot().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables
}
