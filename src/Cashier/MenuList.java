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
public class MenuList {
    private String FOOD_ID;
    private String FOOD_NAME;
    private String FOOD_PRICE;
    private String FOOD_STATUS;

    public MenuList() {
    }

    public MenuList(String FOOD_ID, String FOOD_NAME, String FOOD_PRICE, String FOOD_STATUS) {
        this.FOOD_ID = FOOD_ID;
        this.FOOD_NAME = FOOD_NAME;
        this.FOOD_PRICE = FOOD_PRICE;
        this.FOOD_STATUS = FOOD_STATUS;
    }

    public String getFOOD_ID() {
        return FOOD_ID;
    }

    public String getFOOD_NAME() {
        return FOOD_NAME;
    }

    public String getFOOD_PRICE() {
        return FOOD_PRICE;
    }

    public String getFOOD_STATUS() {
        return FOOD_STATUS;
    }
    
    
}
