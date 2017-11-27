/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cashier;

import Main.LoginFrame;
import com.sun.glass.events.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Locale;

/**
 *
 * @author Melvin
 */
public class PaymentFrame extends javax.swing.JFrame {

    private String orderingSID;
    private ArrayList<PaymentDomain> paymentDomain;
    private String str;
    private String host = "jdbc:derby://localhost:1527/vegansdb";
    private String user = "nbuser";
    private String password = "nbuser";
    private Connection conn = null;
    private PreparedStatement stmt = null;
    private String tableName = "Payment";
    int click = 0;
    
    

    public PaymentFrame() {

        initComponents();

    }

    /**
     * Creates new form PaymentFrame
     */
    public PaymentFrame(String staffID, String orderID, String subTotal, String totalGST) {
        initComponents();
        showDate();
        showTime();
//        jbtTotal.addActionListener(new TotalListener());
//        jbtChanges.addActionListener(new ChangesListener());
        orderingSID = staffID;
//        jtfStaffID.setText(orderingSID);
        jtfOrderID.setText(orderID);
        jtfSubTotal.setText("RM " + subTotal);
        jtfGST.setText("RM " + totalGST);
        double total = (Double.parseDouble(subTotal) + Double.parseDouble(totalGST));
        jtfTotal.setText(String.format("%.2f", total));
        AutoGenerate();
        createConnection();

//        jbtEndTransaction.addActionListener(new CreateListener());
    }

    public void createConnection() {
        try {

            conn = DriverManager.getConnection(host, user, password);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), null, JOptionPane.ERROR_MESSAGE);
        }
    }

    public void showDate() {
        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat s = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        String date = s.format(today);
        jtfDate.setText(date);
    }

    void showTime() {
        new Timer(0, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Date d = new Date();
                SimpleDateFormat s = new SimpleDateFormat("hh:mm:ss a");
                jtfTime.setText(s.format(d));
            }
        }).start();

    }

    private void setDate() {
        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        String date = s.format(today);
        jtfDate.setText(date);
    }

    private void AutoGenerate() {
        MyQuery query = new MyQuery();
        paymentDomain = query.getPayment();

        String[] paymentList = new String[paymentDomain.size()];

        for (int x = 0; x < paymentList.length; x++) {
            jtfPaymentID.setText(paymentDomain.get(x).getPAYMENT_ID());
        }
        str = jtfPaymentID.getText();
        String[] part = str.split("(?<=\\D)(?=\\d)");
        int paymentID2 = Integer.parseInt(part[1]);
        paymentID2++;
        jtfPaymentID.setText("PM" + paymentID2);
    }
    
    
    

