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
        jbtTotal.addActionListener(new TotalListener());
        jbtChanges.addActionListener(new ChangesListener());
        orderingSID = staffID;
        jtfStaffID.setText(orderingSID);
        jtfOrderID.setText(orderID);
        jtfSubTotal.setText(subTotal);
        jtfGST.setText(totalGST);
        AutoGenerate();
        createConnection();

        jbtEndTransaction.addActionListener(new CreateListener());
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

    private class TotalListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            double subTotal = Double.parseDouble(jtfSubTotal.getText());
            double membership = subTotal * 0.10;

            double Total1 = subTotal - membership;
            double roundOffTotal1 = Math.round(Total1 * 20.0) / 20.0;

            double Total2 = subTotal;
            double roundOffTotal2 = Math.round(Total2 * 20.0) / 20.0;

            if (jcbMembership.isSelected()) {
                jtfMemberDiscount.setText(String.format("RM" + "%.2f", membership));
                jtfTotal.setText(String.format("%.2f", roundOffTotal1));

            } else {
                int opt = JOptionPane.showConfirmDialog(null, "Membership?", "Select an Option", JOptionPane.YES_NO_OPTION);

                if (opt == JOptionPane.YES_OPTION) {
                    jcbMembership.setSelected(true);
                    jtfMemberDiscount.setText(String.format("RM" + "%.2f", membership));
                    jtfTotal.setText(String.format("%.2f", roundOffTotal1));
                } else if (opt == JOptionPane.NO_OPTION) {
                    jcbMembership.setSelected(false);
                    jtfMemberDiscount.setText("0");
                    jtfTotal.setText(String.format("%.2f", roundOffTotal2));
                }
            }

        }

    }

    private class ChangesListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            double subTotal = Double.parseDouble(jtfSubTotal.getText());
            double membership = subTotal * 0.10;

            double Total1 = subTotal - membership;
            double roundOffTotal1 = Math.round(Total1 * 20.0) / 20.0;

            double Total2 = subTotal;
            double roundOffTotal2 = Math.round(Total2 * 20.0) / 20.0;

            double payment = Double.parseDouble(jtfPayment.getText());

            double changes1 = payment - roundOffTotal1;

            double changes2 = payment - roundOffTotal2;

            if (jcbMembership.isSelected()) {
                if (payment < Total1) {
                    JOptionPane.showMessageDialog(null, "Payment is less than Total!", "Payment", JOptionPane.ERROR_MESSAGE);
                } else {
                    jtfChanges.setText(String.format("RM" + "%.2f", changes1));
                    new ReceiptFrame(jtfSubTotal.getText(), jtfGST.getText(), jtfMemberDiscount.getText(), jtfTotal.getText(), jtfPayment.getText(), jtfChanges.getText()).setVisible(true);
                }
            } else {
                if (payment < Total2) {
                    JOptionPane.showMessageDialog(null, "Payment is less than Total!", "Payment", JOptionPane.ERROR_MESSAGE);
                } else {
                    jtfChanges.setText(String.format("RM" + "%.2f", changes2));
                    new ReceiptFrame(jtfSubTotal.getText(), jtfGST.getText(), jtfMemberDiscount.getText(), jtfTotal.getText(), jtfPayment.getText(), jtfChanges.getText()).setVisible(true);
                }
            }

        }

    }

    public class CreateListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String PAYMENT_ID = jtfPayment.getText();

            double PAYMENT_AMOUNT = Double.parseDouble(jtfPayment.getText());

            ResultSet rs = newRecord(PAYMENT_ID);
        }

    }

    public ResultSet newRecord(String PAYMENT_ID) {
        ResultSet rs = null;
        double PAYMENT_AMOUNT = Double.parseDouble(jtfPayment.getText());
        setDate();
        try {
            if (click == 0 || PAYMENT_AMOUNT==0) {
                JOptionPane.showMessageDialog(null, "Please complete the payment!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                stmt = conn.prepareStatement("INSERT INTO " + tableName + " VALUES(?,?,?,?,?)");
                stmt.setString(1, jtfPaymentID.getText());
                stmt.setString(2, jtfDate.getText());
                stmt.setDouble(3, PAYMENT_AMOUNT);
                stmt.setString(4, jtfStaffID.getText());
                stmt.setString(5, jtfOrderID.getText());
                stmt.executeUpdate();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return rs;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField4 = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jbtTotal = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jcbMembership = new javax.swing.JCheckBox();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jtfTotal = new javax.swing.JTextField();
        jtfChanges = new javax.swing.JTextField();
        jtfSubTotal = new javax.swing.JTextField();
        jtfGST = new javax.swing.JTextField();
        jtfPayment = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jtfDate = new javax.swing.JTextField();
        jtfPaymentID = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jtfStaffID = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jtfTime = new javax.swing.JTextField();
        jbtChanges = new javax.swing.JButton();
        jbtEndTransaction = new javax.swing.JButton();
        jtfMemberDiscount = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jtfOrderID = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Payment");
        setResizable(false);

        jbtTotal.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jbtTotal.setMnemonic('T');
        jbtTotal.setText("Total");
        jbtTotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtTotalActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setText("Payment");

        jLabel3.setFont(new java.awt.Font("Forte", 1, 48)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 153, 102));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("The Vegans");

        jcbMembership.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jcbMembership.setText("Membership");
        jcbMembership.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbMembershipActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setText("Sub Total (include GST)");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel4.setText("Total GST charged");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel5.setText("Total");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel6.setText("Payment (RM)");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel7.setText("Changes");

        jtfTotal.setEditable(false);
        jtfTotal.setBackground(java.awt.SystemColor.controlHighlight);
        jtfTotal.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jtfTotal.setForeground(new java.awt.Color(255, 0, 0));

        jtfChanges.setEditable(false);
        jtfChanges.setBackground(java.awt.SystemColor.controlHighlight);
        jtfChanges.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jtfChanges.setForeground(new java.awt.Color(255, 0, 0));

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

        jtfPayment.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jtfPayment.setText("0");
        jtfPayment.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtfPaymentMouseClicked(evt);
            }
        });
        jtfPayment.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtfPaymentKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtfPaymentKeyTyped(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel8.setText("Payment ID");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel9.setText("Date");

        jtfDate.setEditable(false);
        jtfDate.setBackground(java.awt.SystemColor.controlHighlight);
        jtfDate.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N

        jtfPaymentID.setEditable(false);
        jtfPaymentID.setBackground(java.awt.SystemColor.controlHighlight);
        jtfPaymentID.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel13.setText("Staff ID");

        jtfStaffID.setEditable(false);
        jtfStaffID.setBackground(java.awt.SystemColor.controlHighlight);
        jtfStaffID.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel11.setText("Current Time");

        jtfTime.setEditable(false);
        jtfTime.setBackground(java.awt.SystemColor.controlHighlight);
        jtfTime.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N

        jbtChanges.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jbtChanges.setMnemonic('C');
        jbtChanges.setText("Changes");
        jbtChanges.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jbtChangesMouseClicked(evt);
            }
        });
        jbtChanges.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtChangesActionPerformed(evt);
            }
        });

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

        jtfMemberDiscount.setEditable(false);
        jtfMemberDiscount.setBackground(java.awt.SystemColor.controlHighlight);
        jtfMemberDiscount.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jtfMemberDiscount.setText("0");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel10.setText("Member discount(10%)");

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel12.setText("Order ID");

        jtfOrderID.setEditable(false);
        jtfOrderID.setBackground(java.awt.SystemColor.controlHighlight);
        jtfOrderID.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel14.setText("RM");

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel15.setText("RM");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtfPaymentID, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39)
                        .addComponent(jLabel12)
                        .addGap(18, 18, 18)
                        .addComponent(jtfOrderID, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 310, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jtfDate, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addGap(18, 18, 18)
                                .addComponent(jtfTime, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addGap(18, 18, 18)
                                .addComponent(jtfStaffID, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(2, 2, 2))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(16, 16, 16)
                                        .addComponent(jLabel4))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(17, 17, 17)
                                        .addComponent(jLabel2)))
                                .addGap(75, 75, 75)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jtfSubTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jtfGST, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                                .addComponent(jcbMembership)
                                .addGap(60, 60, 60))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(16, 16, 16)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel10)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                            .addComponent(jLabel6)
                                            .addGap(86, 86, 86))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                            .addComponent(jLabel7)
                                            .addGap(113, 113, 113)))
                                    .addComponent(jLabel5))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel15)
                                    .addComponent(jLabel14))
                                .addGap(6, 6, 6)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jtfPayment, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jtfChanges, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(jtfTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jtfMemberDiscount, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jbtEndTransaction, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jbtTotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jbtChanges, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap(44, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jtfStaffID, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel9)
                        .addComponent(jtfDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel12)
                        .addComponent(jtfOrderID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel8)
                        .addComponent(jtfPaymentID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel11)
                        .addComponent(jtfTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(57, 57, 57)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jtfSubTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jtfGST, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jcbMembership))
                        .addGap(21, 21, 21)
                        .addComponent(jLabel4)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jbtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbtChanges, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbtEndTransaction, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jtfMemberDiscount, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel10))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jtfTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jtfPayment, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel6))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jtfChanges, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel5)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addGap(63, 63, 63)
                                .addComponent(jLabel14))))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(160, 160, 160)
                        .addComponent(jLabel7)))
                .addContainerGap(44, Short.MAX_VALUE))
        );

        setSize(new java.awt.Dimension(788, 586));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jcbMembershipActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbMembershipActionPerformed


    }//GEN-LAST:event_jcbMembershipActionPerformed

    private void jtfSubTotalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtfSubTotalMouseClicked
