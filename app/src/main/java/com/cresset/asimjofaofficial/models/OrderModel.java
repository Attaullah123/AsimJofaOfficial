package com.cresset.asimjofaofficial.models;

import java.util.List;

/**
 * Created by cresset on 28/07/2017.
 */

public class OrderModel {
    public String Message;
    public String Status;
    public List<OrdersListModel> CustomerOrders;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public List<OrdersListModel> getCustomerOrders() {
        return CustomerOrders;
    }

    public void setCustomerOrders(List<OrdersListModel> customerOrders) {
        CustomerOrders = customerOrders;
    }
}