//    private class TotalListener implements ActionListener {
//
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            double subTotal = Double.parseDouble(jtfSubTotal.getText());
//            double membership = subTotal * 0.10;
//
//            double Total1 = subTotal - membership;
//            double roundOffTotal1 = Math.round(Total1 * 20.0) / 20.0;
//
//            double Total2 = subTotal;
//            double roundOffTotal2 = Math.round(Total2 * 20.0) / 20.0;
//
//            if (jcbMembership.isSelected()) {
//                jtfMemberDiscount.setText(String.format("RM" + "%.2f", membership));
//                jtfTotal.setText(String.format("%.2f", roundOffTotal1));
//
//            } else {
//                int opt = JOptionPane.showConfirmDialog(null, "Membership?", "Select an Option", JOptionPane.YES_NO_OPTION);
//
//                if (opt == JOptionPane.YES_OPTION) {
//                    jcbMembership.setSelected(true);
//                    jtfMemberDiscount.setText(String.format("RM" + "%.2f", membership));
//                    jtfTotal.setText(String.format("%.2f", roundOffTotal1));
//                } else if (opt == JOptionPane.NO_OPTION) {
//                    jcbMembership.setSelected(false);
//                    jtfMemberDiscount.setText("0");
//                    jtfTotal.setText(String.format("%.2f", roundOffTotal2));
//                }
//            }
//
//        }
//
//    }
//
//    private class ChangesListener implements ActionListener {
//
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            double subTotal = Double.parseDouble(jtfSubTotal.getText());
//            double membership = subTotal * 0.10;
//
//            double Total1 = subTotal - membership;
//            double roundOffTotal1 = Math.round(Total1 * 20.0) / 20.0;
//
//            double Total2 = subTotal;
//            double roundOffTotal2 = Math.round(Total2 * 20.0) / 20.0;
//
//            double payment = Double.parseDouble(jtfPayment.getText());
//
//            double changes1 = payment - roundOffTotal1;
//
//            double changes2 = payment - roundOffTotal2;
//
//            if (jcbMembership.isSelected()) {
//                if (payment < Total1) {
//                    JOptionPane.showMessageDialog(null, "Payment is less than Total!", "Payment", JOptionPane.ERROR_MESSAGE);
//                } else {
//                    jtfChanges.setText(String.format("RM" + "%.2f", changes1));
//                    new ReceiptFrame(jtfSubTotal.getText(), jtfGST.getText(), jtfMemberDiscount.getText(), jtfTotal.getText(), jtfPayment.getText(), jtfChanges.getText()).setVisible(true);
//                }
//            } else {
//                if (payment < Total2) {
//                    JOptionPane.showMessageDialog(null, "Payment is less than Total!", "Payment", JOptionPane.ERROR_MESSAGE);
//                } else {
//                    jtfChanges.setText(String.format("RM" + "%.2f", changes2));
//                    new ReceiptFrame(jtfSubTotal.getText(), jtfGST.getText(), jtfMemberDiscount.getText(), jtfTotal.getText(), jtfPayment.getText(), jtfChanges.getText()).setVisible(true);
//                }
//            }
//
//        }
//
//    }
//
//    public class CreateListener implements ActionListener {
//
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            String PAYMENT_ID = jtfPayment.getText();
//
//            double PAYMENT_AMOUNT = Double.parseDouble(jtfPayment.getText());
//
//            ResultSet rs = newRecord(PAYMENT_ID);
//        }
//
//    }
//
//    public ResultSet newRecord(String PAYMENT_ID) {
//        ResultSet rs = null;
//        double PAYMENT_AMOUNT = Double.parseDouble(jtfPayment.getText());
//        setDate();
//        try {
//            if (click == 0 || PAYMENT_AMOUNT==0) {
//                JOptionPane.showMessageDialog(null, "Please complete the payment!", "Error", JOptionPane.ERROR_MESSAGE);
//            } else {
//                stmt = conn.prepareStatement("INSERT INTO " + tableName + " VALUES(?,?,?,?,?)");
//                stmt.setString(1, jtfPaymentID.getText());
//                stmt.setString(2, jtfDate.getText());
//                stmt.setDouble(3, PAYMENT_AMOUNT);
//                stmt.setString(4, jtfStaffID.getText());
//                stmt.setString(5, jtfOrderID.getText());
//                stmt.executeUpdate();
//            }
//        } catch (SQLException ex) {
//            JOptionPane.showMessageDialog(null, ex.getMessage());
//        }
//        return rs;
//    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField4 = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jtfTotal = new javax.swing.JTextField();
        jtfSubTotal = new javax.swing.JTextField();
        jtfGST = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jtfDate = new javax.swing.JTextField();
        jtfPaymentID = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jtfTime = new javax.swing.JTextField();
        jbtEndTransaction = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jtfOrderID = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Payment");
        setResizable(false);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setText("Payment");
        jLabel1.setMaximumSize(new java.awt.Dimension(106, 40));
        jLabel1.setMinimumSize(new java.awt.Dimension(106, 40));
        jLabel1.setPreferredSize(new java.awt.Dimension(106, 40));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setText("Sub Total (include GST)");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel4.setText("Total GST charged");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel5.setText("Total");

        jtfTotal.setEditable(false);
        jtfTotal.setBackground(java.awt.SystemColor.controlHighlight);
        jtfTotal.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jtfTotal.setForeground(new java.awt.Color(255, 0, 0));

        jtfSubTotal.setEditable(false);
        jtfSubTotal.setBackground(java.awt.SystemColor.controlHighlight);
        jtfSubTotal.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jtfSubTotal.setText("0");
        jtfSubTotal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtfSubTotalMouseClicked(evt);
            }
        });

        jtfGST.setEditable(false);
        jtfGST.setBackground(java.awt.SystemColor.controlHighlight);
        jtfGST.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jtfGST.setText("0");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel8.setText("Payment ID");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel9.setText("Date");

        jtfDate.setEditable(false);
        jtfDate.setBackground(java.awt.SystemColor.controlHighlight);
        jtfDate.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jtfDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtfDateActionPerformed(evt);
            }
        });

        jtfPaymentID.setEditable(false);
        jtfPaymentID.setBackground(java.awt.SystemColor.controlHighlight);
        jtfPaymentID.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel11.setText("Current Time");

        jtfTime.setEditable(false);
        jtfTime.setBackground(java.awt.SystemColor.controlHighlight);
        jtfTime.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N

        jbtEndTransaction.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jbtEndTransaction.setMnemonic('E');
        jbtEndTransaction.setText("End Transaction");
        jbtEndTransaction.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtEndTransactionActionPerformed(evt);
            }
        });
        jbtEndTransaction.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jbtEndTransactionKeyPressed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel12.setText("Order ID");

        jtfOrderID.setEditable(false);
        jtfOrderID.setBackground(java.awt.SystemColor.controlHighlight);
        jtfOrderID.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel15.setText("RM");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel5)
                        .addGap(180, 180, 180)
                        .addComponent(jLabel15)
                        .addGap(6, 6, 6)
                        .addComponent(jtfTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel12))
                                .addGap(6, 6, 6)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jtfOrderID, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jtfPaymentID, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel2))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel4)))
                        .addGap(89, 89, 89)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jtfSubTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jtfGST, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(60, 234, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel11)
                                    .addComponent(jLabel9))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jtfDate, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jtfTime, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap(39, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(329, 329, 329))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jbtEndTransaction, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel8)
                    .addComponent(jtfPaymentID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtfDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jtfOrderID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(jtfTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(60, 60, 60)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtfSubTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jtfGST, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jLabel15)
                            .addComponent(jtfTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addComponent(jbtEndTransaction, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(33, Short.MAX_VALUE))
        );

        setSize(new java.awt.Dimension(787, 529));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jtfSubTotalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtfSubTotalMouseClicked
click++;
    }//GEN-LAST:event_jtfSubTotalMouseClicked

    private void jbtEndTransactionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtEndTransactionActionPerformed

            new OrderingFrame(orderingSID).setVisible(true);
            dispose();
    }//GEN-LAST:event_jbtEndTransactionActionPerformed

    private void jbtEndTransactionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jbtEndTransactionKeyPressed

    }//GEN-LAST:event_jbtEndTransactionKeyPressed

    private void jtfDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtfDateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtfDateActionPerformed

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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PaymentFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PaymentFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PaymentFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PaymentFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
//Hello                new PaymentFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JButton jbtEndTransaction;
    private javax.swing.JTextField jtfDate;
    private javax.swing.JTextField jtfGST;
    private javax.swing.JTextField jtfOrderID;
    private javax.swing.JTextField jtfPaymentID;
    private javax.swing.JTextField jtfSubTotal;
    private javax.swing.JTextField jtfTime;
    private javax.swing.JTextField jtfTotal;
    // End of variables declaration//GEN-END:variables
}
