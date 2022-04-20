package com.example.kdt_teamproject_mobile_kiosk_final.model;

import com.google.type.DateTime;

import java.util.Objects;

public class DTO_Cart_Upload {
    DateTime orderDateTime;
    String orderName;
    long orderNum;
    String orderDetail;
    String orderOpt01;
    String orderOpt02;
    long orderOptPrice01;
    long orderOptPrice02;
    String orderPayment;
    long orderPrice;
    String orderState;
    long totalPrice;

    public DTO_Cart_Upload() {
    }

    // 주문 내역 생성자
    public DTO_Cart_Upload(String orderName, String orderDetail, String orderOpt01, String orderOpt02,
                           long orderOptPrice01, long orderOptPrice02, long totalPrice) {
        this.orderName = orderName;
        this.orderDetail = orderDetail;
        this.orderOpt01 = orderOpt01;
        this.orderOpt02 = orderOpt02;
        this.orderOptPrice01 = orderOptPrice01;
        this.orderOptPrice02 = orderOptPrice02;
        this.totalPrice = totalPrice;
    }

    // 전체 생성자
    public DTO_Cart_Upload(DateTime orderDateTime, String orderName, long orderNum, String orderDetail,
                           String orderOpt01, String orderOpt02, long orderOptPrice01, long orderOptPrice02,
                           String orderPayment, long orderPrice, String orderState, long totalPrice) {
        this.orderDateTime = orderDateTime;
        this.orderName = orderName;
        this.orderNum = orderNum;
        this.orderDetail = orderDetail;
        this.orderOpt01 = orderOpt01;
        this.orderOpt02 = orderOpt02;
        this.orderOptPrice01 = orderOptPrice01;
        this.orderOptPrice02 = orderOptPrice02;
        this.orderPayment = orderPayment;
        this.orderPrice = orderPrice;
        this.orderState = orderState;
        this.totalPrice = totalPrice;
    }

    public DateTime getOrderDateTime() {
        return orderDateTime;
    }

    public void setOrderDateTime(DateTime orderDateTime) {
        this.orderDateTime = orderDateTime;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public long getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(long orderNum) {
        this.orderNum = orderNum;
    }

    public String getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(String orderDetail) {
        this.orderDetail = orderDetail;
    }

    public String getOrderOpt01() {
        return orderOpt01;
    }

    public void setOrderOpt01(String orderOpt01) {
        this.orderOpt01 = orderOpt01;
    }

    public String getOrderOpt02() {
        return orderOpt02;
    }

    public void setOrderOpt02(String orderOpt02) {
        this.orderOpt02 = orderOpt02;
    }

    public long getOrderOptPrice01() {
        return orderOptPrice01;
    }

    public void setOrderOptPrice01(long orderOptPrice01) {
        this.orderOptPrice01 = orderOptPrice01;
    }

    public long getOrderOptPrice02() {
        return orderOptPrice02;
    }

    public void setOrderOptPrice02(long orderOptPrice02) {
        this.orderOptPrice02 = orderOptPrice02;
    }

    public String getOrderPayment() {
        return orderPayment;
    }

    public void setOrderPayment(String orderPayment) {
        this.orderPayment = orderPayment;
    }

    public long getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(long orderPrice) {
        this.orderPrice = orderPrice;
    }

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }

    public long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(long totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "DTO_Cart02{" +
                "orderDateTime=" + orderDateTime +
                ", orderName='" + orderName + '\'' +
                ", orderNum=" + orderNum +
                ", orderDetail='" + orderDetail + '\'' +
                ", orderOpt01='" + orderOpt01 + '\'' +
                ", orderOpt02='" + orderOpt02 + '\'' +
                ", orderOptPrice01=" + orderOptPrice01 +
                ", orderOptPrice02=" + orderOptPrice02 +
                ", orderPayment='" + orderPayment + '\'' +
                ", orderPrice=" + orderPrice +
                ", orderState='" + orderState + '\'' +
                ", totalPrice=" + totalPrice +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DTO_Cart_Upload that = (DTO_Cart_Upload) o;
        return orderNum == that.orderNum && orderOptPrice01 == that.orderOptPrice01 && orderOptPrice02 == that.orderOptPrice02 && orderPrice == that.orderPrice && totalPrice == that.totalPrice && Objects.equals(orderDateTime, that.orderDateTime) && Objects.equals(orderName, that.orderName) && Objects.equals(orderDetail, that.orderDetail) && Objects.equals(orderOpt01, that.orderOpt01) && Objects.equals(orderOpt02, that.orderOpt02) && Objects.equals(orderPayment, that.orderPayment) && Objects.equals(orderState, that.orderState);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderDateTime, orderName, orderNum, orderDetail, orderOpt01, orderOpt02, orderOptPrice01, orderOptPrice02, orderPayment, orderPrice, orderState, totalPrice);
    }
}
