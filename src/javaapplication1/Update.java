/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication1;

import java.util.List;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Yee Zi Khai
 */
public class Update extends javax.swing.JFrame {

    public List<Item> itemList = new ArrayList<>();

    public Update() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jlblAdd = new javax.swing.JLabel();
        jlblName = new javax.swing.JLabel();
        jlblPrice = new javax.swing.JLabel();
        jlblCategory = new javax.swing.JLabel();
        jlblDetails = new javax.swing.JLabel();
        jtfName = new javax.swing.JTextField();
        jtfPrice = new javax.swing.JTextField();
        jcbCategory = new javax.swing.JComboBox<>();
        jtfDetails = new javax.swing.JTextField();
        jbtnAdd = new javax.swing.JButton();
        jbtnAdd1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jlblAdd.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jlblAdd.setText("          Update Item");

        jlblName.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jlblName.setText("Item Name               :");

        jlblPrice.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jlblPrice.setText("Price                        :");

        jlblCategory.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jlblCategory.setText("Category                  :");

        jlblDetails.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jlblDetails.setText("Details                      :");

        jcbCategory.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Foods", "Drinks", "Dessert", "Side Dish", " " }));

        jbtnAdd.setText("Update");
        jbtnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnAddActionPerformed(evt);
            }
        });

        jbtnAdd1.setText("Retrieve");
        jbtnAdd1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnAdd1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jlblAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(136, 136, 136))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jlblCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jcbCategory, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jlblPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jtfPrice))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jlblName, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jtfName))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jlblDetails, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jbtnAdd1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jbtnAdd))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jtfDetails, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addGap(109, 109, 109))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlblAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlblName, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtfName, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlblPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtfPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jlblCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcbCategory))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlblDetails, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtfDetails, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbtnAdd)
                    .addComponent(jbtnAdd1))
                .addContainerGap(118, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbtnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnAddActionPerformed
        if (jtfName.getText().equals("") || jtfPrice.getText().equals("") || jtfDetails.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Please fulfill all the requirement.");
        } else {
            Item item = new Item();
            item.setName(jtfName.getText());
            String price = jtfPrice.getText();
            item.setPrice(Integer.valueOf(price));
            Object category = jcbCategory.getSelectedIndex();
            item.setCategory(String.valueOf(category));
            item.setDetails(jtfDetails.getText());
            itemList.set(0, item);
            JOptionPane.showMessageDialog(null, "Update successful!");
                      
            jtfName.setText("");
            jtfPrice.setText("");
            jtfDetails.setText("");
            jcbCategory.setSelectedIndex(0);
        }
    }//GEN-LAST:event_jbtnAddActionPerformed

    private void jbtnAdd1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnAdd1ActionPerformed
        Item item = new Item();
        item.getName();
        item.getPrice();
        item.getCategory();
        item.getDetails();
        itemList.add(item);
        if(jtfName.getText().equals(itemList.get(0).getName()))
        {
            int price = itemList.get(0).getPrice();
            jtfPrice.setText(String.valueOf(price));
            jcbCategory.setSelectedItem(itemList.get(0).getCategory());
            jtfDetails.setText(itemList.get(0).getDetails());
        }else{
            JOptionPane.showMessageDialog(null,"No record found!");
        }
    }//GEN-LAST:event_jbtnAdd1ActionPerformed

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
            java.util.logging.Logger.getLogger(Update.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Update.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Update.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Update.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Update().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jbtnAdd;
    private javax.swing.JButton jbtnAdd1;
    private javax.swing.JComboBox<String> jcbCategory;
    private javax.swing.JLabel jlblAdd;
    private javax.swing.JLabel jlblCategory;
    private javax.swing.JLabel jlblDetails;
    private javax.swing.JLabel jlblName;
    private javax.swing.JLabel jlblPrice;
    private javax.swing.JTextField jtfDetails;
    private javax.swing.JTextField jtfName;
    private javax.swing.JTextField jtfPrice;
    // End of variables declaration//GEN-END:variables
}
