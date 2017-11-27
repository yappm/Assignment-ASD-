package Main;

//import Cashier.CashierFrame;
import Cashier.OrderingFrame;
//import Manager.ManagerFrame;
import com.sun.glass.events.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.*;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author B1
 */
public class LoginFrame extends javax.swing.JFrame implements Serializable{

    private String host = "jdbc:derby://localhost:1527/vegansdb";
    private String user = "nbuser";
    private String password = "nbuser";
    private Connection conn;
    private PreparedStatement stmt;
    private String tableName = "Staff";
    private ResultSet rs;
    
    public LoginFrame() {
        initComponents();
        jbtLogin.addActionListener(new LoginListener());
        createConnection();
    }


    public void createConnection() {
        try {

            conn = DriverManager.getConnection(host, user, password);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), null, JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearField() {
        
        jpfPassword.setText("");
    }

    private class LoginListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String ID = jtfID.getText();
            String pass = jpfPassword.getText();
            ResultSet rsID = searchID(ID, pass);
            if (ID.equals("")) {
                JOptionPane.showMessageDialog(null, "Please fullfill the fields above", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                try {
                    if (rsID.next()) {
                        if (ID.equals("MN001") && pass.equals(rsID.getString("Staff_pass"))) {
                            new OrderingFrame().setVisible(true);
                            
                            setVisible(false);
                        } else {
                            String tempID = ID;
                            new OrderingFrame().setVisible(true);
                            setVisible(false);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid ID or Password");
                        clearField();
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }

            }
        }
    }

    public ResultSet searchID(String ID, String pass) {
        ResultSet rs = null;
        try {
            stmt = conn.prepareStatement("SELECT * FROM " + tableName + " WHERE Staff_ID = ? AND Staff_Pass = ?");
            stmt.setString(1, ID);
            stmt.setString(2, pass);
            rs = stmt.executeQuery();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        return rs;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jtfID = new javax.swing.JTextField();
        jpfPassword = new javax.swing.JPasswordField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jbtForget = new javax.swing.JButton();
        jbtLogin = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Login");
        setBackground(new java.awt.Color(204, 255, 204));
        setResizable(false);

        jtfID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtfIDActionPerformed(evt);
            }
        });
        jtfID.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtfIDKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtfIDKeyTyped(evt);
            }
        });

        jpfPassword.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jpfPasswordKeyPressed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("ID");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Password");

        jLabel3.setFont(new java.awt.Font("Forte", 1, 48)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 153, 102));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("The Vegans");

        jbtForget.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jbtForget.setMnemonic('F');
        jbtForget.setText("Forget Password?");
        jbtForget.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtForgetActionPerformed(evt);
            }
        });

        jbtLogin.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jbtLogin.setMnemonic('L');
        jbtLogin.setText("Login");
        jbtLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtLoginActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel4.setText("Please login before use!");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jbtForget)
                                .addGap(18, 18, 18)
                                .addComponent(jbtLogin))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jtfID, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jpfPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(48, 48, 48))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtfID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jpfPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbtLogin)
                    .addComponent(jbtForget))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setSize(new java.awt.Dimension(350, 297));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jbtLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtLoginActionPerformed

    }//GEN-LAST:event_jbtLoginActionPerformed

    private void jpfPasswordKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jpfPasswordKeyPressed
        String ID = jtfID.getText();
        String pass = jpfPassword.getText();
        ResultSet rsID = searchID(ID, pass);
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (ID.equals("")) {
                JOptionPane.showMessageDialog(null, "Please fullfill the fields above", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                try {
                    if (rsID.next()) {
                        if (ID.equals("MN001") && pass.equals(rsID.getString("Staff_pass"))) {
                            new OrderingFrame().setVisible(true);
                            dispose();
                        } else {
                            String tempID = ID;
                            new OrderingFrame(tempID).setVisible(true);
                            dispose();
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid ID or Password");
                        clearField();
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }

            }
        }
    }//GEN-LAST:event_jpfPasswordKeyPressed

    private void jbtForgetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtForgetActionPerformed

    }//GEN-LAST:event_jbtForgetActionPerformed

    private void jtfIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtfIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtfIDActionPerformed

    private void jtfIDKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfIDKeyPressed
       String ID = jtfID.getText();
        String pass = jpfPassword.getText();
        ResultSet rsID = searchID(ID, pass);
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (ID.equals("")) {
                JOptionPane.showMessageDialog(null, "Please fullfill the fields above", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                try {
                    if (rsID.next()) {
                        if (ID.equals("MN001") && pass.equals(rsID.getString("Staff_pass"))) {
                            new OrderingFrame().setVisible(true);
                            setVisible(false);
                        } else {
                            String tempID = ID;
                            new OrderingFrame(tempID).setVisible(true);
                            setVisible(false);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid ID or Password");
                        clearField();
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }

            }
        }
    }//GEN-LAST:event_jtfIDKeyPressed

    private void jtfIDKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfIDKeyTyped

    }//GEN-LAST:event_jtfIDKeyTyped

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
            java.util.logging.Logger.getLogger(LoginFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LoginFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LoginFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoginFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LoginFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JButton jbtForget;
    private javax.swing.JButton jbtLogin;
    private javax.swing.JPasswordField jpfPassword;
    public static javax.swing.JTextField jtfID;
    // End of variables declaration//GEN-END:variables
}