click++;
    }//GEN-LAST:event_jtfSubTotalMouseClicked

    private void jtfPaymentMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtfPaymentMouseClicked
        jtfPayment.setText("");
    }//GEN-LAST:event_jtfPaymentMouseClicked

    private void jbtEndTransactionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtEndTransactionActionPerformed
        if (click != 0) {
            new OrderingFrame(orderingSID).setVisible(true);
            dispose();
        }
    }//GEN-LAST:event_jbtEndTransactionActionPerformed

    private void jtfPaymentKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfPaymentKeyTyped

        boolean max = jtfPayment.getText().length() >= 6;

        if (max) {
            evt.consume();
        }
        char e = evt.getKeyChar();
        if (!(Character.isDigit(e) || e == KeyEvent.VK_BACKSPACE || e == KeyEvent.VK_DELETE || e == KeyEvent.VK_ENTER || e == '.')) {
            evt.consume();
            JOptionPane.showMessageDialog(null, "Alphabetics detected! Please only key in DIGITS.", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_jtfPaymentKeyTyped

    private void jtfPaymentKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfPaymentKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            double subTotal = Double.parseDouble(jtfSubTotal.getText());
            double membership = subTotal * 0.10;

            double Total1 = subTotal - membership;
            double roundOffTotal1 = Math.round(Total1 * 20.0) / 20.0;

            double Total2 = subTotal;
            double roundOffTotal2 = Math.round(Total2 * 20.0) / 20.0;

            double payment = Double.parseDouble(jtfPayment.getText());

            double changes1 = payment - roundOffTotal1;

            double changes2 = payment - roundOffTotal2;

            if (jcbMembership.isSelected()) {
                if (payment < Total1) {
                    JOptionPane.showMessageDialog(null, "Payment is less than Total!", "Payment", JOptionPane.ERROR_MESSAGE);
                } else {
                    jtfChanges.setText(String.format("RM" + "%.2f", changes1));
                    new ReceiptFrame(jtfSubTotal.getText(), jtfGST.getText(), jtfMemberDiscount.getText(), jtfTotal.getText(), jtfPayment.getText(), jtfChanges.getText()).setVisible(true);
                }
            } else {
                if (payment < Total2) {
                    JOptionPane.showMessageDialog(null, "Payment is less than Total!", "Payment", JOptionPane.ERROR_MESSAGE);

                } else {
                    jtfChanges.setText(String.format("RM" + "%.2f", changes2));
                    new ReceiptFrame(jtfSubTotal.getText(), jtfGST.getText(), jtfMemberDiscount.getText(), jtfTotal.getText(), jtfPayment.getText(), jtfChanges.getText()).setVisible(true);
                }
            }
        }
    }//GEN-LAST:event_jtfPaymentKeyPressed

    private void jbtChangesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtChangesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jbtChangesActionPerformed

    private void jbtTotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtTotalActionPerformed

    }//GEN-LAST:event_jbtTotalActionPerformed

    private void jbtEndTransactionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jbtEndTransactionKeyPressed

    }//GEN-LAST:event_jbtEndTransactionKeyPressed

    private void jbtChangesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbtChangesMouseClicked
        click++;
    }//GEN-LAST:event_jbtChangesMouseClicked

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
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
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
    private javax.swing.JButton jbtChanges;
    private javax.swing.JButton jbtEndTransaction;
    private javax.swing.JButton jbtTotal;
    private javax.swing.JCheckBox jcbMembership;
    private javax.swing.JTextField jtfChanges;
    private javax.swing.JTextField jtfDate;
    private javax.swing.JTextField jtfGST;
    private javax.swing.JTextField jtfMemberDiscount;
    private javax.swing.JTextField jtfOrderID;
    private javax.swing.JTextField jtfPayment;
    private javax.swing.JTextField jtfPaymentID;
    private javax.swing.JTextField jtfStaffID;
    private javax.swing.JTextField jtfSubTotal;
    private javax.swing.JTextField jtfTime;
    private javax.swing.JTextField jtfTotal;
    // End of variables declaration//GEN-END:variables
}
