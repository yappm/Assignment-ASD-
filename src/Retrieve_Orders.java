
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author PM
 */
public class Retrieve_Orders extends javax.swing.JFrame {

    int day, month, year;
    int sec, min, hour;

    public Retrieve_Orders() {
        initComponents();
        Date();
    }

    public void Date() {

        String Day, Month, Year, Sec, Min, Hour;
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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Retrieve_Orders.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Retrieve_Orders.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Retrieve_Orders.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Retrieve_Orders.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        GregorianCalendar gc = new GregorianCalendar();
        day = gc.get(Calendar.DAY_OF_MONTH);
        month = gc.get(Calendar.MONTH);
        year = gc.get(Calendar.YEAR);

        sec = gc.get(Calendar.SECOND);
        min = gc.get(Calendar.MINUTE);
        hour = gc.get(Calendar.HOUR);

        Day = Integer.toString(day);
        Month = Integer.toString(month);
        Year = Integer.toString(year);
        Sec = Integer.toString(sec);
        Min = Integer.toString(min);
        Hour = Integer.toString(hour);
        /* Create and display the form */
        jtfDateTime.setText(Day + "/" + Month + "/" + Year + " " + Hour + ":" + Min + ":" + Sec);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtOrders = new javax.swing.JTable();
        jbtRetrieve = new javax.swing.JButton();
        jlblDateTime = new javax.swing.JLabel();
        jbtnReset = new javax.swing.JButton();
        jcbStaffID = new javax.swing.JComboBox();
        jtfDateTime = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel1.setText("Retrieve Orders");

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel13.setText("Delivery Status :");

        jtOrders.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Order ID", "Order Item", "Quantity", "Order Date", "Order Time", "Order Status", "Delivery Man in-charged"
            }
        ));
        jScrollPane1.setViewportView(jtOrders);

        jbtRetrieve.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jbtRetrieve.setText("Retrieve");
        jbtRetrieve.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtRetrieveActionPerformed(evt);
            }
        });

        jlblDateTime.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jlblDateTime.setText("Date & Time:");

        jbtnReset.setText("Reset");
        jbtnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnResetActionPerformed(evt);
            }
        });

        jcbStaffID.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-- Select Status --", "Delivered", "Delivering", "Pending", "Cancelled" }));

        jtfDateTime.setEditable(false);
        jtfDateTime.setBackground(java.awt.Color.lightGray);
        jtfDateTime.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1084, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jlblDateTime)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jtfDateTime, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addGap(18, 18, 18)
                                .addComponent(jcbStaffID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(35, 35, 35)
                                .addComponent(jbtRetrieve)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jbtnReset)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlblDateTime)
                    .addComponent(jtfDateTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jbtRetrieve)
                    .addComponent(jbtnReset)
                    .addComponent(jcbStaffID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>                        

    private void jbtRetrieveActionPerformed(java.awt.event.ActionEvent evt) {                                            
        jtOrders.setModel(new DefaultTableModel(null, new String[]{"Order ID", "Order Item", "Quantity", "Order Date", "Order Time", "Order Status","Delivery Man in-charged"}));
        DefaultTableModel orders = (DefaultTableModel) jtOrders.getModel();
        if (jcbStaffID.getSelectedItem().equals("Delivered")) {
            
            orders.addRow(new Object[]{"OD001", "Fried Rice", "2", "20/11/2017", "15:30:21", "Delivered","ST001"});
            
        } else if (jcbStaffID.getSelectedItem().equals("Delivering")) {
            
            orders.addRow(new Object[]{"OD003", "Nuggets", "2", "20/11/2017", "15:35:46", "Delivering","ST002"});
            

        } else if (jcbStaffID.getSelectedItem().equals("Pending")) {
            
            orders.addRow(new Object[]{"OD004", "Chili Pan Mee", "2", "20/11/2017", "15:40:13", "Pending","Pending"});
            orders.addRow(new Object[]{"OD005", "Mee Soup", "2", "20/11/2017", "16:00:08", "Pending","Pending"});
            
        } else if (jcbStaffID.getSelectedItem().equals("Cancelled")) {
            
            orders.addRow(new Object[]{"OD002", "Fried Noodles", "2", "20/11/2017", "15:35:11", "Cancelled","--"});
            
        } else if (jcbStaffID.getSelectedItem().equals("-- Select Status --")) {
            JOptionPane.showMessageDialog(null, "Please Select Delivery Status!!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }                                           

    private void jbtnResetActionPerformed(java.awt.event.ActionEvent evt) {                                          
        jtOrders.setModel(new DefaultTableModel(null, new String[]{"Order ID", "Order Item", "Quantity", "Order Date", "Order Time", "Order Status","Delivery Man in-charged"}));
    }                                         

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Retrieve_Orders().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify                     
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbtRetrieve;
    private javax.swing.JButton jbtnReset;
    private javax.swing.JComboBox jcbStaffID;
    private javax.swing.JLabel jlblDateTime;
    private javax.swing.JTable jtOrders;
    private javax.swing.JTextField jtfDateTime;
    // End of variables declaration                   
}
