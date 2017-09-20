package com.cresset.asimjofaofficial.models;

/**
 * Created by cresset on 21/06/2017.
 */

public class OrderPlaceResponse {
    String Status;
    String Message;
    String OrderId;
    String OrderTotal;
    String OrderMessage;

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String orderId) {
        OrderId = orderId;
    }

    public String getOrderTotal() {
        return OrderTotal;
    }

    public void setOrderTotal(String orderTotal) {
        OrderTotal = orderTotal;
    }

    public String getOrderMessage() {
        return OrderMessage;
    }

    public void setOrderMessage(String orderMessage) {
        OrderMessage = orderMessage;
    }
}