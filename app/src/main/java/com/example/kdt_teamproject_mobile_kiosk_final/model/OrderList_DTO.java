package com.example.kdt_teamproject_mobile_kiosk_final.model;

public class OrderList_DTO {

    private String orderState, orderNum, orderDateTime, orderPayment, totalPrice;

    public OrderList_DTO(String orderState, String orderNum, String orderDateTime, String orderPayment, String totalPrice) {
        this.orderState = orderState;
        this.orderNum = orderNum;
        this.orderDateTime = orderDateTime;
        this.orderPayment = orderPayment;
        this.totalPrice = totalPrice;
    }

    public OrderList_DTO() {

    }

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getOrderDateTime() {
        return orderDateTime;
    }

    public void setOrderDateTime(String orderDateTime) {
        this.orderDateTime = orderDateTime;
    }

    public String getOrderPayment() {
        return orderPayment;
    }

    public void setOrderPayment(String orderPayment) {
        this.orderPayment = orderPayment;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

}
