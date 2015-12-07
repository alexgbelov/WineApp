package com.example.parse;

import java.util.Date;

/**
 * Created by tyleraskew on 12/6/15.
 */
public class OrderedItem {

    // instance variables
    private MenuItem menuItem;
    private Double totalPrice;
    private Integer quantity;
    private Date timeOrdered;
    private Boolean isFulfilled;

    public OrderedItem(MenuItem menuItem, Date timeOrdered, Integer quantity, Boolean isFulfilled) {
        this.menuItem = menuItem;
        this.timeOrdered = timeOrdered;
        this.quantity = quantity;
        this.isFulfilled = isFulfilled;
        this.totalPrice = menuItem.getPrice().doubleValue() * quantity;
    }

    public MenuItem getMenuItem() {
        return menuItem;
    }

    public Date getTimeOrdered() {
        return timeOrdered;
    }

    public void setMenuItem(MenuItem menuItem) {
        this.menuItem = menuItem;
    }

    public void setTimeOrdered(Date timeOrdered) {
        this.timeOrdered = timeOrdered;
    }

    public Boolean getIsFulfilled() {
        return isFulfilled;
    }

    public void setIsFulfilled(Boolean isFulfilled) {
        this.isFulfilled = isFulfilled;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

}
