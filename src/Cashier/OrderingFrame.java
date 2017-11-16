package Cashier;

import Main.LoginFrame;
import com.sun.glass.events.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static java.lang.System.out;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author B1
 */
public class OrderingFrame extends javax.swing.JFrame {

    private String host = "jdbc:derby://localhost:1527/vegansdb";
    private String user = "nbuser";
    private String password = "nbuser";
    private Connection conn = null;
    private PreparedStatement stmt = null;
    private String tableName = "MENU";
    private String orderTable = "ORDERING";
    private String orderList = "ORDER_LIST";
//    private String sqlQueryStr = "SELECT * from " + tableName;
    private String orderingSID;
    private ArrayList<MenuList> menuList;
    private ArrayList<OrderDomain> orderDomain;
    private ResultSet rs = null;
    private String str;

    //For Order Table
    private String foodID;
    private String quantity;
    private Double foodPrice;
    private String foodName;
    private Double gstPU; // per unit
    private Double sumFP; // food price
    private Double sumGST;
    private String sumFP1;
    private String sumGST1;

    private double subTotal;
    private double totalGST;

    private int tableRow;

    private int click = 0;

    /**
     * Creates new form OrderingFrame
     */
    public OrderingFrame() {

        initComponents();
    }

    public OrderingFrame(String para) {
        initComponents();
        showDate();
        showTime();
        orderingSID = para;

        
        menuComboBox();
        AutoGenerate();
        ShowInJTable();
        jbtAdd.addActionListener(new OrderListener());
        jbtPayment.addActionListener(new CreateListener());
        createConnection();
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

    public void showTime() {
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

    public void menuComboBox() {
        MyQuery test = new MyQuery();
        menuList = test.getMenu();

        String[] codeList = new String[menuList.size()];
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();

        for (int x = 0; x < codeList.length; x++) {
            if (menuList.get(x).getFOOD_STATUS().equals("Available")) {
                model.addElement(menuList.get(x).getFOOD_ID());
            }
        }
        jcbFoodName.setModel(model);
    }

    public void ShowInJTable() {
        MyQuery test1 = new MyQuery();
        menuList = test1.getMenu();

        String[] codeList = new String[4];

        DefaultTableModel model = (DefaultTableModel) jTable3.getModel();
        for (int i = 0; i < menuList.size(); i++) {
            if (menuList.get(i).getFOOD_STATUS().equals("Available")) {
                codeList[0] = menuList.get(i).getFOOD_ID();
                codeList[1] = menuList.get(i).getFOOD_NAME();
                codeList[2] = menuList.get(i).getFOOD_PRICE();
                codeList[3] = menuList.get(i).getFOOD_STATUS();

                model.addRow(codeList);
            }
        }
    }

    private void AutoGenerate() {
        MyQuery query = new MyQuery();
        orderDomain = query.getOrder();

        String[] orderList = new String[orderDomain.size()];

        for (int x = 0; x < orderList.length; x++) {
            jtfOrderID.setText(orderDomain.get(x).getORDER_ID());
        }
        str = jtfOrderID.getText();
        String[] part = str.split("(?<=\\D)(?=\\d)");
        int orderID2 = Integer.parseInt(part[1]);
        orderID2++;
        jtfOrderID.setText("OD" + orderID2);
    }

    private class OrderListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            MyQuery query = new MyQuery();
            menuList = query.getMenu();

            foodID = (String) jcbFoodName.getSelectedItem();
            quantity = (String) jcbQuantity.getSelectedItem();
            String[] foodList = new String[7];
            DefaultTableModel model = (DefaultTableModel) jTable2.getModel();

            for (int a = 0; a < menuList.size(); a++) {
                if (menuList.get(a).getFOOD_ID().equals(foodID)) {
                    foodPrice = Double.parseDouble(menuList.get(a).getFOOD_PRICE());
                    foodName = menuList.get(a).getFOOD_NAME();
                }
            }
            gstPU = (foodPrice / 1.06) * 0.06;
            int qty = Integer.parseInt(quantity);
            sumFP = foodPrice * qty;
            sumGST = gstPU * qty;

            String foodPrice1 = String.valueOf(String.format("%.2f", foodPrice));
            String gstPU1 = String.valueOf(String.format("%.2f", gstPU));
            String quantity1 = String.valueOf(qty);
            sumFP1 = String.valueOf(String.format("%.2f", sumFP));
            sumGST1 = String.valueOf(String.format("%.2f", sumGST));
            foodList[0] = foodID;
            foodList[1] = foodName;
            foodList[2] = quantity1;
            foodList[3] = foodPrice1;
            foodList[4] = gstPU1;
            foodList[5] = sumFP1;
            foodList[6] = sumGST1;

            model.addRow(foodList);

            double total = Double.parseDouble(sumFP1);
            subTotal += total;
            jtfSubTotal.setText(String.format("%.2f", subTotal));

            double GST = Double.parseDouble(sumGST1);
            totalGST += GST;
            jtfTotalGST.setText(String.format("%.2f", totalGST));

            tableRow++;

        }
    }
    

