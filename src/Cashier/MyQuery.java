/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cashier;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lenovo-IBM
 */
public class MyQuery {

    private String host = "jdbc:derby://localhost:1527/vegansdb";
    private String user = "nbuser";
    private String password = "nbuser";
    private String tableName = "Menu";
    private Connection conn;
    private PreparedStatement stmt;
    private String sqlQueryStr = "SELECT * from " + tableName;
    private String sqlQueryStr2 = "SELECT * from ORDERING";
    private String sqlQueryStr3 = "SELECT * from PAYMENT";
    private String sqlQueryStr4 = "SELECT * from RESERVATION";
    private ResultSet rs;

    public MyQuery() {
        createConnection();
    }

    public void createConnection() {
        try {
            conn = DriverManager.getConnection(host, user, password);
        } catch (SQLException ex) {
            ex.getMessage();
        }
    }

    public ArrayList<MenuList> getMenu() {
        ArrayList<MenuList> ml = new ArrayList<>();

        try {
            stmt = conn.prepareStatement(sqlQueryStr, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = stmt.executeQuery();

            while (rs.next()) {
                String FOOD_ID = rs.getString(1);
                String FOOD_NAME = rs.getString(2);
                String FOOD_PRICE = rs.getString(3);
                String FOOD_STATUS = rs.getString(4);

                MenuList menuL = new MenuList(FOOD_ID, FOOD_NAME, FOOD_PRICE, FOOD_STATUS);
                ml.add(menuL);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MyQuery.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ml;
    }

    public ArrayList<OrderDomain> getOrder() {
        ArrayList<OrderDomain> od = new ArrayList<>();

        try {
            stmt = conn.prepareStatement(sqlQueryStr2, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = stmt.executeQuery();

            while (rs.next()) {
                String ORDER_ID = rs.getString(1);

                OrderDomain orderD = new OrderDomain(ORDER_ID);
                od.add(orderD);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MyQuery.class.getName()).log(Level.SEVERE, null, ex);
        }
        return od;
    }

    public ArrayList<PaymentDomain> getPayment() {
        ArrayList<PaymentDomain> pd = new ArrayList<>();

        try {
            stmt = conn.prepareStatement(sqlQueryStr3, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = stmt.executeQuery();

            while (rs.next()) {
                String PAYMENT_ID = rs.getString(1);

                PaymentDomain paymentD = new PaymentDomain(PAYMENT_ID);
                pd.add(paymentD);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MyQuery.class.getName()).log(Level.SEVERE, null, ex);
        }
        return pd;
    }

//    public ArrayList<ReservationDomain> getReserv() {
//        ArrayList<ReservationDomain> rd = new ArrayList<>();
//
//        try {
//            stmt = conn.prepareStatement(sqlQueryStr4, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
//            rs = stmt.executeQuery();
//
//            while (rs.next()) {
//                String RESERVE_ID = rs.getString(1);
//
//                ReservationDomain reservationD = new ReservationDomain(RESERVE_ID);
//                rd.add(reservationD);
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(MyQuery.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return rd;
//    }

}
