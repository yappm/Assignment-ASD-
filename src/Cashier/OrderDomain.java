/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cashier;

import java.util.Date;

/**
 *
 * @author Liew Cheng Khai
 */
public class OrderDomain {
    private String ORDER_ID;

    public OrderDomain() {
    }

    public OrderDomain(String ORDER_ID) {
        this.ORDER_ID = ORDER_ID;
    }

    public String getORDER_ID() {
        return ORDER_ID;
    }

    public void setORDER_ID(String ORDER_ID) {
        this.ORDER_ID = ORDER_ID;
    }
    
}