    public class CreateListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String ORDER_ID = jtfOrderID.getText();
            if (click != 0) {
                ResultSet rs = newOrderRecord(ORDER_ID);
                ResultSet rs1 = newOrderListRecord(ORDER_ID);
            } else {
                JOptionPane.showMessageDialog(null, "Please make any order before proceed to payment!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public ResultSet newOrderRecord(String ORDER_ID) {
        ResultSet rs = null;
        String staffID = "OD999";

        setDate();
        try {
            stmt = conn.prepareStatement("INSERT INTO " + orderTable + " VALUES(?,?,?)");
            stmt.setString(1, jtfOrderID.getText());
            stmt.setString(2, jtfDate.getText());
            stmt.setString(3, staffID);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }

        return rs;
    }

    public ResultSet newOrderListRecord(String ORDER_ID) {
        ResultSet rs = null;
        for (int i = 0; i < tableRow; i++) {
            String ORDER_ID_FKOL = jtfOrderID.getText();
            String FOOD_ID_FKM = String.valueOf(jTable2.getValueAt(i, 0));
            String ORDER_QUANTITY = String.valueOf(jTable2.getValueAt(i, 2));
            try {

                stmt = conn.prepareStatement("INSERT INTO " + orderList + " VALUES(?,?,?)");
                stmt.setString(1, jtfOrderID.getText());
                stmt.setString(2, FOOD_ID_FKM);
                stmt.setString(3, ORDER_QUANTITY);
                stmt.executeUpdate();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        }
        return rs;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jbtLogOut = new javax.swing.JButton();
        jbtPayment = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jtfOrderID = new javax.swing.JTextField();
        jtfDate = new javax.swing.JTextField();
        jbtBack = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        jtfTime = new javax.swing.JTextField();
        jcbFoodName = new javax.swing.JComboBox();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jLabel14 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jtfSubTotal = new javax.swing.JTextField();
        jbtAdd = new javax.swing.JButton();
        jLabel20 = new javax.swing.JLabel();
        jcbQuantity = new javax.swing.JComboBox();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel21 = new javax.swing.JLabel();
        jtfTotalGST = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Ordering");
        setResizable(false);

        jbtLogOut.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jbtLogOut.setText("Log Out");
        jbtLogOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtLogOutActionPerformed(evt);
            }
        });

        jbtPayment.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jbtPayment.setText("Proceed to payment");
        jbtPayment.setActionCommand("");
        jbtPayment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtPaymentActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel11.setText("Order ID");

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel12.setText("Date");

        jtfOrderID.setEditable(false);
        jtfOrderID.setBackground(java.awt.SystemColor.controlHighlight);
        jtfOrderID.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N

