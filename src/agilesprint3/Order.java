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
public class Order {
    private int order_id=1000;
    private String restaurant="Al-Farid";
    private String selectFood="Nasi Goreng";
    private int quantity=2;
    private String date="Mon,Tue,Wed";
    private String time="2.00pm";
    private String address="Jalan Malinja,KL";
    
    public void OrderList(){
       this.order_id=order_id;
       
    }
    
    public void setOrderId(int order_id){
        this.order_id = order_id;
    }
    
    public void setRestaurant(String restaurant) {
        this.restaurant = restaurant;
    }

    public void setSelectFood(String selectFood) {
        this.selectFood = selectFood;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    
    public int getOrderId(){
        return order_id;
    }
    
    public String getRestaurant() {
        return restaurant;
    }

    public String getSelectFood() {
        return selectFood;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getAddress() {
        return address;
    }
    
    
    
    
    
}
