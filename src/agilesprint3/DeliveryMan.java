/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agilesprint3;

/**
 *
 * @author Asus
 */
public class DeliveryMan {
    private String deliver_name= "Kiven";
    private String status = "Free";

    public DeliveryMan() {
        this.deliver_name=deliver_name;
        this.status=status;
    }

    public void setDeliver_name(String deliver_name) {
        this.deliver_name = deliver_name;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDeliver_name() {
        return deliver_name;
    }

    public String getStatus() {
        return status;
    }
    
    
    
}