        jtfDate.setEditable(false);
        jtfDate.setBackground(java.awt.SystemColor.controlHighlight);
        jtfDate.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jtfDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtfDateActionPerformed(evt);
            }
        });

        jbtBack.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jbtBack.setText("Back");
        jbtBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtBackActionPerformed(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel19.setText("Current Time");

        jtfTime.setEditable(false);
        jtfTime.setBackground(java.awt.SystemColor.controlHighlight);
        jtfTime.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jtfTime.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtfTimeActionPerformed(evt);
            }
        });

        jcbFoodName.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jcbFoodName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbFoodNameActionPerformed(evt);
            }
        });

        jTable3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Food ID", "Food Name", "Food Price", "Food Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable3MouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jTable3);

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel14.setText("Quantity");

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel18.setText("Sub Total (include GST)");

        jtfSubTotal.setEditable(false);
        jtfSubTotal.setBackground(java.awt.SystemColor.controlHighlight);
        jtfSubTotal.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N

        jbtAdd.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jbtAdd.setMnemonic('A');
        jbtAdd.setText("Add");
        jbtAdd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jbtAddMouseClicked(evt);
            }
        });
        jbtAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtAddActionPerformed(evt);
            }
        });

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel20.setText("Food ID");

        jcbQuantity.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jcbQuantity.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20" }));

        jTable2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Food ID", "Food Name", "Quantity", "Food Price Per Unit", "GST Per Unit", "Sum Food Price", "Sum GST"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(jTable2);

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel21.setText("Total GST");

        jtfTotalGST.setEditable(false);
        jtfTotalGST.setBackground(java.awt.SystemColor.controlHighlight);
        jtfTotalGST.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel2.setText("Food Ordered");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel4.setText("**All set menu above included one unlimited refill drink.");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 694, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(925, 925, 925))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jLabel11)
                                .addGap(18, 18, 18)
                                .addComponent(jtfOrderID, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel12)
                                .addGap(18, 18, 18)
                                .addComponent(jtfDate, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(45, 45, 45)
                                .addComponent(jLabel19)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jbtBack)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jbtLogOut))
                                    .addComponent(jtfTime, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jbtPayment, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jbtAdd)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel20)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jcbFoodName, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel14)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jcbQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(26, 26, 26))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel18)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jtfSubTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(jLabel21)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jtfTotalGST, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbtLogOut)
                    .addComponent(jbtBack))
                .addGap(47, 47, 47)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jtfOrderID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtfTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19)
                    .addComponent(jLabel12)
                    .addComponent(jtfDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jcbFoodName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel20)
                            .addComponent(jLabel14)
                            .addComponent(jcbQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(28, 28, 28)
                        .addComponent(jbtAdd)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(jtfSubTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(jtfTotalGST, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jbtPayment, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(41, Short.MAX_VALUE))
        );

        setSize(new java.awt.Dimension(1124, 833));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jbtLogOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtLogOutActionPerformed
        new LoginFrame().setVisible(true);
        dispose();
    }//GEN-LAST:event_jbtLogOutActionPerformed

    private void jbtPaymentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtPaymentActionPerformed
        if (click != 0) {
            new PaymentFrame(orderingSID, jtfOrderID.getText(), jtfSubTotal.getText(), jtfTotalGST.getText()).setVisible(true);
            dispose();
        }
    }//GEN-LAST:event_jbtPaymentActionPerformed

    private void jtfDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtfDateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtfDateActionPerformed

    private void jbtBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtBackActionPerformed
        new CashierFrame(orderingSID).setVisible(true);
        setVisible(false);
    }//GEN-LAST:event_jbtBackActionPerformed

    private void jtfTimeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtfTimeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtfTimeActionPerformed

    private void jcbFoodNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbFoodNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jcbFoodNameActionPerformed

    private void jTable3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable3MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable3MouseClicked

    private void jbtAddMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbtAddMouseClicked
        click++;
    }//GEN-LAST:event_jbtAddMouseClicked

    private void jbtAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtAddActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jbtAddActionPerformed

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
            java.util.logging.Logger.getLogger(OrderingFrame.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(OrderingFrame.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(OrderingFrame.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(OrderingFrame.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new OrderingFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JButton jbtAdd;
    private javax.swing.JButton jbtBack;
    private javax.swing.JButton jbtLogOut;
    private javax.swing.JButton jbtPayment;
    private javax.swing.JComboBox jcbFoodName;
    private javax.swing.JComboBox jcbQuantity;
    private javax.swing.JTextField jtfDate;
    private javax.swing.JTextField jtfOrderID;
    private javax.swing.JTextField jtfSubTotal;
    private javax.swing.JTextField jtfTime;
    private javax.swing.JTextField jtfTotalGST;
    // End of variables declaration//GEN-END:variables
}
