/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cashier;

/**
 *
 * @author Lenovo-IBM
 */
public class PaymentDomain {
    private String PAYMENT_ID;

    public PaymentDomain() {
    }

    public PaymentDomain(String PAYMENT_ID) {
        this.PAYMENT_ID = PAYMENT_ID;
    }

    public String getPAYMENT_ID() {
        return PAYMENT_ID;
    }

    public void setPAYMENT_ID(String PAYMENT_ID) {
        this.PAYMENT_ID = PAYMENT_ID;
    }
    
}
